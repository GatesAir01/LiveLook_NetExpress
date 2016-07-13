
package intraplex.alarms;



import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;
import java.util.logging.SimpleFormatter;

public class CSVFormatter
  extends Formatter
{
    
 static final String lineSep = System.getProperty("line.separator");

 static final SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss");

  public CSVFormatter()
  {
  }


 @Override
  public String format(LogRecord record)
  {
    StringBuilder buf = new StringBuilder();
    Date d = new Date(record.getMillis());
    buf.append(format.format(d));
    buf.append(", ");
    buf.append(formatMessage(record));
    buf.append(lineSep);
    return buf.toString();
  }
}