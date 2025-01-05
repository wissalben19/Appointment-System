package hospiflow1;

import com.mysql.jdbc.PreparedStatement;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

public class DoctorController {

    @FXML
    private TextField firstName;

    @FXML
    private TextField lastName;

    @FXML
    private ComboBox<String> gender;

    @FXML
    private TextField phone;

    @FXML
    private TextField emaildoctor;

    @FXML
    private PasswordField passworddoctor;

    @FXML
    private Button registerButton;

    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;

    @FXML
    private void initialize() {
        gender.getItems().addAll("Female", "Male");
    }

    @FXML
    void handleRegistration() throws IOException {

        String firstnameD = firstName.getText();
        String lastnameD = lastName.getText();
        String phoneNumberD = phone.getText();
        String emailD = emaildoctor.getText();
        String passwordD = passworddoctor.getText();
        String genderd = gender.getValue();
        if (firstnameD.isEmpty() || lastnameD.isEmpty() || emailD.isEmpty() || passwordD.isEmpty() ||phoneNumberD.isEmpty() || genderd == null) {
            firstName.setStyle("-fx-border-color: red;");
            lastName.setStyle("-fx-border-color: red;");
            emaildoctor.setStyle("-fx-border-color: red;");
            passworddoctor.setStyle("-fx-border-color: red;");
            gender.setStyle("-fx-border-color: red;");
            phone.setStyle("-fx-border-color: red;");
            return;
        }
        if(!isNumeric(phoneNumberD)){
            phone.setStyle("-fx-border-color: red;");
            return;
        }
        if (!isValidEmail(emailD)) {
           emaildoctor.setStyle("-fx-border-color: red;");
            return;
        }
        conn = (Connection) mysqlpage.ConnectDb();
        String sql = "INSERT INTO doctorlogin(firstnameD, lastnameD, email, password, phone, genderD)VALUES(?, ?, ?, ?, ?, ?)";
        try {
            pst = (PreparedStatement) conn.prepareStatement(sql);
            pst.setString(1, firstnameD);
            pst.setString(2, lastnameD);
            pst.setString(3, emailD);
            pst.setString(4, passwordD);
            pst.setString(5, phoneNumberD);
            pst.setString(6, gender.getValue().toString());
            pst.execute();
            registerButton.setDisable(true);
            JOptionPane.showMessageDialog(null, "Saved");
            registerButton.getScene().getWindow().hide();
            Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }

    }

     private boolean isValidEmail(String email) {
        return email.contains("@") && email.contains(".");

    }

    private boolean isNumeric(String str) {
        return str.matches("\\d+");
    }

    @FXML
    private void returnLoginPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);

            Stage currentStage = (Stage) registerButton.getScene().getWindow();
            currentStage.close();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

   
}
