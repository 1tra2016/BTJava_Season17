import entity.Customer;
import entity.Order;
import entity.Product;

import java.sql.Date;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        OrderManager manager = new OrderManager();

        while (true) {
            System.out.println("\n===== MENU =====\n"
                + "1. Thêm sản phẩm\n"
                + "2. Cập nhật khách hàng\n"
                + "3. Tạo đơn hàng\n"
                + "4. Danh sách đơn hàng\n"
                + "5. Tìm đơn theo khách hàng\n"
                + "0. Thoát");

            try {
                System.out.print("Chọn: ");
                int choice = Integer.parseInt(sc.nextLine());

                switch (choice) {
                    case 1:
                        System.out.print("Tên SP: ");
                        String name = sc.nextLine();
                        System.out.print("Giá: ");
                        double price = Double.parseDouble(sc.nextLine());
                        manager.addProduct(new Product(name, price));
                        break;

                    case 2:
                        System.out.print("ID KH: ");
                        int cid = Integer.parseInt(sc.nextLine());
                        System.out.print("Tên mới: ");
                        String cname = sc.nextLine();
                        System.out.print("Email mới: ");
                        String email = sc.nextLine();
                        manager.updateCustomer(cid, new Customer(cname, email));
                        break;

                    case 3:
                        System.out.print("ID KH: ");
                        int customerId = Integer.parseInt(sc.nextLine());
                        System.out.print("Tổng tiền: ");
                        double total = Double.parseDouble(sc.nextLine());
                        manager.createOrder(
                                new Order(customerId, new Date(System.currentTimeMillis()), total)
                        );
                        break;

                    case 4:
                        manager.listAllOrders();
                        break;

                    case 5:
                        System.out.print("ID KH: ");
                        int searchId = Integer.parseInt(sc.nextLine());
                        manager.getOrdersByCustomer(searchId);
                        break;

                    case 0:
                        System.exit(0);

                    default:
                        System.out.println("❌ Lựa chọn không hợp lệ");
                }

            } catch (Exception e) {
                System.out.println("❌ Lỗi nhập liệu!");
            }
        }
    }
}
