/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.WorkQueue;

import Business.Patient.Patient;


/**
 *
 * @author Deepak_Chandwani; Naman_Bhargava; Sneha_Shree
 */


public class ReceptionWorkRequest extends WorkRequest{

    private Patient patient; 
    private int requestQuantity;
     public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
    private float price;

    public int getRequestQuantity() {
        return requestQuantity;
    }

    public void setRequestQuantity(int requestQuantity) {
        this.requestQuantity = requestQuantity;
    }
    
    public ReceptionWorkRequest() {
        patient = new Patient();    
    }
    
    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }
    
  
  
  
}
