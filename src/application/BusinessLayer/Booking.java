package application.BusinessLayer;

public class Booking {
    private String HostelName;
    private int roomId;
    private String name;
    private int age;
    private String email;
    private String contactNo;
    private String roomType;

	public Booking(String HostelName, int roomId, String name, int age, String email, String contactNo,
			String roomType) {
		this.HostelName = HostelName;
		this.roomId = roomId;
		this.name = name;
		this.age = age;
		this.email = email;
		this.contactNo = contactNo;
		this.roomType = roomType;
	}
	public String getHostelName() {
		return HostelName;
	}
	
	public void setHostelName(String HostelName) {
		this.HostelName = HostelName;
	}
	
	public int getRoomId() {
		return roomId;
	}
	
	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getAge() {
		return age;
	}
	
	public void setAge(int age) {
		this.age = age;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getContactNo() {
		return contactNo;
	}
	
	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}
	
	public String getRoomType() {
		return roomType;
	}
	
	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}
}
