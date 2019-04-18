package Pracownik;
import dbUtil.dbConnection;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import static Loginapp.LoginControler.namef;

public class WorkersControler implements Initializable {

    public void initialize(URL url, ResourceBundle rb){ this.dc=new dbConnection(); }
    //połączenie
    Connection conn;
    private dbConnection dc;
    @FXML
    private Button reboot;
//dane
@FXML
private Label id,flname,email,dob,stan,pesel;

    private final String sql="SELECT * FROM Pracownik WHERE  fname='"+namef+"';";
    private ObservableList<WorkersData> data;
    //wydarzenia/projekty
    @FXML
    private TableColumn<EventsDataWorker,String> nameproj,namewyd,dateproj,datewyd,statusproj;
    @FXML
    private TableView<EventsDataWorker> workerProjtable,workerWydtable;

    private final String sqlw="SELECT * FROM Event WHERE PW='W'";
    private final String sqlp="SELECT * FROM Event WHERE PW='P'";
    private ObservableList<EventsDataWorker> datap,dataw;
    //wczytanie danych z wydarzeń i projektów
@FXML
    private void reboot(ActionEvent event){
    try {
        conn=dbConnection.getConnection();
        this.datap= FXCollections.observableArrayList();
        ResultSet rs=conn.createStatement().executeQuery(sqlp);
        while(rs.next()){
            this.datap.add(new EventsDataWorker(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6)));
        }
    }catch (SQLException e){
        System.err.println("ERROR"+e);
    }
    this.nameproj.setCellValueFactory(new PropertyValueFactory("name_Event"));
    this.dateproj.setCellValueFactory(new PropertyValueFactory("Date"));
    this.statusproj.setCellValueFactory(new PropertyValueFactory("Status"));
    this.workerProjtable.setItems(null);
    this.workerProjtable.setItems(this.datap);

    try {
        this.dataw= FXCollections.observableArrayList();
        ResultSet rs1=conn.createStatement().executeQuery(sqlw);
        while(rs1.next()){
            this.dataw.add(new EventsDataWorker(rs1.getString(1),rs1.getString(2),rs1.getString(3),rs1.getString(4),rs1.getString(5),rs1.getString(6)));
        }
    }catch (SQLException e){
        System.err.println("ERROR"+e);
    }
    this.namewyd.setCellValueFactory(new PropertyValueFactory("name_Event"));
    this.datewyd.setCellValueFactory(new PropertyValueFactory("Date"));
    this.workerWydtable.setItems(null);
    this.workerWydtable.setItems(this.dataw);
    try {
        this.data= FXCollections.observableArrayList();
        ResultSet rs3=conn.createStatement().executeQuery(sql);
        while(rs3.next()){
            this.data.add(new WorkersData(rs3.getString(1),rs3.getString(2),rs3.getString(3),rs3.getString(4),rs3.getString(5),rs3.getString(6),rs3.getString(7),rs3.getString(8)));
            this.id.setText("ID "+rs3.getString(1));
            this.flname.setText("Imie i nazwisko "+rs3.getString(2 )+" "+rs3.getString(3));
            this.email.setText("email "+rs3.getString(4));
            this.dob.setText("data "+rs3.getString(5));
            this.stan.setText("Stanowisko "+rs3.getString(6));
            this.pesel.setText("Pesel "+rs3.getString(7));
        }
    }catch (SQLException e){
        System.err.println("ERROR"+e);
    }
}
}




