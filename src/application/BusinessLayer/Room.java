package application.BusinessLayer;

public class Room {
	private static final String SINGLE = "Single";
	private static final String DOUBLE = "Double";
	private static final String TRIPLE = "Triple";

	private String roomType;
	private int availableRooms;
	private boolean isAvailable;

	public Room(String roomType, int availableRooms, boolean isAvailable) {
		this.roomType = roomType;
		this.availableRooms = availableRooms;
		this.isAvailable = isAvailable;
	}

	public String getRoomType() {
		return roomType;
	}

	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}

	public int getAvailableRooms() {
		return availableRooms;
	}

	public void setAvailableRooms(int availableRooms) {
		this.availableRooms = availableRooms;
	}

	public boolean isAvailable() {
		return isAvailable;
	}

	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

	public String getRoomTypeAsString() {
		switch (getRoomType()) {
		case SINGLE:
			return "Single";
		case DOUBLE:
			return "Double";
		case TRIPLE:
			return "Triple";
		default:
			throw new IllegalArgumentException("Invalid room type");
		}
	}
}
