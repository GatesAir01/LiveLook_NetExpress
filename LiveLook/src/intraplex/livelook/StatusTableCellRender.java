/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package intraplex.livelook;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author jschreiv
 */
public  class StatusTableCellRender extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        StatusListModel model = (StatusListModel) table.getModel();
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        
        if (column == 3)
        {
    /*        if (model.isConnectionLost(row))
            {
                JButton connectionlost = new JButton("Connection Lost");
                connectionlost.setBackground(Color.red);
                
                return connectionlost;
            }
            else
            { */
                c.setBackground(model.getRowColour(row)); 
           // }
        }
        else if (isSelected)
            c.setBackground(table.getSelectionBackground());
        else
            c.setBackground(table.getBackground());
            
        return c;
    }

}
