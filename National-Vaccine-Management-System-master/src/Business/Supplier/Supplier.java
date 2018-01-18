/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.Supplier;

import Business.WorkQueue.WorkQueue;
import Business.vaccine.VaccineDirectory;


/**
 *
 * @author Deepak_Chandwani; Naman_Bhargava; Sneha_Shree
 */


public class Supplier {
    private String supplierName;
    
    private static int sCount;
    private String supplierId;
    private VaccineDirectory supplierVaccineList;
    private WorkQueue workQueue;
    
    public Supplier(){
        supplierId= "SID"+(++sCount);
        supplierVaccineList= new VaccineDirectory();
        workQueue = new WorkQueue();
    }

    public WorkQueue getWorkQueue() {
        return workQueue;
    }

    public void setWorkQueue(WorkQueue workQueue) {
        this.workQueue = workQueue;
    }
   

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public VaccineDirectory getSupplierVaccineList() {
        return supplierVaccineList;
    }

    public void setSupplierVaccineList(VaccineDirectory supplierVaccineList) {
        this.supplierVaccineList = supplierVaccineList;
    }
    
    
    public String toString()
    {
        return supplierName;
    }
    
}
