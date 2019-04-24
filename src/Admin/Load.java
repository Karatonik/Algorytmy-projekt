package Admin;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

import java.sql.ResultSet;
import java.sql.SQLException;

import static Admin.Updata.updata;

public class Load extends DecAndClear {
//odczyt z bazy danych
    @FXML
    public void loadWorkerData() {
        try {
            this.data = FXCollections.observableArrayList(); //przełączenie na listę wsierająca fxmlcollections
            ResultSet rs = conn.createStatement().executeQuery(sql);
            while (rs.next()) {
                this.data.add(new PracownikData(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6)));
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
            PracownikData ufname = event.getRowValue();
            ufname.setFirstName(event.getNewValue());
            updata("fname", event.getNewValue(), ufname.getID(), "Pracownik", "ID_Pracownika", conn);
        });
        lnamecolumn.setOnEditCommit(event -> {
            PracownikData ulname = event.getRowValue();
            ulname.setLastName(event.getNewValue());
            updata("lname", event.getNewValue(), ulname.getID(), "Pracownik", "ID_Pracownika", conn);
        });
        emailcolumn.setOnEditCommit(event -> {
            PracownikData uemail = event.getRowValue();
            uemail.setEmail(event.getNewValue());
            updata("email", event.getNewValue(), uemail.getID(), "Pracownik", "ID_Pracownika", conn);
        });
        dobcolumn.setOnEditCommit(event -> {
            PracownikData udob = event.getRowValue();
            udob.setDOB(event.getNewValue());
            updata("DOB", event.getNewValue(), udob.getID(), "Pracownik", "ID_Pracownika", conn);
        });

        workertable.setItems(null);
        workertable.setItems(data);//zwraca do tabeli listę data
    }

    //odczyt dla tabeli eventy
    @FXML
    public void loadEventData() {
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
            updata("name_Event", events.getNewValue(), uname.getID_Event(), "Event", "ID_Event", conn);
        });
        deventcolumn.setOnEditCommit(events -> {
            EventData udevnet = events.getRowValue();
            udevnet.setDate(events.getNewValue());
            updata("Date", events.getNewValue(), udevnet.getID_Event(), "Event", "ID_Event", conn);
        });

        this.eventtable.setItems(null);
        this.eventtable.setItems(this.dataev);
    }

    @FXML
    public void loadLoginData() {
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
            updata("pass", events.getNewValue(), upass.getUsername(), "Login", "username", conn);
        });
        divUsercolumn.setOnEditCommit(events -> {
            LoginData udiv = events.getRowValue();
            udiv.setDivision(events.getNewValue());
            updata("pass", events.getNewValue(), udiv.getUsername(), "Login", "username", conn);
        });

        this.loginTable.setItems(null);
        this.loginTable.setItems(this.datalog);
    }

    //odczyt dla tabeli Firmy
    @FXML
    public void loadFirmaData() {
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
            updata("Nazwa_Firmy", events.getNewValue(), uname.getID_Firmy(), "Firma", "ID_Firmy", conn);
        });

        this.firmatable.setItems(null);
        this.firmatable.setItems(this.dataf);
    }

}
