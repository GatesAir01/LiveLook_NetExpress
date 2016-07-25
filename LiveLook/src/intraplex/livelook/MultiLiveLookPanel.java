/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package intraplex.livelook;

import static intraplex.livelook.IPLinkNetworkTool.livelookconfig;
import static intraplex.livelook.IPLinkNetworkTool_Lite.livelookconfiglite;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartMouseEvent;
import org.jfree.chart.ChartMouseListener;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.LogarithmicAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.entity.ChartEntity;
import org.jfree.chart.entity.LegendItemEntity;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 *
 * @author jschreiv
 */
public class MultiLiveLookPanel extends javax.swing.JPanel implements ActionListener, Runnable, NewStreamAvailableListener, ChartMouseListener{

    /**
     * Creates new form NetworkHistoryPanel
     */
    public static int[] interval = new int[] {5000,15000,30000,60000,300000,600000,900000,1200000,1800000,3600000};
    
    int[]    traceTypes;
    long[]   streamId;
    
    JFileChooser chooser;    
    Mutex traceMutex;
   // NetworkMonitor nm;
    SnmpMgr msgMgr;
    
    //Keys for String Lookups 
    TreeMap<String, Long> streamKeys;
    long startingPoint;
    boolean dataReceived;
    
    JMenu[] popupItem;
    JFreeChart[] charts;
    XYSeries  traces[];
    WaitingForConnectionDialog waitDialog;
    int updateNeeded = 0;
    ChartPanel[] panels;
    
    Color[] traceColors;
    int loadedInterval;
    private String tracefont;
    private int tracefontsize;
    private String tracenumberformat;
    private int numberpad;
    boolean lite;
    
    public MultiLiveLookPanel(SnmpMgr mgr, boolean lite) {
    	this.lite = lite;
        loadConfiguation();
        initComponents();
        intervalBox.setSelectedIndex(loadedInterval);
        msgMgr = mgr;
        traceMutex = new Mutex();
        traceTypes = new int[8];
        streamId = new long[8];
        popupItem = new JMenu[2];
        popupItem[0] = new JMenu("Traces");
        popupItem[1] = new JMenu("Traces");
        
        traces = new XYSeries [8];
        traceTypes = new int[8];
        for (int i = 0; i < traces.length; i++)
        {
            traceTypes[i] = -1;
            traces[i] = new XYSeries("Random Data "+i); 
            traces[i].setMaximumItemCount(600);
            //traces[i].setStroke(new BasicStroke(2));
        }
        
        charts = new JFreeChart[2];
        charts[0] = createChart(0);
        charts[1] = createChart(4);
       panels = new ChartPanel[2];
       for (int p =0; p < 2; p++)
       {
        panels[p] = new ChartPanel(charts[p], true, true, true, true, true);
        panels[p].getPopupMenu().remove(0);
        ((JMenu)(panels[p].getPopupMenu().getSubElements()[1])).remove(2);
        panels[p].getPopupMenu().remove(6);
        panels[p].getPopupMenu().remove(6);
        panels[p].getPopupMenu().remove(6);
        panels[p].getPopupMenu().remove(6);
        
        panels[p].addChartMouseListener(this);
       }
       
       chart1Container.setLayout(new BorderLayout());
       chart2Container.setLayout(new BorderLayout());
       
       
        //Added chartpanel to main panel
        chart1Container.add(panels[0]);
        chart2Container.add(panels[1]);

        for (int p = 0; p < 2; p++)
        {
            panels[p].getPopupMenu().add(popupItem[p]);
            String base = p+":-2:";
            JMenu optionMenu = new JMenu("Quick Axis");
            JMenuItem mItem = new JMenuItem("Auto");
            mItem.setActionCommand(base+0);
            mItem.addActionListener(this);
            optionMenu.add(mItem);
            mItem = new JMenuItem("Auto Show Zero");
            mItem.setActionCommand(base+1);
            mItem.addActionListener(this);
            optionMenu.add(mItem);
            mItem = new JMenuItem("Semi-log");
            mItem.setActionCommand(base+2);
            mItem.addActionListener(this);
            optionMenu.add(mItem);
            panels[p].getPopupMenu().add(optionMenu);
            
        }
                
        streamKeys = new TreeMap<>();
        addTrace(2, 0, 0, false);
        addTrace(1, 0, 0, false);
        addTrace(0, 1, 0, false);
        addTrace(5, 1, 0, false);
        //addTrace(0, 0, 0, false);
        //addTrace(7, 0, 0, false);
        //addTrace(4, 1, 0, false);
       //addTrace(5, 1, 0, true);
        prepareMenus();
        
        //msgMgr.setNewStreamListener(this);
        
        dataReceived = false;
        
        new Thread(this, "tracepoints").start();
        disconnectButton.setEnabled(false);
        waitDialog = null;
        QueueMonitor qm = new QueueMonitor(msgMgr, piq);
        
        if(msgMgr.map.size() > 0) {
        	for(Long stream: msgMgr.map.keySet()) {
        		Stream streams = msgMgr.map.get(stream);
        		addStreamKey(stream, streams.streamName, streams.statusOnly);
        	}
        	disconnectButton.setEnabled(true);
        }
    }
    
