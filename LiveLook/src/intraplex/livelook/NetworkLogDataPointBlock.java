
package intraplex.livelook;

import java.util.ArrayList;

/**
 *
 * @author jschreiv
 */


public class NetworkLogDataPointBlock extends NetworkLogDataPoint
{
    
    IplinkNetworkLogEntry firstEntry;
    IplinkNetworkLogEntry lastEntry;
    double maxLossRate = 0;
    double minLossRate = 100;
    double maxLossRateAfterCorrection = 0;
    long myid;

    public NetworkLogDataPointBlock(IplinkNetworkLogEntry e, long id) {
         super(e, id);
         myid = id;
         addEntry(e);
    }

   
    public void reset()
    {
        maxLossRate = 0;
        minLossRate = 100;
        maxLossRateAfterCorrection = 0;
        firstEntry = lastEntry;
    }
    
    public void addEntry(IplinkNetworkLogEntry e)
    {
        if (firstEntry == null)
        {
            firstEntry = e;
            lastEntry = e;
        }
        else
        {
            NetworkLogDataPoint p = new NetworkLogDataPoint(lastEntry,e,0);
//            double lossRate = p.getLossRate();
//            double lossAfter = p.getAdjustedLossRate();
//            if (lossRate > maxLossRate)maxLossRate = lossRate;
//            if (lossAfter > maxLossRateAfterCorrection)maxLossRateAfterCorrection = lossAfter;
//            if (lossRate < minLossRate)minLossRate = lossRate;
            lastEntry = e;
        }
        setValues(firstEntry,lastEntry,myid);
    }
    
    @Override
    public double getValueForPlot(int type, long intVal, boolean pps)
    {
        
//       boolean invalid = Double.isNaN(super.getValueForPlot(LOSS_RATE,intVal,pps));
//       if (invalid)
//       {
//           if (type == DATA_GAP)
//           {
//               return 100000;
//           }
//           else
//           {
//               //System.out.println("Invalid packet");
//               return Double.NaN;
//           }
//       }
//        if (type == MAX_LOSS_RATE)return maxLossRate*100;
//        if (type == MIN_LOSS_RATE)return minLossRate*100;
//        if (type == MAX_LOSS_RATE_AFTER_CORRECTION)return maxLossRateAfterCorrection*100;
//        if (type == DATA_GAP) return 0;
        return super.getValue(type, intVal, pps);
    }
    

    
}
