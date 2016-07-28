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


/**
 * SnmpMgr manages all functionalities related to snmp in LiveLook.
 * It handles: the timing of new snmp requests, the start of writing log, the creation of stream objects,
 * 				and the managing of the stream statuses
 * 
 * @author Josh Lucas
 *
 */
/**
 * @author jlucas
 *
 */
/**
 * @author jlucas
 *
 */
public class SnmpMgr implements Runnable{


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
	public static boolean DefaultShutDownAlarm = true;
	public static boolean DefaultStreamDownAlarm = true;
    boolean StreamDownAlarmTriggered = false;
    boolean ShutDownAlarmTriggered = false;
    boolean mapRemove = false;
    long removeKey;
    boolean mapAdd = false;
    boolean prepareMenus = false;
    Stream addStream;
    LogMapEntry addLME;
	
	/**
	 * Constructor of SnmpMgr
	 * 
	 * @param lite		This is not necessary for NetXpress but for IPLINK it tells if it is lite version or not
	 * @param macList	This is the list of MacAdresses allowed to connect to
	 */
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
	
	/**
	 * 
	 * This method is responsible for the creation of new stream objects.
	 * It also handles the creation of the parallel LogMapEntry object
	 * 
	 * 
	 * @param ip			ipaddress of the stream being connected to
	 * @param streamID		id that the stream is indexed in the NetXpress 
	 * @param readCommunity		snmp read community entered when connecting 
	 * @return			returns a new Stream object
	 */
	public Stream addStream(String ip, String streamID, String readCommunity) {
            Stream stream = new Stream(ip, streamID, nextStreamType, true, statusOnly, readCommunity);
            stream.StreamDownAlarm = StreamDownAlarm;
            stream.ShutDownAlarm = ShutDownAlarm;
            Long temp = Long.parseLong(ip.replace(".", "") + stream.dstPort);
            LogMapEntry lme = null;
            try {
                    lme = new LogMapEntry(InetAddress.getByName(ip), Integer.parseInt(stream.dstPort), stream);
                    lme.index = Integer.parseInt(streamID);
                    lme.streamName = stream.streamName;
                    lme.logEnties = new NetworkLogEntryArray(600,1,temp);
                    addStream = stream;
                    addLME = lme;
                    mapAdd = true;
                    //stream.populateVars();
            } catch (UnknownHostException e) {
                    e.printStackTrace();
            }
            return stream;
	}
	
	
	/**
	 * This causes the streams to request info from the NetXpress machine
	 */
	public void refreshStreams()
        {
            for(Stream stream: map.values()) {
                    stream.populateVars();
            }      
        }
	
