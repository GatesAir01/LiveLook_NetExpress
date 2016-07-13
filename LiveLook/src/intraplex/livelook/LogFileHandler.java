/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package intraplex.livelook;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import org.jfree.data.xy.XYSeries;
/**
 *
 * @author jschreiv
 */

import static intraplex.livelook.IPLinkNetworkTool.config;

public class LogFileHandler {
    
    public static final int NUM_COLS = 27;
    public static final int NUM_COLS_NEW = 32;
    long firstHour;
    long start;
    long end;
    LoadedLogFile logFile;
    ScriptEngine engine;
    int pktInt;
    File[] files;
    boolean allowRecover;
    boolean rememberSelection;
    public boolean pps;
    int firstEntryIndex = -1;
    int lastEntryIndex = -1;
    
    
    public LogFileHandler(String file)
    {
        
        ScriptEngineManager factory = new ScriptEngineManager();
        engine = factory.getEngineByName("JavaScript");
        logFile = new LoadedLogFile();
        firstHour = 0;
        rememberSelection = false;
        allowRecover = true;
        loadConfiguation();
        this.setFile(file);
        reload();
        pps = true;
    }
    
    public void loadConfiguation()     
    {
        try
        {
            allowRecover = Boolean.parseBoolean(config.get("LoadFile.RecoverResets").toString());
            rememberSelection = Boolean.parseBoolean(config.get("LoadFile.RememberSelection").toString());
        }
        catch (Exception e)
        {
        }
    }

    private long nearestMinute()
    {
        Calendar c = new GregorianCalendar();
        c.setTimeInMillis(System.currentTimeMillis());
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTimeInMillis();
    }
        
    public void setFile(String file) {
        
        File[] f = new File[1];
        f[0] = new File(file);
        setFiles(f);
    }
    
    public void setFiles(File[] f) {
        files = f;
        firstHour = 0;
    }

