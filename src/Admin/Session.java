package Admin;

import Loginapp.LoginApp;
import Loginapp.LoginControler;
import dbUtil.dbConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;

public class Session {
    @FXML
    public Button ok, no, start, savePoint;
    public boolean dng = false;
    @FXML
    public Button dconnect;
    //dbConnction
    public dbConnection dc;
    //połączenie
    Connection conn;
    @FXML
            public Button Logout;
    //tranzakcje
    Savepoint savepoint;
    public boolean sp = false;

    //sesja
    @FXML
    public   void disconnecting(ActionEvent a) {
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

    @FXML
    public void setPoint(ActionEvent a) {
        try {
            savepoint = conn.setSavepoint();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        sp = true;
    }

    @FXML
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
    public void loginout(ActionEvent event) throws Exception{
        Stage stage = (Stage) this.Logout.getScene().getWindow();
        stage.close();
        Stage stage1=new Stage();
        LoginApp loginApp =new LoginApp();
        loginApp.start(stage1);

    }



}
