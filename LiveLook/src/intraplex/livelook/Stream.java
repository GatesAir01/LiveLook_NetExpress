package intraplex.livelook;

import java.io.FileWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

import snmp.SNMPOctetString;



public class Stream {
	NetworkLogEntryArray logEnties;   
	
    String ip;
    String dstPort;
    String streamName;
    String connectionState;
    String lastConnectionState;
    IPLinkSnmpInterface snmp;
    int index;
    int secondIndex;  
    int counter;
	
    long lastEntry;
	
    protected int currentQueueLength;
    protected int packetsReceived;								/*Rx stream. Number of bytes received*/
    protected int packetsLost;	 /*Lost packets*/							/*Rx stream. Number of bytes received*/
    protected int packetsRecovered;								/*Number of Rx packets recovered by FEC*/
    protected int packetsLate;
    protected int packetsEarly;
    protected int statsInterval;
    protected int adminState;
    
    protected String[] mac = null;
    
    private int streamType = 0;
    public int packetsSkipped;
    boolean statReset;
    boolean liveViewReset1;
    boolean liveViewReset2;
    boolean liveViewReset;
    
    boolean opened;
    boolean statusOnly;
    
    //Fix:  added to fix alarm timing issues
    public boolean StreamDownAlarm = true;
    public boolean ShutDownAlarm = true;
    public boolean StreamDownAlarmTriggered = false;
    public boolean ShutDownAlarmTriggered = false;        
    public long StreamDownAlarmCounter = 0;
    public long ShutDownAlarmCounter = 0;
    public int alarmthresholdtime = 30;    // default
    public String readCommunity;
    public boolean requestFailed = false;
    
	public Stream(String ip, String streamID, int streamType, boolean populate, boolean statusOnly, String readCommunity) {
		this.ip = ip;
		this.index = Integer.parseInt(streamID);
		this.secondIndex = 1;
		this.streamType = streamType;
		this.statusOnly = statusOnly;
		this.readCommunity = readCommunity;
		counter = 0;
		packetsSkipped = 0;
		statReset = false;
		liveViewReset1 = false;
		liveViewReset2 = false;
		this.connectionState = "";
		opened = false;
		snmp = new IPLinkSnmpInterface();
		opened = snmp.open(ip, readCommunity);
		lastEntry = System.currentTimeMillis();
		try {
            if(populate && !statusOnly && !checkIfShutDown()) { // populate all when not shut down - Fix: Stream connection failed when stream is already shut down
                populateVars();
            }
            else if(checkIfShutDown() || statusOnly) {
                populateBaseInfo();
            }
            }catch(Exception e)
            {
                e.printStackTrace();
            }
	}
	
	//mac 1-3 for wan, lan and mgmt interfaces; NetXpress is the only one of NetXpress, LX, LXR that has mgmt port
	public void getInterfaceMacs() {
                mac = new String[3];  // total number of interfaces 
		// get Interface Macs
                byte[] mac1b = snmp.getSnmp(OIDDictionary.getMacs(), 2, false);
		byte[] mac2b = snmp.getSnmp(OIDDictionary.getMacs(), 3, false);
		byte[] mac3b = snmp.getSnmp(OIDDictionary.getMacs(), 4, false);
		
                // convert mac address to string format
                if(mac1b.length  != 0)
                {    
                    mac[0] = snmp.convertMacsFromByte(mac1b);
                }
                if(mac2b.length  != 0)
                {
                    mac[1] = snmp.convertMacsFromByte(mac2b);
                }
                if(mac3b.length  != 0)
                {    
                    mac[2] = snmp.convertMacsFromByte(mac3b);
                }
    //		SNMPOctetString mac1s = new SNMPOctetString(snmp.getSnmp(OIDDictionary.getMacs(), 2).getBytes());
//		SNMPOctetString mac2s = new SNMPOctetString(snmp.getSnmp(OIDDictionary.getMacs(), 3).getBytes());
//		SNMPOctetString mac3s = new SNMPOctetString(snmp.getSnmp(OIDDictionary.getMacs(), 4).getBytes());
//		
//		mac1 = mac1s.toHexString().trim();
//		mac2 = mac2s.toHexString().trim();
//		mac3 = mac3s.toHexString().trim();
		//System.out.println(mac[0]);
		//System.out.println(mac[1]);
		//System.out.println(mac[2]);
	}
	
