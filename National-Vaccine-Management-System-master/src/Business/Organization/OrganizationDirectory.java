/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.Organization;

import Business.Organization.Organization.Type;
import java.util.ArrayList;


/**
 *
 * @author Deepak_Chandwani; Naman_Bhargava; Sneha_Shree
 */


public class OrganizationDirectory {
    
    private ArrayList<Organization> organizationList;

    public OrganizationDirectory() {
        organizationList = new ArrayList<>();
    }

    public ArrayList<Organization> getOrganizationList() {
        return organizationList;
    }
    
    public Organization createOrganization(Type type){
        Organization organization = null;
        
        if (type.getValue().equals(Type.CDCOrderOrganization.getValue())){
            organization = new CDCOrderOrganization();
            organizationList.add(organization);
        }
        if (type.getValue().equals(Type.ClinicOrganization.getValue())){
            organization = new ClinicOrganization();
            organizationList.add(organization);
        }
        if (type.getValue().equals(Type.DistributionCenter.getValue())){
            organization = new DistributionCenterOrganization();
            organizationList.add(organization);
        }
        if (type.getValue().equals(Type.PHDOrderOrganization.getValue())){
            organization = new PHDOrderOrganization();
            organizationList.add(organization);
        }
        if (type.getValue().equals(Type.SupplierOrganization.getValue())){
            organization = new SupplierOrganization();
            organizationList.add(organization);
        }
        if (type.getValue().equals(Type.ReceptionOrganization.getValue())){
            organization = new ReceptionOrganization();
            organizationList.add(organization);
        }
        if (type.getValue().equals(Type.PharmacyOrganization.getValue())){
            organization = new PharmacyOrganization();
            organizationList.add(organization);
        }
        if (type.getValue().equals(Type.EventOrganisation.getValue())){
            organization = new EventOrganisation();
            organizationList.add(organization);
        }        
       
        if (type.getValue().equals(Type.CDCBillingOrganization.getValue())){
            organization = new CDCBillingOrganization();
            organizationList.add(organization);
        }
         if (type.getValue().equals(Type.HospitalBillingOrganization.getValue())){
            organization = new HospitalBillingOrganization();
            organizationList.add(organization);
        }
         if (type.getValue().equals(Type.InsuaranceBillingOrganization.getValue())){
            organization = new InsuaranceBillingOrganization();
            organizationList.add(organization);
        }
         if (type.getValue().equals(Type.DistributorBillingOrganization.getValue())){
            organization = new DistributorBillingOrganization();
            organizationList.add(organization);
        }
        
        return organization;
    }
}