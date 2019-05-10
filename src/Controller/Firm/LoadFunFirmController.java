package Controller.Firm;

import Model.Firm.EventDataForFirmController;
import Model.Firm.WorkerDataForFirmController;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import java.sql.ResultSet;
import java.sql.SQLException;

import static Controller.Firm.UpdateFunFirmController.updata;

public class LoadFunFirmController extends ClearFunFirmController {
    //odczyt do tabeli Wydarzenia
    @FXML
    public void loadWydData() {
        try {
            this.dataw = FXCollections.observableArrayList();
            ResultSet rs = conn.createStatement().executeQuery(sqlw);
            while (rs.next()) {
                this.dataw.add(new EventDataForFirmController(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
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
            EventDataForFirmController uname = event.getRowValue();
            uname.setName_Event(event.getNewValue());
            updata("name_Event", event.getNewValue(), uname.getID_Event(), "Event", "ID_Event",conn);
        });
        dobwcolumn.setOnEditCommit(event -> {
            EventDataForFirmController udob = event.getRowValue();
            udob.setDate(event.getNewValue());
            updata("Date", event.getNewValue(), udob.getID_Event(), "Event", "ID_Event",conn);
        });

        this.WydTable.setItems(null);
        this.WydTable.setItems(this.dataw);
    }
    //odczyt dla tabeli projekt
    @FXML
    public void loadProjData() {
        try {
            this.dataproj = FXCollections.observableArrayList();
            ResultSet rs = conn.createStatement().executeQuery(sqlproj);
            while (rs.next()) {
                this.dataproj.add(new EventDataForFirmController(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
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
            EventDataForFirmController uname = event.getRowValue();
            uname.setName_Event(event.getNewValue());
            updata("name_Event", event.getNewValue(), uname.getID_Event(), "Event", "ID_Event",conn);
        });
        dobprojcolumn.setOnEditCommit(event -> {
            EventDataForFirmController udob = event.getRowValue();
            udob.setDate(event.getNewValue());
            updata("Date", event.getNewValue(), udob.getID_Event(), "Event", "ID_Event",conn);
        });

        this.ProjektTable.setItems(null);
        this.ProjektTable.setItems(this.dataproj);
    }
    //odczyt dla tabeli Pracownik
    @FXML
    public void loadWorkerData() {
        try {
            this.datap = FXCollections.observableArrayList();
            ResultSet rs = conn.createStatement().executeQuery(sqlp);
            while (rs.next()) {
                this.datap.add(new WorkerDataForFirmController(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8)));
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
            WorkerDataForFirmController uname = event.getRowValue();
            uname.setFirstName(event.getNewValue());
            updata("fname", event.getNewValue(), uname.getID_Pracownika(), "Pracownik", "ID_Pracownika",conn);
        });
        lnamecolumn.setOnEditCommit(event -> {
            WorkerDataForFirmController ulname = event.getRowValue();
            ulname.setLastName(event.getNewValue());
            updata("lname", event.getNewValue(), ulname.getID_Pracownika(), "Pracownik", "ID_Pracownika",conn);
        });
        emailcolumn.setOnEditCommit(event -> {
            WorkerDataForFirmController uemail = event.getRowValue();
            uemail.setEmail(event.getNewValue());
            updata("email", event.getNewValue(), uemail.getID_Pracownika(), "Pracownik", "ID_Pracownika",conn);
        });
        dobcolumn.setOnEditCommit(event -> {
            WorkerDataForFirmController udob = event.getRowValue();
            udob.setDOB(event.getNewValue());
            updata("DOB", event.getNewValue(), udob.getID_Pracownika(), "Pracownik", "ID_Pracownika",conn);
        });
        stancolumn.setOnEditCommit(event -> {
            WorkerDataForFirmController ustan = event.getRowValue();
            ustan.setDOB(event.getNewValue());
            updata("Stanowisko", event.getNewValue(), ustan.getID_Pracownika(), "Pracownik", "ID_Pracownika",conn);
        });
        peselcolumn.setOnEditCommit(event -> {
            WorkerDataForFirmController upesel = event.getRowValue();
            upesel.setDOB(event.getNewValue());
            updata("Pesel", event.getNewValue(), upesel.getID_Pracownika(), "Pracownik", "ID_Pracownika",conn);
        });


        this.workertable.setItems(null);
        this.workertable.setItems(this.datap);
    }
}
