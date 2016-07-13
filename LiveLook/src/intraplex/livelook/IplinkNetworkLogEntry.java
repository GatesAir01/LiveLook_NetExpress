package intraplex.livelook;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jschreiv
 */
public class IplinkNetworkLogEntry implements Comparable{

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
   // protected int c41;	 /* packet received */	 
    
    
    //Other Vars
    protected int pkt;	 /* packets received since last lost */	 
    protected int lost;	 /* packets lost within the current burst */	 
    	
    protected int lost_count;	 /* total number of packets lost */	 
    protected int pkt_count;	 /* total number of packets lost */
    
    protected int pktSent;								/*Tx stream. Number of packets sent*/
    protected int bytesSent;								/*Tx stream. Number of bytes sent*/
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
    //protected int CurrentQ;
    
    
    protected float AvgJitter;
    protected float MaxJitter;
    protected float GrpDelay;
    protected int ExpectedQ;
    protected int recoBufferDelay;
    protected int version;
    
        
    protected int dataState;
    //int pktInterval;
    public IplinkNetworkLogEntry(int[] v, int pktInterval) {
        int i = 0;
        timestamp = System.currentTimeMillis();
        //Skip 4
        version = v[8];
        dataState = v[10];
        i+=12;
        pktSent = v[i++];	
        bytesSent = v[i++];	
        pktsRcvd = v[i++];	
        pktsRcvdPer = v[i++];	
        bytesRcvd = v[i++];	
        pktsLost = v[i++];	
        pktsLostLocal = v[i++];	
        pktsLostPct = Float.intBitsToFloat(v[i++]);	
        pktsLostLocalPct = Float.intBitsToFloat(v[i++]);	
        pktsLostPctCum = Float.intBitsToFloat(v[i++]);	
        pktsLostLocalPctCum = Float.intBitsToFloat(v[i++]);	                                             
        //Skip 4
        i+=4;
        pktsRecvd = v[i++];	 
        pktsLate = v[i++];	 
        pktsEarly = v[i++];	 
        currQLen = v[i++];	  
        currQLenMsec = v[i++];	 
        rxJitterInMsec = v[i++];	 
        numjBufResets = v[i++];	 
        
        //averageDelay = ((double)(v[i++]) * 1000.0) / ((double)(v[i++])/4.0);
        
        i+=25;
	//txSourceType_e		mediaSrcSink;
	//UINT32              wan1_ip;
	//UINT32              wan2_ip;
	//sampleRate_e		SR;
	//BOOL     			isRxStrmCurrSrc;
	//rtpRxStrmRole_e     rxRole;
	//codec_e				codec;
	//char				strName[MAX_NAME_LEN];	  
	//UINT32              peerStrmRateKbs; 
	//UINT32              peerAFStrmRateKbs;
        c11 = v[i++];
        c13 = v[i++];	
        c14 = v[i++];	
        c22 = v[i++];
        c23 = v[i++];
        c33 = v[i++];
        lost_count = v[i++];	 
        pkt_count = v[i++];	
        i++;
        //UINT32				isLiveLookLicON;
        if (version >= 6)
        {
            AvgJitter = Float.intBitsToFloat(v[i++]);
            //System.out.println("The avg jitter is "+AvgJitter);
            MaxJitter = Float.intBitsToFloat(v[i++]);
            //GrpDelay =  Float.intBitsToFloat(v[i++]);
            
            GrpDelay = Float.intBitsToFloat(v[i++]);
            i += 2;
            //int                                             GrpDelayID;  
            // int                                             CurrentQ; 
            ExpectedQ = v[i++] * pktInterval;
           // System.out.println("Expected Buffer delay is " + ExpectedQ);
            i += 2;
            
            
            // int                                             ExpectedQ; 
            //UINT32                                          NumUA;                                          /* NoUA is the ID tag.          This is the Number of Up Adjustments*/
            //UINT32                                          NumDA;
            recoBufferDelay = v[i++];
            
        }
        else
        {
            AvgJitter = 0;
            MaxJitter = 0;
            recoBufferDelay = 0;
            GrpDelay = 0;
            ExpectedQ = 0;
            i+= 9;
        }
        
       
       
    }
    
