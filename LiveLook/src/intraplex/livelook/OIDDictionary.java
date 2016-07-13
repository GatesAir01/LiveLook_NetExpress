package intraplex.livelook;

public class OIDDictionary {
	//IPLINK OID INFO
//	public static String harrisIplinkRTPStreamID = "1.3.6.1.4.1.290.7.1.1.1.8.6.1.2.1.1";
//	public static String harrisIplinkRTPStrmName = "1.3.6.1.4.1.290.7.1.1.1.8.6.1.2.1.2";
//	public static String harrisIplinkRTPStrmTxDestinationIP = "1.3.6.1.4.1.290.7.1.1.1.8.6.1.2.1.4";
//	public static String harrisIplinkRTPStrmRxSourceIP = "1.3.6.1.4.1.290.7.1.1.1.8.6.1.2.1.5";
//	public static String harrisIplinkRTPStrmDestinationUDP = "1.3.6.1.4.1.290.7.1.1.1.8.6.1.2.1.7";
//	public static String harrisIplinkRTPStrmSourceUDP = "1.3.6.1.4.1.290.7.1.1.1.8.6.1.2.1.9";
//	public static String harrisIplinkRTPStreamStatsRTPState = "1.3.6.1.4.1.290.7.1.1.1.8.6.1.3.1.103";
//	public static String harrisIplinkRTPStreamStatsTxPacketsSent = "1.3.6.1.4.1.290.7.1.1.1.8.6.1.3.1.106";
//	public static String harrisIplinkRTPStreamStatsRxPacketsReceived = "1.3.6.1.4.1.290.7.1.1.1.8.6.1.3.1.108";
//	public static String harrisIplinkRTPStreamStatsPacketsLost = "1.3.6.1.4.1.290.7.1.1.1.8.6.1.3.1.107";
//	public static String harrisIplinkRTPStreamStatsRxPacketsRecovered = "1.3.6.1.4.1.290.7.1.1.1.8.6.1.3.1.109";
//	public static String harrisIplinkRTPStreamStatsRxPacketsLate = "1.3.6.1.4.1.290.7.1.1.1.8.6.1.3.1.111";
//	public static String harrisIplinkRTPStreamStatsRxPacketsEarly = "1.3.6.1.4.1.290.7.1.1.1.8.6.1.3.1.110";
//	public static String harrisIplinkRTPStreamStatsRxAvgJitterInMsec = "1.3.6.1.4.1.290.7.1.1.1.8.6.1.3.1.141";
//	public static String harrisIplinkRTPStreamStatsRxMaxJitterInMsec = "1.3.6.1.4.1.290.7.1.1.1.8.6.1.3.1.142";
//	public static String harrisIplinkRTPStreamStatsRxGroupDelayInMsec = "1.3.6.1.4.1.290.7.1.1.1.8.6.1.3.1.143";
//	public static String harrisIplinkRTPStreamStatsRxExpectedQLevel = "1.3.6.1.4.1.290.7.1.1.1.8.6.1.3.1.146";
//	public static String harrisIplinkRTPStreamStatsPacketsLostLocal = "1.3.6.1.4.1.290.7.1.1.1.8.6.1.3.1.125";
//	public static String harrisIplinkRTPStreamStatsPacketsLostPctBeforeRecovery = "1.3.6.1.4.1.290.7.1.1.1.8.6.1.3.1.133";
//	public static String harrisIplinkRTPStreamStatsPacketsCumLostPctBeforeRecovery = "1.3.6.1.4.1.290.7.1.1.1.8.6.1.3.1.135";
//	public static String harrisIplinkRTPStreamStatsRxCurrentQLevel = "1.3.6.1.4.1.290.7.1.1.1.8.6.1.3.1.145";
//	
	
	private static String ifPhysAddress = "1.3.6.1.2.1.2.2.1.6";
	
