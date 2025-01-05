package hospiflow1;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

public class DoctorpageprofileController {

    @FXML
    private TextField First_Name;
    @FXML
    private TextField Last_Name;
    @FXML
    private TextField Address;
    @FXML
    private TextField Email;
    @FXML
    private TextField Phone;
    @FXML
    private JFXComboBox<String> Gender;
    @FXML
    private JFXDatePicker Date_birth;
    @FXML
    private JFXComboBox<String> Specialty;
    @FXML
    private JFXRadioButton workdayMonday;
    @FXML
    private JFXRadioButton workdayTuesday;
    @FXML
    private JFXRadioButton workdayWednesday;
    @FXML
    private JFXRadioButton workdayThursday;
    @FXML
    private JFXRadioButton workdayFriday;
    @FXML
    private JFXRadioButton workdaySaturday;
    @FXML
    private JFXRadioButton workdaySunday;
    @FXML
    private Button Engister;
    @FXML
    private Circle Photo_Profile;
    @FXML
    private JFXButton change_photo_profile;
    @FXML
    private Label DOCOTR_NAME;
    @FXML
    private Label Medical_specialties;
    @FXML
    private Label Doctor_Phone;
    @FXML
    private JFXButton returnpage;
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
    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;

    @FXML
    private void initialize() {
        Gender.getItems().addAll("Female", "Male");
        Specialty.getItems().addAll("Heart and circulatory diseases", "Ear, nose and throat diseases", "Nervous system diseases");
    }

    @FXML
    private void UserProfileButtonClick(ActionEvent event) {
        navigateTo("DoctorPage.fxml");
    }

    @FXML
    private void SignOutButtonClick(ActionEvent event) {
        navigateTo("Login.fxml");
    }

    private void navigateTo(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

            Stage currentStage = (Stage) Engister.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    @FXML
    private void handleEngister() {
        // Get values from the UI controls
        String firstName = First_Name.getText();
        String lastName = Last_Name.getText();
        String address = Address.getText();
        String email = Email.getText();
        String phone = Phone.getText();
        String gender = Gender.getValue().toString();
        LocalDate birthDate = Date_birth.getValue();
        String specialty = Specialty.getValue().toString();
        ;

        // Assuming you have a doctor_id available (retrieved during login or elsewhere)
        int doctorId = 1; // Replace with the actual doctor_id
        conn = (Connection) mysqlpage.ConnectDb();

        String updateQuery = "INSERT INTO doctorprofile (doctor_id, firstnameD, lastnameD, address, email, phone, genderD, workdays, specialty, date_of_birth, photo_path)\n"
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)\n"
                + "ON DUPLICATE KEY UPDATE\n"
                + "firstnameD = VALUES(firstnameD), lastnameD = VALUES(lastnameD),\n"
                + "address = VALUES(address), email = VALUES(email), phone = VALUES(phone),\n"
                + "genderD = VALUES(genderD), workdays = VALUES(workdays), specialty = VALUES(specialty), date_of_birth = VALUES(date_of_birth), photo_path = VALUES(photo_path)";

        try {
            pst = (PreparedStatement) conn.prepareStatement(updateQuery, Statement.RETURN_GENERATED_KEYS);
            pst.setInt(1, doctorId);
            pst.setString(2, firstName);
            pst.setString(3, lastName);
            pst.setString(4, address);
            pst.setString(5, email);
            pst.setString(6, phone);
            pst.setString(7, gender);
            String selectedWorkdays = getSelectedWorkdays();
            pst.setString(8, selectedWorkdays);
            pst.setString(9, specialty);
            String photoPath = handlePhotoUpload();
            pst.setString(10, photoPath);
            pst.setDate(11, java.sql.Date.valueOf(birthDate));

            int affectedRows = pst.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Insert/update failed, no rows affected.");
            }

            try (ResultSet generatedKeys = pst.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    doctorId = generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Insert/update failed, no ID obtained.");
                }
            }

            String doctorName = "Dr " + firstName + " " + lastName;
            DOCOTR_NAME.setText(doctorName);
            String Ms = specialty;
            Medical_specialties.setText(Ms);
            String phoneM = phone;
            Doctor_Phone.setText(phoneM);
            JOptionPane.showMessageDialog(null, "Doctor information updated successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error updating doctor information: " + e.getMessage());
        }
    }

    private String getSelectedWorkdays() {
        // Concatenate selected workdays
        StringBuilder workdaysBuilder = new StringBuilder();
        if (workdayMonday.isSelected()) {
            workdaysBuilder.append("Monday, ");
        }
        if (workdayTuesday.isSelected()) {
            workdaysBuilder.append("Tuesday, ");
        }
        if (workdayWednesday.isSelected()) {
            workdaysBuilder.append("Wednesday, ");
        }
        if (workdayThursday.isSelected()) {
            workdaysBuilder.append("Thursday, ");
        }
        if (workdayFriday.isSelected()) {
            workdaysBuilder.append("Friday, ");
        }
        if (workdaySaturday.isSelected()) {
            workdaysBuilder.append("Saturday, ");
        }
        if (workdaySunday.isSelected()) {
            workdaysBuilder.append("Sunday, ");
        }

        // Remove trailing comma and space
        String selectedWorkdays = workdaysBuilder.toString().replaceAll(", $", "");

        return selectedWorkdays;
    }

    @FXML
    private void handleChangePhoto(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            // Handle the selected file (e.g., display it or save its path)
            String imagePath = selectedFile.getAbsolutePath();

            // Update the Photo_Profile Circle with the selected image
            Image image = new Image(new File(imagePath).toURI().toString());
            Photo_Profile.setFill(new ImagePattern(image));
        }
    }

    private String handlePhotoUpload() throws SQLException {
        String destinationDirectory = "photos";
        int doctorId = 1; // Replace with the actual doctor_id
        String fileName = "doctor_" + doctorId + ".png"; // You may want to use a unique identifier for each doctor

        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
            File selectedFile = fileChooser.showOpenDialog(null);

            if (selectedFile != null) {
                File destinationFile = new File(destinationDirectory, fileName);
                Files.copy(selectedFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                return destinationFile.getAbsolutePath();
            } else {
                throw new SQLException("No photo selected.");
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new SQLException("Error uploading photo: " + e.getMessage());
        }
    }

}
