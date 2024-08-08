package services;

import models.Product;
import utils.FileHandler;

import java.util.ArrayList;
import java.util.List;

public class ProductManagementService {

    private static final String PRODUCT_FILE = "resources/products.txt";

    public static List<Product> getAllProducts() {
        List<String> productsData = FileHandler.readFile(PRODUCT_FILE);
        List<Product> products = new ArrayList<>();
        for (String productLine : productsData) {
            String[] productInfo = productLine.split(",");
            Product product = new Product(Integer.parseInt(productInfo[0]), productInfo[1], Double.parseDouble(productInfo[2]), Integer.parseInt(productInfo[3]), productInfo[4]);
            products.add(product);
        }
        return products;
    }

    public static void addProduct(Product product) {
        String newProductLine = product.getId() + "," + product.getName() + "," + product.getPrice() + "," + product.getQuantity() + "," + product.getDescription();
        List<String> products = FileHandler.readFile(PRODUCT_FILE);
        products.add(newProductLine);
        FileHandler.writeFile(PRODUCT_FILE, products);
    }

    public static void updateProduct(Product product) {
        List<String> products = FileHandler.readFile(PRODUCT_FILE);
        for (int i = 0; i < products.size(); i++) {
            String[] productInfo = products.get(i).split(",");
            if (Integer.parseInt(productInfo[0]) == product.getId()) {
                products.set(i, product.getId() + "," + product.getName() + "," + product.getPrice() + "," + product.getQuantity() + "," + product.getDescription());
                break;
            }
        }
        FileHandler.writeFile(PRODUCT_FILE, products);
    }

    public static void deleteProduct(int productId) {
        List<String> products = FileHandler.readFile(PRODUCT_FILE);
        products.removeIf(productLine -> Integer.parseInt(productLine.split(",")[0]) == productId);
        FileHandler.writeFile(PRODUCT_FILE, products);
    }

    public static boolean hasProducts() {
        List<String> productsData = FileHandler.readFile(PRODUCT_FILE);
        return !productsData.isEmpty();  // Returns true if there are products, false if the list is empty
    }

}
