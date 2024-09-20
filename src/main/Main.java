package main;

import models.*;
import services.*;
import utils.InputValidationUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        clearConsole();
        Scanner scanner = new Scanner(System.in);
        boolean loginFail = false;

        while (true) {
            if (loginFail) {
                clearConsole();
                System.out.println("Invalid credentials!");
            }
            System.out.println("Welcome to the Inventory Management System");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");
            int option = InputValidationUtils.getValidatedInt(0, 2);

            switch (option) {
                case 1 -> loginFail = login(scanner);
                case 2 -> register();
                case 0 -> {
                    System.out.println("Goodbye!");
                    System.exit(0);
                }
                default -> System.out.println("Invalid option. Please choose again.");
            }
        }
    }

    // Login
    private static boolean login(Scanner scanner) {
        boolean loginFail = false;

        System.out.print("Username: ");
        String username = InputValidationUtils.getValidatedUsername();

        System.out.print("Password: ");
        String password = InputValidationUtils.getValidatedPassword();

        User user = UserManagementService.login(username, password);
        if (user != null) {
            clearConsole();
            if (user instanceof Admin) {
                adminMenu(scanner);
            } else {
                customerMenu((Customer) user, scanner);
            }
        } else {
            loginFail = true;
        }
        return loginFail;
    }

    // Register
    private static void register() {

        System.out.print("Username: ");
        String username = InputValidationUtils.getValidatedUsername();

        System.out.print("Password: ");
        String password = InputValidationUtils.getValidatedPassword();

        System.out.print("Register an admin account (y/n)? ");
        boolean isAdmin = InputValidationUtils.getValidatedYesNo();

        if (isAdmin) {
            System.out.print("Enter the admin password: ");
            int staffPass = InputValidationUtils.getValidatedInt(0, Integer.MAX_VALUE);
            if (staffPass != 666) {
                System.out.println("Incorrect admin password.");
                return;
            }
        }

        User newUser = isAdmin ? new Admin(username, password) : new Customer(username, password);

        if (UserManagementService.register(newUser)) {
            clearConsole();
            System.out.println("Registration successful.");
        } else {
            clearConsole();
            System.out.println("Username already exists.");
        }
    }


    // Menu
    private static void adminMenu(Scanner scanner) {
        int option;
        do {
            clearConsole();
            System.out.println("Admin Menu:");
            System.out.println("1. Manage Product");
            System.out.println("2. Manage Order");
            System.out.println("3. Generate Transaction Report");
            System.out.println("0. Logout");
            System.out.print("Choose an option: ");
            option = InputValidationUtils.getValidatedInt(0, 3);

            switch (option) {
                case 1 -> {
                    clearConsole();
                    manageProductMenu(scanner);
                }
                case 2 -> {
                    clearConsole();
                    manageOrderMenu(scanner);
                }
                case 3 -> {
                    clearConsole();
                    generateTransactionReportMenu(scanner);
                }
                case 0 -> {
                    clearConsole();
                    System.out.println("Logging out...");
                }
                default -> {
                    clearConsole();
                    System.out.println("Invalid option. Please choose again.");
                }
            }
        } while (option != 0);
    }

    private static void manageProductMenu(Scanner scanner) {
        int option;
        do {
            System.out.println("Manage Product Menu:");
            System.out.println("1. View Products");
            System.out.println("2. Search/Filter Products");
            System.out.println("3. Modify Products");
            System.out.println("0. Back to Admin Menu");
            System.out.print("Choose an option: ");
            option = InputValidationUtils.getValidatedInt(0, 3);

            switch (option) {
                case 1 -> {
                    viewProducts();
                    System.out.print("\nPress Enter to continue...");
                    scanner.nextLine();
                    clearConsole();
                }
                case 2 -> {
                    clearConsole();
                    searchFilterProductMenu();
                }
                case 3 -> {
                    clearConsole();
                    modifyProductMenu();
                }
                case 0 -> clearConsole();
                default -> System.out.println("Invalid option. Please choose again.");
            }
        } while (option != 0);
    }

    private static void searchFilterProductMenu() {
        int option;
        do {
            System.out.println("Search/Filter Products:");
            System.out.println("1. Search Products");
            System.out.println("2. Filter Products");
            System.out.println("0. Back to Manage Product Menu");
            System.out.print("Choose an option: ");
            option = InputValidationUtils.getValidatedInt(0, 2);

            switch (option) {
                case 1 -> searchProductsMenu();
                case 2 -> filterProductsMenu();
                case 0 -> clearConsole();

                default -> {
                    clearConsole();
                    System.out.println("Invalid option. Please choose again.");
                }
            }
        } while (option != 0);
    }

    private static void modifyProductMenu() {
        int option;
        do {
            System.out.println("Modify Products:");
            System.out.println("1. Add Product");
            System.out.println("2. Update Product");
            System.out.println("3. Delete Product");
            System.out.println("0. Back to Manage Product Menu");
            System.out.print("Choose an option: ");
            option = InputValidationUtils.getValidatedInt(0, 3);

            switch (option) {
                case 1 -> addProduct();
                case 2 -> updateProduct();
                case 3 -> deleteProduct();
                case 0 -> clearConsole();
                default -> System.out.println("Invalid option. Please choose again.");
            }
        } while (option != 0);
    }


    private static void manageOrderMenu(Scanner scanner) {
        int option;
        do {
            System.out.println("Manage Order Menu:");
            System.out.println("1. View Orders");
            System.out.println("2. Fulfill Order");
            System.out.println("0. Back to Admin Menu");
            System.out.print("Choose an option: ");
            option = InputValidationUtils.getValidatedInt(0, 2);

            switch (option) {
                case 1 -> {
                    clearConsole();
                    viewOrdersMenu(scanner);
                }
                case 2 -> fulfillOrder();
                case 0 -> clearConsole();
                default -> System.out.println("Invalid option. Please choose again.");
            }
        } while (option != 0);
    }

    private static void viewOrdersMenu(Scanner scanner) {
        int option;
        do {
            System.out.println("View Orders:");
            System.out.println("1. Track All Orders");
            System.out.println("2. Filter Orders by Status");
            System.out.println("0. Back to Manage Order Menu");
            System.out.print("Choose an option: ");
            option = InputValidationUtils.getValidatedInt(0, 2);

            switch (option) {
                case 1 -> {
                    trackAllOrder();
                    System.out.print("\nPress Enter to continue...");
                    scanner.nextLine();
                    clearConsole();
                }
                case 2 -> {
                    filterOrdersByStatus();
                    System.out.print("\nPress Enter to continue...");
                    scanner.nextLine();
                    clearConsole();
                }
                case 0 -> clearConsole();
                default -> System.out.println("Invalid option. Please choose again.");
            }
        } while (option != 0);
    }

    private static void generateTransactionReportMenu(Scanner scanner) {
        clearConsole();
        System.out.println("Generate Transaction Report:");
        System.out.println("1. Generate Report for a Specific User");
        System.out.println("2. Generate Report for All Transactions");
        System.out.println("3. Generate Report by Date Range");
        System.out.println("0. Back to Admin Menu");
        System.out.print("Choose an option: ");
        int reportOption = InputValidationUtils.getValidatedInt(0, 3);

        List<Transaction> transactions = null;

        switch (reportOption) {
            case 1 -> {
                System.out.print("Enter User ID: ");
                int userId = InputValidationUtils.getValidatedInt(1, Integer.MAX_VALUE);
                clearConsole();
                ReportService.generateUserReport(userId);
                transactions = TransactionManagementService.getTransactionsByUserId(userId);
            }
            case 2 -> {
                clearConsole();
                ReportService.generateAllTransactionsReport();
                transactions = TransactionManagementService.getAllTransactions();
            }
            case 3 -> {
                clearConsole();
                ReportService.trackSalesByDateRange();
                transactions = TransactionManagementService.getAllTransactions();
            }
            case 0 -> clearConsole();

            default -> {
                clearConsole();
                System.out.println("Invalid option. Please choose again.");
            }
        }

        ReportService.askToSaveReport(transactions);

        System.out.print("\nPress Enter to continue...");
        scanner.nextLine();
    }


    private static void customerMenu(Customer customer, Scanner scanner) {
        Wallet wallet = new Wallet();
        int option;
        do {
            System.out.println("Customer Menu:");
            System.out.println("1. Browse Products");
            System.out.println("2. Manage Orders");
            System.out.println("3. Wallet");
            System.out.println("4. Wishlist");
            System.out.println("0. Logout");
            System.out.print("Choose an option: ");
            option = InputValidationUtils.getValidatedInt(0, 4);

            switch (option) {
                case 1 -> {
                    clearConsole();
                    browseProductsMenu(scanner);
                }
                case 2 -> {
                    clearConsole();
                    manageCustomerOrdersMenu(customer);
                }
                case 3 -> walletMenu(customer, wallet);
                case 4 -> wishlistMenu(customer);
                case 0 -> {
                    clearConsole();
                    System.out.println("Logging out...");
                }
                default -> {
                    clearConsole();
                    System.out.println("Invalid option. Please choose again.");
                }
            }
        } while (option != 0);
    }

    private static void browseProductsMenu(Scanner scanner) {
        int option;
        do {
            System.out.println("Browse Products:");
            System.out.println("1. View All Products");
            System.out.println("2. Search Products");
            System.out.println("3. Filter Products");
            System.out.println("4. Sort Products");
            System.out.println("0. Back to Customer Menu");
            System.out.print("Choose an option: ");
            option = InputValidationUtils.getValidatedInt(0, 4);

            switch (option) {
                case 1 -> {
                    viewProducts();
                    System.out.print("\nPress Enter to continue...");
                    scanner.nextLine();
                    clearConsole();
                }
                case 2 -> searchProductsMenu();
                case 3 -> filterProductsMenu();
                case 4 -> sortProductsMenu();
                case 0 -> clearConsole();
                default -> System.out.println("Invalid option. Please choose again.");
            }
        } while (option != 0);
    }

    private static void manageCustomerOrdersMenu(Customer customer) {
        int option;
        do {
            System.out.println("Manage Orders:");
            System.out.println("1. Order Product");
            System.out.println("2. Track Orders");
            System.out.println("3. Cancel Order");
            System.out.println("0. Back to Customer Menu");
            System.out.print("Choose an option: ");
            option = InputValidationUtils.getValidatedInt(0, 3);

            switch (option) {
                case 1 -> orderProduct(customer);
                case 2 -> trackOrder(customer);
                case 3 -> cancelOrder(customer);
                case 0 -> clearConsole();
                default -> System.out.println("Invalid option. Please choose again.");
            }
        } while (option != 0);
    }


    private static void wishlistMenu(Customer customer) {
        int option;
        clearConsole();
        do {
            System.out.println("Wishlist Menu:");
            System.out.println("1. View Wishlist");
            System.out.println("2. Add Product to Wishlist");
            System.out.println("3. Remove Product from Wishlist");
            System.out.println("0. Back to Customer Menu");
            System.out.print("Choose an option: ");
            option = InputValidationUtils.getValidatedInt(0, 3);

            switch (option) {
                case 1 -> viewWishlist(customer);
                case 2 -> addProductToWishlist(customer);
                case 3 -> removeProductFromWishlist(customer);
                case 0 -> clearConsole();
                default -> System.out.println("Invalid option. Please choose again.");
            }
        } while (option != 0);
    }

    private static boolean viewWishlist(Customer customer) {
        Scanner scanner = new Scanner(System.in);
        List<Product> wishlist = WishlistService.getWishlistByUserId(customer.getId());

        if (wishlist.isEmpty()) {
            clearConsole();
            System.out.println("Your wishlist is empty.");
            return true;
        }

        displayProducts(wishlist);
        System.out.print("\nPress Enter to continue...");
        scanner.nextLine();
        clearConsole();
        return false;
    }

    private static void addProductToWishlist(Customer customer) {
        viewProducts();
        System.out.print("Enter Product ID to add to wishlist: ");
        int productId = InputValidationUtils.getValidatedInt(1, Integer.MAX_VALUE);

        Product product = ProductManagementService.getProductById(productId);
        clearConsole();
        if (product != null) {
            WishlistService.addProductToWishlist(customer.getId(), product);
            System.out.println("Product added to wishlist.");
        } else {
            System.out.println("Invalid Product ID.");
        }
    }

    private static void removeProductFromWishlist(Customer customer) {
        // if is empty
        if(viewWishlist(customer)) return;

        System.out.print("Enter Product ID to remove from wishlist: ");
        int productId = InputValidationUtils.getValidatedInt(1, Integer.MAX_VALUE);

        WishlistService.removeProductFromWishlist(customer.getId(), productId);
        System.out.println("Product removed from wishlist.");
    }


    // Admin Product Management
    private static void addProduct() {
        clearConsole();
        System.out.print("Product Name: ");
        String name = InputValidationUtils.getValidatedString(1, 30);

        System.out.print("Price: ");
        double price = InputValidationUtils.getValidatedDouble(0.01, Double.MAX_VALUE);

        System.out.print("Quantity: ");
        int quantity = InputValidationUtils.getValidatedInt(1, Integer.MAX_VALUE);

        System.out.print("Brand: ");
        String brand = InputValidationUtils.getValidatedString(1, 20);

        Product product = new Product(name, price, quantity, brand);
        ProductManagementService.addProduct(product);
        clearConsole();
        System.out.println("Product added successfully.");
    }

    private static void updateProduct() {
        viewProducts();
        System.out.print("Product ID to update (0 to cancel): ");
        int productId = InputValidationUtils.getValidatedInt(0, Integer.MAX_VALUE);

        if (productId == 0) {
            clearConsole();
            System.out.println("Product update cancelled.");
            return;
        }

        // Get the existing product details first
        Product existingProduct = ProductManagementService.getProductById(productId);
        if (existingProduct == null) {
            System.out.println("Product not found.");
            return;
        }

        String name = existingProduct.getName();
        double price = existingProduct.getPrice();
        int quantity = existingProduct.getQuantity();
        String brand = existingProduct.getBrand();

        System.out.println("1. Product Name ");
        System.out.println("2. Product Price ");
        System.out.println("3. Product Quantity ");
        System.out.println("4. Product Brand ");
        System.out.println("0. Cancel");
        System.out.print("Select an option to update: ");
        int option = InputValidationUtils.getValidatedInt(0, 4);

        switch (option) {
            case 1:
                System.out.print("New Product Name: ");
                name = InputValidationUtils.getValidatedString(1, 30);
                break;
            case 2:
                System.out.print("New Price: ");
                price = InputValidationUtils.getValidatedDouble(0.01, Double.MAX_VALUE);
                break;
            case 3:
                System.out.print("New Quantity: ");
                quantity = InputValidationUtils.getValidatedInt(1, Integer.MAX_VALUE);
                break;
            case 4:
                System.out.print("New Brand: ");
                brand = InputValidationUtils.getValidatedString(1, 20);
                break;
            case 0:
                clearConsole();
                System.out.println("Update cancelled.");
                return;
            default:
                System.out.println("Invalid option selected.");
                break;
        }

        Product product = new Product(productId, name, price, quantity, brand);
        ProductManagementService.updateProduct(product);
        clearConsole();
        System.out.println("Product updated successfully.");
    }

    private static void deleteProduct() {
        viewProducts();
        System.out.print("Product ID to delete (0 to cancel): ");
        int productId = InputValidationUtils.getValidatedInt(0, Integer.MAX_VALUE);

        if (productId == 0) {
            clearConsole();
            System.out.println("Product deletion cancelled.");
            return;
        }

        // Get the product by ID
        Product product = ProductManagementService.getProductById(productId);
        if (product == null) {
            System.out.println("Product not found.");
            return;
        }

        clearConsole();

        // Display the product details
        System.out.println("Product to delete:");
        System.out.println("-------------------------------------------------------------------------------------------");
        System.out.printf("| %-5s | %-30s | %-10s | %-10s | %-20s |\n", "ID", "Name", "Price (RM)", "Quantity", "Brand");
        System.out.println("-------------------------------------------------------------------------------------------");
        System.out.printf("| %-5d | %-30s | %-10.2f | %-10d | %-20s |\n",
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getQuantity(),
                product.getBrand());
        System.out.println("-------------------------------------------------------------------------------------------");

        System.out.print("Are you sure you want to delete this product? (Y/N): ");
        boolean confirmation = InputValidationUtils.getValidatedYesNo();

        if (confirmation) {
            ProductManagementService.deleteProduct(productId);
            clearConsole();
            System.out.println("Product deleted successfully.");
        } else {
            clearConsole();
            System.out.println("Product deletion cancelled.");
        }
    }


    // Admin Order Management
    private static void trackAllOrder() {
        clearConsole();
        List<Order> orders = OrderManagementService.getAllOrders();

        if (orders.isEmpty()) {
            System.out.println("No orders available.");
            return;
        }

        System.out.println("All Orders:");
        System.out.println("---------------------------------------------------------------------------------------------------");
        System.out.printf("| %-5s | %-10s | %-10s | %-30s | %-10s | %-15s |\n", "ID", "User ID", "Product ID", "Product Name", "Quantity", "Status");
        System.out.println("---------------------------------------------------------------------------------------------------");

        for (Order order : orders) {
            String productName = ProductManagementService.getProductById(order.getProductId()).getName();
            System.out.printf("| %-5d | %-10d | %-10d | %-30s | %-10d | %-15s |\n",
                    order.getId(),
                    order.getUserId(),
                    order.getProductId(),
                    productName,
                    order.getQuantity(),
                    order.getStatus());
        }

        System.out.println("---------------------------------------------------------------------------------------------------");
    }

    private static void filterOrdersByStatus() {
        clearConsole();
        System.out.println("Filter Orders by Status:");
        System.out.println("1. Pending");
        System.out.println("2. Shipped");
        System.out.println("3. Cancelled");
        System.out.println("0. Back to View Orders Menu");
        System.out.print("Choose a status to filter by: ");
        int statusOption = InputValidationUtils.getValidatedInt(0, 4);

        String status;
        switch (statusOption) {
            case 1 -> status = "Pending";
            case 2 -> status = "Shipped";
            case 3 -> status = "Cancelled";
            case 0 -> {
                clearConsole();
                return;
            }
            default -> {
                clearConsole();
                System.out.println("Invalid option. Please choose again.");
                return;
            }
        }

        List<Order> orders = OrderManagementService.getOrdersByStatus(status);
        if (orders.isEmpty()) {
            System.out.println("No orders found with status: " + status);
        } else {
            System.out.println("Orders with status: " + status);
            System.out.println("---------------------------------------------------------------------------------------------------");
            System.out.printf("| %-5s | %-10s | %-10s | %-30s | %-10s | %-15s |\n", "ID", "User ID", "Product ID", "Product Name", "Quantity", "Status");
            System.out.println("---------------------------------------------------------------------------------------------------");

            for (Order order : orders) {
                String productName = ProductManagementService.getProductById(order.getProductId()).getName();
                System.out.printf("| %-5d | %-10d | %-10d | %-30s | %-10d | %-15s |\n",
                        order.getId(),
                        order.getUserId(),
                        order.getProductId(),
                        productName,
                        order.getQuantity(),
                        order.getStatus());
            }

            System.out.println("---------------------------------------------------------------------------------------------------");
        }
    }


    private static void fulfillOrder() {
        List<Order> pendingOrders = OrderManagementService.getPendingOrders();

        if (pendingOrders.isEmpty()) {
            clearConsole();
            System.out.println("No pending orders to fulfill.");
            return;
        }

        System.out.println("Pending Orders:");
        System.out.println("----------------------------------------------------------------------------------------------");
        System.out.printf("| %-5s | %-10s | %-10s | %-25s | %-10s | %-15s |\n", "ID", "User ID", "Product ID", "Product Name", "Quantity", "Status");
        System.out.println("----------------------------------------------------------------------------------------------");

        for (Order order : pendingOrders) {
            String productName = ProductManagementService.getProductById(order.getProductId()).getName();
            System.out.printf("| %-5d | %-10d | %-10d | %-25s | %-10d | %-15s |\n",
                    order.getId(),
                    order.getUserId(),
                    order.getProductId(),
                    productName,
                    order.getQuantity(),
                    order.getStatus());
        }

        System.out.println("----------------------------------------------------------------------------------------------");

        System.out.print("Order ID to fulfill (0 to cancel): ");
        int orderId = InputValidationUtils.getValidatedInt(0, Integer.MAX_VALUE);

        if (orderId == 0) {
            clearConsole();
            System.out.println("Order fulfillment cancelled.");
            return;
        }

        // Find the order to fulfill
        Order orderToFulfill = null;
        for (Order order : pendingOrders) {
            if (order.getId() == orderId) {
                orderToFulfill = order;
                break;
            }
        }

        if (orderToFulfill == null) {
            System.out.println("Order not found.");
            return;
        }

        // Fulfill the order
        clearConsole();
        OrderManagementService.updateOrderStatus(orderId, "Shipped");
        System.out.println("Order fulfilled successfully.");
    }


    // Customer
    private static void viewProducts() {
        clearConsole();
        List<Product> products = ProductManagementService.getAllProducts();

        System.out.println("Available Products:");
        System.out.println("-------------------------------------------------------------------------------------------");
        System.out.printf("| %-5s | %-30s | %-10s | %-10s | %-20s |\n", "ID", "Name", "Price (RM)", "Quantity", "Brand");
        System.out.println("-------------------------------------------------------------------------------------------");

        for (Product product : products) {
            System.out.printf("| %-5d | %-30s | %-10.2f | %-10d | %-20s |\n",
                    product.getId(),
                    product.getName(),
                    product.getPrice(),
                    product.getQuantity(),
                    product.getBrand());
        }

        System.out.println("-------------------------------------------------------------------------------------------");
    }

    private static void searchProductsMenu() {
        Scanner scanner = new Scanner(System.in);
        clearConsole();
        System.out.println("Search Products:");
        System.out.println("1. Search by Name");
        System.out.println("2. Search by Brand");
        System.out.println("0. Back to Customer Menu");
        System.out.print("Choose an option: ");
        int searchOption = InputValidationUtils.getValidatedInt(0, 2);

        switch (searchOption) {
            case 1 -> {
                System.out.print("Enter product name to search: ");
                String name = InputValidationUtils.getValidatedString(1, 30);
                List<Product> products = ProductManagementService.searchProductsByName(name);
                displayProducts(products);
            }
            case 2 -> {
                System.out.print("Enter brand name to search: ");
                String brand = InputValidationUtils.getValidatedString(1, 30);
                List<Product> products = ProductManagementService.searchProductsByBrand(brand);
                displayProducts(products);
            }
            case 0 -> clearConsole();

            default -> {
                clearConsole();
                System.out.println("Invalid option. Please choose again.");
            }
        }
        System.out.print("\nPress Enter to continue...");
        scanner.nextLine();
        clearConsole();
    }

    private static void filterProductsMenu() {
        Scanner scanner = new Scanner(System.in);
        clearConsole();
        System.out.println("Filter Products:");
        System.out.println("1. Filter by Price Range");
        System.out.println("2. Filter by Minimum Quantity");
        System.out.println("0. Back to Customer Menu");
        System.out.print("Choose an option: ");
        int filterOption = InputValidationUtils.getValidatedInt(0, 2);

        switch (filterOption) {
            case 1 -> {
                System.out.print("Enter minimum price: ");
                double minPrice = InputValidationUtils.getValidatedDouble(0.01, Double.MAX_VALUE);

                double maxPrice;
                while (true) {
                    System.out.print("Enter maximum price: ");
                    maxPrice = InputValidationUtils.getValidatedDouble(minPrice, Double.MAX_VALUE);
                    if (maxPrice >= minPrice) {
                        break;
                    } else {
                        System.out.println("Maximum price must be greater than or equal to the minimum price. Please try again.");
                    }
                }

                List<Product> products = ProductManagementService.filterProductsByPriceRange(minPrice, maxPrice);
                displayProducts(products);
            }
            case 2 -> {
                System.out.print("Enter minimum quantity in stock: ");
                int minQuantity = InputValidationUtils.getValidatedInt(1, Integer.MAX_VALUE);
                List<Product> products = ProductManagementService.filterProductsByMinimumQuantity(minQuantity);
                displayProducts(products);
            }
            case 0 -> clearConsole();

            default -> {
                clearConsole();
                System.out.println("Invalid option. Please choose again.");
            }
        }
        System.out.print("\nPress Enter to continue...");
        scanner.nextLine();
        clearConsole();
    }

    private static void sortProductsMenu() {
        clearConsole();
        System.out.println("Sort Products:");
        System.out.println("1. Sort by Name");
        System.out.println("2. Sort by Price");
        System.out.println("3. Sort by Quantity");
        System.out.println("0. Back to Browse Products Menu");
        System.out.print("Choose an option: ");
        int sortOption = InputValidationUtils.getValidatedInt(0, 3);

        List<Product> products = ProductManagementService.getAllProducts();
        switch (sortOption) {
            case 1 -> products.sort(Comparator.comparing(Product::getName));
            case 2 -> products.sort(Comparator.comparing(Product::getPrice));
            case 3 -> products.sort(Comparator.comparing(Product::getQuantity));
            case 0 -> clearConsole();
            default -> {
                clearConsole();
                System.out.println("Invalid option. Please choose again.");
            }
        }
    }


    private static void displayProducts(List<Product> products) {
        clearConsole();
        if (products.isEmpty()) {
            System.out.println("No products found matching your criteria.");
        } else {
            System.out.println("Search Results:");
            System.out.println("-------------------------------------------------------------------------------------------");
            System.out.printf("| %-5s | %-30s | %-10s | %-10s | %-20s |\n", "ID", "Name", "Price (RM)", "Quantity", "Brand");
            System.out.println("-------------------------------------------------------------------------------------------");

            for (Product product : products) {
                System.out.printf("| %-5d | %-30s | %-10.2f | %-10d | %-20s |\n",
                        product.getId(),
                        product.getName(),
                        product.getPrice(),
                        product.getQuantity(),
                        product.getBrand());
            }

            System.out.println("-------------------------------------------------------------------------------------------");
        }

    }

    private static void orderProduct(Customer customer) {
        Wallet wallet = new Wallet();

        if (ProductManagementService.hasProducts()) {
            viewProducts();
            System.out.print("Product ID to order (0 to cancel): ");
            int productId = InputValidationUtils.getValidatedInt(0, Integer.MAX_VALUE);

            if (productId == 0) {
                clearConsole();
                System.out.println("Order cancelled.");
                return;
            }

            System.out.print("Quantity: ");
            int quantity = InputValidationUtils.getValidatedInt(1, 1000);

            Product product = ProductManagementService.getProductById(productId);
            if (product == null) {
                System.out.println("Invalid Product ID.");
                return;
            }

            // Check if there is enough quantity available
            if (product.getQuantity() < quantity) {
                System.out.println("Insufficient product quantity available.");
                return;
            }

            while (true) {
                double totalPrice = product.getPrice() * quantity;
                double currentBalance = wallet.getBalance(customer.getId());

                System.out.println("Total Price: RM" + totalPrice + "         Balance: RM" + currentBalance);


                if (currentBalance >= totalPrice) {
                    // Ask user to confirm payment
                    System.out.print("Confirm payment of RM" + totalPrice + " (Y/N): ");
                    boolean confirm = InputValidationUtils.getValidatedYesNo();
                    clearConsole();

                    if (confirm) {
                        // Deduct the total price from the wallet
                        wallet.deduct(customer.getId(), totalPrice);

                        // Deduct the ordered quantity from the product's quantity
                        product.setQuantity(product.getQuantity() - quantity);
                        ProductManagementService.updateProduct(product);

                        // Place the order
                        Order order = new Order(customer.getId(), productId, quantity, "Pending");
                        OrderManagementService.placeOrder(order);

                        System.out.println("Order placed successfully.");
                        break;
                    } else {
                        System.out.println("Order cancelled.");
                        break;
                    }
                } else {
                    System.out.println("Insufficient balance. Your current balance is: RM" + currentBalance);
                    System.out.print("Would you like to top up your wallet? (Y/N): ");
                    boolean topUpChoice = InputValidationUtils.getValidatedYesNo();

                    if (topUpChoice) {
                        System.out.print("Enter top-up amount: ");
                        double topUpAmount = InputValidationUtils.getValidatedDouble(0.01, Double.MAX_VALUE);

                        wallet.topUp(customer.getId(), topUpAmount);
                    } else {
                        System.out.println("Order cancelled.");
                        break;
                    }
                }
            }

        } else {
            System.out.println("No products available.");
        }
    }


    private static void trackOrder(Customer customer) {
        clearConsole();
        List<Order> orders = OrderManagementService.getOrdersByUserId(customer.getId());

        if (orders.isEmpty()) {
            System.out.println("You have no orders.");
            return;
        }

        System.out.println("Your Orders:");
        System.out.println("--------------------------------------------------------------------------------------");
        System.out.printf("| %-5s | %-30s | %-10s | %-10s | %-15s |\n", "ID", "Product Name", "Product ID", "Quantity", "Status");
        System.out.println("--------------------------------------------------------------------------------------");

        for (Order order : orders) {
            String productName = ProductManagementService.getProductById(order.getProductId()).getName();
            System.out.printf("| %-5d | %-30s | %-10d | %-10d | %-15s |\n",
                    order.getId(),
                    productName,
                    order.getProductId(),
                    order.getQuantity(),
                    order.getStatus());
        }

        System.out.println("--------------------------------------------------------------------------------------");
    }

    private static void cancelOrder(Customer customer) {
        List<Order> orders = OrderManagementService.getOrdersByUserId(customer.getId());

        if (orders.isEmpty()) {
            clearConsole();
            System.out.println("You have no orders to cancel.");
            return;
        }

        System.out.println("Your Pending Orders:");
        System.out.println("--------------------------------------------------------------------------------------");
        System.out.printf("| %-5s | %-30s | %-10s | %-10s | %-15s |\n", "ID", "Product Name", "Product ID", "Quantity", "Status");
        System.out.println("--------------------------------------------------------------------------------------");

        boolean hasPendingOrder = false;
        for (Order order : orders) {
            if ("Pending".equals(order.getStatus())) {
                String productName = ProductManagementService.getProductById(order.getProductId()).getName();
                System.out.printf("| %-5d | %-30s | %-10d | %-10d | %-15s |\n",
                        order.getId(),
                        productName,
                        order.getProductId(),
                        order.getQuantity(),
                        order.getStatus());
                hasPendingOrder = true;
            }
        }

        if (!hasPendingOrder) {
            clearConsole();
            System.out.println("You have no pending orders to cancel.");
            return;
        }

        System.out.println("--------------------------------------------------------------------------------------");
        System.out.print("Order ID to cancel (0 to cancel action): ");
        int orderId = InputValidationUtils.getValidatedInt(0, Integer.MAX_VALUE);

        if (orderId == 0) {
            clearConsole();
            System.out.println("Order cancellation action cancelled.");
            return;
        }

        // Find the order to cancel
        Order orderToCancel = null;
        for (Order order : orders) {
            if (order.getId() == orderId && "Pending".equals(order.getStatus())) {
                orderToCancel = order;
                break;
            }
        }

        if (orderToCancel == null) {
            System.out.println("Order not found or not in a cancellable state.");
            return;
        }

        // Refund the customer
        Wallet wallet = new Wallet();
        double refundAmount = ProductManagementService.getProductById(orderToCancel.getProductId()).getPrice() * orderToCancel.getQuantity();
        wallet.topUp(customer.getId(), refundAmount);

        // Restore the product quantity
        Product product = ProductManagementService.getProductById(orderToCancel.getProductId());
        product.setQuantity(product.getQuantity() + orderToCancel.getQuantity());
        ProductManagementService.updateProduct(product);

        // Update the order status to "Cancelled"
        OrderManagementService.updateOrderStatus(orderToCancel.getId(), "Cancelled");
        clearConsole();
        System.out.println("Order cancelled and amount of RM" + refundAmount + " has been refunded.");
    }


    private static void walletMenu(Customer customer, Wallet wallet) {
        int walletOption;
        clearConsole();
        do {
            wallet.displayBalance(customer.getId());
            System.out.println("1. Top Up");
            System.out.println("2. View Transaction History");
            System.out.println("0. Back to Customer Menu");
            System.out.print("Choose an option: ");
            walletOption = InputValidationUtils.getValidatedInt(0, 2);

            switch (walletOption) {
                case 1 -> {
                    System.out.print("Enter amount to top up: ");
                    double amount = InputValidationUtils.getValidatedDouble(0.01, Double.MAX_VALUE);
                    clearConsole();
                    wallet.topUp(customer.getId(), amount);

                    // Generate transaction ID and formatted timestamp
                    int transactionId = TransactionManagementService.getNewTransactionId();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
                    String timestamp = LocalDateTime.now().format(formatter);

                    // Record the transaction
                    Transaction transaction = new Transaction(transactionId, customer.getId(), amount, timestamp);
                    TransactionManagementService.recordTransaction(transaction);
                }
                case 2 -> viewWalletTransactionHistory(customer);
            }
        } while (walletOption != 0);
        clearConsole();
    }


    private static void viewWalletTransactionHistory(Customer customer) {
        clearConsole();
        List<Transaction> transactions = TransactionManagementService.getTransactionsByUserId(customer.getId());

        if (transactions.isEmpty()) {
            System.out.println("No transactions found.");
            return;
        }

        System.out.println("Your Wallet Transactions:");
        System.out.println("-------------------------------------------------------");
        System.out.printf("| %-10s | %-20s | %-15s |\n", "ID", "Timestamp", "Amount (RM)");
        System.out.println("-------------------------------------------------------");

        for (Transaction transaction : transactions) {
            System.out.printf("| %-10d | %-20s | %-15.2f |\n",
                    transaction.getId(),
                    transaction.getTimestamp(),
                    transaction.getAmount());
        }

        System.out.println("-------------------------------------------------------");
        System.out.print("\nPress Enter to continue...");
        new Scanner(System.in).nextLine();
    }


    public static void clearConsole() {
        for (int i = 0; i < 20; ++i) {
            System.out.println();
        }
    }


}
