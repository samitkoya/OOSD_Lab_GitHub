/*
Student class with name and sessional mark,
constructor to initialize, method to display details
*/

public class f_StudentMain {

    static class Student {
        String name;
        int sessionalMark;

        Student(String name, int sessionalMark) {
            this.name          = name;
            this.sessionalMark = sessionalMark;
        }

        void display() {
            System.out.println("Name          : " + name);
            System.out.println("Sessional Mark: " + sessionalMark);
            System.out.println(" ");
        }
    }

    public static void main(String[] args) {
        Student s1 = new Student("Alice",   18);
        Student s2 = new Student("Bob",     12);
        Student s3 = new Student("Charlie",  9);

        s1.display();
        s2.display();
        s3.display();
    }
}

