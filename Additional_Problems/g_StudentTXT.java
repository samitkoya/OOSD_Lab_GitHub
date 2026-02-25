/*
Write 5 student objects into "student7.txt" file.
Read and display students with sessional mark > 10.
*/

import java.io.*;

public class g_StudentTXT {

    static class Student {
        String name;
        int sessionalMark;
        int totalMark;

        Student(String name, int sessionalMark, int totalMark) {
            this.name          = name;
            this.sessionalMark = sessionalMark;
            this.totalMark     = totalMark;
        }

        void display() {
            System.out.println("Name: " + name +
                " | Sessional: " + sessionalMark +
                " | Total: " + totalMark);
        }
    }

    public static void main(String[] args) throws IOException {
        // Write 5 students to student7.txt in readable format
        BufferedWriter bw = new BufferedWriter(new FileWriter("Files/student7.txt"));
        bw.write("Alice,15,45");   bw.newLine();
        bw.write("Bob,8,30");      bw.newLine();
        bw.write("Charlie,12,50"); bw.newLine();
        bw.write("Diana,5,20");    bw.newLine();
        bw.write("Eve,18,55");     bw.newLine();
        bw.close();
        System.out.println("5 students written to student7.txt\n");

        // Read and display students with sessional mark > 10
        System.out.println("Students with sessional mark above 10:");
        BufferedReader br = new BufferedReader(new FileReader("Files/student7.txt"));
        String line;
        while ((line = br.readLine()) != null) {
            String[] parts = line.split(",");
            Student s = new Student(parts[0],
                                    Integer.parseInt(parts[1]),
                                    Integer.parseInt(parts[2]));
            if (s.sessionalMark > 10) {
                s.display();
            }
        }
        br.close();
    }
}