    public long nearestMinute()
    {
        Calendar c = new GregorianCalendar();
        c.setTimeInMillis(System.currentTimeMillis());
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTimeInMillis();
    }
        
    private void prepareMenus()
    {
        int tracesPerChart = traces.length/popupItem.length;
        for (int p = 0; p < popupItem.length; p++)
        {
            int tracesAdded = 0;
            popupItem[p].removeAll();
            int offset =tracesPerChart*p;
            for (int i = 0; i < tracesPerChart; i++)
            {
                if (traceTypes[offset+i] >= 0)
                {
                    tracesAdded++;
                    String streamName = msgMgr.getStreamName(streamId[offset+i]);
                    JCheckBoxMenuItem menuItem = new JCheckBoxMenuItem(streamName+" "+NetworkLogDataPoint.getTypeTitle(traceTypes[offset+i],false));
                    menuItem.setActionCommand(p+":"+(offset+i)+":"+streamId[offset+i]);
                    menuItem.setSelected(true);
                    menuItem.addActionListener(this);
                    popupItem[p].add(menuItem);
                }
            }
            if (tracesAdded < tracesPerChart)
            {
                if (streamKeys.isEmpty())
                {
                    JMenu addMenu = new JMenu("Add Trace");
                        String base = p+":-1:0:";
                        for (int j = 0; j < NetworkLogDataPoint.NUM_TRACES_LIVE; j++)
                        {
                            JMenuItem menuItem2 = new JMenuItem(NetworkLogDataPoint.getTypeTitle(j,false));
                            menuItem2.setActionCommand(base+j);
                            menuItem2.addActionListener(this);
                            addMenu.add(menuItem2);
                        
                        }
                    popupItem[p].add(addMenu);
                }
                else
                {
                    JMenu addMenu = new JMenu("Add Trace");
                    for (Map.Entry<String, Long> entry : streamKeys.entrySet())
                    {
                        JMenu streamMenu = new JMenu(entry.getKey());
                        String base = p+":-1:"+entry.getValue()+":";
                        for (int j = 0; j < NetworkLogDataPoint.NUM_TRACES_LIVE; j++)
                        {
                            JMenuItem menuItem2 = new JMenuItem(NetworkLogDataPoint.getTypeTitle(j,false));
                            menuItem2.setActionCommand(base+j);
                            menuItem2.addActionListener(this);
                            streamMenu.add(menuItem2);
                        }
                        addMenu.add(streamMenu);
                    }
                    popupItem[p].add(addMenu);
                    
                }
            }
        }
    }
    
    
    //Show the most points, but limit to 200
    private int getBestInterval(long start, long end)
    {
        long difference = end - start;
        
        for (int i = 0; i < interval.length; i++)
        {
            if (difference/interval[i] < 200)
                return i;
        }
        return interval.length - 1;
    }
    
