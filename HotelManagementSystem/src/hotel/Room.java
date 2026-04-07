package hotel;

public class Room implements Comparable<Room> {
    private int roomNumber;
    private String roomType;
    private double pricePerDay;
    private boolean available;

    public Room(int roomNumber, String roomType, double pricePerDay) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.pricePerDay = pricePerDay;
        this.available = true;
    }

    public int getRoomNumber()        { return roomNumber; }
    public String getRoomType()       { return roomType; }
    public double getPricePerDay()    { return pricePerDay; }
    public boolean isAvailable()      { return available; }
    public void setAvailable(boolean b) { this.available = b; }

    @Override
    public int compareTo(Room other) {
        return Double.compare(this.pricePerDay, other.pricePerDay);
    }

    @Override
    public String toString() {
        return String.format("Room %-5d | %-8s | ₹%-8.2f | %s",
                roomNumber, roomType, pricePerDay,
                available ? "Available" : "Occupied");
    }
}
