package application.UILayer;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.controlsfx.control.Rating;

import application.BusinessLayer.Complaint;
import application.BusinessLayer.Feedback;
import application.BusinessLayer.HostelAdvertisement;
import application.BusinessLayer.LeaveRequest;
import application.BusinessLayer.Room;
import application.DatabaseLayer.PersistenceHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.beans.property.SimpleBooleanProperty;

public class ManagementController {
	@FXML
	private ListView<LeaveRequest> leaveRequestsListView;

	@FXML
	private Label residentIdLabel;

	@FXML
	private Label reasonLabel;

	@FXML
	private Label leaveDateLabel;

	@FXML
	private Label contactNumberLabel;

	@FXML
	private Label forwardingAddressLabel;

	@FXML
	private Label statusLabel;

	@FXML
	private TextArea approvalCommentTextArea;

	private ObservableList<LeaveRequest> leaveRequests;

	@FXML
	private TextArea reasonTextArea;

	@FXML
	private DatePicker leaveDatePicker;

	@FXML
	private TextField contactNumberField;

	@FXML
	private TextField forwardingAddressField;

	@FXML
	private Button applyButton;

	@FXML
	private ListView<Complaint> emailListView;

	@FXML
	private Label emailSubjectLabel;

	@FXML
	private Label emailSenderLabel;

	@FXML
	private TextArea emailContentTextArea;

	@FXML
	private TextField senderField;

	@FXML
	private TextField recipientField;

	@FXML
	private TextField subjectField;

	@FXML
	private TextArea contentTextArea;

	private List<Complaint> complains;
	private Complaint newComplain;
	@FXML
	private TableView<Room> roomTable;
	@FXML
	private TableColumn<Room, String> roomTypeColumn;
	@FXML
	private TableColumn<Room, Integer> availableRoomsColumn;
	@FXML
	private TableColumn<Room, Boolean> availabilityColumn;

	@FXML
	private ComboBox<String> hostelComboBox, roomTypeComboBox;
	@FXML
	private ComboBox<String> availableRoomsComboBox;
	@FXML
	private ComboBox<String> availabilityComboBox;

	private ObservableList<HostelAdvertisement> hostels;
	private ObservableList<Room> rooms;
	private Room selectedRoom;

	@FXML
	private Rating ratingControl;

	@FXML
	private TextArea feedbackTextArea;
	private MainController mainController;
	private UserController userController = new UserController();
	private PersistenceHandler persistenceHandler;

	public void setUserController(UserController userController) {
		this.userController = userController;
		//System.out.println("UserController is set in ManagementController");
	}

	public void setMainController(MainController mainController) {
		this.mainController = mainController;
	}

