package bot.database.sqlite.request;


public class Users {
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