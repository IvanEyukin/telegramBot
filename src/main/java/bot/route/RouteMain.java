package bot.route;

import java.util.Map;

import org.telegram.abilitybots.api.sender.MessageSender;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import TelegramBot.BotSendMessage;
import TelegramBot.ResponceMessage;
import bot.entitie.Bot;
import bot.entitie.User;
import bot.log.LogMessage;
import bot.log.Message;
import bot.log.Service;
import bot.message.Admin;
import bot.session.Session;
import bot.setting.Setting;

public class RouteMain {

    Bot bot = new Bot();
    User user = new User(); 
    Session Sessions = new Session();
    BotSendMessage sendMessage = new BotSendMessage();
    private final Update update;
    private final ResponceMessage responceMessage;

    public RouteMain(Update update, MessageSender sender) {
        this.update = update;
        this.responceMessage = new ResponceMessage(sender);
    }

    public void route() {
        if (update.hasMessage() && update.getMessage().hasText()) {
            routeMessage();
        } else if (update.hasCallbackQuery()) {
            routeCallback();
        }
    }

    private User setUser() {
        user.setId(update.getMessage().getChat().getId());
        user.setName(update.getMessage().getChat().getUserName());
        user.setFirstName(update.getMessage().getChat().getFirstName());
        user.setLastName(update.getMessage().getChat().getLastName());
        user.setDateMessage(update.getMessage().getDate());
        return user;
    }

    private void deleteInLineKeyboard() {
        if (bot.getMessageHasInLineKeyboaard() == true) {
            responceMessage.sendMessage(bot, sendMessage.updateMessage(bot.getUser().getId(), bot.getBotMessageId()));
            bot.setMessageHasInLineKeyboaard(false);
        }
    }

    private void Notification() {
        if (bot.getAdminNotificationMessages() != null && !bot.getAdminNotificationMessages().isEmpty()) {
            for (Map.Entry<Long, String> adminMessage : bot.getAdminNotificationMessages().entrySet()) {
                responceMessage.sendMessage(adminMessage.getKey(), adminMessage.getValue());
                LogMessage.outLogMessage(Service.NOTIFICATION, Message.DISTRIBUTION.concat(Long.toString(adminMessage.getKey())));
            }
            responceMessage.sendMessage((long) Setting.creatorId, Admin.NOTIFICATION_STOP);
        }
    }

    private void routeMessage() {
        RouteMessage routeMessage = new RouteMessage();
        bot.setUser(setUser());
        bot.setUserMessageText(update.getMessage().getText());
        bot = Sessions.getSession(bot);

        deleteInLineKeyboard();
        bot = routeMessage.routeMessageProcessor(bot);
        for (SendMessage message : bot.getMessages()) {
            responceMessage.sendMessage(bot, message);
        }
        Notification();
    }

    private void routeCallback() {
        RouteCallback routeCallback = new RouteCallback();
        responceMessage.answerCallback(update.getCallbackQuery().getId());
        user.setId(update.getCallbackQuery().getFrom().getId());
        bot.setUser(user);
        bot.setCallbackData(update.getCallbackQuery().getData());

        bot = Sessions.getSession(bot);
        bot = routeCallback.routeCallbacProcessor(bot);
        for (SendMessage message : bot.getMessages()) {
            responceMessage.sendMessage(bot, message);
        }
        responceMessage.sendMessage(bot, sendMessage.updateMessage(update.getCallbackQuery().getMessage()));
    }  
}