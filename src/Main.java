import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by zcabez on 7/10/17.
 */
public class Main {
    private static int end = 0;
    private static int start = 0;

    private static int mode;
    private static ArrayList<Integer> choices = new ArrayList<Integer>();
    public static void main(String[] args) {

        choices.add(2);
        choices.add(1);

        Scanner scanner = new Scanner(System.in);

        System.out.println("Choose scan mode: ");
        System.out.println("1. Full Scan");
        System.out.println("2. Partial Scan");
        System.out.println("");

        System.out.print("Choose mode : ");

        int userChoice = 0;

        while (!choices.contains(userChoice)) {
            while (!scanner.hasNextInt()) {
                System.out.println("Please choose mode !!");
                System.out.print("Choose mode : ");
                scanner.next();
            }

            userChoice = scanner.nextInt();
            if (!choices.contains(userChoice)){
                System.out.println("Please choose available mode");
                System.out.print("Choose mode : ");
            }
        }

        System.out.println("");
        if (userChoice == 2) {
            while (end - start <= 0) {
                System.out.print("start index : ");
                while (!scanner.hasNextInt()) {
                    System.out.println("Input starting index !!");
                    System.out.print("start index : ");
                    scanner.next();
                }
                start = scanner.nextInt();

                System.out.print("end index : ");
                while (!scanner.hasNextInt()) {
                    System.out.println("Input end index !!");
                    System.out.print("end index : ");
                    scanner.next();
                }
                end = scanner.nextInt();

                if (end - start <= 0) {
                    System.out.println("Unreasonable range. Try again");
                    System.out.println("");
                }
            }
        }

        System.out.println("");
        switch (userChoice) {
            case 1:
                System.out.println("Starting full scan......");
                FullScan fullScan = new FullScan();
                try {
                    fullScan.initScan();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case 2:
                System.out.println("Starting scan from index " + start + " to " + end + ".....");
                PartialScan partialScan = new PartialScan(start, end);
                try {
                    partialScan.initScan();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }





    }

}
