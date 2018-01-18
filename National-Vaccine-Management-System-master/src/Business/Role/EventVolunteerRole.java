/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.Role;

import Business.EcoSystem;
import Business.Enterprise.Enterprise;
import Business.Network.Network;
import Business.Organization.Organization;
import Business.Organization.EventOrganisation;
import Business.UserAccount.UserAccount;
import javax.swing.JPanel;
import userinterface.Hospital.Event.EventVolunteerWorkAreaJPanel;


/**
 *
 * @author Deepak_Chandwani; Naman_Bhargava; Sneha_Shree
 */


public class EventVolunteerRole extends Role{

    @Override
    public JPanel createWorkArea(JPanel userProcessContainer, UserAccount account, Organization organization, Enterprise enterprise, EcoSystem business,Network network) {
        return new EventVolunteerWorkAreaJPanel(userProcessContainer, account, (EventOrganisation)organization, enterprise, business, network);
    }
    
}
