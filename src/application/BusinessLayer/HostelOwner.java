package application.BusinessLayer;

public class HostelOwner extends User {

    public HostelOwner(String name, String email, String contactNo, String password) {
        super(name, email, contactNo, password, "Hostel Owner");
    }
}
