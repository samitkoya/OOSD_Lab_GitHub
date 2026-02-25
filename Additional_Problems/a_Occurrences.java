/*
Find the occurrence of a word in a given file
*/

import java.io.*;

public class a_Occurrences {
    public static void main(String[] args) throws IOException {
        String filePath = "Files/input.txt";
        String searchWord = "java";
        int count = 0;

        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String line;

        while ((line = br.readLine()) != null) {
            String[] words = line.split("\\s+");
            for (String word : words) {
                if (word.equalsIgnoreCase(searchWord)) {
                    count++;
                }
            }
        }
        br.close();

        System.out.println("Occurrence of \"" + searchWord + "\": " + count);
    }
}
