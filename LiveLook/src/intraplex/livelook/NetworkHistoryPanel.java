/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package intraplex.livelook;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.script.ScriptException;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.text.html.HTMLDocument;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.LogAxis;
import org.jfree.chart.axis.LogarithmicAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.plot.DatasetRenderingOrder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.data.time.Minute;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.IntervalXYDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;


import static intraplex.livelook.IPLinkNetworkTool.config;
import static intraplex.livelook.IPLinkNetworkTool.livelookconfig;
//import static intraplex.livelook.NetworkLogDataPoint.DATA_GAP;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.text.DecimalFormat;
import java.text.Format;
import java.util.ArrayList;
import java.util.List;
import javax.swing.CellRendererPane;
import javax.swing.JColorChooser;
import javax.swing.JList;
import javax.swing.UIManager;
import org.jdesktop.swingx.JXDatePicker;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import org.jfree.chart.ChartMouseEvent;
import org.jfree.chart.ChartMouseListener;
import org.jfree.chart.entity.ChartEntity;
import org.jfree.chart.entity.LegendItemEntity;
import org.jfree.chart.renderer.xy.XYAreaRenderer;
import org.jfree.chart.renderer.xy.XYAreaRenderer2;
import org.jfree.chart.renderer.xy.XYDifferenceRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
/**
 *
 * @author jschreiv
 */
public class NetworkHistoryPanel extends javax.swing.JPanel implements ActionListener, ZoomListener, ChartMouseListener {

    /**
     * Creates new form NetworkHistoryPanel
     */
    LogFileHandler log;
    JEditorPane report;
    String [] searchParams;
    public static int[] interval = new int[] {5000,15000,30000,60000,300000,600000,900000,1200000,1800000,3600000};
    long searchWindow;
        
