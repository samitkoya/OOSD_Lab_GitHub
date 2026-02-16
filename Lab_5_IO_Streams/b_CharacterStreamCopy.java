/* Design and implement a Java application that reads textual data from an existing text file 
using FileReader and writes the same content into another text file using FileWriter.
*/

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class b_CharacterStreamCopy {
    public static void main(String[] args) {

        String sourceFile = "Files(for_IO)/source.txt";
        String destinationFile = "Files(for_IO)/destination.txt";

        try (FileReader fr = new FileReader(sourceFile);
                FileWriter fw = new FileWriter(destinationFile)) {

            int charData;
            while ((charData = fr.read()) != -1) {
                fw.write(charData);
            }

            System.out.println("File copied successfully using Character Streams.");
            System.out.println("Source: " + sourceFile);
            System.out.println("Destination: " + destinationFile);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
