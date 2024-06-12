package application.BusinessLayer;

public class SearchCriteria {

    private String location;
    private String priceRange;
    private boolean airConditioner;
    private boolean laundry;
    private boolean transport;

    public SearchCriteria(String location, String priceRange, boolean airConditioner, boolean laundry, boolean transport) {
        this.location = location;
        this.priceRange = priceRange;
        this.airConditioner = airConditioner;
        this.laundry = laundry;
        this.transport = transport;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPriceRange() {
        return priceRange;
    }

    public void setPriceRange(String priceRange) {
        this.priceRange = priceRange;
    }

    public boolean isAirConditioner() {
        return airConditioner;
    }

    public void setAirConditioner(boolean airConditioner) {
        this.airConditioner = airConditioner;
    }

    public boolean isLaundry() {
        return laundry;
    }

    public void setLaundry(boolean laundry) {
        this.laundry = laundry;
    }

    public boolean isTransport() {
        return transport;
    }

    public void setTransport(boolean transport) {
        this.transport = transport;
    }
}
