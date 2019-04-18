package Admin;
import Loginapp.option;
import dbUtil.dbConnection;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import  javafx.scene.control.TableCell;
import  javafx.scene.control.TableColumn;
import  javafx.scene.control.TableColumn.CellEditEvent;
import  javafx.scene.control.TableView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;

public class AdminControler  implements Initializable {
    Connection conn;

    public void initialize(URL url, ResourceBundle rb) {
        this.dc = new dbConnection();
        this.combodiv.setItems(FXCollections.observableArrayList(option.values()));
        loadWorkerData();
        loadEventData();
        loadLoginData();
        loadFirmaData();
    }

    public AdminControler() {
        try {
            conn = dbConnection.getConnection();
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            System.err.println("ERROR" + e);

        }


    }

    @FXML
    private Button dconnect;
    private boolean dng = false;

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

    //Pracownik
    @FXML
    private Label testl;
    @FXML
    private TextField id;
    @FXML
    private TextField fname;
    @FXML
    private TextField lname;
    @FXML
    private TextField email;
    @FXML
    private TextField idf;
    @FXML
    private DatePicker dob;
    @FXML
    private TableView<PracownikData> workertable;
    @FXML
    private TableColumn<PracownikData, String> idcolumn;
    @FXML
    private TableColumn<PracownikData, String> fnamecolumn;
    @FXML
    private TableColumn<PracownikData, String> lnamecolumn;
    @FXML
    private TableColumn<PracownikData, String> emailcolumn;
    @FXML
    private TableColumn<PracownikData, String> idfcolumn;
    @FXML
    private Button add;
    @FXML
    private Button clear;
    @FXML
    private Button load;
    @FXML
    private TableColumn<PracownikData, String> dobcolumn;
    private ObservableList<PracownikData> data;
    String c1;

    private final String sql = "SELECT * FROM Pracownik";

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

        //makes columns editable
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

    @FXML
    public void getRow() {

        TablePosition pos = workertable.getSelectionModel().getSelectedCells().get(0);
        int row = pos.getRow();
        TableColumn col = pos.getTableColumn();
// this gives the value in the selected cell:
        String data1 = (String) col.getCellObservableValue(row).getValue();
        System.out.println(data1);
//CURRENTLY UNUSED METHOD
    }


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

    @FXML
    private void clearWorkerFild(ActionEvent event) {
        this.id.setText("");
        this.fname.setText("");
        this.lname.setText("");
        this.email.setText("");
        this.idf.setText("");
        this.dob.setValue(null);

    }

    //dbConnction

    private dbConnection dc;
    //Events
    @FXML
    private TextField idevent;
    @FXML
    private TextField nameevent;
    @FXML
    private Button addevent;
    @FXML
    private Button clearevent;
    @FXML
    private Button loadevent;
    @FXML
    private DatePicker devent;
    @FXML
    private TableView<EventData> eventtable;
    @FXML
    private TableColumn<EventData, String> ideventcolumn;
    @FXML
    private TableColumn<EventData, String> nameeventcolumn;
    @FXML
    private TableColumn<EventData, String> deventcolumn;

    private ObservableList<EventData> dataev;

    private final String sqlev = "SELECT * FROM Event";

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

