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
public class WorkersControler implements Initializable {

    public void initialize(URL url, ResourceBundle rb){ this.dc=new dbConnection(); }
    Connection conn;
    public WorkersControler(){
        try {
            conn=dbConnection.getConnection();
            conn.setAutoCommit(false);
        }catch (SQLException e){
            System.err.println("ERROR"+e);
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
    //przeciązenia
@FXML
            private Label id;
    @FXML
            private Label flname;
    @FXML
            private Label email;
    @FXML
            private Label dob;
    @FXML
            private Label stan;
    @FXML
            private Label pesel;
    @FXML
    private Button reboot;
    @FXML
    private TableView<EventsDataWorker> workerProjtable;
    @FXML
    private TableView<EventsDataWorker> workerWydtable;
    @FXML
    private TableColumn<EventsDataWorker,String> nameproj;
    @FXML
    private TableColumn<EventsDataWorker,String> namewyd;
    @FXML
    private TableColumn<EventsDataWorker,String> dateproj;
    @FXML
    private TableColumn<EventsDataWorker,String> datewyd;
    @FXML
    private TableColumn<EventsDataWorker,String> statusproj;
    private ObservableList<EventsDataWorker> datap;
    private ObservableList<EventsDataWorker> dataw;
    private final String sqlw="SELECT * FROM Event WHERE PW='W'";
    private final String sqlp="SELECT * FROM Event WHERE PW='P'";

@FXML
    private void reboot(ActionEvent event){
    try {
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

//lable dla pracownika

}

}












