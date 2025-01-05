/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hospiflow1;

/**
 *
 * @author CabCom
 */
public class Appiontment {
    private int no;
    private String firstName;
    private String lastName;
    private int age;
    private String gender;
    private String dateIn;
    private String timeIn;
    private String doctor;
    private String type;

    public Appiontment(int no, String firstName, String lastName, int age, String gender, String dateIn, String timeIn, String doctor, String type) {
        this.no = no;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.gender = gender;
        this.dateIn = dateIn;
        this.timeIn = timeIn;
        this.doctor = doctor;
        this.type = type;
    }
    public int getNo() {
        return no;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public String getDateIn() {
        return dateIn;
    }

    public String getTimeIn() {
        return timeIn;
    }

    public String getDoctor() {
        return doctor;
    }

    public String getType() {
        return type;
    }
}
