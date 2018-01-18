/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userinterface.Distributor.DistributionCenter;

import Business.DistributorCenter.DistributorCenter;
import Business.EcoSystem;
import Business.Enterprise.Enterprise;
import Business.Events.Events;
import Business.Inventory.RequestedVaccineQty;
import Business.Network.Network;
import Business.Organization.DistributionCenterOrganization;
import Business.Organization.Organization;
import Business.Organization.PharmacyOrganization;
import Business.Organization.SupplierOrganization;
import Business.Supplier.Supplier;
import Business.UserAccount.UserAccount;
import Business.WorkQueue.CDCEventInventoryWorkRequest;
import Business.WorkQueue.DistributorBillingWorkRequest;
import Business.WorkQueue.DistributorWorkRequest;
import Business.WorkQueue.PharmacyWorkRequest;
import Business.WorkQueue.WorkQueue;
import Business.WorkQueue.WorkRequest;
import Business.vaccine.Vaccine;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

import java.net.URLConnection;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPathConstants;
import org.w3c.dom.Document;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author Deepak_Chandwani; Naman_Bhargava; Sneha_Shree
 */
public class DistributorCenterAdminWorkAreaJPanel extends javax.swing.JPanel {

    /**
     * Creates new form ClinicAdminWorkAreaJPanel
     */
    private JPanel userProcessContainer;
    private UserAccount account;
    private DistributionCenterOrganization distributionCenterOrganization;
    private Enterprise enterprise;
    private EcoSystem business;
    private DistributorCenter dc;
    private Network network;

