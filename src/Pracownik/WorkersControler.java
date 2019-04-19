package Pracownik;

import dbUtil.dbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class WorkersControler extends Reboot implements Initializable {

    public void initialize(URL url, ResourceBundle rb) {
        reboot();
        this.dc = new dbConnection();
        filtrProj();
        filtrWyd();
    }


//Wyszukwinaie
    public void filtrWyd() {
        FilteredList<EventsDataWorker> filteredData = new FilteredList<>(dataw, p -> true);
        filterFieldWyd.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(person -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (person.getName_Event().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (person.getDate().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (person.getID_Event().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });
        SortedList<EventsDataWorker> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(workerWydtable.comparatorProperty());
        workerWydtable.setItems(sortedData);

    }

    public void filtrProj() {
        FilteredList<EventsDataWorker> filteredData = new FilteredList<>(datap, p -> true);
        filterFieldProj.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(person -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if (person.getName_Event().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (person.getDate().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (person.getID_Event().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });
        SortedList<EventsDataWorker> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(workerProjtable.comparatorProperty());
        workerProjtable.setItems(sortedData);
    }

}




