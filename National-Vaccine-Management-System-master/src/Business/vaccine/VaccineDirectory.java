/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.vaccine;

import java.util.ArrayList;


/**
 *
 * @author Deepak_Chandwani; Naman_Bhargava; Sneha_Shree
 */


public class VaccineDirectory {
    
    ArrayList<Vaccine> vaccineList;
    
    public VaccineDirectory(){
        vaccineList = new ArrayList();
    }
    public ArrayList<Vaccine> getVaccineList() {
        return vaccineList;
    }

    public void setVaccineList(ArrayList<Vaccine> vaccineList) {
        this.vaccineList = vaccineList;
    }
    
    public Vaccine addVaccine() {
        Vaccine vaccine = new Vaccine();
        vaccineList.add(vaccine);
        return vaccine;
    }
    
    //Removing an Employee from the Directory
    public void removeVaccine(Vaccine vaccine) {
        vaccineList.remove(vaccine);
    }
    
    
    
}
