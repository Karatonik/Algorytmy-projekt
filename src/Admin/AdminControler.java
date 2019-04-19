package Admin;

import Loginapp.option;
import dbUtil.dbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import static Admin.Updata.updata;

public class AdminControler extends Search implements Initializable {
    //konstruktor
    public AdminControler() {
        try {
            conn = dbConnection.getConnection();
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            System.err.println("ERROR" + e);

        }


    }
    public void initialize(URL url, ResourceBundle rb) {
        this.dc = new dbConnection();
        this.combodiv.setItems(FXCollections.observableArrayList(option.values()));
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
    //odczyt dla tabeli pracownik
    //zapis dla tabeli pracownik

    //czyszczenie pól tekstowych
    @FXML
    public void clearWorkerFild(ActionEvent event) {
        this.id.setText("");
        this.fname.setText("");
        this.lname.setText("");
        this.email.setText("");
        this.idf.setText("");
        this.dob.setValue(null);
    }
    //zapis dla tabeli eventy

    public void filtrWorker() {
        FilteredList<PracownikData> filteredData = new FilteredList<>(data, p -> true);
        filterFieldWorker.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(person -> {
                if (newValue == null || newValue.isEmpty()) {
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
                } else if (person.getEmail().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });
        SortedList<PracownikData> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(workertable.comparatorProperty());
        workertable.setItems(sortedData);
    }
    public void filtrEvent() {
        FilteredList<EventData> filteredData = new FilteredList<>(dataev, p -> true);
        filterFieldEvent.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(person -> {
                // If filter text is empty, display all persons.
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
        SortedList<EventData> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(eventtable.comparatorProperty());
        eventtable.setItems(sortedData);
    }
    public void filtrFirma() {
        FilteredList<FirmyData> filteredData = new FilteredList<>(dataf, p -> true);
        filterFieldFirma.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(person -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (person.getNazwa_Firmy().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (person.getID_Firmy().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });
        SortedList<FirmyData> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(firmatable.comparatorProperty());
        firmatable.setItems(sortedData);
    }
    public  void filtrLogin() {
        FilteredList<LoginData> filteredData = new FilteredList<>(datalog, p -> true);
        filterFieldLogin.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(person -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if (person.getDivision().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (person.getUsername().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });
        SortedList<LoginData> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(loginTable.comparatorProperty());
        loginTable.setItems(sortedData);
    }
}
