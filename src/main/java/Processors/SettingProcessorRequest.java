package Processors;

import java.util.ArrayList;
import java.util.List;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import TelegramBot.BotSendMessage;
import bot.database.ReportDatabase;
import bot.dto.Bot;
import bot.dto.User;
import bot.keyboard.Keyboard;
import bot.message.Setting;

public class SettingProcessorRequest {

    public Bot setSettingRequest (Bot bot) {

        BotSendMessage sendMessage = new BotSendMessage();
        List<SendMessage> messages = new ArrayList<SendMessage>();
        ReportDatabase report = new ReportDatabase();
        User user = new User();

        user.setId(bot.getUser().getId());
        user.setName(bot.getUser().getName());
        user.setFirstName(bot.getUser().getFirstName());
        user.setLastName(bot.getUser().getLastName());
        user.setDateMessage(bot.getUser().getDateMessage());

        if (bot.getCallbackData().equals(Keyboard.help.get(0).getCallbackData())) {
            user.setNotification("all");
            messages.add(sendMessage.sendMessage(Setting.SAVE.concat("напоминать регулярно")));
        } else if (bot.getCallbackData().equals(Keyboard.help.get(1).getCallbackData())) {
            user.setNotification("active");
            messages.add(sendMessage.sendMessage(Setting.SAVE.concat("напоминать, если сегодня не было диалога с ботом")));
        } else if (bot.getCallbackData().equals(Keyboard.help.get(2).getCallbackData())) {
            user.setNotification("disabled");
            messages.add(sendMessage.sendMessage(Setting.SAVE.concat("не напоминать")));
        }
        bot.setUser(user);
        report.updateUser(bot.getUser());
        bot.setMessages(messages);

        return bot;
    }
}