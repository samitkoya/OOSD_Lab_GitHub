/* Design and implement a Java application that copies the contents of one file to another 
using byte streams. The program must use FileInputStream to read data from a source file 
and FileOutputStream to write the same data to a destination file.
*/

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class a_ByteStreamCopy {
    public static void main(String[] args) {

        String sourceFile = "source.txt";
        String destinationFile = "destination.txt";

        try (FileInputStream fis = new FileInputStream(sourceFile);
                FileOutputStream fos = new FileOutputStream(destinationFile)) {

            int byteData;
            while ((byteData = fis.read()) != -1) {
                fos.write(byteData);
            }

            System.out.println("File copied successfully using Byte Streams.");
            System.out.println("Source: " + sourceFile);
            System.out.println("Destination: " + destinationFile);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
