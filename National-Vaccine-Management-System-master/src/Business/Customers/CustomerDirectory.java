/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.Customers;

import java.util.ArrayList;

/**
 *
 * @author Deepak_Chandwani; Naman_Bhargava; Sneha_Shree
 */
public class CustomerDirectory {

    ArrayList<Customer> customerList;

    public CustomerDirectory() {
        customerList = new ArrayList();
    }
    
    public ArrayList<Customer> getCustomerList() {
        return customerList;
    }

    public void setCustomerList(ArrayList<Customer> customerList) {
        this.customerList = customerList;
    }
    
    public Customer addCustomer()
    {
        Customer cus = new Customer();
        customerList.add(cus);
        return cus;
    }
    
    
    
    
    
}
