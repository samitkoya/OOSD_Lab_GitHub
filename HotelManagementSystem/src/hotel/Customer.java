package hotel;

public class Customer {
    private String customerId;
    private String name;
    private String contactNumber;
    private int allocatedRoomNumber; // 0 means no room allocated

    public Customer(String customerId, String name, String contactNumber) {
        this.customerId = customerId;
        this.name = name;
        this.contactNumber = contactNumber;
        this.allocatedRoomNumber = 0;
    }

    public String getCustomerId()     { return customerId; }
    public String getName()           { return name; }
    public String getContactNumber()  { return contactNumber; }
    public int getAllocatedRoomNumber() { return allocatedRoomNumber; }
    public void setAllocatedRoomNumber(int roomNumber) { this.allocatedRoomNumber = roomNumber; }

    @Override
    public String toString() {
        String roomInfo = (allocatedRoomNumber == 0) ? "No room" : "Room " + allocatedRoomNumber;
        return String.format("ID: %-6s | %-20s | Contact: %-12s | %s",
                customerId, name, contactNumber, roomInfo);
    }
}
