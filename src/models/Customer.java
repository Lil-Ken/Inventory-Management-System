package models;

public class Customer extends User {

    public Customer(String username, String password) {
        super( username, password);
    }

    public Customer(int id, String username, String password) {
        super(id, username, password);
    }

}
