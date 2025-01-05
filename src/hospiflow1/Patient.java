
package hospiflow1;
import javafx.beans.property.SimpleStringProperty;

public class Patient {
    private final SimpleStringProperty firstName;
    private final SimpleStringProperty lastName;
    private final SimpleStringProperty phoneNumber;
    private final SimpleStringProperty dateOfBirth;
    private final SimpleStringProperty gender;
    private final SimpleStringProperty disease;
    private final SimpleStringProperty additionalInfo;

    public Patient(String firstName, String lastName, String phoneNumber, String dateOfBirth,
                   String gender, String disease, String additionalInfo) {
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.phoneNumber = new SimpleStringProperty(phoneNumber);
        this.dateOfBirth = new SimpleStringProperty(dateOfBirth);
        this.gender = new SimpleStringProperty(gender);
        this.disease = new SimpleStringProperty(disease);
        this.additionalInfo = new SimpleStringProperty(additionalInfo);
    }

    public String getFirstName() {
        return firstName.get();
    }

    public String getLastName() {
        return lastName.get();
    }

    public String getPhoneNumber() {
        return phoneNumber.get();
    }

    public String getDateOfBirth() {
        return dateOfBirth.get();
    }

    public String getGender() {
        return gender.get();
    }

    public String getDisease() {
        return disease.get();
    }

    public String getAdditionalInfo() {
        return additionalInfo.get();
    }
}