    @FXML
    private void addevent(ActionEvent event){
        String sqlInserte="INSERT INTO Event(name_Event,Date) VALUES (?,?) ";
        try{
            PreparedStatement ps=conn.prepareStatement(sqlInserte);
            ps.setString(1,this.nameevent.getText());
            ps.setString(2,this.devent.getEditor().getText());

            ps.execute();

        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    @FXML
    private  void cleareventFild(ActionEvent event){
        this.nameevent.setText("");
        this.devent.setValue(null);
    }

    //LOGIN
    @FXML
    private TextField nameUser;
    @FXML
    private TextField passUser;
    @FXML
    private TextField divUser;
    @FXML
    private Button addUser;
    @FXML
    private Button clearUser;
    @FXML
    private  Button loadUser;
    @FXML
    private ComboBox<option>combodiv;
    @FXML
    private TableView<LoginData> loginTable;
    @FXML
    private TableColumn<LoginData,String> userUsercolumn;
    @FXML
    private TableColumn<LoginData,String> passUsercolumn;
    @FXML
    private TableColumn<LoginData,String> divUsercolumn;

    private ObservableList<LoginData> datalog;
    private final String sqlog="SELECT * FROM Login";
    @FXML
    private void loadLoginData(){
        try {
            this.datalog= FXCollections.observableArrayList();
            ResultSet rs=conn.createStatement().executeQuery(sqlog);
            while(rs.next()){
                this.datalog.add(new LoginData(rs.getString(1),rs.getString(2),rs.getString(3)));
            }
        }catch (SQLException e){
            System.err.println("ERROR"+e);
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
            updata("pass", events.getNewValue(), upass.getUsername(),"Login","username");
        });
        divUsercolumn.setOnEditCommit(events -> {
            LoginData udiv = events.getRowValue();
            udiv.setDivision(events.getNewValue());
            updata("pass", events.getNewValue(), udiv.getUsername(),"Login","username");
        });

        this.loginTable.setItems(null);
        this.loginTable.setItems(this.datalog);
    }

    @FXML
    private void addLogin(ActionEvent event){
        String sqlInsertl="INSERT INTO Login(username,pass,division) VALUES (?,?,?) ";
        try{
            PreparedStatement ps=conn.prepareStatement(sqlInsertl);
            ps.setString(1,this.nameUser.getText());
            ps.setString(2,this.passUser.getText());
            //ps.setString(3,this.divUser.getText());
            ps.setString(3,combodiv.getValue().toString());
            ps.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    @FXML
    private  void clearLoginFild(ActionEvent event){
        this.nameUser.setText("");
        this.passUser.setText("");
        this.divUser.setText("");

    }

    //Firmy
    Savepoint savepoint;
    @FXML
    private Button savePoint;
    @FXML
    private TextField idfirma;
    @FXML
    private  TextField namefirma;
    @FXML
    private Button addfirma;
    @FXML
    private Button clearfirma;
    @FXML
    private Button loadfirma;
    @FXML
    private TableView<FirmyData> firmatable;
    @FXML
    private TableColumn<FirmyData,String> idfirmacolumn;
    @FXML
    private TableColumn<FirmyData,String> namefirmacolumn;
    private ObservableList<FirmyData> dataf;

    private final String sqlfirma="SELECT * FROM Firma";//
    @FXML
    private void loadFirmaData(){
        try {
            this.dataf= FXCollections.observableArrayList();
            ResultSet rs=conn.createStatement().executeQuery(sqlfirma);
            while(rs.next()){
                this.dataf.add(new FirmyData(rs.getString(1),rs.getString(2)));
            }
        }catch (SQLException e){
            System.err.println("ERROR"+e);
        }
        this.firmatable.setEditable(true);
        this.firmatable.getSelectionModel().setCellSelectionEnabled(true);

        this.namefirmacolumn.setCellFactory(TextFieldTableCell.forTableColumn());


        this.idfirmacolumn.setCellValueFactory(new PropertyValueFactory("ID_Firmy"));
        this.namefirmacolumn.setCellValueFactory(new PropertyValueFactory("Nazwa_Firmy"));

        namefirmacolumn.setOnEditCommit(events -> {
            FirmyData uname = events.getRowValue();
            uname.setNazwa_Firmy(events.getNewValue());
            updata("Nazwa_Firmy", events.getNewValue(), uname.getID_Firmy(),"Firma","ID_Firmy");
        });

        this.firmatable.setItems(null);
        this.firmatable.setItems(this.dataf);
    }
    @FXML
    private void addFirmy(ActionEvent event){
        String sqlInsertf="INSERT INTO Firma(Nazwa_Firmy) VALUES (?) ";
        try{
            PreparedStatement ps=conn.prepareStatement(sqlInsertf);
            ps.setString(1,this.namefirma.getText());
            ps.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    @FXML
    private  void clearFirmaFild(ActionEvent event){
        this.namefirma.setText("");
    }
    //tranzakcja
    private boolean sp=false;
    @FXML
    private Button ok;
    @FXML
    private Button no;
    @FXML
    private Button start;
    @FXML
    private void commit(ActionEvent a){
        try {
        conn.commit();
        }catch (SQLException e){
            e.printStackTrace();
        }
        sp=false;
    }
    @FXML
    private void setPoint(ActionEvent a){
            try {
                savepoint = conn.setSavepoint();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            sp=true;
        }
    @FXML
    private void rollback(ActionEvent a){
        if(sp){
        try {
            conn.rollback(savepoint);
        }catch (SQLException e){
            e.printStackTrace();
        }
        }

    }

}




