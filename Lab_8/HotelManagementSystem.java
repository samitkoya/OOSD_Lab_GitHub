import java.util.*;

public class HotelManagementSystem {

    static class Room {
        int roomNumber;
        String type;
        double price;
        boolean isAvailable;

        public Room(int roomNumber, String type, double price) {
            this.roomNumber = roomNumber;
            this.type = type;
            this.price = price;
            this.isAvailable = true;
        }

        public String toString() {
            return String.format("Room #%d | Type: %-8s | Price: %.2f | Status: %s", 
                roomNumber, type, price, (isAvailable ? "Available" : "Occupied"));
        }
    }
    static class Customer {
        int id;
        String name;
        String contact;
        int allocatedRoom;

        public Customer(int id, String name, String contact, int allocatedRoom) {
            this.id = id;
            this.name = name;
            this.contact = contact;
            this.allocatedRoom = allocatedRoom;
        }

        public String toString() {
            return "ID: " + id + " | Name: " + name + " | Contact: " + contact + " | Room: " + allocatedRoom;
        }
    }

    private List<Room> rooms = new ArrayList<>();
    private List<Customer> customers = new ArrayList<>();
    private Map<Integer, Customer> roomMap = new HashMap<>();
    private Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        HotelManagementSystem hms = new HotelManagementSystem();
        hms.runMenu();
    }

    public void runMenu() {
        int choice;
        do {
            System.out.println("\n  HOTEL MANAGEMENT SYSTEM  ");
            System.out.println("1. Add Room");
            System.out.println("2. Display Available Rooms");
            System.out.println("3. Add Customer & Book Room");
            System.out.println("4. Checkout Customer");
            System.out.println("5. Display All Customers");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            
            try {
                choice = scanner.nextInt();
                switch (choice) {
                    case 1 -> addRoom();
                    case 2 -> displayAvailableRooms();
                    case 3 -> bookRoom();
                    case 4 -> checkout();
                    case 5 -> displayCustomers();
                    case 6 -> System.out.println("Exiting System...");
                    default -> System.out.println("Invalid choice! Try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Please enter a valid number.");
                scanner.next();
                choice = 0;
            }
        } while (choice != 6);
    }

    private void addRoom() {
        System.out.print("Enter Room Number: ");
        int num = scanner.nextInt();
        System.out.print("Enter Type (Single/Double/Deluxe/Suite): ");
        String type = scanner.next();
        System.out.print("Enter Price per Day: ");
        double price = scanner.nextDouble();

        rooms.add(new Room(num, type, price));
        // Sorting rooms by price using Collections.sort()
        Collections.sort(rooms, Comparator.comparingDouble(r -> r.price));
        System.out.println("Room " + num + " added and sorted by price.");
    }

    private void displayAvailableRooms() {
        System.out.println("\n--- Available Rooms ---");
        boolean found = false;
        Iterator<Room> it = rooms.iterator();
        while (it.hasNext()) {
            Room r = it.next();
            if (r.isAvailable) {
                System.out.println(r);
                found = true;
            }
        }
        if (!found) System.out.println("No rooms available.");
    }

    private void bookRoom() {
        System.out.print("Enter Room Number to book: ");
        int roomNum = scanner.nextInt();
        
        Room selectedRoom = null;
        for (Room r : rooms) {
            if (r.roomNumber == roomNum) {
                selectedRoom = r;
                break;
            }
        }

        if (selectedRoom != null && selectedRoom.isAvailable) {
            System.out.print("Enter Customer ID: ");
            int id = scanner.nextInt();
            System.out.print("Enter Name: ");
            String name = scanner.next();
            System.out.print("Enter Contact: ");
            String contact = scanner.next();

            Customer newCustomer = new Customer(id, name, contact, roomNum);
            customers.add(newCustomer);
            roomMap.put(roomNum, newCustomer);
            selectedRoom.isAvailable = false;
            System.out.println("Booking Successful for " + name);
        } else {
            System.out.println("Room is either occupied or doesn't exist.");
        }
    }

    private void checkout() {
        System.out.print("Enter Room Number for Checkout: ");
        int roomNum = scanner.nextInt();

        if (roomMap.containsKey(roomNum)) {
            Customer c = roomMap.remove(roomNum);
            customers.remove(c);
            
            // Mark room as available
            for (Room r : rooms) {
                if (r.roomNumber == roomNum) {
                    r.isAvailable = true;
                    break;
                }
            }
            System.out.println("Checkout successful for " + c.name);
        } else {
            System.out.println("No booking found for this room number.");
        }
    }

    private void displayCustomers() {
        System.out.println("\n--- Current Guest Records ---");
        if (customers.isEmpty()) {
            System.out.println("No customers found.");
        } else {
            for (Customer c : customers) {
                System.out.println(c);
            }
        }
    }
}
