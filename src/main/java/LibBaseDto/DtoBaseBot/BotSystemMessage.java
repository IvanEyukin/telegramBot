package LibBaseDto.DtoBaseBot;

import java.time.format.DateTimeFormatter;


public class BotSystemMessage {

    public final static String messageScheduler = "%s [Scheduler] Уведомление направлено пользователю %s\n";
    public final static DateTimeFormatter formatter  = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    
}