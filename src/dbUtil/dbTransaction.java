package dbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class dbTransaction {


    public void beginTransaction(){
            try {
                Connection conn = dbConnection.getConnection();
                PreparedStatement ps=conn.prepareStatement("BEGIN TRANSACTION");
            }catch (SQLException e){
                e.printStackTrace();
            }

    }
    public void rollback(){
        try {
            Connection conn = dbConnection.getConnection();
            PreparedStatement ps=conn.prepareStatement("ROLLBACK");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public void commit(){
        try {
            Connection conn = dbConnection.getConnection();
            PreparedStatement ps=conn.prepareStatement("COMMIT");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
