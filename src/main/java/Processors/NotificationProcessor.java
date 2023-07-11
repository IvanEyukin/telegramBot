package Processors;

import LibBaseDto.DtoBaseBot.BotMessage;
import LibBaseDto.DtoBaseBot.BotSetting;
import LibBaseDto.DtoBaseUser.UserInfo;
import bot.database.ReportDatabase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class NotificationProcessor {

    public Map<Long, String> startNotification(String message) {

        ReportDatabase report = new ReportDatabase(); 
        Map<Long, String> usersMessage = new HashMap<Long, String>();
        List<UserInfo> users = report.selectUsers();

        for (UserInfo user : users) {
            if (user.getId() != BotSetting.creatorId) {
                usersMessage.put(user.getId(), String.format(BotMessage.notificationGreeting, user.getUser()).concat(message));
            }
        }

        return usersMessage;

    }
    
}