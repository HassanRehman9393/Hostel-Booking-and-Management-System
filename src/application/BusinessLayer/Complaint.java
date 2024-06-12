package application.BusinessLayer;

import java.sql.Timestamp;

public class Complaint {
    private int id;
    private String sender;
    private String recipient;
    private String complain;
    private String description;
    private Timestamp timestamp;

    public Complaint(int id, String sender, String recipient, String complain, String description, Timestamp timestamp) {
        this.id = id;
        this.sender = sender;
        this.recipient = recipient;
        this.complain = complain;
        this.description = description;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public String getSender() {
        return sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getcomplain() {
        return complain;
    }

    public String getdescription() {
        return description;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return complain + " (" + timestamp.toString() + ")";
    }
}
