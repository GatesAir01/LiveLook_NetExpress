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
import java.util.Date;
import java.text.SimpleDateFormat;
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
	public SnmpMgr(MacList macList) {
            lastLogEntry = null;
            bindfailed = false;
            map = new TreeMap<>();
            logMap = new TreeMap<>();
            lastEntryMap = new TreeMap<>();
            queuedPoints = new ArrayList<>();
            mgr = this;
            this.macList = macList;
            shutDownCount = 0;
            redStatusCount = 0;
            String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
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
            
            if(stream.streamName.isEmpty())
                return null;
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
                 if(stream.checkIfShutDown() || stream.statusOnly)    // if stream is shut down,call to populatevars will throw an error!
                    stream.populateBaseInfo();
                 else
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
                        boolean reset; 
                        int packetsSkipped;
                        try {
                        	reset = stream.updatePacketsSkipped();
                                packetsSkipped = stream.packetsSkipped;
                        }
                        catch(NumberFormatException e) {
                        	reset = true;
                        	packetsSkipped = 0;
                        	System.out.println("prevented exception");
                        }

                        //if no packets have been skipped we can update and post one point
                        if(packetsSkipped == 0) {
                            try {
                                //this creates a packet for the LogMapEntry to use in it's functions
                                //also though it say if(reset) it really is if(!reset)
                                //thus if stats of the stream hasn't been reset this will be true
                                if(reset){  // check if there is no statreset
                                        createPacket(stream.ip, Integer.parseInt(stream.dstPort));
                                        stream.requestFailed = false;
                                }
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
                            for(int packet = packetsSkipped; packet >= 0; packet--) 
                            {
                                try {
                                    Stream tempStream = stream; // take a previous instance
                                    //this is the snmp request for the stream
                                    stream.populateVars();      // fectch the new data from NetXpress
                                    if(stream.requestFailed)
                                    {
                                        stream = tempStream;    // if there is a receive timeout, replace the previous instance
                                        stream.requestFailed = false;
                                    }
                                    //this creates a packet for the LogMapEntry to use in it's functions
                                    //also though it say if(reset) it really is if(!reset)
                                     //thus if stats of the stream hasn't been reset this will be true
                                    if(reset)
                                    {
                                        createPacket(stream.ip, Integer.parseInt(stream.dstPort));
                                    }
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
                            e.writeToEventLog(e.streamName + ", " + "Stream is not up from Shutdown");
                            createPacket(stream.ip, Integer.parseInt(stream.dstPort), true); // write NaN in the logs
                    }
                }
                else //This is if status only
                {
                   
                }   
                Long key = Long.parseLong(stream.ip.replace(".", "") + stream.dstPort);
                LogMapEntry e = logMap.get(key);
                if(stream.checkIfShutDown())  //this checks if the stream has been shut down before query
                {
                    //This writes to the log and then counts till the alarm is triggered
                    if(stream.ShutDownAlarm && !stream.ShutDownAlarmTriggered)
                    { 
                        stream.ShutDownAlarmCounter += (System.currentTimeMillis() -  e.lastEntry);;
                                          
                        if(stream.ShutDownAlarmCounter >= (stream.alarmthresholdtime*1000) && !stream.ShutDownAlarmTriggered && stream.ShutDownAlarm)
                        {
                            //e.writeToEventLog(e.streamName + ", " + "Stream is shut down"); -  already done part of generateAlarm
                            e.generateAlarm("Stream is shut down");
                            stream.ShutDownAlarmTriggered = true;
                        }
                    }
                    if(stream.ShutDownAlarmTriggered)
                        stream.ShutDownAlarmCounter = 0;
                }
                //this else if is here to count down for the alarm threshold and trigger a alarm clear email
                else
                {
                    if(stream.ShutDownAlarmTriggered){
                        stream.ShutDownAlarmCounter += (((System.currentTimeMillis() -  e.lastEntry)));
                   
                        if(stream.ShutDownAlarmCounter >= (stream.alarmthresholdtime*1000)  && stream.ShutDownAlarm){
                            //e.writeToEventLog(e.streamName + ", " + "Stream is up");  -  already done part of generateAlarm
                            e.generateAlarm("Stream is up");
                            stream.ShutDownAlarmCounter = 0;
                            stream.ShutDownAlarmTriggered = false;
                        }
                    }
                    else
                    {
                        stream.ShutDownAlarmCounter = 0;
                    }
                    stream.updateConnectionState();
                }
                
                // get the operational state of stream
                stream.updateConnectionState();
                int conState = getStreamConnectionStatus(stream.ip, stream.dstPort);

                if (conState == 2 && !stream.checkIfShutDown()){ // stream down -  RED! only
                    if(stream.StreamDownAlarm && !stream.StreamDownAlarmTriggered  ) 
                    {
                        if(!stream.ShutDownAlarmTriggered){ // if a stream is already shut down (outof service), then dont trigger the stream down alarm once more
                            //This writes to the log and then counts till the alarm is triggered
                            stream.StreamDownAlarmCounter += (System.currentTimeMillis() -  e.lastEntry);
                            if( (stream.StreamDownAlarmCounter >= (stream.alarmthresholdtime*1000))&& !stream.StreamDownAlarmTriggered)
                            {
                               // e.writeToEventLog(e.streamName + ", " + "Stream is down");  -  already done part of generateAlarm
                                e.generateAlarm("Stream is down");
                                stream.StreamDownAlarmTriggered = true;
                                stream.StreamDownAlarmCounter = 0;
                            }
                        }
                    }
                    if(stream.StreamDownAlarmTriggered) 
                    {
                        stream.StreamDownAlarmCounter = 0;
                       // stream.StreamDownAlarmCounter = stateConnectionAlarmThreshold;
                    }
                }
                //this else if is here to count down for the alarm threshold and trigger a alarm clear email
                else if( conState == 1){ // stream up -  GREEN!
                     
                    if(stream.StreamDownAlarmTriggered){ // Stream up alarm is required only if the stream down is already triggered
                        stream.StreamDownAlarmCounter += (((System.currentTimeMillis() -  e.lastEntry)));
                        if((stream.StreamDownAlarmCounter >= (stream.alarmthresholdtime*1000)) && stream.StreamDownAlarm ){
                            //e.writeToEventLog(e.streamName + ", " + "Stream is up");  -  already done part of generateAlarm
                            e.generateAlarm("Stream is up");
                            stream.StreamDownAlarmTriggered = false; 
                            stream.StreamDownAlarmCounter = 0;
                        }
                    }
                    else
                    {
                        stream.StreamDownAlarmCounter = 0;
                    }
                }
                Color tabColor = checkForStreamStatus();
                //this updates the color boxes in the Stream Status tab
                IPLinkNetworkTool.updateConnectedStreamstabColor(tabColor);
                //IPLinkNetworkTool_Lite.updateConnectedStreamstabColor(tabColor); // not neede for netXpress
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
            map.remove(removeKey); 
            mapRemove = false;
            prepareMenus = true;
            //this updates the color boxes in the Stream Status tab
            IPLinkNetworkTool.updateConnectedStreamstabColor(checkForStreamStatus());
        }
        if(mapAdd){
            long key = Long.parseLong(addStream.ip.replace(".", "") + addStream.dstPort);
            logMap.put(key, addLME);
            map.put(key, addStream);
            mapAdd = false;
            prepareMenus = true;
            //this updates the color boxes in the Stream Status tab
            IPLinkNetworkTool.updateConnectedStreamstabColor(checkForStreamStatus());
        }
    }
	
	/**
	 * This returns the current stream status in the form of a color for the stream status panel
	 * 
	 * @return   color of stream status
	 */
    public Color checkForStreamStatus()
    {
        Color retVal = Color.gray; // default color of tab is grey
        Color green = new Color(43,148,49);

        for (Stream stream : map.values()) // loop through all the streams
        {
            if(stream !=  null){ // check if the stream object is not null - Fix for NumberFormat Exception
                int retVal_temp = 0; // temp variable
                if(stream.connectionState.isEmpty())
                {
                    retVal = Color.gray;    //  gray when no state is updated
                }
                else
                {
                    retVal_temp = Integer.parseInt(stream.connectionState);
                    if (stream.adminState == 2) // admin state = 2 : Stream Stut down
                    {
                        retVal = Color.yellow;
                    }
                    else if (retVal_temp == 2) // connectionState = 2  :  Stream Down
                    {
                        retVal = Color.red; 
                    }
                    else if (retVal_temp == 1) // connectionState = 1 : Stream Up
                    {
                        if ((retVal!= Color.red) && (retVal!=Color.yellow))
                        {
                            retVal = green;
                        }
                    }
                    else
                    {
                        retVal = Color.gray;
                    }
                }
            }
        }
        return retVal;
    }
    /**
     * This function returns the stream status related color for the specified stream
     * 
     * @param ip - IP address of the stream
     * @param dstPort - Destination port of the stream
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
     * This returns the stream status in the integer format but specifies the stream
     * 
     * @param ip - IP address of the stream
     * @param dstPort - Destination port of the stream
     * @return retVal - Connection state: 1 =  stream UP, 2 - Stream down, 0 - No status
     */
    public int getStreamConnectionStatus(String ip, String dstPort)
    {
        int retVal = 0;
       
        Long key = Long.parseLong(ip.replace(".", "") + dstPort);
      
        Stream stream = map.get(key);
        LogMapEntry lme = logMap.get(key);
        
        if(stream !=  null){ // check if the object is not null - Fix for Null pointer Exception and NumberFormat Exception
            if(stream.connectionState.isEmpty())
                retVal = 0;
            else
                retVal = Integer.parseInt(stream.connectionState);
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
            Collection<LogMapEntry> collection = logMap.values();
            String tempString = "";
            for (LogMapEntry logMapEntry : collection)
            {
                    Stream stream = map.get(Long.parseLong(logMapEntry.address.toString().replace(".", "").replace("/", "") + logMapEntry.destPort));
                tempString += "{"+logMapEntry.toSaveString()+ ", " + stream.statusOnly + ", " + stream.readCommunity + "}\t";
            }
            return tempString;
        }
	
	
	/**
	 * This loads the connections which information is in the String passed to it.
	 * 
	 * @param s
	 */
	public void loadConnections(String s) {
            String[] entries = s.split("\t");
            if(entries.length == 0) // if no previously saved connections - return doing nothing
                return;
            for (int index = 0; index < entries.length; index++)
            {
                //System.out.println(i);
                String e = entries[index].substring(1,entries[index].length()-1);
                Stream newStream = Stream.createFromString(e);
                LogMapEntry newEntry = LogMapEntry.createFromString(e);
                newEntry.stream = newStream;
                newEntry.streamName = newStream.streamName; // Fix: if in case the stream name is updated in th enetXpress client and the log file has the old name, update the log entry with the streamname
                if(newStream.checkIfShutDown() || statusOnly) { // Fix: For error while loading an Out of Service stream to live look.
                    newStream.populateBaseInfo(); // check if the stream is shutdown while adding it to the Livelook, if Yes  - load basic info only 
                }
                else
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
        for (Long key : map.keySet()) {
            Stream stream = map.get(key);
            if (stream == null || stream.streamName == null)
            {
                map.remove(key);
                logMap.remove(key);
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
            for(Map.Entry<Long,LogMapEntry> logMapEntry : logMap.entrySet()) 
            {
                LogMapEntry value = logMapEntry.getValue();
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
            for(Map.Entry<Long,LogMapEntry> logMapEntry : logMap.entrySet()) 
            {
                LogMapEntry value = logMapEntry.getValue();
                value.logEnties.changeWindowSize(windowSize);
            }
	 }
	 
	
	 /**
	 *  This method resets the log entries of the logMapEntries.
	 *  Used to reset zoom.
	 */
	void reset() 
	{
            for(Map.Entry<Long,LogMapEntry> logMapEntry : logMap.entrySet()) 
            {
                LogMapEntry value = logMapEntry.getValue();
                value.logEnties.reset();
            }
        }
	 
	 /**
	  * This method is used to disconnect a stream by removing it from the maps stored in SnmpMgr
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
            else {
            removeKey = key;
          //  map.remove(removeKey); // remove from map
            mapRemove = true;
            System.out.println("mapRemove is set");
            }
        }
	 
	/**
	 * 	This method retrieves the next point that is stored in each LogMapEntry
	 * 
	 * @return
	 */
	public NetworkLogDataPoint getNextPoint() 
	{
	        
            if (queuedPoints.isEmpty())
            {
                for(Map.Entry<Long,LogMapEntry> logMapEntry : logMap.entrySet()) 
                {
                    LogMapEntry value = logMapEntry.getValue();
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
	 
	/**
	 * This method is used to simulate creating a packet that happened in regular livelook.
	 * This is necessary to provide the LogMapEntry with the information that is needed
	 * for the NetworkLogDataPoint to be pulled from it later.
	 * 
	 * @param ip
	 * @param dstPort
	 */
	public void createPacket(String ip, int dstPort)
	{
            Long key = Long.parseLong(ip.replace(".", "") + dstPort);
            LogMapEntry lme2 = logMap.get(key);
            Stream stream = map.get(key);
            if(stream != null){
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
        }

	/**
	 * This method is used to simulate creating a packet that happened in regular livelook.
	 * This version of createPacket is purely for the purpose of providing NaN values to 
	 * the LogMapEntry.
	 * 
	 * @param ip
	 * @param dstPort
	 */
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
	 
	 
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {

            int streamCounter = 0;
            while(true) {
                            //System.out.println(streamCounter);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(MultiLiveLookPanel.class.getName()).log(Level.SEVERE, null, ex);
                }

                streamCounter++;
                if(streamCounter > 5) {
                    streamCounter = 0;
                    snmpRequest();
                }

            }	
	}
	
	/**
	 * This method is a simple get method to retrieve the streams stored in the logMap
	 * 
	 * @return
	 */
	public Collection<LogMapEntry> getStreams() {
		return logMap.values();
	}

	
	/**
	 * This method is simply used to disconnect all the streams that are currently stored in both the map and logMap
	 */
	public void disconnectAll() {
        map.clear();
        logMap.clear();
	}
	
}
