/*
Read student details and display students whose total mark is more than 20.
*/

import java.io.*;

public class h_StudentTotal {

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
        // Write student data to student8.txt in readable format
        BufferedWriter bw = new BufferedWriter(new FileWriter("Files/student8.txt"));
        bw.write("Alice,15,45");   bw.newLine();
        bw.write("Bob,8,30");      bw.newLine();
        bw.write("Charlie,12,50"); bw.newLine();
        bw.write("Diana,5,20");    bw.newLine();
        bw.write("Eve,18,55");     bw.newLine();
        bw.close();
        System.out.println("Students written to student8.txt\n");

        // Read and display students with total mark > 20
        System.out.println("Students with total mark above 20:");
        BufferedReader br = new BufferedReader(new FileReader("Files/student8.txt"));
        String line;
        while ((line = br.readLine()) != null) {
            String[] parts = line.split(",");
            Student s = new Student(parts[0],
                                    Integer.parseInt(parts[1]),
                                    Integer.parseInt(parts[2]));
            if (s.totalMark > 20) {
                s.display();
            }
        }
        br.close();
    }
}