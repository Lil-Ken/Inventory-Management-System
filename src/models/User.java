package models;

public abstract class User {
    protected static int numUser = 0;
    protected int id;
    protected String name;
    protected String username;
    protected String password;

    public User(String name, String username, String password) {
        this.id = ++numUser;
        this.name = name;
        this.username = username;
        this.password = password;
    }

    public User(int id, String name, String username, String password) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public abstract boolean login(String username, String password);
    public abstract boolean register();
}
