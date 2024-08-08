package services;

import models.Order;
import utils.FileHandler;

import java.util.ArrayList;
import java.util.List;

public class OrderManagementService {

    private static final String ORDER_FILE = "resources/orders.txt";

    public static void placeOrder(Order order) {
        String newOrderLine = order.getId() + "," + order.getUserId() + "," + order.getProductId() + "," + order.getQuantity() + "," + order.getStatus();
        List<String> orders = FileHandler.readFile(ORDER_FILE);
        orders.add(newOrderLine);
        FileHandler.writeFile(ORDER_FILE, orders);
    }

    public static List<Order> getOrdersByUserId(int userId) {
        List<String> ordersData = FileHandler.readFile(ORDER_FILE);
        List<Order> orders = new ArrayList<>();
        for (String orderLine : ordersData) {
            String[] orderInfo = orderLine.split(",");
            if (Integer.parseInt(orderInfo[1]) == userId) {
                Order order = new Order(Integer.parseInt(orderInfo[0]), Integer.parseInt(orderInfo[1]), Integer.parseInt(orderInfo[2]), Integer.parseInt(orderInfo[3]), orderInfo[4]);
                orders.add(order);
            }
        }
        return orders;
    }

    // for admin
    public static List<Order> getAllOrders() {
        List<String> ordersData = FileHandler.readFile(ORDER_FILE);
        List<Order> orders = new ArrayList<>();
        for (String orderLine : ordersData) {
            String[] orderInfo = orderLine.split(",");
            Order order = new Order(Integer.parseInt(orderInfo[0]), Integer.parseInt(orderInfo[1]), Integer.parseInt(orderInfo[2]), Integer.parseInt(orderInfo[3]), orderInfo[4]);
            orders.add(order);
        }
        return orders;
    }

    public static void updateOrderStatus(int orderId, String status) {
        List<String> orders = FileHandler.readFile(ORDER_FILE);
        for (int i = 0; i < orders.size(); i++) {
            String[] orderInfo = orders.get(i).split(",");
            if (Integer.parseInt(orderInfo[0]) == orderId) {
                orders.set(i, orderInfo[0] + "," + orderInfo[1] + "," + orderInfo[2] + "," + orderInfo[3] + "," + status);
                break;
            }
        }
        FileHandler.writeFile(ORDER_FILE, orders);
    }
}
