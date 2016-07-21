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
	String port;
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
    
    protected String mac1;
    protected String mac2;
    protected String mac3;
    
    private int streamType = 0;
    public int packetsSkipped;
    boolean statReset;
    boolean liveViewReset1;
    boolean liveViewReset2;
    boolean liveViewReset;
    
    boolean opened;
    boolean statusOnly;
    
    public boolean StreamDownAlarm = false;
    public boolean ShutDownAlarm = false;
    public String readCommunity;
    
	public Stream(String ip, String streamID, int port, int streamType, boolean populate, boolean statusOnly, String readCommunity) {
		this.ip = ip;
		this.index = Integer.parseInt(streamID);
		this.secondIndex = 1;
		this.port = "" + port;
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
		try {
		if(populate && !statusOnly) {
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
	
	public void getMacs() {
		SNMPOctetString mac1s = new SNMPOctetString(snmp.getSnmp(OIDDictionary.getMacs(), 2).getBytes());
		SNMPOctetString mac2s = new SNMPOctetString(snmp.getSnmp(OIDDictionary.getMacs(), 3).getBytes());
		SNMPOctetString mac3s = new SNMPOctetString(snmp.getSnmp(OIDDictionary.getMacs(), 4).getBytes());
		
		mac1 = mac1s.toHexString().trim();
		mac2 = mac2s.toHexString().trim();
		mac3 = mac3s.toHexString().trim();
		System.out.println(mac1);
		System.out.println(mac2);
		System.out.println(mac3);
	}
	
	public void populateVars() {
		
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
	    packetsReceived = Integer.parseInt(snmp.getSnmp(OIDDictionary.getPacketsReceived(streamType), index, secondIndex + packetsSkipped));
	    packetsLost = Integer.parseInt(snmp.getSnmp(OIDDictionary.getPacketsLost(streamType), index, secondIndex + packetsSkipped));
	    packetsRecovered = Integer.parseInt(snmp.getSnmp(OIDDictionary.getPacketsRecovered(streamType), index, secondIndex + packetsSkipped));
	    connectionState = snmp.getSnmp(OIDDictionary.getConnectionState(streamType), index);
	    adminState = Integer.parseInt(snmp.getSnmp(OIDDictionary.getAdminState(streamType), index));
	    dstPort = snmp.getSnmp(OIDDictionary.getDestinationPort(streamType), index);
	    currentQueueLength = Integer.parseInt(snmp.getSnmp(OIDDictionary.getCurrentQueueLength(streamType), index, secondIndex + packetsSkipped));
	    packetsLate = Integer.parseInt(snmp.getSnmp(OIDDictionary.getPacketsLate(streamType), index, secondIndex + packetsSkipped));
	    packetsEarly = Integer.parseInt(snmp.getSnmp(OIDDictionary.getPacketsEarly(streamType), index, secondIndex + packetsSkipped));
	    statsInterval = Integer.parseInt(snmp.getSnmp(OIDDictionary.getStatsInterval(streamType), index));
	    //printVars();
	    if(packetsSkipped > 0)packetsSkipped--;
	    
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
		{return true;}
		return false;
	}
	
	public void updateConnectionState() 
	{
		connectionState = snmp.getSnmp(OIDDictionary.getConnectionState(streamType), index);
	}
	
	public boolean updatePacketsSkipped() 
	{
		int count = 0;
		
		int tempPacketsReceived = Integer.parseInt(snmp.getSnmp(OIDDictionary.getPacketsReceived(streamType), index, secondIndex));
	    int tempPacketsLost = Integer.parseInt(snmp.getSnmp(OIDDictionary.getPacketsLost(streamType), index, secondIndex));
	    int tempPacketsRecovered = Integer.parseInt(snmp.getSnmp(OIDDictionary.getPacketsRecovered(streamType), index, secondIndex));
	    int tempPacketsLate = Integer.parseInt(snmp.getSnmp(OIDDictionary.getPacketsLate(streamType), index, secondIndex));
	    int tempPacketsEarly = Integer.parseInt(snmp.getSnmp(OIDDictionary.getPacketsEarly(streamType), index, secondIndex));
	    
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
			tempPacketsReceived = Integer.parseInt(snmp.getSnmp(OIDDictionary.getPacketsReceived(streamType), index, secondIndex + count));
		    tempPacketsLost = Integer.parseInt(snmp.getSnmp(OIDDictionary.getPacketsLost(streamType), index, secondIndex + count));
		    tempPacketsRecovered = Integer.parseInt(snmp.getSnmp(OIDDictionary.getPacketsRecovered(streamType), index, secondIndex + count));
		    tempPacketsLate = Integer.parseInt(snmp.getSnmp(OIDDictionary.getPacketsLate(streamType), index, secondIndex + count));
		    tempPacketsEarly = Integer.parseInt(snmp.getSnmp(OIDDictionary.getPacketsEarly(streamType), index, secondIndex + count));
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
		return "" + ip +", " + port + ", " + index + ", " + streamType + ", " + statusOnly + ", " + readCommunity;
	}
	
	public static Stream createFromString(String s) {
        String[] parts = s.split(", ");

        Stream stream = new Stream(parts[0],parts[3], Integer.parseInt(parts[19]), Integer.parseInt(parts[2]), true, Boolean.parseBoolean(parts[20]), parts[21]);
        
        return stream;
	}
}
