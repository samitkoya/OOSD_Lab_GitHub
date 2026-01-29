/*
Define a class CurrentDate with data members day, month and year. Define a 
method createDate() to create date object by reading values from keyboard. Throw 
a user defined exception by name InvalidDayException if the day is invalid and 
InvalidMonthException if month is found invalid and display current date if the date 
is valid. Write a test program to illustrate the functionality.
*/
import java.util.Scanner;

class InvalidDayException extends Exception {
    public InvalidDayException(String message) {
        super(message);
    }
}

class InvalidMonthException extends Exception {
    public InvalidMonthException(String message) {
        super(message);
    }
}

class CurrentDate {
    int day, month, year;

    void createDate() throws InvalidDayException, InvalidMonthException {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter day: ");
        day = sc.nextInt();

        System.out.print("Enter month: ");
        month = sc.nextInt();

        System.out.print("Enter year: ");
        year = sc.nextInt();

        if (month < 1 || month > 12) {
            throw new InvalidMonthException("Invalid Month: " + month);
        }

        int maxDays;
        boolean isLeapYear =
                (year % 400 == 0) ||
                (year % 4 == 0 && year % 100 != 0);

        switch (month) {
            case 1: case 3: case 5: case 7:
            case 8: case 10: case 12:
                maxDays = 31;
                break;

            case 4: case 6: case 9: case 11:
                maxDays = 30;
                break;

            case 2:
                maxDays = isLeapYear ? 29 : 28;
                break;

            default:
                maxDays = 0;
        }

        if (day < 1 || day > maxDays) {
            throw new InvalidDayException(
                "Invalid Day: " + day + " for month " + month
            );
        }

        System.out.println("Valid Date: " + day + "/" + month + "/" + year);
    }
}

public class e_DateError {
    public static void main(String[] args) {
        CurrentDate cd = new CurrentDate();

        try {
            cd.createDate();
        } catch (InvalidDayException | InvalidMonthException e) {
            System.out.println("Exception caught: " + e.getMessage());
        }
    }
}
