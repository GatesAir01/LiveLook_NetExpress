/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package intraplex.livelook;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jschreiv
 */
public class StatusPanel extends javax.swing.JPanel implements Runnable {

    /**
     * Creates new form StatusPanel
     */
    public static ActionListener reconnectListener;
    ArrayList<LogMapEntry> streamlist;
    SnmpMgr mgr;
    StatusListModel slm;
    public StatusPanel(SnmpMgr m) {
        
        mgr = m;
        streamlist = new ArrayList<>();
        initComponents();
        
        jTable1.setDefaultRenderer(Object.class, new StatusTableCellRender());
        jTable1.getTableHeader().setReorderingAllowed(false);
        (new Thread(this)).start();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bufferPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        jTable1.setModel(slm = new StatusListModel(streamlist, mgr));
        jTable1.setDoubleBuffered(true);
        jTable1.setRowSelectionAllowed(false);
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout bufferPanelLayout = new javax.swing.GroupLayout(bufferPanel);
        bufferPanel.setLayout(bufferPanelLayout);
        bufferPanelLayout.setHorizontalGroup(
            bufferPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 551, Short.MAX_VALUE)
        );
        bufferPanelLayout.setVerticalGroup(
            bufferPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 573, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bufferPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bufferPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    int i = 0;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel bufferPanel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables

    @Override
    public void run() {
       
        while (true)
        {
            try {
                 Thread.currentThread().sleep(5000);
                streamlist.clear();
                streamlist.addAll(mgr.logMap.values());
                slm.fireTableDataChanged();
            } catch (InterruptedException ex) {
                Logger.getLogger(StatusPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    
    }

 


}
