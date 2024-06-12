package application.BusinessLayer;

public class Student extends User {

    public Student(String name, String email, String contactNo, String password) {
        super(name, email, contactNo, password, "Student");
    }
}