	//NETEXPRESS OID INFO
	private static String harrisStreamName = "1.3.6.1.4.1.290.7.1.1.1.5.3.1.1.2.1.2";
	private static String harrisStreamRxPacketCount = "1.3.6.1.4.1.290.7.1.1.1.5.3.1.1.3.1.4";
	private static String harrisStreamRxPacketCurrQLength = "1.3.6.1.4.1.290.7.1.1.1.5.3.1.1.3.1.6";
	private static String harrisStreamRxPacketLost = "1.3.6.1.4.1.290.7.1.1.1.5.3.1.1.3.1.12";
	private static String harrisRxFecRecovered = "1.3.6.1.4.1.290.7.1.1.1.5.3.1.1.3.1.14";
	private static String harrisStreamLocalRTPPort = "1.3.6.1.4.1.290.7.1.1.1.5.3.1.1.2.1.20";
	private static String harrisStreamOperState = "1.3.6.1.4.1.290.7.1.1.1.5.3.1.1.2.1.10";
	private static String harrisStreamAdminState = "1.3.6.1.4.1.290.7.1.1.1.5.3.1.1.2.1.9";
	private static String harrisStreamRxPacketLate = "1.3.6.1.4.1.290.7.1.1.1.5.3.1.1.3.1.10";
	private static String harrisStreamRxPacketEarly = "1.3.6.1.4.1.290.7.1.1.1.5.3.1.1.3.1.9";
	private static String harrisStramStatsInterval = "1.3.6.1.4.1.290.7.1.1.1.5.3.1.1.2.1.27";
	
	//NETXPRESSLX OID INFO
	private static String LXharrisStreamName = "1.3.6.1.4.1.290.7.1.1.1.7.3.1.1.2.1.2";
	private static String LXharrisStreamRxPacketCount = "1.3.6.1.4.1.290.7.1.1.1.7.3.1.1.3.1.4";
	private static String LXharrisStreamRxPacketCurrQLength = "1.3.6.1.4.1.290.7.1.1.1.7.3.1.1.3.1.6";
	private static String LXharrisStreamRxPacketLost = "1.3.6.1.4.1.290.7.1.1.1.7.3.1.1.3.1.12";
	private static String LXharrisRxFecRecovered = "1.3.6.1.4.1.290.7.1.1.1.7.3.1.1.3.1.14";
	private static String LXharrisStreamLocalRTPPort = "1.3.6.1.4.1.290.7.1.1.1.7.3.1.1.2.1.20";
	private static String LXharrisStreamOperState = "1.3.6.1.4.1.290.7.1.1.1.7.3.1.1.2.1.10";
	private static String LXharrisStreamAdminState = "1.3.6.1.4.1.290.7.1.1.1.7.3.1.1.2.1.9";
	private static String LXharrisStreamRxPacketLate = "1.3.6.1.4.1.290.7.1.1.1.7.3.1.1.3.1.10";
	private static String LXharrisStreamRxPacketEarly = "1.3.6.1.4.1.290.7.1.1.1.7.3.1.1.3.1.9";
	private static String LXharrisStramStatsInterval = "1.3.6.1.4.1.290.7.1.1.1.7.3.1.1.2.1.27";
	
	//NETXPRESSLXR OID INFO
	private static String LXRharrisStreamName = "1.3.6.1.4.1.290.7.1.1.1.9.3.1.1.2.1.2";
	private static String LXRharrisStreamRxPacketCount = "1.3.6.1.4.1.290.7.1.1.1.9.3.1.1.3.1.4";
	private static String LXRharrisStreamRxPacketCurrQLength = "1.3.6.1.4.1.290.7.1.1.1.9.3.1.1.3.1.6";
	private static String LXRharrisStreamRxPacketLost = "1.3.6.1.4.1.290.7.1.1.1.9.3.1.1.3.1.12";
	private static String LXRharrisRxFecRecovered = "1.3.6.1.4.1.290.7.1.1.1.9.3.1.1.3.1.14";
	private static String LXRharrisStreamLocalRTPPort = "1.3.6.1.4.1.290.7.1.1.1.9.3.1.1.2.1.20";
	private static String LXRharrisStreamOperState = "1.3.6.1.4.1.290.7.1.1.1.9.3.1.1.2.1.10";
	private static String LXRharrisStreamAdminState = "1.3.6.1.4.1.290.7.1.1.1.9.3.1.1.2.1.9";
	private static String LXRharrisStreamRxPacketLate = "1.3.6.1.4.1.290.7.1.1.1.9.3.1.1.3.1.10";
	private static String LXRharrisStreamRxPacketEarly = "1.3.6.1.4.1.290.7.1.1.1.9.3.1.1.3.1.9";
	private static String LXRharrisStramStatsInterval = "1.3.6.1.4.1.290.7.1.1.1.9.3.1.1.2.1.27";
	
