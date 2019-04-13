package Firma;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class EventsData {
    private final StringProperty ID_Event;
    private final StringProperty name_Event;
    private final StringProperty Date;
    private final StringProperty ID_Firmy;
    public EventsData(String id,String naz,String dob,String fir){
        this.ID_Event=new SimpleStringProperty(id);
        this.name_Event=new SimpleStringProperty(naz);
        this.Date=new SimpleStringProperty(dob);
        this.ID_Firmy=new SimpleStringProperty(fir);
    }

    public String getID_Event() {
        return ID_Event.get();
    }

    public StringProperty ID_EventProperty() {
        return ID_Event;
    }

    public void setID_Event(String ID_Event) {
        this.ID_Event.set(ID_Event);
    }

    public String getName_Event() {
        return name_Event.get();
    }

    public StringProperty name_EventProperty() {
        return name_Event;
    }

    public void setName_Event(String name_Event) {
        this.name_Event.set(name_Event);
    }

    public String getDate() {
        return Date.get();
    }

    public StringProperty dateProperty() {
        return Date;
    }

    public void setDate(String date) {
        this.Date.set(date);
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
}
