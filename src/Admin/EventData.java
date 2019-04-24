package Admin;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class EventData {
    private final StringProperty name_Event;
    private final StringProperty ID_Event;
    private final StringProperty Date;

//Tworzy ramkę by przy liscie był łatwy dostep do danych
    public EventData(String id, String name, String date) {
        this.name_Event = new SimpleStringProperty(name);
        this.ID_Event = new SimpleStringProperty(id);
        this.Date = new SimpleStringProperty(date);
    }

    public String getName_Event() {
        return name_Event.get();
    }

    public void setName_Event(String name_Event) {
        this.name_Event.set(name_Event);
    }

    public StringProperty name_EventProperty() {
        return name_Event;
    }

    public String getID_Event() {
        return ID_Event.get();
    }

    public void setID_Event(String ID_Event) {
        this.ID_Event.set(ID_Event);
    }

    public StringProperty ID_EventProperty() {
        return ID_Event;
    }

    public String getDate() {
        return Date.get();
    }

    public void setDate(String date) {
        this.Date.set(date);
    }

    public StringProperty dateProperty() {
        return Date;
    }
}