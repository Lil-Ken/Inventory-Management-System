package models;

import services.UserManagementService;

public abstract class User {
    protected static int numUser = 0;
    protected int id;
    protected String username;
    protected String password;

    public User(String username, String password) {
        this.id = UserManagementService.getNewUserId();
        this.username = username;
        this.password = password;
    }

    public User(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

}
