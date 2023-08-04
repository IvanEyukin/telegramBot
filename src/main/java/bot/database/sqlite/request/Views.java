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
                    CASE WHEN u.Name IS null THEN '' ELSE u.Name END || ' ' ||
                    CASE WHEN u.FirstName IS null THEN '' ELSE u.FirstName END || ' ' ||
                    CASE WHEN u.LastName IS null THEN '' ELSE u.LastName END 
                ) AS User,
                ulm.LastDateMessage AS Date  
            FROM 
                UserLastMessage ulm
                JOIN User u ON u.Id = ulm.UserId
            ORDER BY
                Date DESC
            """;
}