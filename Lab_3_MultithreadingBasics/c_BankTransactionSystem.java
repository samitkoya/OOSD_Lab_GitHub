/* Design and implement a Java application to simulate a Bank Transaction System where 
multiple customers perform deposits and withdrawals simultaneously. Each transaction should 
run in a separate thread. Use sleep() to simulate processing time and display transaction 
status messages.
*/

class Deposit implements Runnable {
    @Override
    public void run() {
        System.out.println("Deposit transaction started...");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Deposit transaction completed.");
    }
}

class Withdrawal implements Runnable {
    @Override
    public void run() {
        System.out.println("Withdrawal transaction started...");
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Withdrawal transaction completed.");
    }
}

class BalanceInquiry implements Runnable {
    @Override
    public void run() {
        System.out.println("Balance Inquiry started...");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Balance Inquiry completed.");
    }
}

public class c_BankTransactionSystem {
    public static void main(String[] args) {

        Thread depositThread = new Thread(new Deposit());
        Thread withdrawalThread = new Thread(new Withdrawal());
        Thread inquiryThread = new Thread(new BalanceInquiry());

        depositThread.start();
        withdrawalThread.start();
        inquiryThread.start();
    }
}
