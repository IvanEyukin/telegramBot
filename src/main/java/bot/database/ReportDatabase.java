package bot.database;

import LibBaseDto.DtoBaseUser.UserInfo;
import LibBaseDto.DtoReport.BaseReport;
import bot.message.BotMessage;
import bot.setting.Setting;

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

    public boolean searchUser(UserInfo userInfo) {
        boolean result = false;

        try (Connection conn = connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(SQLRequests.User.selectAddConditionsId(userInfo.getId()))) {
                    result = rs.next();
                    conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    public void insertUser(UserInfo userInfo) {
        try (Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(SQLRequests.User.INSERT)) {
                pstmt.setLong(1, userInfo.getId());
                pstmt.setString(2, userInfo.getName());
                pstmt.setString(3, userInfo.getFirstName());
                pstmt.setString(4, userInfo.getLastName());
                pstmt.setString(5, userInfo.getNotification());
                pstmt.executeUpdate();
                conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public UserInfo selectUser(UserInfo userInfo) {        
        try (Connection conn = connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(SQLRequests.User.selectAddConditionsId(userInfo.getId()))) {
                    while (rs.next()) {
                        userInfo.setName(rs.getString("UserName"));
                        userInfo.setFirstName(rs.getString("UserFirstName"));
                        userInfo.setLastName(rs.getString("UserLastName"));
                        userInfo.setNotification(rs.getString("Notification"));
                    }
                    conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return userInfo;
    }

    public void updateUser(UserInfo userInfo) {
        try (Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(SQLRequests.User.UPDATE)) {
                pstmt.setString(1, userInfo.getName());
                pstmt.setString(2, userInfo.getFirstName());
                pstmt.setString(3, userInfo.getLastName());
                pstmt.setString(4, userInfo.getNotification());
                pstmt.setLong(5, userInfo.getId());
                pstmt.executeUpdate();
                conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<UserInfo> selectUsers() {
        List<UserInfo> users = new ArrayList<UserInfo>();

        try (Connection conn = connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(SQLRequests.User.SELECT)) {
                    while (rs.next()) {
                        UserInfo user = new UserInfo();
                        user.setId(rs.getLong("UserId"));
                        user.setName(rs.getString("UserName"));
                        user.setFirstName(rs.getString("UserFirstName"));
                        user.setLastName(rs.getString("UserLastName"));
                        user.setNotification(rs.getString("Notification"));
                        users.add(user);
                    }
                    conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return users;
    }

    public List<UserInfo> selectUsersLastDataMessage() {
        List<UserInfo> users = new ArrayList<UserInfo>();

        try (Connection conn = connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(SQLRequests.Views.UserLastMessage)) {
                    while (rs.next()) {
                        UserInfo user = new UserInfo();
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

    public void insertFinance(BotMessage botMessage, String tableName) {
        try (Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(String.format(SQLRequests.Finance.INSERT, tableName))) {
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

        try (Connection conn = connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(String.format(SQLRequests.Finance.SELECT, report.getTableName(), report.getUserId(), report.getDateFrom(), report.getDateTo()))) {
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