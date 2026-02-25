/*
Find two replica (duplicate) files in a directory
*/

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class b_Replica {
    public static void main(String[] args) throws IOException {
        File dir = new File("Files/hello.java");
        File[] files = dir.listFiles((d, name) -> name.endsWith(".java"));

        if (files == null || files.length == 0) {
            System.out.println("No Java files found.");
            return;
        }

        boolean found = false;
        for (int i = 0; i < files.length; i++) {
            for (int j = i + 1; j < files.length; j++) {
                if (filesAreEqual(files[i], files[j])) {
                    System.out.println("Replica files found:");
                    System.out.println("  File 1: " + files[i].getName());
                    System.out.println("  File 2: " + files[j].getName());
                    found = true;
                }
            }
        }

        if (!found) {
            System.out.println("No replica files found.");
        }
    }

    static boolean filesAreEqual(File f1, File f2) throws IOException {
        if (f1.length() != f2.length()) return false;
        byte[] b1 = Files.readAllBytes(f1.toPath());
        byte[] b2 = Files.readAllBytes(f2.toPath());
        return Arrays.equals(b1, b2);
    }
}