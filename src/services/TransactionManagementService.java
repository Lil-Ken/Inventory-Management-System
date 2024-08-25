package services;

import models.Transaction;
import utils.FileHandler;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TransactionManagementService {

    private static final String TRANSACTION_FILE = "resources/transactions.txt";
    // Updated to use "dd/MM/yyyy HH:mm:ss" format
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    public static void recordTransaction(Transaction transaction) {
        // Format the timestamp according to the new pattern
        String newTransactionLine = transaction.getId() + ","
                + transaction.getUserId() + ","
                + transaction.getAmount() + ","
                + transaction.getTimestamp();  // Timestamp is already formatted correctly in Transaction class
        List<String> transactions = FileHandler.readFile(TRANSACTION_FILE);
        transactions.add(newTransactionLine);
        FileHandler.writeFile(TRANSACTION_FILE, transactions);
    }

    public static List<Transaction> getTransactionsByUserId(int userId) {
        List<String> transactionsData = FileHandler.readFile(TRANSACTION_FILE);
        List<Transaction> transactions = new ArrayList<>();
        for (String transactionLine : transactionsData) {
            String[] transactionInfo = transactionLine.split(",");
            if (Integer.parseInt(transactionInfo[1]) == userId) {
                Transaction transaction = new Transaction(
                        Integer.parseInt(transactionInfo[0]),
                        Integer.parseInt(transactionInfo[1]),
                        Double.parseDouble(transactionInfo[2]),
                        transactionInfo[3]  // Timestamp string
                );
                transactions.add(transaction);
            }
        }
        return transactions;
    }

    public static List<Transaction> getAllTransactions() {
        List<String> transactionsData = FileHandler.readFile(TRANSACTION_FILE);
        List<Transaction> transactions = new ArrayList<>();
        for (String transactionLine : transactionsData) {
            String[] transactionInfo = transactionLine.split(",");
            Transaction transaction = new Transaction(
                    Integer.parseInt(transactionInfo[0]),
                    Integer.parseInt(transactionInfo[1]),
                    Double.parseDouble(transactionInfo[2]),
                    transactionInfo[3]  // Timestamp string
            );
            transactions.add(transaction);
        }
        return transactions;
    }

    public static int getNewTransactionId() {
        List<String> transactions = FileHandler.readFile(TRANSACTION_FILE);
        int newID = 0;
        for (String transactionLine : transactions) {
            String[] transactionInfo = transactionLine.split(",");
            int currentId = Integer.parseInt(transactionInfo[0]);
            if (currentId > newID) {
                newID = currentId;
            }
        }
        return ++newID;
    }
}
