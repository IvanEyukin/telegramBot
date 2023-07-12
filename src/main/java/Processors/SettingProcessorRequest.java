package Processors;

import java.util.ArrayList;
import java.util.List;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import LibBaseDto.DtoBaseUser.UserInfo;
import TelegramBot.BotSendMessage;
import bot.database.ReportDatabase;
import bot.keyboard.Keyboard;
import bot.message.BotMessage;
import bot.message.Setting;

public class SettingProcessorRequest {

    public BotMessage setSettingRequest (BotMessage botMessage) {

        BotSendMessage sendMessage = new BotSendMessage();
        List<SendMessage> messages = new ArrayList<SendMessage>();
        ReportDatabase report = new ReportDatabase();
        UserInfo user = new UserInfo();

        user.setId(botMessage.getUserInfo().getId());
        user.setName(botMessage.getUserInfo().getName());
        user.setFirstName(botMessage.getUserInfo().getFirstName());
        user.setLastName(botMessage.getUserInfo().getLastName());
        user.setDateMessage(botMessage.getUserInfo().getDateMessage());

        if (botMessage.getCallbackData().equals(Keyboard.help.get(0).getCallbackData())) {
            user.setNotification("all");
            messages.add(sendMessage.sendMessage(Setting.SAVE.concat("напоминать регулярно")));
        } else if (botMessage.getCallbackData().equals(Keyboard.help.get(1).getCallbackData())) {
            user.setNotification("active");
            messages.add(sendMessage.sendMessage(Setting.SAVE.concat("напоминать, если сегодня не было диалога с ботом")));
        } else if (botMessage.getCallbackData().equals(Keyboard.help.get(2).getCallbackData())) {
            user.setNotification("disabled");
            messages.add(sendMessage.sendMessage(Setting.SAVE.concat("не напоминать")));
        }
        botMessage.setUserInfo(user);
        report.updateUser(botMessage.getUserInfo());
        botMessage.setMessages(messages);

        return botMessage;
    }
}