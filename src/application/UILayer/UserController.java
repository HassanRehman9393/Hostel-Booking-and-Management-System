package application.UILayer;

import java.net.URL;
import java.util.ResourceBundle;

import application.BusinessLayer.User;
import application.BusinessLayer.UserFactory;
import application.DatabaseLayer.PersistenceHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class UserController implements Initializable {

    @FXML
    private TextField usernameField;
    
    @FXML
    private PasswordField passwordField;
    
    @FXML
    private ComboBox<String> roleComboBox;
    
    @FXML
    private AnchorPane loginForm;
    
    @FXML
    private AnchorPane registerForm;
    
    @FXML
    private TextField usernameField1; // For registration
    @FXML
    private PasswordField passwordField1; // For registration
    @FXML
    private TextField emailField; // For registration
    @FXML
    private TextField contactNoField; // For registration
    @FXML
    private ComboBox<String> roleComboBox1; // For registration
    
    private String loggedInUser;
    
    private PersistenceHandler persistenceHandler;
    
    private MainController mainController;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (roleComboBox != null && roleComboBox1 != null) {
            roleComboBox.getItems().addAll("Student", "Hostel Owner");
            roleComboBox1.getItems().addAll("Student", "Hostel Owner");
            persistenceHandler = PersistenceHandler.getInstance();
        }
    }
    
    @FXML
    private void handleLogin(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String role = roleComboBox.getValue();

        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Error", "Username and Password fields cannot be empty.");
            return;
        }

        if (role == null || role.isEmpty()) {
            showAlert("Error", "Please select a role.");
            return;
        }

        if (persistenceHandler.authenticate(username, password)) {
            loggedInUser = username;
            if ("Hostel Owner".equals(role) && mainController != null) {
                mainController.switchToHostelOwnerDashboard(event);
            } else if ("Student".equals(role) && mainController != null) {
                mainController.switchToResidentDashboard(event);
            } else {
                // Handle other roles if necessary
            }
        } else {
            showAlert("Error", "Invalid credentials.");
        }
    }

    @FXML
    private void handleRegister(ActionEvent event) {
        String name = usernameField1.getText();
        String email = emailField.getText();
        String contactNo = contactNoField.getText();
        String password = passwordField1.getText();
        String role = roleComboBox1.getValue();
        
        if (name.isEmpty() || email.isEmpty() || contactNo.isEmpty() || password.isEmpty()) {
            showAlert("Error", "Please fill in all the fields.");
            return;
        }

        if (role == null || role.isEmpty()) {
            showAlert("Error", "Please select a role.");
            return;
        }

        User newUser = UserFactory.createUser(role, name, email, contactNo, password);

        if (persistenceHandler.saveUser(newUser)) {
            showAlert("Success", "User registered successfully!");
            showLoginForm(event); // Switch back to login form after successful registration
        } else {
            showAlert("Error", "Registration failed. Please try again.");
        }

        showLoginForm(event);
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    
    public String getUsername() {
        return loggedInUser;
    }
    
    @FXML
    private void showRegisterForm(ActionEvent event) {
        loginForm.setVisible(false);
        registerForm.setVisible(true);
    }

    @FXML
    private void showLoginForm(ActionEvent event) {
        registerForm.setVisible(false);
        loginForm.setVisible(true);
    }
}
