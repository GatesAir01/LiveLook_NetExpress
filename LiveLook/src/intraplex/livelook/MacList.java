
package intraplex.livelook;

import static intraplex.livelook.IPLinkNetworkTool.livelookconfig;

import java.io.IOException;
import java.util.Vector;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 * This file is responsible for the table under the tab Mac Addresses.
 *
 * @author Josh Lucas
 */
public class MacList extends javax.swing.JPanel {

	DefaultTableModel model;
	MacFile file;
	

    /**
     * Class constructor.
     */
    public MacList() {
        initComponents();
        
        try {
        	//Looks to see if the user has selected this file before to save them from having
        	//to load it in every time the program is started
        	String filename = livelookconfig.get("MacFileLocation").toString();
        	loadFile(filename);
        }
        catch(Exception e) {
        	
        }
    }
    
    /**
     * Returns the current list of Strings inside the table of Mac Addresses.
     * 
     * @return		Mac Address list
     */
    public String[] getRows() {
    	if(model == null)
    		return new String[0];
    	String[] strings = new String[model.getRowCount()];
    	
    	//pulls the list of strings from the table model
    	for(int x = 0; x < model.getRowCount(); x++){
    		strings[x] = (String) model.getValueAt(x, 0);
    	}
    	return strings;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        LoadFile = new javax.swing.JButton();
        MacPane = new javax.swing.JScrollPane();
        MacTable = new javax.swing.JTable();

        LoadFile.setText("LoadFile");
        LoadFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LoadFileActionPerformed(evt);
            }
        });

        MacTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mac Addresses"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        MacPane.setViewportView(MacTable);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(MacPane, javax.swing.GroupLayout.DEFAULT_SIZE, 387, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(LoadFile)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(MacPane, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LoadFile)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    /**
     * This loads the file of Mac Addresses through use of the MacFile class then adds them to the table.
     * 
     * @param fileName		filename of the user selected MacAdress text file
     */
    private void loadFile(String fileName) {
    	//Initializes new MacFile
    	file = new MacFile(fileName);

        byte[] byteUnecrypted = new byte[0];
        
        //loads the file and then decrypts the file
        try {
            byteUnecrypted = file.decrypt(file.load());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (BadPaddingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //converts from byte[] to string
        String loadString = new String(byteUnecrypted);

        //splits macs
        String[] parts = loadString.split(",");

        model = new DefaultTableModel();
        model.addColumn("Mac Addresses");
        
        //adds the macs from the file to the table model
        for(int x = 0; x < parts.length; x++){
        	Vector temp = new Vector();
        	temp.add(parts[x]);
            model.addRow(temp);
        }
        MacTable.setModel(model);
    }
    
    /**
     * This reacts when the load button is pressed on the Mac Addresses page.
     * A JFileChooser pops up and asks for the file location and then loads the file into the table.
     * 
     * @param evt
     */
    private void LoadFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LoadFileActionPerformed
        JFileChooser chooser = new JFileChooser();
        
        int returnVal = chooser.showOpenDialog(this);
        
        //Checks to make sure that the file chooser got a successful result
        if(returnVal == JFileChooser.APPROVE_OPTION) {
        	String fileName = chooser.getSelectedFile().getAbsolutePath();
            
            //saves location of file to be used in program start up
            livelookconfig.setProperty("MacFileLocation", fileName);
            livelookconfig.save();

            loadFile(fileName);
        }
        else {
            JOptionPane.showMessageDialog(this, "Please reselect the desired file.");
        }

    }//GEN-LAST:event_LoadFileActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton LoadFile;
    private javax.swing.JScrollPane MacPane;
    private javax.swing.JTable MacTable;
    // End of variables declaration//GEN-END:variables
}
