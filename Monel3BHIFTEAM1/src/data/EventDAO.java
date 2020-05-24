
package data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import model.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class EventDAO {

    private ObservableList<Event> events = FXCollections.observableList(new ArrayList<Event>());
    private ObservableList<EventProtocol> eventProtocols = FXCollections.observableList(new ArrayList<EventProtocol>());
    private static EventDAO instance = null;

    private EventDAO() {
        /*Event e = new Event(2, LocalDate.now(), "Fußball", false);
        this.events.add(new Event(1, LocalDate.now(), "Kino", true));
        this.events.add(e);*/
        //this.eventProtocols.add(new EventProtocol(1, LocalTime.now(), LocalTime.now(), LocalDate.now(), 10.03, new Employee(Salutation.Herr,"Herbert", "Gell"), new Client(Salutation.Herr, "Michael", "Gell"), e, 100.01));
    }
    public static EventDAO getInstance() {
        if (instance == null) {
            instance = new EventDAO();
        }
        return instance;
    }

    public boolean addEvent(Event e) {
        return events.add(e);
    }
    public boolean addEventProtcol(EventProtocol ep) {
        return eventProtocols.add(ep);
    }

    public boolean deleteEvent(Event e) {
        return events.remove(e);
    }
    public boolean deleteEventProtcol(EventProtocol ep) {
        return eventProtocols.remove(ep);
    }

    public EventProtocol getEventProtocolByEvent(Event event) {
        Iterator<EventProtocol> it = eventProtocols.iterator();
        EventProtocol res = null;
        while (it.hasNext() && res == null){
            res = it.next();
            if (res.getEvent().getName() != event.getName()){ //recognises no equal //TODO
                res = null;
            }
        }
        return res;
    }

    public ObservableList<Event> getEvents() {
        return events;
    }

    public ObservableList<EventProtocol> getEventProtocols() {
        return eventProtocols;
    }

    public void setEvents(ObservableList<Event> events) { this.events = events; }

    public void setEventProtocols(ObservableList<EventProtocol> eventProtocols) { this.eventProtocols = eventProtocols; }
}
