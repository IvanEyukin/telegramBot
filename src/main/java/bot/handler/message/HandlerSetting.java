package bot.handler.message;

import bot.database.ReportDatabase;
import bot.entitie.Bot;
import bot.entitie.User;
import bot.keyboard.Keyboard;
import bot.message.Setting;
import bot.message.send.MessageBuilder;
import bot.processors.CheckUserInDatabase;
import bot.state.State;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import java.util.ArrayList;
import java.util.List;


public class HandlerSetting {

    Bot bot;
    List<SendMessage> messages = new ArrayList<SendMessage>();
    MessageBuilder message = new MessageBuilder();
    ReportDatabase report = new ReportDatabase();

    public HandlerSetting(Bot bot) {
        this.bot = bot;
    }

    public Bot getSetting() {
        switch (bot.getState()) {
            case SettingMenu -> {
                CheckUserInDatabase checkUserInDatabase = new CheckUserInDatabase();
                checkUserInDatabase.checkUser(bot.getUser());
                User userDb = report.selectUser(bot.getUser());
                userDb.setDateMessage(bot.getUser().getDateMessage());
                bot.setUser(userDb);
                bot.setMessageHasInLineKeyboaard(true);
                bot.updateBotState(State.ReminderOptionsSelection);
                String notification = Setting.notification.get(bot.getUser().getNotification());
                messages.add(message.sendMessageAndInline(String.format(Setting.NOTIFICATION, bot.getUser().getUser(), notification), Keyboard.setting)); 
            }
            default -> {
                bot.updateBotState(State.SettingMenu);
                messages.add(message.sendMessageAndKeyboard(Setting.MENU, Keyboard.replyKeyboard.SETTING));
            }
        }
        bot.setMessages(messages);

        return bot;
    }
}