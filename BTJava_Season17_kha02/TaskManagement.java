import java.sql.*;

public class TaskManagement {

    // 1. Thêm công việc
    public void addTask(String taskName, String status) {
        String sql = "SELECT add_task(?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, taskName);
            ps.setString(2, status);
            ps.execute();
            System.out.println("Thêm công việc thành công!");

        } catch (Exception e) {
            System.out.println("Lỗi thêm công việc!");
        }
    }

    // 2. Liệt kê
    public void listTasks() {
        String sql = "SELECT * FROM list_tasks()";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (!rs.isBeforeFirst()) {
                System.out.println("Danh sách công việc trống.");
                return;
            }

            while (rs.next()) {
                System.out.println(
                        rs.getInt("id") + " | " +
                                rs.getString("task_name") + " | " +
                                rs.getString("status"));
            }

        } catch (Exception e) {
            System.out.println("Lỗi hiển thị danh sách!");
            e.printStackTrace(); // 🔥 BẮT BUỘC khi debug
        }
    }

    // 3. Cập nhật
    public void updateTaskStatus(int taskId, String status) {
        String sql = "SELECT update_task_status(?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, taskId);
            ps.setString(2, status);
            ps.execute();
            System.out.println("Cập nhật thành công!");

        } catch (Exception e) {
            System.out.println("Lỗi cập nhật!");
        }
    }

    // 4. Xóa
    public void deleteTask(int taskId) {
        String sql = "SELECT delete_task(?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, taskId);
            ps.execute();
            System.out.println("Xóa thành công!");

        } catch (Exception e) {
            System.out.println("Lỗi xóa!");
        }
    }

    // 5. Tìm kiếm
    public void searchTaskByName(String name) {
        String sql = "SELECT * FROM search_task_by_name(?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                System.out.println(
                        rs.getInt("id") + " | " +
                                rs.getString("task_name") + " | " +
                                rs.getString("status"));
            }

        } catch (Exception e) {
            System.out.println("Lỗi tìm kiếm!");
        }
    }

    // 6. Thống kê
    public void taskStatistics() {
        String sql = "SELECT * FROM task_statistics()";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                System.out.println(
                        rs.getString("status") + ": " +
                                rs.getInt("total"));
            }

        } catch (Exception e) {
            System.out.println("Lỗi thống kê!");
        }
    }
}
