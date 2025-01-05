package hospiflow1;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
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
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class ReceptionnistepageprofileController {

    @FXML
    private TextField First_Name;
    @FXML
    private TextField Last_Name;
    @FXML
    private TextField Email;
    @FXML
    private PasswordField Password;
    @FXML
    private Button Update;
    @FXML
    private Label RNAME;
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
    private void SignOut(ActionEvent event) {
        navigateTo("Login.fxml");
    }
      @FXML
    private void Adddoctor (ActionEvent event) {
        navigateTo("Doctorpagetable.fxml");
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

            Stage currentStage = (Stage) Update.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

}
