package Admin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Add extends Load {
    @FXML
    private void addWorker(ActionEvent event) {
        String sqlInsert = "INSERT INTO Pracownik(fname,lname,email,DOB,ID_Firmy) VALUES (?,?,?,?,?) ";
        try {
            PreparedStatement ps = conn.prepareStatement(sqlInsert);
            ps.setString(1, this.fname.getText());
            ps.setString(2, this.lname.getText());
            ps.setString(3, this.email.getText());
            ps.setString(4, this.dob.getEditor().getText());
            ps.setString(5, this.idf.getText());
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void addevent(ActionEvent event) {
        String sqlInserte = "INSERT INTO Event(name_Event,Date) VALUES (?,?) ";
        try {
            PreparedStatement ps = conn.prepareStatement(sqlInserte);
            ps.setString(1, this.nameevent.getText());
            ps.setString(2, this.devent.getEditor().getText());

            ps.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void addLogin(ActionEvent event) {
        String sqlInsertl = "INSERT INTO Login(username,pass,division) VALUES (?,?,?) ";
        try {
            PreparedStatement ps = conn.prepareStatement(sqlInsertl);
            ps.setString(1, this.nameUser.getText());
            ps.setString(2, this.passUser.getText());
            //ps.setString(3,this.divUser.getText());
            ps.setString(3, combodiv.getValue().toString());
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //zapis dla tabeli firmy
    @FXML
    private void addFirmy(ActionEvent event) {
        String sqlInsertf = "INSERT INTO Firma(Nazwa_Firmy) VALUES (?) ";
        try {
            PreparedStatement ps = conn.prepareStatement(sqlInsertf);
            ps.setString(1, this.namefirma.getText());
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}