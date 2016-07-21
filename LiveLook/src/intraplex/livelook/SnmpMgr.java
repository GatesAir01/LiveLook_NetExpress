package intraplex.livelook;

import java.awt.Color;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;


public class SnmpMgr implements Runnable{

	public static int port;
	public static SnmpMgr mgr;
	public static int nextStreamType = 0;
	public static boolean statusOnly = false;
    TreeMap<Long,Stream> map;
    TreeMap<Long, LogMapEntry> logMap;
    TreeMap<Long, IplinkNetworkLogEntry> lastEntryMap;
    ArrayList<NetworkLogDataPoint> queuedPoints;
    boolean bindfailed;
    boolean lite;
    IplinkNetworkLogEntry lastLogEntry;
    MacList macList;
    int shutDownCount;
    int redStatusCount;
    public static boolean StreamDownAlarm = false;
    public static boolean ShutDownAlarm = false;
    static int stateConnectionAlarmThreshold = 30;
	public static boolean DefaultShutDownAlarm = false;
	public static boolean DefaultStreamDownAlarm = false;
    boolean StreamDownAlarmTriggered = false;
    boolean ShutDownAlarmTriggered = false;
    boolean mapUpdate = false;
    long removeKey;
	
	public SnmpMgr(boolean lite, MacList macList) {
		lastLogEntry = null;
        bindfailed = false;
        map = new TreeMap<>();
        logMap = new TreeMap<>();
        lastEntryMap = new TreeMap<>();
        queuedPoints = new ArrayList<>();
        mgr = this;
        this.macList = macList;
        this.lite = lite;
        shutDownCount = 0;
        redStatusCount = 0;
        new Thread(this, "SnmpMgr").start();
	}
	
