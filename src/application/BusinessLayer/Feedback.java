package application.BusinessLayer;

public class Feedback {
    private double ratings;
    private String feedback;
    private String userName;
    private String hostelName;

    public Feedback(double ratings, String feedback,String userName, String hostelName) {
        this.ratings = ratings;
        this.feedback = feedback;
        this.userName = userName;
        this.hostelName = hostelName;
    }

    public double getRatings() {
        return ratings;
    }

    public String getFeedback() {
        return feedback;
    }

    public String getHostelName() {
        return hostelName;
    }
    
	public void setRatings(double ratings) {
		this.ratings = ratings;
	}
	
	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}
	
	public void setHostelName(String hostelName) {
		this.hostelName = hostelName;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@Override
	public String toString() {
		return "Feedback [ratings=" + ratings + ", feedback=" + feedback + ", userName=" + userName + ", hostelName="
				+ hostelName + "]";
	}
	
}
