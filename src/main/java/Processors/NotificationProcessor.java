package Processors;

import bot.database.ReportDatabase;
import bot.dto.User;
import bot.message.Scheduler;
import bot.setting.Setting;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class NotificationProcessor {

    public Map<Long, String> startNotification(String message) {

        ReportDatabase report = new ReportDatabase(); 
        Map<Long, String> usersMessage = new HashMap<Long, String>();
        List<User> users = report.selectUsers();

        for (User user : users) {
            if (user.getId() != Setting.creatorId) {
                usersMessage.put(user.getId(), String.format(Scheduler.GREETING, user.getUser()).concat(message));
            }
        }
        return usersMessage;
    }
}