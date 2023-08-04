package bot.handler.message;

import bot.database.ReportDatabase;
import bot.entitie.Bot;
import bot.entitie.User;
import bot.keyboard.Keyboard;
import bot.message.Admin;
import bot.message.send.MessageBuilder;
import bot.processors.NotificationProcessor;
import bot.state.State;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;


public class HandlerAdmin {

    Bot bot;
    NotificationProcessor notification = new NotificationProcessor();
    List<SendMessage> messages = new ArrayList<SendMessage>();
    MessageBuilder message = new MessageBuilder();

    public HandlerAdmin(Bot bot) {
        this.bot = bot;
    }

    private List<SendMessage> usersReport() {
        ReportDatabase database = new ReportDatabase();
        List<User> users = database.selectUserLastMessageAdminReport();
        String usersReport = "";
        for (User user : users) {
            usersReport = usersReport.concat(String.format(Admin.USERS_REPORT_DETAIL, user.getName(), LocalDateTime.ofInstant(Instant.ofEpochSecond(user.getDateMessage()), TimeZone.getDefault().toZoneId())));
        }
        messages.add(message.sendMessage(usersReport));
        return messages;
    }

    public Bot adminMenu() {
        switch (bot.getState()) {
            case AdminMenu -> {
                if (Keyboard.replyKeyboard.ADMIN.contains(bot.getUserMessageText())) {
                    if (bot.getUserMessageText().contains(Keyboard.replyKeyboard.ADMIN.get(0))) {
                        bot.updateBotState(State.WaitingMessageMailings);
                        messages.add(message.sendMessage(Admin.NOTIFICATION));
                    } else if (bot.getUserMessageText().contains(Keyboard.replyKeyboard.ADMIN.get(1))) {
                        bot.updateBotState(State.Start);
                        usersReport();
                    }
                } else {
                    messages.add(message.sendMessageAndKeyboard(Admin.ERROR, Keyboard.replyKeyboard.ADMIN));
                }
            }
            case WaitingMessageMailings -> {
                bot.setAdminNotificationMessages(notification.startNotification(bot.getUserMessageText()));
                bot.updateBotState(State.Start);
                messages.add(message.sendMessage(Admin.NOTIFICATION_START));
            }
            default -> {
                bot.updateBotState(State.AdminMenu);
                messages.add(message.sendMessageAndKeyboard(Admin.MENU, Keyboard.replyKeyboard.ADMIN));
            }
        }
        bot.setMessages(messages);

        return bot;
    }
}