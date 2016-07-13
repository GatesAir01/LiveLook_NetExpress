/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package intraplex.livelook;

/**
 *
 * @author jschreiv
 */
public class NetworkLogDataPoint{
    
    public static final int LOSS_RATE = 0;
    public static final int BURST_DENSITY = LOSS_RATE+1;
    public static final int BURST_SIZE = BURST_DENSITY+1;
    public static final int GAP_DENSITY = BURST_SIZE+1;
    public static final int ISOLATED_LOSSES = GAP_DENSITY+1;
    public static final int BURST_LOSSES = ISOLATED_LOSSES+1;
    public static final int BURST_RATIO = BURST_LOSSES+1;
    //public static final int LOSS_RATE_2 = 7;
    public static final int LOSS_RATE_ADJ = BURST_RATIO+1;
    public static final int JBUFF_RESETS = LOSS_RATE_ADJ+1;
    public static final int PACKETS_RECOVERED = JBUFF_RESETS+1;
    public static final int PACKETS_LATE = PACKETS_RECOVERED+1;
    public static final int PACKETS_EARLY = PACKETS_LATE+1;
    public static final int GROUP_LOSSES = PACKETS_EARLY+1;
    public static final int PACKET_LOSSES = GROUP_LOSSES+1;
    //public static final int PACKET_LOSSES_ALT = PACKET_LOSSES+1;
    public static final int NET_LOSS = PACKET_LOSSES+1;
    public static final int BUFFER_DELAY = NET_LOSS+1;
    public static final int AVG_JITTER = BUFFER_DELAY+1;
   // public static final int MAX_JITTER =  AVG_JITTER+1;
    public static final int GRP_DELAY  = AVG_JITTER+1;
    
    public static final int EXP_QUEUE  = GRP_DELAY+1;
    public static final int RECO_QUEUE  = EXP_QUEUE+1;
    
    
    public static final int MIN_LOSS_RATE = RECO_QUEUE+1;
    
    public static final int MAX_LOSS_RATE = MIN_LOSS_RATE+1;
    public static final int MAX_LOSS_RATE_AFTER_CORRECTION = MAX_LOSS_RATE+1;
    public static final int DATA_GAP = MAX_LOSS_RATE_AFTER_CORRECTION+1;
    public static final int RX_JITTER_IN_MSEC = DATA_GAP+1;
    public static int NUM_TRACES_LIVE = RECO_QUEUE+1;
    public static int NUM_TRACES = DATA_GAP+1;
    
    public static String[] possibilities = { "Loss Rate", "Burst Density", "Burst Size", 
            "Gap Density", "Isolated Losses", "Burst Losses", "Burst Ratio", 
            "Loss Rate After Correction",
            "Jitter Buffer Resets", "Packets Recovered", "Packets Late",
            "Packets Early", "Group Losses", "Packets Lost", "Net Loss", "Buffer Delay", "Average Jitter","Group Delay","Expected Buffer Delay","Recommended Buffer Delay","Min Loss Rate", "Max Loss Rate", "Max Loss Rate after Correction", "Data Gaps","Jitter"};
    
    
               
    protected long timestamp;
    protected int packetNumber;
    protected double averageDelay;
    protected int c11;	 /* packet received*/
    protected int c13;	 /* packet lost (start of burst) */	
    protected int c14;	 /* isolated packet lost */
    
    //State 2 - burst - no loss
    protected int c22;	 /* packet received within burst */
    protected int c23;	 /* packet lost within burst*/
    
    //State 3 - burst - packet loss
	/*Uint32	c31;	  packet received (end of burst) */
	/*Uint32	c32;	  packet received within burst */
    protected int c33;	 /* packet lost */
    
    //State 4 - gap - packet loss
    //protected int c41;	 /* packet received */	 
    
    
    //Other Vars
    protected int pkt;	 /* packets received since last lost */	 
    protected int lost;	 /* packets lost within the current burst */	 
    	
