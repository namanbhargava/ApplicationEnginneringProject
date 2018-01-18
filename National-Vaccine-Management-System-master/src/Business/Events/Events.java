/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.Events;

import Business.Enterprise.HospitalEnterprise;
import Business.Inventory.RequestedVaccineQty;
import Business.vaccine.Vaccine;
import com.db4o.collections.ActivatableArrayList;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author bharg
 */
public class Events {
    
    private String name;
    private Date startDate;
    private int capacity;
    private EventManagement eventManagement;
    private HospitalEnterprise hospitalEnterprise;
    private ArrayList<RequestedVaccineQty> vaccineInventoryArrayList;
    private String Status;

    public Events() {
        vaccineInventoryArrayList = new ArrayList<>();
        setStatus("Event Not Started");
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }
    
    
    
    

    public HospitalEnterprise getHospitalEnterprise() {
        return hospitalEnterprise;
    }

    public void setHospitalEnterprise(HospitalEnterprise hospitalEnterprise) {
        this.hospitalEnterprise = hospitalEnterprise;
    }

    public ArrayList<RequestedVaccineQty> getVaccineInventoryArrayList() {
        return vaccineInventoryArrayList;
    }

    public void setVaccineInventoryArrayList(ArrayList<RequestedVaccineQty> vaccineInventoryArrayList) {
        this.vaccineInventoryArrayList = vaccineInventoryArrayList;
    } 
    
    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    } 
    
    public EventManagement getEventManagement() {
        return eventManagement;
    }
    
    public void setEventManagement(EventManagement eventManagement) {
        this.eventManagement = eventManagement;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    
    @Override
    public String toString()
    {
        return name;
      
    }
    
    
}
