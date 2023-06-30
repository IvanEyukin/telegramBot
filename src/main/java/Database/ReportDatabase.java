package Database;

import LibBaseDto.DtoBaseUser.UserInfo;
import LibBaseDto.DtoBaseBot.BotMessage;
import LibBaseDto.DtoBaseBot.BotSetting;
import LibBaseDto.DtoReport.BaseReport;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.math.RoundingMode;


public class ReportDatabase {

    public final String tableIncome = "Income";
    public final String tableExpenses = "Expenses";
    public final Map<String, String> tableMap = Map.of("Доходы", tableIncome, "Расходы", tableExpenses);
    private final String dbPath = "jdbc:sqlite:";
    private final String sqlSelectSearchUser  = "SELECT ui.UserId, ui.UserName, ui.UserFirstName, ui.UserLastName FROM UserInfo ui WHERE ui.UserId = ";
    private final String sqlSelectSearchUsers  = "SELECT ui.UserId, ui.UserName, ui.UserFirstName, ui.UserLastName FROM UserInfo ui";
    private final String sqlSelectBudget = "SELECT b.Budget FROM Budget b WHERE b.UserId = ";
    private final String sqlIncertUserInfo = "INSERT INTO UserInfo (UserId, UserName, UserFirstName, UserLastName) VALUES (?,?,?,?)";
    private final String sqlIncertFinance = "INSERT INTO %s (Date, UserId, Category, Sum, Comment) VALUES (?,?,?,?,?)";
    private final String sqlSelectReport = """
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

    private Connection connect() {
        
        String url = dbPath.concat(BotSetting.dbReportPath);
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return conn;

    }

    public boolean searchUser(UserInfo userInfo) {

        String sql = sqlSelectSearchUser.concat(Long.toString(userInfo.getId()));
        boolean result = false;

        try (Connection conn = connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
                    result = rs.next();
                    conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return result;

    }

    public List<UserInfo> searchUsers() {

        List<UserInfo> users = new ArrayList<UserInfo>();

        try (Connection conn = connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sqlSelectSearchUsers)) {
                    while (rs.next()) {
                        UserInfo user = new UserInfo();
                        user.setId(rs.getLong("UserId"));
                        user.setName(rs.getString("UserName"));
                        user.setFirstName(rs.getString("UserFirstName"));
                        user.setLastName(rs.getString("UserLastName"));
                        users.add(user);
                    }
                    conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return users;

    }

    public void insertUser(UserInfo userInfo) {

        try (Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(sqlIncertUserInfo)) {
                pstmt.setLong(1, userInfo.getId());
                pstmt.setString(2, userInfo.getName());
                pstmt.setString(3, userInfo.getFirstName());
                pstmt.setString(4, userInfo.getLastName());
                pstmt.executeUpdate();
                conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public void insertFinance(BotMessage botMessage, String tableName) {

        String sql = String.format(sqlIncertFinance, tableName);

        try (Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setLong(1, botMessage.getUserInfo().getDateMessage());
                pstmt.setLong(2, botMessage.getUserInfo().getId());
                pstmt.setString(3, botMessage.getFinanceSubCategory());
                pstmt.setBigDecimal(4, botMessage.getFinanceSum());
                pstmt.setString(5, botMessage.getComment());
                pstmt.executeUpdate();
                conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public BaseReport selectFinance(BaseReport report) {

        List<BaseReport> result = new ArrayList<BaseReport>();
        String sql = String.format(sqlSelectReport, report.getTableName(), report.getUserId(), report.getDateFrom(), report.getDateTo());

        try (Connection conn = connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
                    while (rs.next()) {
                        BaseReport reportResult = new BaseReport();
                        reportResult.setUserId(rs.getLong("UserId"));
                        reportResult.setCategory(rs.getString("Category"));
                        reportResult.setSum(rs.getBigDecimal("Sum").setScale(2, RoundingMode.HALF_DOWN));
                        result.add(reportResult);
                    }
                    conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        report.setBaseReportsList(result);

        return report;

    }

    public BaseReport selectBudget(BaseReport report) {

        String sql = sqlSelectBudget.concat(report.getUserId().toString());

        try (Connection conn = connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
                    while (rs.next()) {
                        report.setSum(rs.getBigDecimal("Budget").setScale(2, RoundingMode.HALF_DOWN));
                    }
                    conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return report;

    }
   
}