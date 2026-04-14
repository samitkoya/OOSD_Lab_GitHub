package hotel;

public class Booking {
    private String customerId;
    private int roomNumber;
    private int days;
    private double pricePerDay;
    private double serviceCosts;

    public Booking(String customerId, int roomNumber, int days, double pricePerDay) {
        this.customerId = customerId;
        this.roomNumber = roomNumber;
        this.days = days;
        this.pricePerDay = pricePerDay;
        this.serviceCosts = 0;
    }

    public String getCustomerId()  { return customerId; }
    public int getRoomNumber()     { return roomNumber; }
    public int getDays()           { return days; }
    public double getServiceCosts() { return serviceCosts; }
    
    public void addService(double cost) {
        this.serviceCosts += cost;
    }

    public double getTotalCost() {
        return (days * pricePerDay) + serviceCosts;
    }

    @Override
    public String toString() {
        return String.format("Customer %-6s | Room %-5d | %d day(s) | Services: ₹%.2f | Total: ₹%.2f",
                customerId, roomNumber, days, serviceCosts, getTotalCost());
    }
}
