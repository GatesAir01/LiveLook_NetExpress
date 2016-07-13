/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package intraplex.alarms;

import static intraplex.alarms.CSVFormatter.format;
import intraplex.livelook.LogMapEntry;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;


/**
 *
 * @author Schre_000
 */
public class AlarmManager{
    
    
    public static final String settingsfile = "settings.dat";
    static AlarmManager alarmManager;
    
    SMTPMailer mailer;
    boolean sendEmailAlarms;
    Logger logger; 
    Level emailLevel;
    
    private AlarmManager()
    {
        mailer = new SMTPMailer();
        sendEmailAlarms = false;
        logger = Logger.getLogger("AlarmManager");  
        try {  
            FileHandler fh = new FileHandler("alarms.log");  
            logger.addHandler(fh);
            fh.setFormatter(new CSVFormatter());  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        emailLevel = Level.INFO;
    }

    public SMTPMailer getMailer() {
        return mailer;
    }

    public void setMailer(SMTPMailer mailer) {
        this.mailer = mailer;
    }

    public boolean isSendEmailAlarms() {
        return sendEmailAlarms;
    }

    public void setSendEmailAlarms(boolean sendEmailAlarms) {
        this.sendEmailAlarms = sendEmailAlarms;
    }

    public Level getEmailLevel() {
        return emailLevel;
    }

    public void setEmailLevel(Level emailLevel) {
        this.emailLevel = emailLevel;
    }
    
    
    public static AlarmManager getAlarmManager()
    {
        if (alarmManager == null)
        {
            alarmManager = new AlarmManager();
            alarmManager.loadAlarmManager();
        }
        return alarmManager;
    }
    
    public void generateAlarmLocal(Level level, String subject, String message, boolean allowEmail)
    {
        //logger.log(level, message);
        if (allowEmail && sendEmailAlarms && level.intValue() >= emailLevel.intValue())
        {
            MailSend.send(mailer, subject, message);
        }
    }
    
    public static void generateAlarm(Level level, String subject, String message, boolean allowEmail)
    {
        getAlarmManager().generateAlarmLocal(level,subject,message, allowEmail);
    }

  /*
    public static void generateAlarm(Level level, String message)
    {
        getAlarmManager().generateAlarmLocal(level,message, true);
    }
    */
  
  public static void generateAlarm(Level level, LogMapEntry e, String message)
    {
        String subject =  "LiveLook Alarm: "+" Stream: "+e.streamName+";" + " State: "+message;
        StringBuilder buf = new StringBuilder();
        Date d = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        buf.append(dateFormat.format(d));
        // buf.append(format.format(d));
        String msg = "System IP Address: " +e.getReturnAddress() + "\nStream Name:" +e.streamName+"\nCurrent State: "+message+" \nTimestamp: "+buf.toString();
        msg = msg + "\n\n\nPlease note: this is an auto generated e-mail.";
        e.writeToEventLog(e.streamName+", "+message);
        
        getAlarmManager().generateAlarmLocal(level,subject,msg, e.enableEmail);
    }
   
  public static boolean save()
  {
      return getAlarmManager().saveAlarmManager();
  }
  
  public boolean saveAlarmManager()
  {
      String savefile = settingsfile;
      String s = "";
      s+=sendEmailAlarms+"\n";
      s+=emailLevel.getName()+"\n";
      s+=mailer.getAlarmFromPassword()+"\n";
      s+=mailer.getAlarmFromUsername()+"\n";
      s+=mailer.getAlarmToUsername()+"\n";
      s+=mailer.getPort()+"\n";
      s+=mailer.getServerAddress()+"\n";
      return SaveFileEncrypted.saveEncyptedString(savefile, s);
  }
  
  public boolean loadAlarmManager()
  {
      String savefile = settingsfile;
      String data = SaveFileEncrypted.loadEncyptedString(savefile);
      if (data!=null)
      {
          try
          {
          StringTokenizer st = new StringTokenizer(data,"\n");
          sendEmailAlarms = st.nextToken().equals("true");
          emailLevel = Level.parse(st.nextToken());
          mailer.setAlarmFromPassword(st.nextToken());
          mailer.setAlarmFromUsername(st.nextToken());
          mailer.setAlarmToUsername(st.nextToken());
          mailer.setPort(st.nextToken());
          mailer.setServerAddress(st.nextToken());
          return true;
          }
          catch (Exception e)
          {
              return false;
          }
      }
      return false;
  }
    
}
