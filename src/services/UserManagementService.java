package services;

import models.*;
import utils.FileHandler;

import java.util.List;

public class UserManagementService {

    private static final String USER_FILE = "resources/users.txt";

    public static User login(String username, String password) {
        List<String> users = FileHandler.readFile(USER_FILE);
        for (String userLine : users) {
            String[] userInfo = userLine.split(",");
            if (userInfo[1].equals(username) && userInfo[2].equals(password)) {
                if (userInfo[3].equals("admin")) {
                    return new Admin(Integer.parseInt(userInfo[0]), userInfo[1], userInfo[2]);
                } else {
                    return new Customer(Integer.parseInt(userInfo[0]), userInfo[1], userInfo[2]);
                }
            }
        }
        return null;
    }

    public static boolean register(User user) {
        List<String> users = FileHandler.readFile(USER_FILE);
        for (String userLine : users) {
            String[] userInfo = userLine.split(",");
            if (userInfo[1].equals(user.getUsername())) {
                return false;  // Username already exists
            }
        }
        String userType = user instanceof Admin ? "admin" : "customer";
        String newUserLine = user.getId() + "," + user.getUsername() + "," + user.getPassword() + "," + userType;
        users.add(newUserLine);
        FileHandler.writeFile(USER_FILE, users);
        return true;
    }

    public static int getNewUserId() {
        List<String> users = FileHandler.readFile(USER_FILE);
        int newID = 0;
        for (String userLine : users) {
            String[] userInfo = userLine.split(",");
            int currentId = Integer.parseInt(userInfo[0]);
            if (currentId > newID) {
                newID = currentId;
            }
        }
        return ++newID;
    }

}
