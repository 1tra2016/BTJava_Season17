import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        MovieManagement manager = new MovieManagement();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== QUẢN LÝ PHIM ===");
            System.out.println("1. Thêm phim");
            System.out.println("2. Liệt kê phim");
            System.out.println("3. Sửa phim");
            System.out.println("4. Xóa phim");
            System.out.println("0. Thoát");
            System.out.print("Chọn: ");

            try {
                int choice = Integer.parseInt(sc.nextLine());

                switch (choice) {
                    case 1:
                        System.out.print("Tiêu đề: ");
                        String title = sc.nextLine();
                        System.out.print("Đạo diễn: ");
                        String director = sc.nextLine();
                        System.out.print("Năm phát hành: ");
                        int year = Integer.parseInt(sc.nextLine());

                        if (title.isEmpty() || director.isEmpty()) {
                            throw new IllegalArgumentException("Không được để trống!");
                        }

                        manager.addMovie(title, director, year);
                        break;

                    case 2:
                        manager.listMovies();
                        break;

                    case 3:
                        System.out.print("ID phim: ");
                        int id = Integer.parseInt(sc.nextLine());
                        System.out.print("Tiêu đề mới: ");
                        title = sc.nextLine();
                        System.out.print("Đạo diễn mới: ");
                        director = sc.nextLine();
                        System.out.print("Năm mới: ");
                        year = Integer.parseInt(sc.nextLine());

                        manager.updateMovie(id, title, director, year);
                        break;

                    case 4:
                        System.out.print("ID phim cần xóa: ");
                        id = Integer.parseInt(sc.nextLine());
                        manager.deleteMovie(id);
                        break;

                    case 0:
                        System.out.println("Thoát");
                        System.exit(0);

                    default:
                        System.out.println("Lựa chọn không tồn tại");
                }

            } catch (NumberFormatException e) {
                System.out.println("Sai kiểu dữ liệu (phải là số)");
            } catch (Exception e) {
                System.out.println("Lỗi: " + e.getMessage());
            }
        }
    }
}
