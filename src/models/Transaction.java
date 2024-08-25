package models;

import services.TransactionManagementService;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {
    private int id;
    private int userId;
    private double amount;
    private LocalDateTime timestamp;

    // Update the DateTimeFormatter to the desired format
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    // Constructor for new transactions
    public Transaction(int userId, double amount, String timestamp) {
        this.id = TransactionManagementService.getNewTransactionId();
        this.userId = userId;
        this.amount = amount;
        this.timestamp = LocalDateTime.parse(timestamp, formatter);
    }

    // Constructor for loading existing transactions
    public Transaction(int id, int userId, double amount, String timestamp) {
        this.id = id;
        this.userId = userId;
        this.amount = amount;
        this.timestamp = LocalDateTime.parse(timestamp, formatter);
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public double getAmount() {
        return amount;
    }

    // This method returns the timestamp as a formatted string
    public String getTimestamp() {
        return timestamp.format(formatter);
    }

    // This method returns the raw LocalDateTime object
    public LocalDateTime getTimestampAsDateTime() {
        return timestamp;
    }
}
