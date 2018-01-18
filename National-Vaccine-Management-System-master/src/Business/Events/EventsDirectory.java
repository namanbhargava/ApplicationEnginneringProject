/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.Events;

import java.util.ArrayList;

/**
 *
 * @author bharg
 */
public class EventsDirectory {
    
    private ArrayList<Events> eventList;

     public EventsDirectory() {
        eventList = new ArrayList();
    }
    
    public ArrayList<Events> getEventList() {
        return eventList;
    }

    public void setEventList(ArrayList<Events> eventList) {
        this.eventList = eventList;
    }
    
    public Events addEvents()
    {
        Events e = new Events();
        eventList.add(e);
        return e;
    }

}
