package services;

import models.*;
import utils.FileHandler;

import java.util.ArrayList;
import java.util.List;

public class UserManagementService {

    private static final String USER_FILE = "resources/users.txt";

    public static User login(String username, String password) {
        List<String> users = FileHandler.readFile(USER_FILE);
        for (String userLine : users) {
            String[] userInfo = userLine.split(",");
            if (userInfo[2].equals(username) && userInfo[3].equals(password)) {
                if (userInfo[4].equals("admin")) {
                    return new Admin(Integer.parseInt(userInfo[0]), userInfo[1], userInfo[2], userInfo[3]);
                } else {
                    return new Customer(Integer.parseInt(userInfo[0]), userInfo[1], userInfo[2], userInfo[3]);
                }
            }
        }
        return null;
    }

    public static boolean register(User user) {
        List<String> users = FileHandler.readFile(USER_FILE);
        for (String userLine : users) {
            String[] userInfo = userLine.split(",");
            if (userInfo[2].equals(user.getUsername())) {
                return false;  // Username already exists
            }
        }
        String userType = user instanceof Admin ? "admin" : "customer";
        String newUserLine = user.getId() + "," + user.getName() + "," + user.getUsername() + "," + user.getPassword() + "," + userType;
        users.add(newUserLine);
        FileHandler.writeFile(USER_FILE, users);
        return true;
    }
}
