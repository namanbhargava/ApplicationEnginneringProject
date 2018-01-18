/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.Organization;

import Business.Employee.EmployeeDirectory;
import Business.Role.Role;
import Business.SubOrganization.SubOrganizationDirectory;
import Business.UserAccount.UserAccountDirectory;
import Business.WorkQueue.WorkQueue;
import java.util.ArrayList;


/**
 *
 * @author Deepak_Chandwani; Naman_Bhargava; Sneha_Shree
 */


public abstract class Organization {

    private String name;
    private WorkQueue workQueue;
    private EmployeeDirectory employeeDirectory;
    private UserAccountDirectory userAccountDirectory;
    private int organizationID;
    private static int counter;
    //private SubOrganizationDirectory subOrganizationDirectory;
    
    public enum Type{
        Admin("Admin"),
        CDCOrderOrganization("CDCOrder Organization"),
        ClinicOrganization("Clinic Organization"),
        DistributionCenter("DistributionCenter Organization"),
        PHDOrderOrganization("PHDOrder Organization"),
        SupplierOrganization("Supplier Organization"),
        EventOrganisation("Events Organisation"),
        ReceptionOrganization("Reception Organization"),
        PharmacyOrganization("Pharmacy Organization"),
        CDCBillingOrganization("CDC Billing Organization"),
        HospitalBillingOrganization("Hospital Billing Organization"),
        InsuaranceBillingOrganization("InsuaranceBillingOrganization"),
        DistributorBillingOrganization("DistributorBillingOrganization");
        
        private String value;
        private Type(String value) {
            this.value = value;
        }
        public String getValue() {
            return value;
        }
    }

//    public SubOrganizationDirectory getSubOrganizationDirectory() {
//        return subOrganizationDirectory;
//    }
//
//    public void setSubOrganizationDirectory(SubOrganizationDirectory subOrganizationDirectory) {
//        this.subOrganizationDirectory = subOrganizationDirectory;
//    }
    
    

    public Organization(String name) {
        this.name = name;
        workQueue = new WorkQueue();
        employeeDirectory = new EmployeeDirectory();
        userAccountDirectory = new UserAccountDirectory();
        organizationID = counter;
        ++counter;
    }

    public abstract ArrayList<Role> getSupportedRole();
    
    public UserAccountDirectory getUserAccountDirectory() {
        return userAccountDirectory;
    }

    public int getOrganizationID() {
        return organizationID;
    }

    public EmployeeDirectory getEmployeeDirectory() {
        return employeeDirectory;
    }
    
    public String getName() {
        return name;
    }

    public WorkQueue getWorkQueue() {
        return workQueue;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWorkQueue(WorkQueue workQueue) {
        this.workQueue = workQueue;
    }

    @Override
    public String toString() {
        return name;
    }
    
    
}
