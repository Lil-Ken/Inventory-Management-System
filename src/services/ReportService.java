package services;

import main.Main;
import models.Transaction;
import utils.FileHandler;
import utils.InputValidationUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.time.format.DateTimeFormatter;

public class ReportService {

    public static void generateUserReport(int userId) {
        List<Transaction> transactions = TransactionManagementService.getTransactionsByUserId(userId);

        if (transactions.isEmpty()) {
            System.out.println("No transactions found for user ID: " + userId);
            return;
        }

        // Header section
        System.out.println("==========================================================");
        System.out.printf("%38s\n", "Transaction Report");
        System.out.println("==========================================================\n");
        System.out.printf("User ID: %d\n", userId);
        System.out.println("----------------------------------------------------------");
        System.out.printf("| %-10s | %-15s | %-23s |\n", "Trans. ID", "Amount (RM)", "Timestamp");
        System.out.println("----------------------------------------------------------");

        // Transaction rows
        for (Transaction transaction : transactions) {
            System.out.printf("| %10d | %15.2f | %23s |\n",
                    transaction.getId(),
                    transaction.getAmount(),
                    transaction.getTimestamp());
        }

        System.out.println("----------------------------------------------------------");
        System.out.printf("\nTotal Transactions: %d\n", transactions.size());
        System.out.println("==========================================================");
    }


    public static void generateAllTransactionsReport() {
        List<Transaction> transactions = TransactionManagementService.getAllTransactions();

        if (transactions.isEmpty()) {
            System.out.println("No transactions found.");
            return;
        }

        // Header section
        System.out.println("========================================================================");
        System.out.printf("%47s\n", "All Transactions Report");
        System.out.println("========================================================================\n");

        System.out.println("------------------------------------------------------------------------");
        System.out.printf("| %-10s | %-15s | %-23s | %-11s |\n", "Trans. ID", "Amount (RM)", "Timestamp", "User ID");
        System.out.println("------------------------------------------------------------------------");

        // Transaction rows
        for (Transaction transaction : transactions) {
            System.out.printf("| %10d | %15.2f | %23s | %11d |\n",
                    transaction.getId(),
                    transaction.getAmount(),
                    transaction.getTimestamp(),
                    transaction.getUserId());
        }

        System.out.println("------------------------------------------------------------------------");
        System.out.printf("\nTotal Transactions: %d\n", transactions.size());
        System.out.println("===================================================================");
    }


    public static void saveReportToFile(String fileName, List<Transaction> transactions) {
        List<String> lines = new ArrayList<>();
        lines.add("=======================================================================");
        lines.add(String.format("%47s", "Transaction Report"));
        lines.add("=======================================================================");
        lines.add("");
        lines.add("-----------------------------------------------------------------------");
        lines.add(String.format("| %-10s | %-15s | %-23s | %-10s |", "Trans. ID", "Amount (RM)", "Timestamp", "User ID"));
        lines.add("-----------------------------------------------------------------------");

        for (Transaction transaction : transactions) {
            lines.add(String.format("| %10d | %15.2f | %23s | %10d |",
                    transaction.getId(),
                    transaction.getAmount(),
                    transaction.getTimestamp(),
                    transaction.getUserId()));
        }

        lines.add("-----------------------------------------------------------------------");
        lines.add(String.format("\nTotal Transactions: %d", transactions.size()));
        lines.add("=======================================================================");

        FileHandler.writeFile(fileName, lines);
        System.out.println("Report saved to " + fileName);
    }

    public static void askToSaveReport(List<Transaction> transactions) {
        Scanner scanner = new Scanner(System.in);
        if (transactions == null || transactions.isEmpty()) {
            System.out.println("No transactions available to save.");
            return;
        }

        System.out.print("\nWould you like to save the report to a file? (y/n): ");
        String saveOption = scanner.nextLine().trim().toLowerCase();

        if (saveOption.equals("yes") || saveOption.equals("y")) {
            System.out.print("Enter the file name to save the report (e.g. report.txt): ");
            String fileName = scanner.nextLine().trim();
            saveReportToFile(fileName, transactions);
        } else {
            System.out.println("Report not saved.");
        }
    }


    public static void trackSalesByDateRange() {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        final DateTimeFormatter dateOnlyFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        LocalDate startDate = InputValidationUtils.getValidatedStartDate();
        LocalDate endDate = InputValidationUtils.getValidatedEndDate(startDate);
        Main.clearConsole();

        List<Transaction> transactions = TransactionManagementService.getAllTransactions();
        List<Transaction> filteredTransactions = transactions.stream()
                .filter(t -> {
                    LocalDateTime transactionDate = LocalDateTime.parse(t.getTimestamp(), formatter);
                    return !transactionDate.toLocalDate().isBefore(startDate) && !transactionDate.toLocalDate().isAfter(endDate);
                })
                .collect(Collectors.toList());

        if (filteredTransactions.isEmpty()) {
            System.out.println("No transactions found in this date range.");
        } else {
            System.out.println("==========================================================");
            System.out.printf("%42s\n", "Transactions by Date Range");
            System.out.println("==========================================================\n");

            System.out.printf("Transactions between %s and %s:\n", startDate.format(dateOnlyFormatter), endDate.format(dateOnlyFormatter));
            displayTransactions(filteredTransactions);
        }
    }


    public static void displayTransactions(List<Transaction> transactions) {
        System.out.println("----------------------------------------------------------");
        System.out.printf("| %-10s | %-23s | %-15s |\n", "ID", "Timestamp", "Amount (RM)");
        System.out.println("----------------------------------------------------------");

        for (Transaction transaction : transactions) {
            System.out.printf("| %10d | %23s | %15.2f |\n",
                    transaction.getId(),
                    transaction.getTimestamp(),
                    transaction.getAmount());
        }

        System.out.println("----------------------------------------------------------");
        System.out.printf("\nTotal Transactions: %d\n", transactions.size());
        System.out.println("==========================================================");
    }


}
