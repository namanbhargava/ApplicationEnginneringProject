package Business;

import Business.Customers.Customer;
import Business.Disease.Disease;
import Business.Employee.Employee;
import Business.Events.Events;
import Business.Network.Network;
import Business.Role.SystemAdminRole;
import Business.UserAccount.UserAccount;
import Business.vaccine.Vaccine;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import Business.Role.SystemAdminRole;
import Business.UserAccount.UserAccount;
import Business.vaccine.Vaccine;

/**
 *
 * @author Deepak_Chandwani; Naman_Bhargava; Sneha_Shree
 */
public class ConfigureASystem {
    
    public static EcoSystem configure(){
        
        EcoSystem system = EcoSystem.getInstance();
        
        //Create a network
        //create an enterprise
        //initialize some organizations
        //have some employees 
        //create user account
        
        
        Employee employee = system.getEmployeeDirectory().createEmployee("RRH");
        
        UserAccount ua = system.getUserAccountDirectory().createUserAccount("sysadmin", "sysadmin", employee, new SystemAdminRole());
        
//        Disease d = system.getDiseaseDirectory().addDisease();
//        d.setDiseaseName("Flu");
//        
//        Vaccine v = system.getVaccineDirectory().addVaccine();
//        v.setDisease(d);
//        v.setQuantity(30);
//        v.setVaccineName("Flu vaccine");
        Customer cus1 = system.getCustomerDirectory().addCustomer();
        cus1.setName("Deepak");
        cus1.setInsuarance(true);
        Customer cus2 = system.getCustomerDirectory().addCustomer();
        cus2.setName("Sneha");
        cus2.setInsuarance(false);
        
            
        
        return system;
        
    }
    
}
