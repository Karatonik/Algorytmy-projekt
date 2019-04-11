package Admin;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class LoginData {
    private final StringProperty username;
    private final StringProperty pass;
    private final StringProperty division;


    public LoginData(String user,String pass,String div){
        this.username = new SimpleStringProperty(user);
        this.pass= new SimpleStringProperty(pass);
        this.division = new SimpleStringProperty(div);
    }

    public String getUsername() {
        return username.get();
    }

    public StringProperty usernameProperty() {
        return username;
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public String getPass() {
        return pass.get();
    }

    public StringProperty passProperty() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass.set(pass);
    }

    public String getDivision() {
        return division.get();
    }

    public StringProperty divisionProperty() {
        return division;
    }

    public void setDivision(String division) {
        this.division.set(division);
    }
}
