package Admin;

import Loginapp.option;
import dbUtil.dbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

public class AdminControler implements Initializable {
    public static List<PracownikData> filteredWorkerList = new ArrayList<>();
    public static List<FirmyData> filteredFirmaList = new ArrayList<>();
    public static List<LoginData> filteredLoginList = new ArrayList<>();
    public static List<EventData> filteredEventList = new ArrayList<>();
    private final String sql = "SELECT * FROM Pracownik";
    private final String sqlev = "SELECT * FROM Event";
    private final String sqlog = "SELECT * FROM Login";
    private final String sqlfirma = "SELECT * FROM Firma";
    //połączenie
    Connection conn;
    //tranzakcje
    Savepoint savepoint;
    //zmienne
    //Wyszkukiwarka
    @FXML
    private TextField searchW, searchF, searchL, searchE;
    private boolean dng = false;
    @FXML
    private Button dconnect;
    //dbConnction
    private dbConnection dc;
    //Pracownik
    @FXML
    private TextField id, fname, lname, email, idf, testl;
    @FXML
    private DatePicker dob;
    @FXML
    private TableView<PracownikData> workertable;
    @FXML
    private TableColumn<PracownikData, String> idcolumn, fnamecolumn, lnamecolumn, emailcolumn, idfcolumn;
    @FXML
    private Button add, clear, load;
    @FXML
    private TableColumn<PracownikData, String> dobcolumn;
    private ObservableList<PracownikData> data;
    //Events
    @FXML
    private TextField idevent, nameevent;
    @FXML
    private Button addevent, clearevent, loadevent;
    @FXML
    private DatePicker devent;
    @FXML
    private TableView<EventData> eventtable;
    @FXML
    private TableColumn<EventData, String> ideventcolumn, nameeventcolumn, deventcolumn;
    private ObservableList<EventData> dataev;
    //LOGIN
    @FXML
    private TextField nameUser, passUser;
    @FXML
    private Button divUser, addUser, clearUser, loadUser;
    @FXML
    private ComboBox<option> combodiv;
    @FXML
    private TableView<LoginData> loginTable;
    @FXML
    private TableColumn<LoginData, String> userUsercolumn, passUsercolumn, divUsercolumn;
    private ObservableList<LoginData> datalog;
    private boolean sp = false;
    @FXML
    private Button ok, no, start, savePoint;
    //Firmy
    @FXML
    private TextField idfirma, namefirma;
    @FXML
    private Button addfirma, clearfirma, loadfirma;
    @FXML
    private TableView<FirmyData> firmatable;
    @FXML
    private TableColumn<FirmyData, String> idfirmacolumn, namefirmacolumn;
    @FXML
    private ObservableList<FirmyData> dataf;

    //konstruktor
    public AdminControler() {
        try {
            conn = dbConnection.getConnection();
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            System.err.println("ERROR" + e);

        }


    }

