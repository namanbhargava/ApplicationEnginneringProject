/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.SubOrganization;

import Business.Employee.EmployeeDirectory;
import Business.Organization.EventOrganisation;
import Business.Organization.Organization;
import Business.UserAccount.UserAccountDirectory;
import Business.WorkQueue.WorkQueue;
import java.util.ArrayList;

/**
 *
 * @author erdee
 */
public class SubOrganizationDirectory {
    
    private ArrayList<SubOrganization> subOrganizationList;

    public SubOrganizationDirectory() {
        subOrganizationList = new ArrayList<>();        
    }

    public ArrayList<SubOrganization> getOrganizationList() {
        return subOrganizationList;
    }
    
    public SubOrganization createSubOrganization(SubOrganization.TypeSubOrg type){
        SubOrganization organization = null;        
        
        if (type.getValue().equals(SubOrganization.TypeSubOrg.EventClinic.getValue())){
            organization = new EventClinicOrganization();
            subOrganizationList.add(organization);
        }        
        return organization;
    }

    
    
}
