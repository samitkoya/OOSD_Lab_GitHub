/*
Design a base class called Student with the following 2 fields:- (i) Name (ii) Id. 
Derive  2  classes called Sports  and  Exam  from  the  Student  base class.  Class 
Sports  has  a  field  called  s_grade  and  class  Exam  has  a  field  called  e_grade 
which are integer fields. Derive a class called Results which inherit from Sports 
and Exam. This class has a character array or string field to represent the final 
result.  Also  it  has  a  method  called  display  which  can  be  used  to  display  the 
result. Illustrate the usage of these classes in main().\
*/
import java.util.Scanner;
class Student {
    String name;
    int id;

    Student(String name, int id) {
        this.name = name;
        this.id = id;
    }
}
class Sports extends Student {
    int s_grade;

    Sports(String name, int id, int s_grade) {
        super(name, id);
        this.s_grade = s_grade;
    }
}
class Exam extends Student {
    int e_grade;

    Exam(String name, int id, int e_grade) {
        super(name, id);
        this.e_grade = e_grade;
    }
}
class Results extends Sports {
    int e_grade;
    String final_result;

    Results(String name, int id, int s_grade, int e_grade) {
        super(name, id, s_grade);
        this.e_grade = e_grade;
        this.final_result = calculateResult();
    }

    private String calculateResult() {
        int total = s_grade + e_grade;
        if (total >= 150) {
            return "A";
        } else if (total >= 100) {
            return "B";
        } else {
            return "C";
        }
    }

    void display() {
        System.out.println("Name: " + name);
        System.out.println("ID: " + id);
        System.out.println("Sports Grade: " + s_grade);
        System.out.println("Exam Grade: " + e_grade);
        System.out.println("Final Result: " + final_result);
    }
}
public class c_StudentResult {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Student Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Student ID: ");
        int id = scanner.nextInt();

        System.out.print("Enter Sports Grade (/100): ");
        int s_grade = scanner.nextInt();

        System.out.print("Enter Exam Grade (/100): ");
        int e_grade = scanner.nextInt();

        Results result = new Results(name, id, s_grade, e_grade);
        result.display();
    }
}