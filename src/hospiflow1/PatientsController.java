package hospiflow1;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import com.mysql.fabric.xmlrpc.base.Data;
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

public class PatientsController {

    @FXML
    private TextField firstNamep;

    @FXML
    private TextField lastNamep;

    @FXML
    private TextField emailp;

    @FXML
    private PasswordField passwordp;

    @FXML
    private JFXComboBox<String> genderp;

    @FXML
    private JFXButton createAccountButton;
    com.mysql.jdbc.Connection conn = null;
    ResultSet rs = null;
    com.mysql.jdbc.PreparedStatement pst = null;

    @FXML
    public void initialize() {

        genderp.getItems().addAll("Male", "Female");

    }

    @FXML
    private void createAccount() throws IOException {
        String firstName = firstNamep.getText();
        String lastName = lastNamep.getText();
        String email = emailp.getText();
        String password = passwordp.getText();
        String gender = genderp.getValue();

        // Validate input
        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            firstNamep.setStyle("-fx-border-color: red;");
            lastNamep.setStyle("-fx-border-color: red;");
            emailp.setStyle("-fx-border-color: red;");
            passwordp.setStyle("-fx-border-color: red;");
            return;
        }
        if (gender == null) {
            genderp.setStyle("-fx-border-color: red;");
            return;
        }
        if (!isValidEmail(email)) {
            emailp.setStyle("-fx-border-color: red;");
            return;
        }

        conn = (com.mysql.jdbc.Connection) (Connection) mysqlpage.ConnectDb();
        String sql = "INSERT INTO patientregistration( firstnameP, lastnameP, email, password, genderP) VALUES (?, ?, ?, ?, ?)";

        try {
            pst = (com.mysql.jdbc.PreparedStatement) (PreparedStatement) conn.prepareStatement(sql);
            pst.setString(1, firstName);
            pst.setString(2, lastName);
            pst.setString(3, email);
            pst.setString(4, password);
            pst.setString(5, genderp.getValue().toString());
            pst.execute();
            createAccountButton.setDisable(true);
            JOptionPane.showMessageDialog(null, "Saved");
            createAccountButton.getScene().getWindow().hide();
            Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
            Stage mainStage = new Stage();
            Scene scene = new Scene(root);
            mainStage.setScene(scene);
            mainStage.show();

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }

    private boolean isValidEmail(String email) {
        return email.contains("@") && email.contains(".");
    }

    @FXML
    private void returnLoginPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);

            Stage currentStage = (Stage) createAccountButton.getScene().getWindow();
            currentStage.close();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
