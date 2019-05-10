package Controller.Admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateFunAdminController {

    //metoda aktualizująca baze dany po komórkach
    public static void updata(String column, String newValue, String id, String nameTable, String whereID,Connection conn) {
        try (
                PreparedStatement stmt = conn.prepareStatement("UPDATE " + nameTable + " SET " + column + " = ? WHERE " + whereID + "= ? ")
        ) {
            stmt.setString(1, newValue);
            stmt.setString(2, id);
            stmt.execute();
        } catch (SQLException ex) {
            System.err.println("Error");
            ex.printStackTrace(System.err);
        }
    }
}
