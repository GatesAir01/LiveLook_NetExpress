/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package intraplex.livelook;

/**
 *
 * @author jschreiv
 */
public class LogEntry {
    
    public int[] vars;
    public long timestamp;
    public LogEntry(long t, int [] v)
    {
        timestamp = t;
        vars = v;
    }
    
    public static LogEntry createLogEntry(byte[] b, int len)
    {
        int [] vars = new int[len/4];
        for (int i = 0; i < vars.length; i++)
        {
            int bIndex = i*4;
            vars[i] = ( (0xFF & b[bIndex]) << 24) | ((0xFF & b[bIndex+1])<< 16) | ((0xFF & b[bIndex+2]) << 8) | ((0xFF & b[bIndex+3]));
        }
        
        return new LogEntry(System.currentTimeMillis(),vars);
    }
    
    public LogEntry diff(LogEntry e)
    {
        int [] newvars = new int[vars.length];
        newvars[0] = vars[0];
        for (int i = 1; i < vars.length; i++)
        {
            newvars[i] = e.vars[i] - vars[i];
        }
        
        return new LogEntry(timestamp, newvars);
    }
        
    public String toString()
    {   
        String s = "" + timestamp;
        for (int i = 0; i < vars.length; i++)
        {
            s += ", "+vars[i];
        }
        return s;
    }
    
}
