package bot.database.sqlite.request;


public class Views {
    public final static String Budget = """
            SELECT 
                b.Budget 
            FROM 
                Budget b
            """;
    public final static String UserLastMessage = """
            SELECT 
                u.UserId, 
                u.LastDateMessage 
            FROM 
                UserLastMessage u
            """;
    public final static String UserLastMessageAdminReport = """
            SELECT
                TRIM (
                    CASE WHEN u.UserName = 'null' THEN '' ELSE u.UserName END || ' ' ||
                    CASE WHEN u.UserFirstName = 'null' THEN '' ELSE u.UserFirstName END || ' ' ||
                    CASE WHEN u.UserLastName = 'null' THEN '' ELSE u.UserLastName END 
                ) AS User,
                datetime(ulm.LastDateMessage, 'unixepoch') AS Date  
            FROM 
                UserLastMessage ulm
                JOIN User u ON u.UserId = ulm.UserId
            ORDER BY
                Date DESC
            """;
}