    protected int lost_count;	 /* total number of packets lost */	 
    protected int pkt_count;	 /* total number of packets lost */
    
    
    protected int c_total;	 /* total number of packets lost */
    protected double burstDensity;	 
    protected double gapDensity;	 
    protected double averageGap;	 
    protected double averageBurst;	 
    protected double lossRate;	 
    protected double lossRate2;	 
    protected double adjustedLossRate;	 
    protected int isolatedLosses;
    protected int busrtLosses;	
    
    
    
    
    //protected int pktSent;								/*Tx stream. Number of packets sent*/
    //protected int bytesSent;								/*Tx stream. Number of bytes sent*/
    protected int pktsRcvd;								/*Tx stream. Number of bytes sent*/
    protected int pktsRcvdPer;								/*Rx stream. Number of packets received*/
    protected int bytesRcvd;								/*Rx stream. Number of bytes received*/
    protected int pktsLost;	 /*Lost packets*/							/*Rx stream. Number of bytes received*/
    protected int pktsLostLocal;	
    protected float pktsLostPct;	 /*Lost packets*/							/*Rx stream. Number of bytes received*/
    protected float pktsLostLocalPct;     
    protected float pktsLostPctCum;	 /*Lost packets*/							/*Rx stream. Number of bytes received*/
    protected float pktsLostLocalPctCum;                                                            
    //protected float burstDensity;						    /*Average Burst Density in packets since last reset*/	
    //protected float gapDensity;                             /*Average Gap Density in packets since last reset*/	
    //protected int gapLength;				      /*Average Gap Length in packets since last reset*/		
    //protected int burstLength;			              /*Average Burst Length in packets since last reset*/	
    protected int pktsRecvd;								/*Number of Rx packets recovered by FEC*/
    protected int pktsLate;								/*Rx packets discarded - Late*/
    protected int pktsEarly;								/*Rx packets discarded - Early*/
    protected int currQLen;								/*Current Queue Length*/   
    protected int currQLenMsec;
    protected int rxJitterInMsec;							/*Product 2 only*/
    protected int numjBufResets;							/*Number of Jitter buffer reset*/
    protected int recoBufferDelay;
    protected float AvgJitter;
    protected float MaxJitter;
    protected float GrpDelay;
    protected int ExpectedQ;
  

    long streamId;
    int interval;
    boolean invalidPacket;
    
    public static int ppsInterval = 1;
    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getPacketNumber() {
        return packetNumber;
    }

    public void setPacketNumber(int packetNumber) {
        this.packetNumber = packetNumber;
    }

    public double getAverageDelay() {
        return averageDelay;
    }

    public void setAverageDelay(double averageDelay) {
        this.averageDelay = averageDelay;
    }

    public int getC11() {
        return c11;
    }

    public void setC11(int c11) {
        this.c11 = c11;
    }

    public int getC13() {
        return c13;
    }

    public void setC13(int c13) {
        this.c13 = c13;
    }

    public int getC14() {
        return c14;
    }

    public void setC14(int c14) {
        this.c14 = c14;
    }

    public int getC22() {
        return c22;
    }

    public void setC22(int c22) {
        this.c22 = c22;
    }

    public int getC23() {
        return c23;
    }

    public void setC23(int c23) {
        this.c23 = c23;
    }

    public int getC33() {
        return c33;
    }

    public void setC33(int c33) {
        this.c33 = c33;
    }

    public int getPkt() {
        return pkt;
    }

    public void setPkt(int pkt) {
        this.pkt = pkt;
    }

    public int getLost() {
        return lost;
    }

    public void setLost(int lost) {
        this.lost = lost;
    }

    public int getLost_count() {
        return lost_count;
    }

    public void setLost_count(int lost_count) {
        this.lost_count = lost_count;
    }

    public int getPkt_count() {
        return pkt_count;
    }

    public void setPkt_count(int pkt_count) {
        this.pkt_count = pkt_count;
    }

    public int getC_total() {
        return c_total;
    }

    public void setC_total(int c_total) {
        this.c_total = c_total;
    }

    public double getBurstDensity() {
        return burstDensity;
    }

    public void setBurstDensity(double burstDensity) {
        this.burstDensity = burstDensity;
    }

    public double getGapDensity() {
        return gapDensity;
    }

    public void setGapDensity(double gapDensity) {
        this.gapDensity = gapDensity;
    }


    public double getAverageGap() {
        return averageGap;
    }

