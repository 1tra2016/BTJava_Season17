
import entity.Customer;
import entity.Order;
import entity.Product;

import java.sql.*;

public class OrderManager {

    public void addProduct(Product product) {
        String sql = "call add_product(?, ?)";

        try (Connection conn = DBConnection.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setString(1, product.getName());
            cs.setDouble(2, product.getPrice());
            cs.execute();

            System.out.println("Thêm sản phẩm thành công");

        } catch (Exception e) {
            System.out.println("Lỗi thêm sản phẩm: " + e.getMessage());
        }
    }


    public void updateCustomer(int id, Customer customer) {
        String sql = "call update_customer(?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setInt(1, id);
            cs.setString(2, customer.getName());
            cs.setString(3, customer.getEmail());
            cs.execute();

            System.out.println("Cập nhật khách hàng thành công");

        } catch (Exception e) {
            System.out.println("Lỗi cập nhật khách hàng: " + e.getMessage());
        }
    }

    public void createOrder(Order order) {
        String sql = "call create_order(?, ?)";

        try (Connection conn = DBConnection.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setInt(1, order.getCustomerId());
            cs.setDouble(2, order.getTotalAmount());
            cs.execute();

            System.out.println("Tạo đơn hàng thành công");

        } catch (Exception e) {
            System.out.println("Lỗi tạo đơn hàng: " + e.getMessage());
        }
    }

    public void listAllOrders() {
        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false); // rất quan trọng với REF_CURSOR

            try (CallableStatement cs = conn.prepareCall("call list_all_orders(?)")) {
                cs.registerOutParameter(1, Types.OTHER);
                cs.execute();

                try (ResultSet rs = (ResultSet) cs.getObject(1)) {
                    System.out.println("ID | Customer | Date | Total");
                    while (rs.next()) {
                        System.out.printf("%d | %s | %s | %.2f\n",
                                rs.getInt("order_id"),
                                rs.getString("customer_name"),
                                rs.getDate("order_date"),
                                rs.getDouble("total_amount"));
                    }
                }
            }

            conn.commit(); // commit sau khi dùng cursor
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void getOrdersByCustomer(int customerId) {
        String sql = "call get_orders_by_customer(?, ?)";

        try (Connection conn = DBConnection.getConnection()) {

            conn.setAutoCommit(false);

            try (CallableStatement cs = conn.prepareCall(sql)) {

                cs.setInt(1, customerId);
                cs.registerOutParameter(2, Types.OTHER);
                cs.execute();

                try (ResultSet rs = (ResultSet) cs.getObject(2)) {
                    System.out.println("ID | Date | Total");
                    while (rs.next()) {
                        System.out.printf("%d | %s | %.2f\n",
                                rs.getInt("order_id"),
                                rs.getDate("order_date"),
                                rs.getDouble("total_amount"));
                    }
                }
            }

            conn.commit();

        } catch (Exception e) {
            System.out.println("Lỗi tìm đơn hàng: " + e.getMessage());
        }
    }

}