/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package intraplex.livelook;

import static intraplex.livelook.StatusListModel.sdf;
import java.awt.Color;
import java.awt.Component;
import java.text.SimpleDateFormat;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.ListCellRenderer;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author Schre_000
 */
public class StreamStatusPanel extends javax.swing.JPanel implements TableCellRenderer {

    /**
     * Creates new form ActionEventPanel
     */
    
    static SimpleDateFormat sdf = new SimpleDateFormat("EEE hh:mm:ss aa MM/dd/yyyy");
    SnmpMgr mgr;
    
    public StreamStatusPanel(SnmpMgr mgr) {
    	this.mgr = mgr;
    	
        initComponents();
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        nameLabel = new javax.swing.JLabel();

        nameLabel.setText("Event Name");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(nameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 84, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(nameLabel)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel nameLabel;
    // End of variables declaration//GEN-END:variables


     public Component getListCellRendererComponent(
       JList<?> list,           // the list
       Object value,            // value to display
       int index,               // cell index
       boolean isSelected,      // is the cell selected
       boolean cellHasFocus)    // does the cell have focus
     {
         LogMapEntry e = (LogMapEntry)value;
         nameLabel.setText(e.streamName + " - "+sdf.format(e.lastEntry));
         Color failed = mgr.checkForStreamStatus("" + e.address.toString().replace("/", ""), "" + e.destPort);
         if (isSelected) {
             setBackground(failed);
             setForeground(list.getSelectionForeground());
         } else {
        	 setBackground(failed);
             setForeground(list.getForeground());
             
         }
        // System.out.println("System here");
         setEnabled(list.isEnabled());
         setOpaque(true);
         return this;
     }

    @Override
    public Component getTableCellRendererComponent(JTable jtable, Object value, boolean isSelected, boolean bln1, int i, int i1)
    {
         LogMapEntry e = (LogMapEntry)value;
         String text;
        if (i1 == 0)
            text = e.streamName;
        else if (i1 == 1)
            text = sdf.format(e.lastEntry);
        else
            text = e.fileName;
         nameLabel.setText(text);
         Color failed = mgr.checkForStreamStatus("" + e.address.toString().replace("/", ""), "" + e.destPort);
         if (isSelected) {
        	 setBackground(failed);
             setForeground(jtable.getSelectionForeground());
         } else {
        	 setBackground(failed);
             setForeground(jtable.getForeground());
         }
         setEnabled(jtable.isEnabled());
         setOpaque(true);
         return this;
     }
    }
 

