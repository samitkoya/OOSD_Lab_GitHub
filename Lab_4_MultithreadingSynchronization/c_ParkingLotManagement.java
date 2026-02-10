/* Design and implement a Java-based parking lot management system using multiple threads.
The parking lot has a fixed number of parking slots. Multiple vehicle threads attempt to 
park simultaneously. If no slots are available, the vehicle thread should wait. When a slot 
is freed by another vehicle, waiting threads should be notified.
*/

class ParkingLot {
    private int availableSlots;

    public ParkingLot(int totalSlots) {
        this.availableSlots = totalSlots;
    }

    public synchronized void parkVehicle(String vehicleName) {

        while (availableSlots == 0) {
            System.out.println(vehicleName + " is waiting for a parking slot...");
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        availableSlots--;
        System.out.println(vehicleName + " parked. Slots left: " + availableSlots);
    }

    public synchronized void leaveSlot(String vehicleName) {
        availableSlots++;
        System.out.println(vehicleName + " left. Slots available: " + availableSlots);
        notify();
    }
}

// Vehicle thread
class Vehicle extends Thread {
    private ParkingLot parkingLot;
    private String vehicleName;

    public Vehicle(ParkingLot parkingLot, String name) {
        this.parkingLot = parkingLot;
        this.vehicleName = name;
    }

    public void run() {
        parkingLot.parkVehicle(vehicleName);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        parkingLot.leaveSlot(vehicleName);
    }
}

// Main class
public class c_ParkingLotManagement {
    public static void main(String[] args) {

        ParkingLot parkingLot = new ParkingLot(2);

        Vehicle v1 = new Vehicle(parkingLot, "Vehicle-1");
        Vehicle v2 = new Vehicle(parkingLot, "Vehicle-2");
        Vehicle v3 = new Vehicle(parkingLot, "Vehicle-3");
        Vehicle v4 = new Vehicle(parkingLot, "Vehicle-4");

        v1.start();
        v2.start();
        v3.start();
        v4.start();
    }
}
