/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userinterface.Hospital.Event;

import Business.EcoSystem;
import Business.Enterprise.Enterprise;
import Business.Events.Events;
import Business.Inventory.RequestedVaccineQty;
import Business.Network.Network;
import Business.Organization.EventOrganisation;
import Business.Patient.Patient;
import Business.UserAccount.UserAccount;
import Business.WorkQueue.EventClinicWorkRequest;
import Business.WorkQueue.WorkRequest;
import Business.vaccine.Vaccine;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author erdee
 */
public class EventVolunteerWorkAreaJPanel extends javax.swing.JPanel {

    /**
     * Creates new form EventVolunteerWorkAreaJPanel
     */
    private JPanel userProcessContainer;
    private UserAccount account;
    private EventOrganisation eventOrganisation;
    private Enterprise enterprise;
    private EcoSystem business;
    private Network network;
    Events selectedEvent = null;
    private String eventName = "";
    private int eventCapacity = 0;

    public EventVolunteerWorkAreaJPanel(JPanel userProcessContainer, UserAccount account, EventOrganisation eventOrganisation, Enterprise enterprise, EcoSystem business, Network network) {
        initComponents();
        this.userProcessContainer = userProcessContainer;
        this.account = account;
        this.eventOrganisation = eventOrganisation;
        this.enterprise = enterprise;
        this.business = business;
        this.network = network;
        jLabelEventName.setText("Event");

        //populateComboBox();
        populatePatientDetails();
        populateEvents();
        //populateVaccineInventoryTable();

        jLabelEventName.setText("Event : " + eventName + " capacity : " + eventCapacity);

        jTextFieldName.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!(Character.isAlphabetic(c) || (c >= '0') && (c <= '9') || (c == KeyEvent.VK_BACK_SPACE) || c == KeyEvent.VK_DELETE)) {
                    e.consume();
                }
            }
        });

        jTextFieldAge.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!((c >= '0') && (c <= '9') || (c == KeyEvent.VK_BACK_SPACE) || (e.getKeyChar() == '.') || (c == KeyEvent.VK_DELETE))) {
                    e.consume();
                }
            }
        });
        txtQty.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!((c >= '0') && (c <= '9') || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE))) {
                    e.consume();
                }
            }
        });

    }

    public void populateEvents() {
        DefaultTableModel dtm = (DefaultTableModel) tblEvents.getModel();
        dtm.setRowCount(0);

        for (Events e : network.getEventsDirectory().getEventList()) {
            if (e.getHospitalEnterprise() != null) {
                if (e.getHospitalEnterprise().equals(enterprise)) {
                    Date startDate = new Date();
                    long different = e.getStartDate().getTime() - startDate.getTime();

                    long secondsInMilli = 1000;
                    long minutesInMilli = secondsInMilli * 60;
                    long hoursInMilli = minutesInMilli * 60;
                    long daysInMilli = hoursInMilli * 24;

                    long elapsedDays = different / daysInMilli;
                    different = different % daysInMilli;

                    long elapsedHours = different / hoursInMilli;
                    different = different % hoursInMilli;

                    long elapsedMinutes = different / minutesInMilli;
                    different = different % minutesInMilli;

                    System.out.println(elapsedDays);
                    System.out.println(elapsedHours);
                    System.out.println(elapsedMinutes);

                    Object row[] = new Object[5];
                    row[0] = e;
                    if (elapsedDays < 1) {
                        row[1] = elapsedHours + "hrs and " + elapsedMinutes + "mins to go";
                    } else {
                        row[1] = elapsedDays + "days to go";
                    }
                    row[2] = e.getHospitalEnterprise();
                    row[3] = e.getCapacity();
                    row[4] = e.getStatus();
                    dtm.addRow(row);
                }
            }
        }
    }

    public void populateComboBox(Events e) {
        vaccineComboBox.removeAllItems();
        //for (Events e : network.getEventsDirectory().getEventList()) {
        if (e.getHospitalEnterprise() != null) {
            if (e.getHospitalEnterprise().equals(enterprise)) {
                for (RequestedVaccineQty qty : e.getVaccineInventoryArrayList()) {
                    if (qty.getVaccine().getQuantity() > 0) {
                        vaccineComboBox.addItem(qty.getVaccine());
                    }
                    eventName = e.getName();
                    eventCapacity = e.getCapacity();
                }
            }
        }
        //}
    }

    public void populatePatientDetails() {
        DefaultTableModel dtm_ = (DefaultTableModel) jTablePatientDetails.getModel();
        dtm_.setRowCount(0);

        for (WorkRequest work : eventOrganisation.getWorkQueue().getWorkRequestList()) {
            if (work instanceof EventClinicWorkRequest) {
                Object[] row = new Object[5];
                row[0] = ((EventClinicWorkRequest) work).getPatient().getName();
                row[1] = ((EventClinicWorkRequest) work).getPatient().getAge();
                row[2] = ((EventClinicWorkRequest) work).getVaccine();
                row[3] = ((EventClinicWorkRequest) work).getRequestedQty();
                row[4] = work;
                dtm_.addRow(row);
            }
        }
    }

    public void populateVaccineInventoryTable(Events e) {
        DefaultTableModel dtm = (DefaultTableModel) jTableVaccineInventory.getModel();
        dtm.setRowCount(0);
        //for (Events e : network.getEventsDirectory().getEventList()) {
        if (e.getHospitalEnterprise() != null) {
            if (e.getHospitalEnterprise().equals(enterprise)) {
                for (RequestedVaccineQty qty : e.getVaccineInventoryArrayList()) {
                    Object row[] = new Object[5];
                    row[0] = qty.getVaccine();
                    row[1] = qty.getVaccine().getDisease();
                    row[2] = qty.getVaccine().getQuantity();
                    row[3] = qty;
                    dtm.addRow(row);
                }
            }
        }
        //}
    }

    public void updateInventory(Vaccine v, Events e) {
        boolean isVaccineEmpty = false;
        //Events eve = null;
        //for (Events e : network.getEventsDirectory().getEventList()) {
        if (e.getHospitalEnterprise() != null) {
            if (e.getHospitalEnterprise().equals(enterprise)) {
                for (RequestedVaccineQty qty : e.getVaccineInventoryArrayList()) {
                    if (qty.getVaccine().equals(v)) {
                        //eve = e;
                        if (qty.getVaccine().getQuantity() > 0) {
                            qty.getVaccine().setQuantity(qty.getVaccine().getQuantity() - 1);
                        } else {
                            isVaccineEmpty = true;
                        }
                    }
                }
            }
        }
        //}
        if (isVaccineEmpty) {
            JOptionPane.showMessageDialog(null, "No stock available for selected vaccine");
        }
        populateVaccineInventoryTable(e);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabelEventName = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTableVaccineInventory = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTablePatientDetails = new javax.swing.JTable();
        jTextFieldName = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jTextFieldAge = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        vaccineComboBox = new javax.swing.JComboBox();
        txtQty = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblEvents = new javax.swing.JTable();
        jButton4 = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));

        jLabelEventName.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabelEventName.setForeground(new java.awt.Color(255, 153, 0));
        jLabelEventName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelEventName.setText("Event : X is goin on");

        jTableVaccineInventory.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jTableVaccineInventory.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Vaccine", "Disease", "Vaccine Quantity", "Object"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableVaccineInventory.getTableHeader().setReorderingAllowed(false);
        jScrollPane3.setViewportView(jTableVaccineInventory);
        if (jTableVaccineInventory.getColumnModel().getColumnCount() > 0) {
            jTableVaccineInventory.getColumnModel().getColumn(3).setMinWidth(0);
            jTableVaccineInventory.getColumnModel().getColumn(3).setPreferredWidth(0);
            jTableVaccineInventory.getColumnModel().getColumn(3).setMaxWidth(0);
        }

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 153, 0));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Patient Details");

        jTablePatientDetails.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jTablePatientDetails.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Patient Name", "Patient Age", "Vaccine", "Qty", "Object"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTablePatientDetails.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jTablePatientDetails);
        if (jTablePatientDetails.getColumnModel().getColumnCount() > 0) {
            jTablePatientDetails.getColumnModel().getColumn(4).setMinWidth(0);
            jTablePatientDetails.getColumnModel().getColumn(4).setPreferredWidth(0);
            jTablePatientDetails.getColumnModel().getColumn(4).setMaxWidth(0);
        }

        jTextFieldName.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 153, 0));
        jLabel5.setText("Name:");

        jTextFieldAge.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jTextFieldAge.setForeground(new java.awt.Color(255, 153, 0));
        jTextFieldAge.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldAgeActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 153, 0));
        jLabel6.setText("Age:");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 153, 0));
        jLabel7.setText("Vaccine: ");

        vaccineComboBox.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        vaccineComboBox.setForeground(new java.awt.Color(255, 153, 0));
        vaccineComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        txtQty.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        txtQty.setForeground(new java.awt.Color(255, 153, 0));
        txtQty.setText("1");
        txtQty.setEnabled(false);

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 153, 0));
        jLabel8.setText("Qty:");

        jButton2.setBackground(new java.awt.Color(255, 153, 0));
        jButton2.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Request");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 153, 0));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Vaccine Inventory");

        tblEvents.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "EventName", "Start Date", "Hospital", "Capacity", "Status"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane4.setViewportView(tblEvents);

        jButton4.setBackground(new java.awt.Color(255, 153, 0));
        jButton4.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("Inventory");
        jButton4.setMaximumSize(new java.awt.Dimension(150, 40));
        jButton4.setMinimumSize(new java.awt.Dimension(150, 40));
        jButton4.setPreferredSize(new java.awt.Dimension(150, 40));
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
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelEventName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel8))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextFieldName, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextFieldAge, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(vaccineComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtQty)
                                    .addComponent(jButton2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 379, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 386, Short.MAX_VALUE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jScrollPane4)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(157, 157, 157)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelEventName, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jTextFieldName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jTextFieldAge, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(vaccineComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(txtQty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(53, 53, 53))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jTextFieldAgeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldAgeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldAgeActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        if (selectedEvent.getStatus().equalsIgnoreCase("Currently Happening")
                || selectedEvent.getStatus().equalsIgnoreCase("Current Happening")
                || selectedEvent == null) {
            String name = null, age = null;
            //int age = 0;
            boolean checkName = false, checkAge = false;
            if (jTextFieldName.getText().trim().length() > 0) {
                name = jTextFieldName.getText().trim();
                checkName = true;
            } else {
                JOptionPane.showMessageDialog(null, "Name cannot be empty");
                checkName = false;
            }

            if (jTextFieldAge.getText().trim().length() > 0) {
                age = jTextFieldAge.getText().trim();
                checkAge = true;
            } else {
                JOptionPane.showMessageDialog(null, "Age cannot be empty and zero");
                checkAge = false;
            }

            if (txtQty.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Please enter Quantity.");
                return;
            }

            if (checkAge && checkName) {
                EventClinicWorkRequest eventClinicWorkRequest = new EventClinicWorkRequest();
                eventClinicWorkRequest.setRequestedQty(1);
                eventClinicWorkRequest.setVaccine(((Vaccine) vaccineComboBox.getSelectedItem()));
                eventClinicWorkRequest.setSender(account);
                eventClinicWorkRequest.setStatus("Complete");
                Patient patient = new Patient();
                patient.setAge(age);
                patient.setDate(new Date());
                patient.setName(name);
                eventClinicWorkRequest.setPatient(patient);
                eventOrganisation.getWorkQueue().getWorkRequestList().add(eventClinicWorkRequest);
                updateInventory(((Vaccine) vaccineComboBox.getSelectedItem()), selectedEvent);
                populatePatientDetails();

                jTextFieldName.setText("");
                jTextFieldAge.setText("");
                //txtQty.setText("");
                JOptionPane.showMessageDialog(null, "Request created successfully.", "Warning", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Event is either COMPLETED or YET TO START");
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        if (tblEvents.getSelectedRow() < 0) {
            JOptionPane.showMessageDialog(null, "Kindly select a row from events table");
        } else {
            selectedEvent = (Events) tblEvents.getValueAt(tblEvents.getSelectedRow(), 0);
            populateVaccineInventoryTable(selectedEvent);
            populateComboBox(selectedEvent);
        }
    }//GEN-LAST:event_jButton4ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelEventName;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable jTablePatientDetails;
    private javax.swing.JTable jTableVaccineInventory;
    private javax.swing.JTextField jTextFieldAge;
    private javax.swing.JTextField jTextFieldName;
    private javax.swing.JTable tblEvents;
    private javax.swing.JTextField txtQty;
    private javax.swing.JComboBox vaccineComboBox;
    // End of variables declaration//GEN-END:variables
}