    public boolean reload() {
        boolean sucessful = updateFiles();
        loadStartAndEndTimes();
        return sucessful;
    }

    
    public boolean updateFiles()
    {
        logFile.beginLoad();
        IplinkNetworkLogEntry last = null;
        IplinkNetworkLogEntry lastUnadjusted = null;
        boolean recoverActive = false;
        boolean skipRecovery = !allowRecover && rememberSelection;
        boolean localRemember = rememberSelection;
        try {
            for (int f = 0; f < files.length; f++)
            {
                BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(files[f])));
                String s = reader.readLine();
                String[] seperated = s.split(",");
                if (seperated.length == 9)
                {
                    if (!logFile.setStreamInfo(seperated))
                    {
                        logFile.endLoad();
                        return false;
                    }
                }
                while ((s = reader.readLine())!=null)
                {
                    String[] split = s.split(",");
                    
                    if (split.length == NUM_COLS_NEW || split.length == NUM_COLS )
                    {
                        
                        IplinkNetworkLogEntry e = new IplinkNetworkLogEntry(split, (split.length ==NUM_COLS) );
                        if (recoverActive)
                        {
                            if (e.getPktsRcvd() < lastUnadjusted.getPktsRcvd())
                            {
                                lastUnadjusted = e;
                            }
                            else
                            {   
                                IplinkNetworkLogEntry temp = new IplinkNetworkLogEntry(e);
                                e.adjustOffset(last, lastUnadjusted);
                                lastUnadjusted = temp;
                                last = e;
                            }
                        }
                        else if (!skipRecovery && last != null)
                        {
                            if (e.getPktsRcvd() < last.getPktsRcvd())
                            {
                                if (!localRemember)
                                {
                                    localRemember = true;
                                    AskRecoverDialog d = new AskRecoverDialog(null, true);
                                    d.setLocationRelativeTo(null);
                                    d.setVisible(true);
                                    rememberSelection = d.remember;
                                    allowRecover = d.recover;
                                    
                                    if (rememberSelection)
                                    {
                                        config.put("LoadFile.RecoverResets",allowRecover+"");
                                        config.put("LoadFile.RememberSelection",rememberSelection+"");
                                        config.save();
                                    }
                                }
                                if (allowRecover)
                                {
                                    lastUnadjusted = e;
                                    recoverActive = true;
                                }
                            }
                            else
                            {
                                last = e;
                            }
                        }
                        else
                        {
                            last = e;
                        }
                        logFile.add(e);
                    }
                }
            }
            
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        logFile.endLoad();
        return true;
    }
    

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }
    
   public boolean loadStartAndEndTimes()
    {
        
        if (logFile.isEmpty())
        {
            start = nearestMinute()-30000;
            end = start+60000;
        }
        else
        {
            start = logFile.get(0).timestamp;
            end = logFile.get(logFile.size()-1).timestamp;
        }
        
        return true;
                        
    }
        
    public long firstHour()
    {
        if (firstHour == 0)
        {
            Calendar c = new GregorianCalendar();
            for (int i = 0; i < logFile.size(); i++)
            {
                long recordedTime = logFile.get(i).timestamp;
                c.setTimeInMillis(recordedTime);
                if (c.get(Calendar.MINUTE) < 2)
                {
                    firstHour = recordedTime;
                    break;
                }
            }
        }
         
        return firstHour;
                        
    }
      
    public boolean load(int[] type, long starttime, long endtime, long interval, int pointsPerInterval, XYSeries[] trace)
    {
        for (int i = 0; i < trace.length; i++)
        {
            if (type[i] >= 0)
                trace[i].clear();
        }
        boolean started = false;
        long nextTime = 0;
        IplinkNetworkLogEntry[] lastEntry = new IplinkNetworkLogEntry[pointsPerInterval]; 
        int index = 0;
        //System.out.println("The start Time is " + starttime);
        NetworkLogDataPointBlock block = null;
        try {
            
            for (int i = 0; i < logFile.size(); i++)
            {
                IplinkNetworkLogEntry e = logFile.get(i);
                    
                    if (!started)
                    {
                        long recordedTime = e.timestamp;
                        if (starttime <= recordedTime)
                        {
                            nextTime = recordedTime + interval/pointsPerInterval;
                            lastEntry[index++] = e;
                            block = new NetworkLogDataPointBlock(e,0);
                            started = true;
                            if (index == pointsPerInterval)
                            {
                                index=0;
                            }
                        }
                    }
                    else
                    {
                        long recordedTime = e.timestamp;
                        block.addEntry(e);
                        if (nextTime <= recordedTime)
                        {
                            nextTime += interval/pointsPerInterval;
                            IplinkNetworkLogEntry nextEntry = e;
                            if (lastEntry[index] != null)
                            {
                               
                                for (int t = 0; t < trace.length; t++)
                                {
                                    if (type[t] >= 0)
                                    {
                                        trace[t].add(block.getTimestamp(), block.getValueForPlot(type[t],interval,pps));
                                    }
                                }
                            }
                            lastEntry[index++] = nextEntry;
                            if (index == pointsPerInterval)
                            {
                                index=0;
                            }
                            block.reset();
                        }
                        if (endtime > 0 && endtime < recordedTime)
                        {
                            break;
                        }
                    }
                }
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    } 
    
    public boolean createCalculatedFile(long starttime, long endtime, long interval, int pointsPerInterval, String filename)
    {
        boolean started = false;
        long nextTime = 0;
        SimpleDateFormat time = new SimpleDateFormat("h:mm a MM/dd/yyyy");
        Calendar c = new GregorianCalendar();
        
        IplinkNetworkLogEntry[] lastEntry = new IplinkNetworkLogEntry[pointsPerInterval]; 
        int index = 0;
        try {
            FileWriter fos = new FileWriter(filename);
            
            fos.write("Time,Loss Rate(%),Burst Density(%),Average Burst Size,Gap Density(%),Isolated Losses,Burst Losses,Average JBuf Delay(ms),");
            fos.write("Loss Rate Alt Calc(%), Loss Rate After Correction(%),JBuf Resets,Packets Recovered,Packets Late,Packets Early,Group Losses\n");
            fos.flush();
            
            for (int i = 0; i < logFile.size(); i++)
            {
                IplinkNetworkLogEntry e = logFile.get(i);

                if (!started)
                {
                    long recordedTime = e.timestamp;
                    if (starttime <= recordedTime)
                    {
                        nextTime = recordedTime + interval/pointsPerInterval;
                        lastEntry[index++] = e;
                        started = true;
                        if (index == pointsPerInterval)
                        {
                            index=0;
                        }
                    }
                }
                else
                {
                    long recordedTime = e.timestamp;
                    if (nextTime <= recordedTime)
                    {
                        nextTime += interval/pointsPerInterval;
                        IplinkNetworkLogEntry nextEntry = e;
                        if (lastEntry[index] != null)
                        {
                            NetworkLogDataPoint dp = new NetworkLogDataPoint(lastEntry[index],nextEntry,0);
                            c.setTimeInMillis(dp.getTimestamp());
                            fos.write(time.format(c.getTime()));
                            for (int t = 0; t < NetworkLogDataPoint.NUM_TRACES; t++)
                            {
                                fos.write(","+dp.getValue(t,interval,pps));
                            }
                            fos.write("\n");
                            fos.flush();
                        }
                        lastEntry[index++] = nextEntry;
                        if (index == pointsPerInterval)
                        {
                            index=0;
                        }
                    }
                    if (endtime > 0 && endtime < recordedTime)
                    {
                        break;
                    }
                }
                
            }
            
        } catch (Exception ex) {
            return false;
        }
        return true;
    }
     
    public ArrayList<BurstEntry> getBursts(long starttime, long endtime)
    {
        boolean started = false;
        long nextTime = 0;
        long interval = 5000;
        ArrayList<BurstEntry> entries = new ArrayList<>();
        Calendar c = new GregorianCalendar();
        
        
        IplinkNetworkLogEntry lastEntry = null;
        try {
            
             pktInt = getRtpStrmInterval(logFile.getSR(),logFile.getPktInterval(),logFile.getCodec());    
            int i;
            for (i = 0; i < logFile.size(); i++)
            {
                IplinkNetworkLogEntry e = logFile.get(i);
                
                if (!started)
                {
                    long recordedTime = e.timestamp;
                    if (starttime <= recordedTime)
                    {
                        nextTime = recordedTime + interval;
                        lastEntry = e;
                        started = true;
                        firstEntryIndex = i;
                    }
                }
                else
                {
                    long recordedTime = e.timestamp;
                    if (nextTime <= recordedTime)
                    {
                        nextTime += interval;
                        IplinkNetworkLogEntry nextEntry = e;
                        if (lastEntry != null)
                        {
                            NetworkLogDataPoint dp = new NetworkLogDataPoint(lastEntry,nextEntry,0);
                            c.setTimeInMillis(dp.getTimestamp());
                            if (dp.getAverageBurst() > 0)
                            {
                                double averageBurst = dp.getAverageBurst();
                                double averageDensity = dp.getBurstDensity();
                                double burstLosses = dp.getBusrtLosses();

                                int count = (int)Math.round(burstLosses /(averageBurst * averageDensity));

                                for (int b = 0; b < count; b++)
                                {
                                    entries.add(new BurstEntry(averageBurst,averageDensity));
                                }
                            }
                        }
                        lastEntry = nextEntry;
                    }
                    if (endtime > 0 && endtime < recordedTime)
                    {
                        break;
                    }
                }
            }
            lastEntryIndex = i;
        } catch (Exception ex) {
            return null;
        }
        return entries;
    }
     
    public boolean save(int[] type, long starttime, long endtime, long interval, int pointsPerInterval, String filename)
    {
        boolean started = false;
        long nextTime = 0;
        SimpleDateFormat time = new SimpleDateFormat("h:mm:ss a MM/dd/yyyy");
        Calendar c = new GregorianCalendar();
        
        IplinkNetworkLogEntry[] lastEntry = new IplinkNetworkLogEntry[pointsPerInterval]; 
        int index = 0;
        try {
            FileWriter fos = new FileWriter(filename);
            
            fos.write("Time");
            
            for (int i = 0; i < type.length; i++)
            {
                if (type[i] >= 0)
                    fos.write(","+NetworkLogDataPoint.getTypeTitle(type[i],pps));
            }
            
            fos.write("\n");
            fos.flush();
            
            for (int i = 0; i < logFile.size(); i++)
            {
                IplinkNetworkLogEntry e = logFile.get(i);
                    
                if (!started)
                {
                    long recordedTime = e.timestamp;
                    if (starttime <= recordedTime)
                    {
                        nextTime = recordedTime + interval/pointsPerInterval;
                        lastEntry[index++] = e;
                        started = true;
                        if (index == pointsPerInterval)
                        {
                            index=0;
                        }
                    }
                }
                else
                {
                    long recordedTime = e.timestamp;
                    if (nextTime <= recordedTime)
                    {
                        nextTime += interval/pointsPerInterval;
                        IplinkNetworkLogEntry nextEntry = e;
                        if (lastEntry[index] != null)
                        {
                            NetworkLogDataPoint dp = new NetworkLogDataPoint(lastEntry[index],nextEntry,0);
                            c.setTimeInMillis(dp.getTimestamp());
                            fos.write(time.format(c.getTime()));
                            for (int t = 0; t < type.length; t++)
                            {
                                if (type[t] >= 0)
                                    fos.write(","+dp.getValue(type[t],interval,pps));
                            }
                            fos.write("\n");
                            fos.flush();
                        }
                        lastEntry[index++] = nextEntry;
                        if (index == pointsPerInterval)
                        {
                            index=0;
                        }
                    }
                    if (endtime > 0 && endtime < recordedTime)
                    {
                        break;
                    }
                }
            }
            
            
            fos.close();
            
        } catch (Exception ex) {
            return false;
        }
        return true;
    }

    public IplinkNetworkLogEntry getStartEntry() {
        if (logFile.isEmpty())return null;
        if (firstEntryIndex < 0)firstEntryIndex = 0;
        return logFile.get(firstEntryIndex);
    }

    public IplinkNetworkLogEntry getEndEntry() {
        if (logFile.isEmpty())return null;
        if (lastEntryIndex < 0 || lastEntryIndex >= logFile.size()-1)lastEntryIndex = logFile.size()-1;
        return logFile.get(lastEntryIndex);
    }

    public int getPktInt() {
        return pktInt;
    }
    
    
    public boolean match (int[] neededReplacements, String expression, NetworkLogDataPoint point, long interval) throws ScriptException
    {
        for (int i = 0; i < neededReplacements.length; i++)
        {
            expression = expression.replaceAll(Pattern.quote(NetworkLogDataPoint.possibilities[neededReplacements[i]]), point.getValue(neededReplacements[i],interval,pps)+"");
        }
        return engine.eval(expression).toString().equals("true");
    }
    
    public boolean validateSearchExpression(String expression)
    {
        int[] neededReplacements = getNeededReplacements(expression);
        for (int i = 0; i < neededReplacements.length; i++)
        {
            expression = expression.replaceAll(Pattern.quote(NetworkLogDataPoint.possibilities[neededReplacements[i]]), (1+i*.0001)+"");
        }
        try
        {
            String s = engine.eval(expression).toString(); 
            return s.equalsIgnoreCase("true") || s.equalsIgnoreCase("false");
        }
        catch (Exception e)
        {
            
        }
        
        return false;
    }
            
    
    public boolean greater(int[] neededReplacements, String expression, NetworkLogDataPoint point, double[] values, long interval) throws ScriptException
    {
        for (int i = 0; i < neededReplacements.length; i++)
        {
            expression = expression.replaceAll(Pattern.quote(NetworkLogDataPoint.possibilities[neededReplacements[i]]), point.getValue(neededReplacements[i],interval,pps)+"");
        }
        double d = Double.parseDouble(engine.eval(expression).toString());
        values[1] = d;
        return  d > values[0];
    }
    
    public int[] getNeededReplacements(String expression)
    {
        ArrayList l = new ArrayList();
        for (int i = NetworkLogDataPoint.NUM_TRACES-1; i >= 0; i-- )
        {
            if (expression.contains(NetworkLogDataPoint.possibilities[i]))
            {
                l.add(i);
            }
        }
        Integer[] ind = (Integer[]) l.toArray(new Integer[0]);
        int[] indexList = new int[ind.length];
        
        for (int i = 0; i < ind.length; i++)
        {
            indexList[i] = ind[i].intValue();
        }
        
        return indexList;
    }

    public boolean search(long starttime, long endtime, long interval, String expression) throws ScriptException
    {

        boolean started = false;
        long nextTime = 0;
        int index = 0;
        int[] needed = getNeededReplacements(expression);
        try {
            
            for (int i = 0; i < logFile.size(); i++)
            {
                IplinkNetworkLogEntry e = logFile.get(i);
                IplinkNetworkLogEntry e2 = logFile.get(i+5);
                NetworkLogDataPoint dp = new NetworkLogDataPoint(e,e2,0);
                //System.out.println(dp.getIsolatedLosses());
                //System.out.println(match(needed,expression,dp));
                break;
                    
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean greatest(long starttime, long endtime, long interval, String expression, Object[] results)
    {
        boolean started = false;
        int[] needed = getNeededReplacements(expression);
        
        int startIndex = 0;
        int endIndex = 0;
        
        double[] resultValue = new double[2];
        resultValue[0] = -1;
        int rsIndex = 0;
        int reIndex = 0;
        
        try {
            
            for (int i = 0; i < logFile.size(); i++)
            {
                IplinkNetworkLogEntry e = logFile.get(i);
                    
                    if (!started)
                    {
                        long recordedTime = e.timestamp;
                        if (starttime <= recordedTime)
                        {
                            startIndex = i;
                            endIndex = startIndex - 1 + (int)(interval/5000);
                            started = true;
                        }
                    }
                    else
                    {
                        if (endIndex >= i) 
                        {
                            continue;
                        }
                        endIndex++;
                        if (endtime > 0 && logFile.get(endIndex).timestamp > endtime) break;
                        if (startIndex > endIndex) continue;
                        while (logFile.get(endIndex).timestamp-logFile.get(startIndex).timestamp > interval+3000)
                        {
                            startIndex++;
                        }
                        if (logFile.get(endIndex).timestamp-logFile.get(startIndex).timestamp < interval+3000 &&
                            logFile.get(endIndex).timestamp-logFile.get(startIndex).timestamp > interval-13000)
                        {
                            NetworkLogDataPoint dp = new NetworkLogDataPoint(logFile.get(startIndex),logFile.get(endIndex),0);
                            if (greater(needed,expression,dp,resultValue,interval))
                            {
                                resultValue[0] = resultValue[1];
                                rsIndex = startIndex;
                                reIndex = endIndex;
                            }
                        }
                    }
                }
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        results[0] = new Long(logFile.get(rsIndex).timestamp);
        results[1] = new Long(logFile.get(reIndex).timestamp);
        results[2] = new Double(resultValue[0]);
        
        return true;
    } 

    public boolean longest(long starttime, long endtime, long interval, String expression, Object[] results)
    {
        boolean started = false;
        int[] needed = getNeededReplacements(expression);
        
        int startIndex = 0;
        int endIndex = 0;
        
        double[] resultValue = new double[2];
        resultValue[0] = -1;
        int rsIndex = 0;
        int reIndex = 0;
        int lsIndex = 0;
        int leIndex = 0;
        
        try {
            
            for (int i = 0; i < logFile.size(); i++)
            {
                IplinkNetworkLogEntry e = logFile.get(i);
                    
                    if (!started)
                    {
                        long recordedTime = e.timestamp;
                        if (starttime <= recordedTime)
                        {
                            startIndex = i;
                            endIndex = startIndex - 1 + (int)(interval/5000);
                            started = true;
                        }
                    }
                    else
                    {
                        if (endIndex >= i) 
                        {
                            continue;
                        }
                        endIndex++;
                        if (endtime > 0 && logFile.get(endIndex).timestamp > endtime) break;
                        if (startIndex > endIndex) continue;
                        while (logFile.get(endIndex).timestamp-logFile.get(startIndex).timestamp > interval+3000)
                        {
                            startIndex++;
                        }
                        if (logFile.get(endIndex).timestamp-logFile.get(startIndex).timestamp < interval+3000 &&
                            logFile.get(endIndex).timestamp-logFile.get(startIndex).timestamp > interval-13000)
                        {
                            NetworkLogDataPoint dp = new NetworkLogDataPoint(logFile.get(startIndex),logFile.get(endIndex),0);
                            if (match(needed,expression,dp,interval))
                            {
                                resultValue[0] = resultValue[1];
                                
                                if (startIndex < leIndex && leIndex > startIndex)
                                {
                                    leIndex = endIndex;
                                }
                                else
                                {
                                    lsIndex = startIndex;
                                    leIndex = endIndex;
                                }
                                
                                if (logFile.get(leIndex).timestamp-logFile.get(lsIndex).timestamp > 
                                        logFile.get(reIndex).timestamp-logFile.get(rsIndex).timestamp)
                                {
                                    rsIndex = lsIndex;
                                    reIndex = leIndex;
                                }
                                   
                            }
                        }
                    }
                }
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        results[0] = new Long(logFile.get(rsIndex).timestamp);
        results[1] = new Long(logFile.get(reIndex).timestamp);
        
        return true;
    } 
    
    public boolean find(long starttime, long endtime, long interval, String expression, Object[] results)
    {
        boolean started = false;
        int[] needed = getNeededReplacements(expression);
        
        
        int startIndex = 0;
        int endIndex = 0;
        
        int rsIndex = 0;
        int reIndex = 0;
        long intervalShift = 3000;
        try {
            
            for (int i = 0; i < logFile.size(); i++)
            {
                IplinkNetworkLogEntry e = logFile.get(i);
                    
                    if (!started)
                    {
                        long recordedTime = e.timestamp;
                        if (starttime <= recordedTime)
                        {
                            startIndex = i;
                            endIndex = startIndex - 1 + (int)(interval/5000);
                            started = true;
                        }
                    }
                    else
                    {
                        if (endIndex >= i) 
                        {
                            continue;
                        }
                        endIndex++;
                        if (endtime > 0 && logFile.get(endIndex).timestamp > endtime) break;
                        if (startIndex > endIndex) continue;
                        while (logFile.get(endIndex).timestamp-logFile.get(startIndex).timestamp > interval+3000)
                        {
                            startIndex++;
                        }
                        if (logFile.get(endIndex).timestamp-logFile.get(startIndex).timestamp < interval+3000 &&
                            logFile.get(endIndex).timestamp-logFile.get(startIndex).timestamp > interval-intervalShift)
                        {
                            NetworkLogDataPoint dp = new NetworkLogDataPoint(logFile.get(startIndex),logFile.get(endIndex),0);
                            if (!dp.invalidPacket && match(needed,expression,dp,interval))
                            {
                                rsIndex = startIndex;
                                reIndex = endIndex;
                                break;  
                            }
                        }
                    }
                }
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        results[0] = new Long(logFile.get(rsIndex).timestamp);
        results[1] = new Long(logFile.get(reIndex).timestamp);
        if (reIndex > rsIndex)
        {
            return true;
        }
        return false;
    } 
    
    public boolean findReverse(long starttime, long endtime, long interval, String expression, Object[] results)
    {
        boolean started = false;
        int[] needed = getNeededReplacements(expression);
        
        int startIndex = 0;
        int endIndex = 0;
        
        int rsIndex = 0;
        int reIndex = 0;
        
        try {
            
            for (int i = logFile.size()-1; i > 0 ; i--)
            {
                IplinkNetworkLogEntry e = logFile.get(i);
                    
                    if (!started)
                    {
                        long recordedTime = e.timestamp;
                        if (endtime >= recordedTime)
                        {
                            endIndex = i;
                            startIndex = endIndex + 1 - (int)(interval/5000);
                            started = true;
                        }
                    }
                    else
                    {
                        if (startIndex <= i) 
                        {
                            continue;
                        }
                        startIndex--;
                        if (startIndex <= 0 || logFile.get(startIndex).timestamp < starttime) break;
                        if (startIndex > endIndex) continue;
                        while (logFile.get(endIndex).timestamp-logFile.get(startIndex).timestamp > interval+3000)
                        {
                            endIndex--;
                        }
                        if (logFile.get(endIndex).timestamp-logFile.get(startIndex).timestamp < interval+3000 &&
                            logFile.get(endIndex).timestamp-logFile.get(startIndex).timestamp > interval-3000)
                        {
                            NetworkLogDataPoint dp = new NetworkLogDataPoint(logFile.get(startIndex),logFile.get(endIndex),0);
                            if (!dp.invalidPacket && match(needed,expression,dp,interval))
                            {
                                rsIndex = startIndex;
                                reIndex = endIndex;
                                break;  
                            }
                        }
                    }
                }
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        results[0] = new Long(logFile.get(rsIndex).timestamp);
        results[1] = new Long(logFile.get(reIndex).timestamp);
        if (reIndex > rsIndex)
        {
            return true;
        }
        return false;
    } 
 /*
    public boolean longest(long starttime, long endtime, long interval, String expression, Object[] results)
    {
        boolean started = false;
        int[] needed = getNeededReplacements(expression);
        
        int startIndex = 0;
        int endIndex = 0;
        
        
        int rsIndex = 0;
        int reIndex = 0;
        
        try {
            
            for (int i = 0; i < logFile.size()-1; i++)
            {
                IplinkNetworkLogEntry e = logFile.get(i);
                    
                    if (!started)
                    {
                        long recordedTime = e.timestamp;
                        if (starttime <= recordedTime)
                        {
                            startIndex = i;
                            endIndex = startIndex;
                            rsIndex = startIndex;
                            reIndex = startIndex;
                            started = true;
                        }
                    }
                    else
                    {
                        
                        if (logFile.get(i+1).timestamp > endtime) break;
                        startIndex = i;
                        endIndex = i+1;
                        NetworkLogDataPoint dp = new NetworkLogDataPoint(logFile.get(startIndex),logFile.get(endIndex),0);
                        while (match(needed,expression,dp))
                        {
                           endIndex++; 
                           if (endIndex < logFile.size())
                           {
                               dp = new NetworkLogDataPoint(logFile.get(startIndex),logFile.get(endIndex),0);
                           }
                           else
                           {
                               break;
                           }
                        }
                        endIndex--;
                        startIndex--; 
                        dp = new NetworkLogDataPoint(logFile.get(startIndex),logFile.get(endIndex),0);
                        while (match(needed,expression,dp))
                        {
                           if (startIndex <= 0 || logFile.get(startIndex).timestamp < starttime)   
                           {
                               startIndex--; 
                               break;
                           }
                           startIndex--; 
                           dp = new NetworkLogDataPoint(logFile.get(startIndex),logFile.get(endIndex),0);
                        }
                        startIndex++;
                        if (endIndex > startIndex)
                        {
                            System.out.println(logFile.get(endIndex).timestamp - logFile.get(startIndex).timestamp);
                            if (logFile.get(endIndex).timestamp - logFile.get(startIndex).timestamp > 
                                logFile.get(reIndex).timestamp - logFile.get(rsIndex).timestamp)
                            {
                                rsIndex = startIndex;
                                reIndex = endIndex;
                            }
                        }
                        
                    }
                }
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        
        results[0] = new Long(logFile.get(rsIndex).timestamp);
        results[1] = new Long(logFile.get(reIndex).timestamp);
        
        return true;
    }
    
    */
        

    
public static int  getRtpStrmInterval(int SR, int pktI, int codec)
{
    
        int pktInterval = pktI;
	switch (codec) 
	{
	case PCM_16_44K_8MSEC:
	case PCM_16_32K_8MSEC:
		pktInterval=8;
		break;
	case PCM_16_44K_4MSEC:
	case PCM_16_48K_4MSEC:
	case PCM_16_32K_4MSEC:
		pktInterval=4;
		break;
	   
	case G711_ALAW:
	     pktInterval=20;
		break;
	case G711_MULAW:
		 pktInterval=20;
		break;
	case G722:
		break;
	case MP_L2: 
	case MP_L3:
		if(SR == _SR44_1k)
			pktInterval = 26;
		else if(SR == _SR32k)
			 pktInterval = 36;
		else if (SR == _SR16k)
             pktInterval = 72;
        else
			pktInterval=24;
		break;
	case AAC_LC:
		if(SR == _SR44_1k)
			pktInterval = 23;
		else if(SR == _SR32k)
			 pktInterval = 32;
		else if(SR == _SR16k)
			 pktInterval = 64;
		else
			 pktInterval=21;
		break;
	case AAC_HEvTwo:
	case AAC_HE:
		if(SR == _SR44_1k)
			pktInterval = 46;
		else if(SR ==_SR32k)
			 pktInterval = 64;
		else if(SR == _SR16k)
			 pktInterval = 128;
		else
			 pktInterval=42;
	  
		break;
	case EAPT_X:
        if ( pktInterval == 0 )
        {
  
            if(SR == _SR44_1k)
            {
                pktInterval = 6;
			
            }
            else if(SR == _SR32k)
            {
                pktInterval = 8;
            }
            else if(SR == _SR16k)
            {
                pktInterval = 16;
            }
            else
            {
                pktInterval = 5;
            }
        }
        else
		break;
	case AAC_ELD:
		 if(SR == _SR44_1k)
			pktInterval = 12;
		else if(SR == _SR32k)
			 pktInterval = 16;
		else
			 pktInterval=11;
		break;
	case OPUS:
		break;
	case J_41:
	    pktInterval=20;
		break;
	case TEST_TONE:
		pktInterval=8;
		break;
    case UNCOMPRESSED:
		break; 
	case TRANSAES:
        if (SR == _SR192k)
        {
          // if (mode == chan_stereo)
          //        pktInterval = 1;
          // else
                  pktInterval = 2;
        }
        else if (SR == _SR32k)
           pktInterval=6; 
        else if (SR == _SR16k)
            pktInterval=12;
        else  
           pktInterval=4;
		break;
	default:
	    pktInterval=20;
		break;
	}
	return pktInterval;

}


public static final int _SRNULL=(0);
public static final int _SR8k=(1);
public static final int _SR16k=(2);
public static final int _SR32k=(3);
public static final int _SR44_1k=(4);
public static final int _SR48k=(5);
public static final int _SR192k=(6);
public static final int sampleRateMax=(7);


    
public static final int _CODEC_NONE=(0);
public static final int PCM_16_44K_8MSEC=(1);
public static final int PCM_16_44K_4MSEC=(2);
public static final int PCM_16_48K_4MSEC=(3);
public static final int PCM_16_32K_4MSEC=(4);
public static final int PCM_16_32K_8MSEC=(5);
public static final int MP_L2=(6);
public static final int MP_L3=(7);
public static final int AAC_LC=(8);
public static final int AAC_HEvTwo=(9);
public static final int AAC_HE=(10);
public static final int G711_ALAW=(11);
public static final int G711_MULAW=(12);
public static final int G722=(13);
public static final int EAPT_X=(14);
public static final int J_41=(15);
public static final int TEST_TONE=(16);
public static final int AAC_ELD=(17);
public static final int OPUS=(18);
public static final int UNCOMPRESSED=(19);
public static final int TRANSAES=(20);
public static final int codecMax=(21);

}

