/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.Organization;

import Business.Role.EventAdminRole;
import Business.Role.Role;
import Business.SubOrganization.SubOrganizationDirectory;
import java.util.ArrayList;


/**
 *
 * @author Deepak_Chandwani; Naman_Bhargava; Sneha_Shree
 */


public class EventOrganisation extends Organization{
    private SubOrganizationDirectory eventSubOrganizationDirectory;
    
    public EventOrganisation(){
         super(Organization.Type.EventOrganisation.getValue());
         //eventSubOrganizationDirectory.getOrganizationList().add(new )
         eventSubOrganizationDirectory = new SubOrganizationDirectory();
         setEventSubOrganizationDirectory(eventSubOrganizationDirectory);
    }

    public SubOrganizationDirectory getEventSubOrganizationDirectory() {
        return eventSubOrganizationDirectory;
    }

    public void setEventSubOrganizationDirectory(SubOrganizationDirectory eventSubOrganizationDirectory) {
        this.eventSubOrganizationDirectory = eventSubOrganizationDirectory;
    }
    
    
    @Override
    public ArrayList<Role> getSupportedRole() {
        ArrayList<Role> roles = new ArrayList();
        roles.add(new EventAdminRole());
        return roles;
    }
    
}
