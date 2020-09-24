package Database;
import java.sql.*;

public class DatabaseConnection {
    public static Connection getConnection;
    static boolean isConnected = false;
    static Connection conn;
    public static void connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://wgudb.ucertify.com:3306/U07ow8", "U07ow8", "53689089480");
            System.out.println("Connected...");
            isConnected = true;
        } catch (ClassNotFoundException e) {
            System.out.println("ClassNotFoundException: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("ErrorCode: " + e.getErrorCode());
        }
    }
    public static void disconnect() {
        if (isConnected) {
            try {
                conn.close();
                System.out.println("Disconnected...");
            } catch (SQLException e) {
                System.out.print("Couldn't Disconnect...");
            }
        }
    }
    public static Connection getConnection() {
        return conn;
    }
}
