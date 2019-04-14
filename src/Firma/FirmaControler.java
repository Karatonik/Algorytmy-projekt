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
import static Loginapp.LoginControler.namef;

public class FirmaControler implements Initializable  {
    public void initialize(URL url, ResourceBundle rb){
        this.dc=new dbConnection();
    }
        Connection conn;
    int id_Firmy=0;
        public FirmaControler(){
            try {
                conn=dbConnection.getConnection();
                conn.setAutoCommit(false);
            }catch (SQLException e){
                System.err.println("ERROR"+e);
            }
            try {           //Szuka po nazwie firmy numeru firmy :)
                String sqlcheck = "SELECT ID_Firmy FROM Firma WHERE Nazwa_Firmy='" + namef + "';";
                ResultSet rs = conn.createStatement().executeQuery(sqlcheck);
                id_Firmy = rs.getInt(1);
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        //połączenie
        @FXML
        private Button status;
    private dbConnection dc;
        private boolean dng=false;
        @FXML
        private void disconnecting(ActionEvent a) {
            if (dng){
                try {
                    conn=dbConnection.getConnection();
                    conn.setAutoCommit(false);
                }catch (SQLException e){
                    System.err.println("ERROR"+e);
                }
                status.setText("Polaczono");
                dng=false;
            }
            else{
                try {
                    conn.close();
                } catch (SQLException e) {
                    System.err.println("ERROR" + e);
                }
                status.setText("rozłączone");
                dng=true;

            }
        }
    //Nadpisanie interfejsu
    //Pracownicy
    @FXML
    private TextField ID_Prac;
    @FXML
    private TextField name;
    @FXML
    private TextField lname;
    @FXML
    private  TextField email;
    @FXML
    private  TextField Stan;
    @FXML
    private  TextField pesel;
    @FXML
    private DatePicker dobp;
    @FXML
    private  Button addp;
    @FXML
    private Button loadp;
    @FXML
    private Button clearp;
    @FXML
    private TableView<WorkerData> workertable;
    @FXML
    private TableColumn<WorkerData,String> idcolumn;
    @FXML
    private TableColumn<WorkerData,String> namecolumn;
    @FXML
    private TableColumn<WorkerData,String> lnamecolumn;
    @FXML
    private TableColumn<WorkerData,String> emailcolumn;
    @FXML
    private TableColumn<WorkerData,String> stancolumn;
    @FXML
    private TableColumn<WorkerData,String> peselcolumn;
    @FXML
    private TableColumn<WorkerData,String> dobcolumn;
    //Tranzakcje
    Savepoint savepoint;
    private boolean sp=false;
    @FXML
    private Button commit;
    @FXML
    private Button rollback;
    @FXML
    private Button setPoint;
    //Projekty
    @FXML
    private TextField nazwap;
    @FXML
    private DatePicker dobproj;
    @FXML
    private Button loadproj;
    @FXML
    private Button addproj;
    @FXML
    private Button clearproj;
    @FXML
    private TableView<EventsData> ProjektTable;
    @FXML
    private TableColumn<EventsData,String> idprojcolumn;
    @FXML
    private TableColumn<EventsData,String> nameprojcolumn;
    @FXML
    private TableColumn<EventsData,String> dobprojcolumn;
//Wydarzenie
@FXML
private TextField nazwaw;
    @FXML
    private DatePicker dobw;
    @FXML
    private Button loadw;
    @FXML
    private Button addw;
    @FXML
    private Button clearw;
    @FXML
    private TableView<EventsData> WydTable;
    @FXML
    private TableColumn<EventsData,String> idwcolumn;
    @FXML
    private TableColumn<EventsData,String> namewcolumn;
    @FXML
    private TableColumn<EventsData,String> dobwcolumn;
//metody wydarzenia
private ObservableList<EventsData> dataw;
    private final String sqlw="SELECT * FROM Event WHERE PW='W'";
@FXML
private void loadWydData(ActionEvent event){
    try {
        this.dataw= FXCollections.observableArrayList();
        ResultSet rs=conn.createStatement().executeQuery(sqlw);
        while(rs.next()){
            this.dataw.add(new EventsData(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5)));
        }
    }catch (SQLException e){
        System.err.println("ERROR"+e);
    }
    this.idwcolumn.setCellValueFactory(new PropertyValueFactory("ID_Event"));
    this.namewcolumn.setCellValueFactory(new PropertyValueFactory("name_Event"));
    this.dobwcolumn.setCellValueFactory(new PropertyValueFactory("Date"));
    this.WydTable.setItems(null);
    this.WydTable.setItems(this.dataw);
}
    @FXML
    private void addWyd(ActionEvent event){

        String sqlInsert="INSERT INTO Event(name_Event,Date,ID_Firmy,PW) VALUES (?,?,?,?) ";
        try{
            PreparedStatement ps=conn.prepareStatement(sqlInsert);
            ps.setString(1,this.nazwaw.getText());
            ps.setString(2,this.dobw.getEditor().getText());
            ps.setInt(3,id_Firmy);
            ps.setString(4,"W");
            ps.execute();

        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    @FXML
    private  void clearWydFild(ActionEvent event){
        this.nazwaw.setText("");
        this.dobw.setValue(null);
    }
//metody rojektu
private ObservableList<EventsData> dataproj;
    private final String sqlproj="SELECT * FROM Event WHERE PW='P'";
    @FXML
    private void loadProjData(ActionEvent event){
        try {
            this.dataproj= FXCollections.observableArrayList();
            ResultSet rs=conn.createStatement().executeQuery(sqlproj);
            while(rs.next()){
                this.dataproj.add(new EventsData(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5)));
            }
        }catch (SQLException e){
            System.err.println("ERROR"+e);
        }
        this.idprojcolumn.setCellValueFactory(new PropertyValueFactory("ID_Event"));
        this.nameprojcolumn.setCellValueFactory(new PropertyValueFactory("name_Event"));
        this.dobprojcolumn.setCellValueFactory(new PropertyValueFactory("Date"));
        this.ProjektTable.setItems(null);
        this.ProjektTable.setItems(this.dataproj);
    }
    @FXML
    private void addProj(ActionEvent event){

        String sqlInsert="INSERT INTO Event(name_Event,Date,ID_Firmy,PW) VALUES (?,?,?,?) ";
        try{
            PreparedStatement ps=conn.prepareStatement(sqlInsert);
            ps.setString(1,this.nazwap.getText());
            ps.setString(2,this.dobproj.getEditor().getText());
            ps.setInt(3,id_Firmy);
            ps.setString(4,"P");
            ps.execute();

        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    @FXML
    private  void clearProjFild(ActionEvent event){
        this.nazwap.setText("");
        this.dobproj.setValue(null);
    }
    //metody pracownika
    private ObservableList<WorkerData> datap;
    private final String sqlp="SELECT * FROM Pracownik";
    @FXML
    private void loadWorkerData(ActionEvent event){
        try {
            this.datap= FXCollections.observableArrayList();
            ResultSet rs=conn.createStatement().executeQuery(sqlp);
            while(rs.next()){
                this.datap.add(new WorkerData(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8)));
            }
        }catch (SQLException e){
            System.err.println("ERROR"+e);
        }
        this.idcolumn.setCellValueFactory(new PropertyValueFactory("ID_Pracownika"));
        this.namecolumn.setCellValueFactory(new PropertyValueFactory("firstName"));
        this.lnamecolumn.setCellValueFactory(new PropertyValueFactory("lastName"));
        this.emailcolumn.setCellValueFactory(new PropertyValueFactory("email"));
        this.dobcolumn.setCellValueFactory(new PropertyValueFactory("DOB"));
        this.stancolumn.setCellValueFactory(new PropertyValueFactory("Stanowisko"));
        this.peselcolumn.setCellValueFactory(new PropertyValueFactory("Pesel"));
        this.workertable.setItems(null);
        this.workertable.setItems(this.datap);
    }
    @FXML
    private void addWorkers(ActionEvent event){

        String sqlInsert="INSERT INTO Pracownik(fname,lname,email,DOB,ID_Firmy,Stanowisko,Pesel) VALUES (?,?,?,?,?,?,?) ";
        try{
            PreparedStatement ps=conn.prepareStatement(sqlInsert);
            ps.setString(1,this.name.getText());
            ps.setString(2,this.lname.getText());
            ps.setString(3,this.email.getText());
            ps.setString(4,this.dobp.getEditor().getText());
            ps.setInt(5,id_Firmy);
            ps.setString(6,this.Stan.getText());
            ps.setString(7,this.pesel.getText());
            ps.execute();

        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    @FXML
    private  void clearWorkerFild(ActionEvent event){
        this.name.setText("");
        this.lname.setText("");
        this.email.setText("");
        this.Stan.setText("");
        this.pesel.setText("");
        this.dobp.setValue(null);
    }
//metody tranzakcji
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
