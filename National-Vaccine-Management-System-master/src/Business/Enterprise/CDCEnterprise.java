/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.Enterprise;

import Business.Role.CDCAdminRole;
import Business.Role.CDCManagerRole;
import Business.Role.Role;
import java.util.ArrayList;

/**
 *
 * @author Deepak_Chandwani; Naman_Bhargava; Sneha_Shree
 */
public class CDCEnterprise extends Enterprise {
    
    public CDCEnterprise(String name)
    {
        super(name, EnterpriseType.CDC);
    }

    @Override
    public ArrayList<Role> getSupportedRole() {
        ArrayList<Role> roles = new ArrayList();
        roles.add(new CDCAdminRole());
        roles.add(new CDCManagerRole());
        return roles;
    }
    
}
