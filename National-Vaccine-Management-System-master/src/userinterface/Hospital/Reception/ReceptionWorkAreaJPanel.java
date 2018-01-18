/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userinterface.Hospital.Reception;

import Business.EcoSystem;
import Business.Enterprise.Enterprise;
import Business.Events.Events;
import Business.Network.Network;
import Business.Organization.ReceptionOrganization;
import Business.Patient.Patient;
import Business.UserAccount.UserAccount;
import Business.WorkQueue.ReceptionWorkRequest;
import Business.WorkQueue.WorkRequest;
import static Business.sendEmail.sendConfirmationEmail;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author bharg
 */
public class ReceptionWorkAreaJPanel extends javax.swing.JPanel {

    /**
     * Creates new form ReceptionWorkAreaJPanel
     */
    private JPanel userProcessContainer;
    private UserAccount account;
    private ReceptionOrganization receptionOrganization;
    private Enterprise enterprise;
    private EcoSystem business;
    private Network network;

    public ReceptionWorkAreaJPanel(JPanel userProcessContainer, UserAccount account, ReceptionOrganization receptionOrganization, Enterprise enterprise, EcoSystem business, Network network) {
        initComponents();
        this.userProcessContainer = userProcessContainer;
        this.account = account;
        this.receptionOrganization = receptionOrganization;
        this.enterprise = enterprise;
        this.business = business;
        this.network = network;
        populateWorkQueueTable();
        populateEvents();

        txtAge.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!((c >= '0') && (c <= '9') || (c == KeyEvent.VK_BACK_SPACE) || (e.getKeyChar() == '.') || (c == KeyEvent.VK_DELETE))) {
                    e.consume();
                }
            }
        });

        txtName.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!(Character.isAlphabetic(c) || (c >= '0') && (c <= '9') || (c == KeyEvent.VK_BACK_SPACE) || c == KeyEvent.VK_DELETE)) {
                    e.consume();
                }
            }
        });

        txtDate.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!((c >= '0') && (c <= '9') || (c == KeyEvent.VK_SLASH))) {
                    e.consume();
                }
            }
        });

    }

    void populateWorkQueueTable() {

        DefaultTableModel dtm = (DefaultTableModel) ReftoDoctorTbl.getModel();
        dtm.setRowCount(0);
        for (WorkRequest work : enterprise.getWorkQueue().getWorkRequestList()) {
            if (work instanceof ReceptionWorkRequest) {
                Object[] row = new Object[6];
                row[0] = ((ReceptionWorkRequest) work).getPatient().getName();
                row[1] = ((ReceptionWorkRequest) work).getPatient().getAge();
                row[2] = work;
                row[3] = ((ReceptionWorkRequest) work).getPatient().getGender();
                row[4] = work.getReceiver();
                row[5] = ((ReceptionWorkRequest) work).getPatient().getDate();
                dtm.addRow(row);
            }
        }
    }

    void populateEvents() {

        DefaultTableModel dtm = (DefaultTableModel) tblEvents.getModel();
        dtm.setRowCount(0);
        Date startDate = new Date();
        for (Events e : network.getEventsDirectory().getEventList()) {
            Object row[] = new Object[4];
            row[0] = e;
            Date curDate = null, eventDate = null;
            String curDte = null, eveDate = null;
            try {
                curDate = new SimpleDateFormat("dd/MM/yyyy").parse(new SimpleDateFormat("dd/MM/yyyy", Locale.US).format(new Date()));
                eventDate = new SimpleDateFormat("dd/MM/yyyy").parse(new SimpleDateFormat("dd/MM/yyyy", Locale.US).format(e.getStartDate()));
                //System.out.println("parse dates:" + curDate);
                //System.out.println("dates:" + eventDate);
                curDte = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
                eveDate = new SimpleDateFormat("dd/MM/yyyy").format(e.getStartDate());
                System.out.println("curDte " + curDte);
                System.out.println("eveDate " + eveDate);
                System.out.println("curDte.equalsIgnoreCase(eveDate) " + curDte.equalsIgnoreCase(eveDate));
                System.out.println("curDte == (eveDate) " + curDte == (eveDate));
                //Dns@1
            } catch (ParseException ex) {
                //Logger.getLogger(ReceptionWorkAreaJPanel.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Not able to parse dates:" + ex.toString());
                //System.out.println("Not able to parse dates:" + new Date());
                //System.out.println("Not able to parse dates:" + e.getStartDate());
            }
            if (curDate != null && eventDate != null) {
                //if (eventDate.compareTo(curDate) == 0) {
                if (curDte.equalsIgnoreCase(eveDate)) {
                    e.setStatus("Currently Happening");
                } else if (eventDate.compareTo(curDate) > 0) {
                    long different = e.getStartDate().getTime() - startDate.getTime();
                    //System.out.println("Event " + different);
                    //System.out.println("Event startDate.getTime()" + startDate.getTime());

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

                    if (elapsedDays < 1) {
                        row[1] = elapsedHours + "hrs and " + elapsedMinutes + "mins to go";
                    } else {
                        row[1] = elapsedDays + "days to go";
                    }
                }
            }
            row[2] = e.getStatus();
            row[3] = e.getCapacity();
            dtm.addRow(row);
            System.out.println("e.getStartDate().after(curDate)" + e.getStartDate().after(curDate));
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
        ReftoDoctorTbl = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtAge = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblEvents = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        txtDate = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        radioFemale = new javax.swing.JRadioButton();
        radioMale = new javax.swing.JRadioButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();

        setBackground(new java.awt.Color(255, 255, 255));
        setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 10, true));

        ReftoDoctorTbl.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        ReftoDoctorTbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Age", "Status", "Gender", "Receiver", "Date"
            }
        ));
        jScrollPane1.setViewportView(ReftoDoctorTbl);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 153, 0));
        jLabel1.setText("Name:");

        txtName.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        txtName.setForeground(new java.awt.Color(255, 153, 0));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 153, 0));
        jLabel2.setText("Age:");

        txtAge.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        txtAge.setForeground(new java.awt.Color(255, 153, 0));

        jButton1.setBackground(new java.awt.Color(255, 153, 0));
        jButton1.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Request");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 153, 0));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Reception WorkArea");

        tblEvents.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "EventName", "Start Date", "Status"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.String.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tblEvents);

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 153, 0));
        jLabel4.setText("Date:");

        txtDate.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        txtDate.setForeground(new java.awt.Color(255, 153, 0));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 153, 0));
        jLabel5.setText("date : dd/MM/yyyy");

        radioFemale.setBackground(new java.awt.Color(255, 255, 255));
        radioFemale.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        radioFemale.setForeground(new java.awt.Color(255, 153, 0));
        radioFemale.setText("Female");
        radioFemale.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioFemaleActionPerformed(evt);
            }
        });

        radioMale.setBackground(new java.awt.Color(255, 255, 255));
        radioMale.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        radioMale.setForeground(new java.awt.Color(255, 153, 0));
        radioMale.setText("Male");
        radioMale.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioMaleActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 153, 0));
        jLabel6.setText("Gender:");

        jLabel7.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 153, 0));
        jLabel7.setText("e-Mail ID");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(25, 25, 25)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtName, javax.swing.GroupLayout.DEFAULT_SIZE, 177, Short.MAX_VALUE)
                                    .addComponent(txtAge)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addComponent(jLabel7))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtDate, javax.swing.GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(radioMale)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(radioFemale))
                                            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel5)))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtAge, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(radioMale)
                            .addComponent(radioFemale)))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(59, 59, 59)
                .addComponent(jButton1)
                .addContainerGap(103, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        Date date = null;
        if (!txtDate.getText().equals("")) {
            String format = "dd/MM/yyyy";
            String createFlightTimeValidate = txtDate.getText();

            SimpleDateFormat sdf = new SimpleDateFormat(format);
            //Date date = null;
            try {
                date = sdf.parse(createFlightTimeValidate);
            } catch (ParseException ex) {
                // Logger.getLogger(MainJFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (txtName.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Please enter Patient Name.");
        } else if (txtAge.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Please enter Age.");
        } else if (txtDate.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Please enter Date.");
        } else if (date == null) {
            JOptionPane.showMessageDialog(null, "Please enter valid date");
        } else {

            try {
                // TODO add your handling code here:
                ReceptionWorkRequest rwr = new ReceptionWorkRequest();
                Patient p = new Patient();
                p.setName(txtName.getText());
                p.setAge((txtAge.getText()));
                if (radioMale.isSelected()) {
                    p.setGender("Male");
                }
                if (radioFemale.isSelected()) {
                    p.setGender("Female");
                }

                DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                Date startDate = df.parse(txtDate.getText());

                p.setDate(startDate);

                rwr.setPatient(p);
                rwr.setStatus("Reffered to Doctor");
                rwr.setSender(account);
                rwr.setIncludedFlag(false);

                enterprise.getWorkQueue().getWorkRequestList().add(rwr);
                populateWorkQueueTable();

                txtName.setText("");
                txtDate.setText("");
                txtAge.setText("");
                String email = "";
                if (txtEmail.getText() != "") {
                    email = txtEmail.getText();
                }

                JOptionPane.showMessageDialog(null, "Request created successfully.", "Warning", JOptionPane.INFORMATION_MESSAGE);

                if (email != "") {
                    sendConfirmationEmail(email, "Patient Appointment Booked", "New Appointment");
                }

            } catch (ParseException ex) {
                Logger.getLogger(ReceptionWorkAreaJPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }


    }//GEN-LAST:event_jButton1ActionPerformed

    private void radioFemaleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioFemaleActionPerformed
        // TODO add your handling code here:
        if (radioFemale.isSelected()) {
            radioMale.setSelected(false);
        }
    }//GEN-LAST:event_radioFemaleActionPerformed

    private void radioMaleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioMaleActionPerformed
        // TODO add your handling code here:
        if (radioMale.isSelected()) {
            radioFemale.setSelected(false);
        }
    }//GEN-LAST:event_radioMaleActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable ReftoDoctorTbl;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JRadioButton radioFemale;
    private javax.swing.JRadioButton radioMale;
    private javax.swing.JTable tblEvents;
    private javax.swing.JTextField txtAge;
    private javax.swing.JTextField txtDate;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtName;
    // End of variables declaration//GEN-END:variables
}
