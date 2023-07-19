package Processors;

import TelegramBot.BotSendMessage;
import bot.database.ReportDatabase;
import bot.entitie.Bot;
import bot.keyboard.Keyboard;
import bot.message.Setting;
import bot.state.State;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import java.util.ArrayList;
import java.util.List;


public class SettingProcessor {

    public Bot getSetting(Bot bot) {

        List<SendMessage> messages = new ArrayList<SendMessage>();
        BotSendMessage sendMessage = new BotSendMessage();
        ReportDatabase report = new ReportDatabase();
        
        switch (bot.getState()) {
            case SettingMenu -> {
                String notification = "";
                bot.setUser(report.selectUser(bot.getUser()));
                switch (bot.getUser().getNotification()) {
                    case "all" -> {
                        notification = "включены регулярные напоминания";
                    }
                    case "active" -> {
                        notification = "включены напоминания, если не было диалога с ботом";
                    }
                    case "disabled" -> {
                        notification = "напоминания выключены";
                    }
                }
                messages.add(sendMessage.sendMessageAndInline(String.format(Setting.NOTIFICATION, bot.getUser().getUser(), notification), Keyboard.setting)); 
                bot.setMessageHasInLineKeyboaard(true);
                bot.updateBotState(State.InformationRetentionQuestionsSelection);
            }
            default -> {
                messages.add(sendMessage.sendMessageAndKeyboard(Setting.MENU, Keyboard.replyKeyboar.SETTING));
                bot.updateBotState(State.SettingMenu);
            }
        }
        bot.setMessages(messages);

        return bot;
    }
}