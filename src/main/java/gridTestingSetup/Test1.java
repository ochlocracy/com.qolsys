package gridTestingSetup;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Scanner;



public class Test1 {

    public ArrayList<String> primary_udid = new ArrayList<String>();
    public ArrayList<String> transmitter_udid = new ArrayList<String>();

    public static Scanner scanner = new Scanner(System.in);


    @Test
    public void Test1   (){

        boolean quit = false;
        int choice = 0;
        printInstruction();
        while(!quit) {
            System.out.println("Enter your choice: ");
            choice = scanner.nextInt();
            //clear input buffer
            scanner.nextLine();
            switch (choice){
                case 0:
                    printInstruction();
                    break;
                case 1:
                    addUdidPrimary();
                    break;
                case 2:
                    addUdidTransmitter();
                    break;
                case 3:
                    quit = true;
                    break;
            }
        }
    }

    public ArrayList<String> addUdidPrimary() {
        primary_udid.add(scanner.nextLine());
        System.out.println(primary_udid);
        return primary_udid;
    }

    public ArrayList<String> addUdidTransmitter() {
        transmitter_udid.add(scanner.nextLine());
        System.out.println(transmitter_udid);
        return transmitter_udid;
    }

    public static void printInstruction() {
        System.out.println("\nPress ");
        System.out.println("\t 0 - To add primary panel udid");
        System.out.println("\t 1 - To add transmitter udid");
        System.out.println("\t 3 - To quit");
    }
}

