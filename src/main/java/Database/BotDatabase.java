package Database;

import LibBaseDto.DtoBaseUser.UserInfo;
import LibBaseDto.DtoBaseBot.BotMessage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BotDatabase {

    private Connection connect() {

        String url = "jdbc:sqlite:db/BotDatabase.db";
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return conn;

    }

    public boolean searchUser(UserInfo userInfo) {

        String sql = "SELECT ui.UserId, ui.UserName, ui.UserFirstName, ui.UserLastName FROM UserInfo ui WHERE ui.UserId = "
                .concat(Long.toString(userInfo.getId()));
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

    public void insertUser(UserInfo userInfo) {

        String sql = "INSERT INTO UserInfo (UserId, UserName, UserFirstName, UserLastName) VALUES (?,?,?,?)";
        
        try (Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
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

    public void insertExpenses(BotMessage botMessage) {

        String sql = "INSERT INTO Expenses (Date, UserId, Category, Sum, Comment) VALUES (?,?,?,?,?)";
        
        try (Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setLong(1, botMessage.getMessage().getDate());
                pstmt.setLong(2, botMessage.getMessage().getChat().getId());
                pstmt.setString(3, botMessage.getFinanceSubCategory());
                pstmt.setBigDecimal(4, botMessage.getFinanceSum());
                pstmt.setString(5, botMessage.getComment());
                pstmt.executeUpdate();
                conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public void insertIncome(BotMessage botMessage) {

        String sql = "INSERT INTO Income (Date, UserId, Category, Sum, Comment) VALUES (?,?,?,?,?)";
        
        try (Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setLong(1, botMessage.getMessage().getDate());
                pstmt.setLong(2, botMessage.getMessage().getChat().getId());
                pstmt.setString(3, botMessage.getFinanceSubCategory());
                pstmt.setBigDecimal(4, botMessage.getFinanceSum());
                pstmt.setString(5, botMessage.getComment());
                pstmt.executeUpdate();
                conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
    
}
