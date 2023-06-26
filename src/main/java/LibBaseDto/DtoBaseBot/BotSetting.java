package LibBaseDto.DtoBaseBot;

public class BotSetting {

    public final static String name = System.getenv("name");
    public final static int creatorId = Integer.parseInt(System.getenv("creatorId"));
    public final static String token = System.getenv("token");
    public final static String dbReportPath = System.getenv("dbReportPath");
    public final static String dbSessionHost = System.getenv("dbSessionHost");
    public final static int dbSessionPort = Integer.parseInt(System.getenv("dbSessionPort"));
    public final static int sessionTimeToLive = Integer.parseInt(System.getenv("sessionTimeToLive"));
   
}