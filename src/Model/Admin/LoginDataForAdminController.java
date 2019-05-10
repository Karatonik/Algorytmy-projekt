package Model.Admin;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class LoginDataForAdminController {
    private final StringProperty username;
    private final StringProperty pass;
    private final StringProperty division;


    public LoginDataForAdminController(String user, String pass, String div) {
        this.username = new SimpleStringProperty(user);
        this.pass = new SimpleStringProperty(pass);
        this.division = new SimpleStringProperty(div);
    }

    public String getUsername() {
        return username.get();
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public StringProperty usernameProperty() {
        return username;
    }

    public String getPass() {
        return pass.get();
    }

    public void setPass(String pass) {
        this.pass.set(pass);
    }

    public StringProperty passProperty() {
        return pass;
    }

    public String getDivision() {
        return division.get();
    }

    public void setDivision(String division) {
        this.division.set(division);
    }

    public StringProperty divisionProperty() {
        return division;
    }
}