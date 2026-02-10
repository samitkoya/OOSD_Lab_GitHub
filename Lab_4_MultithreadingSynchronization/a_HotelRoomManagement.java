/* Design and implement a Java-based hotel room management application that simulates concurrent 
room booking and room release operations using multiple threads. The system must ensure data 
consistency when multiple customers attempt to book or release rooms simultaneously. A hotel 
has a limited number of rooms. Multiple customer threads attempt to book rooms at the same time. 
If no rooms are available, the booking thread must wait. When a room is released by another thread, 
the waiting booking thread must be notified and allowed to proceed.
*/

class Hotel {
    private int availableRooms;

    public Hotel(int totalRooms) {
        this.availableRooms = totalRooms;
    }

    public synchronized void bookRoom(String customerName) {

        while (availableRooms == 0) {
            System.out.println(customerName + " is waiting for a room...");
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        availableRooms--;
        System.out.println(customerName + " booked a room. Rooms left: " + availableRooms);
    }

    public synchronized void releaseRoom(String customerName) {
        availableRooms++;
        System.out.println(customerName + " released a room. Rooms available: " + availableRooms);
        notify();  
    }
}

// Customer thread
class Customer extends Thread {
    private Hotel hotel;
    private String customerName;

    public Customer(Hotel hotel, String name) {
        this.hotel = hotel;
        this.customerName = name;
    }

    @Override
    public void run() {
        hotel.bookRoom(customerName);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        hotel.releaseRoom(customerName);
    }
}

// Main class
public class a_HotelRoomManagement {
    public static void main(String[] args) {

        Hotel hotel = new Hotel(2);

        Customer c1 = new Customer(hotel, "Customer-1");
        Customer c2 = new Customer(hotel, "Customer-2");
        Customer c3 = new Customer(hotel, "Customer-3");
        Customer c4 = new Customer(hotel, "Customer-4");

        c1.start();
        c2.start();
        c3.start();
        c4.start();
    }
}
