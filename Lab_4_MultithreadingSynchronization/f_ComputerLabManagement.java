/* Design and implement a Java-based computer lab management system using multithreading.
The lab has a limited number of computers. Multiple student threads attempt to use a 
computer simultaneously. If all computers are occupied, the student thread must wait. 
When a computer is released, waiting threads must be notified.
*/

class ComputerLab {
    private int availableComputers;

    public ComputerLab(int totalComputers) {
        this.availableComputers = totalComputers;
    }

    public synchronized void useComputer(String studentName) {

        while (availableComputers == 0) {
            System.out.println(studentName + " is waiting for a computer...");
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        availableComputers--;
        System.out.println(studentName + " is using a computer. Computers left: " + availableComputers);
    }

    public synchronized void releaseComputer(String studentName) {
        availableComputers++;
        System.out.println(studentName + " released a computer. Computers available: " + availableComputers);
        notify();
    }
}

// Student thread
class Student extends Thread {
    private ComputerLab computerLab;
    private String studentName;

    public Student(ComputerLab computerLab, String name) {
        this.computerLab = computerLab;
        this.studentName = name;
    }

    public void run() {
        computerLab.useComputer(studentName);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        computerLab.releaseComputer(studentName);
    }
}

// Main class
public class f_ComputerLabManagement {
    public static void main(String[] args) {

        ComputerLab computerLab = new ComputerLab(2);

        Student s1 = new Student(computerLab, "Student-1");
        Student s2 = new Student(computerLab, "Student-2");
        Student s3 = new Student(computerLab, "Student-3");
        Student s4 = new Student(computerLab, "Student-4");

        s1.start();
        s2.start();
        s3.start();
        s4.start();
    }
}
