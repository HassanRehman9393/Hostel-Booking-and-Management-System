package application.UILayer;

import application.BusinessLayer.CompareHostels;
import application.BusinessLayer.HostelAdvertisement;
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
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class HostelController implements Initializable{

    @FXML
    private Button uploadImagesButton;

    @FXML
    private ImageView imageView;

    @FXML
    private TextField hostelNameField;

    @FXML
    private TextField locationField;

    @FXML
    private TextField numberOfRoomsField;

    @FXML
    private TextField priceField;
    
    @FXML
    private CheckBox airConditionerCheckBox;

    @FXML
    private CheckBox laundaryCheckBox;

    @FXML
    private CheckBox transportCheckBox;

    private Image hostelImage;
    
    private String hostelImageUrl;

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
    private Label resultLabel;
    
    @FXML
    private Label resultLabe2;
    
    @FXML
    private Label resultLabe3;

    @FXML
    private Label hostel1Name;

    @FXML
    private Label hostel2Name;
    
    @FXML
    private Label hostel1Price;
    
    @FXML
    private Label hostel2Price;
    
    @FXML
    private Label hostel1Facilities;
    
    @FXML
    private Label hostel2Facilities;
    
    @FXML
    private Label hostel1Room;
    
    @FXML
    private Label hostel2Room;
    
    @FXML
    private AnchorPane comparePage;
    
    @FXML
    private AnchorPane resultPage;
    
    @FXML
    private ImageView hostel1Image;
    
    @FXML
    private ImageView hostel2Image;
    
    private List<HostelAdvertisement> selectedHostels = new ArrayList<>();

    private MainController mainController;
    
    private PersistenceHandler persistenceHandler;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Check if the initialization is for the HostelController.fxml
        if (url.getPath().contains("ViewAd.fxml")) {
            persistenceHandler = PersistenceHandler.getInstance();
            loadHostels();
        } else if (url.getPath().contains("HostelAd.fxml")) { // Add this condition for HostelAd.fxml
            persistenceHandler = PersistenceHandler.getInstance();
        } else if(url.getPath().contains("CompareHostel.fxml")){
        	persistenceHandler = PersistenceHandler.getInstance();
            loadCompareHostels();
        }
    }



    @FXML
    void createAdvertisement(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("HostelAd.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("HostelAdvertisement.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void viewAd(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewAd.fxml"));
        Parent root = loader.load();
        HostelController controller = loader.getController();
        controller.loadHostels();  
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("ViewAd.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    void compareHostel(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("CompareHostel.fxml"));
        Parent root = loader.load();
        HostelController controller1 = loader.getController();
        controller1.loadHostels();  // Load hostels when ViewAd.fxml is loaded
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("ViewAd.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void uploadImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Image Files", ".png", ".jpg", "*.jpeg")
        );
        Stage stage = (Stage) uploadImagesButton.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            hostelImage = new Image(selectedFile.toURI().toString());
            imageView.setImage(hostelImage);
        }
    }

    private boolean validateFields() {
        StringBuilder errorMessage = new StringBuilder();

        if (hostelNameField.getText().isEmpty()) {
            errorMessage.append("Hostel Name is required.\n");
        }
        // Check if at least one facility checkbox is selected
        if (!airConditionerCheckBox.isSelected() && !laundaryCheckBox.isSelected() && !transportCheckBox.isSelected()) {
            errorMessage.append("Select at least one facility.\n");
        }
        if (locationField.getText().isEmpty()) {
            errorMessage.append("Location is required.\n");
        }
        if (numberOfRoomsField.getText().isEmpty()) {
            errorMessage.append("Number of Rooms is required.\n");
        }
        if (priceField.getText().isEmpty()) {
            errorMessage.append("Price is required.\n");
        }
        if (hostelImage == null) {
            errorMessage.append("Image is required.\n");
        }

        if (errorMessage.length() > 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Validation Error");
            alert.setHeaderText("Please correct the following errors:");
            alert.setContentText(errorMessage.toString());
            alert.showAndWait();
            return false;
        }

        return true;
    }

    // Method to get selected facilities
    private String[] getSelectedFacilities() {
        List<String> selectedFacilities = new ArrayList<>();
        if (airConditionerCheckBox.isSelected()) {
            selectedFacilities.add("Air Conditioner");
        }
        if (laundaryCheckBox.isSelected()) {
            selectedFacilities.add("Laundary");
        }
        if (transportCheckBox.isSelected()) {
            selectedFacilities.add("Transport");
        }
        return selectedFacilities.toArray(new String[0]);
    }
    @FXML
    private void saveAD() {
        if (!validateFields()) {
            return;
        }

        String hostelName = hostelNameField.getText();
        String[] facilities = getSelectedFacilities();
        String location = locationField.getText();
        int numberOfRooms = Integer.parseInt(numberOfRoomsField.getText());
        double price = Double.parseDouble(priceField.getText());

        HostelAdvertisement hostelAdvertisement = new HostelAdvertisement(
                hostelName, facilities, location, numberOfRooms, price, hostelImage.getUrl()
        );
        boolean isSaved = persistenceHandler.saveAdvertisement(hostelAdvertisement);

        if (isSaved) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Advertisement saved successfully.");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Failed to save the advertisement.");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleSubmit(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Add Hostel Advertisement");
        alert.setContentText("Are you sure you want to add this advertisement?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            saveAD();
        }
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
    private void handleBackresident(ActionEvent event) {
        if (mainController != null) {
            mainController.switchToResidentDashboard(event);
        } else {
            System.err.println("MainController is not set in HostelController");
        }
    }

    private void loadHostels() {
        List<HostelAdvertisement> hostels = persistenceHandler.getHostelAdvertisements();

        List<AnchorPane> secondAnchorPanes = List.of(
                secondAnchorPane1, secondAnchorPane2, secondAnchorPane3,
                secondAnchorPane4, secondAnchorPane5, secondAnchorPane6
        );

        int numHostels = Math.min(hostels.size(), secondAnchorPanes.size());

        for (int i = 0; i < numHostels; i++) {
            HostelAdvertisement hostel = hostels.get(i);
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
            nameLabel.setLayoutY(135); // Adjusted Y position for the hostel name
            nameLabel.setStyle("-fx-text-fill: black; -fx-font-weight: bold; -fx-font-size: 14px;"); // Set text color to black, bold, and increase font size

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
            
            Button EditButton = new Button("  Edit  ");
            EditButton.setLayoutX(30);
            EditButton.setLayoutY(255);
            EditButton.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 13px; -fx-background-color: #003135;");
            final int finalI = i;
            EditButton.setOnAction(event -> handleEditButton(hostel, finalI));
            
            Button DeleteButton = new Button("Delete");
            DeleteButton.setLayoutX(100);
            DeleteButton.setLayoutY(255);
            DeleteButton.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 13px; -fx-background-color: #003135;");
            
            DeleteButton.setOnAction(event -> handleDeleteButton(hostel, finalI));
            anchorPane.getChildren().clear(); // Clear existing children before adding new ones
            anchorPane.getChildren().addAll(imageView, nameLabel, locationLabel, descriptionLabel, priceLabel, roomsLabel, EditButton, DeleteButton);
        }
    }

	private void loadCompareHostels() {
        List<HostelAdvertisement> hostels = persistenceHandler.getHostelAdvertisements();

        List<AnchorPane> secondAnchorPanes = List.of(
                secondAnchorPane1, secondAnchorPane2, secondAnchorPane3,
                secondAnchorPane4, secondAnchorPane5, secondAnchorPane6
        );

        int numHostels = Math.min(hostels.size(), secondAnchorPanes.size());

        for (int i = 0; i < numHostels; i++) {
            HostelAdvertisement hostel = hostels.get(i);
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
            nameLabel.setLayoutY(135); // Adjusted Y position for the hostel name
            nameLabel.setStyle("-fx-text-fill: black; -fx-font-weight: bold; -fx-font-size: 14px;"); // Set text color to black, bold, and increase font size

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

            anchorPane.getChildren().clear(); // Clear existing children before adding new ones
            anchorPane.getChildren().addAll(imageView, nameLabel, locationLabel, descriptionLabel, priceLabel, roomsLabel);

            CheckBox selectCheckBox = new CheckBox("Select");
            selectCheckBox.setLayoutX(60);
            selectCheckBox.setLayoutY(260); // Adjusted Y position for the checkbox
            selectCheckBox.setStyle("-fx-text-fill: black; -fx-font-weight: bold; -fx-font-size: 14px;");
            anchorPane.getChildren().add(selectCheckBox);

            // Handle checkbox action
            int finalI = i; // Need to make the index final to use in lambda
            selectCheckBox.setOnAction(event -> handleSelectHostel(selectCheckBox, hostel, finalI));
        }
    }

 // Method to handle hostel selection
    private void handleSelectHostel(CheckBox checkBox, HostelAdvertisement hostel, int index) {
        if (checkBox.isSelected()) {
            selectedHostels.add(hostel);
            if (selectedHostels.size() == 2) {
                compareAndDisplay();
            }
        } else {
            selectedHostels.remove(hostel);
        }
    }

    // Method to compare selected hostels and display the result
    private void compareAndDisplay() {
        if (selectedHostels.size() == 2) {
            HostelAdvertisement hostel1 = selectedHostels.get(0);
            HostelAdvertisement hostel2 = selectedHostels.get(1);
            
            // Compare hostels
            HostelAdvertisement cheaper = CompareHostels.compareByPrice(hostel1, hostel2);
            HostelAdvertisement moreFacilities = CompareHostels.compareByFacilities(hostel1, hostel2);
            HostelAdvertisement moreRooms = CompareHostels.compareByRooms(hostel1, hostel2);
            
            // Load hostel images
            if (hostel1.getHostelImage() != null) {
                hostel1Image.setImage(new Image(hostel1.getHostelImage()));
            }
            if (hostel2.getHostelImage() != null) {
                hostel2Image.setImage(new Image(hostel2.getHostelImage()));
            }

            hostel1Name.setText(hostel1.getHostelName());
            hostel2Name.setText(hostel2.getHostelName());

            hostel1Price.setText(String.valueOf("Price: $" + hostel1.getPrice()));
            hostel2Price.setText(String.valueOf("Price: $" + hostel2.getPrice()));

            hostel1Room.setText(String.valueOf("Rooms: " + hostel1.getNumberOfRooms()));
            hostel2Room.setText(String.valueOf("Rooms: " + hostel2.getNumberOfRooms()));

            hostel1Facilities.setText("Facilities: " + String.join(", ", hostel1.getFacilities()));
            hostel2Facilities.setText("Facilities: " + String.join(", ", hostel2.getFacilities()));
			
			resultLabel.setText("More Cheaper: " + cheaper.getHostelName());
            resultLabe2.setText("More Facilities: " + moreFacilities.getHostelName());
            resultLabe3.setText("More Rooms: " + moreRooms.getHostelName());
            showResultPage();
        }
    }
    
    @FXML
    private void showComparePage(ActionEvent event) {
    	comparePage.setVisible(true);
    	resultPage.setVisible(false);
    }
    
    private void showResultPage() {
    	comparePage.setVisible(false);
    	resultPage.setVisible(true);
    }
    
    private void handleEditButton(HostelAdvertisement hostel, int index) {
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Edit Hostel Advertisement");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        TextField hostelNameField = new TextField(hostel.getHostelName());
        TextField locationField = new TextField(hostel.getLocation());
        TextField numberOfRoomsField = new TextField(String.valueOf(hostel.getNumberOfRooms()));
        TextField priceField = new TextField(String.valueOf(hostel.getPrice()));

        grid.add(new Label("Hostel Name:"), 0, 0);
        grid.add(hostelNameField, 1, 0);
        grid.add(new Label("Location:"), 0, 1);
        grid.add(locationField, 1, 1);
        grid.add(new Label("Number of Rooms:"), 0, 2);
        grid.add(numberOfRoomsField, 1, 2);
        grid.add(new Label("Price:"), 0, 3);
        grid.add(priceField, 1, 3);

        dialog.getDialogPane().setContent(grid);

        ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                String updatedHostelName = hostelNameField.getText();
                String updatedLocation = locationField.getText();
                int updatedNumberOfRooms = Integer.parseInt(numberOfRoomsField.getText());
                double updatedPrice = Double.parseDouble(priceField.getText());

                // Update the hostel advertisement in the database
                HostelAdvertisement updatedHostel = new HostelAdvertisement(updatedHostelName, hostel.getFacilities(), updatedLocation, updatedNumberOfRooms, updatedPrice, hostel.getHostelImage());
                persistenceHandler.updateAdvertisement(updatedHostel, hostel.getHostelName());

                // Update the hostel advertisement in the UI
                loadHostels();
            }
            return null;
        });

        dialog.showAndWait();
    }

    private void handleDeleteButton(HostelAdvertisement hostel, int index) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Hostel Advertisement");
        alert.setHeaderText("Are you sure you want to delete this advertisement?");
        alert.setContentText("Hostel Name: " + hostel.getHostelName());
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            persistenceHandler.deleteAdvertisement(hostel.getHostelName());
            // Update the hostel advertisements in the UI
            loadHostels();
        }
    }
}