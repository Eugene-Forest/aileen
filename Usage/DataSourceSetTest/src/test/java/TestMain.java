import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

public class TestMain {
    // 写一个 mysql 连接用例
    public static void main2(String[] args) {
        // 数据库连接信息
        String url = "jdbc:mysql://localhost:3306/test_db";
        String user = "test_user";
        String password = "test_password";

        // 加载 MySQL 驱动
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC 驱动未找到");
            e.printStackTrace();
            return;
        }

        // 建立连接
        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT 1")) {

            // 处理结果集
            while (rs.next()) {
                System.out.println("连接成功: " + rs.getInt(1));
            }
        } catch (SQLException e) {
            System.out.println("数据库连接失败");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // 数据库连接信息
        String url = "jdbc:sqlserver://localhost:1433;databaseName=test_db";
        String user = "test_user";
        String password = "test_password";

        // 加载 SQL Server 驱动
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            System.out.println("SQL Server JDBC 驱动未找到");
            e.printStackTrace();
            return;
        }

        // 建立连接
        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT 1")) {

            // 处理结果集
            while (rs.next()) {
                System.out.println("连接成功: " + rs.getInt(1));
            }
        } catch (SQLException e) {
            System.out.println("数据库连接失败");
            e.printStackTrace();
        }
    }

}