	public boolean populateVars() {
		
//		srcIpAddress = snmp.getSnmp(OIDDictionary.harrisIplinkRTPStrmRxSourceIP, index);
//	    dstIpAddress = snmp.getSnmp(OIDDictionary.harrisIplinkRTPStrmTxDestinationIP, index);
//	    srcPort = snmp.getSnmp(OIDDictionary.harrisIplinkRTPStrmSourceUDP, index);
//	    dataState = Integer.parseInt(snmp.getSnmp(OIDDictionary.harrisIplinkRTPStreamStatsRTPState, index));
//	    pktSent = Integer.parseInt(snmp.getSnmp(OIDDictionary.harrisIplinkRTPStreamStatsTxPacketsSent, index));
//	    pktsRcvd = Integer.parseInt(snmp.getSnmp(OIDDictionary.harrisIplinkRTPStreamStatsRxPacketsReceived, index));
//	    pktsLost = Integer.parseInt(snmp.getSnmp(OIDDictionary.harrisIplinkRTPStreamStatsPacketsLost, index));
//	    pktsRecvd = Integer.parseInt(snmp.getSnmp(OIDDictionary.harrisIplinkRTPStreamStatsRxPacketsRecovered, index));
//	    pktsLate = Integer.parseInt(snmp.getSnmp(OIDDictionary.harrisIplinkRTPStreamStatsRxPacketsLate, index));
//	    pktsEarly = Integer.parseInt(snmp.getSnmp(OIDDictionary.harrisIplinkRTPStreamStatsRxPacketsEarly, index));
//	    AvgJitter = Float.parseFloat(snmp.getSnmp(OIDDictionary.harrisIplinkRTPStreamStatsRxAvgJitterInMsec, index));
//	    MaxJitter = Float.parseFloat(snmp.getSnmp(OIDDictionary.harrisIplinkRTPStreamStatsRxMaxJitterInMsec, index));
//	    GrpDelay = Float.parseFloat(snmp.getSnmp(OIDDictionary.harrisIplinkRTPStreamStatsRxGroupDelayInMsec, index));
//	    ExpectedQ = Integer.parseInt(snmp.getSnmp(OIDDictionary.harrisIplinkRTPStreamStatsRxExpectedQLevel, index));
//	    pktsLostLocal = Integer.parseInt(snmp.getSnmp(OIDDictionary.harrisIplinkRTPStreamStatsPacketsLostLocal, index));
//	    pktsLostPct = Float.parseFloat(snmp.getSnmp(OIDDictionary.harrisIplinkRTPStreamStatsPacketsLostPctBeforeRecovery, index));
//	    pktsLostPctCum = Float.parseFloat(snmp.getSnmp(OIDDictionary.harrisIplinkRTPStreamStatsPacketsCumLostPctBeforeRecovery, index));
//	    currQLen = Integer.parseInt(snmp.getSnmp(OIDDictionary.harrisIplinkRTPStreamStatsRxCurrentQLevel, index));
//	    
	    lastEntry = System.currentTimeMillis();
	    streamName = snmp.getSnmp(OIDDictionary.getStreamName(streamType), index);
            if(streamName.isEmpty()) // do not continue and return if the snmp is not returning valid stream name for the stream type and index specified
                return false;
	    packetsReceived = snmp.getSnmpInteger(OIDDictionary.getPacketsReceived(streamType), index, secondIndex + packetsSkipped);
	    if(packetsReceived == -1) {
	    	requestFailed = true;
	    	System.out.println("Request Failed/Bad Int 152");
	    	return true;
	    }
	    packetsLost = snmp.getSnmpInteger(OIDDictionary.getPacketsLost(streamType), index, secondIndex + packetsSkipped);
	    if(packetsLost == -1) {
	    	requestFailed = true;
	    	System.out.println("Request Failed/Bad Int 158");
	    	return true;
	    }
	    packetsRecovered = snmp.getSnmpInteger(OIDDictionary.getPacketsRecovered(streamType), index, secondIndex + packetsSkipped);
	    if(packetsRecovered == -1) {
	    	requestFailed = true;
	    	System.out.println("Request Failed/Bad Int 164");
	    	return true;
	    }
	    connectionState = snmp.getSnmp(OIDDictionary.getConnectionState(streamType), index);
	    adminState = snmp.getSnmpInteger(OIDDictionary.getAdminState(streamType), index);
	    if(adminState == -1) {
	    	requestFailed = true;
	    	System.out.println("Request Failed/Bad Int 171");
	    	return true;
	    }
	    dstPort = snmp.getSnmp(OIDDictionary.getDestinationPort(streamType), index);
	    currentQueueLength = snmp.getSnmpInteger(OIDDictionary.getCurrentQueueLength(streamType), index, secondIndex + packetsSkipped);
	    if(currentQueueLength == -1) {
	    	requestFailed = true;
	    	System.out.println("Request Failed/Bad Int 178");
	    	return true;
	    }
	    packetsLate = snmp.getSnmpInteger(OIDDictionary.getPacketsLate(streamType), index, secondIndex + packetsSkipped);
	    if(packetsLate == -1) {
	    	requestFailed = true;
	    	System.out.println("Request Failed/Bad Int 184");
	    	return true;
	    }
	    packetsEarly = snmp.getSnmpInteger(OIDDictionary.getPacketsEarly(streamType), index, secondIndex + packetsSkipped);
	    if(packetsEarly == -1) {
	    	requestFailed = true;
	    	System.out.println("Request Failed/Bad Int 190");
	    	return true;
	    }
	    //printVars();
	    if(packetsSkipped > 0)packetsSkipped--;
	    return true;
	}
	
