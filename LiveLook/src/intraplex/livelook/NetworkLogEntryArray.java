/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package intraplex.livelook;

import java.util.ArrayList;

/**
 *
 * @author jschreiv
 */
public class NetworkLogEntryArray {
    
    IplinkNetworkLogEntry[] entries;
    int index;
    int size;
    int maxSize;
    Mutex mutex;
    int windowSize;
    ArrayList<NetworkLogDataPoint> dataPointQueue;
    long streamID;
    
    public NetworkLogEntryArray(int size, int windowSize, long id)
    {
        entries = new IplinkNetworkLogEntry[size];
        maxSize = size;
        index = 0;
        this.size = 0;
        mutex = new Mutex();
        dataPointQueue = new ArrayList<>();
        this.windowSize = windowSize;
        streamID = id;
    }
    
    public long getStreamID()
    {
        return streamID;
    }
    
    public IplinkNetworkLogEntry add(LogEntry e, int pktInterval)
    {
        IplinkNetworkLogEntry entry = new IplinkNetworkLogEntry(e.vars,pktInterval );
        add(entry);
        return entry;
    }
    
    public IplinkNetworkLogEntry add(Stream stream)
    {
        IplinkNetworkLogEntry entry = new IplinkNetworkLogEntry(stream);
        add(entry);
        return entry;
    }
    
    public void clear()
    {
        mutex.lock();
        dataPointQueue.clear();
        /*
        for (int i = 0; i < maxSize; i++)
        {
            entries[i] = null;
        }
        size = 0;
        index = 0;
                */
        mutex.unlock();
    }
    
    public void reset()
    {
        mutex.lock();
        dataPointQueue.clear();
        
        for (int i = 0; i < maxSize; i++)
        {
            entries[i] = null;
        }
        size = 0;
        index = 0;
                
        mutex.unlock();
    }
    
    public void add(IplinkNetworkLogEntry e)
    {
        mutex.lock();
        if (size < maxSize)size++;
        if (index >= maxSize)index = 0;
        entries[index++] = e;
        if (windowSize > 0 && size-1 > windowSize)
        {
            int index1 = (index-1);
            int index2 = ((index-1) - windowSize);
            if (index1 < 0) index1+=maxSize;
            if (index2 < 0) index2+=maxSize;
            dataPointQueue.add(new NetworkLogDataPoint(entries[index2],entries[index1],streamID));
        }
        mutex.unlock();
    }
    
    public void changeWindowSize(int windowSize)
    {   
        mutex.lock();
        this.windowSize = windowSize;
        dataPointQueue.clear();
        for (int i = 1; i < (size-(1+windowSize)); i++)
        {
            int index1 = (index-i);
            int index2 = ((index-i) - windowSize);
            if (index1 < 0) index1+=maxSize;
            if (index2 < 0) index2+=maxSize;
            dataPointQueue.add(0, new NetworkLogDataPoint(entries[index2],entries[index1],streamID));
        }
        mutex.unlock();
    }
    
    public NetworkLogDataPoint getNextPoint()
    {
        //If the Struct is in use we will just get the data later no big deal
        if (!mutex.trylock())
        {
            return null;
        }
        NetworkLogDataPoint p = null;
        if (dataPointQueue.size()>0)
        {
            p = dataPointQueue.remove(0);
        }
       mutex.unlock();
       return p;
    }
    
    public int pointsInQueue()
    {
       return dataPointQueue.size();  
    }
    
}
