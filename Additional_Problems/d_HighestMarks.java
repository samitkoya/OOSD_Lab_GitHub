/*
Display the student who scored the highest mark
File: student.txt  Format: regno : name : mark
*/

import java.io.*;

public class d_HighestMarks {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("Files/student4.txt"));
        String line;
        String topName  = "";
        String topRegno = "";
        int highestMark = Integer.MIN_VALUE;

        while ((line = br.readLine()) != null) {
            String[] parts = line.split(":");
            if (parts.length == 3) {
                String regno = parts[0].trim();
                String name  = parts[1].trim();
                int mark     = Integer.parseInt(parts[2].trim());

                if (mark > highestMark) {
                    highestMark = mark;
                    topName     = name;
                    topRegno    = regno;
                }
            }
        }
        br.close();

        System.out.println("Student with highest mark:");
        System.out.println("  Regno : " + topRegno);
        System.out.println("  Name  : " + topName);
        System.out.println("  Mark  : " + highestMark);
    }
}
