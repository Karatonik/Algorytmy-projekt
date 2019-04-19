package Firma;

import dbUtil.dbConnection;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.Initializable;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static Loginapp.LoginControler.namef;

public class FirmaControler extends Add implements Initializable {

    //konstruktor
    public FirmaControler() {
        try {
            conn = dbConnection.getConnection();
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            System.err.println("ERROR" + e);
        }
        try {           //Szuka po nazwie firmy numeru firmy :)
            String sqlcheck = "SELECT ID_Firmy FROM Firma WHERE Nazwa_Firmy='" + namef + "';";
            ResultSet rs = conn.createStatement().executeQuery(sqlcheck);
            id_Firmy = rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //metoda interfejsu
    public void initialize(URL url, ResourceBundle rb) {
        this.dc = new dbConnection();
        loadWydData();
        loadProjData();
        loadWorkerData();
        filtrWyd();
        filtrProj();
        filtrWorker();
    }

    public void filtrWyd() {
        FilteredList<EventsData> filteredData = new FilteredList<>(dataw, p -> true);
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
        SortedList<EventsData> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(WydTable.comparatorProperty());
        WydTable.setItems(sortedData);

    }

    public void filtrProj() {
        FilteredList<EventsData> filteredData = new FilteredList<>(dataproj, p -> true);
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
        SortedList<EventsData> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(ProjektTable.comparatorProperty());
        ProjektTable.setItems(sortedData);
    }

    public void filtrWorker() {
        FilteredList<WorkerData> filteredData = new FilteredList<>(datap, p -> true);
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
                } else if (person.getID_Pracownika().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (person.getID_Firmy().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (person.getDOB().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (person.getPesel().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (person.getStanowisko().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (person.getEmail().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });
        SortedList<WorkerData> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(workertable.comparatorProperty());
        workertable.setItems(sortedData);
    }
}
