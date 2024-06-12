package application.UILayer;

import application.BusinessLayer.Booking;
import application.BusinessLayer.HostelAdvertisement;
import application.BusinessLayer.Room;
import application.BusinessLayer.SearchCriteria;
import application.DatabaseLayer.PersistenceHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class ResidentController implements Initializable {

	@FXML
	private AnchorPane mainAnchorPane;

	@FXML
	private AnchorPane hostelContentPane;

	@FXML
	private ScrollPane hostelScrollPane;

	@FXML
	private AnchorPane secondAnchorPane1;

	@FXML
	private AnchorPane secondAnchorPane2;

	@FXML
	private AnchorPane secondAnchorPane3;

	@FXML
	private AnchorPane secondAnchorPane4;

	@FXML
	private AnchorPane secondAnchorPane5;

	@FXML
	private AnchorPane secondAnchorPane6;

	@FXML
	private AnchorPane bookingPage;

	@FXML
	private AnchorPane searchPage;

	@FXML
	private AnchorPane resultPage;

	@FXML
	private TextField locationField;

	@FXML
	private TextField priceRangeField;

	@FXML
	private CheckBox airConditionerCheckBox;

	@FXML
	private CheckBox laundryCheckBox;

	@FXML
	private CheckBox transportCheckBox;

	@FXML
	private TextField nameField;

	@FXML
	private TextField ageField;

	@FXML
	private TextField emailField;

	@FXML
	private TextField contactNoField;

	@FXML
	private ComboBox<String> roomTypeComboBox;

	private HostelAdvertisement selectedHostel;
	private Room selectedRoom;

	private MainController mainController;

	private PersistenceHandler persistenceHandler;

	@FXML
	private AnchorPane resultPane1;

	@FXML
	private AnchorPane resultPane2;

	@FXML
	private AnchorPane resultPane3;

	@FXML
	private AnchorPane resultPane4;

	@FXML
	private AnchorPane resultPane5;

	@FXML
	private AnchorPane resultPane6;

	@FXML
	private AnchorPane DetailsPage;

	public void setMainController(MainController mainController) {
		this.mainController = mainController;
	}

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		persistenceHandler = PersistenceHandler.getInstance();
		loadHostels();
	}

	@FXML
	void selectHostel(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Booking.fxml"));
		Parent root = loader.load();
		Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("ViewAd.css").toExternalForm());
		stage.setScene(scene);
		stage.show();
	}

	@FXML
	private void handleBackButton(ActionEvent event) {
		if (mainController != null) {
			mainController.switchToResidentDashboard(event);
		} else {
			System.err.println("MainController is not set in HostelController");
		}
	}

	private void loadHostels() {
		List<HostelAdvertisement> hostels = persistenceHandler.getHostelAdvertisements();
		List<AnchorPane> secondAnchorPanes = List.of(secondAnchorPane1, secondAnchorPane2, secondAnchorPane3,
				secondAnchorPane4, secondAnchorPane5, secondAnchorPane6);

		int numHostels = Math.min(hostels.size(), secondAnchorPanes.size());

		for (int i = 0; i < numHostels; i++) {
			HostelAdvertisement hostel = hostels.get(i);
			AnchorPane anchorPane = secondAnchorPanes.get(i);
			if (anchorPane == null) {
				continue; // Skip this iteration if anchorPane is null
			}
			ImageView imageView = new ImageView();
			imageView.setFitWidth(175);
			imageView.setFitHeight(120);
			imageView.setLayoutX(10);
			imageView.setLayoutY(5);

			if (hostel.getHostelImage() != null) {
				Image image = new Image(hostel.getHostelImage());
				imageView.setImage(image);
			}

			Label nameLabel = new Label(hostel.getHostelName());
			nameLabel.setLayoutX(10);
			nameLabel.setLayoutY(135); // Adjusted Y position for the hostel name
			nameLabel.setStyle("-fx-text-fill: black; -fx-font-weight: bold; -fx-font-size: 14px;");
			// Show location below hostel name
			Label locationLabel = new Label("Location: " + hostel.getLocation());
			locationLabel.setLayoutX(10);
			locationLabel.setLayoutY(155);
			locationLabel.setStyle("-fx-text-fill: black;");

			// Add description label with max width for word wrap
			Label descriptionLabel = new Label("Facilities: " + String.join(", ", hostel.getFacilities()));
			descriptionLabel.setLayoutX(10);
			descriptionLabel.setLayoutY(175);
			descriptionLabel.setWrapText(true); // Enable word wrap
			descriptionLabel.setMaxWidth(180); // Set max width for word wrap
			descriptionLabel.setStyle("-fx-text-fill: black;");

			// Add price and rooms labels
			Label priceLabel = new Label("Price: $" + hostel.getPrice());
			priceLabel.setLayoutX(10);
			priceLabel.setLayoutY(215);
			priceLabel.setStyle("-fx-text-fill: black;");

			Label roomsLabel = new Label("Rooms: " + hostel.getNumberOfRooms());
			roomsLabel.setLayoutX(10);
			roomsLabel.setLayoutY(235);
			roomsLabel.setStyle("-fx-text-fill: black;");

			// Add the Book button
			Button bookButton = new Button("Book");
			bookButton.setLayoutX(50);
			bookButton.setLayoutY(260);
			bookButton.setPrefWidth(90);
			bookButton.setPrefHeight(20);
			bookButton.setStyle("-fx-background-color: #003135; -fx-text-fill: white;");
			bookButton.setOnAction(event -> {
				selectedHostel = hostel;
				showDetails(event);
			});

			anchorPane.getChildren().clear();
			anchorPane.getChildren().addAll(imageView, nameLabel, locationLabel, descriptionLabel, priceLabel,
					roomsLabel, bookButton);
		}
	}

	@FXML
	void searchHostels(ActionEvent event) throws IOException {
		searchPage.setVisible(true);
		bookingPage.setVisible(false);
		resultPage.setVisible(false);
		DetailsPage.setVisible(false);
		resetSearchFields(); // Reset the search fields and checkboxes
	}

	@FXML
	void backToBookingPage(ActionEvent event) {
		searchPage.setVisible(false);
		bookingPage.setVisible(true);
		resultPage.setVisible(false);
		DetailsPage.setVisible(false);
		resetSearchFields(); // Reset the search fields and checkboxes
	}

	@FXML
	void showResults(ActionEvent event) {
		String location = locationField.getText();
		String priceRange = priceRangeField.getText();
		boolean airConditioner = airConditionerCheckBox.isSelected();
		boolean laundry = laundryCheckBox.isSelected();
		boolean transport = transportCheckBox.isSelected();

		SearchCriteria criteria = new SearchCriteria(location, priceRange, airConditioner, laundry, transport);
		List<HostelAdvertisement> results = persistenceHandler.searchHostels(criteria);

		displaySearchResults(results);
	}

	private void displaySearchResults(List<HostelAdvertisement> results) {
		List<AnchorPane> secondAnchorPanes = List.of(resultPane1, resultPane2, resultPane3, resultPane4, resultPane5,
				resultPane6);
		int numHostels = Math.min(results.size(), secondAnchorPanes.size());

		for (int i = 0; i < numHostels; i++) {
			HostelAdvertisement hostel = results.get(i);
			AnchorPane anchorPane = secondAnchorPanes.get(i);

			ImageView imageView = new ImageView();
			imageView.setFitWidth(175);
			imageView.setFitHeight(120);
			imageView.setLayoutX(10);
			imageView.setLayoutY(5);

			if (hostel.getHostelImage() != null) {
				Image image = new Image(hostel.getHostelImage());
				imageView.setImage(image);
			}

			Label nameLabel = new Label(hostel.getHostelName());
			nameLabel.setLayoutX(10);
			nameLabel.setLayoutY(135);
			nameLabel.setStyle("-fx-text-fill: black; -fx-font-weight: bold; -fx-font-size: 14px;");

			Label locationLabel = new Label("Location: " + hostel.getLocation());
			locationLabel.setLayoutX(10);
			locationLabel.setLayoutY(155);
			locationLabel.setStyle("-fx-text-fill: black;");

			Label descriptionLabel = new Label("Facilities: " + String.join(", ", hostel.getFacilities()));
			descriptionLabel.setLayoutX(10);
			descriptionLabel.setLayoutY(175);
			descriptionLabel.setWrapText(true);
			descriptionLabel.setMaxWidth(180);
			descriptionLabel.setStyle("-fx-text-fill: black;");

			Label priceLabel = new Label("Price: $" + hostel.getPrice());
			priceLabel.setLayoutX(10);
			priceLabel.setLayoutY(215);
			priceLabel.setStyle("-fx-text-fill: black;");

			Label roomsLabel = new Label("Rooms: " + hostel.getNumberOfRooms());
			roomsLabel.setLayoutX(10);
			roomsLabel.setLayoutY(235);
			roomsLabel.setStyle("-fx-text-fill: black;");

			// Add the Book button
			Button bookButton = new Button("Book");
			bookButton.setLayoutX(50);
			bookButton.setLayoutY(260);
			bookButton.setPrefWidth(90);
			bookButton.setPrefHeight(20);
			bookButton.setStyle("-fx-background-color: #003135; -fx-text-fill: white;");
			bookButton.setOnAction(event -> {
				selectedHostel = hostel;
				showDetails(event);
			});

			anchorPane.getChildren().clear();
			anchorPane.getChildren().addAll(imageView, nameLabel, locationLabel, descriptionLabel, priceLabel,
					roomsLabel, bookButton);
		}

		searchPage.setVisible(false);
		bookingPage.setVisible(false);
		DetailsPage.setVisible(false);
		resultPage.setVisible(true);
	}

	private void resetSearchFields() {
		locationField.clear();
		priceRangeField.clear();
		airConditionerCheckBox.setSelected(false);
		laundryCheckBox.setSelected(false);
		transportCheckBox.setSelected(false);
	}

	public void showDetails(ActionEvent event) {
	    DetailsPage.setVisible(true);
	    bookingPage.setVisible(false);
	    searchPage.setVisible(false);
	    resultPage.setVisible(false);

	    // Set the list of room types as the items for the roomTypeComboBox
	    roomTypeComboBox.getItems().clear();
	    roomTypeComboBox.getItems().addAll("Single", "Double", "Triple");

	    // Get the rooms for the selected hostel
	    List<Room> rooms = persistenceHandler.getRooms(selectedHostel.getHostelName());

	    // Find the selected room based on the room type
	    String selectedRoomType = roomTypeComboBox.getValue();
	    for (Room room : rooms) {
	        if (room.getRoomType().equalsIgnoreCase(selectedRoomType)) {
	            selectedRoom = room;
	            break;
	        }
	    }
	    int selectedhostelId = persistenceHandler.getHostelId(selectedHostel.getHostelName());
	    int roomId = persistenceHandler.getRoomId(selectedhostelId, selectedRoomType);
	    selectedRoom = persistenceHandler.getRoom(roomId);
	    roomTypeComboBox.getSelectionModel().select(selectedRoomType);
	}

	public void bookRoom(ActionEvent event) {
	    // Check if selectedRoom is null
	    if (selectedRoom != null && !selectedRoom.isAvailable()) {
	        Alert alert = new Alert(Alert.AlertType.ERROR);
	        alert.setTitle("Error");
	        alert.setHeaderText("Room not available");
	        alert.setContentText("The selected room is not available.");
	        alert.showAndWait();
	        return;
	    }

	    try {
	        // Get the user input from the booking page
	        String name = nameField.getText();
	        int age = Integer.parseInt(ageField.getText());
	        String email = emailField.getText();
	        String contactNo = contactNoField.getText();
	        String roomType = roomTypeComboBox.getValue();

	        // Get the room ID based on the hostel name and room type
	        int roomId = persistenceHandler.bookingId(selectedHostel.getHostelName(), roomType);
	        if (roomId == 0) {
	            Alert alert = new Alert(Alert.AlertType.ERROR);
	            alert.setTitle("Error");
	            alert.setHeaderText("Room not found");
	            alert.setContentText("The selected room type is not available in this hostel.");
	            alert.showAndWait();
	            return;
	        }

	        // Create a new booking
	        Booking booking = new Booking(selectedHostel.getHostelName(), roomId, name, age, email, contactNo, roomType);
	        persistenceHandler.saveBooking(booking);

	        // Set the room as not available
	        if (selectedRoom != null) {
	            selectedRoom.setAvailable(false);
	        }

	        // Show a success message
	        Alert alert = new Alert(Alert.AlertType.INFORMATION);
	        alert.setTitle("Success");
	        alert.setHeaderText("Room booked");
	        alert.setContentText("You have successfully booked a room in " + selectedHostel.getHostelName() + ".");
	        alert.showAndWait();
	    } catch (NumberFormatException e) {
	        // Handle the NumberFormatException when parsing age
	        Alert alert = new Alert(Alert.AlertType.ERROR);
	        alert.setTitle("Error");
	        alert.setHeaderText("Invalid Age");
	        alert.setContentText("Please enter a valid age.");
	        alert.showAndWait();
	    }
	}
	
	
}