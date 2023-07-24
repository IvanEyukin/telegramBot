package bot.handler.message;

import bot.entitie.Bot;
import bot.keyboard.Keyboard;
import bot.message.Admin;
import bot.message.send.MessageBuilder;
import bot.processors.NotificationProcessor;
import bot.state.State;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import java.util.ArrayList;
import java.util.List;


public class HandlerAdmin {

    Bot bot;
    NotificationProcessor notification = new NotificationProcessor();
    List<SendMessage> messages = new ArrayList<SendMessage>();
    MessageBuilder message = new MessageBuilder();

    public HandlerAdmin(Bot bot) {
        this.bot = bot;
    }

    public Bot adminMenu() {
        switch (bot.getState()) {
            case AdminMenu -> {
                bot.updateBotState(State.WaitingMessageMailings);
                messages.add(message.sendMessage(Admin.NOTIFICATION));
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