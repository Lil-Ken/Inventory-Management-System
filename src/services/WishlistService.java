package services;

import models.Product;
import utils.FileHandler;

import java.util.ArrayList;
import java.util.List;

public class WishlistService {

    private static final String WISHLIST_FILE = "resources/wishlist.txt";

    // Add a product to the user's wishlist
    public static void addProductToWishlist(int userId, Product product) {
        String newWishlistItem = userId + "," + product.getId();
        List<String> wishlist = FileHandler.readFile(WISHLIST_FILE);
        wishlist.add(newWishlistItem);
        FileHandler.writeFile(WISHLIST_FILE, wishlist);
    }

    // Remove a product from the user's wishlist
    public static void removeProductFromWishlist(int userId, int productId) {
        List<String> wishlist = FileHandler.readFile(WISHLIST_FILE);
        wishlist.removeIf(wishlistItem -> {
            String[] wishlistInfo = wishlistItem.split(",");
            return Integer.parseInt(wishlistInfo[0]) == userId && Integer.parseInt(wishlistInfo[1]) == productId;
        });
        FileHandler.writeFile(WISHLIST_FILE, wishlist);
    }

    // Get all products in the user's wishlist
    public static List<Product> getWishlistByUserId(int userId) {
        List<String> wishlistData = FileHandler.readFile(WISHLIST_FILE);
        List<Product> wishlist = new ArrayList<>();

        for (String wishlistItem : wishlistData) {
            String[] wishlistInfo = wishlistItem.split(",");
            if (Integer.parseInt(wishlistInfo[0]) == userId) {
                int productId = Integer.parseInt(wishlistInfo[1]);
                Product product = ProductManagementService.getProductById(productId);
                if (product != null) {
                    wishlist.add(product);
                }
            }
        }
        return wishlist;
    }

    // Check if a product is already in the user's wishlist
    public static boolean isProductInWishlist(int userId, int productId) {
        List<String> wishlist = FileHandler.readFile(WISHLIST_FILE);
        return wishlist.stream().anyMatch(wishlistItem -> {
            String[] wishlistInfo = wishlistItem.split(",");
            return Integer.parseInt(wishlistInfo[0]) == userId && Integer.parseInt(wishlistInfo[1]) == productId;
        });
    }
}
