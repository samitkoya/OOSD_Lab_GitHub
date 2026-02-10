/* Create a Java application to simulate a Payroll Processing System where salary calculations 
for multiple employees are performed concurrently using threads.
*/

class Employee1Payroll implements Runnable {
    @Override
    public void run() {
        System.out.println("Employee-1 salary calculation started...");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Employee-1 salary calculation completed.");
    }
}

class Employee2Payroll implements Runnable {
    @Override
    public void run() {
        System.out.println("Employee-2 salary calculation started...");
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Employee-2 salary calculation completed.");
    }
}

class Employee3Payroll implements Runnable {
    @Override
    public void run() {
        System.out.println("Employee-3 salary calculation started...");
        try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Employee-3 salary calculation completed.");
    }
}

public class e_PayrollProcessingSystem {
    public static void main(String[] args) {

        Thread emp1Thread = new Thread(new Employee1Payroll());
        Thread emp2Thread = new Thread(new Employee2Payroll());
        Thread emp3Thread = new Thread(new Employee3Payroll());

        emp1Thread.start();
        emp2Thread.start();
        emp3Thread.start();
    }
}