    public void setAverageGap(double averageGap) {
        this.averageGap = averageGap;
    }

    public double getAverageBurst() {
        return averageBurst;
    }

    public void setAverageBurst(double averageBurst) {
        this.averageBurst = averageBurst;
    }

    public double getLossRate() {
        return lossRate;
    }

    public void setLossRate(double lossRate) {
        this.lossRate = lossRate;
    }

    public int getIsolatedLosses() {
        return isolatedLosses;
    }

    public void setIsolatedLosses(int isolatedLosses) {
        this.isolatedLosses = isolatedLosses;
    }

    public int getBusrtLosses() {
        return busrtLosses;
    }

    public double getBurstRatio() {
        if (busrtLosses > 0)
            return (((double)busrtLosses)/(busrtLosses+isolatedLosses));
        return 0;
    }
    public void setBusrtLosses(int busrtLosses) {
        this.busrtLosses = busrtLosses;
    }

    public double getLossRate2() {
        return lossRate2;
    }

    public void setLossRate2(double lossRate2) {
        this.lossRate2 = lossRate2;
    }

    public double getAdjustedLossRate() {
        return adjustedLossRate;
    }

    public void setAdjustedLossRate(double adjustedLossRate) {
        this.adjustedLossRate = adjustedLossRate;
    }

    public int getPktsLost() {
        return pktsLost;
    }

    public void setPktsLost(int pktsLost) {
        this.pktsLost = pktsLost;
    }

    public int getPktsRecvd() {
        return pktsRecvd;
    }

    public void setPktsRecvd(int pktsRecvd) {
        this.pktsRecvd = pktsRecvd;
    }

    public int getPktsLate() {
        return pktsLate;
    }

    public void setPktsLate(int pktsLate) {
        this.pktsLate = pktsLate;
    }

    public int getPktsEarly() {
        return pktsEarly;
    }

    public void setPktsEarly(int pktsEarly) {
        this.pktsEarly = pktsEarly;
    }

    public int getNumjBufResets() {
        return numjBufResets;
    }

    public void setNumjBufResets(int numjBufResets) {
        this.numjBufResets = numjBufResets;
    }
    
    public float getAvgJitter() {
        return AvgJitter;
    }

    public void setAvgJitter(float AvgJitter) {
        this.AvgJitter = AvgJitter;
    }
    
    public float getMaxJitter() {
        return AvgJitter;
    }

    public void setMaxJitter(float MaxJitter) {
        this.MaxJitter = MaxJitter;
    }
    
    //GrpDelay
    public float getGrpDelay() {
        return GrpDelay;
    }

    public void setGrpDelay(float GrpDelay) {
        this.GrpDelay = GrpDelay;
    }
    
    
    
    
    
    
    public NetworkLogDataPoint(IplinkNetworkLogEntry e, long id)
    {
        timestamp = e.timestamp;
        packetNumber = e.packetNumber;
        averageDelay = e.averageDelay;
        c11 = e.c11;
        c13 = e.c13;	
        c14 = e.c14;	
        c22 = e.c22;
        c23 = e.c23;
        c33 = e.c33;
        lost_count = e.lost_count;	 
        pkt_count = e.pkt_count;
        
        pktsRcvd = e.pktsRcvd;
        pktsRcvdPer = e.pktsRcvdPer;
        bytesRcvd = e.bytesRcvd;
        pktsLost = e.pktsLost;
        pktsLostLocal = e.pktsLostLocal;
        pktsLostPct = e.pktsLostPct;
        pktsLostLocalPct = e.pktsLostLocalPct;   
        pktsLostPctCum = e.pktsLostPctCum;	
        pktsLostLocalPctCum = e.pktsLostLocalPctCum;                                                            
        pktsRecvd = e.pktsRecvd;
        pktsLate = e.pktsLate;
        pktsEarly = e.pktsEarly;
        currQLen = e.currQLen;
        currQLenMsec = e.currQLenMsec;
        rxJitterInMsec = e.rxJitterInMsec;
        numjBufResets = e.numjBufResets;
        
        
        AvgJitter = e.AvgJitter;
        MaxJitter = e.MaxJitter;
        GrpDelay = e.GrpDelay;
        recoBufferDelay = e.recoBufferDelay;
        ExpectedQ = e.ExpectedQ;
        
        
        c_total = c11+2*c13+c14+c22+2*c23+c33;
        double p32 = ((double)c23)/(c13+c23+c33);
        double p23 = 1;
        if (c22+c23 >= 1)
        {
            p23 = 1-(((double)c22)/(c22+c23));
        }
    
        burstDensity = p23/(p23+p32);
        gapDensity = ((double)c14) / (c11 + c14);

        averageGap = ((double)(c11 + c14 + c13)) / c13;
        averageBurst = ((double)c_total)  / c13 - averageGap;

        lossRate = ((double)lost_count)/pkt_count;
         
        isolatedLosses = c14;
        busrtLosses =c13+c23+c33;
        streamId = id;
        invalidPacket = false;
    }
   
