/* Design and implement a Java application to simulate a Hotel Room Service Management System 
where multiple service requests are handled concurrently using multithreading. In a hotel, 
different room service tasks such as room cleaning, food delivery, and maintenance may occur 
at the same time. To efficiently manage these tasks, the application should create separate 
threads for each service request so that they can execute concurrently rather than sequentially.
*/

class RoomCleaning implements Runnable {
    @Override
    public void run() {
        System.out.println("Room Cleaning started...");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Room Cleaning completed.");
    }
}

class FoodDelivery implements Runnable {
    @Override
    public void run() {
        System.out.println("Food Delivery started...");
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Food Delivery completed.");
    }
}

class Maintenance implements Runnable {
    @Override
    public void run() {
        System.out.println("Maintenance work started...");
        try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Maintenance work completed.");
    }
}

public class a_HotelRoomServiceManagement {
    public static void main(String[] args) {

        Thread cleaningThread = new Thread(new RoomCleaning());
        Thread foodThread = new Thread(new FoodDelivery());
        Thread maintenanceThread = new Thread(new Maintenance());

        cleaningThread.start();
        foodThread.start();
        maintenanceThread.start();
    }
}
