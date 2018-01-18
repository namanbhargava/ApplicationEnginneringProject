package userinterface.Hospital.Pharmacy;

import Business.Customers.Customer;
import Business.Customers.CustomerDirectory;
import Business.EcoSystem;
import Business.Enterprise.Enterprise;
import Business.Enterprise.HospitalEnterprise;
import Business.Network.Network;
import Business.Organization.PharmacyOrganization;
import Business.UserAccount.UserAccount;
import Business.WorkQueue.CDCBillingWorkRequest;
import Business.WorkQueue.InsuaranceBillingWorkRequest;
import Business.WorkQueue.PharmacyWorkRequest;
import Business.WorkQueue.ReceptionWorkRequest;
import Business.WorkQueue.WorkRequest;
import Business.vaccine.Vaccine;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Deepak_Chandwani; Naman_Bhargava; Sneha_Shree
 */
public class PharmacyAdminWorkAreaJPanel extends javax.swing.JPanel {

    /**
     * Creates new form ClinicAdminWorkAreaJPanel
     */
    private JPanel userProcessContainer;
    private UserAccount account;
    private PharmacyOrganization pharmacyOrganization;
    private HospitalEnterprise hospitalEnterprise;
    private EcoSystem business;
    private Network network;
    private final int VACINE_THRESHOLD = 5;
    private final int VACINE_AUTO_ORDER_QTY = 10;
    //private Pharmacy pharmacy;
    //private VaccineInventory vaccineInventory;

