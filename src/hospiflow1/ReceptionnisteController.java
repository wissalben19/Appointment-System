package hospiflow1;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

public class ReceptionnisteController {

    @FXML
    private TextField firstName;

    @FXML
    private TextField lastName;

    @FXML
    private TextField email;

    @FXML
    private PasswordField password;

    @FXML
    private Button registerButton;

    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;

    @FXML
    private void initialize() {

    }

    @FXML
    void handleRegistration() throws IOException {
        String firstNameValue = firstName.getText();
        String lastNameValue = lastName.getText();
        String emailValue = email.getText();
        String passwordValue = password.getText();

        // Validate the input fields
        if (firstNameValue.isEmpty() || lastNameValue.isEmpty() || emailValue.isEmpty() || passwordValue.isEmpty()) {
            firstName.setStyle("-fx-border-color: red;");
            lastName.setStyle("-fx-border-color: red;");
            email.setStyle("-fx-border-color: red;");
            password.setStyle("-fx-border-color: red;");
            return;
        }
        conn = (Connection) mysqlpage.ConnectDb();
        String sql = "INSERT INTO receptionnistelogin(firstnameR,lastnameR,email,password) VALUES (?,?,?,?)";
        try {
            pst = (PreparedStatement) conn.prepareStatement(sql);
            pst.setString(1, firstNameValue);
            pst.setString(2, lastNameValue);
            pst.setString(3, emailValue);
            pst.setString(4, passwordValue);
            pst.execute();
            registerButton.setDisable(true);
            JOptionPane.showMessageDialog(null, "Saved");
            registerButton.getScene().getWindow().hide();
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

    @FXML
    private void returnloginPage() {
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
