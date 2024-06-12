package application.UILayer;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MainController { 
	
	@FXML
	private AnchorPane sideBar;
	
	@FXML
	private AnchorPane mainPage;
	
	@FXML
	private AnchorPane sideBar1;
	
	@FXML
	private AnchorPane mainPage1;
	
	
	
    @FXML
    void switchToHostelOwnerDashboard(ActionEvent event) {
        try {
        	FXMLLoader loader = new FXMLLoader(getClass().getResource("OwnerDashBoard.fxml"));
            Parent root = loader.load();

            // Get the stage from the event source
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            
            // Set the scene
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("OwnerDashBoard.css").toExternalForm());
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    void switchToResidentDashboard(ActionEvent event) {
        try {
        	FXMLLoader loader = new FXMLLoader(getClass().getResource("ResidentDashBoard.fxml"));
            Parent root = loader.load();

            // Get the stage from the event source
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            
            // Set the scene
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("OwnerDashBoard.css").toExternalForm());
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    void showSideBar(ActionEvent event) {
        sideBar.setVisible(true);
        mainPage.setVisible(false);
    }

    @FXML
    void showMainPage(ActionEvent event) {
        mainPage.setVisible(true);
        sideBar.setVisible(false);
    }
    
    
    @FXML
    void showSideBarResident(ActionEvent event) {
        sideBar1.setVisible(true);
        mainPage1.setVisible(false);
    }

    @FXML
    void showMainPageResident(ActionEvent event) {
        mainPage1.setVisible(true);
        sideBar1.setVisible(false);
    }
    
    @FXML
    void createAdvertisment(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("HostelAd.fxml"));
	        Parent root;
			root = loader.load();
			HostelController hostelController = loader.getController();
	        hostelController.setMainController(this);

	        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

	        Scene scene = new Scene(root);
	        scene.getStylesheets().add(getClass().getResource("HostelAdvertisement.css").toExternalForm());
	        stage.setScene(scene);
	        stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    @FXML
    void viewAd(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewAd.fxml"));
            Parent root = loader.load();
            HostelController hostelController2 = loader.getController();
            hostelController2.setMainController(this);
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("ViewAd.css").toExternalForm());
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    void selectHostel(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Booking.fxml"));
	        Parent root;
			root = loader.load();
			ResidentController residentController = loader.getController();
	        residentController.setMainController(this);
	        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
	        Scene scene = new Scene(root);
	        scene.getStylesheets().add(getClass().getResource("ViewAd.css").toExternalForm());
	        stage.setScene(scene);
	        stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    @FXML
    void logOut(ActionEvent event) {
    	try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
	        Parent root;
			root = loader.load();
			UserController userController = loader.getController();
	        userController.setMainController(this);
	        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
	        Scene scene = new Scene(root);
	        scene.getStylesheets().add(getClass().getResource("Login.css").toExternalForm());
	        stage.setScene(scene);
	        stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    @FXML
    void compareHostel(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CompareHostel.fxml"));
            Parent root = loader.load();
            HostelController hostelController3 = loader.getController();
            hostelController3.setMainController(this);
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("ViewAd.css").toExternalForm());
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    void roomAllocation(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("RoomAllocation.fxml"));
            Parent root = loader.load();
            ManagementController controller = loader.getController();
            controller.setMainController(this);
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("Room.css").toExternalForm());
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    void registerComplain(ActionEvent event) {
    	 try {
             FXMLLoader loader = new FXMLLoader(getClass().getResource("registerComplain.fxml"));
             Parent root = loader.load();
             ManagementController controller = loader.getController();
             controller.setMainController(this);
             Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
             Scene scene = new Scene(root);
             scene.getStylesheets().add(getClass().getResource("form.css").toExternalForm());
             stage.setScene(scene);
             stage.show();
         } catch (IOException e) {
             e.printStackTrace();
         }
    }

    

    @FXML
    void viewComplaints(ActionEvent event) {
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("OwnerComplain.fxml"));
            Parent root = loader.load();
            ManagementController controller = loader.getController();
            controller.setMainController(this);
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("form.css").toExternalForm());
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    void applyLeaveRequest(ActionEvent event) {
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ApplyLeaveRequest.fxml"));
            Parent root = loader.load();
            ManagementController controller = loader.getController();
            controller.setMainController(this);
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("ApplyLeaveRequests.css").toExternalForm());
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void viewLeaveRequests(ActionEvent event) {
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminLeaveRequests.fxml"));
            Parent root = loader.load();
            ManagementController controller = loader.getController();
            controller.setMainController(this);
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("AdminLeaveRequests.css").toExternalForm());
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    void giveFeedback(ActionEvent event) {
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Feedback.fxml"));
            Parent root = loader.load();
            ManagementController controller = loader.getController();
            controller.setMainController(this);
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("Feedback.css").toExternalForm());
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}