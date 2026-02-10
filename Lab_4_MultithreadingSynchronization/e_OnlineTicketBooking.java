/* Design and implement a Java-based online ticket booking system using multithreading.
The system has a fixed number of tickets. Multiple user threads attempt to book tickets 
concurrently. If tickets are unavailable, the booking thread must wait. When a ticket is 
canceled by another thread, waiting threads must be notified.
*/

class TicketCounter {
    private int availableTickets;

    public TicketCounter(int totalTickets) {
        this.availableTickets = totalTickets;
    }

    public synchronized void bookTicket(String userName) {

        while (availableTickets == 0) {
            System.out.println(userName + " is waiting for a ticket...");
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        availableTickets--;
        System.out.println(userName + " booked a ticket. Tickets left: " + availableTickets);
    }

    public synchronized void cancelTicket(String userName) {
        availableTickets++;
        System.out.println(userName + " canceled a ticket. Tickets available: " + availableTickets);
        notify();
    }
}

// User thread
class BookingUser extends Thread {
    private TicketCounter ticketCounter;
    private String userName;

    public BookingUser(TicketCounter ticketCounter, String name) {
        this.ticketCounter = ticketCounter;
        this.userName = name;
    }
    public void run() {
        ticketCounter.bookTicket(userName);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ticketCounter.cancelTicket(userName);
    }
}

// Main class
public class e_OnlineTicketBooking {
    public static void main(String[] args) {

        TicketCounter ticketCounter = new TicketCounter(2);

        BookingUser u1 = new BookingUser(ticketCounter, "User-1");
        BookingUser u2 = new BookingUser(ticketCounter, "User-2");
        BookingUser u3 = new BookingUser(ticketCounter, "User-3");
        BookingUser u4 = new BookingUser(ticketCounter, "User-4");

        u1.start();
        u2.start();
        u3.start();
        u4.start();
    }
}
