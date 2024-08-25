package utils;

import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class InputValidationUtils {

    private static final Scanner scanner = new Scanner(System.in);
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    // Validates integer input within a specific range
    public static int getValidatedInt(int min, int max) {
        int input;
        while (true) {
            try {
                input = Integer.parseInt(scanner.nextLine());
                if (input >= min && input <= max) {
                    break;
                } else {
                    System.out.print("Please enter a number between " + min + " and " + max + ":");
                }
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a valid number:");
            }
        }
        return input;
    }

    // Validates double input with optional range
    public static double getValidatedDouble(double min, double max) {
        double input;
        while (true) {
            try {
                input = Double.parseDouble(scanner.nextLine());
                if (input >= min && input <= max) {
                    break;
                } else {
                    System.out.print("Please enter a number between " + min + " and " + max + ":");
                }
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a valid decimal number:");
            }
        }
        return input;
    }

    // Validates a non-empty string input
    public static String getValidatedString() {
        String input;
        while (true) {
            input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                break;
            } else {
                System.out.print("Input cannot be empty. Please try again:");
            }
        }
        return input;
    }

    // Validates a string input with minimum and maximum length constraints
    public static String getValidatedString(int minLength, int maxLength) {
        String input;
        while (true) {
            input = scanner.nextLine().trim();
            if (input.length() >= minLength && input.length() <= maxLength) {
                break;
            } else {
                System.out.printf("Input must be between %d and %d characters. Please try again: ", minLength, maxLength);
            }
        }
        return input;
    }

    // Validates yes/no input
    public static boolean getValidatedYesNo() {
        String input;
        while (true) {
            input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("y") || input.equals("yes")) {
                return true;
            } else if (input.equals("n") || input.equals("no")) {
                return false;
            } else {
                System.out.print("Invalid input. Please enter 'y' or 'n':");
            }
        }
    }

    // Validates if the username is in a specific format
    public static boolean isValidUsername(String username) {
        return username != null && username.length() >= 3 && username.length() <= 16;
    }

    // Validates if the password is in a specific format
    public static boolean isValidPassword(String password) {
        return password != null && password.length() >= 6 && password.length() <= 16;
    }

    // Gets a validated username with feedback
    public static String getValidatedUsername() {
        String username;
        while (true) {
            username = getValidatedString();
            if (isValidUsername(username)) {
                break;
            } else {
                System.out.print("Invalid format (3 - 16 characters). Please try again:");
            }
        }
        return username;
    }

    // Gets a validated password with feedback
    public static String getValidatedPassword() {
        String password;
        while (true) {
            password = getValidatedString();
            if (isValidPassword(password)) {
                break;
            } else {
                System.out.print("Invalid format (6 - 16 characters). Please try again:");
            }
        }
        return password;
    }

    public static LocalDate getValidatedStartDate() {
        LocalDate startDate;
        while (true) {
            System.out.print("Enter start date (dd/MM/yyyy): ");
            String input = scanner.nextLine();
            try {
                startDate = LocalDate.parse(input, formatter);
                break;
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please enter a valid date in the format dd/MM/yyyy.");
            }
        }
        return startDate;
    }

    public static LocalDate getValidatedEndDate(LocalDate startDate) {
        LocalDate endDate;
        while (true) {
            System.out.print("Enter end date (dd/MM/yyyy): ");
            String input = scanner.nextLine();
            try {
                endDate = LocalDate.parse(input, formatter);
                if (!endDate.isBefore(startDate)) {
                    break;
                } else {
                    System.out.println("End date cannot be before the start date. Please enter a valid end date.");
                }
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please enter a valid date in the format dd/MM/yyyy.");
            }
        }
        return endDate;
    }
}
