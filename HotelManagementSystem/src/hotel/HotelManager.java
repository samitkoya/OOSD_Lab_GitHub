package hotel;

import java.util.*;

/**
 * Core backend using Java Collections Framework:
 *  - ArrayList  : rooms, customers
 *  - HashMap    : roomNumber -> Customer (active bookings)
 *  - Iterator   : safe traversal / removal
 *  - Collections.sort() : sorting rooms by price
 */
public class HotelManager {

    private ArrayList<Room>     rooms     = new ArrayList<>();
    private ArrayList<Customer> customers = new ArrayList<>();
    private HashMap<Integer, Booking> bookings = new HashMap<>(); // roomNumber -> Booking

    // ─────────────────── ROOM OPERATIONS ───────────────────

    public String addRoom(int roomNumber, String type, double price) {
        // Check duplicate
        for (Room r : rooms) {
            if (r.getRoomNumber() == roomNumber) {
                return "Error: Room " + roomNumber + " already exists.";
            }
        }
        rooms.add(new Room(roomNumber, type, price));
        return "✔ Room " + roomNumber + " (" + type + ") added successfully.";
    }

    public String getAllRooms() {
        if (rooms.isEmpty()) return "No rooms found.";
        Collections.sort(rooms); // sort by price
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-5s | %-8s | %-12s | %s%n", "Room", "Type", "Price/Day", "Status"));
        sb.append("-".repeat(50)).append("\n");
        Iterator<Room> it = rooms.iterator();
        while (it.hasNext()) {
            sb.append(it.next().toString()).append("\n");
        }
        return sb.toString();
    }

    public String getAvailableRooms() {
        StringBuilder sb = new StringBuilder();
        sb.append("--- Available Rooms ---\n");
        boolean found = false;
        Collections.sort(rooms);
        for (Room r : rooms) {
            if (r.isAvailable()) {
                sb.append(r.toString()).append("\n");
                found = true;
            }
        }
        if (!found) sb.append("No rooms available currently.");
        return sb.toString();
    }

