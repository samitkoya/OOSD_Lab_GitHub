/*
Write all palindrome words from a file into a new file
*/

import java.io.*;

public class e_Palindrome {
    public static void main(String[] args) throws IOException {
        String inputFile  = "Files/palindromeinput.txt";
        String outputFile = "Files/palindromeoutput.txt";

        BufferedReader br = new BufferedReader(new FileReader(inputFile));
        BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile));
        String line;

        while ((line = br.readLine()) != null) {
            String[] words = line.split("\\s+");
            for (String word : words) {
                String clean = word.toLowerCase().replaceAll("[^a-z0-9]", "");
                if (clean.length() > 0) {
                    String reversed = new StringBuilder(clean).reverse().toString();
                    if (clean.equals(reversed)) {
                        bw.write(word);
                        bw.newLine();
                    }
                }
            }
        }

        br.close();
        bw.close();
        System.out.println("Palindromes written to: " + outputFile);
    }
}
