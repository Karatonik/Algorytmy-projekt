package Admin;

import dbUtil.dbConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

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
    //tranzakcje
    Savepoint savepoint;
    public boolean sp = false;

    //sesja
    @FXML
    private  void disconnecting(ActionEvent a) {
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
    private void commit(ActionEvent a) {
        try {
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        sp = false;
    }

    @FXML
    private void setPoint(ActionEvent a) {
        try {
            savepoint = conn.setSavepoint();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        sp = true;
    }

    @FXML
    private void rollback(ActionEvent a) {
        if (sp) {
            try {
                conn.rollback(savepoint);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }


}
