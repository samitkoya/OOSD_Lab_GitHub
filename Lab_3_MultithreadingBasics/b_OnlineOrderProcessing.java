/* Design and implement a Java application to simulate an Online Order Processing System 
where multiple customer orders are processed simultaneously using multithreading. In an 
e-commerce platform, several operations such as order validation, payment processing, and 
order shipment must be handled concurrently for different customers. To improve system 
performance and responsiveness, each order processing task should be executed in a separate thread.
*/

class OrderValidation implements Runnable {
    @Override
    public void run() {
        System.out.println("Order Validation started...");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Order Validation completed.");
    }
}

class PaymentProcessing implements Runnable {
    @Override
    public void run() {
        System.out.println("Payment Processing started...");
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Payment Processing completed.");
    }
}

class OrderShipment implements Runnable {
    @Override
    public void run() {
        System.out.println("Order Shipment started...");
        try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Order Shipment completed.");
    }
}

public class b_OnlineOrderProcessing {
    public static void main(String[] args) {

        Thread validationThread = new Thread(new OrderValidation());
        Thread paymentThread = new Thread(new PaymentProcessing());
        Thread shipmentThread = new Thread(new OrderShipment());

        validationThread.start();
        paymentThread.start();
        shipmentThread.start();
    }
}
