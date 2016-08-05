/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package intraplex.livelook;

import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URI;
//import java.net.URISyntaxException;
//import java.util.Collection;
//import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author jschreiv
 */
public  class StatusTableCellRender extends DefaultTableCellRenderer 
		implements MouseListener{
	
	JTable table;
	
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        StatusListModel model = (StatusListModel) table.getModel();
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        
        this.table = table;
       // table.addMouseListener(this); // really need mouse listener for status table ?? try remove and see
        
        if (column == 3)
        {
            JButton button = new JButton();
            button.setText(value.toString());
            return button;
        }
        
        if (column == 6)
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
    
    private static void open(URI uri) {
        if (Desktop.isDesktopSupported()) {
          try {
            Desktop.getDesktop().browse(uri);
          } catch (IOException e) { /* TODO: error handling */ }
        } else { /* TODO: error handling */ }
      }

    boolean mousePressed = false;
    
	@Override
	public void mouseClicked(MouseEvent arg0) {}

	@Override
	public void mouseEntered(MouseEvent arg0) {}

	@Override
	public void mouseExited(MouseEvent arg0) {}

	@Override
	public void mousePressed(MouseEvent arg0) {
		if(mousePressed == false)
			mousePressed = true;
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		if(mousePressed == true && table.getValueAt(table.getSelectedRow(),table.getSelectedColumn()) instanceof URI) {
			open((URI)table.getValueAt(table.getSelectedRow(),table.getSelectedColumn()));
			mousePressed = false;
		}
	}

}
