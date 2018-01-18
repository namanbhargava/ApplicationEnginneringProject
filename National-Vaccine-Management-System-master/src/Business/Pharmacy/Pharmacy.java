/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.Pharmacy;

import Business.Inventory.VaccineInventory;
import Business.WorkQueue.WorkQueue;


/**
 *
 * @author Deepak_Chandwani; Naman_Bhargava; Sneha_Shree
 */


public class Pharmacy { 
    private VaccineInventory vaccineInventory;
    private WorkQueue workQueue;
    
    public Pharmacy(){
        vaccineInventory = new VaccineInventory();
        setVaccineInventory(vaccineInventory);        
        workQueue = new WorkQueue();
    }

    public VaccineInventory getVaccineInventory() {
        return vaccineInventory;
    }

    public void setVaccineInventory(VaccineInventory vaccineInventory) {
        this.vaccineInventory = vaccineInventory;
    }

    public WorkQueue getWorkQueue() {
        return workQueue;
    }

    public void setWorkQueue(WorkQueue workQueue) {
        this.workQueue = workQueue;
    }
}