    public IplinkNetworkLogEntry(IplinkNetworkLogEntry c) {
    
        timestamp = c.timestamp;
        pktSent = c.pktSent;	
        bytesSent = c.bytesSent;	
        pktsRcvd = c.pktsRcvd;	
        pktsRcvdPer = c.pktsRcvdPer;	
        bytesRcvd = c.bytesRcvd;	
        pktsLost = c.pktsLost;	
        pktsLostLocal = c.pktsLostLocal;	
        pktsLostPct = c.pktsLostPct;	
        pktsLostLocalPct = c.pktsLostLocalPct;	
        pktsLostPctCum = c.pktsLostPctCum;	
        pktsLostLocalPctCum = c.pktsLostLocalPctCum;	     
        pktsRecvd = c.pktsRecvd;	 
        pktsLate = c.pktsLate;	 
        pktsEarly = c.pktsEarly;	 
        currQLen = c.currQLen;	  
        currQLenMsec = c.currQLenMsec;	 
        rxJitterInMsec = c.rxJitterInMsec;	 
        numjBufResets = c.numjBufResets;	 
         
        c11 = c.c11;
        c13 = c.c13;	
        c14 = c.c14;	
        c22 = c.c22;
        c23 = c.c23;
        c33 = c.c33;
        lost_count = c.lost_count;	 
        pkt_count = c.pkt_count;	
          AvgJitter = c.AvgJitter;
          MaxJitter = c.MaxJitter;
          GrpDelay = c.GrpDelay;
          recoBufferDelay = c.recoBufferDelay;
          ExpectedQ = c.ExpectedQ;

        
    }
    
    public void adjustOffset(IplinkNetworkLogEntry up, IplinkNetworkLogEntry down)
    {
        pktSent += up.pktSent - down.pktSent;	
        bytesSent += up.bytesSent - down.bytesSent;
        pktsRcvd += up.pktsRcvd - down.pktsRcvd;
        bytesRcvd += up.bytesRcvd - down.bytesRcvd;
        pktsLost += up.pktsLost - down.pktsLost;
        pktsLostLocal += up.pktsLostLocal - down.pktsLostLocal;       
        
        pktsRecvd += up.pktsRecvd - down.pktsRecvd;  	 
        pktsLate += up.pktsLate - down.pktsLate;  	  
        pktsEarly += up.pktsEarly - down.pktsEarly;   
        numjBufResets += up.numjBufResets - down.numjBufResets; 
        
        //AvgJitter += up.AvgJitter - down.AvgJitter;
        //MaxJitter += up.MaxJitter - down.MaxJitter;
        //GrpDelay += up.GrpDelay - down.GrpDelay;
        //recoBufferDelay += up.recoBufferDelay - down.recoBufferDelay;
        //ExpectedQ += up.ExpectedQ - down.ExpectedQ;
       // System.out.println("AdjustOffset: ExpectedQ: " +ExpectedQ);
        
        c11 += up.c11 - down.c11;  
        c13 += up.c13 - down.c13;  
        c14 += up.c14 - down.c14;  
        c22  += up.c22 - down.c22;  
        c23 += up.c23 - down.c23;  
        c33 += up.c33 - down.c33;  
        lost_count += up.lost_count - down.lost_count;  
        pkt_count += up.pkt_count - down.pkt_count;  	
    }
    
    public IplinkNetworkLogEntry(String[] s, boolean skipJitter) {
        int i = 0;
        timestamp = Long.parseLong(s[i++]);
        pktSent= Integer.parseInt(s[i++]);	
        bytesSent= Integer.parseInt(s[i++]);		
        pktsRcvd= Integer.parseInt(s[i++]);	
        pktsRcvdPer= Integer.parseInt(s[i++]);		
        bytesRcvd= Integer.parseInt(s[i++]);		
        pktsLost= Integer.parseInt(s[i++]);		
        pktsLostLocal= Integer.parseInt(s[i++]);		
        pktsLostPct=Float.parseFloat(s[i++]);
        pktsLostLocalPct=Float.parseFloat(s[i++]);
        pktsLostPctCum=Float.parseFloat(s[i++]);
        pktsLostLocalPctCum=Float.parseFloat(s[i++]);       
        pktsRecvd= Integer.parseInt(s[i++]);	 
        pktsLate= Integer.parseInt(s[i++]);	 
        pktsEarly= Integer.parseInt(s[i++]);
        currQLen= Integer.parseInt(s[i++]);
        currQLenMsec= Integer.parseInt(s[i++]);
        rxJitterInMsec= Integer.parseInt(s[i++]);
        numjBufResets= Integer.parseInt(s[i++]); 
        c11= Integer.parseInt(s[i++]); 
        c13= Integer.parseInt(s[i++]); 
        c14= Integer.parseInt(s[i++]); 
        c22= Integer.parseInt(s[i++]); 
        c23= Integer.parseInt(s[i++]); 
        c33= Integer.parseInt(s[i++]); 
        lost_count= Integer.parseInt(s[i++]); 
        pkt_count= Integer.parseInt(s[i++]); 
        if (!skipJitter)
        {
           
            AvgJitter = Float.parseFloat(s[i++]);
            MaxJitter = Float.parseFloat(s[i++]);
            
            recoBufferDelay = Integer.parseInt(s[i++]);
            try{
            GrpDelay = Float.parseFloat(s[i++]);
            ExpectedQ = Integer.parseInt(s[i++]);
            }
            catch (ArrayIndexOutOfBoundsException e)
            {
                GrpDelay = 0;
                ExpectedQ = recoBufferDelay;
            }
           // System.out.println("ExpectedQ is "+ ExpectedQ);
        }
        else
        {
            //skip jitter, pad it with zero's
            AvgJitter = 0;
            MaxJitter = 0;
            GrpDelay = 0;
            recoBufferDelay = currQLenMsec;
            ExpectedQ = currQLenMsec;
            
        }
        
    }   

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

