package Controller.Worker;

import Main.MainClass;
import Model.Worker.EventDataForWorkerController;
import Model.Worker.WorkersDataForWorkerController;
import Util.dbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static Controller.Login.LoginController.namef;

public class WorkersController implements Initializable {

    //wyszukiwarka
    private final String sql = "SELECT * FROM Pracownik WHERE  fname='" + namef + "';";
    private final String sqlw = "SELECT * FROM Event WHERE PW='W'";
    private final String sqlp = "SELECT * FROM Event WHERE PW='P'";
    //połączenie
    @FXML
    public Button Logout;
    //srotowanie
    @FXML
    public TextField filterFieldWyd, filterFieldProj;
    @FXML
    public TextField searchProj, searchWyd;
    private dbConnection dc;
    @FXML
    public Button reboot;
    //dane
    @FXML
    public Label id, flname, email, dob, stan, pesel;
    private ObservableList<WorkersDataForWorkerController> data;
    //wydarzenia/projekty
    @FXML
    public TableColumn<EventDataForWorkerController, String> nameproj, namewyd, dateproj, datewyd, statusproj;
    @FXML
    public TableView<EventDataForWorkerController> workerProjtable, workerWydtable;
    private ObservableList<EventDataForWorkerController> datap, dataw;
    private Connection conn;

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
                } else return person.getID_Event().toLowerCase().contains(lowerCaseFilter);
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
                } else return person.getID_Event().toLowerCase().contains(lowerCaseFilter);
            });
        });
        SortedList<EventDataForWorkerController> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(workerProjtable.comparatorProperty());
        workerProjtable.setItems(sortedData);
    }

    //wczytanie danych z wydarzeń i projektów
    @FXML
    public void reboot() {
        try {
            conn = dbConnection.getConnection();
            this.datap = FXCollections.observableArrayList();
            ResultSet rs = conn.createStatement().executeQuery(sqlp);
            while (rs.next()) {
                this.datap.add(new EventDataForWorkerController(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6)));
            }
        } catch (SQLException e) {
            System.err.println("ERROR" + e);
        }
        this.nameproj.setCellValueFactory(new PropertyValueFactory("name_Event"));
        this.dateproj.setCellValueFactory(new PropertyValueFactory("Date"));
        this.statusproj.setCellValueFactory(new PropertyValueFactory("Status"));
        this.workerProjtable.setItems(null);
        this.workerProjtable.setItems(this.datap);

        try {
            this.dataw = FXCollections.observableArrayList();
            ResultSet rs1 = conn.createStatement().executeQuery(sqlw);
            while (rs1.next()) {
                this.dataw.add(new EventDataForWorkerController(rs1.getString(1), rs1.getString(2), rs1.getString(3), rs1.getString(4), rs1.getString(5), rs1.getString(6)));
            }
        } catch (SQLException e) {
            System.err.println("ERROR" + e);
        }
        this.namewyd.setCellValueFactory(new PropertyValueFactory("name_Event"));
        this.datewyd.setCellValueFactory(new PropertyValueFactory("Date"));
        this.workerWydtable.setItems(null);
        this.workerWydtable.setItems(this.dataw);
        try {
            this.data = FXCollections.observableArrayList();
            ResultSet rs3 = conn.createStatement().executeQuery(sql);
            while (rs3.next()) {
                this.data.add(new WorkersDataForWorkerController(rs3.getString(1), rs3.getString(2), rs3.getString(3), rs3.getString(4), rs3.getString(5), rs3.getString(6), rs3.getString(7), rs3.getString(8)));
                this.id.setText("ID " + rs3.getString(1));
                this.flname.setText("Imie i nazwisko " + rs3.getString(2) + " " + rs3.getString(3));
                this.email.setText("email " + rs3.getString(4));
                this.dob.setText("data " + rs3.getString(5));
                this.stan.setText("Stanowisko " + rs3.getString(6));
                this.pesel.setText("Pesel " + rs3.getString(7));
            }
        } catch (SQLException e) {
            System.err.println("ERROR" + e);
        }
    }

    @FXML
    public void loginout() throws Exception {
        Stage stage = (Stage) this.Logout.getScene().getWindow();
        stage.close();
        Stage stage1 = new Stage();
        MainClass mainClass = new MainClass();
        mainClass.start(stage1);

    }

}




