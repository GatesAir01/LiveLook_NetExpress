/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package intraplex.livelook;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jschreiv
 */
public class NetworkModelAnalyizer {
    
    BurstEntry[] bursts;
    NetworkLogDataPoint overview;
    BurstEntry[] bins;
    int minburst;
    int maxburst;
    double lastrate = 0;
    int binCount = 20;
    int pktInterval;
    LogFileHandler log;
    long starttime;
    long endtime;
    long interval;
    
    public  NetworkModelAnalyizer(long starttime, long endtime, long interval, int pointsPerInterval, LogFileHandler log)
    {
        ArrayList<BurstEntry> burstList = log.getBursts(starttime, endtime);
        IplinkNetworkLogEntry startEntry = log.getStartEntry();
        IplinkNetworkLogEntry endEntry = log.getEndEntry();
        overview = new NetworkLogDataPoint(startEntry,endEntry,0);
        bursts = burstList.toArray(new BurstEntry[0]);
        pktInterval = log.getPktInt();
        this.log = log;
        this.starttime = starttime;
        this.endtime = endtime;
        this.interval = interval;
        
    }
    
    public void loadBasicBurstStats()
    {
        Arrays.sort(bursts);
        bins = new BurstEntry[binCount];
        if (bursts.length > 0)
        {
        minburst = (int) Math.round(bursts[0].burstsize);
        maxburst = (int) Math.round(bursts[bursts.length-1].burstsize);
        
        int binSize = (int) Math.round(bursts.length/binCount);
        int x = 0;
        for (int i = 0; i < binCount-1; i++)
        {
            bins[i] = BurstEntry.createBin(bursts, x, x+binSize);
            x+=binSize;
        }
        bins[binCount-1] = BurstEntry.createBin(bursts, x, bursts.length);
        }
        else
        {
            
            for (int i = 0; i < binCount; i++)
            {
                bins[i] = new BurstEntry(0,0,0,0);
            }
        }
    }
    
    public String getValue(String inner)
    {
        String numberformat="#0.00";
        int index = 0;
        if ((index = inner.indexOf("<numberformat>")) != -1)
        {
            int endindex = inner.indexOf("</numberformat>");
            if (endindex != -1)
            {
                String start = inner.substring(0,index);
                numberformat = inner.substring(index+14,endindex);
                String end = inner.substring(endindex+15,inner.length());
                inner = start + end;
            }
        }
        DecimalFormat df = new DecimalFormat(numberformat);
        if (inner.contains("RecommendedTimeDiversiy") || inner.contains("RecommendedTimeDiversity"))
        {
            double threshold = .1;
            if (inner.indexOf("=") > 0)
            {
               String s = inner.substring(inner.indexOf("=")+1,inner.length());
               int d = recommendedTimeDiversity(bins,overview.pkt_count,overview.c14,minburst,maxburst,Double.parseDouble(s));
               if (d < 0)
               {
                   return "Adding a Single Time Diversity Stream cannot produce a loss rate below "+s+"% for this network";
               }
               if (pktInterval == 0)
                return d+" packets";
               return (d*pktInterval)+" ms";
            }
            return "";
        }
        else if (inner.contains("RecommendedPktDiversiy") || inner.contains("RecommendedPktDiversity"))
        {
            double threshold = .1;
            if (inner.indexOf("=") > 0)
            {
               String s = inner.substring(inner.indexOf("=")+1,inner.length());
               int d = recommendedTimeDiversity(bins,overview.pkt_count,overview.c14,minburst,maxburst,Double.parseDouble(s));
               if (d < 0)
               {
                   return "Adding a Single Time Diversity Stream cannot produce a loss rate below "+s+"% for this network";
               }
               return d+" packets";
            }
            return "";
        }
        else if (inner.equals("TimeOrFEC"))
        {
            if (overview.getLossRate() < .0009)
            {
                return "This network does not require any correction techniques, if you desire a lower loss rate use FEC 25% or 50%";
            }
            if (overview.getLossRate() >= .10)
            {
                 return "This network has a high loss rate, it is recommended to combine it with another network and use Stream Splicing";
            }
            
            boolean fec = useFEC(overview.getLossRate(),overview.getAverageBurst(), overview.getGapDensity(), overview.getBurstDensity());
            
            if (fec)
            {
                return "50% FEC is recommended for this network";
            }
            
            int d = recommendedTimeDiversity(bins,overview.pkt_count,overview.c14,minburst,maxburst,.1);
            if (d > 0 && d < 250)
            {
                String rec = "";
               if (pktInterval == 0)
                rec =  d+" packets";
               else
                rec = (d*pktInterval)+" ms";
               return "Stream Splicing with Time Diversity of "+rec+" is recommended for this Network";
            }
            
            return "It is recommended to use a combination of FEC and Stream Splicing with Time Diversity for this Network, or combine it with an additional Network";
        }
        else if (inner.equals("Max Burst"))
        {
            
            return maxburst+"";
        }
        else if (inner.equals("Min Burst"))
        {
            return minburst+"";
        }
        else if (inner.equals("Interval"))
        {
            return (interval/1000) + "";
        }
        else if (inner.contains("Greatest"))
        {
            String expression = inner.replace("Greatest", "").trim();
            Object[] results = new Object[3];
            if (log.greatest(starttime, endtime, interval, expression, results))
            {
                return df.format(Double.parseDouble(results[2].toString()));
            }
            return "ERROR";
        }
        else
        {
            return df.format(overview.getValueByString(inner,interval,false));
        }
    }
    
    public int recommendedTimeDiversity(BurstEntry[] bins, int packets, int isolost, int minburst, int maxburst, double acceptableLoss)
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
            if (acceptableLoss > 100*(burstlosses+isolosses)/packets) 
            {
                lastrate = 100*(burstlosses+isolosses)/packets;
                return d;
            }
        }
        lastrate = -1;
        return -1;
    }
    
    
    public boolean useFEC(double lossRate, double burstSize, double gapDensity, double burstDensity)
    {
        Object[] data = new Object[5];
        data[0] = lossRate;
        data[1] = burstSize;
        data[2] = gapDensity;
        data[3] = burstDensity;
        try {
            return FECTDClassifier.classify(data) < .5;
        } catch (Exception ex) {
            Logger.getLogger(NetworkModelAnalyizer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
