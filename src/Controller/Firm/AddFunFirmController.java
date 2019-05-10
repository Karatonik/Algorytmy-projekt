package Controller.Firm;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddFunFirmController extends LoadFunFirmController {
    //zapis do tabeli wydarzenia
    @FXML
    private void addWyd(ActionEvent event) {

        String sqlInsert = "INSERT INTO Event(name_Event,Date,ID_Firmy,PW) VALUES (?,?,?,?) ";
        try {
            PreparedStatement ps = conn.prepareStatement(sqlInsert);
            ps.setString(1, this.nazwaw.getText());
            ps.setString(2, this.dobw.getEditor().getText());
            ps.setInt(3, id_Firmy);
            ps.setString(4, "W");
            ps.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }





    //zapis dla tabeli projekt
    @FXML
    private void addProj(ActionEvent event) {

        String sqlInsert = "INSERT INTO Event(name_Event,Date,ID_Firmy,PW) VALUES (?,?,?,?) ";
        try {
            PreparedStatement ps = conn.prepareStatement(sqlInsert);
            ps.setString(1, this.nazwap.getText());
            ps.setString(2, this.dobproj.getEditor().getText());
            ps.setInt(3, id_Firmy);
            ps.setString(4, "P");
            ps.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    //odczyt dla tabeli pracownik
    @FXML
    private void addWorkers(ActionEvent event) {

        String sqlInsert = "INSERT INTO Pracownik(fname,lname,email,DOB,ID_Firmy,Stanowisko,Pesel) VALUES (?,?,?,?,?,?,?) ";
        try {
            PreparedStatement ps = conn.prepareStatement(sqlInsert);
            ps.setString(1, this.name.getText());
            ps.setString(2, this.lname.getText());
            ps.setString(3, this.email.getText());
            ps.setString(4, this.dobp.getEditor().getText());
            ps.setInt(5, id_Firmy);
            ps.setString(6, this.Stan.getText());
            ps.setString(7, this.pesel.getText());
            ps.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