    public NetworkLogDataPoint(IplinkNetworkLogEntry e,IplinkNetworkLogEntry e1, long id)
    {
        timestamp = e.timestamp / 2 + e1.timestamp/2;
        packetNumber = e.packetNumber;
        averageDelay = e.averageDelay/2 + e1.averageDelay/2;
        c11 = e1.c11-e.c11;
        c13 = e1.c13-e.c13;	
        c14 = e1.c14-e.c14;	
        c22 = e1.c22-e.c22;
        c23 = e1.c23-e.c23;
        c33 = e1.c33-e.c33;
        lost_count = e1.lost_count-e.lost_count;	 
        pkt_count = e1.pkt_count-e.pkt_count;
        
        pktsRcvd = e1.pktsRcvd - e.pktsRcvd;
        pktsRcvdPer = e1.pktsRcvdPer - e.pktsRcvdPer;
        bytesRcvd = e1.bytesRcvd - e.bytesRcvd;
        pktsLost = e1.pktsLost - e.pktsLost;
        pktsLostLocal = e1.pktsLostLocal - e.pktsLostLocal;
        pktsLostPct = e1.pktsLostPct/2 + e.pktsLostPct/2;
        pktsLostLocalPct = e1.pktsLostLocalPct/2 + e.pktsLostLocalPct/2;   
        pktsLostPctCum = e1.pktsLostPctCum/2 + e.pktsLostPctCum/2;	
        pktsLostLocalPctCum = e1.pktsLostLocalPctCum/2 + e.pktsLostLocalPctCum/2;                                                            
        pktsRecvd = e1.pktsRecvd - e.pktsRecvd;
        pktsLate = e1.pktsLate - e.pktsLate;
        pktsEarly = e1.pktsEarly - e.pktsEarly;
        currQLen = e1.currQLen/2 + e.currQLen/2;
        currQLenMsec = e1.currQLenMsec/2 + e.currQLenMsec/2;
        rxJitterInMsec = e1.rxJitterInMsec/2+e.rxJitterInMsec/2;
        numjBufResets = e1.numjBufResets - e.numjBufResets;
        AvgJitter = e1.AvgJitter/2 + e.AvgJitter/2;
        MaxJitter = e1.MaxJitter/2 + e.MaxJitter/2;
        GrpDelay = e1.GrpDelay/2 + e.GrpDelay/2;
        recoBufferDelay = e1.recoBufferDelay/2 + e.recoBufferDelay/2;
        ExpectedQ = e1.ExpectedQ/2 + e.ExpectedQ/2;
        
        
        if (pktsRcvd < 0 || pktsLost < 0 || pktsLostLocal < 0 || pktsRecvd < 0 ||
            numjBufResets < 0 || pktsLate < 0 || pktsEarly < 0)
        {
            invalidPacket = true;
            streamId = id;
        }
        else
        {
            if (pktsRcvd == 0 && c33 == 0 && pktsLost > 0)
            {
                c33 = pktsLost;
            }
            invalidPacket = false;
            c_total = c11+2*c13+c14+c22+2*c23+c33;
            double p32 = ((double)c23)/(c13+c23+c33);
            double p23 = 1;
            if (c22+c23 >= 1)
            {
                p23 = 1-(((double)c22)/(c22+c23));
            }

            burstDensity = p23/(p23+p32);
            if (c13+c23+c33 == 0)
            {
                burstDensity = 0;
            }

            gapDensity = ((double)c14) / (c11 + c14);

            averageGap = ((double)(c11 + c14 + c13)) / c13;
            averageBurst = ((double)c_total)  / c13 - averageGap;

            if (c11 + c14 == 0)
            {
                gapDensity = 0;
            }
            if (c13 == 0)
            {
                averageGap = c11 + c14;
                averageBurst = 0;
            }
            lossRate = ((double)lost_count)/pkt_count;

            isolatedLosses = c14;
            busrtLosses =c13+c23+c33;
            lossRate2 = ((double)pktsLostLocal)/(pktsLostLocal+pktsRcvd);
            adjustedLossRate = ((double)(pktsLost - pktsRecvd))/(pktsLostLocal+pktsRcvd);
            streamId = id;

            interval = (int)(e1.timestamp- e.timestamp);
        }
        
    }
    
