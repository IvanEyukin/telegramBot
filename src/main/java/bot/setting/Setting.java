package bot.setting;

import Utils.Parser;


public class Setting {
    public final static String name = System.getenv("name");
    public final static int creatorId = Integer.parseInt(System.getenv("creatorId"));
    public final static String token = System.getenv("token");
    public final static String dbReportPath = System.getenv("dbReportPath");
    public final static String dbSessionHost = System.getenv("dbSessionHost");
    public final static int dbSessionPort = Integer.parseInt(System.getenv("dbSessionPort"));
    public final static int sessionTimeToLive = Integer.parseInt(System.getenv("sessionTimeToLive"));
    public final static String schedulerTime = System.getenv("schedulerTime");

    public final static class schedulerTimeStart {
        public final static int hour = Parser.parseTimetoInteger(schedulerTime, 0);
        public final static int minut = Parser.parseTimetoInteger(schedulerTime, 1);
        public final static int second = Parser.parseTimetoInteger(schedulerTime, 2);
    }
}