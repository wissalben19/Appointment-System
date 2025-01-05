
package hospiflow1;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class DoctorpagetableController {


    @FXML
    private TextField First_Name;
    @FXML
    private TextField Last_Name;
    @FXML
    private TextField Phone_Number;
    @FXML
    private JFXComboBox Gender;
    @FXML
    private JFXComboBox Medical_Specialties;
    @FXML
    private JFXButton ADD;
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
    private JFXButton Patient;
    @FXML
    private JFXButton Doctors;

    @FXML
    private void UserProfile(ActionEvent event) {
        navigateTo("Receptionnistepage.fxml");
    }

    @FXML
    private void UserProfilepage(ActionEvent event) {
        navigateTo("Receptionnistepageprofile.fxml");
    }

    @FXML
    private void SignOut(ActionEvent event) {
        navigateTo("Login.fxml");
    }

    @FXML
    private void patientpage(ActionEvent event) {
        navigateTo("Patientspage.fxml");
    }

    private void navigateTo(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

            Stage currentStage = (Stage) ADD.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }
}
