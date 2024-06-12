package application.BusinessLayer;

public class UserFactory {

    public static User createUser(String userType, String name, String email, String contactNo, String password) {
        if ("Student".equalsIgnoreCase(userType)) {
            return new Student(name, email, contactNo, password);
        } else if ("Hostel Owner".equalsIgnoreCase(userType)) {
            return new HostelOwner(name, email, contactNo, password);
        } else {
            throw new IllegalArgumentException("Unknown user type: " + userType);
        }
    }
}