	@FXML
	void roomAllocation(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("RoomAllocation.fxml"));
		Parent root = loader.load();
		Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("Room.css").toExternalForm());
		stage.setScene(scene);
		stage.show();
	}

	public void initialize() {
		persistenceHandler = PersistenceHandler.getInstance(); // Initialize persistenceHandler

		if (roomTypeColumn != null && availableRoomsColumn != null && availabilityColumn != null) {
			roomTypeColumn.setCellValueFactory(new PropertyValueFactory<>("roomType"));
			availableRoomsColumn.setCellValueFactory(new PropertyValueFactory<>("availableRooms"));
			availabilityColumn
					.setCellValueFactory(cellData -> new SimpleBooleanProperty(cellData.getValue().isAvailable()));

			availabilityColumn.setCellFactory(new Callback<TableColumn<Room, Boolean>, TableCell<Room, Boolean>>() {
				@Override
				public TableCell<Room, Boolean> call(TableColumn<Room, Boolean> param) {
					return new TableCell<Room, Boolean>() {
						@Override
						protected void updateItem(Boolean item, boolean empty) {
							super.updateItem(item, empty);
							if (item == null || empty) {
								setText(null);
							} else {
								setText(item ? "Available" : "Not Available");
							}
						}
					};
				}
			});
		}

		if (hostelComboBox != null && roomTypeComboBox != null && availableRoomsComboBox != null
				&& availabilityComboBox != null) {
			// Load hostel names
			hostels = FXCollections.observableArrayList(persistenceHandler.getHostelAdvertisements());
			for (HostelAdvertisement hostel : hostels) {
				hostelComboBox.getItems().add(hostel.getHostelName());
			}
			// Initialize room types and availability options
			roomTypeComboBox.getItems().addAll("Single", "Double", "Triple"); // example room types
			for (int i = 1; i <= 10; i++) { // assuming max 20 rooms
				availableRoomsComboBox.getItems().add(String.valueOf(i));
			}
			availabilityComboBox.getItems().addAll("Available", "Not Available");
			rooms = FXCollections.observableArrayList();
			hostelComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
				loadRooms();
			});
		}

		if (emailListView != null) {
			complains = persistenceHandler.loadEmailsFromDatabase(); // Load emails from database
			populateEmailListView();

			emailListView.setOnMouseClicked((MouseEvent event) -> {
				Complaint selectedEmail = emailListView.getSelectionModel().getSelectedItem();
				displayEmailDetails(selectedEmail);
			});
		}

		if (leaveRequestsListView != null) {
			// Load leave requests from the database
			leaveRequests = FXCollections.observableArrayList(persistenceHandler.loadLeaveRequestsFromDatabase());
			// Populate leave requests into ListView
			leaveRequestsListView.setItems(leaveRequests);
			leaveRequestsListView.getSelectionModel().selectedItemProperty()
					.addListener((observable, oldValue, newValue) -> showLeaveRequestDetails(newValue));
		}
	}

	private void loadRooms() {
		String selectedHostel = hostelComboBox.getValue();
		if (selectedHostel != null) {
			PersistenceHandler handler = PersistenceHandler.getInstance();
			rooms = FXCollections.observableArrayList(handler.getRooms(selectedHostel));
			roomTable.setItems(rooms);
		}
	}

	@FXML
	private void addRoom(ActionEvent event) {
		String selectedHostel = hostelComboBox.getValue();
		String roomType = roomTypeComboBox.getValue();
		String availableRooms = availableRoomsComboBox.getValue();
		String availability = availabilityComboBox.getValue();

		if (selectedHostel == null || roomType == null || availableRooms == null || availability == null) {
			showAlert("Validation Error", "Please fill all the fields.");
			return;
		}

		PersistenceHandler handler = PersistenceHandler.getInstance();
		HostelAdvertisement hostel = handler.getHostelAdvertisement(selectedHostel);
		if (hostel == null) {
			showAlert("Error", "Hostel not found.");
			return;
		}
		int availableRoomsInt;
		try {
			availableRoomsInt = Integer.parseInt(availableRooms);
		} catch (NumberFormatException e) {
			showAlert("Validation Error", "Available rooms must be a number.");
			return;
		}

		boolean isAvailable = availability.equals("Available");

		// Calculate total rooms already added
		int totalRoomsAdded = rooms.stream().mapToInt(Room::getAvailableRooms).sum();

		// Check if adding the new room would exceed the number of rooms in the hostel
		// advertisement
		if (totalRoomsAdded + availableRoomsInt > hostel.getNumberOfRooms()) {
			showAlert("Error",
					"Adding this room exceeds the maximum number of rooms specified in the hostel advertisement.");
			return;
		}

		Room room = new Room(roomType, availableRoomsInt, isAvailable);

		Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
		confirmationAlert.setTitle("Confirmation Dialog");
		confirmationAlert.setHeaderText("Add Room");
		confirmationAlert.setContentText("Are you sure you want to add this room?");

		Optional<ButtonType> result = confirmationAlert.showAndWait();
		if (result.isPresent() && result.get() == ButtonType.OK) {
			boolean success = handler.updateRoom(selectedHostel, room);
			if (!success) {
				success = handler.saveRoom(selectedHostel, room);
			}

			if (success) {
				loadRooms();
			} else {
				showAlert("Error", "Error saving the room data.");
			}
		}
	}

	private void showAlert(String title, String message) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}

	@FXML
	private void handleBackButton(ActionEvent event) {
		if (mainController != null) {
			mainController.switchToHostelOwnerDashboard(event);
		} else {
			System.err.println("MainController is not set in HostelController");
		}
	}

	@FXML
	private void handleBackButtonResident(ActionEvent event) {
		if (mainController != null) {
			mainController.switchToResidentDashboard(event);
		} else {
			System.err.println("MainController is not set in HostelController");
		}
	}

	@FXML
	void cancelButton(ActionEvent event) {
		if (mainController != null) {
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setTitle("Cancl");
			alert.setHeaderText("Application Cancelled");
			alert.setContentText("Your leave request is Cancelled.");

			alert.showAndWait();
			mainController.switchToResidentDashboard(event);
		} else {
			System.err.println("MainController is not set in HostelController");
		}
	}

	private void populateEmailListView() {
		emailListView.getItems().addAll(complains);
	}

	private void displayEmailDetails(Complaint complain) {
		emailSubjectLabel.setText("Complain: " + complain.getcomplain());
		emailSenderLabel.setText("Sender: " + complain.getSender());
		emailContentTextArea.setText(complain.getdescription());
	}

	@FXML
	private void handleComposeButtonAction() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("complainForm.fxml"));
			loader.setController(this);
			Parent root = loader.load();

			Stage stage = new Stage();
			stage.setScene(new Scene(root));
			stage.setTitle("Compose New Complain");
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.showAndWait();

			if (newComplain != null) {
				persistenceHandler.addEmailToDatabase(newComplain);
				emailListView.getItems().add(0, newComplain);
				complains.add(0, newComplain);
				newComplain = null; // Reset after use
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void handleReplyButtonAction() {
		Complaint selectedEmail = emailListView.getSelectionModel().getSelectedItem();
		if (selectedEmail != null) {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("complainForm.fxml"));
				loader.setController(this);
				Parent root = loader.load();

				setReplyDetails(selectedEmail.getSender(), selectedEmail.getRecipient());

				Stage stage = new Stage();
				stage.setScene(new Scene(root));
				stage.setTitle("Reply to: " + selectedEmail.getSender());
				stage.initModality(Modality.APPLICATION_MODAL);
				stage.showAndWait();

				if (newComplain != null) {
					persistenceHandler.addEmailToDatabase(newComplain);
					emailListView.getItems().add(0, newComplain);
					complains.add(0, newComplain);
					newComplain = null; // Reset after use
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@FXML
	private void handleSendButtonAction() {
		String sender = senderField.getText();
		String recipient = recipientField.getText();
		String complain = subjectField.getText();
		String description = contentTextArea.getText();
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		newComplain = new Complaint(0, sender, recipient, complain, description, timestamp);
		Stage stage = (Stage) senderField.getScene().getWindow();
		stage.close();
	}

	public Complaint getNewEmail() {
		return newComplain;
	}

	public void setReplyDetails(String recipient, String sender) {
		senderField.setText(sender);
		recipientField.setText(recipient);
		subjectField.setText("Re: ");
	}

	// Method to handle apply button action
	@FXML
	void applyLeaveRequest(ActionEvent event) {
		String reason = reasonTextArea.getText();
		LocalDate leaveDate = leaveDatePicker.getValue();
		String contactNumber = contactNumberField.getText();
		String forwardingAddress = forwardingAddressField.getText();

		// Validate inputs (optional)
		if (reason.isEmpty() || leaveDate == null || contactNumber.isEmpty() || forwardingAddress.isEmpty()) {
			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setTitle("Warning");
			alert.setHeaderText("Incomplete Information");
			alert.setContentText("Please fill in all the fields to submit your leave request.");
			alert.showAndWait();
			return;
		}
		// Create LeaveRequest object
		LeaveRequest leaveRequest = new LeaveRequest(
				/* Provide residentId dynamically based on logged-in user */
				1, // Replace with actual resident ID from session or database
				reason, LocalDate.now(), // Requested date is current date
				leaveDate, contactNumber, forwardingAddress);

		// Assuming you have a method to save the leave request to the database
		boolean leaveRequestSaved = persistenceHandler.saveLeaveRequestToDatabase(leaveRequest);

		if (leaveRequestSaved) {
			// Show confirmation or close the window
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("Success");
			alert.setHeaderText("Leave Request Saved Successfully");
			alert.setContentText("Your leave request has been saved successfully.");
			alert.showAndWait();
			handleBackButtonResident(event);
		} else {
			// Handle error or show validation messages
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Failed to Save Leave Request");
			alert.setContentText("There was an error while saving your leave request. Please try again later.");

			alert.showAndWait();
		}

	}

	private void showLeaveRequestDetails(LeaveRequest leaveRequest) {
		if (leaveRequest != null) {
			residentIdLabel.setText("Resident ID: " + leaveRequest.getResidentId());
			reasonLabel.setText("Reason: " + leaveRequest.getReason());
			leaveDateLabel.setText("Leave Date: " + leaveRequest.getLeaveDate());
			contactNumberLabel.setText("Contact Number: " + leaveRequest.getContactNumber());
			forwardingAddressLabel.setText("Forwarding Address: " + leaveRequest.getForwardingAddress());
			statusLabel.setText("Status: " + leaveRequest.getApproval_status());
			approvalCommentTextArea.clear(); // Clear previous approval comment
		} else {
			// Clear labels and text area if no leave request is selected
			residentIdLabel.setText("");
			reasonLabel.setText("");
			leaveDateLabel.setText("");
			contactNumberLabel.setText("");
			forwardingAddressLabel.setText("");
			statusLabel.setText("");
			approvalCommentTextArea.clear();
		}
	}

	@FXML
	private void handleApproveButtonAction() {
		LeaveRequest selectedLeaveRequest = leaveRequestsListView.getSelectionModel().getSelectedItem();
		if (selectedLeaveRequest != null) {
			String approvalComment = approvalCommentTextArea.getText();
			// Update leave request object
			selectedLeaveRequest.setApproval_status("Approved");
			selectedLeaveRequest.setApproved(true);
			selectedLeaveRequest.setApprovalComments(approvalComment);
			selectedLeaveRequest.setApprovalDate(java.sql.Date.valueOf(java.time.LocalDate.now()).toLocalDate());
			persistenceHandler.updateLeaveRequestToDatabase(selectedLeaveRequest);
			leaveRequests.remove(selectedLeaveRequest);
			// Clear details after approval
			showLeaveRequestDetails(null);
		}
	}

	@FXML
	private void handleRejectButtonAction() {
		LeaveRequest selectedLeaveRequest = leaveRequestsListView.getSelectionModel().getSelectedItem();
		if (selectedLeaveRequest != null) {
			String rejectionComment = approvalCommentTextArea.getText();
			selectedLeaveRequest.setApproval_status("Rejected");
			selectedLeaveRequest.setApprovalComments(rejectionComment);
			selectedLeaveRequest.setApproved(false); // Assuming approved status is set to false on rejection
			selectedLeaveRequest.setApprovalDate(java.sql.Date.valueOf(java.time.LocalDate.now()).toLocalDate());
			// Save to database
			persistenceHandler.saveLeaveRequestToDatabase(selectedLeaveRequest);
			leaveRequests.remove(selectedLeaveRequest);
			showLeaveRequestDetails(null);
		}
	}

	@FXML
	void giveFeedback(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Feedback.fxml"));
			Parent root = loader.load();
			Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("Feedback.css").toExternalForm());
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private String someMethod() {
		return userController.getUsername();
	}

	@FXML
	private void submitFeedback() {
		if (userController != null) {
			String name = someMethod();
			System.out.println("Logged in user: " + name); // Added for debugging
			double ratingValue = ratingControl.getRating();
			String feedbackText = feedbackTextArea.getText();

			if (persistenceHandler.checkBooking(name)) {
				if (feedbackText.isEmpty()) {
					showAlert("Validation Error", "Feedback cannot be empty.");
					return;
				}

				String hostelName = persistenceHandler.getBookedHostelName(name);
				Feedback feedback = new Feedback(ratingValue, feedbackText, name, hostelName);
				boolean success = persistenceHandler.saveFeedbackToDatabase(feedback);

				if (success) {
					showAlert("Success", "Feedback submitted successfully.");
					clearFeedbackForm();
				} else {
					showAlert("Error", "There was an error submitting your feedback. Please try again later.");
				}
			} else {
				showAlert("Error", "You have not booked any room yet.");
			}
		} else {
			System.err.println("UserController is not set in ManagementController");
		}
	}

	private void clearFeedbackForm() {
		ratingControl.setRating(0);
		feedbackTextArea.clear();
	}

}
