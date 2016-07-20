/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package intraplex.livelook;

import intraplex.alarms.AlarmManager;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Random;
import java.util.logging.Level;
import javax.swing.AbstractListModel;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Schre_000
 */
public class StatusListModel extends AbstractTableModel {

    ArrayList<LogMapEntry> list;
    
    static SimpleDateFormat sdf = new SimpleDateFormat("EEE hh:mm:ss aa MM/dd/yyyy");
    public static final String[] dataState = new String[]
        {
        "Not In Use",
		"Up",	                        // Green
		"Down",           //Red
		"In Progress",
		"Out of Service"
        };
    Color green = new Color(43,148,49);
    Color[] colors = new Color[]
    {
            Color.gray,	                // Gray
            green,	                        // Green
            Color.red,           //Red
            Color.red,	            //Red
            Color.red,        //Red
            Color.red,     //Red
            Color.yellow,   //Yellow
            green,	        //Green
            Color.red,			    //Red
            Color.yellow,      //Yellow
            Color.yellow,  //Yellow
            Color.red //Red
    };
    SnmpMgr mgr;
            
    public StatusListModel(ArrayList<LogMapEntry> l, SnmpMgr mgr)
    {
        list = l;
        this.mgr = mgr;
    }
    
    @Override
    public int getRowCount() {
        if (list == null) return 0;
        return list.size();
        
        }

    @Override
    public int getColumnCount() {
        return 8;
    }
    
    private final String[] colNames = new String[] {"Stream Name", "Stream Id", "Stream IP", "Link", "Last Message Time", "Logging Path", "Status", "Full Monitoring"};

    public String getColumnName(int col) {
        return colNames[col];
    }
    
    @Override
    public Object getValueAt(int i, int i1) {
    	Stream e;
    	LogMapEntry entry = null;
    	Collection<Stream> col = mgr.map.values();
    	Iterator it =  col.iterator();
    	
    	for( int x = 0; x < i; x++) {
    		e = (Stream)it.next();
    	}
    	e = (Stream)it.next();
        if (i1 == 0)
            return e.streamName;
        else if(i1 == 1)
        	return e.index;
        else if(i1 == 2)
        	return e.ip;
        else if(i1 == 3) {
        	URI uri = null;
			try {
				uri = new URI("http://" + e.ip);
			} catch (URISyntaxException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			};
        	return uri;
        }
        else if (i1 == 4)
            return sdf.format(e.lastEntry);
        else if (i1 == 5)
        {
        	 entry = mgr.logMap.get(Long.parseLong(e.ip.replace(".",  "") + e.dstPort));
            if (entry.enableLogging && !e.statusOnly)
                return entry.fileName;
            else
            	if(e.statusOnly)
            		return "Events Only";
            	else
            		return "Logging is disabled";
        }
        else if(i1 == 7) {
        	String s = "" + !e.statusOnly;
        	return s.substring(0, 1).toUpperCase() + s.substring(1);
        }
        
        int state = Integer.parseInt(e.connectionState);
        
        if (state < dataState.length && e.adminState != 2)
        	return dataState[state];
        else if(e.adminState == 2)
        	return dataState[4];
        	
        return "Error";
    
    }
    
    private static void open(URI uri) {
        if (Desktop.isDesktopSupported()) {
          try {
            Desktop.getDesktop().browse(uri);
          } catch (IOException e) { /* TODO: error handling */ }
        } else { /* TODO: error handling */ }
      }
    
    public Color getRowColour(int row) {
        
        LogMapEntry e = list.get(row);
        return mgr.checkForStreamStatus("" + e.address.toString().replace("/", ""), "" + e.destPort);
    }

    boolean isConnectionLost(int row) {
        LogMapEntry e = list.get(row);
        
        if (System.currentTimeMillis() - e.lastEntry > 30000)
            return true;
        
        return false;
        
    }

    
}
