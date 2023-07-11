package bot.log;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class LogMessage {
    private final static DateTimeFormatter FROMATTER  = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final static String MESSAGE_FROMAT = "%s %s %s \n";
    
    public class Service {
        public final static String SCHEDULER = "[Scheduler]";
        public final static String NOTIFICATION = "[AdminNotification]";
    }

    public class Message {
        public final static String DISTRIBUTION = "Уведомление направлено пользователю ";
    }

    public static void outLogMessage(String service, String message) {
        System.out.println(String.format(MESSAGE_FROMAT, LocalDateTime.now().format(FROMATTER), service, message));
    }
}