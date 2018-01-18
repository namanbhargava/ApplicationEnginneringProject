/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.Organization;

import Business.Inventory.VaccineInventory;
import Business.Pharmacy.Pharmacy;
import Business.Role.PharmacyAdminRole;
import Business.Role.Role;
import Business.WorkQueue.WorkQueue;
import java.util.ArrayList;


/**
 *
 * @author Deepak_Chandwani; Naman_Bhargava; Sneha_Shree
 */


public class PharmacyOrganization extends Organization {

    private Pharmacy pharmacy;
    private VaccineInventory vaccineInventory;
    private WorkQueue workQueue;

//    public PharmacyOrganization(){
//        //pharmacyVaccineDirectory = new VaccineDirectory();
//        vaccineInventory = new VaccineInventory();
//        setVaccineInventory(vaccineInventory);        
//        workQueue = new WorkQueue();
//    }
    public VaccineInventory getVaccineInventory() {
        return vaccineInventory;
    }

    public void setVaccineInventory(VaccineInventory vaccineInventory) {
        this.vaccineInventory = vaccineInventory;
    }

    public Pharmacy getPharmacy() {
        return pharmacy;
    }

    public void setPharmacy(Pharmacy pharmacy) {
        this.pharmacy = pharmacy;
    }

    public PharmacyOrganization() {        
        super(Organization.Type.PharmacyOrganization.getValue());
        vaccineInventory = new VaccineInventory();
        setVaccineInventory(vaccineInventory);
    }

    @Override
    public ArrayList<Role> getSupportedRole() {
        ArrayList<Role> roles = new ArrayList();
        roles.add(new PharmacyAdminRole());
        return roles;
    }
}
