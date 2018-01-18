package userinterface.PHD;

import Business.EcoSystem;
import Business.Enterprise.Enterprise;
import Business.Network.Network;
import Business.Organization.PHDOrderOrganization;
import Business.UserAccount.UserAccount;
import Business.WorkQueue.PharmacyWorkRequest;
import Business.WorkQueue.WorkRequest;
import java.awt.CardLayout;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Deepak_Chandwani; Naman_Bhargava; Sneha_Shree
 */
public class PHDManagerWorkAreaJPanel extends javax.swing.JPanel {

    /**
     * Creates new form CDCManagerWorkAreaJPanel
     */
    private JPanel userProcessContainer;
    private UserAccount account;
    private PHDOrderOrganization organization;
    private Enterprise enterprise;
    private EcoSystem business;
    private Network network;

    public PHDManagerWorkAreaJPanel(JPanel userProcessContainer, UserAccount account, PHDOrderOrganization organization, Enterprise enterprise, EcoSystem business, Network network) {
        initComponents();
        this.userProcessContainer = userProcessContainer;
        this.account = account;
        this.organization = organization;
        this.enterprise = enterprise;
        this.business = business;
        this.network = network;
        populateWorkQueueTable();

    }

    public void populateWorkQueueTable() {
        DefaultTableModel model = (DefaultTableModel) requestTable.getModel();
        model.setRowCount(0);
        for (WorkRequest work : network.getWorkQueue().getWorkRequestList()) {
            if (work instanceof PharmacyWorkRequest) {
                Object[] row = new Object[5];
                row[0] = work.getVaccine().getVaccineName();
                row[1] = ((PharmacyWorkRequest) work).getRequestedQty();
                row[2] = work;
                row[3] = work.getReceiver();
                row[4] = work.getSender();
                model.addRow(row);
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
        requestTable = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        backjButton1 = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));
        setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 0, 0), 10, true));
        setForeground(new java.awt.Color(255, 255, 255));

        requestTable.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        requestTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Vaccine Name", "Quantity", "Status", "Receiver", "Sender"
            }
        ));
        jScrollPane1.setViewportView(requestTable);

        jButton1.setBackground(new java.awt.Color(255, 153, 0));
        jButton1.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Assign to me");
        jButton1.setPreferredSize(new java.awt.Dimension(200, 40));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(255, 153, 0));
        jButton2.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Forward to CDC");
        jButton2.setPreferredSize(new java.awt.Dimension(200, 40));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(255, 153, 0));
        jButton3.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("Reject");
        jButton3.setPreferredSize(new java.awt.Dimension(200, 40));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 153, 0));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Requests from Pharmacy");

        backjButton1.setBackground(new java.awt.Color(255, 153, 0));
        backjButton1.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        backjButton1.setForeground(new java.awt.Color(255, 255, 255));
        backjButton1.setText("<< Back");
        backjButton1.setPreferredSize(new java.awt.Dimension(200, 40));
        backjButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backjButton1ActionPerformed(evt);
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
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addComponent(backjButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 205, Short.MAX_VALUE)
                .addComponent(backjButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        int selectedRow = requestTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(null, "Please select the row to assign the Pharmacy Request", "Warning", JOptionPane.WARNING_MESSAGE);
        } else {
            PharmacyWorkRequest p = (PharmacyWorkRequest) requestTable.getValueAt(selectedRow, 2);
            p.setStatus("Pending");
            p.setReceiver(account);
            populateWorkQueueTable();
              JOptionPane.showMessageDialog(null, "Request assigned successfully.", "Warning", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        int selectedRow = requestTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(null, "Please select the row to Forward Pharmacy request to CDC", "Warning", JOptionPane.WARNING_MESSAGE);
        } else {
            PharmacyWorkRequest p = (PharmacyWorkRequest) requestTable.getValueAt(selectedRow, 2);
            p.setStatus("Forwarded to CDC");
            p.setReceiver(account);
            populateWorkQueueTable();
            
              JOptionPane.showMessageDialog(null, "Request Forwarded to CDC successfully.", "Warning", JOptionPane.INFORMATION_MESSAGE);
        }


    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:

        int selectedRow = requestTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(null, "Please select the request to be Deleted", "Warning", JOptionPane.WARNING_MESSAGE);
        } else {
            PharmacyWorkRequest p = (PharmacyWorkRequest) requestTable.getValueAt(selectedRow, 2);
            p.setStatus("Rejected");
            p.setReceiver(account);
            populateWorkQueueTable();
              JOptionPane.showMessageDialog(null, "Request rejected successfully.", "Warning", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void backjButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backjButton1ActionPerformed
        // TODO add your handling code here:
        userProcessContainer.remove(this);
        CardLayout layout = (CardLayout) userProcessContainer.getLayout();
        layout.previous(userProcessContainer);
    }//GEN-LAST:event_backjButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton backjButton1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable requestTable;
    // End of variables declaration//GEN-END:variables
}
