/* Design and implement a Java-based library management system where multiple user threads 
attempt to borrow books concurrently. The library has a limited number of copies of a book. 
If no copies are available, the user thread must wait until a book is returned. Once a book 
is returned, waiting threads should be notified.
*/

class Library {
    private int availableCopies;

    public Library(int totalCopies) {
        this.availableCopies = totalCopies;
    }

    public synchronized void borrowBook(String userName) {

        while (availableCopies == 0) {
            System.out.println(userName + " is waiting for a book copy...");
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        availableCopies--;
        System.out.println(userName + " borrowed a book. Copies left: " + availableCopies);
    }

    public synchronized void returnBook(String userName) {
        availableCopies++;
        System.out.println(userName + " returned a book. Copies available: " + availableCopies);
        notify();
    }
}

// User thread
class LibraryUser extends Thread {
    private Library library;
    private String userName;

    public LibraryUser(Library library, String name) {
        this.library = library;
        this.userName = name;
    }

    public void run() {
        library.borrowBook(userName);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        library.returnBook(userName);
    }
}

// Main class
public class d_LibraryManagement {
    public static void main(String[] args) {

        Library library = new Library(2);

        LibraryUser u1 = new LibraryUser(library, "User-1");
        LibraryUser u2 = new LibraryUser(library, "User-2");
        LibraryUser u3 = new LibraryUser(library, "User-3");
        LibraryUser u4 = new LibraryUser(library, "User-4");

        u1.start();
        u2.start();
        u3.start();
        u4.start();
    }
}
