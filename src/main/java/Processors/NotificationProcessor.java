package Processors;

import Database.ReportDatabase;
import LibBaseDto.DtoBaseUser.UserInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class NotificationProcessor {

    public Map<Long, String> startNotification(String message) {

        ReportDatabase report = new ReportDatabase(); 
        Map<Long, String> usersMessage = new HashMap<Long, String>();
        List<UserInfo> users = report.searchUsers();

        for (UserInfo user : users) {
            usersMessage.put(user.getId(), message);
        }

        return usersMessage;

    }
    
}