	public void populateBaseInfo() {
		streamName = snmp.getSnmp(OIDDictionary.getStreamName(streamType), index);
		dstPort = snmp.getSnmp(OIDDictionary.getDestinationPort(streamType), index);
	}
	
	public boolean checkIfShutDown() 
	{
		String adminString = snmp.getSnmp(OIDDictionary.getAdminState(streamType), index);
		try
		{
                    adminState = Integer.parseInt(adminString);
                    if(adminState == 1) {
                            return false;
                    }
                    else if(adminState == 2)
                    {
                            return true;
                    }
		}
		catch(Exception e) 
		{
                    return true;
                }
		return false;
	}
	
	public void updateConnectionState() 
	{
            lastEntry = System.currentTimeMillis();
            connectionState = snmp.getSnmp(OIDDictionary.getConnectionState(streamType), index);
	}
	
	public boolean updatePacketsSkipped() 
	{
        int count = 0;
		
        int tempPacketsReceived = snmp.getSnmpInteger(OIDDictionary.getPacketsReceived(streamType), index, secondIndex);
        if(tempPacketsReceived == -1) {
        	requestFailed = true;
	    	System.out.println("Request Failed/Bad Int 237");
        	return true;
        }

	    int tempPacketsLost = snmp.getSnmpInteger(OIDDictionary.getPacketsLost(streamType), index, secondIndex);
	    if(tempPacketsLost == -1) {
        	requestFailed = true;
	    	System.out.println("Request Failed/Bad Int 244");
        	return true;
        }
	    
	    int tempPacketsRecovered = snmp.getSnmpInteger(OIDDictionary.getPacketsRecovered(streamType), index, secondIndex);
	    if(tempPacketsRecovered == -1) {
        	requestFailed = true;
	    	System.out.println("Request Failed/Bad Int 251");
        	return true;
        }
	    
	    int tempPacketsLate = snmp.getSnmpInteger(OIDDictionary.getPacketsLate(streamType), index, secondIndex);
	    if(tempPacketsLate == -1) {
        	requestFailed = true;
	    	System.out.println("Request Failed/Bad Int 258");
        	return true;
        }

	    int tempPacketsEarly = snmp.getSnmpInteger(OIDDictionary.getPacketsEarly(streamType), index, secondIndex);
	    if(tempPacketsEarly == -1) {
        	requestFailed = true;
	    	System.out.println("Request Failed/Bad Int 265");
        	return true;
        }
	    
		if(tempPacketsReceived == 0 && tempPacketsLost == 0 && tempPacketsRecovered == 0 && 
				tempPacketsLate == 0 && tempPacketsEarly == 0)
		{
			statReset = true;
			liveViewReset1 = true;
			liveViewReset2 = true;
			//System.out.println("STATRESET");
			return false;
		}
		else if(statReset == true)
		{
			statReset = false;
		}
		liveViewReset = liveViewReset1 || liveViewReset2;
		
		//System.out.println(tempPacketsReceived + ":" + packetsReceived + ", " +  tempPacketsLost + ":" + packetsLost
	    //		+ ", " + tempPacketsRecovered + ":" +  packetsRecovered + ", " +  tempPacketsLate + ":" +  packetsLate
	    //		+ ", " +  tempPacketsEarly + ":" +  packetsEarly);
		
		while(tempPacketsReceived != packetsReceived || tempPacketsLost != packetsLost
	    		|| tempPacketsRecovered != packetsRecovered || tempPacketsLate != packetsLate
	    		|| tempPacketsEarly != packetsEarly && count < 10)
		{
			//System.out.println(tempPacketsReceived + ":" + packetsReceived + ", " +  tempPacketsLost + ":" + packetsLost
	    	//	+ ", " + tempPacketsRecovered + ":" +  packetsRecovered + ", " +  tempPacketsLate + ":" +  packetsLate
	    	//	+ ", " +  tempPacketsEarly + ":" +  packetsEarly);
            count++;
            tempPacketsReceived = snmp.getSnmpInteger(OIDDictionary.getPacketsReceived(streamType), index, secondIndex + count);
            if(tempPacketsReceived == -1) {
            	requestFailed = true;
    	    	System.out.println("Request Failed/Bad Int 299");
            	return true;
            }
            tempPacketsLost = snmp.getSnmpInteger(OIDDictionary.getPacketsLost(streamType), index, secondIndex + count);
            if(tempPacketsReceived == -1) {
            	requestFailed = true;
    	    	System.out.println("Request Failed/Bad Int 305");
            	return true;
            }
            tempPacketsRecovered = snmp.getSnmpInteger(OIDDictionary.getPacketsRecovered(streamType), index, secondIndex + count);
            if(tempPacketsReceived == -1) {
            	requestFailed = true;
    	    	System.out.println("Request Failed/Bad Int 311");
            	return true;
            }
            tempPacketsLate = snmp.getSnmpInteger(OIDDictionary.getPacketsLate(streamType), index, secondIndex + count);
            if(tempPacketsReceived == -1) {
            	requestFailed = true;
    	    	System.out.println("Request Failed/Bad Int 317");
            	return true;
            }            
            tempPacketsEarly = snmp.getSnmpInteger(OIDDictionary.getPacketsEarly(streamType), index, secondIndex + count);
            if(tempPacketsReceived == -1) {
            	requestFailed = true;
    	    	System.out.println("Request Failed/Bad Int 323");
            	return true;
            }
		}
	    packetsSkipped = count;
	    return true;
	}
	
