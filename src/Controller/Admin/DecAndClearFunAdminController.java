package Controller.Admin;

import Model.Admin.EventDataForAdminController;
import Model.Admin.FirmDataForAdminController;
import Model.Admin.LoginDataForAdminController;
import Model.Admin.WorkerDataForAdminController;
import Model.Option;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class DecAndClearFunAdminController extends SessionFunAdminController {
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
    public TableView<WorkerDataForAdminController> workertable;
    public ObservableList<WorkerDataForAdminController> data;
    @FXML
    public TableView<EventDataForAdminController> eventtable;
    public ObservableList<EventDataForAdminController> dataev;
    @FXML
    public TableView<LoginDataForAdminController> loginTable;
    public ObservableList<LoginDataForAdminController> datalog;
    @FXML
    public TableView<FirmDataForAdminController> firmatable;
    @FXML
    public ObservableList<FirmDataForAdminController> dataf;
    //Przeciązenia pól w pliku fxml
    //Pracownik
    @FXML
    public TextField id, fname, lname, email, idf, testl;
    @FXML
    public DatePicker dob;
    @FXML
    public TableColumn<WorkerDataForAdminController, String> idcolumn, fnamecolumn, lnamecolumn, emailcolumn, idfcolumn;
    @FXML
    public Button add, clear, load;
    @FXML
    public TableColumn<WorkerDataForAdminController, String> dobcolumn;
    //Events
    @FXML
    public TextField idevent, nameevent;
    @FXML
    public Button addevent, clearevent, loadevent;
    @FXML
    public DatePicker devent;
    @FXML
    public TableColumn<EventDataForAdminController, String> ideventcolumn, nameeventcolumn, deventcolumn;
    //LOGIN
    @FXML
    public TextField nameUser, passUser;
    @FXML
    public Button divUser, addUser, clearUser, loadUser;
    @FXML
    public ComboBox<Option> combodiv;
    @FXML
    public TableColumn<LoginDataForAdminController, String> userUsercolumn, passUsercolumn, divUsercolumn;

    //Firmy
    @FXML
    public TextField idfirma, namefirma;
    @FXML
    public Button addfirma, clearfirma, loadfirma;
    @FXML
    public TableColumn<FirmDataForAdminController, String> idfirmacolumn, namefirmacolumn;

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
