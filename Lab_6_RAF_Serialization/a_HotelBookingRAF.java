import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

public class a_HotelBookingRAF {

    static final String FILE_NAME = "hotel_rooms.dat";
    static final int ROOM_TYPE_LENGTH = 20;
    static final int RECORD_SIZE = 4 + (ROOM_TYPE_LENGTH * 2) + 8 + 1;

    static void writeFixedString(RandomAccessFile raf, String text) throws IOException {
        for (int i = 0; i < ROOM_TYPE_LENGTH; i++) {
            raf.writeChar(i < text.length() ? text.charAt(i) : ' ');
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

    static void addRoom(Scanner sc) {
        System.out.print("Enter Room Number   : ");
        int roomNumber = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter Room Type     : ");
        String roomType = sc.nextLine();
        System.out.print("Enter Price/Night   : ");
        double price = sc.nextDouble();
        System.out.print("Is Booked (true/false): ");
        boolean isBooked = sc.nextBoolean();

        try (RandomAccessFile raf = new RandomAccessFile(FILE_NAME, "rw")) {
            int count = recordCount(raf);
            seekToRecord(raf, count);
            raf.writeInt(roomNumber);
            writeFixedString(raf, roomType);
            raf.writeDouble(price);
            raf.writeBoolean(isBooked);
            System.out.println("Room " + roomNumber + " added successfully.");
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    static void displayRoom(Scanner sc) {
        System.out.print("Enter Room Number to view: ");
        int roomNumber = sc.nextInt();

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

            if (!found)
                System.out.println("Room " + roomNumber + " not found.");
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    static void updateBookingStatus(Scanner sc) {
        System.out.print("Enter Room Number to update: ");
        int roomNumber = sc.nextInt();
        System.out.print("New Status (true = Booked, false = Available): ");
        boolean newStatus = sc.nextBoolean();

        try (RandomAccessFile raf = new RandomAccessFile(FILE_NAME, "rw")) {
            int count = recordCount(raf);
            boolean found = false;

            for (int slot = 0; slot < count; slot++) {
                seekToRecord(raf, slot);
                int num = raf.readInt();

                if (num == roomNumber) {
                    long offset = (long) slot * RECORD_SIZE + 4 + (ROOM_TYPE_LENGTH * 2) + 8;
                    raf.seek(offset);
                    raf.writeBoolean(newStatus);
                    System.out.println(
                            "Room " + roomNumber + " status updated to: " + (newStatus ? "Booked" : "Available"));
                    found = true;
                    break;
                }
            }

            if (!found)
                System.out.println("Room " + roomNumber + " not found.");
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    static void displayAllRooms() {
        try (RandomAccessFile raf = new RandomAccessFile(FILE_NAME, "r")) {
            int count = recordCount(raf);

            if (count == 0) {
                System.out.println("No rooms available.");
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
                System.out.println(" ");
            }
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Hotel Room Booking System (RandomAccessFile) :-");
        System.out.println("Record size: " + RECORD_SIZE + " bytes per room");

        boolean running = true;
        while (running) {
            System.out.println("\n1. Add Room");
            System.out.println("2. View Room");
            System.out.println("3. Update Booking Status");
            System.out.println("4. View All Rooms");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1 -> addRoom(sc);
                case 2 -> displayRoom(sc);
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
