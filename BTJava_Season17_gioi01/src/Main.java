import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        BookManager manager = new BookManager();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("""
                1. Thêm sách
                2. Cập nhật sách
                3. Xóa sách
                4. Tìm theo tác giả
                5. Hiển thị tất cả
                0. Thoát
            """);

            try {
                System.out.print("Chọn: ");
                int choice = Integer.parseInt(sc.nextLine());

                switch (choice) {
                    case 1 -> {
                        System.out.print("Title: ");
                        String title = sc.nextLine();
                        System.out.print("Author: ");
                        String author = sc.nextLine();
                        System.out.print("Year: ");
                        int year = Integer.parseInt(sc.nextLine());
                        System.out.print("Price: ");
                        double price = Double.parseDouble(sc.nextLine());

                        manager.addBook(
                                new Book(title, author, year, price)
                        );
                    }
                    case 2 -> {
                        System.out.print("ID: ");
                        int id = Integer.parseInt(sc.nextLine());
                        System.out.print("Title: ");
                        String title = sc.nextLine();
                        System.out.print("Author: ");
                        String author = sc.nextLine();
                        System.out.print("Year: ");
                        int year = Integer.parseInt(sc.nextLine());
                        System.out.print("Price: ");
                        double price = Double.parseDouble(sc.nextLine());

                        manager.updateBook(
                                id, new Book(title, author, year, price)
                        );
                    }
                    case 3 -> {
                        System.out.print("ID: ");
                        manager.deleteBook(Integer.parseInt(sc.nextLine()));
                    }
                    case 4 -> {
                        System.out.print("Author: ");
                        manager.findBooksByAuthor(sc.nextLine());
                    }
                    case 5 -> manager.listAllBooks();
                    case 0 -> {
                        System.out.println("Thoát chương trình");
                        return;
                    }
                }
            } catch (Exception e) {
                System.out.println("Dữ liệu nhập không hợp lệ");
            }
        }
    }
}
