package Scheduler;

import Database.ReportDatabase;
import LibBaseDto.DtoBaseBot.BotMessage;
import LibBaseDto.DtoBaseUser.UserInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SchedulerMessage {

    public Map<Long, String> botReminder() {

        ReportDatabase report = new ReportDatabase(); 
        Map<Long, String> usersMessage = new HashMap<Long, String>();
        List<UserInfo> users = report.searchUsers();

        for (UserInfo user : users) {
            usersMessage.put(user.getId(), String.format(BotMessage.schedulerBotReminder, user.getUser()));
        }

        return usersMessage;

    }
    
}