package models;

public class Customer extends User {

    public Customer(String name, String username, String password) {
        super(name, username, password);
    }

    public Customer(int id, String name, String username, String password) {
        super(id, name, username, password);
    }

    @Override
    public boolean login(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }

    @Override
    public boolean register() {
        return true;
    }

    public void viewProducts() {
        // Logic to view products
    }

    public void orderProduct(Product product, int quantity) {
        // Logic to order product
    }

    public void trackOrder(int orderId) {
        // Logic to track order
    }
}
