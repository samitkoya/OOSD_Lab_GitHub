/* Design and implement a Java application for managing hotel room bookings where room booking
details are stored as serialized objects in a file. The application uses serialization to save
hotel room booking objects permanently and deserialization to retrieve them. This approach
simulates real-world object persistence without using a database.
*/

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
        try (FileOutputStream fos = new FileOutputStream(FILE_NAME);
                ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            for (Room room : rooms) {
                oos.writeObject(room);
            }
            System.out.println("Rooms saved successfully to file.");

        } catch (IOException e) {
            System.err.println("Error saving rooms: " + e.getMessage());
        }
    }

    static List<Room> loadRooms() {
        List<Room> rooms = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(FILE_NAME);
                ObjectInputStream ois = new ObjectInputStream(fis)) {

            while (true) {
                try {
                    Room room = (Room) ois.readObject();
                    rooms.add(room);
                } catch (EOFException e) {
                    break;
                }
            }

        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading rooms: " + e.getMessage());
        }

        return rooms;
    }

    static void displayAllRooms() {
        List<Room> rooms = loadRooms();

        if (rooms.isEmpty()) {
            System.out.println("No rooms available in the system.");
            return;
        }

        System.out.println("\n========== All Room Details ==========");
        for (Room room : rooms) {
            System.out.println(room);
            System.out.println("--");
        }
    }

    static void searchRoom(int roomNumber) {
        List<Room> rooms = loadRooms();
        boolean found = false;

        for (Room room : rooms) {
            if (room.roomNumber == roomNumber) {
                System.out.println("\n Room Found ");
                System.out.println(room);
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("Room " + roomNumber + " not found.");
        }
    }

    static void addRoom(Room newRoom) {
        List<Room> rooms = loadRooms();
        rooms.add(newRoom);
        saveRooms(rooms);
        System.out.println("Room " + newRoom.roomNumber + " added successfully.");
    }

    static void updateBookingStatus(int roomNumber, boolean newStatus, String guestName) {
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
            System.out.println("Room " + roomNumber + " status updated to: "
                    + (newStatus ? "Booked" : "Available"));
        } else {
            System.out.println("Room " + roomNumber + " not found.");
        }
    }

    public static void main(String[] args) {

        System.out.println(" Hotel Room Booking System (Serialization) :-\n");

        List<Room> initialRooms = new ArrayList<>();
        initialRooms.add(new Room(101, "Single", 1500.0, false, ""));
        initialRooms.add(new Room(102, "Double", 2500.0, false, ""));
        initialRooms.add(new Room(103, "Suite", 5000.0, true, "Rajesh Kumar"));
        initialRooms.add(new Room(104, "Deluxe", 3500.0, false, ""));

        System.out.println(" Saving initial rooms to file ");
        saveRooms(initialRooms);

        System.out.println("\n Displaying all rooms (loaded from file) :- ");
        displayAllRooms();

        System.out.println("\n Searching for Room 102 :- ");
        searchRoom(102);

        System.out.println("\n Booking Room 102 for guest Priya Sharma :- ");
        updateBookingStatus(102, true, "Priya Sharma");

        System.out.println("\n Vacating Room 103 :- ");
        updateBookingStatus(103, false, "");

        System.out.println("\n Adding New Room 105 :- ");
        addRoom(new Room(105, "Presidential", 10000.0, false, ""));

        System.out.println("\n All rooms after updates :- ");
        displayAllRooms();

        System.out.println("\n Searching for Room 999 :- ");
        searchRoom(999);
    }
}
