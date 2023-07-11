package Processors;

import LibBaseDto.DtoBaseUser.UserInfo;
import bot.database.ReportDatabase;
import bot.message.Scheduler;
import bot.setting.Setting;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class NotificationProcessor {

    public Map<Long, String> startNotification(String message) {

        ReportDatabase report = new ReportDatabase(); 
        Map<Long, String> usersMessage = new HashMap<Long, String>();
        List<UserInfo> users = report.selectUsers();

        for (UserInfo user : users) {
            if (user.getId() != Setting.creatorId) {
                usersMessage.put(user.getId(), String.format(Scheduler.GREETING, user.getUser()).concat(message));
            }
        }
        return usersMessage;
    }
}