	public Stream addStream(String ip, String streamID, int port, String readCommunity) {
		Stream stream = new Stream(ip, streamID, port, nextStreamType, true, statusOnly, readCommunity);
		stream.StreamDownAlarm = StreamDownAlarm;
		stream.ShutDownAlarm = ShutDownAlarm;
		Long temp = Long.parseLong(ip.replace(".", "") + stream.dstPort);
		LogMapEntry lme = null;
		try {
			lme = new LogMapEntry(InetAddress.getByName(ip), Integer.parseInt(stream.dstPort), stream);
			lme.index = Integer.parseInt(streamID);
			lme.streamName = stream.streamName;
			lme.logEnties = new NetworkLogEntryArray(600,1,temp);
			map.put(temp, stream);
			logMap.put(temp, lme);
			//stream.populateVars();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return stream;
	}
	
	//Pulls info for streams
	public void refreshStreams()
    {
		for(Stream stream: map.values()) {
			stream.populateVars();
		}      
    }
	
	public void snmpRequest()
    {
		for(Stream stream: map.values()) {
//			System.out.println(stream.counter + " " + Math.ceil(((stream.statsInterval * 10) * 1.75)));
//			if(stream.counter >= Math.ceil((stream.statsInterval * 10) * 1.75)){
//				stream.counter = 0;

	        if(stream.counter >= stream.statsInterval) {
	        	//System.out.println(stream.streamName);
				//System.out.println(stream.ip + " " + stream.port);
	        	if(!stream.statusOnly){
		        	boolean shutDown = stream.checkIfShutDown();
		        	//System.out.println(shutDown);
		        	if(!shutDown){
		        		boolean reset = stream.updatePacketsSkipped();
		        		int packetsSkipped = stream.packetsSkipped;
		        		//System.out.println(!reset);
		        		//System.out.println("Packets Skipped: " + stream.packetsSkipped);
			        	if(packetsSkipped == 0) {
			        		try {
			        		stream.populateVars();
			        		
			        		if(reset)
			        			createPacket(stream.ip, Integer.parseInt(stream.dstPort));
			        		else
			        		{
			        			Long key = Long.parseLong(stream.ip.replace(".", "") + stream.dstPort);
			        			LogMapEntry e = logMap.get(key);
			        			e.writeToEventLog(e.streamName + ", " + "Stat Reset");
			        			e.statReset = true;
			        		}
			        		}
			        		catch(NumberFormatException e) {
			        			e.printStackTrace();
			        		}
			        	}
			        	else
			        	{
			        		//System.out.println(stream.packetsSkipped);
							for(int x = packetsSkipped; x >= 0; x--) 
							{
								try {
								stream.populateVars();
								//System.out.println(stream.packetsReceived);
								
								if(reset)
				        			createPacket(stream.ip, Integer.parseInt(stream.dstPort));
				        		else
				        		{
				        			Long key = Long.parseLong(stream.ip.replace(".", "") + stream.dstPort);
				        			LogMapEntry e = logMap.get(key);
				        			e.writeToEventLog(e.streamName + ", " + "Stat Reset");
				        		}
								}
				        		catch(NumberFormatException e) {
				        			e.printStackTrace();
				        		}
		
							}
							stream.packetsSkipped = 0;
			        	}
		        	}
		        	else {
		        		Long key = Long.parseLong(stream.ip.replace(".", "") + stream.dstPort);
	        			LogMapEntry e = logMap.get(key);
	        			e.writeToEventLog(e.streamName + ", " + "Stream not up");
	        			createPacket(stream.ip, Integer.parseInt(stream.dstPort), true);
		        	}
	        	}
	        	else
	        	{
	        		boolean shutDown = stream.checkIfShutDown();
	        		
	        		if(shutDown) 
	        		{
		        		Long key = Long.parseLong(stream.ip.replace(".", "") + stream.dstPort);
	        			LogMapEntry e = logMap.get(key);
	        			e.writeToEventLog(e.streamName + ", " + "Stream not up");
	        			if(StreamDownAlarm)shutDownCount++;
	        			if(shutDownCount > (stateConnectionAlarmThreshold / 5) && !ShutDownAlarmTriggered && StreamDownAlarm)
	        			{
	        				e.generateAlarm("Stream shut down past alarm level");
	        				ShutDownAlarmTriggered = true;
	    				}
	        			if(ShutDownAlarmTriggered) shutDownCount = (stateConnectionAlarmThreshold / 5);
	        		}
	        		else if(shutDownCount > 0 && ShutDownAlarm){
						if(shutDownCount == 1){
							shutDownCount = 0;
							ShutDownAlarmTriggered = false;
							Long key = Long.parseLong(stream.ip.replace(".", "") + stream.dstPort);
		        			LogMapEntry e = logMap.get(key);
		        			e.generateAlarm("Stream Connection back in safe level");
						}
						if(ShutDownAlarmTriggered){
							shutDownCount--;
						}
					}
	        		
	        		if(!shutDown) {
	        			stream.updateConnectionState();
	        		}
	        	}
				Color tabColor = checkForStreamStatus();
				
				if(tabColor == Color.red && StreamDownAlarm)
				{
					redStatusCount++;
					if(redStatusCount > (stateConnectionAlarmThreshold / 5) && !StreamDownAlarmTriggered)
					{
						Long key = Long.parseLong(stream.ip.replace(".", "") + stream.dstPort);
	        			LogMapEntry e = logMap.get(key);
						e.writeToEventLog(e.streamName + ", " + "Stream packet loss alarm");
						e.generateAlarm("Stream Connection is at a bad level");
						StreamDownAlarmTriggered = true;
					}
					if(StreamDownAlarmTriggered) redStatusCount = (stateConnectionAlarmThreshold / 5);
				}
				else if(redStatusCount > 0 && StreamDownAlarm){
					if(redStatusCount == 1){
						redStatusCount = 0;
						StreamDownAlarmTriggered = false;
						Long key = Long.parseLong(stream.ip.replace(".", "") + stream.dstPort);
	        			LogMapEntry e = logMap.get(key);
	        			e.generateAlarm("Stream Connection back in safe level");
					}
					if(StreamDownAlarmTriggered){
						redStatusCount--;
					}
				}
	            IPLinkNetworkTool.updateConnectedStreamstabColor(tabColor);
	            IPLinkNetworkTool_Lite.updateConnectedStreamstabColor(tabColor);
	            stream.counter = 0;
	        }
	        stream.counter++;
//			}
//			else {
//				stream.counter++;
//			}
		}
		if(mapUpdate){
			map.remove(removeKey);
			mapUpdate = false;
		}
    }
	
	public Color checkForStreamStatus()
    {
        Color retVal = Color.gray;
        Color green = new Color(43,148,49);
        for (Stream stream : map.values())
        {
            int retVal_temp;
            if(stream.connectionState != null)
            	retVal_temp = Integer.parseInt(stream.connectionState);
            else
            	retVal_temp = 0;
            if (retVal_temp == 2)
            {
                retVal = Color.red;
            }
            else if (stream.adminState == 2)
            {
            	retVal = Color.yellow;
            }
            else if (retVal_temp == 1)
            {
                if (retVal!=Color.red && retVal!=Color.yellow)
                {
                    retVal = green;
                }
            }
            
        }
        return retVal;
    }
	
	public Color checkForStreamStatus(String ip, String dstPort)
    {
        Color retVal = Color.gray;
        Color green = new Color(43,148,49);
        Long key = Long.parseLong(ip.replace(".", "") + dstPort);
        Stream stream = map.get(key);
        LogMapEntry lme = logMap.get(key);
        
        int retVal_temp;
        if(stream.connectionState.isEmpty())
        	retVal_temp = 0;
        else
        	retVal_temp = Integer.parseInt(stream.connectionState);
        
        if(stream.adminState == 2) {
        	retVal = Color.yellow;
        	retVal_temp = 4;
        }
        else 
        {
	        
	        if (retVal_temp == 2)
	        {
	            retVal = Color.red;
	        }
	        else if (retVal_temp == 1)
	        {
	            if (retVal!=Color.red && retVal!=Color.yellow)
	            {
	                retVal = green;
	            }
	        }
        }
        lme.dataState = retVal_temp;
        return retVal;
    }
	
	public boolean checkIfExisting(String ip, int id) 
	{
        for (Stream stream : map.values())
        {
            if (ip.equals(stream.ip))
            {
                if (id == stream.index)
                {
                    // found an entry matching same id and ip
                    return true;
                }
            }
        }
        return false;
	}
	
	public boolean checkIfMacAllowed(Stream stream) {
		stream.getMacs();
		for (String Mac : macList.getRows())
        {
			if(Mac.equalsIgnoreCase(stream.mac1.replace(" ", ":")) || Mac.equalsIgnoreCase(stream.mac2.replace(" ", ":")) || Mac.equalsIgnoreCase(stream.mac3.replace(" ", ":")))
				return true;
        }
        return false;
	}
	
	public String getConnectionsForSave()
    {
        Collection<LogMapEntry> c = logMap.values();
        String s = "";
        for (LogMapEntry e : c)
        {
        	Stream stream = map.get(Long.parseLong(e.address.toString().replace(".", "").replace("/", "") + e.destPort));
            s += "{"+e.toSaveString()+ ", " + stream.statusOnly + ", " + stream.readCommunity + "}\t";
        }
        return s;
    }
	
	public void loadConnections(String s) {
		String[] entries = s.split("\t");
        for (int i = 0; i < entries.length; i++)
        {
        	System.out.println(i);
            String e = entries[i].substring(1,entries[i].length()-1);
            Stream newStream = Stream.createFromString(e);
            LogMapEntry newEntry = LogMapEntry.createFromString(e);
            newStream.populateVars();
            
            long key = Long.parseLong(newStream.ip.replace(".", "") + newStream.dstPort);
            if(!map.containsKey(key))
            {
            	map.put(key, newStream);
            	try
                {
                    newEntry.logEnties = new NetworkLogEntryArray(600,1,key);
                    logMap.put(key, newEntry);
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            	
            }
                
        }
	}
	
	public Stream getStream(String ip, String dstPort) {
		return map.get(Long.parseLong(ip + dstPort));
	}
	
	public String getStreamName(long key) {
		String s = "";
        Stream stream = map.get(key);
        if (stream != null)
        {
            s = stream.streamName;
            if (s == null)
                s = "";
        }
        
        return s;
	}
	
	public void cleanUp() {
        for (Long l : map.keySet()) {
            Stream stream = map.get(l);
            if (stream == null || stream.streamName == null)
            {
                map.remove(l);
            }
        }
    }
	
	public int bindState()
    {
        if (bindfailed) return 1;
        if ( map == null) return 0;
        return 2;
    }

	 public int pointsInQueue() 
	 {
	        
        int points  = 0;
        for(Map.Entry<Long,LogMapEntry> entry : logMap.entrySet()) 
        {
            LogMapEntry value = entry.getValue();
            points += value.logEnties.pointsInQueue();
        }
        return points;
	        
	 }
	 void changeWindowSize(int windowSize)
	 {
        for(Map.Entry<Long,LogMapEntry> entry : logMap.entrySet()) 
        {
            LogMapEntry value = entry.getValue();
            value.logEnties.changeWindowSize(windowSize);
        }
	        
	 }
	 
	 void reset() 
	 {
        for(Map.Entry<Long,LogMapEntry> entry : logMap.entrySet()) 
        {
            LogMapEntry value = entry.getValue();
            value.logEnties.reset();
        }
    }
	 
	 public void disconnect(Long key) 
	 {
        if (key == null)return;
        LogMapEntry e = logMap.get(key);
        if (e == null)return;
       // e.closeFiles();
        Stream stream = map.get(key);
        if(stream == null)return;
        
        logMap.remove(key);
        removeKey = key;
        mapUpdate = true;
    }
	 
	 public NetworkLogDataPoint getNextPoint() 
	 {
	        
        if (queuedPoints.isEmpty())
        {
            for(Map.Entry<Long,LogMapEntry> entry : logMap.entrySet()) 
            {
                LogMapEntry value = entry.getValue();
                NetworkLogDataPoint p = value.logEnties.getNextPoint();
                if (p != null)
                {
                    queuedPoints.add(p);
                }
            }
        }
        
        if (queuedPoints.isEmpty())
        {
            return null;
        }
        
        return queuedPoints.remove(0);
                
    }
	 
	 public void createPacket(String ip, int dstPort)
	 {
		Long key = Long.parseLong(ip.replace(".", "") + dstPort);
        LogMapEntry lme2 = logMap.get(key);
        Stream stream = map.get(key);
        
        IplinkNetworkLogEntry inle = lme2.logEnties.add(stream);
               
        if (lme2.streamName == null)
        {
            lme2.streamName = stream.streamName;
            lme2.streamName = lme2.streamName.replace('\\', ' ');
            lme2.streamName = lme2.streamName.replace('/', ' ');
            lme2.streamName = lme2.streamName.replace('-', ' ');
            lme2.streamName = lme2.streamName.replace('*', ' ');
            lme2.streamName = lme2.streamName.replace('?', ' ');
            lme2.streamName = lme2.streamName.replace('|', ' ');
            lme2.streamName = lme2.streamName.replace('<', ' ');
            lme2.streamName = lme2.streamName.replace('>', ' ');
            lme2.streamName = lme2.streamName.replace('\"', ' ');
        }
        try
        {
        	if(lastEntryMap.get(key) == null)
        	{
	            lme2.writeToLog(inle);
        	}
        	else if(inle.isDifference(lastEntryMap.get(key))) {
        		lme2.writeToLog(inle);
        	}
        	lastEntryMap.put(key, inle);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

	 public void createPacket(String ip, int dstPort, boolean NaN)
	 {
		 if(NaN)
		 {
			Long key = Long.parseLong(ip.replace(".", "") + dstPort);
	        LogMapEntry lme2 = logMap.get(key);
	        IplinkNetworkLogEntry inle = new IplinkNetworkLogEntry();
	        lme2.writeNaNToLog(inle);
		 }
    }
	 
	@Override
	public void run() {

        int streamCounter = 0;
		while(true) {
			//System.out.println(streamCounter);
			try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(MultiLiveLookPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
			
			streamCounter++;
            if(streamCounter > 10) {
            	streamCounter = 0;
            	snmpRequest();
            }
			
		}
		
	}
	
	public Collection<LogMapEntry> getStreams() {
		return logMap.values();
	}

}
