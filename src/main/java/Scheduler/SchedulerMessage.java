package Scheduler;

import LibBaseDto.DtoBaseUser.UserInfo;
import bot.database.ReportDatabase;
import bot.message.Scheduler;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SchedulerMessage {

    public Map<Long, String> botReminder() {

        ReportDatabase report = new ReportDatabase(); 
        Map<Long, String> usersMessage = new HashMap<Long, String>();
        List<UserInfo> users = report.selectUsers();
        List<UserInfo> userMessage = report.selectUsersLastDataMessage();

        int dayNow = LocalDate.now().getDayOfMonth();

        for (UserInfo user : users) {
            if (!user.getNotification().equals("disabled")) {
                if (user.getNotification().equals("active")) {
                    for (UserInfo userDateMessage : userMessage) {
                        if (user.getId().equals(userDateMessage.getId())) {
                            Calendar dateMessage = Calendar.getInstance();
                            dateMessage.setTimeInMillis(userDateMessage.getDateMessage() * 1000L);
                            int dayMessage = dateMessage.get(Calendar.DAY_OF_MONTH);

                            if ((dayNow - dayMessage) != 0) {
                                usersMessage.put(user.getId(), String.format(Scheduler.REMINDER, user.getUser()));
                            }
                        }
                    }
                } else {
                    usersMessage.put(user.getId(), String.format(Scheduler.REMINDER, user.getUser()));
                }
            }
        }
        return usersMessage;
    }
}