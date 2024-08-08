import models.*;
import services.*;


import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        clearConsole();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            clearConsole();
            System.out.println("Welcome to the Inventory Management System");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");
            int option = scanner.nextInt();
            scanner.nextLine();  // consume newline

            if (option == 1) {
                String username;
                String password;

                // Username validation loop
                while (true) {
                    System.out.print("Username: ");
                    username = scanner.nextLine();

                    if (isValidUsername(username)) {
                        break;
                    } else {
                        System.out.println("Invalid username!");
                    }
                }

                // Password validation loop
                while (true) {
                    System.out.print("Password: ");
                    password = scanner.nextLine();

                    if (isValidPassword(password)) {
                        break;
                    } else {
                        System.out.println("Invalid password!");
                    }
                }

                User user = UserManagementService.login(username, password);
                if (user != null) {
                    if (user instanceof Admin) {
                        adminMenu((Admin) user, scanner);
                    } else {
                        customerMenu((Customer) user, scanner);
                    }
                } else {
                    System.out.println("Invalid credentials.");
                }
            } else if (option == 2) {
                int staffPass;
                boolean isAdmin;
                String username, name, password;

                // Full Name validation loop
                while (true) {
                    System.out.print("Full Name: ");
                    name = scanner.nextLine();

                    if (isValidName(name)) {
                        break;
                    } else {
                        System.out.println("Invalid name!");
                    }
                }

                // Username validation loop
                while (true) {
                    System.out.print("Username: ");
                    username = scanner.nextLine();

                    if (isValidUsername(username)) {
                        break;
                    } else {
                        System.out.println("Invalid username!");
                    }
                }

                // Password validation loop
                while (true) {
                    System.out.print("Password: ");
                    password = scanner.nextLine();

                    if (isValidPassword(password)) {
                        break;
                    } else {
                        System.out.println("Invalid password!");
                    }
                }

                while (true) {
                    System.out.print("Register an admin account (y/n)? ");
                    isAdmin = scanner.nextLine().equalsIgnoreCase("y");
                    if (isAdmin){
                        System.out.print("Enter the admin password: "); // 666
                        staffPass = scanner.nextInt();
                        if(staffPass == 666) break;
                    }
                    else break;
                }

                User newUser;
                if (isAdmin) {
                    newUser = new Admin(name, username, password);
                } else {
                    newUser = new Customer(name, username, password);
                }

                if (UserManagementService.register(newUser)) {
                    System.out.println("Registration successful.");
                } else {
                    System.out.println("Username already exists.");
                }
            } else if (option == 0) {
                System.out.println("Goodbye!");
                System.exit(0);
            }

        }
    }

    private static void adminMenu(Admin admin, Scanner scanner) {
        clearConsole();
        int option;
        do {
            System.out.println("Admin Menu:");
            System.out.println("1. Add Product");
            System.out.println("2. Update Product");
            System.out.println("3. Delete Product");
            System.out.println("4. Fulfill Order");
            System.out.println("0. Logout");
            System.out.print("Choose an option: ");
            option = scanner.nextInt();
            scanner.nextLine();  // consume newline

            switch (option) {
                case 1 -> addProduct(scanner);
                case 2 -> updateProduct(scanner);
                case 3 -> deleteProduct(scanner);
                case 4 -> fulfillOrder(scanner);
            }
        } while (option != 0);
    }

    private static void customerMenu(Customer customer, Scanner scanner) {
        clearConsole();
        int option;
        do {
            System.out.println("Customer Menu:");
            System.out.println("1. View Products");
            System.out.println("2. Order Product");
            System.out.println("3. Track Order");
            System.out.println("0. Logout");
            System.out.print("Choose an option: ");
            option = scanner.nextInt();
            scanner.nextLine();  // consume newline

            switch (option) {
                case 1 -> viewProducts();
                case 2 -> orderProduct(customer, scanner);
                case 3 -> trackOrder(customer);
            }
        } while (option != 0);
    }

    private static void addProduct(Scanner scanner) {
        viewProducts();
        System.out.print("Product Name: ");
        String name = scanner.nextLine();
        System.out.print("Price: ");
        double price = scanner.nextDouble();
        System.out.print("Quantity: ");
        int quantity = scanner.nextInt();
        scanner.nextLine();  // consume newline
        System.out.print("Description: ");
        String description = scanner.nextLine();

        Product product = new Product(name, price, quantity, description);
        ProductManagementService.addProduct(product);
        System.out.println("Product added successfully.");
    }

    private static void updateProduct(Scanner scanner) {
        viewProducts();
        System.out.print("Product ID to update: ");
        int productId = scanner.nextInt();
        scanner.nextLine();  // consume newline
        System.out.print("New Product Name: ");
        String name = scanner.nextLine();
        System.out.print("New Price: ");
        double price = scanner.nextDouble();
        System.out.print("New Quantity: ");
        int quantity = scanner.nextInt();
        scanner.nextLine();  // consume newline
        System.out.print("New Description: ");
        String description = scanner.nextLine();

        Product product = new Product(productId, name, price, quantity, description);
        ProductManagementService.updateProduct(product);
        System.out.println("Product updated successfully.");
    }

    private static void deleteProduct(Scanner scanner) {
        viewProducts();
        System.out.print("Product ID to delete: ");
        int productId = scanner.nextInt();
        scanner.nextLine();  // consume newline

        ProductManagementService.deleteProduct(productId);
        System.out.println("Product deleted successfully.");
    }

    private static void fulfillOrder(Scanner scanner) {

        System.out.print("Order ID to fulfill: ");
        int orderId = scanner.nextInt();
        scanner.nextLine();  // consume newline

        OrderManagementService.updateOrderStatus(orderId, "Shipped");
        System.out.println("Order fulfilled successfully.");
    }

    private static void viewProducts() {
        clearConsole();
        List<Product> products = ProductManagementService.getAllProducts();
        System.out.println("Available Products:");
        for (Product product : products) {
            System.out.println(product.getId() + ". " + product.getName() + " - RM" + product.getPrice() + " (" + product.getQuantity() + " available)");
        }
        System.out.println();
    }

    private static void orderProduct(Customer customer, Scanner scanner) {
        if (ProductManagementService.hasProducts()) {
            viewProducts();
            System.out.print("Product ID to order: ");
            int productId = scanner.nextInt();
            System.out.print("Quantity: ");
            int quantity = scanner.nextInt();
            scanner.nextLine();  // consume newline

            Order order = new Order(customer.getId(), productId, quantity, "Pending");
            OrderManagementService.placeOrder(order);
            System.out.println("Order placed successfully.");
        }
    }

    private static void trackOrder(Customer customer) {
        clearConsole();
        List<Order> orders = OrderManagementService.getOrdersByUserId(customer.getId());
        System.out.println("Your Orders:");
        for (Order order : orders) {
            System.out.println(order.getId() + ". Product ID: " + order.getProductId() + " - Quantity: " + order.getQuantity() + " - Status: " + order.getStatus());
        }
        System.out.println();
    }

    // For admin
    private static void trackOrder() {
        clearConsole();
        List<Order> orders = OrderManagementService.getAllOrders();
        System.out.println("All Orders:");
        for (Order order : orders) {
            System.out.println(order.getId() + ". Product ID: " + order.getProductId() + " - Quantity: " + order.getQuantity() + " - Status: " + order.getStatus());
        }
        System.out.println();
    }

    public static boolean isValidName(String name) {
        if (name == null || name.length() < 3 || name.length() > 30) {
            return false;
        }
        return true;
    }

    public static boolean isValidUsername(String username) {
        if (username == null || username.length() < 3 || username.length() > 16) {
            return false;
        }
        return true;
    }

    public static boolean isValidPassword(String password) {
        if (password == null || password.length() < 6 || password.length() > 16) {
            return false;
        }
        return true;
    }

    public static void clearConsole() {
        for (int i = 0; i < 20; ++i) {
            System.out.println();
        }
    }


}
