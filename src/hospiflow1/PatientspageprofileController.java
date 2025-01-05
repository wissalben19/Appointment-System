package hospiflow1;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
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
import javafx.scene.control.TextField;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class PatientspageprofileController {

    @FXML
    private JFXTextField Weigt;
    @FXML
    private JFXTextField Address;
    @FXML
    private JFXTextField Height;
    @FXML
    private JFXTextField PhoneNumber;
    @FXML
    private JFXComboBox Blood_Type ;
    @FXML
    private Button Save;
    @FXML
    private Label Number_ofp;
    @FXML
    private Button User_Profile;
    @FXML
    private Button Messages;
    @FXML
    private Button Settings;
    @FXML
    private Button Sing_Out;;

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

            Stage currentStage = (Stage) Save.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

}
