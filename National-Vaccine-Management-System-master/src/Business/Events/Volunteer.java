/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.Events;

import Business.Employee.Employee;
import Business.Enterprise.HospitalEnterprise;
import Business.Network.Network;
import Business.Organization.Organization;

/**
 *
 * @author erdee
 */
public class Volunteer {
    private int volunteerID;
    private Employee employee;
    private Network network;
    private HospitalEnterprise enterprise;
    private Organization organisation;

    public Volunteer() {
        employee = new Employee();
    }

    public int getVolunteerID() {
        return volunteerID;
    }

    public void setVolunteerID(int volunteerID) {
        this.volunteerID = volunteerID;
    }


    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Network getNetwork() {
        return network;
    }

    public void setNetwork(Network network) {
        this.network = network;
    }

    public HospitalEnterprise getEnterprise() {
        return enterprise;
    }

    public void setEnterprise(HospitalEnterprise enterprise) {
        this.enterprise = enterprise;
    }

    public Organization getOrganisation() {
        return organisation;
    }

    public void setOrganisation(Organization organisation) {
        this.organisation = organisation;
    }
    
    
    
    
}
