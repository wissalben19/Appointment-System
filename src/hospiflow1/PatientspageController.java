package hospiflow1;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PatientspageController {

    @FXML
    private TextField First_Name;
    @FXML
    private TextField Last_Name;
    @FXML
    private TextField Additional_information;
    @FXML
    private TextField Phone_Number;
    @FXML
    private JFXComboBox Gender;
    @FXML
    private JFXDatePicker Date_birth;
    @FXML
    private JFXComboBox Classify_the_disease;
    @FXML
    private JFXButton Confirm;
    @FXML
    private Button Add_a_patient;
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

    com.mysql.jdbc.Connection conn = null;
    ResultSet rs = null;
    com.mysql.jdbc.PreparedStatement preparedStatement = null;

    @FXML
    private TableView<Patient> patientTableView;

    // Create columns for the TableView
    @FXML
    private TableColumn<Patient, String> noColumn;
    @FXML
    private TableColumn<Patient, String> firstNameColumn;
    @FXML
    private TableColumn<Patient, String> lastNameColumn;
    @FXML
    private TableColumn<Patient, String> phoneNumberColumn;
    @FXML
    private TableColumn<Patient, String> dateOfBirthColumn;
    @FXML
    private TableColumn<Patient, String> genderColumn;
    @FXML
    private TableColumn<Patient, String> diseaseColumn;
    @FXML
    private TableColumn<Patient, String> additionalInfoColumn;

    // Create an ObservableList of patients
    private ObservableList<Patient> patients = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        Gender.getItems().addAll("Female", "Male");
        Classify_the_disease.getItems().addAll("Coronary Artery Disease", "Hypertension ", "Sinusitis", " Otitis Media", "Tonsillitis", "Alzheimer's Disease", "Parkinson's Disease");

    }

    public void tablepatients() {
        // Initialize columns
        noColumn.setCellValueFactory(new PropertyValueFactory<>("no"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        dateOfBirthColumn.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
        genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
        diseaseColumn.setCellValueFactory(new PropertyValueFactory<>("disease"));
        additionalInfoColumn.setCellValueFactory(new PropertyValueFactory<>("additionalInfo"));

        // Set items to the TableView
        patientTableView.setItems(patients);
    }

    @FXML
    private void UserProfile(ActionEvent event) {
        navigateTo("Receptionnistepage.fxml");
    }

    @FXML
    private void UserProfilepage(ActionEvent event) {
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

            Stage currentStage = (Stage) Confirm.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    private void addPatientToTable(Patient patient) {
        patients.add(patient);
    }

    @FXML
    private void confirmButtonAction(ActionEvent event) {
        // Get data from text fields and other input controls
        String firstName = First_Name.getText();
        String lastName = Last_Name.getText();
        String phoneNumber = Phone_Number.getText();
        String dateOfBirth = Date_birth.getValue() != null ? Date_birth.getValue().toString() : "";
        String gender = Gender.getValue() != null ? Gender.getValue().toString() : "Unknown";
        String disease = Classify_the_disease.getValue() != null ? Classify_the_disease.getValue().toString() : "";
        String additionalInfo = Additional_information.getText();
        Confirm.setDisable(true);

        // Create a new patient
        Patient newPatient = new Patient(firstName, lastName, phoneNumber, dateOfBirth, gender, disease, additionalInfo);

        // Add the patient to the TableView
        addPatientToTable(newPatient);

        // Insert data into the database
        insertPatientIntoDatabase(newPatient);
    }

    private void insertPatientIntoDatabase(Patient patient) {
        conn = (com.mysql.jdbc.Connection) mysqlpage.ConnectDb();

        String sql = "INSERT INTO patients (first_name, last_name, phone_number, date_of_birth, gender, disease, additional_info) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            preparedStatement = (com.mysql.jdbc.PreparedStatement) conn.prepareStatement(sql);
            preparedStatement.setString(1, patient.getFirstName());
            preparedStatement.setString(2, patient.getLastName());
            preparedStatement.setString(3, patient.getPhoneNumber());
            preparedStatement.setString(4, patient.getDateOfBirth());
            preparedStatement.setString(5, patient.getGender());
            preparedStatement.setString(6, patient.getDisease());
            preparedStatement.setString(7, patient.getAdditionalInfo());

            // Execute the update
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error inserting patient into the database: " + e.getMessage());
            e.printStackTrace(); // Print the stack trace for detailed error information
        }
    }

}
