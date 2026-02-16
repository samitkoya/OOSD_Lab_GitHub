/* Write a Java program that reads a text file (input.txt) using BufferedReader and appends 
its content to another file (output.txt) using BufferedWriter in append mode.
*/

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class d_BufferedAppend {
    public static void main(String[] args) {

        String inputFile = "Files(for_IO)/input.txt";
        String outputFile = "Files(for_IO)/output.txt";

        try (BufferedReader br = new BufferedReader(new FileReader(inputFile));
                BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile, true))) {

            String line;
            while ((line = br.readLine()) != null) {
                bw.write(line);
                bw.newLine();
            }

            System.out.println("Content appended successfully using BufferedReader and BufferedWriter.");
            System.out.println("Source: " + inputFile);
            System.out.println("Destination: " + outputFile + " (append mode)");

        } catch (IOException e) {
            System.err.println("Error appending content: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
