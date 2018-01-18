/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.DistributorCenter;

import Business.WorkQueue.WorkQueue;
import Business.vaccine.VaccineDirectory;

/**
 *
 * @author Deepak_Chandwani; Naman_Bhargava; Sneha_Shree
 */
public class DistributorCenter {
    
    private VaccineDirectory distVaccineDirectory;
    private WorkQueue workQueue;

    public WorkQueue getWorkQueue() {
        return workQueue;
    }

    public void setWorkQueue(WorkQueue workQueue) {
        this.workQueue = workQueue;
    }
    
    public DistributorCenter() {
        distVaccineDirectory = new VaccineDirectory();
        workQueue= new WorkQueue();
    }

    public VaccineDirectory getDistVaccineDirectory() {
        return distVaccineDirectory;
    }

    public void setDistVaccineDirectory(VaccineDirectory distVaccineDirectory) {
        this.distVaccineDirectory = distVaccineDirectory;
    }

}
