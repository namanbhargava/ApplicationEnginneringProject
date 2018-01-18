/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.SubOrganization;

import Business.Organization.Organization;
import Business.Role.EventAdminRole;
import Business.Role.EventVolunteerRole;
import Business.Role.Role;
import java.util.ArrayList;


/**
 *
 * @author Deepak_Chandwani; Naman_Bhargava; Sneha_Shree
 */


public class EventClinicOrganization extends SubOrganization{   
    
    
    public EventClinicOrganization(){
         super(SubOrganization.TypeSubOrg.EventClinic.getValue());
    }

    @Override
    public ArrayList<Role> getSupportedRole() {
        ArrayList<Role> roles = new ArrayList();
        roles.add(new EventVolunteerRole());
        return roles;
    }
    
}
