import java.sql.*;

public class MovieManagement {

    public void addMovie(String title, String director, int year) {
        String sql = "CALL add_movie(?, ?, ?) ";

        try (Connection conn = DBConnection.getConnection();

             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setString(1, title);
            cs.setString(2, director);
            cs.setInt(3, year);

            cs.execute();
            System.out.println("Thêm phim thành công");

    } catch (Exception e) {
            System.out.println("Lỗi thêm phim: " + e.getMessage());
        }
    }

    public void listMovies() {
        String sql = "CALL list_movies(?)";

        try (Connection conn = DBConnection.getConnection()) {

            conn.setAutoCommit(false);

            CallableStatement cs = conn.prepareCall(sql);

            cs.registerOutParameter(1, Types.REF_CURSOR);

            cs.execute();
            ResultSet rs = (ResultSet) cs.getObject(1);

            while (rs.next()) {
                System.out.println(
                        rs.getInt("id") + " | " +
                                rs.getString("title") + " | " +
                                rs.getString("director") + " | " +
                                rs.getInt("year")
                );
            }

            conn.commit();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void updateMovie(int id, String title, String director, int year) {
        String sql = "CALL update_movie(?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setInt(1, id);
            cs.setString(2, title);
            cs.setString(3, director);
            cs.setInt(4, year);
            cs.execute();
            System.out.println("Cập nhật thành công");

        } catch (Exception e) {
            System.out.println("Lỗi cập nhật: " + e.getMessage());
        }
    }

    public void deleteMovie(int id) {
        String sql = "CALL delete_movie(?)";
        try (Connection conn = DBConnection.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setInt(1, id);
            cs.execute();
            System.out.println("Xóa phim thành công");

        } catch (Exception e) {
            System.out.println("Lỗi xóa phim: " + e.getMessage());
        }
    }
}
