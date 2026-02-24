/*
Create a generic Java class `Pair<T, U>` to associate room numbers with guest details, 
instantiate it as `Pair<Integer, String>`, and display booking records without any explicit 
type casting.
*/

public class e_GenericPair {

    // Generic class Pair to associate two values — no type casting required
    static class Pair<T, U> {
        private T first;
        private U second;

        public Pair(T first, U second) {
            this.first  = first;
            this.second = second;
        }

        public T getFirst()  { return first; }
        public U getSecond() { return second; }

        public void displayBookingRecord() {
            System.out.println("Room Number : " + first);
            System.out.println("Guest Name  : " + second);
            System.out.println(" ");
        }
    }

    public static void main(String[] args) {

        // Store Room Number (Integer) and Guest Name (String)
        Pair<Integer, String> booking1 = new Pair<>(101, "Rahul Sharma");
        Pair<Integer, String> booking2 = new Pair<>(202, "Priya Mehta");
        Pair<Integer, String> booking3 = new Pair<>(303, "Amit Verma");

        System.out.println("Booking Records :-");
        booking1.displayBookingRecord();
        booking2.displayBookingRecord();
        booking3.displayBookingRecord();
    }
}