	public void printVars() {
//		System.out.println(srcIpAddress);
//		System.out.println(dstIpAddress);
//		System.out.println(srcPort);
//		System.out.println(dstPort);
//		System.out.println(dataState);
//		System.out.println(pktSent);
//		System.out.println(pktsRcvd);
//		System.out.println(pktsLost);
//		System.out.println(pktsRecvd);
//		System.out.println(pktsLate);
//		System.out.println(pktsEarly);
//		System.out.println(AvgJitter);
//		System.out.println(MaxJitter);
//		System.out.println(GrpDelay);
//		System.out.println(ExpectedQ);
//		System.out.println(pktsLostLocal);
//		System.out.println(pktsLostPct);
//		System.out.println(pktsLostPctCum);
//		System.out.println(currQLen);
		System.out.println(packetsReceived);
		System.out.println(packetsLost);
		System.out.println(packetsRecovered);
	}
	
	public String toSaveString() {
		return "" + ip +", " + index + ", " + streamType + ", " + statusOnly + ", " + readCommunity;
	}
	
	public static Stream createFromString(String s) {
        String[] parts = s.split(", ");

        Stream stream = new Stream(parts[0],parts[3], Integer.parseInt(parts[2]), true, Boolean.parseBoolean(parts[20]), parts[21]);
        
        return stream;
	}
}
