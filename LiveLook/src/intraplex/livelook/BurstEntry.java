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
public class BurstEntry implements Comparable{
   public double burstsize, burstdensity;
   int numlosses;
   int count;
    
    public BurstEntry(double burstsize, double burstdensity)
    {
        this.burstdensity = burstdensity;
        this.burstsize = burstsize;
        numlosses = (int)Math.round(burstsize * burstdensity);
        count = 1;
    }
    
    public BurstEntry(double burstsize, double burstdensity, int numlosses, int count)
    {
        this.burstdensity = burstdensity;
        this.burstsize = burstsize;
        this.numlosses = numlosses;
        this.count = count;
    }
    
    public static BurstEntry createBin(BurstEntry[] entrys, int s, int e)
    {
        double size = 0;
        int losses = 0;
        int count = 0;
        for (int i = s; i < e; i++)
        {
           
            size+=entrys[i].burstsize;
            losses+=entrys[i].numlosses;
            count++;
        }
        if (count == 0 )new BurstEntry(0,0,0,0);
        return new BurstEntry(size/count,losses/size,losses,count);
    }
    

    @Override
    public int compareTo(Object t) {
        
        if (t instanceof BurstEntry)
        {
            double b = ((BurstEntry)t).burstsize;
            if (burstsize > b)return 1;
            if (burstsize < b)return -1;
            return 0;
                    
        }
        return 0;
    }
}
