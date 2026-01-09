import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        TaskManagement tm = new TaskManagement();

        while (true) {
            System.out.println("\n===== TODO LIST =====");
            System.out.println("1. Thêm công việc");
            System.out.println("2. Liệt kê công việc");
            System.out.println("3. Cập nhật trạng thái");
            System.out.println("4. Xóa công việc");
            System.out.println("5. Tìm kiếm công việc");
            System.out.println("6. Thống kê");
            System.out.println("0. Thoát");
            System.out.print("Chọn: ");

            try {
                int choice = Integer.parseInt(sc.nextLine());

                switch (choice) {
                    case 1 -> {
                        System.out.print("Tên công việc: ");
                        String name = sc.nextLine();
                        if (name.isBlank()) throw new Exception();

                        System.out.print("Trạng thái: ");
                        String status = sc.nextLine();
                        tm.addTask(name, status);
                    }
                    case 2 -> tm.listTasks();
                    case 3 -> {
                        System.out.print("ID: ");
                        int id = Integer.parseInt(sc.nextLine());
                        System.out.print("Trạng thái mới: ");
                        String status = sc.nextLine();
                        tm.updateTaskStatus(id, status);
                    }
                    case 4 -> {
                        System.out.print("ID cần xóa: ");
                        int id = Integer.parseInt(sc.nextLine());
                        tm.deleteTask(id);
                    }
                    case 5 -> {
                        System.out.print("Tên cần tìm: ");
                        String name = sc.nextLine();
                        tm.searchTaskByName(name);
                    }
                    case 6 -> tm.taskStatistics();
                    case 0 -> {
                        System.out.println("Thoát!");
                        return;
                    }
                    default -> System.out.println("Chọn sai!");
                }

            } catch (NumberFormatException e) {
                System.out.println("Sai kiểu dữ liệu!");
            } catch (Exception e) {
                System.out.println("Dữ liệu không hợp lệ!");
            }
        }
    }
}
