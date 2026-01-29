/*
Write a program to print all combinations of four-digit number. A four-digit 
number is generated using only four digits {1, 2, 3, 4}.  
• Case 1: Duplication of digit is allowed.  
• Case 2: Duplication of digit is not allowed.
*/
public class b_FourDigitCombi {
    public static void main(String[] args) {
        System.out.println("Case 1: Duplication of digit is allowed.");
        for (int i = 1; i <= 4; i++) {
            for (int j = 1; j <= 4; j++) {
                for (int k = 1; k <= 4; k++) {
                    for (int l = 1; l <= 4; l++) {
                        System.out.println("" + i + j + k + l);
                    }
                }
            }
        }

        System.out.println("\nCase 2: Duplication of digit is not allowed.");
        for (int i = 1; i <= 4; i++) {
            for (int j = 1; j <= 4; j++) {
                if (j == i) continue;
                for (int k = 1; k <= 4; k++) {
                    if (k == i || k == j) continue;
                    for (int l = 1; l <= 4; l++) {
                        if (l == i || l == j || l == k) continue;
                        System.out.println("" + i + j + k + l);
                    }
                }
            }
        }
    }
}