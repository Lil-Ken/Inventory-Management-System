import java.util.Scanner;

public class menu {
    public static void menu(){
        System.out.println("Welcome to FCZJ-CKJW Phone Accessory");
        System.out.println("0. Exit");
        System.out.println("1. Login Account");
        System.out.println("2. Register a Account");

        Scanner sc = new Scanner(System.in);
        int choice = sc.nextInt();
        switch(choice) {
            case 0:
                System.exit(0);

        }
    }
}
