/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.Enterprise;

import Business.Role.ClinicAdminRole;
import Business.Role.HospitalAdminRole;
import Business.Role.Role;
import java.util.ArrayList;


/**
 *
 * @author Deepak_Chandwani; Naman_Bhargava; Sneha_Shree
 */


public class HospitalEnterprise extends Enterprise{
    private boolean hospitalApproved;

    public HospitalEnterprise(String name) {
        super(name, EnterpriseType.Hospital);
    }

    public boolean isHospitalApproved() {
        return hospitalApproved;
    }

    public void setHospitalApproved(boolean hospitalApproved) {
        this.hospitalApproved = hospitalApproved;
    }
    
    

    @Override
    public ArrayList<Role> getSupportedRole() {
        ArrayList<Role> roles = new ArrayList();
        roles.add(new HospitalAdminRole());
        return roles;
    }
    
    
    
}