    public void setValues(IplinkNetworkLogEntry e,IplinkNetworkLogEntry e1, long id)
    {
        timestamp = e.timestamp / 2 + e1.timestamp/2;
        packetNumber = e.packetNumber;
        averageDelay = e.averageDelay/2 + e1.averageDelay/2;
        c11 = e1.c11-e.c11;
        c13 = e1.c13-e.c13;	
        c14 = e1.c14-e.c14;	
        c22 = e1.c22-e.c22;
        c23 = e1.c23-e.c23;
        c33 = e1.c33-e.c33;
        lost_count = e1.lost_count-e.lost_count;	 
        pkt_count = e1.pkt_count-e.pkt_count;
        
        pktsRcvd = e1.pktsRcvd - e.pktsRcvd;
        pktsRcvdPer = e1.pktsRcvdPer - e.pktsRcvdPer;
        bytesRcvd = e1.bytesRcvd - e.bytesRcvd;
        pktsLost = e1.pktsLost - e.pktsLost;
        pktsLostLocal = e1.pktsLostLocal - e.pktsLostLocal;
        pktsLostPct = e1.pktsLostPct/2 + e.pktsLostPct/2;
        pktsLostLocalPct = e1.pktsLostLocalPct/2 + e.pktsLostLocalPct/2;   
        pktsLostPctCum = e1.pktsLostPctCum/2 + e.pktsLostPctCum/2;	
        pktsLostLocalPctCum = e1.pktsLostLocalPctCum/2 + e.pktsLostLocalPctCum/2;                                                            
        pktsRecvd = e1.pktsRecvd - e.pktsRecvd;
        pktsLate = e1.pktsLate - e.pktsLate;
        pktsEarly = e1.pktsEarly - e.pktsEarly;
        currQLen = e1.currQLen/2 + e.currQLen/2;
        currQLenMsec = e1.currQLenMsec/2 + e.currQLenMsec/2;
        rxJitterInMsec = e1.rxJitterInMsec/2+e.rxJitterInMsec/2;
        numjBufResets = e1.numjBufResets - e.numjBufResets;
        AvgJitter = e1.AvgJitter/2 + e.AvgJitter/2;
        MaxJitter = e1.MaxJitter/2 + e.MaxJitter/2;
        GrpDelay = e1.GrpDelay/2 + e.GrpDelay/2;
        recoBufferDelay = e1.recoBufferDelay/2 + e.recoBufferDelay/2;
        ExpectedQ = e1.ExpectedQ/2 + e.ExpectedQ/2;
        //System.out.println("SetValues: ExpectedQ " + ExpectedQ);
        
        if (pktsRcvd < 0 || pktsLost < 0 || pktsLostLocal < 0 || pktsRecvd < 0 ||
            numjBufResets < 0 || pktsLate < 0 || pktsEarly < 0)
        {
            invalidPacket = true;
            streamId = id;
        }
        else
        {
            if (pktsRcvd == 0 && c33 == 0 && pktsLost > 0)
            {
                c33 = pktsLost;
            }
            invalidPacket = false;
            c_total = c11+2*c13+c14+c22+2*c23+c33;
            double p32 = ((double)c23)/(c13+c23+c33);
            double p23 = 1;
            if (c22+c23 >= 1)
            {
                p23 = 1-(((double)c22)/(c22+c23));
            }

            burstDensity = p23/(p23+p32);
            if (c13+c23+c33 == 0)
            {
                burstDensity = 0;
            }

            gapDensity = ((double)c14) / (c11 + c14);

            averageGap = ((double)(c11 + c14 + c13)) / c13;
            averageBurst = ((double)c_total)  / c13 - averageGap;

            if (c11 + c14 == 0)
            {
                gapDensity = 0;
            }
            if (c13 == 0)
            {
                averageGap = c11 + c14;
                averageBurst = 0;
            }
            lossRate = ((double)lost_count)/pkt_count;

            isolatedLosses = c14;
            busrtLosses =c13+c23+c33;
            lossRate2 = ((double)pktsLostLocal)/(pktsLostLocal+pktsRcvd);
            adjustedLossRate = ((double)(pktsLost - pktsRecvd))/(pktsLostLocal+pktsRcvd);
            streamId = id;

            interval = (int)(e1.timestamp- e.timestamp);
        }
        
    }

