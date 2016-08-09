/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package intraplex.livelook;

import intraplex.alarms.AlarmManager;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;


import static intraplex.livelook.IPLinkNetworkTool.livelookconfig;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.UnknownHostException;
import java.util.Date;
/**
 *
 * @author jschreiv
 */
public class LogMapEntry implements Comparable{
    
    NetworkLogEntryArray logEnties;   
    public String fileName;
    public FileWriter fos;
    public FileWriter fos2;
    public String streamName;
    int index;
    int srcIpAddress;
    int dstIpAddress;
    int srcPort;
    int dstPort;
    int pktInterval;
    int codec;
    int SR;
    long lastEntry;
    long lastEntry_log;
    public InetAddress address;
    public int destPort;
    int daysSinceRoll;
    public static int daysPerLog = 1;
    int dataState;
    int oldDataState;
    int stateChangeAlarmCount= 0;
    IplinkNetworkLogEntry lastLogEntry;
    IplinkNetworkLogEntry currentLogEntry;
    
    public static final SimpleDateFormat time = new SimpleDateFormat("yyyy_MM_dd");
    public static boolean default_lossRateAlarmEnabled = false;
    public static boolean default_lossRateCorrectedAlarmEnabled = false;
    public static boolean default_enableEmail = false;
    public static double default_lossRateAlarm = 0.05;
    public static double default_lossRateCorrectedAlarm = .01;
    public static int default_alarmthresholdTime = 30;
    public static boolean default_enableStreamLogging = true;
    public static boolean next_lossRateAlarmEnabled = false;
    public static boolean next_lossRateCorrectedAlarmEnabled = false;
    public static boolean next_enableEmail = false;
    public static double next_lossRateAlarm = 0.05;
    public static double next_lossRateCorrectedAlarm = .01;
    public static int next_alarmthresholdTime = 30;
    public static boolean next_enableStreamLogging = true;
    public static boolean next_useDefault = true;
    public static int next_DPort = 50000;
    public static int nextStreamType = 0;
    
    public int streamType;
    public boolean enableLogging=true;
    public boolean lossRateAlarmEnabled = false;
    public boolean lossRateCorrectedAlarmEnabled = false;
    public boolean enableEmail = false;
    public boolean useDefault = true;
    public double lossRateAlarm = 0.05;
    public double lossRateCorrectedAlarm = .01;
    public int alarmThresholdStream = 30;
    
    int lossRateOverThreshold;
    int lossRateCorOverThreshold;
    
    boolean stateChangeAlarmSent = false;
    boolean ConnectionLostAlarmSent = false;
    boolean lossrateAlarmSent = false;
    boolean lossrateCorrectionAlarmSent = false;
    
    int lossRateGoodCnt = 0;
    int lossRateFailCnt = 0;
    int lossrateCorrectionGoodCnt = 0;
    int lossrateCorrectionsFailCnt = 0;
    
    int stateChangeGoodCnt = 0;
    int stateChangeFailCnt = 0;
    int ConnectionStatusGoodCnt = 0;
    int ConnectionStatusFailCnt = 0;
    boolean statReset = false;
    static Stream stream;
    
    Color[] colors_dataState = new Color[]
    {
            Color.gray,	                // Gray  //"notin use"
            Color.green,	                        // Green //"up"
            Color.red,           //Red // "Down (TX No Route)"
            Color.red,	            //Red // //"Down (TX WAN)"
            Color.red,        //Red // Down (RX Packet Loss)
            Color.red,     //Red // "Down (Remote Indication)"
            Color.yellow,   //Yellow //"Down (No Channel Activity)"
            Color.green,	        //Green //"Down (AES Unlock)"
            Color.red,			    //Red //"TX Send Error"
            Color.yellow,      //Yellow //Down (RX Queue Is Full)
            Color.red,  //was earlier Yellow //"Down (Invalid Configuration)"
            Color.red //Red //"Down (RX Packet Loss Threshold)"
    };
    
    static final SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss");
 
