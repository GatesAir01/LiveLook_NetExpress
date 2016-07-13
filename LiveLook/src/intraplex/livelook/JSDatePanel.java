/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package intraplex.livelook;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.jdesktop.swingx.JXDatePicker;

/**
 *
 * @author jschreiv
 */
public class JSDatePanel extends JPanel implements PropertyChangeListener{
    private javax.swing.JSpinner spinner;
    JXDatePicker picker; 
    
    public JSDatePanel()
    {
        this(new Date(System.currentTimeMillis()));
    }
    
    public JSDatePanel(Date d)
    {
        spinner = new javax.swing.JSpinner();
        spinner.setModel(new javax.swing.SpinnerDateModel(d, null, null, java.util.Calendar.MINUTE));
        spinner.setEditor(new JSpinner.DateEditor(spinner, " h:mm a  "));
        setLayout(new BorderLayout());
        picker = createDatePicker(d);
        picker.addPropertyChangeListener(this);
        add(picker, BorderLayout.EAST);
        add(spinner, BorderLayout.WEST);
    }
    
    public JXDatePicker createDatePicker(Date d)
    {
        JXDatePicker datePicker = new JXDatePicker(d);
        datePicker.getMonthView().setSelectionBackground(UIManager.getColor("Table.foreground"));
        datePicker.getMonthView().setSelectionForeground(UIManager.getColor("Table.background"));
        datePicker.getMonthView().setMonthStringBackground(UIManager.getColor("Table.selectionBackground"));
        datePicker.getMonthView().setMonthStringForeground(UIManager.getColor("Table.selectionForeground"));
        datePicker.getMonthView().setForeground(UIManager.getColor("Panel.foreground"));
        datePicker.getMonthView().setBackground(UIManager.getColor("Panel.background"));
        datePicker.getMonthView().setDaysOfTheWeekForeground(UIManager.getColor("Panel.foreground"));
        datePicker.getLinkPanel().getComponent(0).setForeground(Color.black);
        return datePicker;
    }

    @Override
    public void propertyChange(PropertyChangeEvent pce) {
        
        if (pce.getNewValue() instanceof Date)
        {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime((Date)spinner.getValue());
            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTime((Date)pce.getNewValue());
            
            calendar.set(Calendar.MONTH, calendar2.get(Calendar.MONTH));
            calendar.set(Calendar.YEAR, calendar2.get(Calendar.YEAR));
            calendar.set(Calendar.DATE, calendar2.get(Calendar.DATE));
            
            spinner.setValue(calendar.getTime());
            picker.setDate(calendar.getTime());
        }
    }


    public void setEnabled(boolean b)
    {
        super.setEnabled(b);
        spinner.setEnabled(b);
        picker.setEnabled(b);
    }

    public void setDate(Date d)
    {
        spinner.setValue(d);
        picker.setDate(d);
    }
    
    public Date getDate()
    {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime((Date)spinner.getValue());
            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTime((Date)picker.getDate());
            
            calendar.set(Calendar.MONTH, calendar2.get(Calendar.MONTH));
            calendar.set(Calendar.YEAR, calendar2.get(Calendar.YEAR));
            calendar.set(Calendar.DATE, calendar2.get(Calendar.DATE));
            
            return calendar.getTime();
    }
    
}
