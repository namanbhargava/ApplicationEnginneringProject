/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.HospitalList;

/**
 *
 * @author bharg
 */
public class Hospital {
    
    String name;
    int requestedQty;
    int usedQty;
    float vaccineUsage;
    float vaccineWaste;
    
    public float getVaccineWaste() {
        return vaccineWaste;
    }

    public void setVaccineWaste(float vaccineWaste) {
        this.vaccineWaste = vaccineWaste;
    }
    

    public float getVaccineUsage() {
        return vaccineUsage;
    }

    public void setVaccineUsage(float vaccineUsage) {
        this.vaccineUsage = vaccineUsage;
    }
    

    public int getUsedQty() {
        return usedQty;
    }

    public void setUsedQty(int usedQty) {
        this.usedQty = usedQty;
    }

    public int getRequestedQty() {
        return requestedQty;
    }

    public void setRequestedQty(int requestedQty) {
        this.requestedQty = requestedQty;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

   
}