    LogMapEntry(InetAddress address, int dport, Stream stream) {
        this.address = address;
        daysSinceRoll = 0;
        oldDataState = 1;
        lossRateOverThreshold = 0;
        lossRateCorOverThreshold = 0;
        lastEntry = System.currentTimeMillis();
        lastEntry_log = System.currentTimeMillis();
        lossRateAlarmEnabled = next_lossRateAlarmEnabled;
        lossRateCorrectedAlarmEnabled = next_lossRateCorrectedAlarmEnabled;
        enableEmail = next_enableEmail;
        lossRateAlarm = next_lossRateAlarm;
        lossRateCorrectedAlarm = next_lossRateCorrectedAlarm;
        alarmThresholdStream = next_alarmthresholdTime;
        enableLogging = next_enableStreamLogging;
        useDefault = next_useDefault;
        destPort = dport;
        streamType = nextStreamType;
        this.stream = stream;
    }

    public int getOldDataState() {
        return oldDataState;
    }

    public void setOldDataState(int oldDataState) {
        this.oldDataState = oldDataState;
    }
    
    
    public String getReturnAddress()
    {
        return address.getHostAddress();
    }
    
    public int getStreamIndex(){
    
        return index;
    }
    
    public String getBase()
    {
        String s = streamName;
        s += ", "+index;
        s += ", "+address.toString().replace("/", "");
        s += ", "+destPort;
        s += ", "+streamType;
        return s;
    }
    
    public String toString()
    {
        return getBase();
    }
    
    public String toIp(int i)
    {
        return ((i >> 24)&0xFF) +"."+
               ((i >> 16)&0xFF) +"."+
               ((i >> 8)&0xFF) +"."+
               (i&0xFF);
    }
    
    public void periodicCheckin()
    {
        //JDispatchMgr.sendSetupMessage(getReturnAddress(),index,1,destPort);
    }
    
    void checkForLostConnections()
    {
        int oldState = getOldDataState();
        int state = dataState;
        long currentSystemTime = System.currentTimeMillis();
        long timeGap = currentSystemTime - lastEntry;
        if (currentSystemTime - lastEntry > 30000)
        {
            state = StatusListModel.dataState.length-1;
            //send a reconnect message
            //System.out.println("Sending a reconnect message to "+getReturnAddress()+" Strm Id "+ index);
            //JDispatchMgr.sendSetupMessage(getReturnAddress(),index,1,destPort);
        }
        
        if (oldState != state && streamName != null)
        {
            if (timeGap>=(alarmThresholdStream*1000))
            {
                
                AlarmManager.generateAlarm(Level.INFO,this, StatusListModel.dataState[state]);
                setOldDataState(state);
               
            }
        }
    }
    
    public Color getStreamStatusColor()
    {
        Color retVal = Color.red;
        if (System.currentTimeMillis() - lastEntry > 30000)
        {
            //connection lost
            retVal = Color.red;
            return retVal;
        }
        if (dataState < StatusListModel.dataState.length)
            retVal = colors_dataState[dataState];
        return retVal;
    }
    
    void closeFiles(){
       if (fos != null)
       {
           try{
               fos.flush();
            fos.close();
            
           }
           catch (IOException e){
               
           }
           
       }
       if (fos2 != null)
       {
           try{
            fos2.flush();
            fos2.close();
            
           }
           catch (IOException e){
               
               
           }
           
       }
        
    }
    
