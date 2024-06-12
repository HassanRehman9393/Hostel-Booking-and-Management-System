package application.BusinessLayer;

public class CompareHostels {

    // Method to compare hostels by price
    public static HostelAdvertisement compareByPrice(HostelAdvertisement hostel1, HostelAdvertisement hostel2) {
        return (hostel1.getPrice() <= hostel2.getPrice()) ? hostel1 : hostel2;
    }

    // Method to compare hostels by facilities (Air Conditioner, Laundry, Transport)
    public static HostelAdvertisement compareByFacilities(HostelAdvertisement hostel1, HostelAdvertisement hostel2) {
        int count1 = 0;
        int count2 = 0;

        for (String facility : hostel1.getFacilities()) {
            if (facility.equals("Air Conditioner") || facility.equals("Laundry") || facility.equals("Transport")) {
                count1++;
            }
        }

        for (String facility : hostel2.getFacilities()) {
            if (facility.equals("Air Conditioner") || facility.equals("Laundry") || facility.equals("Transport")) {
                count2++;
            }
        }

        return (count1 >= count2) ? hostel1 : hostel2;
    }

    // Method to compare hostels by number of rooms
    public static HostelAdvertisement compareByRooms(HostelAdvertisement hostel1, HostelAdvertisement hostel2) {
        return (hostel1.getNumberOfRooms() >= hostel2.getNumberOfRooms()) ? hostel1 : hostel2;
    }

    // Example method to compare and get a summary
    public static String getComparisonSummary(HostelAdvertisement hostel1, HostelAdvertisement hostel2) {
        HostelAdvertisement cheaper = compareByPrice(hostel1, hostel2);
        HostelAdvertisement moreFacilities = compareByFacilities(hostel1, hostel2);
        HostelAdvertisement moreRooms = compareByRooms(hostel1, hostel2);

        return String.format(
            "Cheaper: %s%nMore Facilities: %s%nMore Rooms: %s",
            cheaper.getHostelName(),
            moreFacilities.getHostelName(),
            moreRooms.getHostelName()
        );
    }
}

