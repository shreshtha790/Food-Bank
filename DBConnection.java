import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    public static Connection getConnection() {
        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            String url = "jdbc:mysql://localhost:3306/foodbank?useSSL=false&serverTimezone=UTC";
            String user = "root";
            String password = "Root@100";

            con = DriverManager.getConnection(url, user, password);

            System.out.println("Connected Successfully!");

        } catch (Exception e) {
            System.out.println("Connection Error: " + e);
        }
        return con;
    }
}