/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.Customers;

/**
 *
 * @author Deepak_Chandwani; Naman_Bhargava; Sneha_Shree
 */
public class Customer {
    
    String name;
    boolean insuarance;

    public Customer() {
        //setInsuarance(false);
    }
    

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isInsuarance() {
        return insuarance;
    }

    public void setInsuarance(boolean insuarance) {
        this.insuarance = insuarance;
    }
    
}
