package Controller.Admin;

import Main.MainClass;
import Model.Admin.EventDataForAdminController;
import Model.Admin.FirmDataForAdminController;
import Model.Admin.LoginDataForAdminController;
import Model.Admin.WorkerDataForAdminController;
import Model.Option;
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
import java.util.ResourceBundle;


public class AdminController implements Initializable {
    //Wybór tabeli w bazie danych
    private final String sql = "SELECT * FROM Pracownik";
    private final String sqlev = "SELECT * FROM Event";
    private final String sqlog = "SELECT * FROM Login";
    private final String sqlfirma = "SELECT * FROM Firma";
    @FXML
    public Button Logout;
    //sortowanie przyciski
    @FXML
    public TextField filterFieldWorker, filterFieldFirma, filterFieldLogin, filterFieldEvent;
    //tworzenie obiektów oblserwowalntch list oraz tabeli tableview
    @FXML
    public TableView<WorkerDataForAdminController> workertable;
    @FXML
    Button dconnect;
    //metoda słuzaca operaca na sesji
    @FXML
    private Button ok, no, start, savePoint;
    private boolean dng = false;
    //dbConnction
    private dbConnection dc;
    //połączenie
    private Connection conn;
    //tranzakcje
    private Savepoint savepoint;
    private boolean sp = false;
    private ObservableList<WorkerDataForAdminController> data;
    @FXML
    private TableView<EventDataForAdminController> eventtable;
    private ObservableList<EventDataForAdminController> dataev;
    @FXML
    private TableView<LoginDataForAdminController> loginTable;
    private ObservableList<LoginDataForAdminController> datalog;
    @FXML
    private TableView<FirmDataForAdminController> firmatable;
    @FXML
    private ObservableList<FirmDataForAdminController> dataf;
    //Przeciązenia pól w pliku fxml
    @FXML
    private TextField id, fname, lname, email, idf, testl;
    @FXML
    private DatePicker dob;
    @FXML
    private TableColumn<WorkerDataForAdminController, String> idcolumn, fnamecolumn, lnamecolumn, emailcolumn, idfcolumn;
    @FXML
    private Button add, clear, load;
    @FXML
    private TableColumn<WorkerDataForAdminController, String> dobcolumn;
    //Events
    @FXML
    private TextField idevent, nameevent;
    @FXML
    private Button addevent, clearevent, loadevent;
    @FXML
    private DatePicker devent;
    @FXML
    private TableColumn<EventDataForAdminController, String> ideventcolumn, nameeventcolumn, deventcolumn;
    //LOGIN
    @FXML
    private TextField nameUser, passUser;
    @FXML
    private Button divUser, addUser, clearUser, loadUser;
    @FXML
    private ComboBox<Option> combodiv;
    @FXML
    private TableColumn<LoginDataForAdminController, String> userUsercolumn, passUsercolumn, divUsercolumn;
    //Firmy
    @FXML
    private TextField idfirma, namefirma;
    @FXML
    private Button addfirma, clearfirma, loadfirma;
    @FXML
    private TableColumn<FirmDataForAdminController, String> idfirmacolumn, namefirmacolumn;
    //konstruktor
    public AdminController() {
        try {
            conn = dbConnection.getConnection();
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            System.err.println("ERROR" + e);

        }


    }

