/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package intraplex.livelook;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author jschreiv
 */
public class LoadedLogFile extends ArrayList<IplinkNetworkLogEntry>{
    
    boolean streamInfoSet;
    String streamName;
    int index;
    String srcIpAddress;
    String dstIpAddress;
    int srcPort;
    int dstPort;
    int pktInterval;
    int codec;
    int SR;
    
    public LoadedLogFile()
    {
        streamInfoSet = false;
    }
    
    public void beginLoad()
    {
        streamInfoSet = false;
        this.clear();
    }
    
    public void endLoad()
    {
        Collections.sort(this);
    }

    public boolean setStreamInfo(String[] seperated) {
       
        try
        {
            if (!streamInfoSet)
            {
                streamName = seperated[0].trim();
                index = Integer.parseInt(seperated[1].trim());
                srcIpAddress = seperated[2].trim();
                dstIpAddress = seperated[3].trim();
                srcPort = Integer.parseInt(seperated[4].trim());
                dstPort = Integer.parseInt(seperated[5].trim());
                pktInterval = Integer.parseInt(seperated[6].trim());
                codec = Integer.parseInt(seperated[7].trim());
                SR = Integer.parseInt(seperated[8].trim());
                return true;
            }
            else
            {
                return streamName.equals(seperated[0]);
            }
        }
        catch (Exception e)
        {
            
        }
        return true;
    }

    public int getPktInterval() {
        if (streamInfoSet)
            return pktInterval;
        return 0;
    }

    public int getCodec() {
        if (streamInfoSet)
            return codec;
        return 0;
    }

    public int getSR() {
        if (streamInfoSet)
            return SR;
        return 0;
    }
    
    
    
}
