package Processors;

import BotFSM.BotState;
import Database.ReportDatabase;
import LibBaseDto.DtoBaseBot.BotMessage;
import LibBaseDto.DtoBaseKeyboard.KeyboardMessage;
import TelegramBot.BotSendMessage;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import java.util.ArrayList;
import java.util.List;


public class SettingProcessor {

    public BotMessage getSetting(BotMessage botMessage) {

        List<SendMessage> messages = new ArrayList<SendMessage>();
        KeyboardMessage keyboardMessage = new KeyboardMessage();
        BotSendMessage sendMessage = new BotSendMessage();
        ReportDatabase report = new ReportDatabase();
        
        switch (botMessage.getBotState()) {
            case SettingMenu -> {
                String notification = "";
                botMessage.setUserInfo(report.searchUserInfo(botMessage.getUserInfo()));
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
                messages.add(sendMessage.sendMessageAndInline(String.format(botMessage.settingNotificationQuestion, botMessage.getUserInfo().getUser(), notification), keyboardMessage.getNotificationButtons()));
                botMessage.setMessageHasInLineKeyboaard(true);
                botMessage.updateBotState(BotState.WaitCallbacSetting);
            }
            default -> {
                messages.add(sendMessage.sendMessageAndKeyboard(botMessage.settingQuestion, keyboardMessage.getSettingMenuButton()));
                botMessage.updateBotState(BotState.SettingMenu);
            }
        }

        botMessage.setMessages(messages);

        return botMessage;

    }

}