    public long getStreamId() {
        return streamId;
    }
    
    
    public double getValueByString(String name, long intVal, boolean pps)
    {
        return getValue(lookup(name), intVal, pps);
    }
    
    public double getValueForPlot(int type, long intVal, boolean pps)
    {
        //Only Plot Valid Values
        if (interval < 30000)
            return getValue(type, intVal, pps);
        if (interval > intVal*2) 
            return Double.NaN;
        
        return getValue(type, intVal, pps);
    }
    public double getValue(int type, long intVal, boolean pps)
    {
        if (invalidPacket)
        {
            //System.out.println("Invalid packet");
            return Double.NaN;
        }
        switch (type)
        {
            case LOSS_RATE:
                return getLossRate2()*100;
            case BURST_DENSITY:
                return getBurstDensity()*100;
            case BURST_SIZE:
                return getAverageBurst();
            case GAP_DENSITY:
                return getGapDensity()*100;
            case ISOLATED_LOSSES:
                if (!pps)
                    return getIsolatedLosses();
                else
                    return ppsInterval*getIsolatedLosses()/(intVal/1000.0);
            case BURST_LOSSES:
                if (!pps)
                    return getBusrtLosses();
                else
                    return ppsInterval*getBusrtLosses()/(intVal/1000.0);
            case BURST_RATIO:
                return getBurstRatio()*100;
            //case LOSS_RATE_2:
            //    return getLossRate2()*100;
            case LOSS_RATE_ADJ:
                return getAdjustedLossRate()*100;
            case JBUFF_RESETS:
                return this.getNumjBufResets();
            case PACKETS_RECOVERED:
                if (!pps)
                    return this.getPktsRecvd();
                else
                    return ppsInterval*this.getPktsRecvd()/(intVal/1000.0);
            case PACKETS_LATE:
                if (!pps)
                    return this.getPktsLate();
                else
                    return ppsInterval*this.getPktsLate()/(intVal/1000.0);
            case PACKETS_EARLY:
                if (!pps)
                    return this.getPktsEarly();
                else
                    return ppsInterval*this.getPktsEarly()/(intVal/1000.0);
            case GROUP_LOSSES:
                if (!pps)
                    return this.getPktsLost();
                else
                    return ppsInterval*this.getPktsLost()/(intVal/1000.0);
            case PACKET_LOSSES:
                if (!pps)
                    return this.pktsLostLocal;
                else
                    return ppsInterval*this.pktsLostLocal/(intVal/1000.0);
            //case PACKET_LOSSES_ALT:
            //    return getIsolatedLosses()+getBusrtLosses();
            case NET_LOSS:
                if (!pps)
                    return this.getPktsLost()-this.getPktsRecvd();
                else
                    return ppsInterval*(this.getPktsLost()-this.getPktsRecvd())/(intVal/1000.0);
            case BUFFER_DELAY:
                return this.currQLenMsec;
            case RX_JITTER_IN_MSEC:
                return this.rxJitterInMsec;
                
            case AVG_JITTER:
                return this.AvgJitter;
           // case MAX_JITTER:
                //return this.MaxJitter;
            case GRP_DELAY:
                return this.GrpDelay;
            case EXP_QUEUE:
                return this.ExpectedQ;
                
            case RECO_QUEUE:
            {
                //System.out.println("Recommended Q delay is "+this.recoBufferDelay);
                
                return this.recoBufferDelay;
            }
            
                
        }
        return 0;
    }
    
