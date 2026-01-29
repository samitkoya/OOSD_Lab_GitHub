/*
Design and implement a Java application to manage room tariff details in a Hotel 
Management System using Java enumerations (enum). The application should 
demonstrate the use of enum constants, enum constructors, and enum methods. 

i. Define an enum named RoomType to represent different types of hotel rooms such as 
STANDARD, DELUXE, and SUITE. Each enum constant should be associated with a base 
tariff value using an enum constructor. The enum should also include methods to return 
the base tariff and to calculate the total room cost based on the number of days stayed. 

ii. Create a main class to select a room type, specify the number of days of stay, and 
compute the total room tariff by invoking the enum methods. The application should 
clearly illustrate how enum constructors are used to initialize constant-specific data and 
how enum methods operate on that data. 
*/

import java.util.Scanner;

enum RoomType {

    STANDARD(2000),
    DELUXE(3500),
    SUITE(6000);

    private double baseTariff;

    RoomType(double baseTariff) {
        this.baseTariff = baseTariff;
    }

    public double getBaseTariff() {
        return baseTariff;
    }
    public double calculateTotalCost(int days) {
        return baseTariff * days;
    }
}

public class b_HotelManagement {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("Select Room Type:");
        System.out.println("1. STANDARD");
        System.out.println("2. DELUXE");
        System.out.println("3. SUITE");
        System.out.print("Enter choice (1-3): ");
        int choice = sc.nextInt();

        System.out.print("Enter number of days stayed: ");
        int days = sc.nextInt();

        RoomType room;

        switch (choice) {
            case 1:
                room = RoomType.STANDARD;
                break;
            case 2:
                room = RoomType.DELUXE;
                break;
            case 3:
                room = RoomType.SUITE;
                break;
            default:
                System.out.println("Invalid room selection!");
                return;
        }

        double totalCost = room.calculateTotalCost(days);

        System.out.println("\n Hotel Room Tariff :-");
        System.out.println("Room Type       : " + room);
        System.out.println("Base Tariff     : " + room.getBaseTariff());
        System.out.println("Days Stayed     : " + days);
        System.out.println("");
        System.out.println("Total Cost      : " + totalCost);
    }
}

