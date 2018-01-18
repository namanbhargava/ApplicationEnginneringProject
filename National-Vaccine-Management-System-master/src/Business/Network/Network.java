/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.Network;

import Business.Enterprise.EnterpriseDirectory;
import Business.Events.EventsDirectory;
import Business.WorkQueue.WorkQueue;


/**
 *
 * @author Deepak_Chandwani; Naman_Bhargava; Sneha_Shree
 */


public class Network{
    
    private String name;
    private EnterpriseDirectory enterpriseDirectory;
    private WorkQueue workQueue;
    private EventsDirectory eventsDirectory;
   
    
     public Network() {
        enterpriseDirectory = new EnterpriseDirectory();
        workQueue = new WorkQueue();
        eventsDirectory = new EventsDirectory();
    }

    public EventsDirectory getEventsDirectory() {
        return eventsDirectory;
    }

    public void setEventsDirectory(EventsDirectory eventsDirectory) {
        this.eventsDirectory = eventsDirectory;
    }
    
    public WorkQueue getWorkQueue() {
        return workQueue;
    }

    public void setWorkQueue(WorkQueue workQueue) {
        this.workQueue = workQueue;
    }

   public EnterpriseDirectory getEnterpriseDirectory() {
        return enterpriseDirectory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

   
}