    void writeNaNToLog(IplinkNetworkLogEntry inle) {
    	try {
            if(fos == null)
            {
                 String base = "logs/"+address.toString().replace("/", "")+"/"+ streamName+"-"+index+"-"+getDate(inle.timestamp);
                 try {
                        File f = new File(base+".csv").getAbsoluteFile();
                        int count = 0;
                        boolean oldFile =false;
                        while (f.exists())
                        {
                           System.out.println("");
                            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
                            String headerline = br.readLine();
                            br.close();
                            if (headerline.equals(getBase()))
                            {
                                oldFile = true;
                                break;
                            }
                            f = new File(base+"-"+count+".csv").getAbsoluteFile();
                            count++;
                 
                        }
                        fileName = f.getAbsolutePath();
                        fos = new FileWriter(f,true);

                        if (!oldFile)
                        {
                            fos.write(getBase()+"\n");
                        }
                 }
                  catch (IOException ex) {
                    //Make sure the directory exists
                    File logDir = new File("logs").getAbsoluteFile();
                    if (!logDir.exists()) {
                        if (logDir.mkdir())System.out.println("The directory was created");
                    }
                    logDir = new File("logs/"+"/"+address.toString().replace("/", "")).getAbsoluteFile();
                    if (!logDir.exists()) {
                        if (logDir.mkdir())System.out.println("The directory was created");
                    }
                    fos =  null;  
                }
            }
            else
            {
                if(inle!= null)
                    fos.write(inle.getNaNRow() + "\n");
            } 
        }
        catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
	    }
    }
    
    void writeToLog(IplinkNetworkLogEntry inle) {
        
        dataState = inle.dataState;
        boolean shutDown = (stream.adminState == 2); 
//        if ( lossRateAlarmEnabled && lastLogEntry != null && lastLogEntry.pktsRcvd - inle.pktsRcvd == 0 && !shutDown)
//        {        
//            lossRateGoodCnt = 0;
//            lossRateFailCnt = lossRateFailCnt+1;
//            if ((lossRateFailCnt >= alarmThresholdStream) && !lossrateAlarmSent)
//            {
//               AlarmManager.generateAlarm(Level.WARNING,this,"Loss Rate is high");
//               lossrateAlarmSent = true;
//               
//            }
//           // lossRateOverThreshold=10;    
//        }
//        else if (lossRateAlarmEnabled && !shutDown)
//        {
//            //lossRateOverThreshold--;
//            lossRateGoodCnt = lossRateGoodCnt+1;
//            lossRateFailCnt= 0;
//            if (lossRateGoodCnt>=alarmThresholdStream && lossrateAlarmSent)
//            {      
//                AlarmManager.generateAlarm(Level.INFO,this, "Loss Rate is now at an acceptable rate");
//                lossrateAlarmSent = false;
//            }
//        }
        
       // System.out.println("the adjusted lost pct for stream name: "+this.streamName+" is " + getCurrentLossPCT(lastLogEntry, inle) );
        //System.out.println(lastLogEntry.pktsRcvd);
        //System.out.println(inle.pktsRcvd);
        //System.out.println(lastLogEntry.pktsRcvd - inle.pktsRcvd == 0);
//        if ( lossRateCorrectedAlarmEnabled && lossrateCorrectionGoodCnt > 1)
//        {
//            lossrateCorrectionGoodCnt = 0;
//            lossrateCorrectionsFailCnt = lossrateCorrectionsFailCnt + 5;
//            if (lossrateCorrectionsFailCnt>=alarmThresholdStream && !lossrateCorrectionAlarmSent)
//            {
//                AlarmManager.generateAlarm(Level.SEVERE,this, "Packet Loss is high");
//                //System.out.println("Loss rate correted alarm is "+lossRateCorrectedAlarm+ " The loss rate after correction is " +inle.getPktsLostPct() + " The loss rate before correction is " + inle.getPktsLostLocalPct() );
//                lossrateCorrectionAlarmSent = true;
//            }
//            //lossRateCorOverThreshold=10;
//        }
//        else if (lossRateCorrectedAlarmEnabled)
//        {
//            lossrateCorrectionGoodCnt = lossrateCorrectionGoodCnt+5;
//            lossrateCorrectionsFailCnt = 0;
//            if (lossrateCorrectionGoodCnt >= alarmThresholdStream && lossrateCorrectionAlarmSent)
//            {
//                
//                AlarmManager.generateAlarm(Level.INFO,this, "Packet Loss is now at an acceptable rate");
//                lossrateCorrectionAlarmSent = false;
//            }
//        }
        
       
        //ssudhaka: make change here to return if logging is disabled
        
        int oldState = getOldDataState();
        int state = dataState;
        
        if (System.currentTimeMillis() - lastEntry > 30000)
            state = StatusListModel.dataState.length-1;
        
        if (oldState != state && streamName != null)
        {
            stateChangeFailCnt= stateChangeFailCnt+5;
            if (stateChangeFailCnt >= alarmThresholdStream)
            {
                //AlarmManager.generateAlarm(Level.INFO,this, StatusListModel.dataState[state] + "BLARR");
                setOldDataState(state);
                stateChangeFailCnt = 0;
            }
        }
        else if (streamName != null)
        {
             stateChangeFailCnt = 0; //state is same as oldstate   
        }
         
        lastEntry = inle.timestamp;
        lastLogEntry = inle;
        if (!enableLogging)
        {
            //System.out.println ("\n Returning without writing to a file.");
             return;
        }
        
        
        if (fos == null || fos2 == null)
        {
           // System.out.println("null file pointers");
            String base = "logs/"+address.toString().replace("/", "")+"/"+ streamName+"-"+index+"-"+getDate(inle.timestamp);
            String base2 = "logs/"+address.toString().replace("/", "")+"/Event_Log_"+ streamName+"-"+index+"-"+getDate(inle.timestamp);
            try {
                File f = new File(base+".csv").getAbsoluteFile();
                File f2 = new File(base2+".csv").getAbsoluteFile();
                int count = 0;
                boolean oldFile =false;
                while (f.exists() || f2.exists())
                {
                    System.out.println("");
                    BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
                    String headerline = br.readLine();
                    br.close();
                    if (headerline.equals(getBase()))
                    {
                        oldFile = true;
                        break;
                    }
                    f = new File(base+"-"+count+".csv").getAbsoluteFile();
                    f2 = new File(base2+"-"+count+".csv").getAbsoluteFile();
                    count++;
                }
                fileName = f.getAbsolutePath();
                //f.delete();
                fos = new FileWriter(f,true);
                fos2 = new FileWriter(f2,true);
                if (!oldFile)
                {
                    fos.write(getBase()+"\n");
                    fos2.write(getBase()+"\n");
                }
            } catch (IOException ex) {
                
              // System.out.println("IOException exception");
              // ex.printStackTrace();
                //Make sure the directory exists
                File logDir = new File("logs").getAbsoluteFile();
                if (!logDir.exists()) {
                    if (logDir.mkdir())System.out.println("The directory was created");
                }
                logDir = new File("logs/"+"/"+address.toString().replace("/", "")).getAbsoluteFile();
                if (!logDir.exists()) {
                    if (logDir.mkdir())System.out.println("The directory was created");
                }
                
                fos =  null;
                fos2 =  null;
            }
        }
        else if (createNewLog(inle.timestamp))
        {
            String base = "logs/"+address.toString().replace("/", "")+"/"+ streamName+"-"+index+"-"+getDate(inle.timestamp);
            String base2 = "logs/"+address.toString().replace("/", "")+"/Event_Log_"+ streamName+"-"+index+"-"+getDate(inle.timestamp);
            try {
                if (fos != null)
                {
                    fos.flush();
                    fos.close();
                }
                if (fos2 != null)
                {
                    fos2.flush();
                    fos2.close();
                }
                File f = new File(base+".csv").getAbsoluteFile();
                File f2 = new File(base2+".csv").getAbsoluteFile();
                int count = 0;
                boolean oldFile =false;
                while (f.exists() || f2.exists())
                {
                    BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
                    String headerline = br.readLine();
                    br.close();
                    if (headerline.equals(getBase()))
                    {
                        oldFile = true;
                        break;
                    }
                    f = new File(base+"-"+count+".csv").getAbsoluteFile();
                    f2 = new File(base2+"-"+count+".csv").getAbsoluteFile();
                    count++;
                }
                fos = new FileWriter(f,true);
                fos2 = new FileWriter(f2,true);
                fileName = f.getAbsolutePath();
                if (!oldFile)
                {
                    fos.write(getBase()+"\n");
                    fos2.write(getBase()+"\n");
                }
            } catch (IOException ex) {
                
                ex.printStackTrace();
                fos =  null;
                fos2 =  null;
            }
        }
           
            if (fos != null)
            {
                try {
                		if(statReset) {
                			statReset = false;
                			fos.write(inle.getNaNRow() + "\n");
                		}
                		else
                			fos.write(inle.getEntryRow()+"\n");
                        fos.flush();
                } catch (IOException ex) {

                    ex.printStackTrace();
                    fos =  null;
                }
            }
        
        
        
        
    }
    
    public float getCurrentLossPCT(IplinkNetworkLogEntry lastEntry , IplinkNetworkLogEntry currentEntry )
    {
        if (lastEntry != null)
        {
        int pktsLost_new = currentEntry.pktsLost - lastEntry.pktsLost;
        int pktsRecvd_new = currentEntry.pktsRecvd - lastEntry.pktsRecvd;
        int pktsLostLocal_new = currentEntry.pktsLostLocal - lastEntry.pktsLostLocal;
        int pktsRcvd_new = currentEntry.pktsRcvd - lastEntry.pktsRcvd;
        if (pktsLost_new<0 || pktsRecvd_new<0 || pktsLostLocal_new<0 || pktsRcvd_new<0 )
        {
            //System.out.println("pktsLost_new:" + pktsLost_new + "pktsRecvd_new:" + pktsRecvd_new + "pktsLostLocal_new:" + pktsLostLocal_new + "pktsRcvd_new:" + pktsRcvd_new);
            //System.out.println("lastEntry.pktsRcvd: " + lastEntry.pktsRcvd + "currentEntry.pktsRecvd: " +currentEntry.pktsRecvd );
            return ((float)0.00);
        }
        return ((float)(pktsLost_new - pktsRecvd_new))/((pktsLostLocal_new+pktsRcvd_new));
        }
        else
        {
            return ((float)0.00);
        }
    }
    
     public void writeToEventLog(String s)
    {
        if (fos2 != null)
            try {
            fos2.write(format.format(new Date(System.currentTimeMillis()))+","+s+"\n");
            fos2.flush();
        } catch (IOException ex) {
            Logger.getLogger(LogMapEntry.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void generateAlarm(String alarmString) {
    	AlarmManager.generateAlarm(Level.INFO,this, alarmString);
    }
     
    public boolean createNewLog(long currentTime)
    {
      Calendar c1 = new GregorianCalendar();
      c1.setTimeInMillis(lastEntry_log);
      Calendar c2 = new GregorianCalendar();
      c2.setTimeInMillis(currentTime);
      lastEntry_log = currentTime;
      if (c1.get(Calendar.DAY_OF_YEAR) != c2.get(Calendar.DAY_OF_YEAR))
      {
          daysSinceRoll++;
          //System.out.println("Days since roll is " +daysSinceRoll );
          if (daysSinceRoll == daysPerLog)
          {
              //System.out.println("New file should be created");
              daysSinceRoll = 0;
              return true;
          }
      }
        //System.out.println("No need to create a new log file");
      return false;
    }
    
    public static String getDate(long l)
    {
      Calendar c = new GregorianCalendar();
      c.setTimeInMillis(l);
       return time.format(c.getTime());
    }

    @Override
    public int compareTo(Object t) {
        if (t instanceof LogMapEntry)
        {
            return ((LogMapEntry)t).streamName.compareTo(streamName);
        }
        return 0;
    }
    
    public String toSaveString()
    {
        String s = address.getHostAddress()+", "+streamName;
        s += ", " + streamType;
        s += ", "+index;
        s += ", "+srcIpAddress;
        s += ", "+dstIpAddress;
        s += ", "+srcPort;
        s += ", "+dstPort;
        s += ", "+pktInterval;
        s += ", "+codec;
        s += ", "+SR;
        s += ", "+lossRateAlarmEnabled;
        s += ", "+lossRateCorrectedAlarmEnabled;
        s += ", "+enableEmail;
        s += ", "+lossRateAlarm;
        s += ", "+lossRateCorrectedAlarm;
        s += ", "+useDefault;
        s += ", "+enableLogging;
        s += ", "+alarmThresholdStream;
        s += ", "+destPort;
        return s;
    }
    
    public static LogMapEntry createFromString(String s)
    {
        try {
            String[] parts = s.split(", ");
            String dport = "50000";
            if (parts.length > 19)
            {
                dport = parts[19].trim();
            }
            LogMapEntry lme = new LogMapEntry(InetAddress.getByName(parts[0]),Integer.parseInt(dport), stream);
            lme.streamName = parts[1].trim();
            lme.streamType = Integer.parseInt(parts[2].trim());
            lme.index = Integer.parseInt(parts[3].trim());
            lme.srcIpAddress = Integer.parseInt(parts[4].trim());
            lme.dstIpAddress = Integer.parseInt(parts[5].trim());
            lme.srcPort = Integer.parseInt(parts[6].trim());
            lme.dstPort = Integer.parseInt(parts[7].trim());
            lme.pktInterval = Integer.parseInt(parts[8].trim());
            lme.codec = Integer.parseInt(parts[9].trim());
            lme.SR = Integer.parseInt(parts[10].trim());
            lme.lossRateAlarmEnabled = parts[11].trim().equals("true");
            lme.lossRateCorrectedAlarmEnabled = parts[12].trim().equals("true");
            lme.enableEmail = parts[13].trim().equals("true");
            lme.lossRateAlarm = Double.parseDouble(parts[14].trim());
            lme.lossRateCorrectedAlarm = Double.parseDouble(parts[15].trim());
            lme.useDefault = parts[16].trim().equals("true");
            lme.enableLogging = parts[17].trim().equals("true");
            lme.alarmThresholdStream = Integer.parseInt(parts[18].trim());
            
            return lme;
        } catch (UnknownHostException ex) {
            Logger.getLogger(LogMapEntry.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
        
        
    }
   
    public static void SaveConfig()
    {
            
      livelookconfig.put("Alert.EnableLossRate",default_lossRateAlarmEnabled+"");
      livelookconfig.put("Alert.EnableCorrectedLossRate",default_lossRateCorrectedAlarmEnabled+"");
      livelookconfig.put("Alert.EnableEmail",default_enableEmail+"");
      livelookconfig.put("Alert.LossRatePercent",default_lossRateAlarm+"");
      livelookconfig.put("Alert.CorrectedLossRatePercent",default_lossRateCorrectedAlarm+"");
      livelookconfig.put("Alert.EnableStreamLogging",default_enableStreamLogging+"");
      livelookconfig.put("Alert.alarmthresholdtime", default_alarmthresholdTime+"");
      livelookconfig.save();
    }
    
    public static void loadConfig()
    {
        default_lossRateAlarm=livelookconfig.getDouble("Alert.LossRatePercent",.05);
        default_lossRateCorrectedAlarm=livelookconfig.getDouble("Alert.CorrectedLossRatePercent",.01);
        default_lossRateAlarmEnabled=livelookconfig.getBoolean("Alert.EnableLossRate",false);
        default_lossRateCorrectedAlarmEnabled=livelookconfig.getBoolean("Alert.EnableCorrectedLossRate",false);
        default_enableEmail=livelookconfig.getBoolean("Alert.EnableEmail",true);
        default_enableStreamLogging=livelookconfig.getBoolean("Alert.EnableStreamLogging", true);
        default_alarmthresholdTime=livelookconfig.getInt("Alert.alarmthresholdtime", 30);
        
        LogMapEntry.next_lossRateAlarmEnabled = LogMapEntry.default_lossRateAlarmEnabled;
        LogMapEntry.next_lossRateCorrectedAlarmEnabled = LogMapEntry.default_lossRateCorrectedAlarmEnabled;
        LogMapEntry.next_enableEmail = LogMapEntry.default_enableEmail;
        LogMapEntry.next_lossRateAlarm = LogMapEntry.default_lossRateAlarm;
        LogMapEntry.next_lossRateCorrectedAlarm = LogMapEntry.default_lossRateCorrectedAlarm;
        LogMapEntry.next_alarmthresholdTime = LogMapEntry.default_alarmthresholdTime;
        LogMapEntry.next_enableStreamLogging = LogMapEntry.default_enableStreamLogging;
        LogMapEntry.next_useDefault = true;
    }
    
}