    public void updatePlot()
    {
        long intvl = interval[intervalBox.getSelectedIndex()];
      
        
        //zoomableChart1.zoomAll();
        //zoomableChart2.zoomAll();

        
        traceMutex.lock();
        msgMgr.changeWindowSize((int)(intvl/5000));
        for (int i = 0; i < traceTypes.length; i++)
        {
            if (traceTypes[i] >= 0)
            {
                traces[i].clear();
                
                //charts[chart].getXYPlot().getRenderer().setSeriesVisible(i, Boolean.FALSE);
                //traces[i].setVisible(false);
            }
        }
        traceMutex.unlock();
        
       // zoomableChart2.setSynchronizedXStartChart(zoomableChart1);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        intervalBox = new javax.swing.JComboBox();
        javax.swing.JLabel jLabel4 = new javax.swing.JLabel();
        connectButton = new javax.swing.JButton();
        clearData = new javax.swing.JButton();
        chart2Container = new javax.swing.JPanel();
        chart1Container = new javax.swing.JPanel();
        disconnectButton = new javax.swing.JButton();
        piq = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        intervalBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "5 Seconds", "15 Seconds", "30 Seconds", "1 Minute", "5 Minutes" }));
        intervalBox.setSelectedIndex(0);
        intervalBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                intervalBoxActionPerformed(evt);
            }
        });

        jLabel4.setText("Interval");

        connectButton.setText("Add Connection");
        connectButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                connectButtonActionPerformed(evt);
            }
        });

        clearData.setText("Clear Data");
        clearData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearDataActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout chart2ContainerLayout = new javax.swing.GroupLayout(chart2Container);
        chart2Container.setLayout(chart2ContainerLayout);
        chart2ContainerLayout.setHorizontalGroup(
            chart2ContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        chart2ContainerLayout.setVerticalGroup(
            chart2ContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 210, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout chart1ContainerLayout = new javax.swing.GroupLayout(chart1Container);
        chart1Container.setLayout(chart1ContainerLayout);
        chart1ContainerLayout.setHorizontalGroup(
            chart1ContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        chart1ContainerLayout.setVerticalGroup(
            chart1ContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 210, Short.MAX_VALUE)
        );

        disconnectButton.setText("Disconnect");
        disconnectButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                disconnectButtonActionPerformed(evt);
            }
        });

        piq.setText("0");

        jButton1.setText("Reset Zoom");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(intervalBox, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(209, 551, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(chart1Container, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(chart2Container, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(connectButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(disconnectButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(clearData)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(piq)
                        .addGap(26, 26, 26))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(chart1Container, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chart2Container, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(intervalBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(connectButton)
                    .addComponent(clearData)
                    .addComponent(disconnectButton)
                    .addComponent(piq)
                    .addComponent(jButton1))
                .addGap(6, 6, 6))
        );
    }// </editor-fold>//GEN-END:initComponents

    @SuppressWarnings("empty-statement")
    private void connectButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_connectButtonActionPerformed
     ConnectIPLinkDialog cipd = new ConnectIPLinkDialog(null,true, lite);
     cipd.setVisible(true);
   //  cipd.enableLogging
     try
     {
         if (msgMgr.checkIfExisting(cipd.iPAddress, Integer.parseInt(cipd.stream)))
         {
             JOptionPane.showMessageDialog(this, "LiveLook NetXpress is already connected to this stream\nTry connecting to another stream");
             return;
         }
         
     waitDialog = new WaitingForConnectionDialog(null,true);
    // waitDialog.enableLogging = cipd.enableLogging;
     int dport = Integer.parseInt(cipd.getDPort());
     LogMapEntry.next_DPort = dport;
     Stream stream = msgMgr.addStream(cipd.iPAddress, cipd.stream, cipd.readCom);
     if (!msgMgr.checkIfMacAllowed(stream))
     {
         JOptionPane.showMessageDialog(this, "Mac is not registered for use");
         msgMgr.disconnect(Long.parseLong(stream.ip.replace(".", "") + stream.dstPort));
         return;
     }
     else {
	     if(stream.opened)waitDialog.notifyConnection();
	     addStreamKey(Long.parseLong(stream.ip.replace(".", "")  + stream.dstPort), stream.streamName, stream.statusOnly);
	     //JDispatchMgr.sendSetupMessage(cipd.iPAddress,Integer.parseInt(cipd.stream),1,dport);
	     waitDialog.setVisible(true);
	     //waitDialog.enableLogging = cipd.enableLogging;
	     //System.out.println(" enable logging in Wait Dialog is "+waitDialog.enableLogging);
	        if (waitDialog.connected)
	        {
	            if (streamKeys.size() <= 2 && !stream.statusOnly)
	            {
	                long streamKey1 = 0;
	                long newKey = 0;
	                    
	                for (int i = 0; i < traces.length; i++)
	                {
	                    if (traceTypes[i] != -1)
	                    {
	                        streamKey1 = streamId[i];
	                    }
	                }
	                
	               
	                Iterator<Long> r = streamKeys.values().iterator();
	                while (r.hasNext())
	                {
	                    Long l = r.next();
	                    if (l.longValue() != streamKey1 )
	                    {
	                        newKey = l.longValue();
	                    }
	                }
	                
	                if (newKey != 0)
	                { 
	                    int[] toAdd = new int[] {1,2};
	                    int added = 0;
	                    for (int i = 0; i < 4; i++)
	                    {
	                        if (traceTypes[i] != -1 && streamId[i] == 0)break;
	                        if (traceTypes[i] == -1 )
	                        {
	                            addTrace(toAdd[added++], 0, newKey, false);
	                            if (added == 2)break;
	                        }
	                    }
	                    toAdd = new int[] {0,5};
	                    added = 0;
	                    for (int i = 0; i < 4; i++)
	                    {
	                        if (traceTypes[i+4] != -1 && streamId[i+4] == 0)break;
	                        if (traceTypes[i+4] == -1 )
	                        {
	                            addTrace(toAdd[added++], 1, newKey, false);
	                            if (added == 2)break;
	                        }
	                    }
	
	                    updateNeeded++;
	                    prepareMenus();
	                }
	            }
	        }
	        else
	        {
	            msgMgr.cleanUp();
	        }
     	}
     }
     catch (Exception e)
     {
         System.out.println("ERROR");
     }
     
            
     
    }//GEN-LAST:event_connectButtonActionPerformed

    private void intervalBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_intervalBoxActionPerformed

    	if(!lite){
        	livelookconfig.put("LiveView.Interval",(interval[intervalBox.getSelectedIndex()]/1000)+"");
            livelookconfig.save();
        }
        else {
        	livelookconfiglite.put("LiveView.Interval",(interval[intervalBox.getSelectedIndex()]/1000)+"");
            livelookconfiglite.save();
        }
                
        updateNeeded++;
    }//GEN-LAST:event_intervalBoxActionPerformed

    private void clearDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearDataActionPerformed
        msgMgr.reset();
        
        for (int i = 0; i < 2; i++)
        {
            XYPlot plot = charts[i].getXYPlot();
            DateAxis xaxis = (DateAxis) new DateAxis();
            xaxis.setAutoRange(true);
            xaxis.setAxisLinePaint(Color.white);
            xaxis.setDateFormatOverride(new SimpleDateFormat("h:mm a"));
            xaxis.setTickLabelPaint(Color.white);
            xaxis.setLabelFont(new Font("Tahoma",Font.PLAIN,11));
            xaxis.setTickLabelFont(new Font("Tahoma",Font.PLAIN,11));
            xaxis.setVerticalTickLabels(false);
            plot.setDomainAxis(xaxis);
        }
        
        dataReceived = false;
        updateNeeded++;
    }//GEN-LAST:event_clearDataActionPerformed

    private void disconnectButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_disconnectButtonActionPerformed   	
    	System.out.println(streamKeys.size());
    	
       if (streamKeys.isEmpty())return;
       String[] streams = streamKeys.keySet().toArray(new String[0]);
       
       String selectedValue = streams[0];
       if (streams.length > 1)
       {
        selectedValue = (String)JOptionPane.showInputDialog(null,
         "Select Stream", "Disconnect",
         JOptionPane.INFORMATION_MESSAGE, null,
         streams, streams[0]);
       }
       
       if (selectedValue!=null)
       {
        long key = streamKeys.get(selectedValue);
        msgMgr.disconnect(key);
        removeTraces(key,true);
        streamKeys.remove(selectedValue);
        if (streamKeys.isEmpty())
        {
            disconnectButton.setEnabled(false);
        }
       }
       
    }//GEN-LAST:event_disconnectButtonActionPerformed

    public void disconnectAll()
    {
        Iterator i = streamKeys.values().iterator();
        while (i.hasNext())
        {
            try
            {
                Long l = (Long)i.next();
                msgMgr.disconnect(l.longValue());
            }
            catch (Exception e)
            {
                
            }
        }
    }
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
       for (int p =0; p < 2; p++)
       {
        panels[p].restoreAutoBounds();
       }
                
    }//GEN-LAST:event_jButton1ActionPerformed

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel chart1Container;
    private javax.swing.JPanel chart2Container;
    private javax.swing.JButton clearData;
    private javax.swing.JButton connectButton;
    private javax.swing.JButton disconnectButton;
    private javax.swing.JComboBox intervalBox;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel piq;
    // End of variables declaration//GEN-END:variables

    @Override
    public void actionPerformed(ActionEvent ae) {
        String[] command = ae.getActionCommand().split(":");
        int i = Integer.parseInt(command[1]);
        int chart = Integer.parseInt(command[0]);
       
        if (i >= 0)
        {
            traces[i].clear();
            charts[chart].getXYPlot().getRenderer().setSeriesVisible(i, Boolean.FALSE);
            
            if (chart == 0)
                charts[chart].getXYPlot().getRenderer().setSeriesVisible(i, Boolean.FALSE);
            else
                charts[chart].getXYPlot().getRenderer().setSeriesVisible(i-4, Boolean.FALSE);
            traces[i].setKey("Random Data "+i); 
            traceTypes[i] = -1;
            prepareMenus();
        }
        else if (i == -1)
        { 
            addTrace(Integer.parseInt(command[3]),chart,Long.parseLong(command[2]),true);
        }
        else
        {
            quickChartChange(Integer.parseInt(command[2]),chart);
        }
        
    }
    
    public boolean addTrace(int type, int chart, long key, boolean update)
    {
        try
        {
            int tracesPerChart = traces.length/popupItem.length;
            int offset =tracesPerChart*chart;
            for (int k = 0; k < tracesPerChart; k++)
            {
                if (traceTypes[offset+k] == -1)
                {
                    String streamName = msgMgr.getStreamName(key);
                    
                    try
                    {
                    traces[offset+k].setKey(streamName+" "+NetworkLogDataPoint.getTypeTitle(type,false));
                    }
                    catch (IllegalArgumentException e)
                    {
                        JOptionPane.showMessageDialog(this, "Trace has already been added to this chart\nTry selecting a different trace");
                        return false;
                    }
                    traceTypes[offset+k] = type;    
                    streamId[offset+k] = key; 
                    
                    charts[chart].getXYPlot().getRenderer().setSeriesVisible(k, Boolean.TRUE);
                    
                    if (update)
                    {
                        updateNeeded++;
                        prepareMenus();
                    }
                    return true;
                }
            }
            return false;
        }
        catch (Exception e)
        {
            Logger.getLogger("errorlog").log(Level.SEVERE, null, e);
            JOptionPane.showMessageDialog(this, "Error Adding Trace");
        }
        return false;
    
    }
    
    public boolean removeTraces(long key, boolean update)
    {
        try
        {
            int tracesPerChart = traces.length/popupItem.length;
            for (int c = 0; c < 2; c++)
            {
                int offset =tracesPerChart*c;
                for (int k = 0; k < tracesPerChart; k++)
                {
                    if ( streamId[offset+k] == key)
                    {
                        traceTypes[offset+k] = -1;    
                        streamId[offset+k] = 0; 
                        traces[offset+k].setKey("Random Data "+(offset+k)); 
                        charts[c].getXYPlot().getRenderer().setSeriesVisible(k, Boolean.FALSE);
                    }
                }
            }
            if (update)
            {
                updateNeeded++;
                prepareMenus();
            }
            return true;
        }
        catch (Exception e)
        {
            Logger.getLogger("errorlog").log(Level.SEVERE, null, e);
            JOptionPane.showMessageDialog(this, "Error Adding Trace");
        }
        return false;
    
    }

    @Override
    public void run() {
        
        long maxlength = 1000*60*60;
        long resetLength = maxlength+5000;
        int counter = 0;
        while (true)
        {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(MultiLiveLookPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            counter++;
            if (counter > 10)
            {   counter = 0;
                if (charts[0].getXYPlot().getDomainAxis().getRange().getLength() > resetLength)
                {
                    charts[0].getXYPlot().getDomainAxis().setFixedAutoRange(maxlength);
                    charts[1].getXYPlot().getDomainAxis().setFixedAutoRange(maxlength);
                }
            }
            
            /*
            if (v.getRange().getLength() > resetLength)
            {
                charts[0].getXYPlot().getDomainAxis().setFixedAutoRange(maxlength);
                charts[0].getXYPlot().getDomainAxis().setUpperBound(WIDTH);
                charts[1].getXYPlot().getDomainAxis().setFixedAutoRange(maxlength);
            }
            */
            if (updateNeeded > 0)
            {
                charts[0].setNotify(false);
                charts[1].setNotify(false);
                updatePlot();
                updateNeeded--;
                if (updateNeeded > 0)
                    updateNeeded=1;
            }
            //Only Lock when nothings going on
            if (!traceMutex.trylock())
            {
                continue;
            }
            
            long intvl = interval[intervalBox.getSelectedIndex()];
            NetworkLogDataPoint p = msgMgr.getNextPoint();
            int pointsQ = 0;
            if ((pointsQ = msgMgr.pointsInQueue()) > 20)
            {
                charts[0].setNotify(false);
                charts[1].setNotify(false);
            }
            else
            {
                charts[0].setNotify(true);
                charts[1].setNotify(true);
            }
            int count = 0;
                while (p != null)
                {
                    pointsQ--;
                    if (pointsQ == 18)
                    {
                        charts[0].setNotify(true);
                        charts[1].setNotify(true);
                    }
                    Stream stream;
                    boolean isReset;
                    boolean isShutDown;
                    boolean isNegative;
                    boolean isZero;
                    for (int i = 0; i < traceTypes.length; i++)
                    {
                        if (traceTypes[i] >= 0)
                        {
                            if (streamId[i] == 0)
                            {
                                long key = p.getStreamId();
                                String name = msgMgr.getStreamName(key);
                                if (name != null)
                                {
                                    streamId[i] = key;
                                    traces[i].setKey(name+" "+NetworkLogDataPoint.getTypeTitle(traceTypes[i],false));
                                    prepareMenus();
                                }
                                stream = msgMgr.map.get(streamId[i]);
                                isReset = stream.liveViewReset;
                                isShutDown = stream.adminState == 2;
                            	isZero = p.isZeroOrNaN();
                            	isNegative = p.isNegative();
                            }
                            else
                            {
                            	stream = msgMgr.map.get(streamId[i]);
                                isReset = stream.liveViewReset;
                                isShutDown = stream.adminState == 2;
                            	isZero = p.isZeroOrNaN();
                            	isNegative = p.isNegative();
                            }
                            
                            if (streamId[i] == p.getStreamId() && isZero && !isNegative && !isReset && !isShutDown)
                            {
                            	traces[i].add(p.getTimestamp(),p.getValueForPlot(traceTypes[i],intvl,false));
                                dataReceived = true;
                                try {
                                    if (count % 10 == 0)Thread.sleep(10);
                                    //    traces[i].setVisible(true);
                                    //    traces[i].setVisible(true);
                                } catch (InterruptedException ex) {
                                    Logger.getLogger(MultiLiveLookPanel.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                            else if(isReset) {
                            	traces[i].add(p.getTimestamp(), null);
                            }
                        }
                    }
                    
                    if(msgMgr.map.get(p.streamId).liveViewReset2)msgMgr.map.get(p.streamId).liveViewReset2 = false;
                    else if(msgMgr.map.get(p.streamId).liveViewReset1)msgMgr.map.get(p.streamId).liveViewReset1 = false;
                    p = msgMgr.getNextPoint();
                    count++;
                }
                   
            traceMutex.unlock();
        }
    }

    @Override
    public void newStreamAvailable(long key, String name) {
        
        streamKeys.put(name, key);
        boolean retVal = true;
        disconnectButton.setEnabled(true);
        if (waitDialog != null)
        {
           // retVal = waitDialog.getEnableLogging();
            //System.out.println("retVal is :" + retVal);
            //msgMgr.setEnableLogging(key,retVal);
            waitDialog.notifyConnection();
            
           // Logger.getLogger(MultiLiveLookPanel.class.getName()).log(Level.INFO, null, "Enable loging set to"+retVal);
            
        }
        
        prepareMenus();
        this.repaint();
        //return retVal;
        
    }
    
    
    public void addStreamKey(long key, String name, boolean statusOnly) {
        
    	
    	streamKeys.put(name, key);
    	
        boolean retVal = true;
        disconnectButton.setEnabled(true);
        if (waitDialog != null)
        {
           // retVal = waitDialog.getEnableLogging();
            //System.out.println("retVal is :" + retVal);
            //msgMgr.setEnableLogging(key,retVal);
            waitDialog.notifyConnection();
            
           // Logger.getLogger(MultiLiveLookPanel.class.getName()).log(Level.INFO, null, "Enable loging set to"+retVal);
            
        }
        
        prepareMenus();
        this.repaint();
        //return retVal;
        
    }
    
    public boolean getCurrentLoggingStatus() {
        boolean retVal = true;
        if (waitDialog != null)
        {
           retVal = waitDialog.enableLogging;
        }
        
        return retVal;
    
    }
     
    public void quickChartChange(int t, int i)
    {
        //2 Log
        if (t == 2)
        {
            
                LogarithmicAxis yaxis = new LogarithmicAxis("");
                yaxis.setAllowNegativesFlag(true);
                yaxis.setAxisLinePaint(Color.white);
                yaxis.setTickLabelPaint(Color.white);
                ((NumberAxis)yaxis).setNumberFormatOverride(new StaticLengthFormat(tracenumberformat, numberpad)); 
                yaxis.setLabelFont(new Font(tracefont,Font.PLAIN,tracefontsize));
                yaxis.setTickLabelFont(new Font(tracefont,Font.PLAIN,tracefontsize));
                yaxis.setAutoRange(true);
                charts[i].getXYPlot().setRangeAxis(yaxis);
                    
                /*LogAxis yaxis = new LogAxis("");
                yaxis.setAxisLinePaint(Color.white);
                yaxis.setTickLabelPaint(Color.white);
                yaxis.setLabelFont(new Font("Tahoma",Font.PLAIN,11));
                yaxis.setTickLabelFont(new Font("Tahoma",Font.PLAIN,11));
                yaxis.setMinorTickMarksVisible(true);
                yaxis.setAutoRange(true);
                yaxis.setSmallestValue(.1);
                charts[i].getXYPlot().setRangeAxis(yaxis);*/
        }
        else if (t == 1)
        {
                NumberAxis yaxis = new NumberAxis("");
                yaxis.setAxisLinePaint(Color.white);
                yaxis.setTickLabelPaint(Color.white);
                ((NumberAxis)yaxis).setNumberFormatOverride(new StaticLengthFormat(tracenumberformat, numberpad)); 
                yaxis.setLabelFont(new Font(tracefont,Font.PLAIN,tracefontsize));
                yaxis.setTickLabelFont(new Font(tracefont,Font.PLAIN,tracefontsize));
                yaxis.setAutoRange(true);
                yaxis.setAutoRangeIncludesZero(true);
                charts[i].getXYPlot().setRangeAxis(yaxis);
        }
        else
        {
                NumberAxis yaxis = new NumberAxis("");
                yaxis.setAxisLinePaint(Color.white);
                yaxis.setTickLabelPaint(Color.white);
                ((NumberAxis)yaxis).setNumberFormatOverride(new StaticLengthFormat(tracenumberformat, numberpad)); 
                yaxis.setLabelFont(new Font(tracefont,Font.PLAIN,tracefontsize));
                yaxis.setTickLabelFont(new Font(tracefont,Font.PLAIN,tracefontsize));
                yaxis.setAutoRange(true);
                yaxis.setAutoRangeIncludesZero(false);
                charts[i].getXYPlot().setRangeAxis(yaxis);
        }
    }
    
    private JFreeChart createChart(int traceOffset) {
        
    // create plot ...
    final XYSeriesCollection data = new XYSeriesCollection();
    
    for (int i = 0; i < 4; i++)
    {
        data.addSeries(traces[i+traceOffset]);
    }
    JFreeChart chart = ChartFactory.createTimeSeriesChart("", "", "", data, true, true, false);

    LegendTitle lt = chart.getLegend();
    lt.setBackgroundPaint(new java.awt.Color(24, 24, 24));
    lt.setItemPaint(Color.white);
    lt.setFrame(BlockBorder.NONE);
    lt.setItemFont(new Font("Tahoma",Font.PLAIN,11));
    
    final XYPlot plot = chart.getXYPlot();
    XYItemRenderer render = plot.getRenderer();
    for (int i = 0; i < 4; i++)
    {
        render.setSeriesVisible(i,Boolean.FALSE);
        render.setSeriesStroke(i, new BasicStroke(2));
        render.setSeriesPaint(i, traceColors[traceOffset+i]);
    }
    plot.setRenderer(render);
    
    
    plot.setBackgroundPaint(new java.awt.Color(24, 24, 24));
    plot.setDomainGridlinesVisible(true);
    plot.setDomainGridlinePaint(Color.lightGray);
    plot.setRangeGridlinesVisible(true);
    plot.setRangeGridlinePaint(Color.lightGray);
    plot.setOutlinePaint(Color.darkGray);
    
    chart.setBackgroundPaint(new java.awt.Color(24, 24, 24));
    
    DateAxis xaxis = (DateAxis)plot.getDomainAxis();
    xaxis.setAutoRange(true);
    xaxis.setAxisLinePaint(Color.white);
    xaxis.setDateFormatOverride(new SimpleDateFormat("h:mm a"));
    xaxis.setTickLabelPaint(Color.white);
    xaxis.setLabelFont(new Font("Tahoma",Font.PLAIN,11));
    xaxis.setTickLabelFont(new Font("Tahoma",Font.PLAIN,11));
    xaxis.setVerticalTickLabels(false);
    
    ValueAxis yaxis = plot.getRangeAxis();
    yaxis.setAxisLinePaint(Color.white);
    yaxis.setTickLabelPaint(Color.white);
    ((NumberAxis)yaxis).setNumberFormatOverride(new StaticLengthFormat(tracenumberformat, numberpad)); 
    yaxis.setLabelFont(new Font(tracefont,Font.PLAIN,tracefontsize));
    yaxis.setTickLabelFont(new Font(tracefont,Font.PLAIN,tracefontsize));
    yaxis.setAutoRange(true);
    plot.setRangeAxis(yaxis);
    
    return chart;

    }
    
        @Override
    public void chartMouseClicked(ChartMouseEvent event) {
        ChartEntity e = event.getEntity();
        if (e != null) {
            if (e instanceof LegendItemEntity) {
                LegendItemEntity entity = (LegendItemEntity) e;
                Comparable seriesKey = entity.getSeriesKey();
                XYPlot plot = (XYPlot) event.getChart().getPlot();
                XYDataset dataset = plot.getDataset();
                XYItemRenderer renderer = plot.getRenderer();
                
                int offset = charts[0] == event.getChart()? 0 : 4;
                
                if(!lite) {
                	for (int i = 0; i < dataset.getSeriesCount(); i++) {
                        if (dataset.getSeriesKey(i).equals(seriesKey)) {
                            Color c = (Color)renderer.getSeriesPaint(i);
                            c = JColorChooser.showDialog(this, "Change Color", c);
                            livelookconfig.put("LiveView.Trace."+(offset+i+1)+".Color",Integer.toHexString(c.getRGB()));
                            livelookconfig.save();
                            renderer.setSeriesPaint(i, c);
                            break;
                            
                        }
                    }
                }
                else {
                	for (int i = 0; i < dataset.getSeriesCount(); i++) {
                        if (dataset.getSeriesKey(i).equals(seriesKey)) {
                            Color c = (Color)renderer.getSeriesPaint(i);
                            c = JColorChooser.showDialog(this, "Change Color", c);
                            livelookconfiglite.put("LiveView.Trace."+(offset+i+1)+".Color",Integer.toHexString(c.getRGB()));
                            livelookconfiglite.save();
                            renderer.setSeriesPaint(i, c);
                            break;
                            
                        }
                    }
                }
            }
        }
    }

    @Override
    public void chartMouseMoved(ChartMouseEvent event) {

      }
    
    public void loadConfiguation()     
    {
        
        traceColors = new Color[8];
        
        traceColors[0] = Color.red;
        traceColors[1] = Color.green;
        traceColors[2] = Color.white;
        traceColors[3] = new Color(255,104,179);
        
        traceColors[4] = Color.red;
        traceColors[5] = Color.green;
        traceColors[6] = Color.white;
        traceColors[7] = new Color(255,104,179);
        
        if(!lite){
        	tracefont = (String) livelookconfig.get("Trace.FontName");
  	        tracefontsize = livelookconfig.getInt("Trace.FontSize",12);
  	        tracenumberformat = (String) livelookconfig.get("Trace.NumberFormat");
  	        numberpad = livelookconfig.getInt("Trace.NumberPad",5);
        }
        else {
        	tracefont = (String) livelookconfiglite.get("Trace.FontName");
	        tracefontsize = livelookconfiglite.getInt("Trace.FontSize",12);
	        tracenumberformat = (String) livelookconfiglite.get("Trace.NumberFormat");
	        numberpad = livelookconfiglite.getInt("Trace.NumberPad",5);
        }
        
        if (tracefont == null)
            tracefont = "Courier New";
        if (tracenumberformat == null)
            tracenumberformat = "#.###";
        
        for (int i = 0; i < 8; i++ )
        {
          try
          {
        	  String s = (lite)?(String)livelookconfiglite.get("LiveView.Trace."+(i+1)+".Color"):(String)livelookconfig.get("LiveView.Trace."+(i+1)+".Color");
              int x = (int)Long.parseLong(s, 16);
              traceColors[i] = new Color(x);
          }
          catch (Exception e)
          {
          }  
        }
        try
          {
        	int s = Integer.parseInt((lite)?(String)livelookconfiglite.get("LiveView.Interval"):(String)livelookconfig.get("LiveView.Interval"))*1000;
              for (int i = 0; i < interval.length; i++)
              {
                  if (s == interval[i])
                  {
                      loadedInterval = i;
                  }
              }
          }
          catch (Exception e)
          {
              loadedInterval = 0;
          }  
        
    }
    
   
    
    
    public void reloadConfiguation()     
    {
        
        traceColors = new Color[8];
        
        traceColors[0] = Color.red;
        traceColors[1] = Color.green;
        traceColors[2] = Color.white;
        traceColors[3] = new Color(255,104,179);
        
        traceColors[4] = Color.red;
        traceColors[5] = Color.green;
        traceColors[6] = Color.white;
        traceColors[7] = new Color(255,104,179);
        
        
        for (int i = 0; i < 8; i++ )
        {
          try
          {
        	  String s = (lite)?(String)livelookconfiglite.get("LiveView.Trace."+(i+1)+".Color"):(String)livelookconfig.get("LiveView.Trace."+(i+1)+".Color");
              int x = (int)Long.parseLong(s, 16);
              traceColors[i] = new Color(x);
          }
          catch (Exception e)
          {
          }  
        }
        
        for (int c = 0; c < 2; c++)
        {
            
            XYPlot plot = (XYPlot) charts[c].getPlot();
            XYDataset dataset = plot.getDataset();
            XYItemRenderer renderer = plot.getRenderer();

            for (int i = 0; i < dataset.getSeriesCount(); i++) {

                renderer.setSeriesPaint(i, traceColors[c*4+i]);

            }
        }
        
        loadedInterval = 0;
        try
          {
        	int s = Integer.parseInt((lite)?(String)livelookconfiglite.get("LiveView.Interval"):(String)livelookconfig.get("LiveView.Interval"))*1000;
              for (int i = 0; i < interval.length; i++)
              {
                  if (s == interval[i])
                  {
                      loadedInterval = i;
                  }
              }
          }
          catch (Exception e)
          {
          }
        
        intervalBox.setSelectedIndex(loadedInterval);
        updateNeeded++;
        
    }
    
    public void refreshStreamKeys() {
    	TreeMap<Long, Stream> map = msgMgr.map;
    	TreeMap<String, Long> sk = new TreeMap<String, Long>();
    	
    	Iterator it = map.entrySet().iterator();
    	for(int x = 0; x < map.size(); x++) {
    		Map.Entry<Long, Stream> entry = (Map.Entry<Long, Stream>) it.next();
    		sk.put(entry.getValue().streamName, entry.getKey());
    	}
    	
    	streamKeys = sk;
    	
    	if(streamKeys.size() > 0) {
    		disconnectButton.setEnabled(true);
    	}
    }
}
