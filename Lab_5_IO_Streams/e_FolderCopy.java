/* Design a Java program that copies all files (not directories) from one folder (sourceFolder) 
to another folder (destFolder) using FileInputStream and FileOutputStream. Skip directories 
inside the source folder.
*/

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class e_FolderCopy {
    public static void main(String[] args) {

        String sourceFolder = "Files(for_IO)";
        String destFolder = "Files(for_IO)_copy";

        File srcDir = new File(sourceFolder);
        File destDir = new File(destFolder);

        if (!srcDir.exists() || !srcDir.isDirectory()) {
            System.err.println("Source folder does not exist or is not a directory: " + sourceFolder);
            return;
        }

        if (!destDir.exists()) {
            destDir.mkdirs();
            System.out.println("Destination folder created: " + destFolder);
        }

        File[] files = srcDir.listFiles();

        if (files == null || files.length == 0) {
            System.out.println("No files found in source folder.");
            return;
        }

        int filesCopied = 0;
        int directoriesSkipped = 0;

        for (File file : files) {
            if (file.isDirectory()) {
                System.out.println("Skipping directory: " + file.getName());
                directoriesSkipped++;
                continue;
            }

            File destFile = new File(destDir, file.getName());

            try (FileInputStream fis = new FileInputStream(file);
                    FileOutputStream fos = new FileOutputStream(destFile)) {

                byte[] buffer = new byte[1024];
                int bytesRead;

                while ((bytesRead = fis.read(buffer)) != -1) {
                    fos.write(buffer, 0, bytesRead);
                }

                System.out.println("Copied: " + file.getName());
                filesCopied++;

            } catch (IOException e) {
                System.err.println("Error copying file " + file.getName() + ": " + e.getMessage());
            }
        }

        System.out.println("\nCopy operation completed.");
        System.out.println("Files copied: " + filesCopied);
        System.out.println("Directories skipped: " + directoriesSkipped);
        System.out.println("Source: " + sourceFolder);
        System.out.println("Destination: " + destFolder);
    }
}
