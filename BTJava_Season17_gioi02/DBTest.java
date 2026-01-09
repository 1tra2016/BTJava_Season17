import java.sql.*;

public class DBTest {
    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;

        try {
            // 1️⃣ Kết nối DB
            conn = DBConnection.getConnection();
            stmt = conn.createStatement();

            // --- Kiểm tra database hiện tại ---
            try (ResultSet rs = stmt.executeQuery("SELECT current_database()")) {
                if (rs.next()) {
                    System.out.println("Connected database: " + rs.getString(1));
                }
            }

            // --- Kiểm tra schema hiện tại ---
            try (ResultSet rs = stmt.executeQuery("SELECT current_schema()")) {
                if (rs.next()) {
                    System.out.println("Current schema: " + rs.getString(1));
                }
            }

            // --- Liệt kê tất cả bảng trong schema public ---
            try (ResultSet rs = stmt.executeQuery(
                    "SELECT table_schema, table_name " +
                            "FROM information_schema.tables " +
                            "WHERE table_schema='public' AND table_type='BASE TABLE'")) {
                System.out.println("Tables in public schema:");
                while (rs.next()) {
                    System.out.println(rs.getString("table_schema") + "." + rs.getString("table_name"));
                }
            }

            // --- Kiểm tra các PROCEDURE cần thiết ---
            String[] procedures = {
                    "add_product",
                    "update_customer",
                    "create_order",
                    "list_all_orders",
                    "get_orders_by_customer"
            };

            for (String proc : procedures) {
                String sql = "SELECT routine_name FROM information_schema.routines " +
                        "WHERE routine_schema='public' AND routine_name = '" + proc + "'";
                try (ResultSet rs = stmt.executeQuery(sql)) {
                    if (rs.next()) {
                        System.out.println("Procedure found: " + rs.getString("routine_name"));
                    } else {
                        System.out.println("Procedure NOT found: " + proc);
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("Lỗi kết nối hoặc truy vấn DB: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Đóng tài nguyên
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
