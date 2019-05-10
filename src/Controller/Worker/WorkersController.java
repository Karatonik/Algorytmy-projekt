package Controller.Worker;

import Model.Worker.EventDataForWorkerController;
import Util.dbConnection;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class WorkersController extends ParentWorkersController implements Initializable {

    public void initialize(URL url, ResourceBundle rb) {
        reboot();
        this.dc = new dbConnection();
        filtrProj();
        filtrWyd();
    }


    //Wyszukwinaie
    public void filtrWyd() {
        FilteredList<EventDataForWorkerController> filteredData = new FilteredList<>(dataw, p -> true);
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
        SortedList<EventDataForWorkerController> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(workerWydtable.comparatorProperty());
        workerWydtable.setItems(sortedData);

    }

    public void filtrProj() {
        FilteredList<EventDataForWorkerController> filteredData = new FilteredList<>(datap, p -> true);
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
        SortedList<EventDataForWorkerController> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(workerProjtable.comparatorProperty());
        workerProjtable.setItems(sortedData);
    }

}




