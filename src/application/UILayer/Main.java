package application.UILayer;
	
import javafx.application.Application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.*;

public class Main extends Application {
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load the main FXML file
        FXMLLoader mainLoader = new FXMLLoader(getClass().getResource("Login.fxml"));
        Parent root = mainLoader.load();
        // Get the MainController instance
        UserController userController = mainLoader.getController();
        // Create and set the UserController instance into MainController
        MainController mainController = new MainController();
        userController.setMainController(mainController);
        
        ManagementController managementController = new ManagementController();
        managementController.setUserController(userController);
        // Set the scene
        Scene scene = new Scene(root);
       
        scene.getStylesheets().add(getClass().getResource("Login.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Login");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}