
package intraplex.livelook;

import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.NumberFormat;

/**
 *
 * @author jschreiv
 */


public class StaticLengthFormat extends DecimalFormat{
    
    int len;
    DecimalFormat df; 
    public StaticLengthFormat(String format, int len)
    {
        df = new DecimalFormat(format);
        this.len = len;
    }
    
   @Override
    public StringBuffer format(double number, StringBuffer toAppendTo,
            FieldPosition pos)
    {
        String s = df.format(number);
        while (s.length() < len)s = " "+s;
        return toAppendTo.append(s);
    }
    
    public static void main(String[] args)
    {
        StaticLengthFormat format = new StaticLengthFormat("#.###", 5);
        double d = 2;
        for (int i = -10; i < 10; i++)
        {
            System.out.println(format.format(d * Math.pow(10, i)));
        }
    }
}