    public int getPktSent() {
        return pktSent;
    }

    public void setPktSent(int pktSent) {
        this.pktSent = pktSent;
    }

    public int getBytesSent() {
        return bytesSent;
    }

    public void setBytesSent(int bytesSent) {
        this.bytesSent = bytesSent;
    }

    public int getPktsRcvd() {
        return pktsRcvd;
    }

    public void setPktsRcvd(int pktsRcvd) {
        this.pktsRcvd = pktsRcvd;
    }

    public int getPktsRcvdPer() {
        return pktsRcvdPer;
    }

    public void setPktsRcvdPer(int pktsRcvdPer) {
        this.pktsRcvdPer = pktsRcvdPer;
    }

    public int getBytesRcvd() {
        return bytesRcvd;
    }

    public void setBytesRcvd(int bytesRcvd) {
        this.bytesRcvd = bytesRcvd;
    }

    public int getPktsLost() {
        return pktsLost;
    }

    public void setPktsLost(int pktsLost) {
        this.pktsLost = pktsLost;
    }

    public int getPktsLostLocal() {
        return pktsLostLocal;
    }

    public void setPktsLostLocal(int pktsLostLocal) {
        this.pktsLostLocal = pktsLostLocal;
    }

    public float getPktsLostPct() {
        return pktsLostPct;
    }
    
    public float getAdjustedLostPct() {
        
        return ((float)(pktsLost - pktsRecvd))/((pktsLostLocal+pktsRcvd));
    }
    

    public void setPktsLostPct(float pktsLostPct) {
        this.pktsLostPct = pktsLostPct;
    }

    public float getPktsLostLocalPct() {
        return pktsLostLocalPct;
    }

    public void setPktsLostLocalPct(float pktsLostLocalPct) {
        this.pktsLostLocalPct = pktsLostLocalPct;
    }

    public float getPktsLostPctCum() {
        return pktsLostPctCum;
    }

    public void setPktsLostPctCum(float pktsLostPctCum) {
        this.pktsLostPctCum = pktsLostPctCum;
    }

    public float getPktsLostLocalPctCum() {
        return pktsLostLocalPctCum;
    }

    public void setPktsLostLocalPctCum(float pktsLostLocalPctCum) {
        this.pktsLostLocalPctCum = pktsLostLocalPctCum;
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

    public int getCurrQLen() {
        return currQLen;
    }

    public void setCurrQLen(int currQLen) {
        this.currQLen = currQLen;
    }

    public int getCurrQLenMsec() {
        return currQLenMsec;
    }

    public void setCurrQLenMsec(int currQLenMsec) {
        this.currQLenMsec = currQLenMsec;
    }

    public int getRxJitterInMsec() {
        return rxJitterInMsec;
    }

    public void setRxJitterInMsec(int rxJitterInMsec) {
        this.rxJitterInMsec = rxJitterInMsec;
    }

    public int getNumjBufResets() {
        return numjBufResets;
    }

    public void setNumjBufResets(int numjBufResets) {
        this.numjBufResets = numjBufResets;
    }
    
    public String getEntryRow()
    {
        String s  = "";
        s+=timestamp+",";
        s+=pktSent+",";	
        s+=bytesSent+",";	
        s+=pktsRcvd+",";	
        s+=pktsRcvdPer+",";	
        s+=bytesRcvd+",";	
        s+=pktsLost+",";	
        s+=pktsLostLocal+",";	
        s+=pktsLostPct+",";	
        s+=pktsLostLocalPct+",";	
        s+=pktsLostPctCum+",";	
        s+=pktsLostLocalPctCum+",";	         
        s+=pktsRecvd+",";	 
        s+=pktsLate+",";	 
        s+=pktsEarly+",";	 
        s+=currQLen+",";	  
        s+=currQLenMsec+",";	 
        s+=rxJitterInMsec+",";	 
        s+=numjBufResets+",";	 
        s+=c11+",";
        s+=c13+",";	
        s+=c14+",";	
        s+=c22+",";
        s+=c23+",";
        s+=c33+",";
        s+=lost_count+",";	 
        s+=pkt_count+",";
        s+=AvgJitter+",";
        s+=MaxJitter+",";
        s+=recoBufferDelay+",";
        s+=GrpDelay+",";
        s+=ExpectedQ;
        
        return s;
    }

    @Override
    public int compareTo(Object t) {
        
        if (t instanceof IplinkNetworkLogEntry)
        {
            long l = timestamp - ((IplinkNetworkLogEntry)t).timestamp;
            int i = (int)l;
            return i;
        }
        return 0;
    }
    
    
    
}
