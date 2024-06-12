package application.BusinessLayer;

import java.util.ArrayList;
import java.util.List;

public class HostelAdvertisement {
    private String hostelName;
    private String[] facilities;
    private String location;
    private int numberOfRooms;
    private double price;
    private String hostelImage;
    private List<Room> rooms;

    // Constructor
    public HostelAdvertisement(String hostelName, String[] facilities, String location, int numberOfRooms, double price, String hostelImage) {
        this.hostelName = hostelName;
        this.facilities = facilities;
        this.location = location;
        this.numberOfRooms = numberOfRooms;
        this.price = price;
        this.hostelImage = hostelImage;
        this.rooms = new ArrayList<>();
    }

    // Getters and Setters
    public String getHostelName() {
        return hostelName;
    }

    public void setHostelName(String hostelName) {
        this.hostelName = hostelName;
    }

    public String[] getFacilities() {
        return facilities;
    }

    public void setFacilities(String[] facilities) {
        this.facilities = facilities;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getNumberOfRooms() {
        return numberOfRooms;
    }

    public void setNumberOfRooms(int numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getHostelImage() {
        return hostelImage;
    }

    public void setHostelImage(String hostelImage) {
        this.hostelImage = hostelImage;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    public void addRoom(Room room) {
        if (rooms.size() < numberOfRooms) {
            rooms.add(room);
        }
    }
}
