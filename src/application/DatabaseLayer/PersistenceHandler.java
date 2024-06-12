package application.DatabaseLayer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import application.BusinessLayer.Booking;
import application.BusinessLayer.Complaint;
import application.BusinessLayer.Feedback;
import application.BusinessLayer.HostelAdvertisement;
import application.BusinessLayer.LeaveRequest;
import application.BusinessLayer.Room;
import application.BusinessLayer.SearchCriteria;
import application.BusinessLayer.User;
import javafx.collections.FXCollections;

public class PersistenceHandler {
	private static final String DB_URL = "jdbc:mysql://localhost:3306/project_hms";
	private static final String DB_USER = "root";
	private static final String DB_PASSWORD = "viper9393";

	private static PersistenceHandler instance = null;

	private PersistenceHandler() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static synchronized PersistenceHandler getInstance() {
		if (instance == null) {
			instance = new PersistenceHandler();
		}
		return instance;
	}

	public boolean authenticate(String username, String password) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		boolean isAuthenticated = false;

		try {
			connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

			String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
			statement = connection.prepareStatement(sql);
			statement.setString(1, username);
			statement.setString(2, password);

			resultSet = statement.executeQuery();

			if (resultSet.next()) {
				isAuthenticated = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (statement != null) {
					statement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return isAuthenticated;
	}

	public boolean saveUser(User user) {
		Connection connection = null;
		PreparedStatement statement = null;
		boolean isSaved = false;

		try {
			connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

			String sql = "INSERT INTO users (username, email, contactNo, password, userType) VALUES (?, ?, ?, ?, ?)";
			statement = connection.prepareStatement(sql);
			statement.setString(1, user.getName());
			statement.setString(2, user.getEmail());
			statement.setString(3, user.getContactNo());
			statement.setString(4, user.getPassword());
			statement.setString(5, user.getUserType());

			int rowsInserted = statement.executeUpdate();
			if (rowsInserted > 0) {
				isSaved = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return isSaved;
	}

	public boolean saveAdvertisement(HostelAdvertisement advertisement) {
		Connection connection = null;
		PreparedStatement statement = null;
		boolean isSaved = false;

		try {
			connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

			String sql = "INSERT INTO hostel_advertisements (hostel_name, facilities, location, number_of_rooms, price, hostel_image_url) VALUES (?, ?, ?, ?, ?, ?)";
			statement = connection.prepareStatement(sql);
			statement.setString(1, advertisement.getHostelName());
			statement.setString(2, String.join(",", advertisement.getFacilities())); // Join facilities with commas
			statement.setString(3, advertisement.getLocation());
			statement.setInt(4, advertisement.getNumberOfRooms());
			statement.setDouble(5, advertisement.getPrice());
			statement.setString(6, advertisement.getHostelImage());

			int rowsInserted = statement.executeUpdate();
			if (rowsInserted > 0) {
				isSaved = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return isSaved;
	}

	public List<HostelAdvertisement> getHostelAdvertisements() {
		List<HostelAdvertisement> hostels = new ArrayList<>();
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

			String sql = "SELECT * FROM hostel_advertisements";
			statement = connection.prepareStatement(sql);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				// Split facilities by commas to get array
				String[] facilities = resultSet.getString("facilities").split(",");
				HostelAdvertisement hostel = new HostelAdvertisement(resultSet.getString("hostel_name"), facilities,
						resultSet.getString("location"), resultSet.getInt("number_of_rooms"),
						resultSet.getDouble("price"), resultSet.getString("hostel_image_url"));
				hostels.add(hostel);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (statement != null) {
					statement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return hostels;
	}

	public List<HostelAdvertisement> searchHostels(SearchCriteria criteria) {
		List<HostelAdvertisement> hostels = new ArrayList<>();
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

			StringBuilder sql = new StringBuilder("SELECT * FROM hostel_advertisements WHERE 1=1");

			// Append criteria to the SQL query
			if (criteria.getLocation() != null && !criteria.getLocation().isEmpty()) {
				sql.append(" AND location LIKE ?");
			}
			if (criteria.getPriceRange() != null && !criteria.getPriceRange().isEmpty()) {
				sql.append(" AND price <= ?");
			}
			if (criteria.isAirConditioner() || criteria.isLaundry() || criteria.isTransport()) {
				sql.append(" AND (");

				boolean first = true;

				if (criteria.isAirConditioner()) {
					sql.append("facilities LIKE ?");
					first = false;
				}
				if (criteria.isLaundry()) {
					if (!first) {
						sql.append(" OR ");
					}
					sql.append("facilities LIKE ?");
					first = false;
				}
				if (criteria.isTransport()) {
					if (!first) {
						sql.append(" OR ");
					}
					sql.append("facilities LIKE ?");
				}

				sql.append(")");
			}

			statement = connection.prepareStatement(sql.toString());
			int paramIndex = 1;

			// Set parameters for the prepared statement
			if (criteria.getLocation() != null && !criteria.getLocation().isEmpty()) {
				statement.setString(paramIndex++, "%" + criteria.getLocation() + "%");
			}
			if (criteria.getPriceRange() != null && !criteria.getPriceRange().isEmpty()) {
				statement.setDouble(paramIndex++, Double.parseDouble(criteria.getPriceRange()));
			}
			if (criteria.isAirConditioner()) {
				statement.setString(paramIndex++, "%Air Conditioner%");
			}
			if (criteria.isLaundry()) {
				statement.setString(paramIndex++, "%Laundry%");
			}
			if (criteria.isTransport()) {
				statement.setString(paramIndex++, "%Transport%");
			}

			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				String[] facilities = resultSet.getString("facilities").split(",");
				HostelAdvertisement hostel = new HostelAdvertisement(resultSet.getString("hostel_name"), facilities,
						resultSet.getString("location"), resultSet.getInt("number_of_rooms"),
						resultSet.getDouble("price"), resultSet.getString("hostel_image_url"));
				hostels.add(hostel);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (statement != null) {
					statement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return hostels;
	}

	public HostelAdvertisement getHostelAdvertisement(String hostelName) {
		HostelAdvertisement hostel = null;
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

			String sql = "SELECT * FROM hostel_advertisements WHERE hostel_name = ?";
			statement = connection.prepareStatement(sql);
			statement.setString(1, hostelName);
			resultSet = statement.executeQuery();

			if (resultSet.next()) {
				// Split facilities by commas to get array
				String[] facilities = resultSet.getString("facilities").split(",");
				hostel = new HostelAdvertisement(resultSet.getString("hostel_name"), facilities,
						resultSet.getString("location"), resultSet.getInt("number_of_rooms"),
						resultSet.getDouble("price"), resultSet.getString("hostel_image_url"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// Close resources
		}

		return hostel;
	}

	public boolean updateAdvertisement(HostelAdvertisement updatedHostel, String oldHostelName) {
		Connection connection = null;
		PreparedStatement statement = null;
		boolean isUpdated = false;

		try {
			connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

			String sql = "UPDATE hostel_advertisements SET hostel_name = ?, facilities = ?, location = ?, number_of_rooms = ?, price = ?, hostel_image_url = ? WHERE hostel_name = ?";
			statement = connection.prepareStatement(sql);
			statement.setString(1, updatedHostel.getHostelName());
			statement.setString(2, String.join(",", updatedHostel.getFacilities()));
			statement.setString(3, updatedHostel.getLocation());
			statement.setInt(4, updatedHostel.getNumberOfRooms());
			statement.setDouble(5, updatedHostel.getPrice());
			statement.setString(6, updatedHostel.getHostelImage());
			statement.setString(7, oldHostelName);

			int rowsAffected = statement.executeUpdate();
			isUpdated = rowsAffected > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return isUpdated;
	}

	public boolean deleteAdvertisement(String hostelName) {
		Connection connection = null;
		PreparedStatement statement = null;
		boolean isDeleted = false;

		try {
			connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

			// Delete associated rooms
			String deleteRoomsSql = "DELETE FROM room WHERE hostelId = (SELECT id FROM hostel_advertisements WHERE hostel_name =?)";
			statement = connection.prepareStatement(deleteRoomsSql);
			statement.setString(1, hostelName);
			statement.executeUpdate();

			// Delete hostel advertisement
			String deleteHostelSql = "DELETE FROM hostel_advertisements WHERE hostel_name =?";
			statement = connection.prepareStatement(deleteHostelSql);
			statement.setString(1, hostelName);

			int rowsAffected = statement.executeUpdate();
			isDeleted = rowsAffected > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return isDeleted;
	}

	public boolean saveRoom(String hostelName, Room room) {
		Connection connection = null;
		PreparedStatement statement = null;
		boolean isSaved = false;

		try {
			connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

			String sql = "INSERT INTO Room (hostelId, roomType, availableRooms, isAvailable) VALUES ((SELECT id FROM hostel_advertisements WHERE hostel_name = ?), ?, ?, ?)";
			statement = connection.prepareStatement(sql);
			statement.setString(1, hostelName);
			statement.setString(2, room.getRoomType());
			statement.setInt(3, room.getAvailableRooms());
			statement.setBoolean(4, room.isAvailable());

			int rowsInserted = statement.executeUpdate();
			if (rowsInserted > 0) {
				isSaved = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return isSaved;
	}

	public boolean updateRoom(String hostelName, Room room) {
		Connection connection = null;
		PreparedStatement statement = null;
		boolean isUpdated = false;

		try {
			connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

			String sql = "UPDATE Room SET availableRooms = ?, isAvailable = ? WHERE hostelId = (SELECT id FROM hostel_advertisements WHERE hostel_name = ?) AND roomType = ?";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, room.getAvailableRooms());
			statement.setBoolean(2, room.isAvailable());
			statement.setString(3, hostelName);
			statement.setString(4, room.getRoomType());
			int rowsUpdated = statement.executeUpdate();
			if (rowsUpdated > 0) {
				isUpdated = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return isUpdated;
	}

	public List<Room> getRooms(String hostelName) {
		List<Room> rooms = new ArrayList<>();
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

			String sql = "SELECT * FROM Room WHERE hostelId = (SELECT id FROM hostel_advertisements WHERE hostel_name = ?)";
			statement = connection.prepareStatement(sql);
			statement.setString(1, hostelName);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				Room room = new Room(resultSet.getString("roomType"), resultSet.getInt("availableRooms"),
						resultSet.getBoolean("isAvailable"));
				rooms.add(room);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (statement != null) {
					statement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return rooms;
	}

	public Room getRoom(int roomId) {
		Room room = null;
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

			String sql = "SELECT * FROM Room WHERE roomId =?";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, roomId);
			resultSet = statement.executeQuery();

			if (resultSet.next()) {
				room = new Room(resultSet.getString("roomType"), resultSet.getInt("availableRooms"),
						resultSet.getBoolean("isAvailable"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (statement != null) {
					statement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return room;
	}

	public int bookingId(String hostelName, String roomType) {
		int roomId = 0;
		try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
			PreparedStatement pstmt = conn.prepareStatement(
					"SELECT r.roomId " + "FROM Room r " + "JOIN hostel_advertisements ha ON r.hostelId = ha.id "
							+ "WHERE ha.hostel_name =? AND r.roomType =?");
			pstmt.setString(1, hostelName);
			pstmt.setString(2, roomType);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				roomId = rs.getInt("roomId");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return roomId;
	}

	public void saveBooking(Booking booking) {
		String sql = "INSERT INTO Booking (hostelName, roomId, name, age, email, contactNo, roomType) VALUES (?,?,?,?,?,?,?)";
		try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
				PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, booking.getHostelName());
			statement.setInt(2, booking.getRoomId());
			statement.setString(3, booking.getName());
			statement.setInt(4, booking.getAge());
			statement.setString(5, booking.getEmail());
			statement.setString(6, booking.getContactNo());
			statement.setString(7, booking.getRoomType());
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int getHostelId(String hostelName) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		int hostelId = -1;

		try {
			connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

			String sql = "SELECT id FROM hostel_advertisements WHERE hostel_name = ?";
			statement = connection.prepareStatement(sql);
			statement.setString(1, hostelName);

			resultSet = statement.executeQuery();

			if (resultSet.next()) {
				hostelId = resultSet.getInt("id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (statement != null) {
					statement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return hostelId;
	}

	public int getRoomId(int hostelId, String roomType) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		int roomId = -1;

		try {
			connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

			String sql = "SELECT roomId FROM Room WHERE hostelId = ? AND roomType = ?";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, hostelId);
			statement.setString(2, roomType);

			resultSet = statement.executeQuery();

			if (resultSet.next()) {
				roomId = resultSet.getInt("roomId");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (statement != null) {
					statement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return roomId;
	}

	private List<Complaint> complains;

	public List<Complaint> loadEmailsFromDatabase() {
		complains = new ArrayList<>();
		try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery("SELECT * FROM complains ORDER BY timestamp DESC")) {

			while (resultSet.next()) {
				Complaint email = new Complaint(resultSet.getInt("id"), resultSet.getString("sender"),
						resultSet.getString("recipient"), resultSet.getString("complain"),
						resultSet.getString("description"), resultSet.getTimestamp("timestamp"));
				complains.add(email);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return complains;
	}

	public void addEmailToDatabase(Complaint complain) {
		try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
				PreparedStatement statement = connection.prepareStatement(
						"INSERT INTO complains (sender, recipient, complain, description, timestamp) VALUES (?,?,?,?,?)")) {

			statement.setString(1, complain.getSender());
			statement.setString(2, complain.getRecipient());
			statement.setString(3, complain.getcomplain());
			statement.setString(4, complain.getdescription());
			statement.setTimestamp(5, complain.getTimestamp());
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean saveLeaveRequestToDatabase(LeaveRequest leaveRequest) {
		try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
			// Prepare a SQL statement to insert leave request into database
			String sql = "INSERT INTO leave_requests (resident_id, reason, requested_date, leave_date, contact_number, forwarding_address) VALUES (?, ?, ?, ?, ?, ?)";
			try (PreparedStatement statement = connection.prepareStatement(sql)) {
				statement.setInt(1, leaveRequest.getResidentId()); // Set resident ID
				statement.setString(2, leaveRequest.getReason()); // Set reason for leaving
				statement.setDate(3, java.sql.Date.valueOf(java.time.LocalDate.now())); // Set current system date as
																						// request date
				statement.setDate(4, java.sql.Date.valueOf(leaveRequest.getLeaveDate())); // Set leave date
				statement.setString(5, leaveRequest.getContactNumber()); // Set contact number
				statement.setString(6, leaveRequest.getForwardingAddress()); // Set forwarding address

				// Execute the statement
				int rowsInserted = statement.executeUpdate();

				// Check if any rows were inserted
				return rowsInserted > 0; // Return true if rows were inserted, otherwise false
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false; // Return false if an exception occurs
		}
	}

	public void updateLeaveRequestToDatabase(LeaveRequest leaveRequest) {
		try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
			String sql = "UPDATE leave_requests SET approved = ?, approval_date = ?, approval_comments = ?, approval_status = ? WHERE id = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setBoolean(1, leaveRequest.isApproved());
			statement.setDate(2, java.sql.Date.valueOf(leaveRequest.getApprovalDate()));
			statement.setString(3, leaveRequest.getApprovalComments());
			statement.setString(4, leaveRequest.getApproval_status());
			statement.setInt(5, leaveRequest.getId());
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<LeaveRequest> loadLeaveRequestsFromDatabase() {
		List<LeaveRequest> leaveRequests = new ArrayList<>();

		try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
				PreparedStatement statement = connection.prepareStatement("SELECT * FROM leave_requests");
				ResultSet resultSet = statement.executeQuery()) {

			while (resultSet.next()) {
				LeaveRequest leaveRequest = new LeaveRequest(resultSet.getInt("id"), resultSet.getInt("resident_id"),
						resultSet.getString("reason"), resultSet.getDate("leave_date").toLocalDate(),
						resultSet.getString("contact_number"), resultSet.getString("forwarding_address"),
						resultSet.getString("approval_status")
						// Add more attributes as per your database schema
						, false);
				leaveRequests.add(leaveRequest);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			// Handle SQL exception
		}

		return leaveRequests;
	}

	public boolean saveFeedbackToDatabase(Feedback feedback) {
	    String sql = "INSERT INTO Feedback (userName, ratings, feedback, hostelName) VALUES (?, ?, ?, ?)";
	    try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
	         PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
	        preparedStatement.setString(1, feedback.getUserName());
	        preparedStatement.setDouble(2, feedback.getRatings());
	        preparedStatement.setString(3, feedback.getFeedback());
	        preparedStatement.setString(4, feedback.getHostelName());
	        return preparedStatement.executeUpdate() > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return false;
	}


	public String getBookedHostelName(String username) {
	    String sql = "SELECT ha.hostel_name FROM Booking b " +
	                 "JOIN Room r ON b.roomId = r.roomId " +
	                 "JOIN hostel_advertisements ha ON r.hostelId = ha.id " +
	                 "WHERE b.name = ?";
	    try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
	         PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
	        preparedStatement.setString(1, username);
	        try (ResultSet resultSet = preparedStatement.executeQuery()) {
	            if (resultSet.next()) {
	                return resultSet.getString("hostel_name");
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return "";
	}

	public boolean checkBooking(String username) {
	    boolean isBooked = false;
	    String sql = "SELECT * FROM Booking WHERE name = ?";
	    try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
	         PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
	        preparedStatement.setString(1, username);
	        try (ResultSet resultSet = preparedStatement.executeQuery()) {
	            if (resultSet.next()) {
	                isBooked = true;
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    System.out.println("checkBooking: User " + username + " booked: " + isBooked);
	    return isBooked;
	}


}