    private void update(String column, String newValue, String id, String nameTable, String whereID, Connection conn) {
        try (
                PreparedStatement stmt = conn.prepareStatement("UPDATE " + nameTable + " SET " + column + " = ? WHERE " + whereID + "= ? ")
        ) {
            stmt.setString(1, newValue);
            stmt.setString(2, id);
            stmt.execute();
        } catch (SQLException ex) {
            System.err.println("Error");
            ex.printStackTrace(System.err);
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
    private void filtrWorker() { //metoda  używa technologi lambda
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

    private void filtrEvent() {
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

    private void filtrFirma() {
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

    private void filtrLogin() {
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
    public void commit(ActionEvent a) {
        try {
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        sp = false;
    }

    @FXML//ustawia zapis
    public void setPoint(ActionEvent a) {
        try {
            savepoint = conn.setSavepoint();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        sp = true;
    }

    @FXML //cofnięcie
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

    //czysci pola tekstowe dla tabeli Event
    @FXML
    private void cleareventFild(ActionEvent event) {
        this.nameevent.setText("");
        this.devent.setValue(null);
    }

    //czysci pola tekstowe dla tabeli Login
    @FXML
    private void clearLoginFild(ActionEvent event) {
        this.nameUser.setText("");
        this.passUser.setText("");
        this.divUser.setText("");

    }

    //czyści pola tekstowe da tabeli Firmy
    @FXML
    private void clearFirmaFild(ActionEvent event) {
        this.namefirma.setText("");
    }

    //czyszczenie pól tekstowych
    @FXML
    public void clearWorkerFild(ActionEvent event) {
        this.fname.setText("");
        this.lname.setText("");
        this.email.setText("");
        this.idf.setText("");
        this.dob.setValue(null);
    }

    //odczyt z bazy danych
    @FXML
    private void loadWorkerData() {
        try {
            this.data = FXCollections.observableArrayList(); //przełączenie na listę wsierająca fxmlcollections
            ResultSet rs = conn.createStatement().executeQuery(sql);
            while (rs.next()) {
                this.data.add(new WorkerDataForAdminController(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6)));
            }//przypisuje wartości z bazy danych do ramki
        } catch (SQLException e) {
            System.err.println("ERROR" + e);
        }
        this.workertable.setEditable(true);
        this.workertable.getSelectionModel().setCellSelectionEnabled(true);
//ystawia edytowanie tebeli
        this.fnamecolumn.setCellFactory(TextFieldTableCell.forTableColumn());
        this.lnamecolumn.setCellFactory(TextFieldTableCell.forTableColumn());
        this.emailcolumn.setCellFactory(TextFieldTableCell.forTableColumn());
        this.dobcolumn.setCellFactory(TextFieldTableCell.forTableColumn());
        this.idfcolumn.setCellFactory(TextFieldTableCell.forTableColumn());
//ustawia wartośc komórki tabeli
        this.idcolumn.setCellValueFactory(new PropertyValueFactory<>("ID"));
        this.fnamecolumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        this.lnamecolumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        this.emailcolumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        this.dobcolumn.setCellValueFactory(new PropertyValueFactory<>("DOB"));
        this.idfcolumn.setCellValueFactory(new PropertyValueFactory<>("ID_Firmy"));
//zapis służący dodaniu eventu do wybranych komórek tabeli
        fnamecolumn.setOnEditCommit(event -> {
            WorkerDataForAdminController ufname = event.getRowValue();
            ufname.setFirstName(event.getNewValue());
            update("fname", event.getNewValue(), ufname.getID(), "Pracownik", "ID_Pracownika", conn);
        });
        lnamecolumn.setOnEditCommit(event -> {
            WorkerDataForAdminController ulname = event.getRowValue();
            ulname.setLastName(event.getNewValue());
            update("lname", event.getNewValue(), ulname.getID(), "Pracownik", "ID_Pracownika", conn);
        });
        emailcolumn.setOnEditCommit(event -> {
            WorkerDataForAdminController uemail = event.getRowValue();
            uemail.setEmail(event.getNewValue());
            update("email", event.getNewValue(), uemail.getID(), "Pracownik", "ID_Pracownika", conn);
        });
        dobcolumn.setOnEditCommit(event -> {
            WorkerDataForAdminController udob = event.getRowValue();
            udob.setDOB(event.getNewValue());
            update("DOB", event.getNewValue(), udob.getID(), "Pracownik", "ID_Pracownika", conn);
        });

        workertable.setItems(null);
        workertable.setItems(data);//zwraca do tabeli listę data
    }

    //odczyt dla tabeli eventy
    @FXML
    private void loadEventData() {
        try {
            this.dataev = FXCollections.observableArrayList();
            ResultSet rs = conn.createStatement().executeQuery(sqlev);
            while (rs.next()) {
                this.dataev.add(new EventDataForAdminController(rs.getString(1), rs.getString(2), rs.getString(3)));
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
            EventDataForAdminController uname = events.getRowValue();
            uname.setName_Event(events.getNewValue());
            update("name_Event", events.getNewValue(), uname.getID_Event(), "Event", "ID_Event", conn);
        });
        deventcolumn.setOnEditCommit(events -> {
            EventDataForAdminController udevnet = events.getRowValue();
            udevnet.setDate(events.getNewValue());
            update("Date", events.getNewValue(), udevnet.getID_Event(), "Event", "ID_Event", conn);
        });

        this.eventtable.setItems(null);
        this.eventtable.setItems(this.dataev);
    }

    @FXML
    private void loadLoginData() {
        try {
            this.datalog = FXCollections.observableArrayList();
            ResultSet rs = conn.createStatement().executeQuery(sqlog);
            while (rs.next()) {
                this.datalog.add(new LoginDataForAdminController(rs.getString(1), rs.getString(2), rs.getString(3)));
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
            LoginDataForAdminController upass = events.getRowValue();
            upass.setPass(events.getNewValue());
            update("pass", events.getNewValue(), upass.getUsername(), "Login", "username", conn);
        });
        divUsercolumn.setOnEditCommit(events -> {
            LoginDataForAdminController udiv = events.getRowValue();
            udiv.setDivision(events.getNewValue());
            update("pass", events.getNewValue(), udiv.getUsername(), "Login", "username", conn);
        });

        this.loginTable.setItems(null);
        this.loginTable.setItems(this.datalog);
    }

    //odczyt dla tabeli Firmy
    @FXML
    private void loadFirmaData() {
        try {
            this.dataf = FXCollections.observableArrayList();
            ResultSet rs = conn.createStatement().executeQuery(sqlfirma);
            while (rs.next()) {
                this.dataf.add(new FirmDataForAdminController(rs.getString(1), rs.getString(2)));
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
            FirmDataForAdminController uname = events.getRowValue();
            uname.setNazwa_Firmy(events.getNewValue());
            update("Nazwa_Firmy", events.getNewValue(), uname.getID_Firmy(), "Firma", "ID_Firmy", conn);
        });

        this.firmatable.setItems(null);
        this.firmatable.setItems(this.dataf);
    }

    //metody zapisu rekordu
    @FXML
    private void addWorker(ActionEvent event) {
        String sqlInsert = "INSERT INTO Pracownik(fname,lname,email,DOB,ID_Firmy) VALUES (?,?,?,?,?) "; //zapytanie w języku sql
        try {
            PreparedStatement ps = conn.prepareStatement(sqlInsert); //dzięki obiektowi preparedstatment możemy w miejsca ? wstawić stringa
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

}
