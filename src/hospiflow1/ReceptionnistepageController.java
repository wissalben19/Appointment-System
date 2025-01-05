package hospiflow1;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTimePicker;
import com.mysql.jdbc.PreparedStatement;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import com.mysql.jdbc.PreparedStatement;
import javafx.scene.control.cell.PropertyValueFactory;

public class ReceptionnistepageController {

    @FXML
    private TextField First_Name;
    @FXML
    private TextField Last_Name;
    @FXML
    private TextField Age;
    @FXML
    private JFXComboBox Select_doctor;
    @FXML
    private JFXComboBox Gender;
    @FXML
    private JFXDatePicker Date;
    @FXML
    private JFXTimePicker Time;
    @FXML
    private Button Engister;
    @FXML
    private Label Receptionniste_NAME;
    @FXML
    private JFXButton Patient;
    @FXML
    private JFXButton User_Profile;
    @FXML
    private JFXButton Messages;
    @FXML
    private JFXButton Settings;
    @FXML
    private JFXButton Sing_Out;
    @FXML
    private JFXButton Doctors;
    @FXML
    private TableView<Appiontment> patientTableView;

    @FXML
    private TableColumn<Appiontment, Integer> colNo;
    @FXML
    private TableColumn<Appiontment, String> colFirstName;
    @FXML
    private TableColumn<Appiontment, String> colLastName;
    @FXML
    private TableColumn<Appiontment, Integer> colAge;
    @FXML
    private TableColumn<Appiontment, String> colGende;
    @FXML
    private TableColumn<Appiontment, String> colDateIn;
    @FXML
    private TableColumn<Appiontment, String> colTimeIn;
    @FXML
    private TableColumn<Appiontment, String> colDoctor;
    @FXML
    private TableColumn<Appiontment, String> colType;

    private ObservableList<Appiontment> patientList = FXCollections.observableArrayList();
    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;
    private static final int MAX_APPOINTMENTS_PER_DAY = 30;

    @FXML
    private void initialize() {
        Gender.getItems().addAll("Female", "Male");
        initializeComboBoxItems();
    }

    @FXML
    private void UserProfile(ActionEvent event) {
        navigateTo("Receptionnistepageprofile.fxml");
    }

    @FXML
    private void Adddoctor(ActionEvent event) {
        navigateTo("Doctorpagetable.fxml");
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

            Stage currentStage = (Stage) Engister.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    @FXML
    private void initializeComboBoxItems() {
        // Clear existing items
        Select_doctor.getItems().clear();

        // Fetch doctor names and IDs from the database
        conn = (Connection) mysqlpage.ConnectDb();
        String sql = "SELECT doctor_id, CONCAT('Dr. ', firstnameD, ' ', lastnameD) AS doctor_name FROM doctorlogin";

        try {
            pst = (PreparedStatement) conn.prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {
                // Store the doctor_id and doctor_name in a custom class (Doctor) and add it to the ComboBox
                Doctor doctor = new Doctor(rs.getInt("doctor_id"), rs.getString("doctor_name"));
                Select_doctor.getItems().add(doctor);
            }

            // Set the default value for the ComboBox, assuming your Doctor class has a toString method
            Select_doctor.getSelectionModel().selectFirst();

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error fetching doctors: " + e.getMessage());
        }
    }

    public class Doctor {

        private int doctorId;
        private String doctorName;

        public Doctor(int doctorId, String doctorName) {
            this.doctorId = doctorId;
            this.doctorName = doctorName;
        }

        public int getDoctorId() {
            return doctorId;
        }

        @Override
        public String toString() {
            return doctorName;
        }
    }

    @FXML
    private void Engister(ActionEvent event) {
        String firstName = First_Name.getText();
        String lastName = Last_Name.getText();
        int age = Integer.parseInt(Age.getText());
        String selectedDoctor = Select_doctor.getValue() != null ? Select_doctor.getValue().toString() : "";

        String gender = Gender.getValue().toString();
        String dateOfBirth = Date.getValue().toString();
        String timeOfRegistration = Time.getValue().toString();
        Engister.setDisable(true);
        // Create an appointment instance
        Appiontment appointment = new Appiontment(1, firstName, lastName, age, gender, dateOfBirth, timeOfRegistration, selectedDoctor, "Type");

        // Debugging statement
        System.out.println("Adding appointment: " + appointment);

        // Add the appointment to the table view
        patientList.add(appointment);

        // Insert into the database
        insertAppointmentIntoDatabase(firstName, lastName, age, gender, dateOfBirth, timeOfRegistration, selectedDoctor);

        // Update the table view
        updateTableView();
    }

    private void insertAppointmentIntoDatabase(String firstName, String lastName, int age, String gender, String dateOfBirth, String timeOfRegistration, String selectedDoctor) {
        conn = (Connection) mysqlpage.ConnectDb();
        String sql = "INSERT INTO appiontment (firstname, lastname, age, gender, datein, timein, selectdoctor) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pst = (PreparedStatement) conn.prepareStatement(sql)) {
            pst.setString(1, firstName);
            pst.setString(2, lastName);
            pst.setInt(3, age);
            pst.setString(4, gender);
            pst.setString(5, dateOfBirth);
            pst.setString(6, timeOfRegistration);
            pst.setString(7, selectedDoctor);
            pst.execute();
            //pst.executeUpdate();

            JOptionPane.showMessageDialog(null, "User added successfully");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        } finally {
            // Close resources in the finally block
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateTableView() {
        // Update the table view with the new data
        colNo.setCellValueFactory(new PropertyValueFactory<>("no"));
        colFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        colLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        colAge.setCellValueFactory(new PropertyValueFactory<>("age"));
        colGende.setCellValueFactory(new PropertyValueFactory<>("gender"));
        colDateIn.setCellValueFactory(new PropertyValueFactory<>("dateIn"));
        colTimeIn.setCellValueFactory(new PropertyValueFactory<>("timeIn"));
        colDoctor.setCellValueFactory(new PropertyValueFactory<>("doctor"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));

        patientTableView.setItems(patientList);
    }

}
