package Scheduler;

import Database.ReportDatabase;
import LibBaseDto.DtoBaseBot.BotMessage;
import LibBaseDto.DtoBaseBot.BotSetting;
import LibBaseDto.DtoBaseUser.UserInfo;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SchedulerMessage {

    public Map<Long, String> botReminder() {

        ReportDatabase report = new ReportDatabase(); 
        Map<Long, String> usersMessage = new HashMap<Long, String>();
        List<UserInfo> users = report.searchUsers();
        List<UserInfo> userMessage = report.searchUsersLastDataMessage();

        int dayNow = LocalDate.now().getDayOfMonth();

        for (UserInfo user : users) {
            if (user.getId() == BotSetting.creatorId) {
                if (!user.getNotification().equals("disabled")) {
                    if (user.getNotification().equals("active")) {
                        for (UserInfo userDateMessage : userMessage) {
                            if (user.getId().equals(userDateMessage.getId())) {
                                Calendar dateMessage = Calendar.getInstance();
                                dateMessage.setTimeInMillis(userDateMessage.getDateMessage() * 1000L);
                                int dayMessage = dateMessage.get(Calendar.DAY_OF_MONTH);

                                if ((dayNow - dayMessage) != 0) {
                                    usersMessage.put(user.getId(), String.format(BotMessage.schedulerBotReminder, user.getUser()));
                                }
                            }
                        }
                    } else {
                        usersMessage.put(user.getId(), String.format(BotMessage.schedulerBotReminder, user.getUser()));
                    }
                }
            }
        }

        return usersMessage;

    }
    
}