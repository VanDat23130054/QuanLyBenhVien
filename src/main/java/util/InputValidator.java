package util;

import java.util.Scanner;

public class InputValidator {
    private static Scanner scanner = new Scanner(System.in);

    public static int getIntInput(String prompt) {
        System.out.print(prompt);
        try {
            int value = Integer.parseInt(scanner.nextLine().trim());
            return value;
        } catch (NumberFormatException e) {
            System.err.println("Invalid input. Please enter a valid integer.");
            return getIntInput(prompt);
        }
    }

    public static String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    public static double getDoubleInput(String prompt) {
        System.out.print(prompt);
        try {
            double value = Double.parseDouble(scanner.nextLine().trim());
            return value;
        } catch (NumberFormatException e) {
            System.err.println("Invalid input. Please enter a valid number.");
            return getDoubleInput(prompt);
        }
    }

    public static boolean isEmailValid(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }

    public static boolean isPhoneValid(String phone) {
        return phone.matches("^\\d{10,11}$");
    }

    public static void closeScanner() {
        if (scanner != null) {
            scanner.close();
        }
    }
}
