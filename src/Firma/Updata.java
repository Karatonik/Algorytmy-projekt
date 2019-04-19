package Firma;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Updata {

    //zapis do bazy danych edytowanych komórek
    public static void updata(String column, String newValue, String id, String nameTable, String whereID,Connection conn) {
        try (
                PreparedStatement stmt = conn.prepareStatement("UPDATE " + nameTable + " SET " + column + " = ? WHERE " + whereID + "= ? ")
        ) {

            stmt.setString(1, newValue);
            stmt.setString(2, id);
            stmt.execute();
        } catch (SQLException ex) {
            System.err.println("Error");
            // if anything goes wrong, you will need the stack trace:
            ex.printStackTrace(System.err);
        }
    }
}
