package Admin;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class FirmyData {
    private final StringProperty ID_Firmy;
    private final StringProperty Nazwa_Firmy;

    public FirmyData(String id,String name){
        this.ID_Firmy=new SimpleStringProperty(id);
        this.Nazwa_Firmy=new SimpleStringProperty(name);
    }

    public String getID_Firmy() {
        return ID_Firmy.get();
    }

    public StringProperty ID_FirmyProperty() {
        return ID_Firmy;
    }

    public void setID_Firmy(String ID_Firmy) {
        this.ID_Firmy.set(ID_Firmy);
    }

    public String getNazwa_Firmy() {
        return Nazwa_Firmy.get();
    }

    public StringProperty nazwa_FirmyProperty() {
        return Nazwa_Firmy;
    }

    public void setNazwa_Firmy(String nazwa_Firmy) {
        this.Nazwa_Firmy.set(nazwa_Firmy);
    }
}
