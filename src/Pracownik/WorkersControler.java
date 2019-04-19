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

    @FXML
    public void SearchWyd() {
        String criteria = searchWyd.getText();

        try {
        } catch (Exception e) {
        }
        filteredWydList = workerWydtable.getItems().stream().filter(person -> {
            if (person.getID_Event().contains(criteria)) {
                return true;
            } else if (person.getDate().contains(criteria)) {
                return true;
            } else return person.getName_Event().contains(criteria);
        }).collect(Collectors.toList());
        ObservableList<EventsDataWorker> filteredPersons = FXCollections.observableArrayList(filteredWydList);
        workerWydtable.setItems(filteredPersons);
        if (searchWyd.getText().isEmpty()) {
            workerWydtable.setItems(dataw);
        }
    }

    @FXML
    public void SearchProj() {
        String criteria = searchProj.getText();

        try {
        } catch (Exception e) {
        }
        filteredProjList = workerProjtable.getItems().stream().filter(person -> {
            if (person.getID_Event().contains(criteria)) {
                return true;
            } else if (person.getDate().contains(criteria)) {
                return true;
            } else if (person.getName_Event().contains(criteria)) {
                return true;
            } else return person.getStatus().contains(criteria);
        }).collect(Collectors.toList());
        ObservableList<EventsDataWorker> filteredPersons = FXCollections.observableArrayList(filteredProjList);
        workerProjtable.setItems(filteredPersons);
        if (searchProj.getText().isEmpty()) {
            workerProjtable.setItems(datap);
        }
    }

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




