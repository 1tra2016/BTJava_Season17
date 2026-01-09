import java.sql.*;

public class BookManager {

    public void addBook(Book book) {
        String checkSql = """
        SELECT 1 FROM books WHERE title = ? AND author = ?
    """;

        String insertSql = """
        INSERT INTO books(title, author, published_year, price)
        VALUES (?, ?, ?, ?)
    """;

        try (Connection conn = DBConnection.getConnection()) {

            PreparedStatement check = conn.prepareStatement(checkSql);
            check.setString(1, book.getTitle());
            check.setString(2, book.getAuthor());

            if (check.executeQuery().next()) {
                System.out.println("Sách đã tồn tại!");
                return;
            }

            PreparedStatement ps = conn.prepareStatement(insertSql);
            ps.setString(1, book.getTitle());
            ps.setString(2, book.getAuthor());
            ps.setInt(3, book.getPublishedYear());
            ps.setDouble(4, book.getPrice());

            ps.executeUpdate();
            System.out.println("Thêm sách thành công");

        } catch (Exception e) {
            System.out.println("Lỗi thêm sách: " + e.getMessage());
        }
    }


    public void updateBook(int id, Book book) {
        String sql = """
        UPDATE books
        SET title=?, author=?, published_year=?, price=?
        WHERE id=?
    """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, book.getTitle());
            ps.setString(2, book.getAuthor());
            ps.setInt(3, book.getPublishedYear());
            ps.setDouble(4, book.getPrice());
            ps.setInt(5, id);

            if (ps.executeUpdate() == 0) {
                System.out.println("Không tìm thấy sách");
            } else {
                System.out.println("Cập nhật thành công");
            }

        } catch (Exception e) {
            System.out.println("Lỗi cập nhật: " + e.getMessage());
        }
    }


    public void deleteBook(int id) {
        String sql = "DELETE FROM books WHERE id=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            if (ps.executeUpdate() == 0) {
                System.out.println("Không tìm thấy sách");
            } else {
                System.out.println("Đã xóa sách");
            }

        } catch (Exception e) {
            System.out.println("Lỗi xóa: " + e.getMessage());
        }
    }


    public void findBooksByAuthor(String author) {
        String sql = "SELECT * FROM books WHERE author ILIKE ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + author + "%");
            ResultSet rs = ps.executeQuery();

            boolean found = false;
            while (rs.next()) {
                found = true;
                System.out.println(
                        rs.getInt("id") + " | " +
                                rs.getString("title") + " | " +
                                rs.getString("author") + " | " +
                                rs.getInt("published_year") + " | " +
                                rs.getDouble("price")
                );
            }

            if (!found) {
                System.out.println("Không có sách của tác giả này");
            }

        } catch (Exception e) {
            System.out.println("Lỗi tìm kiếm: " + e.getMessage());
        }
    }
    public void listAllBooks() {
        String sql = "SELECT * FROM books ORDER BY id";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                System.out.println(
                        rs.getInt("id") + " | " +
                        rs.getString("title") + " | " +
                        rs.getString("author") + " | " +
                        rs.getInt("published_year") + " | " +
                        rs.getDouble("price")
                );
            }

        } catch (Exception e) {
            System.out.println("Lỗi hiển thị: " + e.getMessage());
        }
    }

}