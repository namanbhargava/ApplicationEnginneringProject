/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.Inventory;

import Business.vaccine.Vaccine;
import java.util.ArrayList;


/**
 *
 * @author Deepak_Chandwani; Naman_Bhargava; Sneha_Shree
 */


public class VaccineInventory {
    
    private ArrayList<Vaccine> vaccineInventoryArrayList;
    
    public VaccineInventory() {
        vaccineInventoryArrayList = new ArrayList<>();
    }
    
    public ArrayList<Vaccine> getVaccineInventoryArrayList() {
        return vaccineInventoryArrayList;
    }
    
    public void setVaccineInventoryArrayList(ArrayList<Vaccine> vaccineInventoryArrayList) {
        this.vaccineInventoryArrayList = vaccineInventoryArrayList;
    }
    
    public void addVaccineInventory(Vaccine vaccine) {
        boolean foundUpdate = false;
        if (vaccineInventoryArrayList.contains(vaccine)) {            
            Vaccine vacc = null;
            for (Vaccine v : vaccineInventoryArrayList) {
                if (vaccine.equals(v)) {
                    vacc = v;
                    foundUpdate = true;
                    vacc.setQuantity(vacc.getQuantity()+vaccine.getQuantity());
                }
            }
            //vacc.setQuantity(vacc.getQuantity()+vaccine.getQuantity());
        }else
        {
            vaccineInventoryArrayList.add(vaccine);
        }
    }
    
    public void deleteVaccine(Vaccine vaccine)
    {
        vaccineInventoryArrayList.remove(vaccine);
    }
    
}
