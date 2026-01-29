/*
The Hotel Billing system should calculate the total bill amount for hotel guests based on 
room charges and additional service charges. Store numeric values such as room tariff, 
number of days stayed, and service charges using wrapper class objects (Integer, 
Double) instead of primitive data types. 

Demonstrate autoboxing by automatically converting primitive values to wrapper class 
objects when assigning values or storing them in collections. Demonstrate unboxing by 
automatically converting wrapper class objects back to primitive types while performing 
arithmetic operations for bill calculation. 

Create a main class to: 
i. Initialize room tariff and number of days using primitive data types and store them in 
wrapper objects. 
ii. Perform total bill calculation using unboxed primitive values. 
iii. Display the final hotel bill.
*/

class a_HotelBilling {
    public static void main(String[] args) {

        double roomTariff = 2500.50;
        int numberOfDays = 4;
        double serviceCharges = 1200.75;

        Double tariffObj = roomTariff;
        Integer daysObj = numberOfDays;
        Double serviceObj = serviceCharges;

        double totalBill = (tariffObj * daysObj) + serviceObj;

        System.out.println("Hotel Bill Details :-");
        System.out.println("Room Tariff per Day : " + tariffObj);
        System.out.println("Number of Days      : " + daysObj);
        System.out.println("Service Charges     : " + serviceObj);
        System.out.println("");
        System.out.println("Total Bill Amount   : " + totalBill);
    }
}
