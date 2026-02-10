/* Design and implement a Java-based restaurant table reservation system using multithreading.
The restaurant has a limited number of tables. Multiple customer threads attempt to reserve 
tables concurrently. If no tables are available, the customer thread must wait. When a table 
is freed, waiting customers must be notified and allowed to reserve a table.
*/

class Restaurant {
    private int availableTables;

    public Restaurant(int totalTables) {
        this.availableTables = totalTables;
    }

    public synchronized void reserveTable(String customerName) {

        while (availableTables == 0) {
            System.out.println(customerName + " is waiting for a table...");
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        availableTables--;
        System.out.println(customerName + " reserved a table. Tables left: " + availableTables);
    }

    public synchronized void freeTable(String customerName) {
        availableTables++;
        System.out.println(customerName + " freed a table. Tables available: " + availableTables);
        notify();
    }
}

// Customer thread
class Diner extends Thread {
    private Restaurant restaurant;
    private String customerName;

    public Diner(Restaurant restaurant, String name) {
        this.restaurant = restaurant;
        this.customerName = name;
    }

    public void run() {
        restaurant.reserveTable(customerName);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        restaurant.freeTable(customerName);
    }
}

// Main class
public class b_RestaurantTableReservation {
    public static void main(String[] args) {

        Restaurant restaurant = new Restaurant(2);

        Diner d1 = new Diner(restaurant, "Customer-1");
        Diner d2 = new Diner(restaurant, "Customer-2");
        Diner d3 = new Diner(restaurant, "Customer-3");
        Diner d4 = new Diner(restaurant, "Customer-4");

        d1.start();
        d2.start();
        d3.start();
        d4.start();
    }
}
