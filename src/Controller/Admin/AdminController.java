package Controller.Admin;

import Model.Admin.EventDataForAdminController;
import Model.Admin.FirmDataForAdminController;
import Model.Admin.LoginDataForAdminController;
import Model.Admin.WorkerDataForAdminController;
import Model.Option;
import Util.dbConnection;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AdminController extends AddFunAdminController implements Initializable {
    //konstruktor
    public AdminController() {
        try {
            conn = dbConnection.getConnection();
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            System.err.println("ERROR" + e);

        }


    }

    public void initialize(URL url, ResourceBundle rb) { //metoda inicjująca okno
        this.dc = new dbConnection();
        this.combodiv.setItems(FXCollections.observableArrayList(Option.values()));
        loadWorkerData();
        loadEventData();
        loadLoginData();
        loadFirmaData();
        filtrWorker();
        filtrLogin();
        filtrFirma();
        filtrEvent();
    }

    //Potrzebna metoda to edytowalnej tablicy
    @FXML
    public void getRow() {
        TablePosition pos = workertable.getSelectionModel().getSelectedCells().get(0);
        int row = pos.getRow();
        TableColumn col = pos.getTableColumn();
        String data1 = (String) col.getCellObservableValue(row).getValue();
        System.out.println(data1);
    }

    //metody filtrujce treśc
    public void filtrWorker() { //metoda  używa technologi lambda
        FilteredList<WorkerDataForAdminController> filteredData = new FilteredList<>(data, p -> true); //używa obiektu filteredList by znaleśc day element w liście
        filterFieldWorker.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(person -> {
                if (newValue == null || newValue.isEmpty()) {//sprawdza czy instnieje dana wartość w iscie
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (person.getFirstName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (person.getLastName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (person.getID().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (person.getID_Firmy().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (person.getDOB().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else return person.getEmail().toLowerCase().contains(lowerCaseFilter);
            });
        });
        SortedList<WorkerDataForAdminController> sortedData = new SortedList<>(filteredData); //tworzy posortowaną listę
        sortedData.comparatorProperty().bind(workertable.comparatorProperty());
        workertable.setItems(sortedData); //zwraca posortowaną listę
    }

    public void filtrEvent() {
        FilteredList<EventDataForAdminController> filteredData = new FilteredList<>(dataev, p -> true);
        filterFieldEvent.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(person -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (person.getName_Event().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (person.getDate().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else return person.getID_Event().toLowerCase().contains(lowerCaseFilter);
            });
        });
        SortedList<EventDataForAdminController> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(eventtable.comparatorProperty());
        eventtable.setItems(sortedData);
    }

    public void filtrFirma() {
        FilteredList<FirmDataForAdminController> filteredData = new FilteredList<>(dataf, p -> true);
        filterFieldFirma.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(person -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (person.getNazwa_Firmy().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else return person.getID_Firmy().toLowerCase().contains(lowerCaseFilter);
            });
        });
        SortedList<FirmDataForAdminController> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(firmatable.comparatorProperty());
        firmatable.setItems(sortedData);
    }

    public void filtrLogin() {
        FilteredList<LoginDataForAdminController> filteredData = new FilteredList<>(datalog, p -> true);
        filterFieldLogin.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(person -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if (person.getDivision().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else return person.getUsername().toLowerCase().contains(lowerCaseFilter);
            });
        });
        SortedList<LoginDataForAdminController> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(loginTable.comparatorProperty());
        loginTable.setItems(sortedData);
    }
}
