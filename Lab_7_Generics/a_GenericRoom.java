/*
Create a generic Java class `Room<T, U>` where `T` represents the Room ID/Number and `U` 
represents the Room Type or Price. Demonstrate it with varied data types like `Room<Integer, String>` for 
room-type storage and `Room<Integer, Double>` for pricing, then display the stored details.
*/

public class a_GenericRoom {

    // Generic class Room with two type parameters
    // T represents Room Number or Room ID
    // U represents Room Type or Price
    static class Room<T, U> {
        private T roomId;
        private U roomAttribute;

        public Room(T roomId, U roomAttribute) {
            this.roomId        = roomId;
            this.roomAttribute = roomAttribute;
        }

        public T getRoomId()        { return roomId; }
        public U getRoomAttribute() { return roomAttribute; }

        public void displayRoomDetails() {
            System.out.println("Room ID/Number : " + roomId);
            System.out.println("Room Attribute : " + roomAttribute);
            System.out.println(" ");
        }
    }

    public static void main(String[] args) {

        // T = Integer (Room Number), U = String (Room Type)
        Room<Integer, String> room1 = new Room<>(101, "Deluxe");
        System.out.println("Room<Integer, String>");
        room1.displayRoomDetails();

        // T = String (Room ID), U = Double (Price)
        Room<String, Double> room2 = new Room<>("R202", 4500.00);
        System.out.println("Room<String, Double>");
        room2.displayRoomDetails();

        // T = Integer (Room Number), U = Double (Price)
        Room<Integer, Double> room3 = new Room<>(303, 7200.50);
        System.out.println("Room<Integer, Double>");
        room3.displayRoomDetails();
    }
}
