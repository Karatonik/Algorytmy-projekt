package Admin;

import Loginapp.option;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class DecAndClear extends Session {
    //sortowanie przyciski
    @FXML
    public TextField filterFieldWorker, filterFieldFirma, filterFieldLogin, filterFieldEvent;
    //Wybór tabeli w bazie danych
    public final String sql = "SELECT * FROM Pracownik";
    public final String sqlev = "SELECT * FROM Event";
    public final String sqlog = "SELECT * FROM Login";
    public final String sqlfirma = "SELECT * FROM Firma";
//tworzenie obiektów oblserwowalntch list oraz tabeli tableview
    @FXML
    public TableView<PracownikData> workertable;
    public ObservableList<PracownikData> data;
    @FXML
    public TableView<EventData> eventtable;
    public ObservableList<EventData> dataev;
    @FXML
    public TableView<LoginData> loginTable;
    public ObservableList<LoginData> datalog;
    @FXML
    public TableView<FirmyData> firmatable;
    @FXML
    public ObservableList<FirmyData> dataf;
    //Przeciązenia pól w pliku fxml
    //Pracownik
    @FXML
    public TextField id, fname, lname, email, idf, testl;
    @FXML
    public DatePicker dob;
    @FXML
    public TableColumn<PracownikData, String> idcolumn, fnamecolumn, lnamecolumn, emailcolumn, idfcolumn;
    @FXML
    public Button add, clear, load;
    @FXML
    public TableColumn<PracownikData, String> dobcolumn;
    //Events
    @FXML
    public TextField idevent, nameevent;
    @FXML
    public Button addevent, clearevent, loadevent;
    @FXML
    public DatePicker devent;
    @FXML
    public TableColumn<EventData, String> ideventcolumn, nameeventcolumn, deventcolumn;
    //LOGIN
    @FXML
    public TextField nameUser, passUser;
    @FXML
    public Button divUser, addUser, clearUser, loadUser;
    @FXML
    public ComboBox<option> combodiv;
    @FXML
    public TableColumn<LoginData, String> userUsercolumn, passUsercolumn, divUsercolumn;

    //Firmy
    @FXML
    public TextField idfirma, namefirma;
    @FXML
    public Button addfirma, clearfirma, loadfirma;
    @FXML
    public TableColumn<FirmyData, String> idfirmacolumn, namefirmacolumn;

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
}