	public static String getMacs() {
		return ifPhysAddress;
	}
	
	public static String getStreamName(int i){
		switch(i) 
		{
			case 0:
				return harrisStreamName;
			case 1:
				return LXharrisStreamName;
			case 2:
				return LXRharrisStreamName;
				
		}
		return "";
	}
	
	public static String getPacketsReceived(int i){
		switch(i) 
		{
			case 0:
				return harrisStreamRxPacketCount;
			case 1:
				return LXharrisStreamRxPacketCount;
			case 2:
				return LXRharrisStreamRxPacketCount;
				
		}
		return "";
	}
	
	public static String getPacketsLost(int i){
		switch(i) 
		{
			case 0:
				return harrisStreamRxPacketLost;
			case 1:
				return LXharrisStreamRxPacketLost;
			case 2:
				return LXRharrisStreamRxPacketLost;
				
		}
		return "";
	}
	
	public static String getPacketsRecovered(int i){
		switch(i) 
		{
			case 0:
				return harrisRxFecRecovered;
			case 1:
				return LXharrisRxFecRecovered;
			case 2:
				return LXRharrisRxFecRecovered;
				
		}
		return "";
	}
	
	public static String getConnectionState(int i){
		switch(i) 
		{
			case 0:
				return harrisStreamOperState;
			case 1:
				return LXharrisStreamOperState;
			case 2:
				return LXRharrisStreamOperState;
				
		}
		return "";
	}
	
	public static String getAdminState(int i){
		switch(i) 
		{
			case 0:
				return harrisStreamAdminState;
			case 1:
				return LXharrisStreamAdminState;
			case 2:
				return LXRharrisStreamAdminState;
				
		}
		return "";
	}
	
	public static String getDestinationPort(int i){
		switch(i) 
		{
			case 0:
				return harrisStreamLocalRTPPort;
			case 1:
				return LXharrisStreamLocalRTPPort;
			case 2:
				return LXRharrisStreamLocalRTPPort;
				
		}
		return "";
	}
	
	public static String getCurrentQueueLength(int i){
		switch(i) 
		{
			case 0:
				return harrisStreamRxPacketCurrQLength;
			case 1:
				return LXharrisStreamRxPacketCurrQLength;
			case 2:
				return LXRharrisStreamRxPacketCurrQLength;
				
		}
		return "";
	}
	
	public static String getPacketsLate(int i){
		switch(i) 
		{
			case 0:
				return harrisStreamRxPacketLate;
			case 1:
				return LXharrisStreamRxPacketLate;
			case 2:
				return LXRharrisStreamRxPacketLate;
				
		}
		return "";
	}
	
	public static String getPacketsEarly(int i){
		switch(i) 
		{
			case 0:
				return harrisStreamRxPacketEarly;
			case 1:
				return LXharrisStreamRxPacketEarly;
			case 2:
				return LXRharrisStreamRxPacketEarly;
				
		}
		return "";
	}
	
	public static String getStatsInterval(int i){
		switch(i) 
		{
			case 0:
				return harrisStramStatsInterval;
			case 1:
				return LXharrisStramStatsInterval;
			case 2:
				return LXRharrisStramStatsInterval;
				
		}
		return "";
	}
	
}
