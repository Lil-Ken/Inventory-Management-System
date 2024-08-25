package models;

import services.OrderManagementService;

public class Order {
    private static int numOrder = 0;
    private int id;
    private int userId;
    private int productId;
    private int quantity;
    private String status;

    public Order(int userId, int productId, int quantity, String status) {
        this.id = OrderManagementService.getNewOrderId();
        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
        this.status = status;
    }

    public Order(int id, int userId, int productId, int quantity, String status) {
        this.id = id;
        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
        this.status = status;
    }

    public static int getNumOrder() {
        return numOrder;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public int getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
