/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.Organization;

import Business.Role.PHDManagerRole;
import Business.Role.Role;
import java.util.ArrayList;


/**
 *
 * @author Deepak_Chandwani; Naman_Bhargava; Sneha_Shree
 */


public class PHDOrderOrganization extends Organization{


    PHDOrderOrganization() {
        super(Organization.Type.PHDOrderOrganization.getValue());
    }
    @Override
    public ArrayList<Role> getSupportedRole() {
        ArrayList<Role> roles = new ArrayList<>();
         roles.add(new PHDManagerRole());
         return roles;
    }
    
}