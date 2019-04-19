package Firma;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import java.util.stream.Collectors;

public class Search extends Add{
    @FXML
    public void SearchWyd() {
        String criteria = searchWyd.getText();

        try {
        } catch (Exception e) {

        }
        filteredWydList = WydTable.getItems().stream().filter(person -> {
            if (person.getID_Event().contains(criteria)) {
                return true;
            } else if (person.getDate().contains(criteria)) {
                return true;
            } else return person.getName_Event().contains(criteria);
        }).collect(Collectors.toList());
        ObservableList<EventsData> filteredPersons = FXCollections.observableArrayList(filteredWydList);
        WydTable.setItems(filteredPersons);
        if (searchWyd.getText().isEmpty()) {
            WydTable.setItems(dataw);
        }
    }

    @FXML
    public void SearchProj() {
        String criteria = searchProj.getText();

        try {
        } catch (Exception e) {

        }
        filteredProjList = ProjektTable.getItems().stream().filter(person -> {
            if (person.getID_Event().contains(criteria)) {
                return true;
            } else if (person.getDate().contains(criteria)) {
                return true;
            } else return person.getName_Event().contains(criteria);
        }).collect(Collectors.toList());
        ObservableList<EventsData> filteredPersons = FXCollections.observableArrayList(filteredProjList);
        ProjektTable.setItems(filteredPersons);
        if (searchProj.getText().isEmpty()) {
            ProjektTable.setItems(dataproj);
        }
    }

    @FXML
    public void SearchWorkers() {
        String criteria = searchWorker.getText();

        try {
        } catch (Exception e) {

        }
        filteredWorkerList = workertable.getItems().stream().filter(person -> {
            if (person.getID_Pracownika().contains(criteria)) {
                return true;
            } else if (person.firstNameProperty().getValue().contains(criteria)) {
                return true;
            } else if (person.getPesel().contains(criteria)) {
                return true;
            } else if (person.getStanowisko().contains(criteria)) {
                return true;
            } else if (person.lastNameProperty().getValue().contains(criteria)) {
                return true;
            } else if (person.emailProperty().getValue().contains(criteria)) {
                return true;
            } else if (person.DOBProperty().getValue().contains(criteria)) {
                return true;
            } else return person.ID_FirmyProperty().getValue().contains(criteria);
        }).collect(Collectors.toList());

        ObservableList<WorkerData> filteredPersons = FXCollections.observableArrayList(filteredWorkerList);
        workertable.setItems(filteredPersons);
        if (searchWorker.getText().isEmpty()) {
            workertable.setItems(datap);
        }
    }

}