    public PharmacyAdminWorkAreaJPanel(JPanel userProcessContainer, UserAccount account, PharmacyOrganization pharmacyOrganization, Enterprise enterprise, EcoSystem business, Network network) {
        initComponents();
        this.userProcessContainer = userProcessContainer;
        this.account = account;
        this.pharmacyOrganization = pharmacyOrganization;
        this.hospitalEnterprise = (HospitalEnterprise) enterprise;
        this.business = business;
        this.network = network;

        populateBox();
        populateTbl();
        populateWorkQueueTable();
        populateVaccineInventory();
        orderAutomation();
        
        
        
        txtVaccineQty.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!((c >= '0') && (c <= '9') || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE))) {
                    e.consume();
                }
            }
        });
         
    }

    public void orderAutomation() {
        boolean orderAutomtion = false, addInPWR = false;
        Vaccine v = null;
        WorkRequest wrTest = null;
        if (!pharmacyOrganization.getVaccineInventory().getVaccineInventoryArrayList().isEmpty()) {
            if (hospitalEnterprise.isHospitalApproved()) {
                for (Vaccine vaccine : pharmacyOrganization.getVaccineInventory().getVaccineInventoryArrayList()) {
                    if (vaccine.getQuantity() < VACINE_THRESHOLD) {
                        for (WorkRequest wr : pharmacyOrganization.getWorkQueue().getWorkRequestList()) {
                            if (vaccine.equals(wr.getVaccine())) {
//                                if (!((wr.getStatus().equalsIgnoreCase("Sent to PHD"))
//                                        || (wr.getStatus().equalsIgnoreCase("Pending"))
//                                        || (wr.getStatus().equalsIgnoreCase("Forwarded to CDC"))
//                                        || (wr.getStatus().equalsIgnoreCase("Forwarded to Distributor")))) {
                                wrTest = wr;
                                addInPWR = true;
                                //System.out.println("Status "+wr.getStatus());
                                v = wr.getVaccine();
                                //break;
                                //}
                            }
                        }

                        if (addInPWR) {
                            System.out.println("Status " + wrTest.getStatus());
                            if (!((wrTest.getStatus().equalsIgnoreCase("Sent to PHD"))
                                    || (wrTest.getStatus().equalsIgnoreCase("Pending"))
                                    || (wrTest.getStatus().equalsIgnoreCase("Forwarded to CDC"))
                                    || (wrTest.getStatus().equalsIgnoreCase("Forwarded to Distributor")))) {

                                PharmacyWorkRequest pwr = new PharmacyWorkRequest();
                                pwr.setVaccine(v);
                                pwr.setRequestedQty(VACINE_AUTO_ORDER_QTY);
                                pwr.setStatus("Sent to PHD");
                                pwr.setSender(account);
                                pwr.setEnterpeise(hospitalEnterprise);
                                pwr.setOrganization(pharmacyOrganization);
                                pwr.setIncludedFlag(false);

                                pharmacyOrganization.getWorkQueue().getWorkRequestList().add(pwr);
                                hospitalEnterprise.getWorkQueue().getWorkRequestList().add(pwr);
                                network.getWorkQueue().getWorkRequestList().add(pwr);
                                account.getWorkQueue().getWorkRequestList().add(pwr);
                                //business.getWorkQueue().getWorkRequestList().add(pwr);
                                populateWorkQueueTable();
                            }
                        }

                    }
                }
            } else {
                ///hospital is not approved
                JOptionPane.showMessageDialog(null, "Auto Order cannot be processed as Hospital is not approved by PHD");
            }
        } else {

        }

    }

    public void populateBox() {
        vaccineComboBox.removeAllItems();
        for (Vaccine vaccine : business.getVaccineDirectory().getVaccineList()) {
            vaccineComboBox.addItem(vaccine);
        }
    }

    void populateTbl() {

        DefaultTableModel dtm_ = (DefaultTableModel) tblRequestfromClinic.getModel();
        dtm_.setRowCount(0);
        for (WorkRequest work : hospitalEnterprise.getWorkQueue().getWorkRequestList()) {
            if (work instanceof ReceptionWorkRequest) {
                Object[] row = new Object[8];
                row[0] = ((ReceptionWorkRequest) work).getPatient().getName();
                row[1] = ((ReceptionWorkRequest) work).getPatient().getAge();
                row[2] = work;
                row[3] = work.getVaccine();
                row[4] = ((ReceptionWorkRequest) work).getPrice();
                row[5] = ((ReceptionWorkRequest) work).getRequestQuantity();
                row[6] = work.getSender();
                row[7] = work.getReceiver();
                dtm_.addRow(row);
            }
        }
    }

    public void populateVaccineInventory() {

        if (!pharmacyOrganization.getVaccineInventory().getVaccineInventoryArrayList().isEmpty()) {

//            System.out.println("isEmpty " + !pharmacyOrganization.getVaccineInventory().getVaccineInventoryArrayList().isEmpty());
//            System.out.println("size " + pharmacyOrganization.getVaccineInventory().getVaccineInventoryArrayList().size());
//            Vaccine vx = pharmacyOrganization.getVaccineInventory().getVaccineInventoryArrayList().get(0);
//            System.out.println("Vaccine "+vx);
            DefaultTableModel dtm = (DefaultTableModel) jTableVaccineInventory.getModel();
            dtm.setRowCount(0);
            for (Vaccine v : pharmacyOrganization.getVaccineInventory().getVaccineInventoryArrayList()) {
                System.out.println("Vaccine " + v);
                Object[] row = new Object[4];
                row[0] = v.getVaccineId();
                row[1] = v;
                row[2] = v.getQuantity();
                row[3] = v.getDisease();
                dtm.addRow(row);
            }
        }
    }

    public void populateWorkQueueTable() {

        DefaultTableModel dtm = (DefaultTableModel) tblWorkQueue.getModel();
        dtm.setRowCount(0);

        for (WorkRequest wr : pharmacyOrganization.getWorkQueue().getWorkRequestList()) {
            Object[] row = new Object[4];
            row[0] = wr.getVaccine().getVaccineName();
            row[1] = ((PharmacyWorkRequest) wr).getRequestedQty();
            row[2] = wr;
            row[3] = wr.getReceiver();
            dtm.addRow(row);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblWorkQueue = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtVaccineQty = new javax.swing.JTextField();
        vaccineComboBox = new javax.swing.JComboBox();
        jButtonRequest = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblRequestfromClinic = new javax.swing.JTable();
        jButtonAssignToMe = new javax.swing.JButton();
        jButtonAccept = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTableVaccineInventory = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        txtPinCode = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 153, 0));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Pharmacy ");

        tblWorkQueue.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        tblWorkQueue.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Vaccine Name", "Qty", "Status", "Receiver"
            }
        ));
        jScrollPane1.setViewportView(tblWorkQueue);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 153, 0));
        jLabel2.setText("Vaccine:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 153, 0));
        jLabel3.setText("Qty:");

        txtVaccineQty.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        txtVaccineQty.setForeground(new java.awt.Color(255, 153, 0));

        vaccineComboBox.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        vaccineComboBox.setForeground(new java.awt.Color(255, 153, 0));
        vaccineComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jButtonRequest.setBackground(new java.awt.Color(255, 153, 0));
        jButtonRequest.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jButtonRequest.setForeground(new java.awt.Color(255, 255, 255));
        jButtonRequest.setText("Request");
        jButtonRequest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRequestActionPerformed(evt);
            }
        });

        tblRequestfromClinic.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        tblRequestfromClinic.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Patient Name", "Patient Age", "Status", "Vaccine", "Price", "Qty", "Sender", "Receiver"
            }
        ));
        tblRequestfromClinic.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(tblRequestfromClinic);

        jButtonAssignToMe.setBackground(new java.awt.Color(255, 153, 0));
        jButtonAssignToMe.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jButtonAssignToMe.setForeground(new java.awt.Color(255, 255, 255));
        jButtonAssignToMe.setText("Assign");
        jButtonAssignToMe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAssignToMeActionPerformed(evt);
            }
        });

        jButtonAccept.setBackground(new java.awt.Color(255, 153, 0));
        jButtonAccept.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jButtonAccept.setForeground(new java.awt.Color(255, 255, 255));
        jButtonAccept.setText("Accept");
        jButtonAccept.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAcceptActionPerformed(evt);
            }
        });

        jTableVaccineInventory.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jTableVaccineInventory.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Vaccine ID", "Vaccine Name", "Vaccine Quantity", "Disease"
            }
        ));
        jTableVaccineInventory.getTableHeader().setReorderingAllowed(false);
        jScrollPane3.setViewportView(jTableVaccineInventory);

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 153, 0));
        jLabel4.setText("PinCode:");

        txtPinCode.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        txtPinCode.setForeground(new java.awt.Color(255, 153, 0));

        jLabel5.setForeground(new java.awt.Color(255, 153, 0));
        jLabel5.setText("Vaccine Request");

        jLabel6.setForeground(new java.awt.Color(255, 153, 0));
        jLabel6.setText("Vaccine Inventory");

        jLabel7.setForeground(new java.awt.Color(255, 153, 0));
        jLabel7.setText("Patient Details");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jScrollPane1)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel2)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(vaccineComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(jLabel3)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtVaccineQty, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jLabel4)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(txtPinCode, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButtonRequest, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(7, 7, Short.MAX_VALUE)
                                .addComponent(jButtonAssignToMe, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButtonAccept, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane3)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonAssignToMe)
                    .addComponent(jButtonAccept)
                    .addComponent(vaccineComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(txtVaccineQty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(jButtonRequest)
                    .addComponent(jLabel4)
                    .addComponent(txtPinCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 323, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(98, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonRequestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRequestActionPerformed
        // TODO add your handling code here:
        
        if (txtVaccineQty.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Please enter Quantity.");
        } else {

            if (hospitalEnterprise.isHospitalApproved()) {
                PharmacyWorkRequest pwr = new PharmacyWorkRequest();
                pwr.setVaccine((Vaccine) vaccineComboBox.getSelectedItem());
                pwr.setRequestedQty(Integer.parseInt(txtVaccineQty.getText()));
                pwr.setStatus("Sent to PHD");
                pwr.setSender(account);
                pwr.setEnterpeise(hospitalEnterprise);
                pwr.setOrganization(pharmacyOrganization);
                pwr.setIncludedFlag(false);
                pwr.setEnterpriseName(hospitalEnterprise.getName());
                pwr.setPincode(txtPinCode.getText());

                pharmacyOrganization.getWorkQueue().getWorkRequestList().add(pwr);
                hospitalEnterprise.getWorkQueue().getWorkRequestList().add(pwr);
                network.getWorkQueue().getWorkRequestList().add(pwr);
                account.getWorkQueue().getWorkRequestList().add(pwr);
                //business.getWorkQueue().getWorkRequestList().add(pwr);
                populateWorkQueueTable();
                txtVaccineQty.setText("");
                  JOptionPane.showMessageDialog(null, "Request created successfully.", "Warning", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Order cannot be processed as Hospital is not approved by PHD");
            }
        }

    }//GEN-LAST:event_jButtonRequestActionPerformed

    private void jButtonAssignToMeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAssignToMeActionPerformed
        // TODO add your handling code here:

        int selectedRow = tblRequestfromClinic.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(null, "Please select the row to assign Pharmacy.", "Warning", JOptionPane.WARNING_MESSAGE);
        } else {
            ReceptionWorkRequest cwr = (ReceptionWorkRequest) tblRequestfromClinic.getValueAt(selectedRow, 2);
            cwr.setReceiver(account);
            cwr.setStatus("Pending");
            populateTbl();
              JOptionPane.showMessageDialog(null, "Request Assigned successfully.", "Warning", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jButtonAssignToMeActionPerformed

    private void jButtonAcceptActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAcceptActionPerformed
        // TODO add your handling code here:
        int selectedRow = tblRequestfromClinic.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(null, "Please select the row to Accept the Pharmarcy.", "Warning", JOptionPane.WARNING_MESSAGE);
        } else {
            ReceptionWorkRequest rwr = (ReceptionWorkRequest) tblRequestfromClinic.getValueAt(selectedRow, 2);
            Vaccine vaccine = rwr.getVaccine();
            boolean requestedQtyAvailable = false;
            if (!pharmacyOrganization.getVaccineInventory().getVaccineInventoryArrayList().isEmpty()) {
                for (Vaccine v : pharmacyOrganization.getVaccineInventory().getVaccineInventoryArrayList()) {
                    if (vaccine.equals(v)) {
                        if (v.getQuantity() > rwr.getRequestQuantity()) {
                            requestedQtyAvailable = true;
                            v.setQuantity(v.getQuantity() - rwr.getRequestQuantity());
                            rwr.setStatus("Complete");
                            if (rwr.getStatus().equals("Complete")) {
                                System.out.println("is Complete");
                                CustomerDirectory cusDir = business.getCustomerDirectory();
                                for (Customer cus : cusDir.getCustomerList()) {
                                    System.out.println(cus.getName());
                                    if ((cus.getName()).equals(rwr.getPatient().getName())) {
                                        if (cus.isInsuarance()) {
                                            System.out.println("He is insured!");
                                            InsuaranceBillingWorkRequest ibwr = new InsuaranceBillingWorkRequest();
                                            ibwr.setVaccine(rwr.getVaccine());
                                            ibwr.setName(rwr.getPatient().getName());
                                            ibwr.setSender(account);
                                            ibwr.setStatus("Sent to Insuarance");
                                            ibwr.setMessage("Patient is insured");
                                            ibwr.setPrice(rwr.getRequestQuantity()*(rwr.getVaccine().getPrice()));
                                            hospitalEnterprise.getWorkQueue().getWorkRequestList().add(ibwr);
                                            network.getWorkQueue().getWorkRequestList().add(ibwr);
                                        } else {
                                            System.out.println("Not insured");
                                            CDCBillingWorkRequest cbwr = new CDCBillingWorkRequest();
                                            cbwr.setVaccine(rwr.getVaccine());
                                            cbwr.setName(rwr.getPatient().getName());
                                            cbwr.setSender(account);
                                            cbwr.setStatus("Sent to CDC");
                                            cbwr.setMessage("Patient is not insured");
                                            cbwr.setPrice(rwr.getRequestQuantity()*(rwr.getVaccine().getPrice()));
                                            hospitalEnterprise.getWorkQueue().getWorkRequestList().add(cbwr);
                                            network.getWorkQueue().getWorkRequestList().add(cbwr);
                                            //cdc work queue
                                        }
                                    }
                                }
                            }
                            populateVaccineInventory();// update Inventory
                            populateTbl();//
                            
                              JOptionPane.showMessageDialog(null, "Request Accepted  successfully.", "Warning", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            break;
                        }
                    }
                }
                if (!requestedQtyAvailable) {
                    JOptionPane.showMessageDialog(null, "Requested qty is greater than available in inventory");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Inventory is Empty, request your order to PHD");
            }
        }
    }//GEN-LAST:event_jButtonAcceptActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAccept;
    private javax.swing.JButton jButtonAssignToMe;
    private javax.swing.JButton jButtonRequest;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTableVaccineInventory;
    private javax.swing.JTable tblRequestfromClinic;
    private javax.swing.JTable tblWorkQueue;
    private javax.swing.JTextField txtPinCode;
    private javax.swing.JTextField txtVaccineQty;
    private javax.swing.JComboBox vaccineComboBox;
    // End of variables declaration//GEN-END:variables
}
