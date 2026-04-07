package hotel;

public class Booking {
    private String customerId;
    private int roomNumber;
    private int days;
    private double totalCost;

    public Booking(String customerId, int roomNumber, int days, double pricePerDay) {
        this.customerId = customerId;
        this.roomNumber = roomNumber;
        this.days = days;
        this.totalCost = days * pricePerDay;
    }

    public String getCustomerId()  { return customerId; }
    public int getRoomNumber()     { return roomNumber; }
    public int getDays()           { return days; }
    public double getTotalCost()   { return totalCost; }

    @Override
    public String toString() {
        return String.format("Customer %-6s | Room %-5d | %d day(s) | Total: ₹%.2f",
                customerId, roomNumber, days, totalCost);
    }
}
