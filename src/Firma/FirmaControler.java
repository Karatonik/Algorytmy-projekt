package Firma;

import dbUtil.dbConnection;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

import static Loginapp.LoginControler.namef;

public class FirmaControler implements Initializable {
    //połączenie
    Connection conn;
    @FXML
    private Button status;
    private dbConnection dc;
    private boolean dng = false;
    //Pracownicy
    int id_Firmy = 0;
    @FXML
    private TextField ID_Prac, name, lname, email, Stan, pesel;
    @FXML
    private DatePicker dobp;
    @FXML
    private Button addp, loadp, clearp;
    @FXML
    private TableView<WorkerData> workertable;
    @FXML
    private TableColumn<WorkerData, String> idcolumn, namecolumn, lnamecolumn, emailcolumn, stancolumn, peselcolumn, dobcolumn;

    private ObservableList<WorkerData> datap;
    private final String sqlp = "SELECT * FROM Pracownik";
    //Tranzakcje
    Savepoint savepoint;
    private boolean sp = false;
    @FXML
    private Button commit, rollback, setPoint;
    //Projekty
    @FXML
    private TextField nazwap;
    @FXML
    private DatePicker dobproj;
    @FXML
    private Button loadproj, addproj, clearproj;
    @FXML
    private TableView<EventsData> ProjektTable;
    @FXML
    private TableColumn<EventsData, String> idprojcolumn, nameprojcolumn, dobprojcolumn;

    private ObservableList<EventsData> dataproj;
    private final String sqlproj = "SELECT * FROM Event WHERE PW='P'";
    //Wydarzenie
    @FXML
    private TextField nazwaw;
    @FXML
    private DatePicker dobw;
    @FXML
    private Button loadw, addw, clearw;
    @FXML
    private TableView<EventsData> WydTable;
    @FXML
    private TableColumn<EventsData, String> idwcolumn, namewcolumn, dobwcolumn;

    private ObservableList<EventsData> dataw;
    private final String sqlw = "SELECT * FROM Event WHERE PW='W'";

    //metoda interfejsu
    public void initialize(URL url, ResourceBundle rb) {
        this.dc = new dbConnection();
        loadWydData();
        loadProjData();
        loadWorkerData();
    }

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

    //sesja
    @FXML
    private void disconnecting(ActionEvent a) {
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
    private void commit(ActionEvent a) {
        try {
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        sp = false;
    }

    @FXML
    private void setPoint(ActionEvent a) {
        try {
            savepoint = conn.setSavepoint();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        sp = true;
    }

    @FXML
    private void rollback(ActionEvent a) {
        if (sp) {
            try {
                conn.rollback(savepoint);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    //zapis do bazy danych edytowanych komórek
    public void updata(String column, String newValue, String id, String nameTable, String whereID) {
        try (
                PreparedStatement stmt = conn.prepareStatement("UPDATE " + nameTable + " SET " + column + " = ? WHERE " + whereID + "= ? ");
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

    //odczyt do tabeli Wydarzenia
    @FXML
    private void loadWydData() {
        try {
            this.dataw = FXCollections.observableArrayList();
            ResultSet rs = conn.createStatement().executeQuery(sqlw);
            while (rs.next()) {
                this.dataw.add(new EventsData(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
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
            EventsData uname = event.getRowValue();
            uname.setName_Event(event.getNewValue());
            updata("name_Event", event.getNewValue(), uname.getID_Event(), "Event", "ID_Event");
        });
        dobwcolumn.setOnEditCommit(event -> {
            EventsData udob = event.getRowValue();
            udob.setDate(event.getNewValue());
            updata("Date", event.getNewValue(), udob.getID_Event(), "Event", "ID_Event");
        });

        this.WydTable.setItems(null);
        this.WydTable.setItems(this.dataw);
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

    //czyszczenie pół tekstowych dla taeli wydarzenia
    @FXML
    private void clearWydFild(ActionEvent event) {
        this.nazwaw.setText("");
        this.dobw.setValue(null);
    }

    //odczyt dla tabeli projekt
    @FXML
    private void loadProjData() {
        try {
            this.dataproj = FXCollections.observableArrayList();
            ResultSet rs = conn.createStatement().executeQuery(sqlproj);
            while (rs.next()) {
                this.dataproj.add(new EventsData(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
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
            EventsData uname = event.getRowValue();
            uname.setName_Event(event.getNewValue());
            updata("name_Event", event.getNewValue(), uname.getID_Event(), "Event", "ID_Event");
        });
        dobprojcolumn.setOnEditCommit(event -> {
            EventsData udob = event.getRowValue();
            udob.setDate(event.getNewValue());
            updata("Date", event.getNewValue(), udob.getID_Event(), "Event", "ID_Event");
        });

        this.ProjektTable.setItems(null);
        this.ProjektTable.setItems(this.dataproj);
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

    //czyści pola tekstowe dla tabeli projekt
    @FXML
    private void clearProjFild(ActionEvent event) {
        this.nazwap.setText("");
        this.dobproj.setValue(null);
    }

    //odczyt dla tabeli Pracownik
    @FXML
    private void loadWorkerData() {
        try {
            this.datap = FXCollections.observableArrayList();
            ResultSet rs = conn.createStatement().executeQuery(sqlp);
            while (rs.next()) {
                this.datap.add(new WorkerData(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8)));
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
            WorkerData uname = event.getRowValue();
            uname.setFirstName(event.getNewValue());
            updata("fname", event.getNewValue(), uname.getID_Pracownika(), "Pracownik", "ID_Pracownika");
        });
        lnamecolumn.setOnEditCommit(event -> {
            WorkerData ulname = event.getRowValue();
            ulname.setLastName(event.getNewValue());
            updata("lname", event.getNewValue(), ulname.getID_Pracownika(), "Pracownik", "ID_Pracownika");
        });
        emailcolumn.setOnEditCommit(event -> {
            WorkerData uemail = event.getRowValue();
            uemail.setEmail(event.getNewValue());
            updata("email", event.getNewValue(), uemail.getID_Pracownika(), "Pracownik", "ID_Pracownika");
        });
        dobcolumn.setOnEditCommit(event -> {
            WorkerData udob = event.getRowValue();
            udob.setDOB(event.getNewValue());
            updata("DOB", event.getNewValue(), udob.getID_Pracownika(), "Pracownik", "ID_Pracownika");
        });
        stancolumn.setOnEditCommit(event -> {
            WorkerData ustan = event.getRowValue();
            ustan.setDOB(event.getNewValue());
            updata("Stanowisko", event.getNewValue(), ustan.getID_Pracownika(), "Pracownik", "ID_Pracownika");
        });
        peselcolumn.setOnEditCommit(event -> {
            WorkerData upesel = event.getRowValue();
            upesel.setDOB(event.getNewValue());
            updata("Pesel", event.getNewValue(), upesel.getID_Pracownika(), "Pracownik", "ID_Pracownika");
        });


        this.workertable.setItems(null);
        this.workertable.setItems(this.datap);
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

    //czyści pola tekstowa dla tabeli pravownik
    @FXML
    private void clearWorkerFild(ActionEvent event) {
        this.name.setText("");
        this.lname.setText("");
        this.email.setText("");
        this.Stan.setText("");
        this.pesel.setText("");
        this.dobp.setValue(null);
    }

}
