package Processors;

import java.util.ArrayList;
import java.util.List;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import Database.ReportDatabase;
import LibBaseDto.DtoBaseBot.BotMessage;
import LibBaseDto.DtoBaseKeyboard.KeyboardMessage;
import LibBaseDto.DtoBaseUser.UserInfo;
import TelegramBot.BotSendMessage;

public class SettingProcessorRequest {

    public BotMessage setSettingRequest (BotMessage botMessage) {

        BotSendMessage sendMessage = new BotSendMessage();
        KeyboardMessage keyboardMessage = new KeyboardMessage();
        List<SendMessage> messages = new ArrayList<SendMessage>();
        ReportDatabase report = new ReportDatabase();
        UserInfo user = new UserInfo();

        user.setId(botMessage.getUserInfo().getId());
        user.setName(botMessage.getUserInfo().getName());
        user.setFirstName(botMessage.getUserInfo().getFirstName());
        user.setLastName(botMessage.getUserInfo().getLastName());
        user.setDateMessage(botMessage.getUserInfo().getDateMessage());

        if (botMessage.getCallbackData().equals(keyboardMessage.getNotificationAllButton().getCallBack())) {
            user.setNotification("all");
            messages.add(sendMessage.sendMessage(botMessage.settingNotificationSave.concat("напоминать регулярно")));
        } else if (botMessage.getCallbackData().equals(keyboardMessage.getNotificationActiveButton().getCallBack())) {
            user.setNotification("active");
            messages.add(sendMessage.sendMessage(botMessage.settingNotificationSave.concat("напоминать, если сегодня не было диалога с ботом")));
        } else if (botMessage.getCallbackData().equals(keyboardMessage.getNotificationFalseButton().getCallBack())) {
            user.setNotification("disabled");
            messages.add(sendMessage.sendMessage(botMessage.settingNotificationSave.concat("не напоминать")));
        }

        botMessage.setUserInfo(user);
        report.updateUser(botMessage.getUserInfo());

        botMessage.setMessages(messages);

        return botMessage;

    }
    
}