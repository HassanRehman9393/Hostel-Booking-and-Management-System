package application.BusinessLayer;

public abstract class User {
    private String name;
    private String email;
    private String contactNo;
    private String password;
    private String userType;

    public User(String name, String email, String contactNo, String password, String userType) {
        this.name = name;
        this.email = email;
        this.contactNo = contactNo;
        this.password = password;
        this.userType = userType;
    }

    // Getters and setters

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getContactNo() {
        return contactNo;
    }

    public String getPassword() {
        return password;
    }

    public String getUserType() {
        return userType;
    }
}
