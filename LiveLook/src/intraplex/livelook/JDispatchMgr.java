/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package intraplex.livelook;

import java.awt.Color;
import java.io.IOException;
import java.net.BindException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import intraplex.livelook.IPLinkNetworkTool;

/**
 *
 * @author jschreiv
 */
public class JDispatchMgr implements Runnable {

    public static JDispatchMgr mgr;
    DatagramSocket  rxSocket;
    static String ip = "192.168.16.100";
    static String index = "1";
    static int port = 50000;
    TreeMap<Long,LogMapEntry> map;
    ArrayList<NetworkLogDataPoint> queuedPoints;
    NewStreamAvailableListener listener;
    boolean bindfailed;
    boolean lite;
    public JDispatchMgr(boolean lite)
    {
    	this.lite = lite;
        bindfailed = false;
        rxSocket = null;
        map = new TreeMap<>();
        new Thread(this, "jdispatch").start();
        queuedPoints = new ArrayList<>();
        mgr = this;
    }
    
    public void disconnect(Long key) {
        if (key == null)return;
        LogMapEntry e = map.get(key);
        if (e == null)return;
       // e.closeFiles();
        
        
        map.remove(key);
        sendSetupMessage(e.getReturnAddress(),e.index,0,e.destPort);
    }
    
    public String getConnectionsForSave()
    {
        Collection<LogMapEntry> c = map.values();
        String s = "";
        for (LogMapEntry e : c)
        {
            s += "{"+e.toSaveString()+"}\t";
        }
        return s;
    }
    
    public Collection<LogMapEntry> getStreams()
    {
        return map.values();
    }
    
    
    public void checkForLostConnections()
    {
        Collection<LogMapEntry> c = map.values();
        for (LogMapEntry e : c)
        {
            e.checkForLostConnections();
        }
    }
    
    public void sendSetupHeartbeat()
    {
        Collection<LogMapEntry> c = map.values();
        for (LogMapEntry e : c)
        {
            e.periodicCheckin();
        }
    }
    
    
    
     public boolean checkIfExisting (String ip, int id)
    {
        boolean retVal = false;
        Collection<LogMapEntry> c = map.values();
        for (LogMapEntry e : c)
        {
            if (ip.equals(e.getReturnAddress()))
            {
                if (id == e.getStreamIndex())
                {
                    // found an entry matching same id and ip
                    return true;
                }
            }
        }
        return false;
    }
    
    public Color checkForStreamStatus()
    {
        Color retVal = Color.gray;
        Color green = new Color(43,148,49);
        Collection<LogMapEntry> c = map.values();
        for (LogMapEntry e : c)
        {
            Color retVal_temp;
            retVal_temp = e.getStreamStatusColor();
            if (retVal_temp == Color.red)
            {
                retVal = Color.red;
            }
            else if (retVal_temp == Color.yellow)
            {
                //check if it is already red
                if (retVal != Color.red)
                {
                    retVal = Color.yellow;
                }
            }
            else if (retVal_temp == Color.green)
            {
                if (retVal!=Color.red && retVal!=Color.yellow)
                {
                    retVal = green;
                }
            }
            
        }
        return retVal;
    }
    
