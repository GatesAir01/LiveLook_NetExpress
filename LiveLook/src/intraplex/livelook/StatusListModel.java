/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package intraplex.livelook;

import intraplex.alarms.AlarmManager;
import java.awt.Color;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
		"Not In Use",	                // Gray
		"Up",	                        // Green
		"Down (TX No Route)",           //Red
		"Down (TX WAN)",	            //Red
		"Down (RX Packet Loss)",        //Red
		"Down (Remote Indication)",     //Red
		"Down (No Channel Activity)",   //Yellow
		"Down (AES Unlock)",	        //Green
		"TX Send Error",			    //Red
		"Down (RX Queue Is Full)",      //Yellow
                "Down (Invalid Configuration)",  //Yellow
		"Down (RX Packet Loss Threshold)", //Red
		"Connection Lost" //Red
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
            
    public StatusListModel(ArrayList<LogMapEntry> l )
    {
        list = l;
    }

    @Override
    public int getRowCount() {
        if (list == null) return 0;
        return list.size();
        
        }

    @Override
    public int getColumnCount() {
        return 4;
    }
    
    private final String[] colNames = new String[] {"Stream Name", "Last Message Time", "Logging Path", "Status"};

    public String getColumnName(int col) {
        return colNames[col];
    }
    
    @Override
    public Object getValueAt(int i, int i1) {
        
        LogMapEntry e = list.get(i);
        if (i1 == 0)
            return e.streamName;
        else if (i1 == 1)
            return sdf.format(e.lastEntry);
        else if (i1 == 2)
            if (e.enableLogging)
                return e.fileName;
            else
                return "Logging is disabled";
        
        int state = e.dataState;
        
        if (System.currentTimeMillis() - e.lastEntry > 30000)
            state = dataState.length-1;
        
        
        if (state < dataState.length)
            return dataState[state];
        
        return "Error";
    
    }
    
    public Color getRowColour(int row) {
        
        LogMapEntry e = list.get(row);
        
        if (System.currentTimeMillis() - e.lastEntry > 30000)
            return Color.red;
        
        if (e.dataState < dataState.length)
            return colors[e.dataState];
        
        return Color.red;
    }

    boolean isConnectionLost(int row) {
        LogMapEntry e = list.get(row);
        
        if (System.currentTimeMillis() - e.lastEntry > 30000)
            return true;
        
        return false;
        
    }

    
}
