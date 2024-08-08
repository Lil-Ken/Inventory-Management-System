package models;

public class VirtualAccount {
    private int userId;
    private double balance;

    public VirtualAccount(int userId, double balance) {
        this.userId = userId;
        this.balance = balance;
    }

    public void transferFunds(double amount) {
        if (balance >= amount) {
            balance -= amount;
        } else {
            System.out.println("Insufficient funds.");
        }
    }

    public double checkBalance() {
        return balance;
    }
}
