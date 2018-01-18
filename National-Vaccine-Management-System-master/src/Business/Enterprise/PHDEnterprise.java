/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.Enterprise;

import Business.Role.PHDAdminRole;
import Business.Role.PHDManagerRole;
import Business.Role.Role;
import Business.WorkQueue.PHDHospitalApproval;
import java.util.ArrayList;


/**
 *
 * @author Deepak_Chandwani; Naman_Bhargava; Sneha_Shree
 */


public class PHDEnterprise extends Enterprise{
    private ArrayList<PHDHospitalApproval> hospitalApprovalArrayList;

    public PHDEnterprise(String name)
    {
        super(name, Enterprise.EnterpriseType.PHD);
        hospitalApprovalArrayList = new ArrayList<>();
    }

    public ArrayList<PHDHospitalApproval> getHospitalApprovalArrayList() {
        return hospitalApprovalArrayList;
    }

    public void setHospitalApprovalArrayList(ArrayList<PHDHospitalApproval> hospitalApprovalArrayList) {
        this.hospitalApprovalArrayList = hospitalApprovalArrayList;
    }
    
    

    @Override
    public ArrayList<Role> getSupportedRole() {
        ArrayList<Role> roles = new ArrayList();
        roles.add(new PHDAdminRole());
        roles.add(new PHDManagerRole());
        return roles;
    }
    
}
