/*
Create a Java class with a generic method using `<T extends Number>` to accept only 
numeric types, taking room price and discount as parameters to calculate total price 
and discounted price, preventing non-numeric types at compile time.
*/

public class c_BoundedTypeParams {

    // Generic class using bounded type parameter <T extends Number>
    // Accepts only numeric values — prevents non-numeric types at compile time
    static class RoomCharges<T extends Number> {
        private T roomPrice;
        private T discount;

        public RoomCharges(T roomPrice, T discount) {
            this.roomPrice = roomPrice;
            this.discount  = discount;
        }

        // Calculate total price (no discount applied)
        public double getTotalPrice() {
            return roomPrice.doubleValue();
        }

        // Calculate discounted price
        public double getDiscountedPrice() {
            return roomPrice.doubleValue() - discount.doubleValue();
        }

        public void displayCharges() {
            System.out.println("Room Price       : " + roomPrice);
            System.out.println("Discount         : " + discount);
            System.out.println("Total Price      : " + getTotalPrice());
            System.out.println("Discounted Price : " + getDiscountedPrice());
            System.out.println(" ");
        }
    }

    public static void main(String[] args) {

        // Using Integer values
        RoomCharges<Integer> intCharges = new RoomCharges<>(5000, 500);
        System.out.println("RoomCharges<Integer>:");
        intCharges.displayCharges();

        // Using Double values
        RoomCharges<Double> doubleCharges = new RoomCharges<>(4500.00, 450.50);
        System.out.println("RoomCharges<Double>:");
        doubleCharges.displayCharges();

        // RoomCharges<String> would cause a compile-time error — non-numeric type prevented
    }
}