	/**
	 * This method is responsible for the periodic requesting of information from the NetXpress machine.
	 * It will cycle through the streams, and check for alarm statuses and if it shuts down, or packets are skipped.
	 */
    public void snmpRequest()
    {
		//goes through all the streams in the map
		for(Stream stream: map.values()) {

			//counts to see if it is time to poll for data
	        if(stream.counter >= stream.statsInterval) {
	        	//if the stream is status only it will not poll for data 
	        	if(!stream.statusOnly){
	        		//checks to make sure if the stream has been shut down before it queries
		        	boolean shutDown = stream.checkIfShutDown();
		        	
		        	if(!shutDown){
		        		//though NetXpress has mib indexes for every 5 seconds of data 
		        		//NetXpress only updates the mibs every 10 seconds thus when polling 
		        		//every 5 seconds approximately you have to take this into account
		        		//updatePacketsSkipped() will show how many times this info must be repolled and displayed
		        		boolean reset = stream.updatePacketsSkipped();
		        		int packetsSkipped = stream.packetsSkipped;
		        		
		        		//if no packets have been skipped we can update and post one point
			        	if(packetsSkipped == 0) {
			        		try {
			        			//this is the snmp request for the stream
				        		stream.populateVars();
				        		
				        		//this creates a packet for the LogMapEntry to use in it's functions
				        		//also though it say if(reset) it really is if(!reset)
				        		//thus if stats of the stream hasn't been reset this will be true
				        		if(reset)
				        			createPacket(stream.ip, Integer.parseInt(stream.dstPort));
				        		else
				        		{
				        			Long key = Long.parseLong(stream.ip.replace(".", "") + stream.dstPort);
				        			LogMapEntry e = logMap.get(key);
				        			//writes an event to the event log 
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
			        		//for every packet skipped by NetXpress it will go down the indexes and update
							for(int x = packetsSkipped; x >= 0; x--) 
							{
								try {
									//this is the snmp request for the stream
									stream.populateVars();
									
									//this creates a packet for the LogMapEntry to use in it's functions
					        		//also though it say if(reset) it really is if(!reset)
					        		//thus if stats of the stream hasn't been reset this will be true
									if(reset)
					        			createPacket(stream.ip, Integer.parseInt(stream.dstPort));
					        		else
					        		{
					        			Long key = Long.parseLong(stream.ip.replace(".", "") + stream.dstPort);
					        			LogMapEntry e = logMap.get(key);
					        			//writes an event to the event log 
					        			e.writeToEventLog(e.streamName + ", " + "Stat Reset");
					        			e.statReset = true;
					        		}
								}
				        		catch(NumberFormatException e) {
				        			//this is to make sure that if packets won't be skipped due to this catch
				        			packetsSkipped++;
				        			e.printStackTrace();
				        		}
		
							}
							stream.packetsSkipped = 0;
			        	}
		        	}
		        	else {  //This else is incase of shut down
		        		Long key = Long.parseLong(stream.ip.replace(".", "") + stream.dstPort);
	        			LogMapEntry e = logMap.get(key);
	        			e.writeToEventLog(e.streamName + ", " + "Stream not up");
	        			createPacket(stream.ip, Integer.parseInt(stream.dstPort), true);
		        	}
	        	}
	        	else //This is if status only
	        	{
                            //this checks if the stream has been shut down before query
                            boolean shutDown = stream.checkIfShutDown();

                            if(shutDown) 
                            {
                                    //This writes to the log and then counts till the alarm is triggered
                                    //shutDownCount++;
                                    Long key = Long.parseLong(stream.ip.replace(".", "") + stream.dstPort);
                                    LogMapEntry e = logMap.get(key);
                                    e.writeToEventLog(e.streamName + ", " + "Stream not up");
                                    if(ShutDownAlarm)shutDownCount++;
                                    if(shutDownCount > stateConnectionAlarmThreshold && !ShutDownAlarmTriggered && ShutDownAlarm)
                                    {
                                            e.generateAlarm("Stream's admin state is down");
                                            ShutDownAlarmTriggered = true;
                                    }
                                    if(ShutDownAlarmTriggered) shutDownCount = stateConnectionAlarmThreshold;
                            }
                            //this else if is here to count down for the alarm threshold and trigger a alarm clear email
                            else if(shutDownCount > 0 && ShutDownAlarm){
                                            if(shutDownCount == 1){
                                                    shutDownCount = 0;
                                                    ShutDownAlarmTriggered = false;
                                                    Long key = Long.parseLong(stream.ip.replace(".", "") + stream.dstPort);
                                            LogMapEntry e = logMap.get(key);
                                            e.generateAlarm("Stream's admin state is up");
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
                                //This writes to the log and then counts till the alarm is triggered
                                redStatusCount++;
                                if(redStatusCount > stateConnectionAlarmThreshold && !StreamDownAlarmTriggered)
                                {
                                        Long key = Long.parseLong(stream.ip.replace(".", "") + stream.dstPort);
                                LogMapEntry e = logMap.get(key);
                                        e.writeToEventLog(e.streamName + ", " + "Stream packet loss alarm");
                                        e.generateAlarm("Stream is down");
                                        StreamDownAlarmTriggered = true;
                                }
                                if(StreamDownAlarmTriggered) redStatusCount = stateConnectionAlarmThreshold;
                        }
                        //this else if is here to count down for the alarm threshold and trigger a alarm clear email
                        else if(redStatusCount > 0 && StreamDownAlarm){
                                if(redStatusCount == 1){
                                        redStatusCount = 0;
                                        StreamDownAlarmTriggered = false;
                                        Long key = Long.parseLong(stream.ip.replace(".", "") + stream.dstPort);
                                LogMapEntry e = logMap.get(key);
                                e.generateAlarm("Stream is up");
                                }
                                if(StreamDownAlarmTriggered){
                                        redStatusCount--;
                                }
                        }
		    //this updates the color boxes in the Stream Status tab
	            IPLinkNetworkTool.updateConnectedStreamstabColor(tabColor);
	            IPLinkNetworkTool_Lite.updateConnectedStreamstabColor(tabColor);
	            stream.counter = 0;
	        }
	        //counts to see if it is time to query the streams for info
	        stream.counter++;
//			}
//			else {
//				stream.counter++;
//			}
		}
		//waits for the loop to finish accessing the map and then updates them to avoid
		//ConcurrentMismatchError
		if(mapRemove){
			logMap.remove(removeKey);
			//map.remove(removeKey); // do remove from map when disconnect routine is called
			mapRemove = false;
			prepareMenus = true;
		}
		if(mapAdd){
			long key = Long.parseLong(addStream.ip.replace(".", "") + addStream.dstPort);
			logMap.put(key, addLME);
			map.put(key, addStream);
			mapAdd = false;
			prepareMenus = true;
		}
    }
	
	/**
	 * This returns the current stream status in the form of a color for the stream status panel
	 * 
	 * @return   color of stream status
	 */
    public Color checkForStreamStatus()
    {
        Color retVal = Color.gray;
        Color green = new Color(43,148,49);

        for (Stream stream : map.values())
        {
            if(stream !=  null){ // check if the object is not null - Fix for NumberFormat Exception
                int retVal_temp;
                if(stream.connectionState.isEmpty())
                    retVal_temp = 0;
                else
                    retVal_temp = Integer.parseInt(stream.connectionState);
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
        }
        return retVal;
    }
	
	/**
	 * This returns the stream status related color but specifies the stream
	 * 
	 * @param ip
	 * @param dstPort
	 * @return
	 */
    public Color checkForStreamStatus(String ip, String dstPort)
    {
        Color retVal = Color.gray;
        Color green = new Color(43,148,49);
        Long key = Long.parseLong(ip.replace(".", "") + dstPort);
      
        Stream stream = map.get(key);
        LogMapEntry lme = logMap.get(key);
        
        if(stream !=  null){ // check if the object is not null - Fix for Null pointer Exception and NumberFormat Exception
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
             if(lme != null) lme.dataState = retVal_temp; // check if the object is not null 
        }
       
        return retVal;
    }
	
	
	/**
	 * Checks if the stream is existing, this is used to stop multiple instances from existing
	 * 
	 * @param ip
	 * @param id
	 * @return			true if the stream is already existing
	 */
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
	
	
	/**
	 * This checks to see if the mac of the stream is in the list of the macs the user has loaded
	 * 
	 * @param stream
	 * @return				returns true if the mac is allowed false otherwise
	 */
	public boolean checkIfMacAllowed(Stream stream) {
		//This triggers the stream to get it's related mac addresses
		stream.getInterfaceMacs();
		
                //Goes through each Mac and checks if it matches one of the three macs pulled from the stream
		for(String Mac: macList.getRows())
                {
                    //System.out.println(Mac);
                    for (String mac : stream.mac) {
                        // System.out.println(stream.mac[streamMacIndex]);
                        if ((mac != null) && (Mac.equalsIgnoreCase(mac.replace(" ", ":")))) {
                            // found a match
                            return true;
                        }
                    }
                }
        return false;
	}
	
	
	/**
	 * Saves all the connection information using their individual save methods
	 * This is used to load them at program start
	 * 
	 * @return
	 */
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
	
	
	/**
	 * This loads the connections which information is in the String passed to it.
	 * 
	 * @param s
	 */
	public void loadConnections(String s) {
		String[] entries = s.split("\t");
        for (int i = 0; i < entries.length; i++)
        {
        	//System.out.println(i);
            String e = entries[i].substring(1,entries[i].length()-1);
            Stream newStream = Stream.createFromString(e);
            LogMapEntry newEntry = LogMapEntry.createFromString(e);
            newEntry.stream = newStream;
            newStream.populateVars();
            
            long key = Long.parseLong(newStream.ip.replace(".", "") + newStream.dstPort);
            //makes sure the stream doesn't exist previously
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
	
	
	/**
	 * Returns the Stream that is stored in the map under these parameters
	 * 
	 * @param ip
	 * @param dstPort
	 * @return
	 */
	public Stream getStream(String ip, String dstPort) {
		return map.get(Long.parseLong(ip + dstPort));
	}
	
	
	/**
	 * Returns the stream name of the stream related to the key passed in the params
	 * 
	 * @param key
	 * @return
	 */
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
	
	
	/**
	 * This method removes Streams with null values
	 */
	public void cleanUp() {
        for (Long l : map.keySet()) {
            Stream stream = map.get(l);
            if (stream == null || stream.streamName == null)
            {
                map.remove(l);
                logMap.remove(l);
            }
        }
    }
	
	
	/**
	 * This returns 1 if the bind failed 0 if the map is null and 2 if everything is good
	 * 
	 * @return
	 */
	public int bindState()
    {
        if (bindfailed) return 1;
        if ( map == null) return 0;
        return 2;
    }

	
	 /**
	  * This retrieves points that are in the queue of the LogMapEntry
	  * 
	 * @return
	 */
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
	
	
	 /**
	  * This method is responsible for handling window resizing
	  * 
	 * @param windowSize
	 */
	void changeWindowSize(int windowSize)
	 {
        for(Map.Entry<Long,LogMapEntry> entry : logMap.entrySet()) 
        {
            LogMapEntry value = entry.getValue();
            value.logEnties.changeWindowSize(windowSize);
        }
       
	 }
	 
	
	 /**
	 *  This method resets the log entries of the logMapEntries.
	 *  Used to reset zoom.
	 */
	void reset() 
	{
            for(Map.Entry<Long,LogMapEntry> entry : logMap.entrySet()) 
            {
                LogMapEntry value = entry.getValue();
                value.logEnties.reset();
            }
        }
	 
	 /**
	  * 
	  * 
	 * @param key
	 */
	public void disconnect(Long key) 
	{
            if (key == null)
                return;
            LogMapEntry e = logMap.get(key);
            if (e == null)
                return;
           // e.closeFiles();
            Stream stream = map.get(key);
            if(stream == null)
                return;
            removeKey = key;
            map.remove(removeKey); // remove from map
            mapRemove = true;
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

	public void disconnectAll() {
        map.clear();
        logMap.clear();
	}
	
}
