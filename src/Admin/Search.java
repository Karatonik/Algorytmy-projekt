package Admin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import java.util.stream.Collectors;

public class Search extends Add{

    @FXML
    public void SearchWorker() {
        String criteria = searchW.getText();

        try {
        } catch (Exception e) {

        }//stworzenie strumienia obiektów wyświetlanych w tabeli
        filteredWorkerList = workertable.getItems().stream().filter(person -> { //stworzenie filtra który wybierze tylko te obiekty które spełniają kryteria wyboru usera
            if (person.getID().contains(criteria)) {
                return true;
            } else if (person.firstNameProperty().getValue().contains(criteria)) {
                return true;
            } else if (person.lastNameProperty().getValue().contains(criteria)) {
                return true;
            } else if (person.emailProperty().getValue().contains(criteria)) {
                return true;

            } else //wyrażenie lambda zwraca true jeżeli chociaż jeden warunek został spełniony
                if (person.DOBProperty().getValue().contains(criteria)) {
                    return true;
                } else return person.ID_FirmyProperty().getValue().contains(criteria);
        }).collect(Collectors.toList());
        // stworzenie listy obiektów które pomyślnie przeszły przez filtr
        ObservableList<PracownikData> filteredPersons = FXCollections.observableArrayList(filteredWorkerList); //konwersja List na ObservableList
        workertable.setItems(filteredPersons); //wstawienie przefiltrowanej listy do tabeli
        if (searchW.getText().isEmpty()) {
            workertable.setItems(data); //jeżeli textField wyszukiwarki jest pusty to przywraca listę wszystkich obiektów z powrotem do tabeli
        }
    }
    @FXML
    public void SearchLogin() {
        String criteria = searchL.getText();

        try {
        } catch (Exception e) {

        }
        filteredLoginList = loginTable.getItems().stream().filter(person -> {
            if (person.getUsername().contains(criteria)) {
                return true;
            } else return person.getDivision().contains(criteria);
        }).collect(Collectors.toList());
        ObservableList<LoginData> filteredPersons = FXCollections.observableArrayList(filteredLoginList);
        loginTable.setItems(filteredPersons);
        if (searchL.getText().isEmpty()) {
            loginTable.setItems(datalog);
        }
    }
    @FXML
    public void SearchFirma() {
        String criteria = searchF.getText();

        try {
        } catch (Exception e) {

        }
        filteredFirmaList = firmatable.getItems().stream().filter(person -> {
            if (person.getID_Firmy().contains(criteria)) {
                return true;
            } else return person.getNazwa_Firmy().contains(criteria);
        }).collect(Collectors.toList());
        ObservableList<FirmyData> filteredPersons = FXCollections.observableArrayList(filteredFirmaList);
        firmatable.setItems(filteredPersons);
        if (searchF.getText().isEmpty()) {
            firmatable.setItems(dataf);
        }
    }
    @FXML
    public void SearchEvent() {
        String criteria = searchE.getText();

        try {
        } catch (Exception e) {

        }
        filteredEventList = eventtable.getItems().stream().filter(person -> {
            if (person.getID_Event().contains(criteria)) {
                return true;
            } else if (person.getDate().contains(criteria)) {
                return true;
            } else return person.getName_Event().contains(criteria);
        }).collect(Collectors.toList());
        ObservableList<EventData> filteredPersons = FXCollections.observableArrayList(filteredEventList);
        eventtable.setItems(filteredPersons);
        if (searchE.getText().isEmpty()) {
            eventtable.setItems(dataev);
        }
    }
}
