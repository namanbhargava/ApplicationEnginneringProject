/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.Events;

import Business.Patient.Patient;
import Business.vaccine.Vaccine;
import java.util.ArrayList;

/**
 *
 * @author erdee
 */
public class EventManagement {
    
    private ArrayList<Volunteer> volunteerArrayList;
    private ArrayList<Patient> patientArrayList;

    public EventManagement() {
        volunteerArrayList = new ArrayList<>();
        patientArrayList = new ArrayList<>();
        
    }

    public ArrayList<Volunteer> getVolunteerArrayList() {
        return volunteerArrayList;
    }

    public void setVolunteerArrayList(ArrayList<Volunteer> volunteerArrayList) {
        this.volunteerArrayList = volunteerArrayList;
    }

    public ArrayList<Patient> getPatientArrayList() {
        return patientArrayList;
    }

    public void setPatientArrayList(ArrayList<Patient> patientArrayList) {
        this.patientArrayList = patientArrayList;
    }
    
    
    
    
    
    
}