    int[]    traceTypes;
    JFileChooser chooser;    
    JMenu[] popupItem;
    JFreeChart[] charts;
    XYSeries  traces[];
    JSChart[] panels;
    long startingPoint;
    long foundStart;
    long foundEnd;
    boolean search;
    JSDatePanel endTime,startTime;
    Color[] traceColors;
    private String tracefont;
    private int tracefontsize;
    private String tracenumberformat;
    private int numberpad;
    public int maxDesiredPoints = 200; 
    public NetworkHistoryPanel(String f, JEditorPane r) {
        
        
        loadConfiguation();
        initComponents();
          
        endTime = new JSDatePanel();
        startTime = new JSDatePanel();
        
        endDate.setLayout(new GridLayout(1,1));
        endDate.add(endTime);
        startDate.setLayout(new GridLayout(1,1));
        startDate.add(startTime);
        
        searchWindow = 60000;
        log = new LogFileHandler(f);
        report = r;
        long firstHour = log.firstHour();
        
        if (log.loadStartAndEndTimes())
        {
            Calendar c = new GregorianCalendar();
            c.setTimeInMillis(log.getStart());
            startTime.setDate(c.getTime());
            c.setTimeInMillis(log.getEnd());
            endTime.setDate(c.getTime());
            
        }
        popupItem = new JMenu[2];
        popupItem[0] = new JMenu("Traces");
        popupItem[1] = new JMenu("Traces");
        
        traces = new XYSeries [8];
        traceTypes = new int[8];
        for (int i = 0; i < traces.length; i++)
        {
            traceTypes[i] = -1;
            traces[i] = new XYSeries("Random Data "+i); 
            traces[i].setMaximumItemCount(100000);
            //traces[i].setStroke(new BasicStroke(2));
        }
        
        charts = new JFreeChart[2];
        charts[0] = createChart(0);
        charts[1] = createChart(4);
        
        
       panels = new JSChart[2];
       for (int p =0; p < 2; p++)
       {
        panels[p] = new JSChart(charts[p], true, true, true, true, true,this);
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
        chooser = new JFileChooser("logs");
        chooser.setMultiSelectionEnabled(true);
        fileNameField.setText(f);
        addTrace(2, 0, false);
        addTrace(1, 0, false);
        addTrace(0, 1, false);
        addTrace(5, 1, true);
        

        prepareMenus();
    }
    
    public void loadConfiguation()     
    {
        searchParams = new String[]{"", "Packets Lost > 10"
        };
        traceColors = new Color[8];
        
        traceColors[0] = Color.red;
        traceColors[1] = Color.green;
        traceColors[2] = Color.white;
        traceColors[3] = new Color(255,104,179);
        
        traceColors[4] = Color.red;
        traceColors[5] = Color.green;
        traceColors[6] = Color.white;
        traceColors[7] = new Color(255,104,179);
        
    
        tracefont = (String) livelookconfig.get("Trace.FontName");
        tracefontsize = livelookconfig.getInt("Trace.FontSize",12);
        tracenumberformat = (String) livelookconfig.get("Trace.NumberFormat");
        numberpad = livelookconfig.getInt("Trace.NumberPad",5);
        //maxDesiredPoints = livelookconfig.getInt("History.MaxPoints",2000);
        maxDesiredPoints = 2000;
        
        if (tracefont == null)
            tracefont = "Courier New";
        if (tracenumberformat == null)
            tracenumberformat = "#.###";
        
        for (int i = 0; i < 8; i++ )
        {
          try
          {
              String s = (String)livelookconfig.get("History.Trace."+(i+1)+".Color");
              int x = (int)Long.parseLong(s, 16);
              traceColors[i] = new Color(x);
          }
          catch (Exception e)
          {
          }  
        }
        try
        {
            searchParams[0] = config.get("Search.Query").toString();
            for (int i = 1; i < 4; i++)
                searchParams[i] = livelookconfig.get("Search.Query."+i).toString();
        }
        catch (Exception e)
        {
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
              String s = (String)livelookconfig.get("History.Trace."+(i+1)+".Color");
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
                    JCheckBoxMenuItem menuItem = new JCheckBoxMenuItem(NetworkLogDataPoint.getTypeTitle(traceTypes[offset+i],log.pps));
                    menuItem.setActionCommand(p+":"+(offset+i));
                    menuItem.setSelected(true);
                    menuItem.addActionListener(this);
                    popupItem[p].add(menuItem);
                }
            }
            if (tracesAdded < tracesPerChart)
            {
                   JMenu addMenu = new JMenu("Add Trace");
                        String base = p+":-1:";
                        for (int j = 0; j < NetworkLogDataPoint.NUM_TRACES; j++)
                        {
                            JMenuItem menuItem2 = new JMenuItem(NetworkLogDataPoint.getTypeTitle(j,log.pps));
                            menuItem2.setActionCommand(base+j);
                            menuItem2.addActionListener(this);
                            addMenu.add(menuItem2);
                        
                        }
                    popupItem[p].add(addMenu);
            }
        }
    }
    
    //Show the most points, but limit to 200
    private long getBestInterval(long start, long end)
    {
        return getBestInterval(end - start);
    }
    
    private long getBestInterval(long difference)
    {
        
        for (int i = 0; i < interval.length; i++)
        {
            if (difference/interval[i] < maxDesiredPoints)
            {
                return interval[i];
            }
        }
        return interval[interval.length - 1];
    }
    
    public void updatePlot()
    {
        long start = startTime.getDate().getTime();
        long end = endTime.getDate().getTime();
        
        long intvl;
        if (search)
        {
           start = foundStart;
           end = foundEnd;
           intvl = getBestInterval(start,end);
        }
        else
        {
            intvl = getBestInterval(start,end);
            start = start - start%intvl;
            end = end - (end%intvl);


            Calendar c = new GregorianCalendar();
            c.setTimeInMillis(start);
            startTime.setDate(c.getTime());
            c.setTimeInMillis(end);
            endTime.setDate(c.getTime());
        }
        
        intervalSize.setText("Showing "+intvl/1000+" second intervals");
        //zoomableChart1.zoomAll(); TODO
        //zoomableChart2.zoomAll();
        //log.load(plotComboBox.getSelectedIndex(), start-intvl/2,
        //    end+intvl/2, intvl, 1, trace);
        
        int estimatedPoints = (int)((end - start)/intvl);
        int pointsPerInterval = 1;
        if (estimatedPoints >= 1 && estimatedPoints < 50)
        {
            //Add Some Filler
            pointsPerInterval = 100/estimatedPoints;
            
            if (pointsPerInterval > (int)(intvl/5000))
            {
                pointsPerInterval = (int)(intvl/5000);
            }
        }
        log.load(traceTypes, start-intvl/2,
            end+intvl/2, intvl, pointsPerInterval, traces);
        //zoomableChart2.setSynchronizedXStartChart(zoomableChart1); TODO
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.JLabel jLabel1 = new javax.swing.JLabel();
        javax.swing.JLabel jLabel2 = new javax.swing.JLabel();
        fileNameField = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        loadFileButton = new javax.swing.JButton();
        reloadButton = new javax.swing.JButton();
        exportButton = new javax.swing.JButton();
        generateReport = new javax.swing.JButton();
        findButton = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        firstButton = new javax.swing.JButton();
        jSplitPane1 = new javax.swing.JSplitPane();
        chart1Container = new javax.swing.JPanel();
        chart2Container = new javax.swing.JPanel();
        intervalSize = new javax.swing.JLabel();
        searchBox = new javax.swing.JComboBox();
        endDate = new javax.swing.JPanel();
        startDate = new javax.swing.JPanel();
        updateButton = new javax.swing.JButton();

        jLabel1.setText("Start");

        jLabel2.setText("End");

        fileNameField.setText("log.csv");
        fileNameField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileNameFieldActionPerformed(evt);
            }
        });

        jLabel3.setText("File");

        loadFileButton.setText("Browse");
        loadFileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadFileButtonActionPerformed(evt);
            }
        });

        reloadButton.setText("Reload");
        reloadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reloadButtonActionPerformed(evt);
            }
        });

        exportButton.setText("Export");
        exportButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exportButtonActionPerformed(evt);
            }
        });

        generateReport.setText("Generate Report");
        generateReport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                generateReportActionPerformed(evt);
            }
        });

        findButton.setText("Find It For Me!");
        findButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                findButtonActionPerformed(evt);
            }
        });

        jLabel5.setText("Search");

        firstButton.setText("Go");
        firstButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                firstButtonActionPerformed(evt);
            }
        });

        jSplitPane1.setDividerLocation(200);
        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        javax.swing.GroupLayout chart1ContainerLayout = new javax.swing.GroupLayout(chart1Container);
        chart1Container.setLayout(chart1ContainerLayout);
        chart1ContainerLayout.setHorizontalGroup(
            chart1ContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        chart1ContainerLayout.setVerticalGroup(
            chart1ContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jSplitPane1.setLeftComponent(chart1Container);

        javax.swing.GroupLayout chart2ContainerLayout = new javax.swing.GroupLayout(chart2Container);
        chart2Container.setLayout(chart2ContainerLayout);
        chart2ContainerLayout.setHorizontalGroup(
            chart2ContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        chart2ContainerLayout.setVerticalGroup(
            chart2ContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jSplitPane1.setBottomComponent(chart2Container);

        intervalSize.setText("Showing X Second Intervals");

        searchBox.setEditable(true);
        searchBox.setModel(new javax.swing.DefaultComboBoxModel(searchParams));

        javax.swing.GroupLayout endDateLayout = new javax.swing.GroupLayout(endDate);
        endDate.setLayout(endDateLayout);
        endDateLayout.setHorizontalGroup(
            endDateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 131, Short.MAX_VALUE)
        );
        endDateLayout.setVerticalGroup(
            endDateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout startDateLayout = new javax.swing.GroupLayout(startDate);
        startDate.setLayout(startDateLayout);
        startDateLayout.setHorizontalGroup(
            startDateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 131, Short.MAX_VALUE)
        );
        startDateLayout.setVerticalGroup(
            startDateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 24, Short.MAX_VALUE)
        );

        updateButton.setText("Update");
        updateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSplitPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(searchBox, javax.swing.GroupLayout.PREFERRED_SIZE, 388, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(firstButton, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(startDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(endDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(updateButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(generateReport)))
                        .addGap(0, 319, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(fileNameField, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(loadFileButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(reloadButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(findButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(exportButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(intervalSize)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(fileNameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(loadFileButton)
                    .addComponent(reloadButton)
                    .addComponent(findButton)
                    .addComponent(exportButton)
                    .addComponent(intervalSize))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSplitPane1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(firstButton)
                    .addComponent(searchBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(startDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(endDate, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(updateButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(generateReport))
                        .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING))
                    .addComponent(jLabel1))
                .addGap(25, 25, 25))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void updateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateButtonActionPerformed
        search = false;
        
        for (int p =0; p < 2; p++)
        {
         panels[p].setZoomed(false);
        }
        updatePlot();
    }//GEN-LAST:event_updateButtonActionPerformed

    private void fileNameFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fileNameFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fileNameFieldActionPerformed

    private void loadFileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadFileButtonActionPerformed

        int returnVal = chooser.showOpenDialog(this);
        
        if(returnVal == JFileChooser.APPROVE_OPTION) 
        {
            if(!loadFiles(chooser.getSelectedFiles(),0,0)){
                JOptionPane.showMessageDialog(null,"File cannot be loaded!", "Error",
                                JOptionPane.ERROR_MESSAGE);
            }
            else
            {
                JOptionPane.showMessageDialog(null,"File Loaded succeefully", "Message",
                                JOptionPane.INFORMATION_MESSAGE);
            }
    }//GEN-LAST:event_loadFileButtonActionPerformed
   }
    /**
     *
     * @param f
     * @param s
     * @param e
     * @return
     */
    public boolean loadFiles(File[] f, long s, long e)
    {
        boolean bRet = false;
        try 
        {
            log.setFiles(f);
            String list = f[0].getName();
            for (int i = 1; i < f.length; i++)
            {
                list += ", "+f[i].getName();
            }
            fileNameField.setText(list);
            if (log.reload())
            {
                Calendar c = new GregorianCalendar();
                if (!(s > 0 && s > log.getStart()))
                {
                    s = log.getStart();
                }
                c.setTimeInMillis(s);
                startTime.setDate(c.getTime());
                
                if (!(e > 0 && e < log.getEnd()))
                {
                    e = log.getEnd();
                }
                
                c.setTimeInMillis(e);
                endTime.setDate(c.getTime());
                search = false;
                updatePlot();
                bRet = true;
            }
            else
            {
                JOptionPane.showMessageDialog(null,"Files do not contain data or are not from the same stream!", "Error",
                                JOptionPane.ERROR_MESSAGE);
            }
        } 
        catch (Exception ex) {
           JOptionPane.showMessageDialog(null,"Files do not contain data or are not from the same stream!", "Error",
                                JOptionPane.ERROR_MESSAGE);
        }
        return bRet;
    }
    private void reloadButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reloadButtonActionPerformed

                try {
                    panels[0].restoreAutoBounds();
                    panels[1].restoreAutoBounds();
                    if (log.reload())
                    {
                        Calendar c = new GregorianCalendar();
                        c.setTimeInMillis(log.getStart());
                        startTime.setDate(c.getTime());
                        c.setTimeInMillis(log.getEnd());
                        endTime.setDate(c.getTime());
                        search = false;
                        updatePlot();
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null,"File does not appear to Have any Network Logger Data!", "Error",
                                        JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {
                   JOptionPane.showMessageDialog(null,"File does not appear to Have any Network Logger Data!", "Error",
                                        JOptionPane.ERROR_MESSAGE);
                }

        
    }//GEN-LAST:event_reloadButtonActionPerformed

    private void exportButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exportButtonActionPerformed
         int returnVal = chooser.showSaveDialog(this);
        if(returnVal == JFileChooser.APPROVE_OPTION) 
        {
           long start = startTime.getDate().getTime();
           long end = endTime.getDate().getTime();
           long intvl = getBestInterval(start, end);

           start = start - start%intvl;
           end = end - (end%intvl);

           //log.load(plotComboBox.getSelectedIndex(), start-intvl/2,
           //    end+intvl/2, intvl, 1, trace);

           int estimatedPoints = (int)((end - start)/intvl);
           int pointsPerInterval = 1;
           if (estimatedPoints < 50)
           {
               //Add Some Filler
               pointsPerInterval = 100/estimatedPoints;

               if (pointsPerInterval > (int)(intvl/6000))
               {
                   pointsPerInterval = (int)(intvl/6000);
               }
           }
           else if (estimatedPoints > 50000)
           {
               JOptionPane.showMessageDialog(null,"Interval is too small for range selected,\nentire range may not be able to be shown!", "Warning",
                               JOptionPane.WARNING_MESSAGE);
           }

           String saveFile = chooser.getSelectedFile().getPath();
           if (!saveFile.endsWith(".csv"))
           {
               saveFile = saveFile + ".csv";
           }
           log.save(traceTypes, start-intvl/2,end+intvl/2, intvl, pointsPerInterval,saveFile);
        }
    }//GEN-LAST:event_exportButtonActionPerformed

    private void generateReportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_generateReportActionPerformed
        

        long start = startTime.getDate().getTime();
        long end = endTime.getDate().getTime();
        if (search)
        {
            start = foundStart;
            end = foundEnd;
        }
        
        long intvl = getBestInterval(start, end);
        
      
        start = start - start%intvl;
        end = end - (end%intvl);

        Calendar c = new GregorianCalendar();
        c.setTimeInMillis(start);
        startTime.setDate(c.getTime());
        c.setTimeInMillis(end);
        endTime.setDate(c.getTime());
        //log.load(plotComboBox.getSelectedIndex(), start-intvl/2,
        //    end+intvl/2, intvl, 1, trace);
        
        int estimatedPoints = (int)((end - start)/intvl);
        int pointsPerInterval = 1;
        if (estimatedPoints >= 1 && estimatedPoints < 50)
        {
            //Add Some Filler
            pointsPerInterval = 100/estimatedPoints;
            
            if (pointsPerInterval > (int)(intvl/5000))
            {
                pointsPerInterval = (int)(intvl/5000);
            }
        }
        else if (estimatedPoints > 50000)
        {
            JOptionPane.showMessageDialog(null,"Interval is too small for range selected,\nentire range may not be able to be shown!", "Warning",
                            JOptionPane.WARNING_MESSAGE);
        }
        
        String reportname = "";
        if(log.gapTimes.size() > 0) {
        	reportname = ReportBuilder.generateReport(start-intvl/2,end+intvl/2, intvl, pointsPerInterval,log, log.gapTimes.size());
        }
        else 
        {
        	reportname = ReportBuilder.generateReport(start-intvl/2,end+intvl/2, intvl, pointsPerInterval,log);
        }
        File f = new File(reportname).getAbsoluteFile();
        
        try {
            report.setDocument(new HTMLDocument());
            report.setPage(f.toURI().toURL());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        JOptionPane.showMessageDialog(this, "Report has been saved to "+f.getAbsolutePath()+"\nReport can be viewed in the report tab");
    }//GEN-LAST:event_generateReportActionPerformed

    private void findButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_findButtonActionPerformed
        
                FindItForMe dialog = new FindItForMe(null, true);
                dialog.setVisible(true);
                
                File[] files = dialog.getResults();
                if (files != null)
                {
                    if (files.length == 0 )
                    {
                        JOptionPane.showMessageDialog(this, "Could Not Find any Files!");
                    }
                    else
                    {
                        loadFiles(files, dialog.getStarttime(), dialog.getEndtime());
                    }
                }
    }//GEN-LAST:event_findButtonActionPerformed

    private void firstButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_firstButtonActionPerformed
        findFirst();
    }//GEN-LAST:event_firstButtonActionPerformed
                                   
    public void findNext()
    {
        //Start the Search Half way through current interval
        
        long intvl = getBestInterval(searchWindow);
        long start;
        if (search)
        {
            start = foundEnd/2 + foundStart/2 + intvl*3/2;
        }
        else
        {
            start = startTime.getDate().getTime();
        }
            
        long end = endTime.getDate().getTime();
        search(start,end,true);
    }

    public void findPrev()
    {
        //Start the Search Half way through current interval
        
        long intvl = getBestInterval(searchWindow);
        long end;
        if (search)
        {
            end = foundEnd/2 + foundStart/2 -  intvl*3/2;
        }
        else
        {
            end = endTime.getDate().getTime();
        }
            
        long start = startTime.getDate().getTime();
        search(start,end,false);
    }
        
    
    public void findFirstAfter(long s)
    {
        long start = s;
        long end = endTime.getDate().getTime();
        
        search(start,end,true,"");
    }
    
    public void findFirst()
    {
        long start = startTime.getDate().getTime();
        long end = endTime.getDate().getTime();
        search(start,end,true);
    }
    
    public void findLast()
    {
        long start = startTime.getDate().getTime();
        long end = endTime.getDate().getTime();
        search(start,end,false);
    }   
    
    public void search(long start, long end, boolean forward)
    {
        search(start, end, forward, null);
    }
    
    public void search(long start, long end, boolean forward, String query)
    {
        
        String expression = searchBox.getSelectedItem().toString();
        if (query != null)
            expression = query;
       config.put("Search.Query",expression);
       config.save();
       boolean emptySearch = false;
       if (expression.isEmpty())
       {
           expression = "true";
           emptySearch = true;
       }
       
        if (!log.validateSearchExpression(expression))
        {
          JOptionPane.showMessageDialog(this, "Search Expression is not valid");
          return;
        }
        
        
        long intvl = getBestInterval(searchWindow);
        Object[] results = new Object[3];
            
        start = start - start%intvl;
        end = end - (end%intvl);
        if (forward)
        {
            if (foundEnd <= end && log.find(start, end, intvl, expression, results))
            {
                long pad2 = searchWindow/2;
                
                long newfoundStart = ((Long)results[0]).longValue()-pad2;
                long newfoundEnd = ((Long)results[0]).longValue()+pad2;
                
                if (emptySearch)
                {
                    if (newfoundStart < start)
                    {
                        newfoundStart = start;
                    }
                    if (newfoundEnd > end)
                    {
                        newfoundEnd = end;
                    }
                }
                
                if (newfoundEnd - newfoundStart < pad2)
                {
                    Toolkit.getDefaultToolkit().beep();
                    
                    JOptionPane.showMessageDialog(this, "No Results Found");
                }
                else
                {
                    foundEnd = newfoundEnd;
                    foundStart = newfoundStart;
                    search = true;
                    for (int p =0; p < 2; p++)
                    {
                     panels[p].setZoomed(true);
                    }
                    updatePlot();
                }
            }
            else
            {
                
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(this, "No Results Found");
                        
            }
        }
        else
        {
            if (foundStart >= start && log.findReverse(start, end, intvl, expression, results))
            {

                long pad2 = searchWindow/2;
                
                long newfoundStart = ((Long)results[0]).longValue()-pad2;
                long newfoundEnd = ((Long)results[0]).longValue()+pad2;
                
                if (emptySearch)
                {
                    if (newfoundStart < start)
                    {
                        newfoundStart = start;
                    }
                    if (newfoundEnd > end)
                    {
                        newfoundEnd = end;
                    }
                }
                
                if (newfoundEnd - newfoundStart < pad2)
                {
                    Toolkit.getDefaultToolkit().beep();
                    JOptionPane.showMessageDialog(this, "No Results Found");
                }
                else
                {
                    foundEnd = newfoundEnd;
                    foundStart = newfoundStart;
                    
                    for (int p =0; p < 2; p++)
                    {
                     panels[p].setZoomed(true);
                    }
                    updatePlot();
                }
            }
            else
            {
                
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(this, "No Results Found");
            }
        }
        
        
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel chart1Container;
    private javax.swing.JPanel chart2Container;
    private javax.swing.JPanel endDate;
    private javax.swing.JButton exportButton;
    private javax.swing.JTextField fileNameField;
    private javax.swing.JButton findButton;
    private javax.swing.JButton firstButton;
    private javax.swing.JButton generateReport;
    private javax.swing.JLabel intervalSize;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JButton loadFileButton;
    private javax.swing.JButton reloadButton;
    private javax.swing.JComboBox searchBox;
    private javax.swing.JPanel startDate;
    private javax.swing.JButton updateButton;
    // End of variables declaration//GEN-END:variables

    @Override
    public void actionPerformed(ActionEvent ae) {
        String[] command = ae.getActionCommand().split(":");
        int i = Integer.parseInt(command[1]);
        int chart = Integer.parseInt(command[0]);   
        if (i >= 0)
        {
            traces[i].clear();
            if (chart == 0)
            {
                charts[chart].getXYPlot().getRenderer().setSeriesVisible(i, Boolean.FALSE);
                charts[chart].getXYPlot().getRenderer(1).setSeriesVisible(i, Boolean.FALSE);
            }
            else
            {
                charts[chart].getXYPlot().getRenderer().setSeriesVisible(i-4, Boolean.FALSE);
                charts[chart].getXYPlot().getRenderer(1).setSeriesVisible(i-4, Boolean.FALSE);
            }
            traces[i].setKey("Random Data "+i); 
            traceTypes[i] = -1;
            prepareMenus();
        }
        else if (i == -1)
        {
            addTrace(Integer.parseInt(command[2]),chart,true);
        }
        else
        {
            quickChartChange(Integer.parseInt(command[2]),chart);
        }
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
    
    public boolean addTrace(int type, int chart, boolean update)
    {
        try
        {
            int tracesPerChart = traces.length/popupItem.length;
            int offset =tracesPerChart*chart;
            for (int k = 0; k < tracesPerChart; k++)
            {
                if (traceTypes[offset+k] == -1)
                {
                    //traces[offset+k].
                    try
                    {
                        traces[offset+k].setKey(NetworkLogDataPoint.getTypeTitle(type,log.pps)); 
                    }
                    catch (IllegalArgumentException e)
                    {
                        JOptionPane.showMessageDialog(this, "Trace has already been added to this chart\nTry selecting a different trace");
                        return false;
                    }
                    traceTypes[offset+k] = type;   
                    charts[chart].getXYPlot().getRenderer().setSeriesVisible(k, Boolean.TRUE);
                    
                    if (update)
                    {
                        updatePlot();
                        prepareMenus();
                    }
                    return true;
                }
            }
        }
        catch (Exception e)
        {
           Logger.getLogger("errorlog").log(Level.SEVERE, null, e);
           JOptionPane.showMessageDialog(this, "Error Adding Trace");
        }
        
        return false;
        
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
    plot.setDataset(1, data); 
    XYItemRenderer render = plot.getRenderer();
   // XYAreaRenderer render = new XYAreaRenderer();
    
    //render.setPositivePaint(new Color(255,0,0,64));
    //render.setNegativePaint(new Color(255,0,0,64));
    
    for (int i = 0; i < 4; i++)
    {
        render.setSeriesVisible(i,Boolean.FALSE);
        render.setSeriesStroke(i, new BasicStroke(2));
        render.setSeriesPaint(i, traceColors[traceOffset+i]);
    }
    plot.setRenderer(render);
    
    XYAreaRenderer render2 = new XYAreaRenderer(); 
    render2.setBaseFillPaint(new Color(255,0,0,128));
    
    for (int i = 0; i < 4; i++)
    {
        Color c = new Color(traceColors[traceOffset+i].getRed(),
                            traceColors[traceOffset+i].getGreen(),
                            traceColors[traceOffset+i].getBlue(),128);
        render2.setSeriesVisible(i,Boolean.FALSE);
        render2.setSeriesStroke(i, new BasicStroke(2));
        render2.setSeriesPaint(i,c);
    }
    
    plot.setRenderer(1, render2); 
    plot.setDatasetRenderingOrder(DatasetRenderingOrder.REVERSE); 
    
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
    xaxis.setDateFormatOverride(new SimpleDateFormat("MM/dd h:mm a"));
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
    public void zoom(long s, long e) {
        long end = endTime.getDate().getTime();
        if (e > end)
            e = end;
        foundEnd = end;
        searchWindow = (e - s)*2;
        this.findFirstAfter(s);
        
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
                XYItemRenderer renderer2 = plot.getRenderer(1);
                
                int offset = charts[0] == event.getChart()? 0 : 4;
                
                for (int i = 0; i < dataset.getSeriesCount(); i++) {
                    if (dataset.getSeriesKey(i).equals(seriesKey)) {
                        Color c = (Color)renderer.getSeriesPaint(i);
                        c = JColorChooser.showDialog(this, "Change Color", c);
                        livelookconfig.put("History.Trace."+(offset+i+1)+".Color",Integer.toHexString(c.getRGB()));
                        livelookconfig.save();
                        renderer.setSeriesPaint(i, c);
                        
                        Color cA = new Color(c.getRed(),
                            c.getGreen(),
                            c.getBlue(),128);
                        renderer2.setSeriesPaint(i, cA);
                        
                        break;
                        
                    }
                }
            }
        }
    }

    @Override
    public void chartMouseMoved(ChartMouseEvent event) {

      }
    
    
}
