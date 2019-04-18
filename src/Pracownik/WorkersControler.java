package Pracownik;

import dbUtil.dbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import static Loginapp.LoginControler.namef;

public class WorkersControler implements Initializable {
    //wyszukiwarka
    public static List<EventsDataWorker> filteredProjList, filteredWydList = new ArrayList<>();
    private final String sql = "SELECT * FROM Pracownik WHERE  fname='" + namef + "';";
    private final String sqlw = "SELECT * FROM Event WHERE PW='W'";
    private final String sqlp = "SELECT * FROM Event WHERE PW='P'";
    //połączenie
    Connection conn;
    //srotowanie
    @FXML
    private TextField filterFieldWyd, filterFieldProj;
    @FXML
    private TextField searchProj, searchWyd;
    private dbConnection dc;
    @FXML
    private Button reboot;
    //dane
    @FXML
    private Label id, flname, email, dob, stan, pesel;
    private ObservableList<WorkersData> data;
    //wydarzenia/projekty
    @FXML
    private TableColumn<EventsDataWorker, String> nameproj, namewyd, dateproj, datewyd, statusproj;
    @FXML
    private TableView<EventsDataWorker> workerProjtable, workerWydtable;
    private ObservableList<EventsDataWorker> datap, dataw;

    public void initialize(URL url, ResourceBundle rb) {
        reboot();
        this.dc = new dbConnection();
        filtrProj();
        filtrWyd();
    }

    //wczytanie danych z wydarzeń i projektów
    @FXML
    private void reboot() {
        try {
            conn = dbConnection.getConnection();
            this.datap = FXCollections.observableArrayList();
            ResultSet rs = conn.createStatement().executeQuery(sqlp);
            while (rs.next()) {
                this.datap.add(new EventsDataWorker(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6)));
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
                this.dataw.add(new EventsDataWorker(rs1.getString(1), rs1.getString(2), rs1.getString(3), rs1.getString(4), rs1.getString(5), rs1.getString(6)));
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
                this.data.add(new WorkersData(rs3.getString(1), rs3.getString(2), rs3.getString(3), rs3.getString(4), rs3.getString(5), rs3.getString(6), rs3.getString(7), rs3.getString(8)));
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
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (person.getName_Event().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches first name.
                } else if (person.getDate().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                } else if (person.getID_Event().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                }
                return false; // Does not match.
            });
        });
        SortedList<EventsDataWorker> sortedData = new SortedList<>(filteredData);
        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(workerWydtable.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        workerWydtable.setItems(sortedData);

    }

    public void filtrProj() {
        FilteredList<EventsDataWorker> filteredData = new FilteredList<>(datap, p -> true);
        filterFieldProj.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(person -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (person.getName_Event().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches first name.
                } else if (person.getDate().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                } else if (person.getID_Event().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                }
                return false; // Does not match.
            });
        });
        SortedList<EventsDataWorker> sortedData = new SortedList<>(filteredData);
        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(workerProjtable.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        workerProjtable.setItems(sortedData);
    }

}




