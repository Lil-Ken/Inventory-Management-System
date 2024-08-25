package models;

import services.ProductManagementService;

public class Product {
    private static int numProduct = 0;
    private int id;
    private String name;
    private double price;
    private int quantity;
    private String brand;

    public Product(String name, double price, int quantity, String brand) {
        this.id = ProductManagementService.getNewProductId();
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.brand = brand;
        numProduct++;
    }

    public Product(int id, String name, double price, int quantity, String brand) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.brand = brand;
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

    public String getBrand() {
        return brand;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