    public static String getTypeTitle(int chartType, boolean pps)
    {
        if (chartType == LOSS_RATE)
        {
            return "Loss Rate (%)";
        }
        else if (chartType == BURST_DENSITY)
        {
            return "Burst Density(%)";
        }
        else if (chartType == BURST_SIZE)
        {
            return "Burst Size";
        }
        else if (chartType == GAP_DENSITY)
        {
            return "Gap Density (%)";
        }
        else if (chartType == ISOLATED_LOSSES)
        {
            if (!pps)
                return "Isolated Losses";
            else
                return "Isolated Losses (pps)";
        }
        else if (chartType == BURST_LOSSES)
        {
            if (!pps)
                return "Burst Losses";
            else
                return "Burst Losses (pps)";
        }
        else if (chartType == BURST_RATIO)
        {
            return "Burst Ratio (%)";
        }
        //else if (chartType == LOSS_RATE_2)
        //{
        //    return "Loss Rate (%)";
        //}
        else if (chartType == LOSS_RATE_ADJ )
        {
            return "Loss Rate After Correction (%)";
        }
        else if (chartType == JBUFF_RESETS )
        {
            return "Buffer Resets";
        }
        else if (chartType == PACKETS_RECOVERED  )
        {
            
            if (!pps)
                return "Packets Recovered";
            else
                return "Packets Recovered (pps)";
        }
        else if (chartType == PACKETS_LATE  )
        {
            if (!pps)
                return "Packets Late";
            else
                return "Packets Late (pps)";
        }
        else if (chartType == PACKETS_EARLY  )
        {
            if (!pps)
                return "Packets Early";
            else
                return "Packets Early (pps)";
        }
        else if (chartType == GROUP_LOSSES  )
        {
            if (!pps)
                return "Group Losses";
            else
                return "Group Losses (pps)";
        }
        else if (chartType == PACKET_LOSSES  )
        {
            if (!pps)
                return "Packets Lost";
            else
                return "Packets Lost (pps)";
        }
        //else if (chartType == PACKET_LOSSES_ALT  )
        //{
        //    return "Packets Lost(Burst+Iso)";
        //}
        else if (chartType == NET_LOSS  )
        {
            if (!pps)
                return "Net Losses";
            else
                return "Net Losses (pps)";
        }
        else if (chartType == BUFFER_DELAY  )
        {
            return "Current Buffer Delay (ms)";
        }
        else if (chartType == RX_JITTER_IN_MSEC  )
        {
            return "Jitter (ms)";
        }
        else if (chartType == MIN_LOSS_RATE  )
        {
            return "Min Loss Rate (%)";
        }
        else if (chartType == MAX_LOSS_RATE  )
        {
            return "Max Loss Rate (%)";
        }
        else if (chartType == MAX_LOSS_RATE_AFTER_CORRECTION  )
        {
            return "Max Loss Rate after Correction (%)";
        }
        else if (chartType == DATA_GAP  )
        {
            return "Data Gap";
        }
        else if (chartType == AVG_JITTER)
        {
            return "Average Jitter (ms)";
        }
        /*else if (chartType == MAX_JITTER)
        {
            return "Maximum Jitter (ms)";
        }*/
        else if (chartType == GRP_DELAY)
        {
            return "Group Delay (ms)";
        }
        else if (chartType == EXP_QUEUE)
        {
            return "Configured Buffer Delay (ms)";
        }
        else if (chartType == RECO_QUEUE)
        {
            return "Recommended Buffer Delay (ms)";
        }
    
    return "";

    }
    
    public static int lookup(String s)
    {
        for (int i = 0; i < NUM_TRACES; i++)
        {
            
            if (possibilities[i].equals(s))
            {
                return i;
            }
        }
        return 0;
    }

    
    
}
