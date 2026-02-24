/*
Create a Java program with a generic method `<T> void printArray(T[] array)` to print 
arrays of different types — `Integer[]` for room numbers, `String[]` for room types, 
and `Double[]` for prices — without using the Collections framework.
*/

public class d_GenericArray {

    // Generic method to print arrays of any type — no collections framework used
    static <T> void printArray(T[] array) {
        for (T element : array) {
            System.out.print(element + "  ");
        }
        System.out.println();
        System.out.println(" ");
    }

    public static void main(String[] args) {

        // Room numbers array
        Integer[] roomNumbers = {101, 102, 103, 104, 105};
        System.out.println("Room Numbers:");
        printArray(roomNumbers);

        // Room types array
        String[] roomTypes = {"Single", "Double", "Suite", "Deluxe", "Penthouse"};
        System.out.println("Room Types:");
        printArray(roomTypes);

        // Room prices array
        Double[] roomPrices = {1500.00, 2500.00, 4500.00, 6000.00, 12000.00};
        System.out.println("Room Prices:");
        printArray(roomPrices);
    }
}
