package hospiflow1;

import com.mysql.fabric.xmlrpc.base.Data;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

public class Loginpage {

    @FXML
    private TextField Email;
    @FXML
    private PasswordField Password;
    @FXML
    private CheckBox showPasswordCheckbox;
    @FXML
    private ComboBox<String> userTypeComboBox;
    @FXML
    private Button loginin;
    @FXML
    private TextField login_showPassword;

    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;

    @FXML
    private void initialize() {
        userTypeComboBox.getItems().addAll("Doctor", "Patient", "Receptionist");

    }

    @FXML
    void handleLogin(ActionEvent event) {
        // Move initialization code here
        String email = Email.getText();
        String password = Password.getText();
        String userType = userTypeComboBox.getValue();

        if (userType == null) {
            userTypeComboBox.setStyle("-fx-border-color: red;");
            return;
        }
        // Rest of the code remains unchanged
        if (password.isEmpty() || password.length() > 8) {
            Password.setStyle("-fx-border-color: red;");
            // Display a message to the user
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Password Validation");
            alert.setHeaderText(null);

            if (password.isEmpty()) {
                alert.setContentText("Password cannot be empty.");
            } else {
                alert.setContentText("Password should be 8 characters or shorter.");
            }

            alert.showAndWait();
            return;
        }

        if (email.isEmpty() || !isValidEmail(email)) {
            Email.setStyle("-fx-border-color: red;");
            return;
        }

        Email.setStyle(null);
        Password.setStyle(null);

        conn = (Connection) mysqlpage.ConnectDb();

        String sql = "SELECT * FROM loginpage WHERE email = ? AND password = ? AND user_type = ?";
        try {
            pst = (PreparedStatement) conn.prepareStatement(sql);
            pst.setString(1, email);
            pst.setString(2, password);
            pst.setString(3, userTypeComboBox.getValue().toString());

            rs = pst.executeQuery();

            if (rs.next()) {
                loginin.setDisable(true);
                JOptionPane.showMessageDialog(null, "Username and Password are Correct");
                loginin.getScene().getWindow().hide();

                String userLoginType = userTypeComboBox.getValue().toString();

                // Load the appropriate FXML file based on the user type
                String fxmlFile;
                if ("Doctor".equals(userLoginType)) {
                    fxmlFile = "DoctorPage.fxml";
                } else if ("Receptionist".equals(userLoginType)) {
                    fxmlFile = "Receptionnistepage.fxml";
                } else if ("Patient".equals(userLoginType)) {
                    fxmlFile = "Patientspageprofile.fxml";
                } else {
                    // Handle the case for other user types or show an error message
                    JOptionPane.showMessageDialog(null, "Invalid User Type");
                    return;
                }

                Parent root = FXMLLoader.load(getClass().getResource(fxmlFile));
                Stage mainStage = new Stage();
                Scene scene = new Scene(root);
                mainStage.setScene(scene);
                mainStage.show();
            } else {
                JOptionPane.showMessageDialog(null, "Invalid Username or Password");

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    private boolean isValidEmail(String email) {
        return email.contains("@") && email.contains(".");

    }

    @FXML
    void handleShowPassword() {
        if (showPasswordCheckbox.isSelected()) {
            login_showPassword.setText(Password.getText());
            Password.setVisible(false);
            login_showPassword.setVisible(true);
        } else {
            Password.setText(login_showPassword.getText());
            Password.setVisible(true);
            login_showPassword.setVisible(false);
        }
    }

    @FXML
    void newAccount() {
        String selectedUserType = userTypeComboBox.getValue();

        try {
            FXMLLoader loader = new FXMLLoader();

            Stage stage = new Stage();
            stage.setResizable(false);

            if ("Doctor".equals(selectedUserType)) {
                loader.setLocation(getClass().getResource("Doctor.fxml"));
                Parent doctorRoot = loader.load();
                DoctorController doctorController = loader.getController();
                Scene doctorScene = new Scene(doctorRoot, 600, 400);
                stage.setScene(doctorScene);
            } else if ("Receptionist".equals(selectedUserType)) {
                loader.setLocation(getClass().getResource("Receptionniste.fxml"));
                Parent receptionistRoot = loader.load();
                ReceptionnisteController receptionistController = loader.getController();
                Scene receptionistScene = new Scene(receptionistRoot, 600, 400);
                stage.setScene(receptionistScene);
            } else if ("Patient".equals(selectedUserType)) {
                loader.setLocation(getClass().getResource("Patients.fxml"));
                Parent patientsRoot = loader.load();
                PatientsController patientsController = loader.getController();
                Scene receptionistScene = new Scene(patientsRoot, 600, 400);
                stage.setScene(receptionistScene);
            }

            Stage loginStage = (Stage) Email.getScene().getWindow();
            loginStage.close();

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Error loading selected page.");
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

}
