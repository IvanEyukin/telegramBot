package Processors;

import TelegramBot.BotSendMessage;
import bot.database.ReportDatabase;
import bot.keyboard.Keyboard;
import bot.message.BotMessage;
import bot.message.Setting;
import bot.state.State;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import java.util.ArrayList;
import java.util.List;


public class SettingProcessor {

    public BotMessage getSetting(BotMessage botMessage) {

        List<SendMessage> messages = new ArrayList<SendMessage>();
        BotSendMessage sendMessage = new BotSendMessage();
        ReportDatabase report = new ReportDatabase();
        
        switch (botMessage.getSession()) {
            case SettingMenu -> {
                String notification = "";
                botMessage.setUserInfo(report.selectUser(botMessage.getUserInfo()));
                switch (botMessage.getUserInfo().getNotification()) {
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
                messages.add(sendMessage.sendMessageAndInline(String.format(Setting.NOTIFICATION, botMessage.getUserInfo().getUser(), notification), Keyboard.setting)); 
                botMessage.setMessageHasInLineKeyboaard(true);
                botMessage.updateBotState(State.InformationRetentionQuestionsSelection);
            }
            default -> {
                messages.add(sendMessage.sendMessageAndKeyboard(Setting.MENU, Keyboard.replyKeyboar.SETTING));
                botMessage.updateBotState(State.SettingMenu);
            }
        }
        botMessage.setMessages(messages);

        return botMessage;
    }
}