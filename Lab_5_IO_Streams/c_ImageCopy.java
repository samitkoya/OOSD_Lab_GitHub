/* Design and implement a Java program that copies an image file (e.g., photo.jpg) from one 
location to another using byte streams. Use FileInputStream for reading and FileOutputStream 
for writing.
*/

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class c_ImageCopy {
    public static void main(String[] args) {

        String sourceImage = "Files(for_IO)/image.png";
        String destinationImage = "Files(for_IO)/image_copy.png";

        try (FileInputStream fis = new FileInputStream(sourceImage);
                FileOutputStream fos = new FileOutputStream(destinationImage)) {

            byte[] buffer = new byte[1024];
            int bytesRead;

            while ((bytesRead = fis.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }

            System.out.println("Image copied successfully using Byte Streams.");
            System.out.println("Source: " + sourceImage);
            System.out.println("Destination: " + destinationImage);

        } catch (IOException e) {
            System.err.println("Error copying image: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
