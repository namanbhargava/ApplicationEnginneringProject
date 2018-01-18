/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.HospitalList;

import java.util.ArrayList;

/**
 *
 * @author bharg
 */
public class HospitalDirectory {
    
    ArrayList<Hospital> hList;

    public ArrayList<Hospital> gethList() {
        return hList;
    }

    public void sethList(ArrayList<Hospital> hList) {
        this.hList = hList;
    }

    public HospitalDirectory() {
        hList = new ArrayList();
    }
    
    public Hospital addHospital()
    {
        Hospital hos = new Hospital();
        hList.add(hos);
        return hos;
    }
}
