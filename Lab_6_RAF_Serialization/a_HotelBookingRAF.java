/*
Design a Java hotel booking system using `RandomAccessFile` with fixed-length records containing 
Room Number (int), Room Type (20-char String), Price per Night (double), and Booking Status (boolean).
Use `seek()` to navigate directly to any record by position without sequential reading. 
Implement three operations: add new rooms, display a specific room's details by room number,
and update booking status (book/vacate). Ensure data is read and written in identical sequence/format
for consistency. Close the file after every operation.
*/

import java.io.*;
import java.util.Scanner;

public class a_HotelBookingRAF {

    static final int ROOM_TYPE_LENGTH = 20;
    static final int RECORD_SIZE = 4 + (ROOM_TYPE_LENGTH * 2) + 8 + 1;
    static final String FILE_NAME = "hotel_rooms.dat";

    static void writeRoom(RandomAccessFile raf, int roomNumber, String roomType,
                          double pricePerNight, boolean isBooked) throws IOException {
        raf.writeInt(roomNumber);
        StringBuilder sb = new StringBuilder(roomType);
        while (sb.length() < ROOM_TYPE_LENGTH) sb.append(' ');
        String fixed = sb.substring(0, ROOM_TYPE_LENGTH);
        raf.writeChars(fixed);
        raf.writeDouble(pricePerNight);
        raf.writeBoolean(isBooked);
    }

    static void readAndPrintRoom(RandomAccessFile raf) throws IOException {
        int roomNumber = raf.readInt();
        StringBuilder roomType = new StringBuilder();
        for (int i = 0; i < ROOM_TYPE_LENGTH; i++) {
            roomType.append(raf.readChar());
        }
        double price = raf.readDouble();
        boolean booked = raf.readBoolean();

        System.out.println(" ");
        System.out.println("Room Number   : " + roomNumber);
        System.out.println("Room Type     : " + roomType.toString().trim());
        System.out.println("Price/Night   : Rs. " + price);
        System.out.println("Booking Status: " + (booked ? "Booked" : "Available"));
        System.out.println(" ");
    }

    static int getTotalRecords(RandomAccessFile raf) throws IOException {
        return (int) (raf.length() / RECORD_SIZE);
    }

    static int findRoomIndex(RandomAccessFile raf, int roomNumber) throws IOException {
        int total = getTotalRecords(raf);
        for (int i = 0; i < total; i++) {
            raf.seek((long) i * RECORD_SIZE);
            int rn = raf.readInt();
            if (rn == roomNumber) return i;
        }
        return -1;
    }

    // Add new room records
    static void addRoom(int roomNumber, String roomType, double price, boolean isBooked) {
        try (RandomAccessFile raf = new RandomAccessFile(FILE_NAME, "rw")) {
            int index = findRoomIndex(raf, roomNumber);
            if (index != -1) {
                System.out.println("Room " + roomNumber + " already exists.");
                return;
            }
            raf.seek(raf.length());
            writeRoom(raf, roomNumber, roomType, price, isBooked);
            System.out.println("Room " + roomNumber + " added successfully.");
        } catch (IOException e) {
            System.out.println("Error adding room: " + e.getMessage());
        }
    }

    // Display details of a specific room using its room number
    static void displayRoom(int roomNumber) {
        try (RandomAccessFile raf = new RandomAccessFile(FILE_NAME, "r")) {
            int index = findRoomIndex(raf, roomNumber);
            if (index == -1) {
                System.out.println("Room " + roomNumber + " not found.");
                return;
            }
            // Use the seek() method to jump directly to the position of a room record
            raf.seek((long) index * RECORD_SIZE);
            System.out.println("Details for Room " + roomNumber + ":");
            readAndPrintRoom(raf);
        } catch (IOException e) {
            System.out.println("Error displaying room: " + e.getMessage());
        }
    }

    // Update booking status (book / vacate a room)
    static void updateBookingStatus(int roomNumber, boolean book) {
        try (RandomAccessFile raf = new RandomAccessFile(FILE_NAME, "rw")) {
            int index = findRoomIndex(raf, roomNumber);
            if (index == -1) {
                System.out.println("Room " + roomNumber + " not found.");
                return;
            }
            // Use the seek() method to jump directly to the position of a room record
            long statusOffset = (long) index * RECORD_SIZE + 4 + (ROOM_TYPE_LENGTH * 2) + 8;
            raf.seek(statusOffset);
            raf.writeBoolean(book);
            System.out.println("Room " + roomNumber + " is now " + (book ? "Booked" : "Vacated") + ".");
        } catch (IOException e) {
            System.out.println("Error updating booking status: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n - Hotel Room Booking System (RandomAccessFile) - ");
            System.out.println("1. Add Room");
            System.out.println("2. Display Room Details");
            System.out.println("3. Book a Room");
            System.out.println("4. Vacate a Room");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter Room Number: ");
                    int rn = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter Room Type (e.g., Single, Double, Suite): ");
                    String rt = sc.nextLine();
                    System.out.print("Enter Price Per Night: ");
                    double price = sc.nextDouble();
                    System.out.print("Is room currently booked? (true/false): ");
                    boolean booked = sc.nextBoolean();
                    addRoom(rn, rt, price, booked);
                    break;

                case 2:
                    System.out.print("Enter Room Number to display: ");
                    displayRoom(sc.nextInt());
                    break;

                case 3:
                    System.out.print("Enter Room Number to book: ");
                    updateBookingStatus(sc.nextInt(), true);
                    break;

                case 4:
                    System.out.print("Enter Room Number to vacate: ");
                    updateBookingStatus(sc.nextInt(), false);
                    break;

                case 5:
                    System.out.println("Exiting...");
                    break;

                default:
                    System.out.println("Invalid choice. Try again.");
            }
        } while (choice != 5);

        sc.close();
    }
}