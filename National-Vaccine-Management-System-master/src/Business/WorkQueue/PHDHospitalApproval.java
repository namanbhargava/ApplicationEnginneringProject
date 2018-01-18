/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.WorkQueue;

import Business.Enterprise.HospitalEnterprise;


/**
 *
 * @author Deepak_Chandwani; Naman_Bhargava; Sneha_Shree
 */


public class PHDHospitalApproval {
    private HospitalEnterprise hospitalEnterprise;
    private String hospitalStatus;
    //private boolean hospitalApproved;

    public PHDHospitalApproval() {
    }

    public HospitalEnterprise getHospitalEnterprise() {
        return hospitalEnterprise;
    }

    public void setHospitalEnterprise(HospitalEnterprise hospitalEnterprise) {
        this.hospitalEnterprise = hospitalEnterprise;
    }

    public String getHospitalStatus() {
        return hospitalStatus;
    }

    public void setHospitalStatus(String hospitalStatus) {
        this.hospitalStatus = hospitalStatus;
    }

//    public boolean isHospitalApproved() {
//        return hospitalApproved;
//    }
//
//    public void setHospitalApproved(boolean hospitalApproved) {
//        this.hospitalApproved = hospitalApproved;
//    }
    
    
    
    
    
}
