import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Room implements Serializable {

    private static final long serialVersionUID = 1L;

    int roomNumber;
    String roomType;
    double pricePerNight;
    boolean isBooked;
    String guestName;

    Room(int roomNumber, String roomType, double pricePerNight, boolean isBooked, String guestName) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.pricePerNight = pricePerNight;
        this.isBooked = isBooked;
        this.guestName = guestName;
    }

    @Override
    public String toString() {
        return "Room Number   : " + roomNumber +
                "\nRoom Type     : " + roomType +
                "\nPrice/Night   : Rs." + pricePerNight +
                "\nBooking Status: " + (isBooked ? "Booked" : "Available") +
                "\nGuest Name    : " + (guestName.isEmpty() ? "N/A" : guestName);
    }
}

public class b_HotelBookingSerialization {

    static final String FILE_NAME = "hotel_rooms_serialized.dat";

    static void saveRooms(List<Room> rooms) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            for (Room room : rooms) {
                oos.writeObject(room);
            }
            System.out.println("Rooms saved to file.");
        } catch (IOException e) {
            System.err.println("Error saving: " + e.getMessage());
        }
    }

    static List<Room> loadRooms() {
        List<Room> rooms = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            while (true) {
                try {
                    rooms.add((Room) ois.readObject());
                } catch (EOFException e) {
                    break;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading: " + e.getMessage());
        }
        return rooms;
    }

    static void addRoom(Scanner sc) {
        System.out.print("Enter Room Number   : ");
        int roomNumber = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter Room Type     : ");
        String roomType = sc.nextLine();
        System.out.print("Enter Price/Night   : ");
        double price = sc.nextDouble();
        sc.nextLine();
        System.out.print("Is Booked (yes/no)  : ");
        boolean isBooked = sc.nextLine().equalsIgnoreCase("yes");
        String guestName = "";
        if (isBooked) {
            System.out.print("Enter Guest Name    : ");
            guestName = sc.nextLine();
        }

        List<Room> rooms = loadRooms();
        rooms.add(new Room(roomNumber, roomType, price, isBooked, guestName));
        saveRooms(rooms);
        System.out.println("Room " + roomNumber + " added successfully.");
    }

    static void searchRoom(Scanner sc) {
        System.out.print("Enter Room Number to search: ");
        int roomNumber = sc.nextInt();
        List<Room> rooms = loadRooms();
        boolean found = false;

        for (Room room : rooms) {
            if (room.roomNumber == roomNumber) {
                System.out.println("\nRoom Found :-\n" + room);
                found = true;
                break;
            }
        }

        if (!found)
            System.out.println("Room " + roomNumber + " not found.");
    }

    static void updateBookingStatus(Scanner sc) {
        System.out.print("Enter Room Number to update: ");
        int roomNumber = sc.nextInt();
        sc.nextLine();
        System.out.print("New Status (yes = Booked, no = Available): ");
        boolean newStatus = sc.nextLine().equalsIgnoreCase("yes");
        String guestName = "";
        if (newStatus) {
            System.out.print("Enter Guest Name: ");
            guestName = sc.nextLine();
        }

        List<Room> rooms = loadRooms();
        boolean found = false;

        for (Room room : rooms) {
            if (room.roomNumber == roomNumber) {
                room.isBooked = newStatus;
                room.guestName = newStatus ? guestName : "";
                found = true;
                break;
            }
        }

        if (found) {
            saveRooms(rooms);
            System.out.println("Room " + roomNumber + " status updated to: " + (newStatus ? "Booked" : "Available"));
        } else {
            System.out.println("Room " + roomNumber + " not found.");
        }
    }

    static void displayAllRooms() {
        List<Room> rooms = loadRooms();

        if (rooms.isEmpty()) {
            System.out.println("No rooms available.");
            return;
        }

        System.out.println("\n========== All Room Details ==========");
        for (Room room : rooms) {
            System.out.println(room);
            System.out.println("--------------------------------------");
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("=== Hotel Room Booking System (Serialization) ===");

        boolean running = true;
        while (running) {
            System.out.println("\n1. Add Room");
            System.out.println("2. Search Room");
            System.out.println("3. Update Booking Status");
            System.out.println("4. View All Rooms");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1 -> addRoom(sc);
                case 2 -> searchRoom(sc);
                case 3 -> updateBookingStatus(sc);
                case 4 -> displayAllRooms();
                case 5 -> running = false;
                default -> System.out.println("Invalid choice.");
            }
        }

        sc.close();
        System.out.println("Exiting...");
    }
}
