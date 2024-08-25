package models;

public class Admin extends User {

    public Admin(String username, String password) {
        super(username, password);
    }

    public Admin(int id, String username, String password) {
        super(id, username, password);
    }

}
