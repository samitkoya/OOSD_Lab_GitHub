/* Design and implement a Java application to manage hotel room bookings where room records
are stored in a file and accessed using RandomAccessFile. Each room record is of fixed length,
enabling direct (random) access to any room's booking information without reading the file
sequentially. The system supports adding rooms, viewing room details, and updating booking
status by directly navigating to the required record position using seek().
*/

import java.io.IOException;
import java.io.RandomAccessFile;

public class a_HotelBookingRAF {

    static final String FILE_NAME = "hotel_rooms.dat";

    static final int ROOM_TYPE_LENGTH = 20;
    static final int RECORD_SIZE = 4 + (ROOM_TYPE_LENGTH * 2) + 8 + 1; // 53 bytes

    static void writeFixedString(RandomAccessFile raf, String text) throws IOException {
        for (int i = 0; i < ROOM_TYPE_LENGTH; i++) {
            if (i < text.length()) {
                raf.writeChar(text.charAt(i));
            } else {
                raf.writeChar(' ');
            }
        }
    }

    static String readFixedString(RandomAccessFile raf) throws IOException {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < ROOM_TYPE_LENGTH; i++) {
            sb.append(raf.readChar());
        }
        return sb.toString().trim();
    }

    static void seekToRecord(RandomAccessFile raf, int slot) throws IOException {
        raf.seek((long) slot * RECORD_SIZE);
    }

    static int recordCount(RandomAccessFile raf) throws IOException {
        return (int) (raf.length() / RECORD_SIZE);
    }

    static void addRoom(int roomNumber, String roomType, double pricePerNight, boolean isBooked) {
        try (RandomAccessFile raf = new RandomAccessFile(FILE_NAME, "rw")) {
            int count = recordCount(raf);
            seekToRecord(raf, count);
            raf.writeInt(roomNumber);
            writeFixedString(raf, roomType);
            raf.writeDouble(pricePerNight);
            raf.writeBoolean(isBooked);
            System.out.println("Room " + roomNumber + " added successfully.");
        } catch (IOException e) {
            System.err.println("Error adding room: " + e.getMessage());
        }
    }

    static void displayRoom(int roomNumber) {
        try (RandomAccessFile raf = new RandomAccessFile(FILE_NAME, "r")) {
            int count = recordCount(raf);
            boolean found = false;

            for (int slot = 0; slot < count; slot++) {
                seekToRecord(raf, slot);
                int num = raf.readInt();
                String type = readFixedString(raf);
                double price = raf.readDouble();
                boolean booked = raf.readBoolean();

                if (num == roomNumber) {
                    System.out.println("\nRoom Details :-");
                    System.out.println("Room Number   : " + num);
                    System.out.println("Room Type     : " + type);
                    System.out.println("Price/Night   : Rs." + price);
                    System.out.println("Booking Status: " + (booked ? "Booked" : "Available"));
                    found = true;
                    break;
                }
            }

            if (!found) {
                System.out.println("Room " + roomNumber + " not found.");
            }
        } catch (IOException e) {
            System.err.println("Error reading room: " + e.getMessage());
        }
    }

    static void updateBookingStatus(int roomNumber, boolean newStatus) {
        try (RandomAccessFile raf = new RandomAccessFile(FILE_NAME, "rw")) {
            int count = recordCount(raf);
            boolean found = false;

            for (int slot = 0; slot < count; slot++) {
                seekToRecord(raf, slot);
                int num = raf.readInt();

                if (num == roomNumber) {
                    long booleanOffset = (long) slot * RECORD_SIZE + 4 + (ROOM_TYPE_LENGTH * 2) + 8;
                    raf.seek(booleanOffset);
                    raf.writeBoolean(newStatus);
                    System.out.println("Room " + roomNumber + " status updated to: "
                            + (newStatus ? "Booked" : "Available"));
                    found = true;
                    break;
                }
            }

            if (!found) {
                System.out.println("Room " + roomNumber + " not found.");
            }
        } catch (IOException e) {
            System.err.println("Error updating room: " + e.getMessage());
        }
    }

    static void displayAllRooms() {
        try (RandomAccessFile raf = new RandomAccessFile(FILE_NAME, "r")) {
            int count = recordCount(raf);

            if (count == 0) {
                System.out.println("No rooms available in the system.");
                return;
            }

            System.out.println("\nAll Room Details :-");
            for (int slot = 0; slot < count; slot++) {
                seekToRecord(raf, slot);
                int num = raf.readInt();
                String type = readFixedString(raf);
                double price = raf.readDouble();
                boolean booked = raf.readBoolean();

                System.out.println("Room Number   : " + num);
                System.out.println("Room Type     : " + type);
                System.out.println("Price/Night   : Rs." + price);
                System.out.println("Booking Status: " + (booked ? "Booked" : "Available"));
                System.out.println("--------------------------------------");
            }
        } catch (IOException e) {
            System.err.println("Error displaying rooms: " + e.getMessage());
        }
    }

    public static void main(String[] args) {

        System.out.println(" Hotel Room Booking System (RandomAccessFile)");
        System.out.println("Record size: " + RECORD_SIZE + " bytes per room\n");

        // Add rooms
        addRoom(101, "Single", 1500.0, false);
        addRoom(102, "Double", 2500.0, false);
        addRoom(103, "Suite", 5000.0, true);
        addRoom(104, "Deluxe", 3500.0, false);

        // Display all rooms
        displayAllRooms();

        // Display a specific room
        System.out.println("\nFetching details for Room 102 :-");
        displayRoom(102);

        // Update booking status
        System.out.println("\nBooking Room : 102");
        updateBookingStatus(102, true);

        System.out.println("\nVacating Room : 103");
        updateBookingStatus(103, false);

        System.out.println("\nAll rooms after booking updates :- ");
        displayAllRooms();

        System.out.println("\nFetching details for Room 999:-");
        displayRoom(999);
    }
}
