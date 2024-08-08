package models;

public class Product {
    private static int numProduct = 0;
    private int id;
    private String name;
    private double price;
    private int quantity;
    private String description;

    public Product(String name, double price, int quantity, String description) {
        this.id = ++numProduct;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
    }

    public Product(int id, String name, double price, int quantity, String description) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
    }

    public static int getNumProduct() {
        return numProduct;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
