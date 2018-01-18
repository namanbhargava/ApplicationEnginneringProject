/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userinterface.Hospital.Billing;

import Business.EcoSystem;
import Business.Enterprise.Enterprise;
import Business.Network.Network;
import Business.Organization.HospitalBillingOrganization;
import Business.UserAccount.UserAccount;
import Business.WorkQueue.CDCBillingWorkRequest;
import Business.WorkQueue.DistributorBillingWorkRequest;
import Business.WorkQueue.InsuaranceBillingWorkRequest;
import Business.WorkQueue.PharmacyWorkRequest;
import Business.WorkQueue.WorkRequest;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author bharg
 */
public class BillingWorkAreaJPanel extends javax.swing.JPanel {

    /**
     * Creates new form BillingWorkAreaJPanel
     */
    private JPanel userProcessContainer;
    private UserAccount account;
    private HospitalBillingOrganization hospitalBillingOrganization;
    private Enterprise enterprise;
    private EcoSystem business;
    private Network network;
    

    public BillingWorkAreaJPanel(JPanel userProcessContainer, UserAccount account, HospitalBillingOrganization hospitalBillingOrganization, Enterprise enterprise, EcoSystem business, Network network) {
        initComponents();
        this.userProcessContainer = userProcessContainer;
        this.account= account;
        this.hospitalBillingOrganization = hospitalBillingOrganization;
        this.enterprise = enterprise;
        this.business = business;
        this.network = network;
        populateTbl();
        populateDistBillTbl();
    }
    
    void populateTbl()
    {
        DefaultTableModel dtm = (DefaultTableModel)tblPharmacyBills.getModel();
       dtm.setRowCount(0);
       
       for(WorkRequest wr : network.getWorkQueue().getWorkRequestList())  
       {
           if(wr instanceof CDCBillingWorkRequest){
               Object[] row = new Object[7];
               row[0] = ((CDCBillingWorkRequest) wr).getName();
               row[1] = wr.getVaccine();
               row[2] = wr;
               row[3] = ((CDCBillingWorkRequest) wr).getPrice();
               row[4] = wr.getSender();
               row[5] = wr.getReceiver();
               row[6] = wr.getMessage();
               dtm.addRow(row);
           }
           if(wr instanceof InsuaranceBillingWorkRequest){
               Object[] row = new Object[7];
               row[0] = ((InsuaranceBillingWorkRequest) wr).getName();
               row[1] = wr.getVaccine();
               row[2] = wr;
               row[3] = ((InsuaranceBillingWorkRequest) wr).getPrice();
               row[4] = wr.getSender();
               row[5] = wr.getReceiver();
               row[6] = wr.getMessage();
               dtm.addRow(row);
           }
       }
    }
    
    
    void populateDistBillTbl()
    {
        DefaultTableModel model = (DefaultTableModel)distBillsJTbl.getModel();
        model.setRowCount(0);
        
        for(WorkRequest dwr : network.getWorkQueue().getWorkRequestList())
        {
            if(dwr instanceof DistributorBillingWorkRequest)
            {
                if(dwr.getEnterpeise().getName().equals(enterprise.getName()))
                {
                    Object row[]= new Object[6];
                    row[0]= dwr.getVaccine();
                    row[1]= ((DistributorBillingWorkRequest) dwr).getQty();
                    row[2]= ((DistributorBillingWorkRequest) dwr).getPrice();
                    row[3]= dwr;
                    row[4]= dwr.getReceiver();
                    row[5]= dwr.getSender();
                    model.addRow(row);
                }
            }
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

        jScrollPane1 = new javax.swing.JScrollPane();
        tblPharmacyBills = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        distBillsJTbl = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));

        tblPharmacyBills.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        tblPharmacyBills.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Patient Name", "Vaccine", "Status", "Price", "Sender", "Receiver", "Message"
            }
        ));
        jScrollPane1.setViewportView(tblPharmacyBills);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 153, 0));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("BILLING WORK AREA");

        distBillsJTbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Vaccine", "Quantity", "Price", "Status", "Receiver", "Sender"
            }
        ));
        jScrollPane3.setViewportView(distBillsJTbl);

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel3.setText("Distributor Bills ");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel2.setText("All Billing summary");

        jButton1.setText("Assign");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Accept");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Reject");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 904, Short.MAX_VALUE)
                    .addComponent(jScrollPane3)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButton1)
                                .addGap(124, 124, 124)
                                .addComponent(jButton2)
                                .addGap(18, 18, 18)
                                .addComponent(jButton3)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addContainerGap(225, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        int selectedRow = distBillsJTbl.getSelectedRow();
        if(selectedRow<0)
        {
            JOptionPane.showMessageDialog(null, "Please select the row to assign", "Warning", JOptionPane.WARNING_MESSAGE);
        }
        else{
            DistributorBillingWorkRequest cwr = (DistributorBillingWorkRequest)distBillsJTbl.getValueAt(selectedRow,3);
            cwr.setReceiver(account);
            cwr.setStatus("Pending");
            populateDistBillTbl();
              JOptionPane.showMessageDialog(null, "Assigned successfully.", "Warning", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        int cdcSelectedRow = distBillsJTbl.getSelectedRow();
        if(cdcSelectedRow<0)
        {
            JOptionPane.showMessageDialog(null, "Please select the row to accept", "Warning", JOptionPane.WARNING_MESSAGE);
        }
        else
        {
            DistributorBillingWorkRequest cr = (DistributorBillingWorkRequest)distBillsJTbl.getValueAt(cdcSelectedRow, 3);
            cr.setStatus("Bill Paid");
            populateDistBillTbl();
              JOptionPane.showMessageDialog(null, "Payment done!", "Warning", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        int cdcSelectedRow = distBillsJTbl.getSelectedRow();
        if(cdcSelectedRow<0)
        {
            JOptionPane.showMessageDialog(null, "Please select the row to accept", "Warning", JOptionPane.WARNING_MESSAGE);
        }
        else
        {
            DistributorBillingWorkRequest cr = (DistributorBillingWorkRequest)distBillsJTbl.getValueAt(cdcSelectedRow, 3);
            cr.setStatus("Payment Rejected");
            populateDistBillTbl();
              JOptionPane.showMessageDialog(null, "Payment has been rejected!", "Warning", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jButton3ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable distBillsJTbl;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable tblPharmacyBills;
    // End of variables declaration//GEN-END:variables
}
