/*
Design a Java hotel booking system using object serialization where a `Room` class 
(implementing `Serializable`) stores Room Number, Room Type, Price per Night, Booking Status,
and Guest Name. Serialize room objects to a file for persistent storage and deserialize them
to display all rooms or search by room number. Support booking status updates by deserializing 
all objects, modifying the target room, and re-serializing the entire list back to the file. 
Handle `IOException` and `ClassNotFoundException` appropriately throughout.
*/

import java.io.*;
import java.util.*;

// Create a Room class that implements Serializable
class Room implements Serializable {
    private static final long serialVersionUID = 1L;

    private int roomNumber;
    private String roomType;
    private double pricePerNight;
    private boolean isBooked;
    private String guestName;

    public Room(int roomNumber, String roomType, double pricePerNight,
                boolean isBooked, String guestName) {
        this.roomNumber    = roomNumber;
        this.roomType      = roomType;
        this.pricePerNight = pricePerNight;
        this.isBooked      = isBooked;
        this.guestName     = guestName;
    }

    public int getRoomNumber()       { return roomNumber; }
    public String getRoomType()      { return roomType; }
    public double getPricePerNight() { return pricePerNight; }
    public boolean isBooked()        { return isBooked; }
    public String getGuestName()     { return guestName; }

    public void setBooked(boolean booked)      { this.isBooked   = booked; }
    public void setGuestName(String guestName) { this.guestName  = guestName; }

    @Override
    public String toString() {
        return "Room Number   : " + roomNumber
             + "\nRoom Type     : " + roomType
             + "\nPrice/Night   : Rs. " + pricePerNight
             + "\nBooking Status: " + (isBooked ? "Booked" : "Available")
             + "\nGuest Name    : " + (guestName == null || guestName.isEmpty() ? "N/A" : guestName);
    }
}

public class b_HotelBookingSerialization {

    static final String FILE_NAME = "hotel_rooms_ser.dat";

    // Deserialize objects from the file
    @SuppressWarnings("unchecked")
    static List<Room> loadRooms() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (List<Room>) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO error during deserialization: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("Class not found during deserialization: " + e.getMessage());
        }
        return new ArrayList<>();
    }

    // Serialize room booking objects and store them in a file
    static void saveRooms(List<Room> rooms) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(rooms);
        } catch (IOException e) {
            System.out.println("IO error during serialization: " + e.getMessage());
        }
    }

    static void addRoom(int roomNumber, String roomType, double price,
                        boolean isBooked, String guestName) {
        List<Room> rooms = loadRooms();
        for (Room r : rooms) {
            if (r.getRoomNumber() == roomNumber) {
                System.out.println("Room " + roomNumber + " already exists.");
                return;
            }
        }
        rooms.add(new Room(roomNumber, roomType, price, isBooked, guestName));
        saveRooms(rooms);
        System.out.println("Room " + roomNumber + " added successfully.");
    }

    // Display all room details
    static void displayAllRooms() {
        List<Room> rooms = loadRooms();
        if (rooms.isEmpty()) {
            System.out.println("No rooms found.");
            return;
        }
        System.out.println("\n===== All Room Details =====");
        for (Room r : rooms) {
            System.out.println(" ");
            System.out.println(r);
            System.out.println(" ");
        }
    }

    // Search for a room using room number
    static void searchRoom(int roomNumber) {
        List<Room> rooms = loadRooms();
        for (Room r : rooms) {
            if (r.getRoomNumber() == roomNumber) {
                System.out.println("\nRoom found:");
                System.out.println(" ");
                System.out.println(r);
                System.out.println(" ");
                return;
            }
        }
        System.out.println("Room " + roomNumber + " not found.");
    }

    // Allow updating booking status by:
    // Deserializing the objects -> Modifying the required room object -> Re-serializing back to file
    static void updateBookingStatus(int roomNumber, boolean book, String guestName) {
        List<Room> rooms = loadRooms();
        boolean found = false;
        for (Room r : rooms) {
            if (r.getRoomNumber() == roomNumber) {
                r.setBooked(book);
                r.setGuestName(book ? guestName : "");
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Room " + roomNumber + " not found.");
            return;
        }
        saveRooms(rooms);
        System.out.println("Room " + roomNumber + " is now " + (book ? "Booked" : "Vacated") + ".");
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n - Hotel Room Booking System (Serialization) - ");
            System.out.println("1. Add Room");
            System.out.println("2. Display All Rooms");
            System.out.println("3. Search Room by Room Number");
            System.out.println("4. Book a Room");
            System.out.println("5. Vacate a Room");
            System.out.println("6. Exit");
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
                    sc.nextLine();
                    String guest = "";
                    if (booked) {
                        System.out.print("Enter Guest Name: ");
                        guest = sc.nextLine();
                    }
                    addRoom(rn, rt, price, booked, guest);
                    break;

                case 2:
                    displayAllRooms();
                    break;

                case 3:
                    System.out.print("Enter Room Number to search: ");
                    searchRoom(sc.nextInt());
                    break;

                case 4:
                    System.out.print("Enter Room Number to book: ");
                    int bookRn = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter Guest Name: ");
                    String guestName = sc.nextLine();
                    updateBookingStatus(bookRn, true, guestName);
                    break;

                case 5:
                    System.out.print("Enter Room Number to vacate: ");
                    updateBookingStatus(sc.nextInt(), false, "");
                    break;

                case 6:
                    System.out.println("Exiting...");
                    break;

                default:
                    System.out.println("Invalid choice. Try again.");
            }
        } while (choice != 6);

        sc.close();
    }
}