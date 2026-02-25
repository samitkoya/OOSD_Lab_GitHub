/*
Insert Anil, Sunil, Rahul into student details.
*/
import java.io.*;
import java.util.*;

public class i_StudentInsert {

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
        // Write initial students to student9.txt
        BufferedWriter bw = new BufferedWriter(new FileWriter("Files/student9.txt"));
        bw.write("Alice,15,45");   bw.newLine();
        bw.write("Bob,8,30");      bw.newLine();
        bw.write("Charlie,12,50"); bw.newLine();
        bw.write("Diana,5,20");    bw.newLine();
        bw.write("Eve,18,55");     bw.newLine();
        bw.close();

        // Read existing students
        List<Student> allStudents = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader("Files/student9.txt"));
        String line;
        while ((line = br.readLine()) != null) {
            String[] parts = line.split(",");
            allStudents.add(new Student(parts[0],
                                        Integer.parseInt(parts[1]),
                                        Integer.parseInt(parts[2])));
        }
        br.close();

        // Append new students
        allStudents.add(new Student("Anil",  12, 13));
        allStudents.add(new Student("Sunil",  8,  9));
        allStudents.add(new Student("Rahul", 13, 11));

        // Write all back to student9.txt
        bw = new BufferedWriter(new FileWriter("Files/student9.txt"));
        for (Student s : allStudents) {
            bw.write(s.name + "," + s.sessionalMark + "," + s.totalMark);
            bw.newLine();
        }
        bw.close();

        System.out.println("Updated student9.txt contents:");
        for (Student s : allStudents) {
            s.display();
        }
    }
}