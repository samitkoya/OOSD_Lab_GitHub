/*
Java program to remove comments from the file 
*/

import java.io.*;

public class c_Comments {
    public static void main(String[] args) throws IOException {
        String inputFile  = "Files/hello.java";
        String outputFile = "Files/hello_1.java";

        BufferedReader br = new BufferedReader(new FileReader(inputFile));
        BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile));

        String line;
        boolean inBlockComment = false;

        while ((line = br.readLine()) != null) {
            StringBuilder result = new StringBuilder();
            int i = 0;

            while (i < line.length()) {
                if (inBlockComment) {
                    if (i + 1 < line.length() &&
                        line.charAt(i) == '*' && line.charAt(i + 1) == '/') {
                        inBlockComment = false;
                        i += 2;
                    } else {
                        i++;
                    }
                } else {
                    if (i + 1 < line.length() &&
                        line.charAt(i) == '/' && line.charAt(i + 1) == '/') {
                        break; // rest of line is a comment
                    } else if (i + 1 < line.length() &&
                               line.charAt(i) == '/' && line.charAt(i + 1) == '*') {
                        inBlockComment = true;
                        i += 2;
                    } else {
                        result.append(line.charAt(i));
                        i++;
                    }
                }
            }

            String cleaned = result.toString().trim();
            if (!cleaned.isEmpty()) {
                bw.write(cleaned);
                bw.newLine();
            }
        }

        br.close();
        bw.close();
        System.out.println("Comments removed. Output saved to: " + outputFile);
    }
}
