package bot.database.sqlite.request;


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