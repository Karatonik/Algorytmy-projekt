package Firma;

import dbUtil.dbConnection;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.ArrayList;
import java.util.List;

public class Session {
    public static List<WorkerData> filteredWorkerList = new ArrayList<>();
    public static List<EventsData> filteredProjList, filteredWydList = new ArrayList<>();
    public final String sqlp = "SELECT * FROM Pracownik";
    public final String sqlproj = "SELECT * FROM Event WHERE PW='P'";
    public final String sqlw = "SELECT * FROM Event WHERE PW='W'";
    //połączenie
    Connection conn;
    //Pracownicy
    int id_Firmy = 0;
    //Tranzakcje
    Savepoint savepoint;
    //sortowanie
    @FXML
    public TextField filterFieldWorker, filterFieldWyd, filterFieldProj;
    //Wyszkukiwarka
    @FXML
    public TextField searchWorker, searchProj, searchWyd;
    @FXML
    public Button status;
    public dbConnection dc;
    public boolean dng = false;
    @FXML
    public TextField ID_Prac, name, lname, email, Stan, pesel;
    @FXML
    public DatePicker dobp;
    @FXML
    public Button addp, loadp, clearp;
    @FXML
    public TableView<WorkerData> workertable;
    @FXML
    public TableColumn<WorkerData, String> idcolumn, namecolumn, lnamecolumn, emailcolumn, stancolumn, peselcolumn, dobcolumn;
    public ObservableList<WorkerData> datap;
    public boolean sp = false;
    @FXML
    public Button commit, rollback, setPoint;
    //Projekty
    @FXML
    public TextField nazwap;
    @FXML
    public DatePicker dobproj;
    @FXML
    public Button loadproj, addproj, clearproj;
    @FXML
    public TableView<EventsData> ProjektTable;
    @FXML
    public TableColumn<EventsData, String> idprojcolumn, nameprojcolumn, dobprojcolumn;
    public ObservableList<EventsData> dataproj;
    //Wydarzenie
    @FXML
    public TextField nazwaw;
    @FXML
    public DatePicker dobw;
    @FXML
    public Button loadw, addw, clearw;
    @FXML
    public TableView<EventsData> WydTable;
    @FXML
    public TableColumn<EventsData, String> idwcolumn, namewcolumn, dobwcolumn;
    public ObservableList<EventsData> dataw;


    //sesja
    @FXML
   public void disconnecting(ActionEvent a) {
        if (dng) {
            try {
                conn = dbConnection.getConnection();
                conn.setAutoCommit(false);
            } catch (SQLException e) {
                System.err.println("ERROR" + e);
            }
            status.setText("Polaczono");
            dng = false;
        } else {
            try {
                conn.close();
            } catch (SQLException e) {
                System.err.println("ERROR" + e);
            }
            status.setText("rozłączone");
            dng = true;

        }
    }

    //metody tranzakcji
    @FXML
    public void commit(ActionEvent a) {
        try {
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        sp = false;
    }

    @FXML
    public void setPoint(ActionEvent a) {
        try {
            savepoint = conn.setSavepoint();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        sp = true;
    }

    @FXML
   public void rollback(ActionEvent a) {
        if (sp) {
            try {
                conn.rollback(savepoint);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }







}
