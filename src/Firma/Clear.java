package Firma;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class Clear extends Session {

    //czyszczenie pół tekstowych dla taeli wydarzenia
    @FXML
    public void clearWydFild(ActionEvent event) {
        this.nazwaw.setText("");
        this.dobw.setValue(null);
    }

    //czyści pola tekstowe dla tabeli projekt
    @FXML
    public void clearProjFild(ActionEvent event) {
        this.nazwap.setText("");
        this.dobproj.setValue(null);
    }
    //czyści pola tekstowa dla tabeli pravownik
    @FXML
    public void clearWorkerFild(ActionEvent event) {
        this.name.setText("");
        this.lname.setText("");
        this.email.setText("");
        this.Stan.setText("");
        this.pesel.setText("");
        this.dobp.setValue(null);
    }

}