    public DistributorCenterAdminWorkAreaJPanel(JPanel userProcessContainer, UserAccount account, DistributionCenterOrganization distributionCenterOrganization, Enterprise enterprise, EcoSystem business, Network network) {
        initComponents();
        this.userProcessContainer = userProcessContainer;
        this.account = account;
        this.distributionCenterOrganization = distributionCenterOrganization;
        this.enterprise = enterprise;
        this.business = business;
        this.network = network;
        dc = distributionCenterOrganization.getDistCenter();

        System.out.println("busi" + business.getWorkQueue().getWorkRequestList().size());

        if (dc.getWorkQueue() == null) {
            WorkQueue w = new WorkQueue();
            dc.setWorkQueue(w);
        }
        populatetbl();
        populateUpdatedInventory();
        populateInventory();
        populateComboBox();
        populateSupCombo();
        populateDistRequestTbl();

        txtQty.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!((c >= '0') && (c <= '9') || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE))) {
                    e.consume();
                }
            }
        });

    }

    void populatetbl() {
        DefaultTableModel dtm = (DefaultTableModel) distJTbl.getModel();
        dtm.setRowCount(0);
        for (WorkRequest wr : network.getWorkQueue().getWorkRequestList()) {
            if (wr instanceof PharmacyWorkRequest || wr instanceof CDCEventInventoryWorkRequest) {
                Object[] row = new Object[5];
                row[0] = wr.getVaccine().getVaccineName();
                if (wr instanceof PharmacyWorkRequest) {
                    row[1] = ((PharmacyWorkRequest) wr).getRequestedQty();
                } else if (wr instanceof CDCEventInventoryWorkRequest) {
                    row[1] = ((CDCEventInventoryWorkRequest) wr).getRequestedQty();
                }
                row[2] = wr;
                row[3] = wr.getReceiver();
                row[4] = wr.getSender();
                dtm.addRow(row);
            }
        }
    }

    public void populateSupCombo() {
        supCombo.removeAllItems();
        for (Organization organization1 : enterprise.getOrganizationDirectory().getOrganizationList()) {
            System.out.println(organization1);
            if (organization1 instanceof SupplierOrganization) {
                SupplierOrganization s = (SupplierOrganization) organization1;
                for (Supplier supplier : s.getSupplierList().getSupplierList()) {
                    supCombo.addItem(supplier);
                }
            }
        }
    }

    void populateDistRequestTbl() {

        DefaultTableModel model1 = (DefaultTableModel) distRequestTbl.getModel();
        model1.setRowCount(0);

        for (WorkRequest distReq : business.getWorkQueue().getWorkRequestList()) {
            if (distReq instanceof DistributorWorkRequest) {
                Object[] row = new Object[5];
                row[0] = distReq.getVaccine().getVaccineName();
                row[1] = ((DistributorWorkRequest) distReq).getRequestQuantity();
                row[2] = distReq;
                row[3] = ((DistributorWorkRequest) distReq).getSupplier().getSupplierName();
                row[4] = distReq.getSender();
                model1.addRow(row);
            }
        }

    }

    void populateInventory() {

        DefaultTableModel dtm2 = (DefaultTableModel) availVaccineJTbl.getModel();
        dtm2.setRowCount(0);

        try {
            for (Vaccine vac : dc.getDistVaccineDirectory().getVaccineList()) {
                Object[] rowVac = new Object[2];
                rowVac[0] = vac.getVaccineName();
                rowVac[1] = vac.getQuantity();
                dtm2.addRow(rowVac);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No stocks in the inventory");
        }

    }

    public void populateUpdatedInventory() {
        boolean foundUpdated = false;
        for (WorkRequest workRequest : dc.getWorkQueue().getWorkRequestList()) {
            System.out.println(workRequest.getStatus());
            DistributorWorkRequest p = (DistributorWorkRequest) workRequest;
            if (workRequest instanceof DistributorWorkRequest) {
                System.out.println(workRequest.getStatus());

                if (workRequest.getStatus().equals("Complete") && !workRequest.isIncludedFlag()) {
                    Vaccine v = workRequest.getVaccine();

                    for (Vaccine vaccine : dc.getDistVaccineDirectory().getVaccineList()) {
                        if (v.getVaccineName().equals(vaccine.getVaccineName())) {
                            vaccine.setQuantity(p.getRequestQuantity() + vaccine.getQuantity());
                            foundUpdated = true;
                        } else {
                            foundUpdated = false;
                        }
                    }
                    if (!foundUpdated) {
                        Vaccine vac = dc.getDistVaccineDirectory().addVaccine();
                        vac.setVaccineName(v.getVaccineName());
                        vac.setQuantity(p.getRequestQuantity());
                        vac.setDisease(v.getDisease());
                    }

                }
            }
            workRequest.setIncludedFlag(true);// count once
        }

    }

    void populateComboBox() {

        vaccineCombo.removeAllItems();
        for (Vaccine vacCombo : business.getVaccineDirectory().getVaccineList()) {
            vaccineCombo.addItem(vacCombo);
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
        distJTbl = new javax.swing.JTable();
        btnAssign = new javax.swing.JButton();
        btnAccept = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        availVaccineJTbl = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        distRequestTbl = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        vaccineCombo = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        txtQty = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        supCombo = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 153, 0));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Distribution Center Work Area");

        distJTbl.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        distJTbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Vaccine Name", "Qty", "Status", "Receiver", "Sender"
            }
        ));
        jScrollPane1.setViewportView(distJTbl);

        btnAssign.setBackground(new java.awt.Color(255, 153, 0));
        btnAssign.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        btnAssign.setForeground(new java.awt.Color(255, 255, 255));
        btnAssign.setText("Assign to me ");
        btnAssign.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAssignActionPerformed(evt);
            }
        });

        btnAccept.setBackground(new java.awt.Color(255, 153, 0));
        btnAccept.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        btnAccept.setForeground(new java.awt.Color(255, 255, 255));
        btnAccept.setText("Accept");
        btnAccept.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAcceptActionPerformed(evt);
            }
        });

        availVaccineJTbl.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        availVaccineJTbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Vaccine Name", "Qty"
            }
        ));
        jScrollPane2.setViewportView(availVaccineJTbl);

        distRequestTbl.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        distRequestTbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Vaccine Name", "Qty", "Status", "Supplier", "Sender"
            }
        ));
        jScrollPane3.setViewportView(distRequestTbl);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 153, 0));
        jLabel2.setText("Request Supplier");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 153, 0));
        jLabel3.setText("Vaccine Name:");

        vaccineCombo.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        vaccineCombo.setForeground(new java.awt.Color(255, 153, 0));
        vaccineCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 153, 0));
        jLabel4.setText("Qty :");

        txtQty.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        txtQty.setForeground(new java.awt.Color(255, 153, 0));

        jButton1.setBackground(new java.awt.Color(255, 153, 0));
        jButton1.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Request");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        supCombo.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        supCombo.setForeground(new java.awt.Color(255, 153, 0));
        supCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 153, 0));
        jLabel5.setText("Supplier:");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 153, 0));
        jLabel6.setText("Request from Pharmacy");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 153, 0));
        jLabel7.setText("Inventory");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 153, 0));
        jLabel8.setText("Request to supplier");

        jButton4.setBackground(new java.awt.Color(255, 153, 0));
        jButton4.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("Locate Pharmacy");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addContainerGap()
                                        .addComponent(jScrollPane1))
                                    .addGroup(layout.createSequentialGroup()
                                        .addContainerGap()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel2)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jButton4)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnAssign))
                                            .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel3)
                                                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                    .addComponent(vaccineCombo, 0, 203, Short.MAX_VALUE)
                                                    .addComponent(txtQty)
                                                    .addComponent(supCombo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                    .addGroup(layout.createSequentialGroup()
                                                        .addGap(24, 24, 24)
                                                        .addComponent(jButton1)))))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnAccept)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 871, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 677, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(btnAccept)
                                .addComponent(btnAssign)
                                .addComponent(jButton4))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel3)
                                    .addComponent(vaccineCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel4)
                                    .addComponent(txtQty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(supCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 367, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)))
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(31, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnAssignActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAssignActionPerformed
        // TODO add your handling code here:
        int selectedRow = distJTbl.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(null, "Please select the row to assign the Pharmacy", "Warning", JOptionPane.WARNING_MESSAGE);
        } else {

            if (distJTbl.getValueAt(selectedRow, 2) instanceof PharmacyWorkRequest) {
                PharmacyWorkRequest cwr = (PharmacyWorkRequest) distJTbl.getValueAt(selectedRow, 2);
                cwr.setReceiver(account);
                cwr.setStatus("Pending");
            } else if (distJTbl.getValueAt(selectedRow, 2) instanceof CDCEventInventoryWorkRequest) {
                CDCEventInventoryWorkRequest cwr = (CDCEventInventoryWorkRequest) distJTbl.getValueAt(selectedRow, 2);
                cwr.setReceiver(account);
                cwr.setStatus("Pending");
                for (Events e : network.getEventsDirectory().getEventList()) {
                    if (e.equals(cwr.getEvents())) {
                        System.out.println("e.equals(cwr.getEvent()) EVENT FOUND");
                        for (RequestedVaccineQty rvqty : e.getVaccineInventoryArrayList()) {
                            if (rvqty.getVaccine().equals(cwr.getVaccine())) {
                                System.out.println("e.equals(cwr.getEvent()) Vaccine FOUND");
                                rvqty.setStatus("Pending");
                            }
                        }
                    }
                }
            }
            populatetbl();

            JOptionPane.showMessageDialog(null, " Request assigned successfully.", "Warning", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_btnAssignActionPerformed

    private void btnAcceptActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAcceptActionPerformed
        // TODO add your handling code here:
        int cdcSelectedRow = distJTbl.getSelectedRow();
        if (cdcSelectedRow < 0) {
            JOptionPane.showMessageDialog(null, "Please select the row", "Warning", JOptionPane.WARNING_MESSAGE);
        } else {
            if (distJTbl.getValueAt(cdcSelectedRow, 2) instanceof PharmacyWorkRequest) {
                PharmacyWorkRequest pharmacyWorkRequest = (PharmacyWorkRequest) distJTbl.getValueAt(cdcSelectedRow, 2);
                if (pharmacyWorkRequest.getStatus().equals("Pending")) {
                    if (dc.getDistVaccineDirectory().getVaccineList().size() <= 0) {
                        JOptionPane.showMessageDialog(null, "No Stock available. Request from Supplier");
                        return;
                    }
                    for (Vaccine vaclist : dc.getDistVaccineDirectory().getVaccineList()) {
                        if (pharmacyWorkRequest.getVaccine().getVaccineName().equals(vaclist.getVaccineName())) {
                            if (vaclist.getQuantity() - pharmacyWorkRequest.getRequestedQty() < 0) {
                                JOptionPane.showMessageDialog(null, "No enough Vaccines for supply. Wait for sometime");
                                return;
                            } else {

                                vaclist.setQuantity(vaclist.getQuantity() - pharmacyWorkRequest.getRequestedQty());
                                //PharmacyOrganization po = (PharmacyOrganization) pharmacyWorkRequest.getOrganization();
                                pharmacyWorkRequest.getVaccine().setQuantity(pharmacyWorkRequest.getRequestedQty());
                                for (Organization pharmacyOrganization : pharmacyWorkRequest.getEnterpeise().getOrganizationDirectory().getOrganizationList()) {
                                    if (pharmacyOrganization instanceof PharmacyOrganization) {
                                        ((PharmacyOrganization) pharmacyOrganization).getVaccineInventory().addVaccineInventory(pharmacyWorkRequest.getVaccine());
                                    }
                                }
                            }
                        }
                    }
                    pharmacyWorkRequest.setStatus("Complete");
                    JOptionPane.showMessageDialog(null, "You have successfully completed the request");
                    populateInventory();
                    populatetbl();
                    
                    DistributorBillingWorkRequest dbwr = new DistributorBillingWorkRequest();
                    
                    dbwr.setEnterpeise(pharmacyWorkRequest.getEnterpeise());
                    //dbwr.setOrganization(pharmacyWorkRequest.getOrganization());
                    dbwr.setVaccine(pharmacyWorkRequest.getVaccine());
                    dbwr.setPrice((pharmacyWorkRequest.getVaccine().getPrice())*(pharmacyWorkRequest.getRequestedQty()));
                    dbwr.setSender(account);
                    dbwr.setQty(pharmacyWorkRequest.getRequestedQty());
                    dbwr.setStatus("Bill Generated");
                    
                    network.getWorkQueue().getWorkRequestList().add(dbwr);
                    //pharmacyWorkRequest.getOrganization().getWorkQueue().getWorkRequestList().add(dbwr);
                } else {
                    JOptionPane.showMessageDialog(null, "Please assign first");
                }

            } else if (distJTbl.getValueAt(cdcSelectedRow, 2) instanceof CDCEventInventoryWorkRequest) {
                CDCEventInventoryWorkRequest eventInventoryWorkRequest = (CDCEventInventoryWorkRequest) distJTbl.getValueAt(cdcSelectedRow, 2);
                if (eventInventoryWorkRequest.getStatus().equals("Pending")) {
                    if (dc.getDistVaccineDirectory().getVaccineList().size() <= 0) {
                        JOptionPane.showMessageDialog(null, "No Stock available. Request from Supplier");
                        return;
                    }
                    for (Vaccine vaclist : dc.getDistVaccineDirectory().getVaccineList()) {
                        if (eventInventoryWorkRequest.getVaccine().getVaccineName().equals(vaclist.getVaccineName())) {
                            if (vaclist.getQuantity() - eventInventoryWorkRequest.getRequestedQty() < 0) {
                                JOptionPane.showMessageDialog(null, "No enough Vaccines for supply. Wait for sometime");
                                return;
                            } else {
                                vaclist.setQuantity(vaclist.getQuantity() - eventInventoryWorkRequest.getRequestedQty());
                                eventInventoryWorkRequest.getVaccine().setQuantity(eventInventoryWorkRequest.getRequestedQty());                              
                            }
                        }
                    }
                    eventInventoryWorkRequest.setStatus("Complete");

                    for (Events e : network.getEventsDirectory().getEventList()) {
                        if (e.equals(eventInventoryWorkRequest.getEvents())) {
                            for (RequestedVaccineQty rvqty : e.getVaccineInventoryArrayList()) {
                                if (rvqty.getVaccine().equals(eventInventoryWorkRequest.getVaccine())) {
                                    rvqty.setStatus("Complete");
                                    rvqty.getVaccine().setQuantity(rvqty.getRequestedQty());
                                    System.out.println("rvqty.getVaccine().setQuantity(rvqty.getRequestedQty())");
                                    System.out.println("Vaccine().getQuantity() " + rvqty.getVaccine().getQuantity());
                                }
                            }
                        }
                    }
                    JOptionPane.showMessageDialog(null, "You have successfully completed the request");
                    populateInventory();
                    populatetbl();
                } else {
                    JOptionPane.showMessageDialog(null, "Please assign first");
                }
            }
        }
    }//GEN-LAST:event_btnAcceptActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:

        if (txtQty.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Please enter  Quantity.");
        } else {

            DistributorWorkRequest dwr = new DistributorWorkRequest();
            Supplier sup = (Supplier) supCombo.getSelectedItem();
            dwr.setVaccine((Vaccine) vaccineCombo.getSelectedItem());
            dwr.setRequestQuantity(Integer.parseInt(txtQty.getText()));
            dwr.setStatus("Sent to Supplier");
            dwr.setSender(account);
            dwr.setSupplier(sup);
            dwr.setIncludedFlag(false);
            sup.getWorkQueue().getWorkRequestList().add(dwr);
            distributionCenterOrganization.getWorkQueue().getWorkRequestList().add(dwr);
            dc.getWorkQueue().getWorkRequestList().add(dwr);
            business.getWorkQueue().getWorkRequestList().add(dwr);
            account.getWorkQueue().getWorkRequestList().add(dwr);
            populateDistRequestTbl();

            txtQty.setText("");

            JOptionPane.showMessageDialog(null, "Request generated successfully.", "Warning", JOptionPane.INFORMATION_MESSAGE);
        }

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        int SelectedRow = distJTbl.getSelectedRow();
        if (SelectedRow < 0) {
            JOptionPane.showMessageDialog(null, "Please select the row", "Warning", JOptionPane.WARNING_MESSAGE);
        } else {
            try {
                PharmacyWorkRequest p = (PharmacyWorkRequest) distJTbl.getValueAt(SelectedRow, 2);
                String postCode = String.valueOf(p.getPincode());
                String latLongs[] = getLatLongPositions(postCode);
                System.out.println("latitude" + latLongs[0] + "longitude" + latLongs[1]);
                System.out.println(postCode);
                JFrame test = new JFrame("Google Maps");
                try {
                    String latitude = latLongs[0];
                    String longitude = latLongs[1];

                    String imageUrl = "https://maps.googleapis.com/maps/api/staticmap?center=" + latitude + "," + longitude + "&zoom=18&size=612x612&markers=color:red%7Clabel:S%7C" + latitude + "," + longitude + "&scale=2&maptype=roadmap";
                    String destinationFile = "image.jpg";
                    // read the map image from Google
                    // then save it to a local file: image.jpg
                    //
                    URL url = new URL(imageUrl);
                    InputStream is = url.openStream();
                    OutputStream os = new FileOutputStream(destinationFile);
                    byte[] b = new byte[2048];
                    int length;
                    while ((length = is.read(b)) != -1) {
                        os.write(b, 0, length);
                    }
                    is.close();
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    System.exit(1);
                }
                // create a GUI component that loads the image: image.jpg
                //
                ImageIcon imageIcon = new ImageIcon((new ImageIcon("image.jpg")).getImage().getScaledInstance(630, 600, java.awt.Image.SCALE_SMOOTH));
                test.add(new JLabel(imageIcon));
                // show the GUI window
                test.setVisible(true);
                test.pack();
            } catch (Exception ex) {
                Logger.getLogger(DistributorCenterAdminWorkAreaJPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jButton4ActionPerformed
    public static String[] getLatLongPositions(String address) throws Exception {
        int responseCode = 0;
        String api = "http://maps.googleapis.com/maps/api/geocode/xml?address=" + URLEncoder.encode(address, "UTF-8") + "&sensor=true";
        URL url = new URL(api);
        HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
        httpConnection.connect();
        responseCode = httpConnection.getResponseCode();
        if (responseCode == 200) {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();;
            Document document = builder.parse(httpConnection.getInputStream());
            XPathFactory xPathfactory = XPathFactory.newInstance();
            XPath xpath = xPathfactory.newXPath();
            XPathExpression expr = xpath.compile("/GeocodeResponse/status");
            String status = (String) expr.evaluate(document, XPathConstants.STRING);
            if (status.equals("OK")) {
                expr = xpath.compile("//geometry/location/lat");
                String latitude = (String) expr.evaluate(document, XPathConstants.STRING);
                expr = xpath.compile("//geometry/location/lng");
                String longitude = (String) expr.evaluate(document, XPathConstants.STRING);
                return new String[]{latitude, longitude};
            } else {
                throw new Exception("Error from the API - response status: " + status);
            }
        }
        return null;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable availVaccineJTbl;
    private javax.swing.JButton btnAccept;
    private javax.swing.JButton btnAssign;
    private javax.swing.JTable distJTbl;
    private javax.swing.JTable distRequestTbl;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JComboBox supCombo;
    private javax.swing.JTextField txtQty;
    private javax.swing.JComboBox vaccineCombo;
    // End of variables declaration//GEN-END:variables
}
