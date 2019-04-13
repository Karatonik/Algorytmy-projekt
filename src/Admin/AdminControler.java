package Admin;

import Loginapp.option;
import dbUtil.dbConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class AdminControler implements Initializable {
    //Pracownik
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
    private TableColumn<PracownikData,String> idcolumn;
    @FXML
    private TableColumn<PracownikData,String> fnamecolumn;
    @FXML
    private TableColumn<PracownikData,String> lnamecolumn;
    @FXML
    private TableColumn<PracownikData,String> emailcolumn;
    @FXML
    private TableColumn<PracownikData,String> idfcolumn;
    @FXML
    private Button add;
    @FXML
    private Button clear;
    @FXML
    private Button load;
    @FXML
    private TableColumn<PracownikData,String> dobcolumn;
    private ObservableList<PracownikData> data;



    private final String sql="SELECT * FROM Pracownik";
    public void initialize(URL url, ResourceBundle rb){
this.dc=new dbConnection();
this.combodiv.setItems(FXCollections.observableArrayList(option.values()));
    }
    @FXML
    private void loadWorkerData(ActionEvent event){
        try {
            Connection conn=dbConnection.getConnection();
            this.data= FXCollections.observableArrayList();
            ResultSet rs=conn.createStatement().executeQuery(sql);
            while(rs.next()){
                this.data.add(new PracownikData(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6)));
            }
        }catch (SQLException e){
            System.err.println("ERROR"+e);
        }
        this.idcolumn.setCellValueFactory(new PropertyValueFactory("ID"));
        this.fnamecolumn.setCellValueFactory(new PropertyValueFactory("firstName"));
        this.lnamecolumn.setCellValueFactory(new PropertyValueFactory("lastName"));
        this.emailcolumn.setCellValueFactory(new PropertyValueFactory("email"));
        this.dobcolumn.setCellValueFactory(new PropertyValueFactory("DOB"));
        this.idfcolumn.setCellValueFactory(new PropertyValueFactory("ID_Firmy"));

        this.workertable.setItems(null);
        this.workertable.setItems(this.data);
    }
    @FXML
    private void addWorker(ActionEvent event){
        String sqlInsert="INSERT INTO Pracownik(fname,lname,email,DOB,ID_Firmy) VALUES (?,?,?,?,?) ";
        try{
Connection conn=dbConnection.getConnection();
            PreparedStatement ps=conn.prepareStatement(sqlInsert);
            ps.setString(1,this.fname.getText());
            ps.setString(2,this.lname.getText());
            ps.setString(3,this.email.getText());
            ps.setString(4,this.dob.getEditor().getText());
            ps.setString(5,this.idf.getText());
            ps.execute();
            conn.close();

        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    @FXML
    private  void clearWorkerFild(ActionEvent event){
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
    private  Button clearevent;
@FXML
    private  Button loadevent;
@FXML
    private DatePicker devent;
@FXML
    private TableView<EventData> eventtable;
    @FXML
    private TableColumn<EventData,String> ideventcolumn;
    @FXML
    private TableColumn<EventData,String> nameeventcolumn;
    @FXML
    private TableColumn<EventData,String> deventcolumn;

    private ObservableList<EventData> dataev;

    private final String sqlev="SELECT * FROM Event";
    @FXML
    private void loadEventData(ActionEvent event){
        try {
            Connection conn=dbConnection.getConnection();
            this.dataev= FXCollections.observableArrayList();
            ResultSet rs=conn.createStatement().executeQuery(sqlev);
            while(rs.next()){
                this.dataev.add(new EventData(rs.getString(1),rs.getString(2),rs.getString(3)));
            }
        }catch (SQLException e){
            System.err.println("ERROR"+e);
        }
        this.ideventcolumn.setCellValueFactory(new PropertyValueFactory("ID_Event"));
        this.nameeventcolumn.setCellValueFactory(new PropertyValueFactory("name_Event"));
        this.deventcolumn.setCellValueFactory(new PropertyValueFactory("Date"));

        this.eventtable.setItems(null);
        this.eventtable.setItems(this.dataev);
    }
    @FXML
    private void addevent(ActionEvent event){
        String sqlInserte="INSERT INTO Event(name_Event,Date) VALUES (?,?) ";
        try{
            Connection conn=dbConnection.getConnection();
            PreparedStatement ps=conn.prepareStatement(sqlInserte);
            ps.setString(1,this.nameevent.getText());
            ps.setString(2,this.devent.getEditor().getText());

            ps.execute();
            conn.close();

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
    private void loadLoginData(ActionEvent event){
        try {
            Connection conn=dbConnection.getConnection();
            this.datalog= FXCollections.observableArrayList();
            ResultSet rs=conn.createStatement().executeQuery(sqlog);
            while(rs.next()){
                this.datalog.add(new LoginData(rs.getString(1),rs.getString(2),rs.getString(3)));
            }
        }catch (SQLException e){
            System.err.println("ERROR"+e);
        }
        this.userUsercolumn.setCellValueFactory(new PropertyValueFactory("username"));
        this.passUsercolumn.setCellValueFactory(new PropertyValueFactory("pass"));
        this.divUsercolumn.setCellValueFactory(new PropertyValueFactory("division"));


        this.loginTable.setItems(null);
        this.loginTable.setItems(this.datalog);
    }
    @FXML
    private void addLogin(ActionEvent event){
        String sqlInsertl="INSERT INTO Login(username,pass,division) VALUES (?,?,?) ";
        try{
            Connection conn=dbConnection.getConnection();
            PreparedStatement ps=conn.prepareStatement(sqlInsertl);
            ps.setString(1,this.nameUser.getText());
            ps.setString(2,this.passUser.getText());
            //ps.setString(3,this.divUser.getText());
            ps.setString(3,combodiv.getValue().toString());

            ps.execute();
            conn.close();

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
    private void loadFirmaData(ActionEvent event){
        try {
            Connection conn=dbConnection.getConnection();
            this.dataf= FXCollections.observableArrayList();
            ResultSet rs=conn.createStatement().executeQuery(sqlfirma);
            while(rs.next()){
                this.dataf.add(new FirmyData(rs.getString(1),rs.getString(2)));
            }
        }catch (SQLException e){
            System.err.println("ERROR"+e);
        }
        this.idfirmacolumn.setCellValueFactory(new PropertyValueFactory("ID_Firmy"));
        this.namefirmacolumn.setCellValueFactory(new PropertyValueFactory("Nazwa_Firmy"));

        this.firmatable.setItems(null);
        this.firmatable.setItems(this.dataf);
    }
    @FXML
    private void addFirmy(ActionEvent event){

        String sqlInsertf="INSERT INTO Firma(Nazwa_Firmy) VALUES (?) ";
        try{
            Connection conn=dbConnection.getConnection();
            PreparedStatement ps=conn.prepareStatement(sqlInsertf);
            ps.setString(1,this.namefirma.getText());

            ps.execute();
            conn.close();

        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    @FXML
    private  void clearFirmaFild(ActionEvent event){
        this.namefirma.setText("");
    }
}
