/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package intraplex.livelook;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.title.LegendTitle;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 *
 * @author jschreiv
 */
public class ReportBuilder {
    
    
    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd-hh_mm_ss_aa");
    static String chartname;
    public static String loadTemplate(String file)
    {
        BufferedReader reader = null;
        String result = "";
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String s = null;
            while ((s = reader.readLine())!=null)
            {
                result+=s+"\n";
            }
            reader.close();
        } catch (Exception ex) {
            Logger.getLogger(ReportBuilder.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return result;
    }
    
    public static String loadTemplate(String file, int gaps)
    {
        BufferedReader reader = null;
        String result = "";
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String s = null;
            while ((s = reader.readLine())!=null)
            {
                result+=s+"\n";
            }
            reader.close();
        } catch (Exception ex) {
            Logger.getLogger(ReportBuilder.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        int startIndex = result.indexOf("<html>");
        int endIndex = result.indexOf("</html>");
        String temp = "" + result.substring(0, startIndex + 6);
        for(int x = 0; x <= gaps / 2; x++) {
        	temp += "<h1>Section " +  (x + 1) + "</h1><br>";
        	temp += result.substring(startIndex + 6, endIndex);
        	temp += "<br>";
        }
        
        return temp += result.substring(endIndex);
    }
    
        public static void saveReport(String file,String report)
    {
           BufferedWriter writer = null;
           String absolute = new File(file).getAbsolutePath();
           try
           {
               writer = new BufferedWriter( new FileWriter(absolute));
               writer.write( report);
               writer.close( );
           }
           catch ( IOException e)
           {
           }
    }
        
    public static String generateReport(long starttime, long endtime, long interval, int pointsPerInterval, LogFileHandler log)
    {
        String base = loadTemplate("C:/Users/jlucas/LiveLook NetXpress/templates/report.html");
        chartname = "chart"+sdf.format(System.currentTimeMillis());
        //Ok we have the base get chart images
       //System.out.println("The start Time is " +starttime+" and the end time is "+  endtime);
        int index = -1;
        int imgRef = 1;
        while ((index = base.indexOf("<livelookchart>")) != -1)
        {
            int endindex = base.indexOf("</livelookchart>");
            if (endindex == -1)break;
            String start = base.substring(0,index);
            String inner = base.substring(index+15,endindex);
            String end = base.substring(endindex+16,base.length());
            String image = generateChart(inner,starttime,endtime, interval, pointsPerInterval, log,imgRef);
            base = start + image +end;
            imgRef++;
            
        }

        NetworkModelAnalyizer nma = new NetworkModelAnalyizer(starttime, endtime, interval, pointsPerInterval, log);
        nma.loadBasicBurstStats();
        
        while ((index = base.indexOf("<livelookstat>")) != -1)
        {
            int endindex = base.indexOf("</livelookstat>");
            if (endindex == -1)break;
            String start = base.substring(0,index);
            String inner = base.substring(index+14,endindex);
            String end = base.substring(endindex+15,base.length());
            
            if(inner.equals("Start Time"))
            	base = start + (new Date((new Timestamp(starttime)).getTime())).toString() + end;
            else if(inner.equals("End Time"))
            	base = start + (new Date((new Timestamp(endtime)).getTime())).toString() + end;
            else
            base = start + nma.getValue(inner) +end;

            imgRef++;
            
        }
        
        String reportname = "generated/report"+sdf.format(System.currentTimeMillis())+".html";
        saveReport(reportname,base);
        return reportname;
    }
    
    public static String generateReport(long starttime, long endtime, long interval, int pointsPerInterval, LogFileHandler log, int gaps)
    {
        String base = loadTemplate("C:/Users/jlucas/LiveLook NetXpress/templates/report.html", gaps);
        chartname = "chart"+sdf.format(System.currentTimeMillis());
        //Ok we have the base get chart images
       //System.out.println("The start Time is " +starttime+" and the end time is "+  endtime);
        
        for(int x = 0; x <= gaps / 2; x++){
        	String temp = "<h1>Section " +  (x + 1) + "</h1><br>";
        	String temp2 = "<br><h1>Section " +  (x + 2) + "</h1><br>";
	        int index = base.indexOf(temp) + temp.length();
	        int endIndex = base.indexOf(temp2, index);
	        int imgRef = 1;
	        while ((index = base.indexOf("<livelookchart>")) != -1 && (base.indexOf("<livelookchart>") > endIndex || endIndex == -1))
	        {
	            int endindex = base.indexOf("</livelookchart>");
	            if (endindex == -1)break;
	            String start = base.substring(0,index);
	            String inner = base.substring(index+15,endindex);
	            String end = base.substring(endindex+16,base.length());
	            String image = generateChart(inner,starttime,endtime, interval, pointsPerInterval, log,imgRef);
	            base = start + image +end;
	            endIndex = base.indexOf(temp2, index);
	            imgRef++;
	            
	        }
	        long tempStart = 0;
	        long tempEnd = 0;
	        
	        if(x == 0){
	        	tempStart = starttime;
	        	tempEnd = log.gapTimes.get(x);
	        }
	        else if(x == gaps / 2){
	        	tempStart = log.gapTimes.get(x);
	        	tempEnd = endtime;
	        }
	        else {
	        	tempStart = log.gapTimes.get(x);
	        	tempEnd = log.gapTimes.get(x + 1);
	        }
	        
	        NetworkModelAnalyizer nma = new NetworkModelAnalyizer(tempStart, tempEnd, interval, pointsPerInterval, log);

	        nma.loadBasicBurstStats();
	        while ((index = base.indexOf("<livelookstat>")) != -1 && (base.indexOf("<livelookstat>") < endIndex || endIndex == -1))
	        {
	            int endindex = base.indexOf("</livelookstat>");
	            if (endindex == -1)break;
	            String start = base.substring(0,index);
	            String inner = base.substring(index+14,endindex);
	            String end = base.substring(endindex+15,base.length());

	            if(inner.equals("Start Time"))
	            	base = start + (new Date((new Timestamp(tempStart)).getTime())).toString() + end;
	            else if(inner.equals("End Time"))
	            	base = start + (new Date((new Timestamp(tempEnd)).getTime())).toString() + end;
	            else
	            base = start + nma.getValue(inner) +end;
	            endIndex = base.indexOf(temp2, index);
	            imgRef++;
	            
	        }
        }
        String reportname = "generated/report"+sdf.format(System.currentTimeMillis())+".html";
        saveReport(reportname,base);
        return reportname;
    }
    
    //Change to Binary Search Eventually
    public static int recommendedTimeDiversity(BurstEntry[] bins, int packets, int isolost, int minburst, int maxburst, double acceptableLoss)
    {
        double isolosses = Math.pow((double)isolost/packets,2)*packets;
        for (int d = minburst; d <= maxburst; d++)
        {
            double burstlosses = 0;
            for (int i = 0; i < bins.length; i++)
            {
                if (d < bins[i].burstsize)
                {
                     double avgLoss =   Math.pow(bins[i].burstdensity,2)*(bins[i].burstsize - d);
                     burstlosses += (avgLoss * bins[i].count);
                }
            }
            if (acceptableLoss > 100*(burstlosses+isolosses)/packets) return d;
        }
        
        return maxburst+1;
    }
    
    public static String generateChart(String html,long starttime, long endtime, long interval, int pointsPerInterval, LogFileHandler log,int count)
    {
        int[] types = getTypes(html);
        

        XYSeries[] traces = new XYSeries [types.length];
         // create plot ...
        final XYSeriesCollection data = new XYSeriesCollection();
    
        for (int i = 0; i < types.length; i++)
        { 
            if (types[i] != -1)
            {
                traces[i] = new XYSeries(NetworkLogDataPoint.getTypeTitle(types[i],false)); 
                traces[i].setMaximumItemCount(100000);
                data.addSeries(traces[i]);
            }
            else
            {
                traces[i] = new XYSeries("Random "+i); 
                traces[i].setMaximumItemCount(100000);
                data.addSeries(traces[i]);
            }
        }
        
        JFreeChart chart = ChartFactory.createTimeSeriesChart("", "", "", data, true, true, false);

        LegendTitle lt = chart.getLegend();
        lt.setBackgroundPaint(Color.white);
        lt.setItemPaint(Color.black);
        lt.setFrame(BlockBorder.NONE);
        lt.setItemFont(new Font("Tahoma",Font.PLAIN,11));

        final XYPlot plot = chart.getXYPlot();
        for (int i = 0; i < types.length; i++)
        {
            if (types[i] == -1)
            {
            plot.getRenderer().setSeriesVisible(i,Boolean.FALSE);
            }
            plot.getRenderer().setSeriesStroke(i, new BasicStroke(2));
        }
        plot.getRenderer().setSeriesPaint(0, Color.red);
        plot.getRenderer().setSeriesPaint(1, Color.green);
        plot.getRenderer().setSeriesPaint(2, Color.white);
        plot.getRenderer().setSeriesPaint(3, new Color(255,104,179));

        plot.setBackgroundPaint(Color.white);
        plot.setDomainGridlinesVisible(true);
        plot.setDomainGridlinePaint(Color.darkGray);
        plot.setRangeGridlinesVisible(true);
        plot.setRangeGridlinePaint(Color.darkGray);
        plot.setOutlinePaint(Color.lightGray);

        chart.setBackgroundPaint(Color.white);

        DateAxis xaxis = (DateAxis)plot.getDomainAxis();
        xaxis.setAutoRange(true);
        xaxis.setAxisLinePaint(Color.black);
        
        
        
        int intIndex = 0;
        if ((intIndex = html.indexOf("<interval>")) != -1)
        {
            int windowEndIndex = html.indexOf("</interval>");
            
            String start = html.substring(0,intIndex);
            long newInterval = getTimeInterval(html.substring(intIndex+10,windowEndIndex));
            String end = html.substring(windowEndIndex+11,html.length());
            
            html = start +end;
            if (newInterval != 0)
            {
                interval = newInterval;
            }
        }
        
        int timeIndex = 0;
        String timeformat = "h:mm a";
        if ((timeIndex = html.indexOf("<timeformat>")) != -1)
        {
            int windowEndIndex = html.indexOf("</timeformat>");
            
            
            String start = html.substring(0,timeIndex);
            timeformat = html.substring(timeIndex+12,windowEndIndex);
            String end = html.substring(windowEndIndex+13,html.length());
            
            html = start +end;
        }
        
        xaxis.setDateFormatOverride(new SimpleDateFormat(timeformat));
        
        
        
        
        xaxis.setTickLabelPaint(Color.black);
        xaxis.setLabelFont(new Font("Tahoma",Font.PLAIN,11));
        xaxis.setTickLabelFont(new Font("Tahoma",Font.PLAIN,11));
        xaxis.setVerticalTickLabels(false);

        ValueAxis yaxis = plot.getRangeAxis();
        yaxis.setAxisLinePaint(Color.black);
        yaxis.setTickLabelPaint(Color.black);
        yaxis.setLabelFont(new Font("Tahoma",Font.PLAIN,11));
        yaxis.setTickLabelFont(new Font("Tahoma",Font.PLAIN,11));
        yaxis.setAutoRange(true);
        plot.setRangeAxis(yaxis);
        
        
        int windowIndex = 0;
        if ((windowIndex = html.indexOf("<window>")) != -1)
        {
            int windowEndIndex = html.indexOf("</window>");
            
            long pad = 30000; //30Seconds
            String start = html.substring(0,windowIndex);
            String inner = html.substring(windowIndex+8,windowEndIndex);
            String end = html.substring(windowEndIndex+9,html.length());
            int padIndex = 0;
            if ((padIndex = inner.indexOf("<pad>")) != -1)
            {
                int padEndIndex = inner.indexOf("</pad>");

                String startp = inner.substring(0,padIndex);
                String innerp = inner.substring(padIndex+5,padEndIndex);
                String endp = inner.substring(padEndIndex+6,inner.length());
                long newpad = getTimeInterval(innerp);
                if (newpad > 0)
                {
                    pad = newpad;
                }
                inner = startp+endp;
            }
            
            
            html = start +end;
            
            if (inner.contains("Greatest"))
            {
                String expression = inner.replace("Greatest", "").trim();
                Object[] results = new Object[3];
                if (!log.greatest(starttime, endtime, interval, expression, results))
                {
                    return "Error generating chart";
                }

                long offset = Math.max(interval*2, pad);
                starttime = ((Long)results[0]).longValue()-offset;
                endtime = ((Long)results[1]).longValue()+offset;
                
                pointsPerInterval = (int)(interval/5000);
            }
            if (inner.contains("Longest"))
            {
                String expression = inner.replace("Longest", "").trim();
                Object[] results = new Object[3];
                if (!log.longest(starttime, endtime, interval, expression, results))
                {
                    return "Error generating chart";
                }

                long offset = Math.max(interval*2, pad);
                starttime = ((Long)results[0]).longValue()-offset;
                endtime = ((Long)results[1]).longValue()+offset;
                
                pointsPerInterval = (int)(interval/5000);
            }            
        }
        log.load(types, starttime,
            endtime, interval, pointsPerInterval, traces);
        
        BufferedImage bi = new BufferedImage(750, 200,2);
        try {
            ChartUtilities.saveChartAsPNG(new File("generated/"+chartname+count+".png").getAbsoluteFile(),
                    chart,750,200,null,false, 0x1);
        } catch (IOException ex) {
            Logger.getLogger(ReportBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "<img src=\""+chartname+count+".png\"></img>";
    }
    
    public static int[] getTypes(String base)
    {
        int[] types = new int[4];
        for (int i = 0; i < types.length; i++)
        {
            types[i] = -1;
        }
        int tracecount = 0;
        int index;
        while ((index = base.indexOf("<livelooktrace>")) != -1)
        {
            int endindex = base.indexOf("</livelooktrace>");
            if (endindex == -1)return types;
            String start = base.substring(0,index);
            String inner = base.substring(index+15,endindex);
            String end = base.substring(endindex+16,base.length());
            
            base = start +end;
            types[tracecount++] = NetworkLogDataPoint.lookup(inner);
            
        }
        
        return types;
    }
    
    public static long getTimeInterval(String text)
    {
        try
        {
            if (text.contains("h"))
            {
                text = text.replaceAll("\\D", "");
                return 1000*3600*Integer.parseInt(text);
            }
            if (text.contains("m"))
            {
                text = text.replaceAll("\\D", "");
                return 1000*60*Integer.parseInt(text);
            }
            if (text.contains("s"))
            {
                text = text.replaceAll("\\D", "");
                int sec = Integer.parseInt(text);
                if (sec % 5 != 0)
                {
                    sec = sec + 5 - sec % 5;
                }
                return 1000*sec;
            }
        }
        catch (Exception e)
        {
        }
        return 0;
    }
}
