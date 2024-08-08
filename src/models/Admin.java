package models;

public class Admin extends User {

    public Admin(String name, String username, String password) {
        super(name, username, password);
    }

    public Admin(int id, String name, String username, String password) {
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

    public void manageProduct(Product product, String action) {
        if ("add".equals(action)) {
            // Logic to add product
        } else if ("update".equals(action)) {
            // Logic to update product
        } else if ("delete".equals(action)) {
            // Logic to delete product
        }
    }

    public void fulfillOrder(Order order) {
        order.setStatus("Shipped");
    }
}
