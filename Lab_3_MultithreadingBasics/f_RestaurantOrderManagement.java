/* Design and implement a Java application to simulate a Restaurant Order Management System 
where multiple customer orders are prepared concurrently. Each order should run in a separate 
thread representing food preparation stages.
*/

class OrderTaking implements Runnable {
    @Override
    public void run() {
        System.out.println("Order Taking started...");
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Order Taking completed.");
    }
}

class FoodPreparation implements Runnable {
    @Override
    public void run() {
        System.out.println("Food Preparation started...");
        try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Food Preparation completed.");
    }
}

class OrderServing implements Runnable {
    @Override
    public void run() {
        System.out.println("Order Serving started...");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Order Serving completed.");
    }
}

public class f_RestaurantOrderManagement {
    public static void main(String[] args) {

        Thread orderThread = new Thread(new OrderTaking());
        Thread cookThread = new Thread(new FoodPreparation());
        Thread serveThread = new Thread(new OrderServing());

        orderThread.start();
        cookThread.start();
        serveThread.start();
    }
}