    public void loadConnections(String s)
    {
        String[] entries = s.split("\t");
        for (int i = 0; i < entries.length; i++)
        {
            String e = entries[i].substring(1,entries[i].length()-1);
            LogMapEntry newEntry = LogMapEntry.createFromString(e);
            
            byte[] byt = newEntry.address.getAddress();
            int addr = byt[0] << 24 | byt[1] << 16 | byt[2] << 8 | byt[3];
            long key = addr + ((long)newEntry.dstPort << 32);
            if(!map.containsKey(key))
            {
                try
                {
                    newEntry.logEnties = new NetworkLogEntryArray(600,1,key);

                    map.put(key, newEntry);
                    
                    if (listener != null)
                        listener.newStreamAvailable(key,newEntry.streamName);
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            }
            sendSetupMessage(newEntry.getReturnAddress(),newEntry.index,1,newEntry.destPort);
        }
    }
    
    public void cleanUp() {
        Collection<Long> c = map.navigableKeySet();
        for (Iterator it = c.iterator(); it.hasNext();) {
            Long l = (Long) it.next();
            LogMapEntry e = map.get(l);
            if (e == null || e.streamName == null)
            {
                map.remove(l);
            }
        }
    }
    
    
   
    public void setNewStreamListener(NewStreamAvailableListener l)
    {
        listener = l;
    }
    
    public int bindState()
    {
        if (bindfailed) return 1;
        if (rxSocket == null) return 0;
        return 2;
    }
    
    
    public static void sendDispatchMsg(String host, int type, int xid, int app, int msgid, byte[] data, int dport)
    {
        try
        {
        int[] header = new int[5];
        int headersize = header.length*4;
        byte[] message = new byte[data.length+headersize];
        
        header[0] = type;
        header[1] = xid;
        header[2] = app;
        header[3] = msgid;
        header[4] = data.length;
        
        for (int i = 0; i < header.length; i++)
        {
            message[i*4  ] = (byte) ((header[i] >> 24 ) & 0xFF);
            message[i*4+1] = (byte) ((header[i] >> 16 ) & 0xFF);
            message[i*4+2] = (byte) ((header[i] >> 8  ) & 0xFF);
            message[i*4+3] = (byte) ((header[i]       ) & 0xFF);
        }
        
        for (int i = 0; i < data.length; i++)
        {
            message[i+headersize] = data[i];
        }
        InetAddress address = InetAddress.getByName(host);
        
        DatagramPacket packet = new DatagramPacket(message, message.length,
          address, dport);
          DatagramSocket socket;
          if (mgr == null || mgr.rxSocket == null)
         {
         socket = new DatagramSocket();
         socket.send(packet);
         socket.close();
         }
          else 
          {
          socket = mgr.rxSocket;
          socket.send(packet);
          }
        
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public static void sendSetupMessage(String ip, int ssnindex, int port, int dport)
    {
        byte[] data = new byte[8];      
        
        data[0] = (byte) ((ssnindex >> 24     ) & 0xFF);
        data[1] = (byte) ((ssnindex >> 16 ) & 0xFF);
        data[2] = (byte) ((ssnindex >> 8) & 0xFF);
        data[3] = (byte) ((ssnindex     ) & 0xFF);
        data[4] = (byte) ((port >> 24     ) & 0xFF);
        data[5] = (byte) ((port >> 16 ) & 0xFF);
        data[6] = (byte) ((port >> 8) & 0xFF);
        data[7] = (byte) ((port     ) & 0xFF);
        //System.out.println("Sending Message to "+ip+":"+dport);
        sendDispatchMsg(ip, 2, 0, 11, 143, data, dport);
        
    }

    @Override
    public void run() {
        try
        {
               int p = port;
                byte[] b = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(b, b.length);
                rxSocket = new DatagramSocket(p);
                rxSocket.setSoTimeout(500);
                int i = 0;
                int j = 0;
                try
                {
                    while (true)
                    {
                        try
                        {
                            if (port != p)
                            {
                                rxSocket.close();
                                p = port;
                                rxSocket = new DatagramSocket(p);
                            }
                            
                            rxSocket.receive(receivePacket);
                            sortPacket(b,receivePacket);
                        }
                        catch (SocketTimeoutException timeout)
                        {
                        }
                        i ++;
                        if (i > 30)
                        {
                            checkForLostConnections();
                            Color tabColor = checkForStreamStatus();
                            IPLinkNetworkTool.updateConnectedStreamstabColor(tabColor);
                            IPLinkNetworkTool_Lite.updateConnectedStreamstabColor(tabColor);
                            i = 0;
                        }
                        
                        j ++;
                        if (j > 30)
                        {
                            //sendSetupHeartbeat();
                            j = 0;
                        }
                    }
                }
                catch (IOException ex) 
                {
                    Logger.getLogger(JDispatchMgr.class.getName()).log(Level.SEVERE, null, ex);
                }
        }
        catch (BindException e)
        {
            bindfailed = true;
        } 
        catch (SocketException ex) 
        {
            bindfailed = true;
        } 
    }
    
    public static int setupPacket = 56;
    public static int setupMsgID = 148;
    public static int dataPacket = 268;
    public static int liteMsgId = 156;
    public static int msgId = 155;
   
    public void sortPacket(byte[] b, DatagramPacket receivePacket)
    {
        LogEntry nextEntry = LogEntry.createLogEntry(b, receivePacket.getLength());
        //System.out.println("\n Got a packet.");
        byte[] byt = receivePacket.getAddress().getAddress();
        int addr = byt[0] << 24 | byt[1] << 16 | byt[2] << 8 | byt[3];
        if (receivePacket.getLength() == setupPacket)
        {
            if (nextEntry.vars[3] == setupMsgID)
            {
                //IF IPLink begins using the same send port and receive this code should be changed
                //LogMapEntry lme = new LogMapEntry(receivePacket.getAddress(),receivePacket.getPort());
                LogMapEntry lme = new LogMapEntry(receivePacket.getAddress(),LogMapEntry.next_DPort);
                //System.out.println("recvd packet port" + receivePacket.getPort());
                lme.index = nextEntry.vars[5];
                if (nextEntry.vars[6] == 1)
                {
                    lme.srcIpAddress = nextEntry.vars[7];
                    lme.dstIpAddress = nextEntry.vars[9];
                    lme.srcPort = nextEntry.vars[8];
                    lme.dstPort = nextEntry.vars[10];
                    
                }
                else
                {
                    lme.srcIpAddress = nextEntry.vars[9];
                    lme.dstIpAddress = nextEntry.vars[7];
                    lme.srcPort = nextEntry.vars[10];
                    lme.dstPort = nextEntry.vars[8];
                }
                
                lme.pktInterval = nextEntry.vars[11];
                lme.codec = nextEntry.vars[12];
                lme.SR = nextEntry.vars[13];
                long key = addr + ((long)lme.dstPort << 32);
                if(!map.containsKey(key))
                {
                    try
                    {
                        lme.logEnties = new NetworkLogEntryArray(600,1,key);
                        
                        map.put(key, lme);
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        }
        else
        {   
            long key = addr + ((long)((nextEntry.vars[7]) & 0xFFFF) << 32);
            LogMapEntry lme2 = map.get(key);
            if(lme2 != null && lookForMsgId(nextEntry.vars[3]))
            {
                //Look up successful
                IplinkNetworkLogEntry inle = lme2.logEnties.add(nextEntry, lme2.pktInterval);
                
                
                
                if (lme2.streamName == null)
                {
                    //Ok We are actually getting data from this stream lets extract the stream name
                    lme2.streamName = extractStreamName(nextEntry);
                    lme2.streamName = lme2.streamName.replace('\\', ' ');
                    lme2.streamName = lme2.streamName.replace('/', ' ');
                    lme2.streamName = lme2.streamName.replace('-', ' ');
                    lme2.streamName = lme2.streamName.replace('*', ' ');
                    lme2.streamName = lme2.streamName.replace('?', ' ');
                    lme2.streamName = lme2.streamName.replace('|', ' ');
                    lme2.streamName = lme2.streamName.replace('<', ' ');
                    lme2.streamName = lme2.streamName.replace('>', ' ');
                    lme2.streamName = lme2.streamName.replace('\"', ' ');
                    if (listener != null)
                    {
                        //System.out.println("New Stream available; updating ");
                        listener.newStreamAvailable(key,lme2.streamName);
                        //getCurrentLoggingStatus();
                        //getEnableLogging();
                        
                    }
                    
                }
               // System.out.println(" Stream Name "+lme2.streamName+" packet loss pct " + inle.getPktsLostPct() );
                try
                {
                    lme2.writeToLog(inle);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
                   
        }
    }
    
    public boolean lookForMsgId (int num) {
    	
    	String numString = Integer.toHexString(num);
    	
    	return (numString.equals(Integer.toHexString((lite)?liteMsgId:msgId)))?true:false;
    }
    
    public String extractStreamName(LogEntry nextEntry)
    {
        int start = 41;
        int end = 57;
        String name = "";
        for (int i = start; i < end; i++)
        {
            for (int j = 0; j < 4; j++)
            {
                int v = ((nextEntry.vars[i] >> (24-(j*8))) & 0xFF);
                if (v == 0)return name;
                name += (char)v;
            }
        }
        if (name.length() > 0)return name;
        return "Unknown Stream";
    }
    

    void changeWindowSize(int windowSize) {
        for(Map.Entry<Long,LogMapEntry> entry : map.entrySet()) 
            {
                LogMapEntry value = entry.getValue();
                value.logEnties.changeWindowSize(windowSize);
            }
        
    }
    
    public String getStreamName(long key)
    {
        String s = "";
        LogMapEntry e = map.get(key);
        if (e != null)
        {
            s = e.streamName;
            if (s == null)
                s = "";
        }
        
        return s;
    }
    
    public void setEnableLogging(long key, boolean value)
    {
        LogMapEntry e = map.get(key);
        if (e != null)
        {
            e.enableLogging = value;
            //System.out.println("log map entry found and updated to :" + value);
        }
        else
        {
            //System.out.println("log map entry not found");
        }
    }
    

    void reset() {
        for(Map.Entry<Long,LogMapEntry> entry : map.entrySet()) 
            {
                LogMapEntry value = entry.getValue();
                value.logEnties.reset();
            }
    }

    
    NetworkLogDataPoint getNextPoint() {
        
        if (queuedPoints.isEmpty())
        {
            for(Map.Entry<Long,LogMapEntry> entry : map.entrySet()) 
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
 
    public int pointsInQueue() {
        
        int points  = 0;
        for(Map.Entry<Long,LogMapEntry> entry : map.entrySet()) 
            {
                LogMapEntry value = entry.getValue();
                points += value.logEnties.pointsInQueue();
            }
        return points;
        
    }
}
