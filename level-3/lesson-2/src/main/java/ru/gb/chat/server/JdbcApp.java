package ru.gb.chat.server;

import java.sql.*;
import java.text.SimpleDateFormat;

public class JdbcApp {
    private static Connection connection;
    private static Statement stmt;

    public static void main(String[] args) throws ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        try {
            connect();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }
    }

    public static void connect() throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite:chart.db");
        stmt = connection.createStatement();
    }

    public static void disconnect() {
        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addMessage(String username, String message) throws SQLException {
        String queryAdd = "INSERT INTO Messages_log (user_name, action_date, Message, Error) VALUES (?, ?, ?, NULL);";
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            PreparedStatement ps = connection.prepareStatement(queryAdd);
            ps.setString(1, username);
            ps.setString(2, dateFormat.format(timestamp));
            ps.setString(3, message);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void setActive(String username) throws SQLException {
        String queryAdd = "INSERT INTO Users (user_name, is_active, action_date) VALUES (?, ?, ?);";
        String logAdd = "INSERT INTO Messages_log (user_name, action_date, Message) VALUES (?, ?, 'Entered in the chat');";
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            PreparedStatement ps1 = connection.prepareStatement(queryAdd);
            ps1.setString(1, username);
            ps1.setBoolean(2, true);
            ps1.setString(3, dateFormat.format(timestamp));
            ps1.execute();

            PreparedStatement ps2 = connection.prepareStatement(logAdd);
            ps2.setString(1, username);
            ps2.setString(2, dateFormat.format(timestamp));
            ps2.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void setInactive(String username) throws SQLException {
        String queryDel = "UPDATE Users SET is_active=false, TD=? WHERE user_name = ?";
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            PreparedStatement ps1 = connection.prepareStatement(queryDel);
            ps1.setString(1, dateFormat.format(timestamp));
            ps1.setString(2, String.valueOf(username));
            ps1.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int isFree(String username) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("SELECT count(*) cnt FROM USERS WHERE user_name = ?");
        ps.setString(1, username);
        ResultSet rs = ps.executeQuery();
        return rs.getInt("cnt");
    }
}