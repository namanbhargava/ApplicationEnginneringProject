/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.Enterprise;


import Business.Role.DistributorCenterAdminRole;
import Business.Role.Role;
import Business.Role.SupplierManagerRole;
import java.util.ArrayList;

/**
 *
 * @author Deepak_Chandwani; Naman_Bhargava; Sneha_Shree
 */

public class DistributorEnterprise extends Enterprise{
    public DistributorEnterprise(String name) {
        super(name, Enterprise.EnterpriseType.Distributor);
    }

    @Override
    public ArrayList<Role> getSupportedRole() {
        ArrayList<Role> roles = new ArrayList();
        roles.add(new DistributorCenterAdminRole());
        roles.add(new SupplierManagerRole());
        return roles;
    }
    
}