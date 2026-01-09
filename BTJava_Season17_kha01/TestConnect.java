import java.sql.Connection;

public class TestConnect {
    public static void main(String[] args) {
        try (Connection conn = DBConnection.getConnection()) {
            if (conn != null && !conn.isClosed()) {
                System.out.println("✅ Kết nối PostgreSQL thành công!");
            }
        } catch (Exception e) {
            System.out.println("❌ Kết nối thất bại!");
            e.printStackTrace();
        }
    }
}