    public String filterRooms(String type, double min, double max) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("--- Rooms (%s, ₹%.0f - ₹%.0f) ---\n", 
            (type == null || type.isEmpty()) ? "Any" : type, min, max));
        boolean found = false;
        for (Room r : rooms) {
            boolean typeMatch = (type == null || type.isEmpty() || r.getRoomType().equalsIgnoreCase(type));
            boolean priceMatch = (r.getPricePerDay() >= min && r.getPricePerDay() <= max);
            if (typeMatch && priceMatch) {
                sb.append(r.toString()).append("\n");
                found = true;
            }
        }
        if (!found) sb.append("No matching rooms found.");
        return sb.toString();
    }

    // ─────────────────── CUSTOMER OPERATIONS ───────────────────

    public String addCustomer(String id, String name, String contact) {
        for (Customer c : customers) {
            if (c.getCustomerId().equalsIgnoreCase(id)) {
                return "Error: Customer ID " + id + " already exists.";
            }
        }
        customers.add(new Customer(id, name, contact));
        return "✔ Customer " + name + " (ID: " + id + ") added.";
    }

    public String getAllCustomers() {
        if (customers.isEmpty()) return "No customers found.";
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-6s | %-20s | %-10s | %s%n", "ID", "Name", "Visits", "Status"));
        sb.append("-".repeat(60)).append("\n");
        for (Customer c : customers) {
            sb.append(c.toString()).append("\n");
        }
        return sb.toString();
    }

    public String searchCustomers(String query) {
        if (customers.isEmpty()) return "No customers found.";
        StringBuilder sb = new StringBuilder();
        sb.append("--- Search Results ---\n");
        boolean found = false;
        for (Customer c : customers) {
            if (c.getCustomerId().equalsIgnoreCase(query) || c.getName().toLowerCase().contains(query.toLowerCase())) {
                sb.append(c.toString()).append("\n");
                found = true;
            }
        }
        if (!found) sb.append("No matches for: ").append(query);
        return sb.toString();
    }

    public String removeCustomer(String id) {
        Iterator<Customer> it = customers.iterator();
        while (it.hasNext()) {
            Customer c = it.next();
            if (c.getCustomerId().equalsIgnoreCase(id)) {
                if (c.getAllocatedRoomNumber() != 0) {
                    return "Error: Customer has an active booking. Please checkout first.";
                }
                it.remove();
                return "✔ Customer " + c.getName() + " removed.";
            }
        }
        return "Error: Customer ID " + id + " not found.";
    }

    // ─────────────────── BOOKING OPERATIONS ───────────────────

    public String bookRoom(String customerId, int roomNumber, int days) {
        // Find customer
        Customer customer = findCustomer(customerId);
        if (customer == null) return "Error: Customer ID " + customerId + " not found.";
        if (customer.getAllocatedRoomNumber() != 0)
            return "Error: Customer already has Room " + customer.getAllocatedRoomNumber() + " booked.";

        // Find room
        Room room = findRoom(roomNumber);
        if (room == null) return "Error: Room " + roomNumber + " does not exist.";
        if (!room.isAvailable()) return "Error: Room " + roomNumber + " is already occupied.";
        if (days <= 0) return "Error: Number of days must be at least 1.";

        // Book
        Booking booking = new Booking(customerId, roomNumber, days, room.getPricePerDay());
        bookings.put(roomNumber, booking);
        room.setAvailable(false);
        customer.setAllocatedRoomNumber(roomNumber);

        return String.format("✔ Room %d booked for %s. Total cost: ₹%.2f",
                roomNumber, customer.getName(), booking.getTotalCost());
    }

    public String addServiceCharge(String customerId, String serviceName, double cost) {
        Customer customer = findCustomer(customerId);
        if (customer == null) return "Error: Customer ID " + customerId + " not found.";
        
        int roomNum = customer.getAllocatedRoomNumber();
        if (roomNum == 0) return "Error: Customer " + customerId + " has no active booking.";

        Booking booking = bookings.get(roomNum);
        if (booking == null) return "Error: No booking found for room " + roomNum;

        booking.addService(cost);
        return String.format("✔ Added %s (₹%.2f) to %s's bill.", serviceName, cost, customer.getName());
    }

    public String addServiceByRoom(int roomNumber, String serviceName, double cost) {
        Booking booking = bookings.get(roomNumber);
        if (booking == null) {
            return "Error: Room " + roomNumber + " is not currently booked.";
        }
        
        Customer customer = findCustomer(booking.getCustomerId());
        booking.addService(cost);
        
        String name = (customer != null) ? customer.getName() : "Guest";
        return String.format("✔ Added %s (₹%.2f) to Room %d (%s).", serviceName, cost, roomNumber, name);
    }

    public String checkout(String customerId) {
        Customer customer = findCustomer(customerId);
        if (customer == null) return "Error: Customer ID " + customerId + " not found.";
        int roomNum = customer.getAllocatedRoomNumber();
        if (roomNum == 0) return "Error: Customer " + customerId + " has no active booking.";

        Booking booking = bookings.get(roomNum);
        Room room = findRoom(roomNum);

        double total = (booking != null) ? booking.getTotalCost() : 0;
        double discount = 0;

        // Loyalty Discount: 10% if 3+ previous visits
        if (customer.getVisitCount() >= 3) {
            discount = total * 0.10;
            total -= discount;
        }

        // Release room
        if (room != null) room.setAvailable(true);
        bookings.remove(roomNum);
        customer.setAllocatedRoomNumber(0);
        customer.incrementVisits(); // Increment for next stay

        String msg = String.format("✔ Checkout successful for %s.\n", customer.getName());
        if (discount > 0) {
            msg += String.format("Loyalty Discount (10%%) applied: -₹%.2f\n", discount);
        }
        msg += String.format("Final bill: ₹%.2f", total);
        return msg;
    }

    public String getBookingSummary() {
        if (bookings.isEmpty()) return "No active bookings.";
        StringBuilder sb = new StringBuilder("--- Active Bookings ---\n");
        for (Map.Entry<Integer, Booking> entry : bookings.entrySet()) {
            sb.append(entry.getValue().toString()).append("\n");
        }
        return sb.toString();
    }

    // ─────────────────── HELPERS ───────────────────

    private Customer findCustomer(String id) {
        for (Customer c : customers)
            if (c.getCustomerId().equalsIgnoreCase(id)) return c;
        return null;
    }

    private Room findRoom(int number) {
        for (Room r : rooms)
            if (r.getRoomNumber() == number) return r;
        return null;
    }
}
