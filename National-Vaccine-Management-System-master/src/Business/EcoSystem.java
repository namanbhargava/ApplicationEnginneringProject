package Business;

import Business.Customers.CustomerDirectory;
import Business.Disease.DiseaseDirectory;
import Business.DistributorCenter.DistributorCenter;
import Business.Network.Network;
import Business.Organization.Organization;
import Business.Role.Role;
import Business.Role.SystemAdminRole;
import Business.vaccine.VaccineDirectory;
import java.util.ArrayList;

/**
 *
 * @author Deepak_Chandwani; Naman_Bhargava; Sneha_Shree
 */
public class EcoSystem extends Organization {

    private static EcoSystem business;
    private ArrayList<Network> networkList;
    
    private VaccineDirectory vaccineDirectory;
    private DiseaseDirectory diseaseDirectory;
    private CustomerDirectory customerDirectory;
    public CustomerDirectory getCustomerDirectory() {
        return customerDirectory;
    }

    public void setCustomerDirectory(CustomerDirectory customerDirectory) {
        this.customerDirectory = customerDirectory;
    }
   
    public DistributorCenter getDistCenter() {
        return distCenter;
    }

    public void setDistCenter(DistributorCenter distCenter) {
        this.distCenter = distCenter;
    }
    private DistributorCenter distCenter;

    public VaccineDirectory getVaccineDirectory() {
        return vaccineDirectory;
    }

    public void setVaccineDirectory(VaccineDirectory vaccineDirectory) {
        this.vaccineDirectory = vaccineDirectory;
    }

    public DiseaseDirectory getDiseaseDirectory() {
        return diseaseDirectory;
    }

    public void setDiseaseDirectory(DiseaseDirectory diseaseDirectory) {
        this.diseaseDirectory = diseaseDirectory;
    }

    public static EcoSystem getInstance() {
        if (business == null) {
            business = new EcoSystem();
        }
        return business;
    }

    private EcoSystem() {
        super(null);
        networkList = new ArrayList<>();
        vaccineDirectory = new VaccineDirectory();
        diseaseDirectory = new DiseaseDirectory();
        distCenter = new DistributorCenter();
        customerDirectory = new CustomerDirectory();
        
        vaccineDirectory= new VaccineDirectory();
        diseaseDirectory = new DiseaseDirectory();
        distCenter = new DistributorCenter();
        customerDirectory = new CustomerDirectory();
    }

    public ArrayList<Network> getNetworkList() {
        return networkList;
    }

    public Network createAndAddNetwork() {
        Network network = new Network();
        networkList.add(network);
        return network;
    }

    @Override
    public ArrayList<Role> getSupportedRole() {
        ArrayList<Role> roleList = new ArrayList<>();
        roleList.add(new SystemAdminRole());
        return roleList;
    }

    public boolean checkIfUsernameIsUnique(String username) {

        if (!this.getUserAccountDirectory().checkIfUsernameIsUnique(username)) {
            return false;
        }

       

        return true;
    }
}