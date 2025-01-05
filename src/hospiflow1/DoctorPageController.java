package hospiflow1;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
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
import com.jfoenix.controls.JFXButton;
import javafx.scene.paint.ImagePattern;

public class DoctorPageController {

    @FXML
    private JFXButton User_Profile;
    @FXML
    private JFXButton Messages;
    @FXML
    private JFXButton Settings;
    @FXML
    private JFXButton Sing_Out;
    @FXML
    private JFXButton Calendar;
    @FXML
    private Circle Photo_Profile;
    @FXML
    private Label DOCOTR_NAME;
    @FXML
    private Label medical_specialties;

    @FXML
    private void UserProfileButtonClick(ActionEvent event) {
        navigateTo("Doctorpageprofile.fxml");
    }

    @FXML
    private void SignOutButtonClick(ActionEvent event) {
        navigateTo("Login.fxml");
    }
    
    @FXML
    private void initialize() {
        // Retrieve and display doctor information
        int doctorId = 1; // Replace with the actual doctor_id
        displayDoctorInformation(doctorId);
    }

    private void displayDoctorInformation(int doctorId) {
        try {
            // Connect to the database
            Connection conn = (Connection) DriverManager.getConnection("jdbc:mysql://your_database_url", "your_username", "your_password");

            // Retrieve doctor information from the database
            String query = "SELECT firstnameD, lastnameD, specialty, photo_path FROM doctorprofile WHERE doctor_id = ?";
            PreparedStatement pst = (PreparedStatement) conn.prepareStatement(query);
            pst.setInt(1, doctorId);
            ResultSet rs = pst.executeQuery();

            // Display doctor information in the UI
            if (rs.next()) {
                String firstName = rs.getString("firstnameD");
                String lastName = rs.getString("lastnameD");
                String specialty = rs.getString("specialty");
                String photoPath = rs.getString("photo_path");

                // Display doctor name
                String doctorName = "Dr " + firstName + " " + lastName;
                DOCOTR_NAME.setText(doctorName);

                // Display medical specialties
                medical_specialties.setText(specialty);

                // Display photo
                if (photoPath != null) {
                    Image photo = new Image("file:" + photoPath);
                    Photo_Profile.setFill(new ImagePattern(photo));
                }
            }

            // Close resources
            rs.close();
            pst.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void navigateTo(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

            Stage currentStage = (Stage) User_Profile.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

}
