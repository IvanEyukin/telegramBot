package bot.log;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class LogMessage {
    private final static DateTimeFormatter FROMATTER  = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final static String MESSAGE_FROMAT = "%s %s %s \n";

    public static void outLogMessage(String service, String message) {
        System.out.println(String.format(MESSAGE_FROMAT, LocalDateTime.now().format(FROMATTER), service, message));
    }
}