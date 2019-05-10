package Model.Worker;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class WorkersDataForWorkerController {
    private final StringProperty ID_Pracownika;
    private final StringProperty firstName;
    private final StringProperty lastName;
    private final StringProperty email;
    private final StringProperty DOB;
    private final StringProperty ID_Firmy;
    private final StringProperty Stanowisko;
    private final StringProperty Pesel;

    public WorkersDataForWorkerController(String id, String firstName, String lastName, String email, String DOB, String ID_f, String Stan, String pesel) {
        this.ID_Pracownika = new SimpleStringProperty(id);
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.email = new SimpleStringProperty(email);
        this.DOB = new SimpleStringProperty(DOB);
        this.Stanowisko = new SimpleStringProperty(Stan);
        this.Pesel = new SimpleStringProperty(pesel);
        this.ID_Firmy = new SimpleStringProperty(ID_f);

    }

    public String getID_Pracownika() {
        return ID_Pracownika.get();
    }

    public void setID_Pracownika(String ID_Pracownika) {
        this.ID_Pracownika.set(ID_Pracownika);
    }

    public StringProperty ID_PracownikaProperty() {
        return ID_Pracownika;
    }

    public String getFirstName() {
        return firstName.get();
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public StringProperty firstNameProperty() {
        return firstName;
    }

    public String getLastName() {
        return lastName.get();
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public StringProperty lastNameProperty() {
        return lastName;
    }

    public String getEmail() {
        return email.get();
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public StringProperty emailProperty() {
        return email;
    }

    public String getDOB() {
        return DOB.get();
    }

    public void setDOB(String DOB) {
        this.DOB.set(DOB);
    }

    public StringProperty DOBProperty() {
        return DOB;
    }

    public String getStanowisko() {
        return Stanowisko.get();
    }

    public void setStanowisko(String stanowisko) {
        this.Stanowisko.set(stanowisko);
    }

    public StringProperty stanowiskoProperty() {
        return Stanowisko;
    }

    public String getPesel() {
        return Pesel.get();
    }

    public void setPesel(String pesel) {
        this.Pesel.set(pesel);
    }

    public StringProperty peselProperty() {
        return Pesel;
    }

    public String getID_Firmy() {
        return ID_Firmy.get();
    }

    public void setID_Firmy(String ID_Firmy) {
        this.ID_Firmy.set(ID_Firmy);
    }

    public StringProperty ID_FirmyProperty() {
        return ID_Firmy;
    }
}
