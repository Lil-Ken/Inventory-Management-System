package services;

import models.Product;
import utils.FileHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        String newProductLine = product.getId() + "," + product.getName() + "," + product.getPrice() + "," + product.getQuantity() + "," + product.getBrand();
        List<String> products = FileHandler.readFile(PRODUCT_FILE);
        products.add(newProductLine);
        FileHandler.writeFile(PRODUCT_FILE, products);
    }

    public static void updateProduct(Product product) {
        List<String> products = FileHandler.readFile(PRODUCT_FILE);
        for (int i = 0; i < products.size(); i++) {
            String[] productInfo = products.get(i).split(",");
            if (Integer.parseInt(productInfo[0]) == product.getId()) {
                products.set(i, product.getId() + "," + product.getName() + "," + product.getPrice() + "," + product.getQuantity() + "," + product.getBrand());
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

    public static int getNewProductId() {
        List<String> products = FileHandler.readFile(PRODUCT_FILE);
        int newID = 0;
        for (String productLine : products) {
            String[] productInfo = productLine.split(",");
            int currentId = Integer.parseInt(productInfo[0]);
            if (currentId > newID) {
                newID = currentId;
            }
        }
        return ++newID;
    }

    public static Product getProductById(int productId) {
        List<String> productsData = FileHandler.readFile(PRODUCT_FILE);
        for (String productLine : productsData) {
            String[] productInfo = productLine.split(",");
            if (Integer.parseInt(productInfo[0]) == productId) {
                return new Product(
                        Integer.parseInt(productInfo[0]),
                        productInfo[1],
                        Double.parseDouble(productInfo[2]),
                        Integer.parseInt(productInfo[3]),
                        productInfo[4]
                );
            }
        }
        return null;
    }

    // Search products by name (case-insensitive)
    public static List<Product> searchProductsByName(String name) {
        List<Product> products = getAllProducts();
        return products.stream()
                .filter(product -> product.getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
    }

    // Search products by brand (case-insensitive)
    public static List<Product> searchProductsByBrand(String brand) {
        List<Product> products = getAllProducts();
        return products.stream()
                .filter(product -> product.getBrand().toLowerCase().contains(brand.toLowerCase()))
                .collect(Collectors.toList());
    }

    // Filter products by price range
    public static List<Product> filterProductsByPriceRange(double minPrice, double maxPrice) {
        List<Product> products = getAllProducts();
        return products.stream()
                .filter(product -> product.getPrice() >= minPrice && product.getPrice() <= maxPrice)
                .collect(Collectors.toList());
    }

    // Filter products by quantity in stock (minimum quantity)
    public static List<Product> filterProductsByMinimumQuantity(int minQuantity) {
        List<Product> products = getAllProducts();
        return products.stream()
                .filter(product -> product.getQuantity() >= minQuantity)
                .collect(Collectors.toList());
    }
}
