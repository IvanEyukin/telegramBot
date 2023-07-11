package bot.database;


public class SQLRequests {
    public class User {
        public final static String SELECT = """
                SELECT
                    u.Id, 
                    u.Name, 
                    u.FirstName, 
                    u.LastName, 
                    u.Notification 
                FROM 
                    User u
                """;
        public final static String INSERT = """
                INSERT INTO User
                    (Id, Name, FirstName, LastName, Notification)
                VALUES 
                    (?,?,?,?,?)
                """;
        public final static String UPDATE = """
                UPDATE
                    User
                SET
                    Name = ?,
                    FirstName = ?,
                    LastName = ?,
                    Notification = ?
                WHERE
                    Id = ? 
                """;

        public static String selectAddConditionsId(Long id) {
            String Condition = """
                    WHERE
                        Id = %s
                    """;
            return SELECT.concat(String.format(Condition, Long.toString(id)));
        }
    }

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

    public class Finance {
        public final static String INSERT = """
                INSERT INTO  %s 
                    (Date, UserId, Category, Sum, Comment)
                VALUES 
                    (?,?,?,?,?)     
                """;
        public final static String SELECT = """
                SELECT 
                    UserId   AS UserId,
                    Category AS Category,
                    SUM(Sum) AS Sum
                FROM 
                    %s
                WHERE
                    UserId = %s
                    AND Date >= strftime('%%s','%s')
                    AND Date < strftime('%%s','%s')
                GROUP BY 
                    UserId,
                    Category
                """;
    }
}