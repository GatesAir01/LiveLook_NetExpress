/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package intraplex.livelook;

import java.net.InetAddress;
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
    InetAddress address;
    int destPort;
    
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
                address = InetAddress.getByName(seperated[2].trim());
                destPort = Integer.parseInt(seperated[3].trim());
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


    
    
}
