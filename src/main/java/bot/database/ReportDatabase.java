package bot.database;

import bot.setting.Setting;
import bot.database.sqlite.dto.BaseReport;
import bot.database.sqlite.request.Finance;
import bot.database.sqlite.request.Users;
import bot.database.sqlite.request.Views;
import bot.entitie.Bot;
import bot.entitie.User;

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

    private Connection connect() {
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(dbPath.concat(Setting.dbReportPath));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public void insertUser(User user) {
        try (Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(Users.INSERT)) {
                pstmt.setLong(1, user.getId());
                pstmt.setString(2, user.getName());
                pstmt.setString(3, user.getFirstName());
                pstmt.setString(4, user.getLastName());
                pstmt.setString(5, user.getNotification());
                pstmt.executeUpdate();
                conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public User selectUser(User user) {  
        User userDb = new User();      
        try (Connection conn = connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(Users.selectAddConditionsId(user.getId()))) {
                    while (rs.next()) {
                        userDb.setId(rs.getLong("Id"));
                        userDb.setName(rs.getString("Name"));
                        userDb.setFirstName(rs.getString("FirstName"));
                        userDb.setLastName(rs.getString("LastName"));
                        userDb.setNotification(rs.getString("Notification"));
                        userDb.setIsInDatabase(true);
                    }
                    conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return userDb;
    }

    public void updateUser(User user) {
        try (Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(Users.UPDATE)) {
                pstmt.setString(1, user.getName());
                pstmt.setString(2, user.getFirstName());
                pstmt.setString(3, user.getLastName());
                pstmt.setString(4, user.getNotification());
                pstmt.setLong(5, user.getId());
                pstmt.executeUpdate();
                conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<User> selectUsers() {
        List<User> users = new ArrayList<User>();

        try (Connection conn = connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(Users.SELECT)) {
                    while (rs.next()) {
                        User user = new User();
                        user.setId(rs.getLong("Id"));
                        user.setName(rs.getString("Name"));
                        user.setFirstName(rs.getString("FirstName"));
                        user.setLastName(rs.getString("LastName"));
                        user.setNotification(rs.getString("Notification"));
                        users.add(user);
                    }
                    conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return users;
    }

    public List<User> selectUsersLastDataMessage() {
        List<User> users = new ArrayList<User>();

        try (Connection conn = connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(Views.UserLastMessage)) {
                    while (rs.next()) {
                        User user = new User();
                        user.setId(rs.getLong("UserId"));
                        user.setDateMessage(rs.getInt("LastDateMessage"));
                        users.add(user);
                    }
                    conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return users;
    }

    public void insertFinance(Bot bot, String tableName) {
        try (Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(String.format(Finance.INSERT, tableName))) {
                pstmt.setLong(1, bot.getUser().getDateMessage());
                pstmt.setLong(2, bot.getUser().getId());
                pstmt.setString(3, bot.getSubCategory());
                pstmt.setBigDecimal(4, bot.getSum());
                pstmt.setString(5, bot.getComment());
                pstmt.executeUpdate();
                conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public BaseReport selectFinance(BaseReport report) {
        List<BaseReport> result = new ArrayList<BaseReport>();

        try (Connection conn = connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(String.format(Finance.SELECT, report.getTableName(), report.getUserId(), report.getDateFrom(), report.getDateTo()))) {
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

    // public BaseReport selectBudget(BaseReport report) {

    //     String sql = sqlSelectBudget.concat(report.getUserId().toString());

    //     try (Connection conn = connect();
    //             Statement stmt = conn.createStatement();
    //             ResultSet rs = stmt.executeQuery(sql)) {
    //                 while (rs.next()) {
    //                     report.setSum(rs.getBigDecimal("Budget").setScale(2, RoundingMode.HALF_DOWN));
    //                 }
    //                 conn.close();
    //     } catch (SQLException e) {
    //         System.out.println(e.getMessage());
    //     }

    //     return report;

    // }
}