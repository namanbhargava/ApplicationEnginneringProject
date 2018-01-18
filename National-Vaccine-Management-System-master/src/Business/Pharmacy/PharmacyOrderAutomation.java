/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.Pharmacy;

import Business.Organization.PharmacyOrganization;
import Business.WorkQueue.PharmacyWorkRequest;
import Business.vaccine.Vaccine;

/**
 *
 * @author erdee
 */
public class PharmacyOrderAutomation {

    PharmacyOrganization pharmacyOrganization;
    public PharmacyOrderAutomation(PharmacyOrganization pharmacyOrganization) {
        this.pharmacyOrganization = pharmacyOrganization;
    }
    
    private void placeOrder()
    {
//        if (hospitalEnterprise.isHospitalApproved()) {
//            PharmacyWorkRequest pwr = new PharmacyWorkRequest();
//            pwr.setVaccine((Vaccine) vaccineComboBox.getSelectedItem());
//            pwr.setRequestedQty(Integer.parseInt(txtVaccineQty.getText()));
//            pwr.setStatus("Sent to PHD");
//            pwr.setSender(account);
//            pwr.setEnterpeise(hospitalEnterprise);
//            pwr.setOrganization(pharmacyOrganization);
//            pwr.setIncludedFlag(false);
//
//            pharmacyOrganization.getWorkQueue().getWorkRequestList().add(pwr);
//            hospitalEnterprise.getWorkQueue().getWorkRequestList().add(pwr);
//            network.getWorkQueue().getWorkRequestList().add(pwr);
//            account.getWorkQueue().getWorkRequestList().add(pwr);
//            business.getWorkQueue().getWorkRequestList().add(pwr);
//            populateWorkQueueTable();
//        }
    }
    
    
    
}
