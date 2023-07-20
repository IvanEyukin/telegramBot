package bot.handler.callback;

import TelegramBot.BotSendMessage;
import bot.database.ReportDatabase;
import bot.entitie.Bot;
import bot.entitie.User;
import bot.keyboard.Keyboard;
import bot.message.Setting;
import bot.message.callback.CallbackSetting;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import java.util.ArrayList;
import java.util.List;


public class HandlerSetting {

    Bot bot;
    User user = new User();
    BotSendMessage sendMessage = new BotSendMessage();
    List<SendMessage> messages = new ArrayList<SendMessage>();
    ReportDatabase database = new ReportDatabase();

    public HandlerSetting(Bot bot) {
        this.bot = bot;
    }

    private void setUserNotification(String notification) {
        user.setId(bot.getUser().getId());
        user.setName(bot.getUser().getName());
        user.setFirstName(bot.getUser().getFirstName());
        user.setLastName(bot.getUser().getLastName());
        user.setDateMessage(bot.getUser().getDateMessage());
        user.setNotification(notification);
        bot.setUser(user);
    }

    public Bot setSetting() {
        String notification = "";
        String notificationAll = Keyboard.setting.get(0).getCallbackData();
        String notificationActive = Keyboard.setting.get(1).getCallbackData();
        String notificationDisabled = Keyboard.setting.get(2).getCallbackData();

        if (bot.getCallbackData().equals(notificationAll)) {
            notification = CallbackSetting.notificationKey.get(notificationAll);
            messages.add(sendMessage.sendMessage(Setting.SAVE.concat(CallbackSetting.notification.get(notification))));
        } else if (bot.getCallbackData().equals(notificationActive)) {
            notification = CallbackSetting.notificationKey.get(notificationActive);
            messages.add(sendMessage.sendMessage(Setting.SAVE.concat(CallbackSetting.notification.get(notification))));
        } else if (bot.getCallbackData().equals(notificationDisabled)) {
            notification = CallbackSetting.notificationKey.get(notificationDisabled);
            messages.add(sendMessage.sendMessage(Setting.SAVE.concat(CallbackSetting.notification.get(notification))));
        }
        setUserNotification(notification);
        database.updateUser(bot.getUser());
        bot.setMessages(messages);

        return bot;
    }
}