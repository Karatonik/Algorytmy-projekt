package Controller.Firm;

import Main.MainClass;
import Model.Firm.EventDataForFirmController;
import Model.Firm.WorkerDataForFirmController;
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
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static Controller.Login.LoginController.namef;

public class FirmController implements Initializable {

    public static List<WorkerDataForFirmController> filteredWorkerList = new ArrayList<>();
    public static List<EventDataForFirmController> filteredProjList, filteredWydList = new ArrayList<>();
    private final String sqlp = "SELECT * FROM Pracownik";
    private final String sqlproj = "SELECT * FROM Event WHERE PW='P'";
    private final String sqlw = "SELECT * FROM Event WHERE PW='W'";
    //sortowanie
    @FXML
    public TextField filterFieldWorker, filterFieldWyd, filterFieldProj;
    //Wyszkukiwarka
    @FXML
    public TextField searchWorker, searchProj, searchWyd;
    @FXML
    public Button Logout;
    @FXML
    public Button status;
    private dbConnection dc;
    private boolean dng = false;
    @FXML
    public TextField ID_Prac, name, lname, email, Stan, pesel;
    @FXML
    public DatePicker dobp;
    @FXML
    public Button addp, loadp, clearp;
    @FXML
    public TableView<WorkerDataForFirmController> workertable;
    @FXML
    public TableColumn<WorkerDataForFirmController, String> idcolumn, namecolumn, lnamecolumn, emailcolumn, stancolumn, peselcolumn, dobcolumn;
    private ObservableList<WorkerDataForFirmController> datap;
    private boolean sp = false;
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
    public TableView<EventDataForFirmController> ProjektTable;
    @FXML
    public TableColumn<EventDataForFirmController, String> idprojcolumn, nameprojcolumn, dobprojcolumn;
    private ObservableList<EventDataForFirmController> dataproj;
    //Wydarzenie
    @FXML
    public TextField nazwaw;
    @FXML
    public DatePicker dobw;
    @FXML
    public Button loadw, addw, clearw;
    @FXML
    public TableView<EventDataForFirmController> WydTable;
    @FXML
    public TableColumn<EventDataForFirmController, String> idwcolumn, namewcolumn, dobwcolumn;
    private ObservableList<EventDataForFirmController> dataw;
    //połączenie
    private Connection conn;
    //Pracownicy
    private int id_Firmy = 0;
    //Tranzakcje
    private Savepoint savepoint;
    //konstruktor
    public FirmController() {
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

    private void filtrWyd() {
        FilteredList<EventDataForFirmController> filteredData = new FilteredList<>(dataw, p -> true);
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
        SortedList<EventDataForFirmController> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(WydTable.comparatorProperty());
        WydTable.setItems(sortedData);

    }

    private void filtrProj() {
        FilteredList<EventDataForFirmController> filteredData = new FilteredList<>(dataproj, p -> true);
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
        SortedList<EventDataForFirmController> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(ProjektTable.comparatorProperty());
        ProjektTable.setItems(sortedData);
    }

    private void filtrWorker() {
        FilteredList<WorkerDataForFirmController> filteredData = new FilteredList<>(datap, p -> true);
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
                } else return person.getEmail().toLowerCase().contains(lowerCaseFilter);
            });
        });
        SortedList<WorkerDataForFirmController> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(workertable.comparatorProperty());
        workertable.setItems(sortedData);
    }

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

    @FXML
    public void loginout(ActionEvent event) throws Exception {
        Stage stage = (Stage) this.Logout.getScene().getWindow();
        stage.close();
        Stage stage1 = new Stage();
        MainClass mainClass = new MainClass();
        mainClass.start(stage1);

    }

    //czyszczenie pół tekstowych dla taeli wydarzenia
    @FXML
    public void clearWydFild(ActionEvent event) {
        this.nazwaw.setText("");
        this.dobw.setValue(null);
    }

    //czyści pola tekstowe dla tabeli projekt
    @FXML
    public void clearProjFild(ActionEvent event) {
        this.nazwap.setText("");
        this.dobproj.setValue(null);
    }

    //czyści pola tekstowa dla tabeli pravownik
    @FXML
    public void clearWorkerFild(ActionEvent event) {
        this.name.setText("");
        this.lname.setText("");
        this.email.setText("");
        this.Stan.setText("");
        this.pesel.setText("");
        this.dobp.setValue(null);
    }

    //odczyt do tabeli Wydarzenia
    @FXML
    public void loadWydData() {
        try {
            this.dataw = FXCollections.observableArrayList();
            ResultSet rs = conn.createStatement().executeQuery(sqlw);
            while (rs.next()) {
                this.dataw.add(new EventDataForFirmController(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
            }
        } catch (SQLException e) {
            System.err.println("ERROR" + e);
        }
        this.WydTable.setEditable(true);
        this.WydTable.getSelectionModel().setCellSelectionEnabled(true);

        this.namewcolumn.setCellFactory(TextFieldTableCell.forTableColumn());
        this.dobwcolumn.setCellFactory(TextFieldTableCell.forTableColumn());


        this.idwcolumn.setCellValueFactory(new PropertyValueFactory("ID_Event"));
        this.namewcolumn.setCellValueFactory(new PropertyValueFactory("name_Event"));
        this.dobwcolumn.setCellValueFactory(new PropertyValueFactory("Date"));

        namewcolumn.setOnEditCommit(event -> {
            EventDataForFirmController uname = event.getRowValue();
            uname.setName_Event(event.getNewValue());
            update("name_Event", event.getNewValue(), uname.getID_Event(), "Event", "ID_Event", conn);
        });
        dobwcolumn.setOnEditCommit(event -> {
            EventDataForFirmController udob = event.getRowValue();
            udob.setDate(event.getNewValue());
            update("Date", event.getNewValue(), udob.getID_Event(), "Event", "ID_Event", conn);
        });

        this.WydTable.setItems(null);
        this.WydTable.setItems(this.dataw);
    }

    //odczyt dla tabeli projekt
    @FXML
    public void loadProjData() {
        try {
            this.dataproj = FXCollections.observableArrayList();
            ResultSet rs = conn.createStatement().executeQuery(sqlproj);
            while (rs.next()) {
                this.dataproj.add(new EventDataForFirmController(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
            }
        } catch (SQLException e) {
            System.err.println("ERROR" + e);
        }
        this.ProjektTable.setEditable(true);
        this.ProjektTable.getSelectionModel().setCellSelectionEnabled(true);

        this.nameprojcolumn.setCellFactory(TextFieldTableCell.forTableColumn());
        this.dobprojcolumn.setCellFactory(TextFieldTableCell.forTableColumn());

        this.idprojcolumn.setCellValueFactory(new PropertyValueFactory("ID_Event"));
        this.nameprojcolumn.setCellValueFactory(new PropertyValueFactory("name_Event"));
        this.dobprojcolumn.setCellValueFactory(new PropertyValueFactory("Date"));

        nameprojcolumn.setOnEditCommit(event -> {
            EventDataForFirmController uname = event.getRowValue();
            uname.setName_Event(event.getNewValue());
            update("name_Event", event.getNewValue(), uname.getID_Event(), "Event", "ID_Event", conn);
        });
        dobprojcolumn.setOnEditCommit(event -> {
            EventDataForFirmController udob = event.getRowValue();
            udob.setDate(event.getNewValue());
            update("Date", event.getNewValue(), udob.getID_Event(), "Event", "ID_Event", conn);
        });

        this.ProjektTable.setItems(null);
        this.ProjektTable.setItems(this.dataproj);
    }

    //odczyt dla tabeli Pracownik
    @FXML
    public void loadWorkerData() {
        try {
            this.datap = FXCollections.observableArrayList();
            ResultSet rs = conn.createStatement().executeQuery(sqlp);
            while (rs.next()) {
                this.datap.add(new WorkerDataForFirmController(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8)));
            }
        } catch (SQLException e) {
            System.err.println("ERROR" + e);
        }
        this.workertable.setEditable(true);
        this.workertable.getSelectionModel().setCellSelectionEnabled(true);

        this.idcolumn.setCellFactory(TextFieldTableCell.forTableColumn());
        this.namecolumn.setCellFactory(TextFieldTableCell.forTableColumn());
        this.lnamecolumn.setCellFactory(TextFieldTableCell.forTableColumn());
        this.emailcolumn.setCellFactory(TextFieldTableCell.forTableColumn());
        this.dobcolumn.setCellFactory(TextFieldTableCell.forTableColumn());
        this.stancolumn.setCellFactory(TextFieldTableCell.forTableColumn());
        this.peselcolumn.setCellFactory(TextFieldTableCell.forTableColumn());


        this.idcolumn.setCellValueFactory(new PropertyValueFactory("ID_Pracownika"));
        this.namecolumn.setCellValueFactory(new PropertyValueFactory("firstName"));
        this.lnamecolumn.setCellValueFactory(new PropertyValueFactory("lastName"));
        this.emailcolumn.setCellValueFactory(new PropertyValueFactory("email"));
        this.dobcolumn.setCellValueFactory(new PropertyValueFactory("DOB"));
        this.stancolumn.setCellValueFactory(new PropertyValueFactory("Stanowisko"));
        this.peselcolumn.setCellValueFactory(new PropertyValueFactory("Pesel"));

        namecolumn.setOnEditCommit(event -> {
            WorkerDataForFirmController uname = event.getRowValue();
            uname.setFirstName(event.getNewValue());
            update("fname", event.getNewValue(), uname.getID_Pracownika(), "Pracownik", "ID_Pracownika", conn);
        });
        lnamecolumn.setOnEditCommit(event -> {
            WorkerDataForFirmController ulname = event.getRowValue();
            ulname.setLastName(event.getNewValue());
            update("lname", event.getNewValue(), ulname.getID_Pracownika(), "Pracownik", "ID_Pracownika", conn);
        });
        emailcolumn.setOnEditCommit(event -> {
            WorkerDataForFirmController uemail = event.getRowValue();
            uemail.setEmail(event.getNewValue());
            update("email", event.getNewValue(), uemail.getID_Pracownika(), "Pracownik", "ID_Pracownika", conn);
        });
        dobcolumn.setOnEditCommit(event -> {
            WorkerDataForFirmController udob = event.getRowValue();
            udob.setDOB(event.getNewValue());
            update("DOB", event.getNewValue(), udob.getID_Pracownika(), "Pracownik", "ID_Pracownika", conn);
        });
        stancolumn.setOnEditCommit(event -> {
            WorkerDataForFirmController ustan = event.getRowValue();
            ustan.setDOB(event.getNewValue());
            update("Stanowisko", event.getNewValue(), ustan.getID_Pracownika(), "Pracownik", "ID_Pracownika", conn);
        });
        peselcolumn.setOnEditCommit(event -> {
            WorkerDataForFirmController upesel = event.getRowValue();
            upesel.setDOB(event.getNewValue());
            update("Pesel", event.getNewValue(), upesel.getID_Pracownika(), "Pracownik", "ID_Pracownika", conn);
        });


        this.workertable.setItems(null);
        this.workertable.setItems(this.datap);
    }


    //zapis do tabeli wydarzenia
    @FXML
    private void addWyd(ActionEvent event) {

        String sqlInsert = "INSERT INTO Event(name_Event,Date,ID_Firmy,PW) VALUES (?,?,?,?) ";
        try {
            PreparedStatement ps = conn.prepareStatement(sqlInsert);
            ps.setString(1, this.nazwaw.getText());
            ps.setString(2, this.dobw.getEditor().getText());
            ps.setInt(3, id_Firmy);
            ps.setString(4, "W");
            ps.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    //zapis dla tabeli projekt
    @FXML
    private void addProj(ActionEvent event) {

        String sqlInsert = "INSERT INTO Event(name_Event,Date,ID_Firmy,PW) VALUES (?,?,?,?) ";
        try {
            PreparedStatement ps = conn.prepareStatement(sqlInsert);
            ps.setString(1, this.nazwap.getText());
            ps.setString(2, this.dobproj.getEditor().getText());
            ps.setInt(3, id_Firmy);
            ps.setString(4, "P");
            ps.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    //odczyt dla tabeli pracownik
    @FXML
    private void addWorkers(ActionEvent event) {

        String sqlInsert = "INSERT INTO Pracownik(fname,lname,email,DOB,ID_Firmy,Stanowisko,Pesel) VALUES (?,?,?,?,?,?,?) ";
        try {
            PreparedStatement ps = conn.prepareStatement(sqlInsert);
            ps.setString(1, this.name.getText());
            ps.setString(2, this.lname.getText());
            ps.setString(3, this.email.getText());
            ps.setString(4, this.dobp.getEditor().getText());
            ps.setInt(5, id_Firmy);
            ps.setString(6, this.Stan.getText());
            ps.setString(7, this.pesel.getText());
            ps.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //zapis do bazy danych edytowanych komórek
    private void update(String column, String newValue, String id, String nameTable, String whereID, Connection conn) {
        try (
                PreparedStatement stmt = conn.prepareStatement("UPDATE " + nameTable + " SET " + column + " = ? WHERE " + whereID + "= ? ")
        ) {

            stmt.setString(1, newValue);
            stmt.setString(2, id);
            stmt.execute();
        } catch (SQLException ex) {
            System.err.println("Error");
            // if anything goes wrong, you will need the stack trace:
            ex.printStackTrace(System.err);
        }


    }


}
