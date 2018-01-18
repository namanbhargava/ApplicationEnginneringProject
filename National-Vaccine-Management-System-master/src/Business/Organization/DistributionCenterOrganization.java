/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.Organization;

import Business.DistributorCenter.DistributorCenter;
import Business.Role.DistributorCenterAdminRole;
import Business.Role.Role;
import java.util.ArrayList;


/**
 *
 * @author Deepak_Chandwani; Naman_Bhargava; Sneha_Shree
 */


public class DistributionCenterOrganization extends Organization{

    private DistributorCenter distCenter;
            
    public DistributionCenterOrganization() {
        super(Organization.Type.DistributionCenter.getValue());
        distCenter = new DistributorCenter();
    }
    
    public DistributorCenter getDistCenter() {
        return distCenter;
    }

    public void setDistCenter(DistributorCenter distCenter) {
        this.distCenter = distCenter;
    }

    @Override
    public ArrayList<Role> getSupportedRole() {
        ArrayList<Role> roles = new ArrayList<>();
         roles.add(new DistributorCenterAdminRole());
         return roles;
    }
    
}