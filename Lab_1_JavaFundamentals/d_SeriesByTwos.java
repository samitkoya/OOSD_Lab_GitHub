/*
Design an interface called Series with the following methods :-
i) GetNext (returns the next number in series)  
ii) reset(to restart the series)  
iii) setStart (to set the value from which the series should start)  
 
Design a class named ByTwos that will implement the methods of the interface 
Series such that it generates a series of numbers, each two greater than the previous 
one. Also design a class which will include the main method for referencing the 
interface.
*/

import java.util.Scanner;
interface Series {
    int getNext();
    void reset();
    void setStart(int start);
}

class ByTwos implements Series {
    private int current;
    private int start;

    public ByTwos() {
        start = 0;
        current = start;
    }

    public int getNext() {
        int nextValue = current;
        current += 2;
        return nextValue;
    }

    public void reset() {
        current = start;
    }

    public void setStart(int start) {
        this.start = start;
        this.current = start;
    }
}

public class d_SeriesByTwos {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Series s;              
        s = new ByTwos();      
        System.out.println("Which number to start from?");
        int n = sc.nextInt();
        s.setStart(n);

        System.out.println("Series by twos:");
        for (int i = 0; i < 3; i++) {
            System.out.println(s.getNext());
        }

        System.out.println("After reset:");
        s.reset();
        for (int i = 0; i < 5; i++) {
            System.out.println(s.getNext());
        }
    }
}
