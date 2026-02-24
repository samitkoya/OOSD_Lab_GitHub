/*
Implement a generic method `<T> void display(T data)` in a Java hotel management 
program and call it with different types — `Integer` for room number, `String` for 
room type, `Double` for price, and `Boolean` for booking status — demonstrating type-safe 
polymorphism without casting.
*/

public class b_GenericMethod {

    // Generic method to display room-related data of different types
    static <T> void display(T data) {
        System.out.println("Value : " + data);
        System.out.println("Type  : " + data.getClass().getSimpleName());
        System.out.println("----------------------------------");
    }

    public static void main(String[] args) {

        // Call with Room Number (Integer)
        System.out.println("Room Number:");
        display(101);

        // Call with Room Type (String)
        System.out.println("Room Type:");
        display("Suite");

        // Call with Price per Night (Double)
        System.out.println("Price per Night:");
        display(3500.75);

        // Call with Booking Status (Boolean)
        System.out.println("Booking Status:");
        display(true);
    }
}
