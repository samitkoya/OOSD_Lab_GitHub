/*
Write a Java program to generate prime numbers between n and m.(Hint: A 
prime number is a natural number greater than 1 that has no positive 
divisors other than 1 and itself. Eg: 2, 3, 5,7,11 etc.)
*/
import java.util.Scanner;       
public class a_PrimeNumber {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Enter the lower bound (n): ");
        int n = scanner.nextInt();
        System.out.print("Enter the upper bound (m): ");
        int m = scanner.nextInt();
        
        System.out.println("Prime numbers between " + n + " and " + m + " are:");
        for (int num = n; num <= m; num++) {
            if (isPrime(num)) {
                System.out.print(num + " ");
            }
        }
    }
    
    public static boolean isPrime(int number) {
        if (number <= 1) {
            return false;
        }
        for (int i = 2; i <= Math.sqrt(number); i++) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }
}