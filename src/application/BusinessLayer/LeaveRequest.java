package application.BusinessLayer;

import java.time.LocalDate;

public class LeaveRequest {
    private int id;
    private int residentId; // ID of the resident requesting leave
    private String reason; // Reason for leaving
    private LocalDate requestedDate; // Date when the leave request was submitted
    private LocalDate leaveDate; // Date when the resident plans to leave the hostel
    private String contactNumber; // Contact number of the resident
    private String forwardingAddress; // Address where resident wants correspondence to be forwarded
    private boolean approved; // Whether the leave request has been approved
    private LocalDate approvalDate; 
    private String approvalComments;
    private String approval_status; 

    

	// Constructors
    public LeaveRequest(int residentId, String reason, LocalDate requestedDate, LocalDate leaveDate,
                        String contactNumber, String forwardingAddress) {
        this.residentId = residentId;
        this.reason = reason;
        this.requestedDate = requestedDate;
        this.leaveDate = leaveDate;
        this.contactNumber = contactNumber;
        this.forwardingAddress = forwardingAddress;
        this.approved = false; // By default, leave request is not approved
    }

    public LeaveRequest(int id, int residentId, String reason, LocalDate requestedDate, LocalDate leaveDate,
                        String contactNumber, String forwardingAddress, boolean approved,
                        LocalDate approvalDate, String approvalComments, String approval_status) {
        this.id = id;
        this.residentId = residentId;
        this.reason = reason;
        this.requestedDate = requestedDate;
        this.leaveDate = leaveDate;
        this.contactNumber = contactNumber;
        this.forwardingAddress = forwardingAddress;
        this.approved = approved;
        this.approvalDate = approvalDate;
        this.approvalComments = approvalComments;
        this.approval_status = approval_status;
        
    }

    public LeaveRequest(int id, int residentId, String reason, LocalDate leaveDate, String contactNumber, String forwardingAddress, String approvalStatus, boolean approved) {
        this.id = id;
        this.residentId = residentId;
        this.reason = reason;
        this.leaveDate = leaveDate;
        this.contactNumber = contactNumber;
        this.forwardingAddress = forwardingAddress;
        this.approval_status = approvalStatus;
        this.approved = approved;
    }// Getters and Setters
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getResidentId() {
        return residentId;
    }

    public void setResidentId(int residentId) {
        this.residentId = residentId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public LocalDate getRequestedDate() {
        return requestedDate;
    }

    public void setRequestedDate(LocalDate requestedDate) {
        this.requestedDate = requestedDate;
    }

    public LocalDate getLeaveDate() {
        return leaveDate;
    }

    public void setLeaveDate(LocalDate leaveDate) {
        this.leaveDate = leaveDate;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getForwardingAddress() {
        return forwardingAddress;
    }

    public void setForwardingAddress(String forwardingAddress) {
        this.forwardingAddress = forwardingAddress;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public LocalDate getApprovalDate() {
        return approvalDate;
    }

    public void setApprovalDate(LocalDate approvalDate) {
        this.approvalDate = approvalDate;
    }

    public String getApprovalComments() {
        return approvalComments;
    }

    public void setApprovalComments(String approvalComments) {
        this.approvalComments = approvalComments;
    }

    public String getApproval_status() {
		return approval_status;
	}

	public void setApproval_status(String approval_status) {
		this.approval_status = approval_status;
	}
    @Override
    public String toString() {
        return 
                "Id = " + id +
                " - Resident Id = " + residentId +
                " - Reason = " + reason + 
                " - Status = " + approval_status                  
                ;
    }
}
