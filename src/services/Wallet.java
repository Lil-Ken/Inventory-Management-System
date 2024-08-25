package services;

import utils.FileHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Wallet {

    private static final String WALLET_FILE = "resources/wallets.txt";
    private Map<Integer, Double> balances;

    public Wallet() {
        balances = new HashMap<>();
        loadBalances();
    }

    // Load balances from file into the map
    private void loadBalances() {
        List<String> walletData = FileHandler.readFile(WALLET_FILE);
        for (String line : walletData) {
            String[] walletInfo = line.split(",");
            int userId = Integer.parseInt(walletInfo[0]);
            double balance = Double.parseDouble(walletInfo[1]);
            balances.put(userId, balance);
        }
    }

    // Save balances from the map to the file
    private void saveBalances() {
        List<String> walletData = balances.entrySet().stream()
                .map(entry -> entry.getKey() + "," + entry.getValue())
                .toList();
        FileHandler.writeFile(WALLET_FILE, walletData);
    }

    // Get the balance for a specific user
    public double getBalance(int userId) {
        return balances.getOrDefault(userId, 0.0);
    }

    // Top up the wallet balance for a specific user
    public void topUp(int userId, double amount) {
        if (amount <= 0) {
            System.out.println("Amount must be greater than zero.");
            return;
        }
        double newBalance = balances.getOrDefault(userId, 0.0) + amount;
        balances.put(userId, newBalance);
        saveBalances();
        System.out.println("Top-up successful.");
    }

    // Deduct the wallet balance for a specific user
    public boolean deduct(int userId, double amount) {
        double currentBalance = balances.getOrDefault(userId, 0.0);
        if (amount > currentBalance) {
            System.out.println("Insufficient balance.");
            return false;
        }
        double newBalance = currentBalance - amount;
        balances.put(userId, newBalance);
        saveBalances();
        System.out.println("Payment successful. Remaining balance: RM" + newBalance);
        return true;
    }

    // Display the current balance for a specific user
    public void displayBalance(int userId) {
        double balance = balances.getOrDefault(userId, 0.0);
        System.out.println("Current balance: RM" + balance);
    }
}
