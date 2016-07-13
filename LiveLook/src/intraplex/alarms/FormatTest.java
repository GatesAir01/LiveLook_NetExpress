
package intraplex.alarms;

import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 *
 * @author jschreiv
 */


public class FormatTest {
    
    public static void main(String[] args)
    {
        Logger logger = Logger.getLogger("AlarmManager");  
        try {  
            FileHandler fh = new FileHandler("alarms.log");  
            fh.setFormatter(new CSVFormatter());
            logger.addHandler(fh);
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        
        logger.info("This is a test");
         
    }
}