    //metody
    //interfejs
    public void initialize(URL url, ResourceBundle rb) {
        this.dc = new dbConnection();
        this.combodiv.setItems(FXCollections.observableArrayList(option.values()));
        loadWorkerData();
        loadEventData();
        loadLoginData();
        loadFirmaData();
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
            dconnect.setText("Polaczono");
            dng = false;
        } else {
            try {
                conn.close();
            } catch (SQLException e) {
                System.err.println("ERROR" + e);
            }
            dconnect.setText("rozłączone");
            dng = true;

        }
    }

    //kontrola tranzakcji
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

    //metoda aktualizująca baze dany po komórkach
    public void updata(String column, String newValue, String id, String nameTable, String whereID) {
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
    @FXML
    private void loadWorkerData() {
        try {
            this.data = FXCollections.observableArrayList();
            ResultSet rs = conn.createStatement().executeQuery(sql);
            while (rs.next()) {
                this.data.add(new PracownikData(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6)));
            }
        } catch (SQLException e) {
            System.err.println("ERROR" + e);
        }
        this.workertable.setEditable(true);
        this.workertable.getSelectionModel().setCellSelectionEnabled(true);

        this.fnamecolumn.setCellFactory(TextFieldTableCell.forTableColumn());
        this.lnamecolumn.setCellFactory(TextFieldTableCell.forTableColumn());
        this.emailcolumn.setCellFactory(TextFieldTableCell.forTableColumn());
        this.dobcolumn.setCellFactory(TextFieldTableCell.forTableColumn());
        this.idfcolumn.setCellFactory(TextFieldTableCell.forTableColumn());

        this.idcolumn.setCellValueFactory(new PropertyValueFactory<>("ID"));
        this.fnamecolumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        this.lnamecolumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        this.emailcolumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        this.dobcolumn.setCellValueFactory(new PropertyValueFactory<>("DOB"));
        this.idfcolumn.setCellValueFactory(new PropertyValueFactory<>("ID_Firmy"));

        fnamecolumn.setOnEditCommit(event -> {
            PracownikData ufname = event.getRowValue();
            ufname.setFirstName(event.getNewValue());
            updata("fname", event.getNewValue(), ufname.getID(), "Pracownik", "ID_Pracownika");
        });
        lnamecolumn.setOnEditCommit(event -> {
            PracownikData ulname = event.getRowValue();
            ulname.setLastName(event.getNewValue());
            updata("lname", event.getNewValue(), ulname.getID(), "Pracownik", "ID_Pracownika");
        });
        emailcolumn.setOnEditCommit(event -> {
            PracownikData uemail = event.getRowValue();
            uemail.setEmail(event.getNewValue());
            updata("email", event.getNewValue(), uemail.getID(), "Pracownik", "ID_Pracownika");
        });
        dobcolumn.setOnEditCommit(event -> {
            PracownikData udob = event.getRowValue();
            udob.setDOB(event.getNewValue());
            updata("DOB", event.getNewValue(), udob.getID(), "Pracownik", "ID_Pracownika");
        });

        workertable.setItems(null);
        workertable.setItems(data);
    }

    //zapis dla tabeli pracownik
    @FXML
    private void addWorker(ActionEvent event) {
        String sqlInsert = "INSERT INTO Pracownik(fname,lname,email,DOB,ID_Firmy) VALUES (?,?,?,?,?) ";
        try {
            PreparedStatement ps = conn.prepareStatement(sqlInsert);
            ps.setString(1, this.fname.getText());
            ps.setString(2, this.lname.getText());
            ps.setString(3, this.email.getText());
            ps.setString(4, this.dob.getEditor().getText());
            ps.setString(5, this.idf.getText());
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    //czyszczenie pól tekstowych
    @FXML
    private void clearWorkerFild(ActionEvent event) {
        this.id.setText("");
        this.fname.setText("");
        this.lname.setText("");
        this.email.setText("");
        this.idf.setText("");
        this.dob.setValue(null);
    }

    //odczyt dla tabeli eventy
    @FXML
    private void loadEventData() {
        try {
            this.dataev = FXCollections.observableArrayList();
            ResultSet rs = conn.createStatement().executeQuery(sqlev);
            while (rs.next()) {
                this.dataev.add(new EventData(rs.getString(1), rs.getString(2), rs.getString(3)));
            }
        } catch (SQLException e) {
            System.err.println("ERROR" + e);
        }
        this.eventtable.setEditable(true);
        this.eventtable.getSelectionModel().setCellSelectionEnabled(true);

        this.nameeventcolumn.setCellFactory(TextFieldTableCell.forTableColumn());
        this.deventcolumn.setCellFactory(TextFieldTableCell.forTableColumn());

        this.ideventcolumn.setCellValueFactory(new PropertyValueFactory("ID_Event"));
        this.nameeventcolumn.setCellValueFactory(new PropertyValueFactory("name_Event"));
        this.deventcolumn.setCellValueFactory(new PropertyValueFactory("Date"));

        nameeventcolumn.setOnEditCommit(events -> {
            EventData uname = events.getRowValue();
            uname.setName_Event(events.getNewValue());
            updata("name_Event", events.getNewValue(), uname.getID_Event(), "Event", "ID_Event");
        });
        deventcolumn.setOnEditCommit(events -> {
            EventData udevnet = events.getRowValue();
            udevnet.setDate(events.getNewValue());
            updata("Date", events.getNewValue(), udevnet.getID_Event(), "Event", "ID_Event");
        });

        this.eventtable.setItems(null);
        this.eventtable.setItems(this.dataev);
    }

    //zapis dla tabeli eventy
    @FXML
    private void addevent(ActionEvent event) {
        String sqlInserte = "INSERT INTO Event(name_Event,Date) VALUES (?,?) ";
        try {
            PreparedStatement ps = conn.prepareStatement(sqlInserte);
            ps.setString(1, this.nameevent.getText());
            ps.setString(2, this.devent.getEditor().getText());

            ps.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //czysci pola tekstowe dla tabeli eventy
    @FXML
    private void cleareventFild(ActionEvent event) {
        this.nameevent.setText("");
        this.devent.setValue(null);
    }

    //odczyt dla tabeli Login
    @FXML
    private void loadLoginData() {
        try {
            this.datalog = FXCollections.observableArrayList();
            ResultSet rs = conn.createStatement().executeQuery(sqlog);
            while (rs.next()) {
                this.datalog.add(new LoginData(rs.getString(1), rs.getString(2), rs.getString(3)));
            }
        } catch (SQLException e) {
            System.err.println("ERROR" + e);
        }
        this.loginTable.setEditable(true);
        this.loginTable.getSelectionModel().setCellSelectionEnabled(true);

        this.passUsercolumn.setCellFactory(TextFieldTableCell.forTableColumn());
        this.divUsercolumn.setCellFactory(TextFieldTableCell.forTableColumn());

        this.userUsercolumn.setCellValueFactory(new PropertyValueFactory("username"));
        this.passUsercolumn.setCellValueFactory(new PropertyValueFactory("pass"));
        this.divUsercolumn.setCellValueFactory(new PropertyValueFactory("division"));

        passUsercolumn.setOnEditCommit(events -> {
            LoginData upass = events.getRowValue();
            upass.setPass(events.getNewValue());
            updata("pass", events.getNewValue(), upass.getUsername(), "Login", "username");
        });
        divUsercolumn.setOnEditCommit(events -> {
            LoginData udiv = events.getRowValue();
            udiv.setDivision(events.getNewValue());
            updata("pass", events.getNewValue(), udiv.getUsername(), "Login", "username");
        });

        this.loginTable.setItems(null);
        this.loginTable.setItems(this.datalog);
    }

    //zapis dla tabeli login
    @FXML
    private void addLogin(ActionEvent event) {
        String sqlInsertl = "INSERT INTO Login(username,pass,division) VALUES (?,?,?) ";
        try {
            PreparedStatement ps = conn.prepareStatement(sqlInsertl);
            ps.setString(1, this.nameUser.getText());
            ps.setString(2, this.passUser.getText());
            //ps.setString(3,this.divUser.getText());
            ps.setString(3, combodiv.getValue().toString());
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //czysci pola tekstowe dla tabeli Login
    @FXML
    private void clearLoginFild(ActionEvent event) {
        this.nameUser.setText("");
        this.passUser.setText("");
        this.divUser.setText("");

    }

    //odczyt dla tabeli Firmy
    @FXML
    private void loadFirmaData() {
        try {
            this.dataf = FXCollections.observableArrayList();
            ResultSet rs = conn.createStatement().executeQuery(sqlfirma);
            while (rs.next()) {
                this.dataf.add(new FirmyData(rs.getString(1), rs.getString(2)));
            }
        } catch (SQLException e) {
            System.err.println("ERROR" + e);
        }
        this.firmatable.setEditable(true);
        this.firmatable.getSelectionModel().setCellSelectionEnabled(true);

        this.namefirmacolumn.setCellFactory(TextFieldTableCell.forTableColumn());


        this.idfirmacolumn.setCellValueFactory(new PropertyValueFactory("ID_Firmy"));
        this.namefirmacolumn.setCellValueFactory(new PropertyValueFactory("Nazwa_Firmy"));

        namefirmacolumn.setOnEditCommit(events -> {
            FirmyData uname = events.getRowValue();
            uname.setNazwa_Firmy(events.getNewValue());
            updata("Nazwa_Firmy", events.getNewValue(), uname.getID_Firmy(), "Firma", "ID_Firmy");
        });

        this.firmatable.setItems(null);
        this.firmatable.setItems(this.dataf);
    }

    //zapis dla tabeli firmy
    @FXML
    private void addFirmy(ActionEvent event) {
        String sqlInsertf = "INSERT INTO Firma(Nazwa_Firmy) VALUES (?) ";
        try {
            PreparedStatement ps = conn.prepareStatement(sqlInsertf);
            ps.setString(1, this.namefirma.getText());
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //czyści pola tekstowe da tabeli Firmy
    @FXML
    private void clearFirmaFild(ActionEvent event) {
        this.namefirma.setText("");
    }

    @FXML
    public void SearchWorker() {
        String criteria = searchW.getText();

        try {
        } catch (Exception e) {

        }//stworzenie strumienia obiektów wyświetlanych w tabeli
        filteredWorkerList = workertable.getItems().stream().filter(person -> { //stworzenie filtra który wybierze tylko te obiekty które spełniają kryteria wyboru usera
            if (person.getID().contains(criteria)) {
                return true;
            } else if (person.firstNameProperty().getValue().contains(criteria)) {
                return true;
            } else if (person.lastNameProperty().getValue().contains(criteria)) {
                return true;
            } else if (person.emailProperty().getValue().contains(criteria)) {
                return true;

            } else //wyrażenie lambda zwraca true jeżeli chociaż jeden warunek został spełniony
                if (person.DOBProperty().getValue().contains(criteria)) {
                    return true;
                } else return person.ID_FirmyProperty().getValue().contains(criteria);
        }).collect(Collectors.toList());
        // stworzenie listy obiektów które pomyślnie przeszły przez filtr
        ObservableList<PracownikData> filteredPersons = FXCollections.observableArrayList(filteredWorkerList); //konwersja List na ObservableList
        workertable.setItems(filteredPersons); //wstawienie przefiltrowanej listy do tabeli
        if (searchW.getText().isEmpty()) {
            workertable.setItems(data); //jeżeli textField wyszukiwarki jest pusty to przywraca listę wszystkich obiektów z powrotem do tabeli
        }
    }

    @FXML
    public void SearchLogin() {
        String criteria = searchL.getText();

        try {
        } catch (Exception e) {

        }
        filteredLoginList = loginTable.getItems().stream().filter(person -> {
            if (person.getUsername().contains(criteria)) {
                return true;
            } else return person.getDivision().contains(criteria);
        }).collect(Collectors.toList());
        ObservableList<LoginData> filteredPersons = FXCollections.observableArrayList(filteredLoginList);
        loginTable.setItems(filteredPersons);
        if (searchL.getText().isEmpty()) {
            loginTable.setItems(datalog);
        }
    }

    @FXML
    public void SearchFirma() {
        String criteria = searchF.getText();

        try {
        } catch (Exception e) {

        }
        filteredFirmaList = firmatable.getItems().stream().filter(person -> {
            if (person.getID_Firmy().contains(criteria)) {
                return true;
            } else return person.getNazwa_Firmy().contains(criteria);
        }).collect(Collectors.toList());
        ObservableList<FirmyData> filteredPersons = FXCollections.observableArrayList(filteredFirmaList);
        firmatable.setItems(filteredPersons);
        if (searchF.getText().isEmpty()) {
            firmatable.setItems(dataf);
        }
    }

    @FXML
    public void SearchEvent() {
        String criteria = searchE.getText();

        try {
        } catch (Exception e) {

        }
        filteredEventList = eventtable.getItems().stream().filter(person -> {
            if (person.getID_Event().contains(criteria)) {
                return true;
            } else if (person.getDate().contains(criteria)) {
                return true;
            } else return person.getName_Event().contains(criteria);
        }).collect(Collectors.toList());
        ObservableList<EventData> filteredPersons = FXCollections.observableArrayList(filteredEventList);
        eventtable.setItems(filteredPersons);
        if (searchE.getText().isEmpty()) {
            eventtable.setItems(dataev);
        }
    }


}




