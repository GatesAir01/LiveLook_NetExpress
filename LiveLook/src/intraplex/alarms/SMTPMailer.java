/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package intraplex.alarms;

import com.sun.net.ssl.internal.ssl.Provider;
import java.security.Security;
import java.util.Properties;
import java.util.logging.Level;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Schre_000
 */
public class SMTPMailer extends javax.mail.Authenticator {
    
    String serverAddress;
    String port;
    String alarmFromUsername;
    String alarmFromPassword;
    String alarmToUsername;
    Properties props;
    
    public SMTPMailer()
    {
        alarmFromUsername = "";
        alarmFromPassword = "";
        alarmToUsername = "";
        serverAddress = "";
        port = "465";
        Security.addProvider(new Provider());
        props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.host", "smtp.gmail.com");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.quitwait", "false");
    }
    
    public PasswordAuthentication getPasswordAuthentication() {
       return new PasswordAuthentication(alarmFromUsername, alarmFromPassword);
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
        props.setProperty("mail.smtp.port", port);
        props.setProperty("mail.smtp.socketFactory.port", port);
    }

    public String getAlarmFromUsername() {
        return alarmFromUsername;
    }

    public void setAlarmFromUsername(String alarmFromUsername) {
        this.alarmFromUsername = alarmFromUsername;
    }

    public String getAlarmFromPassword() {
        return alarmFromPassword;
    }

    public void setAlarmFromPassword(String alarmFromPassword) {
        this.alarmFromPassword = alarmFromPassword;
    }

    public String getAlarmToUsername() {
        return alarmToUsername;
    }

    public void setAlarmToUsername(String alarmToUsername) {
        this.alarmToUsername = alarmToUsername;
    }

    public String getServerAddress() {
        return serverAddress;
    }

    public void setServerAddress(String serverAddress) {
        
        props.setProperty("mail.host", serverAddress);
        this.serverAddress = serverAddress;
    }
        
    


   public void send_mail(String subject, String body)
        throws Exception
    {

      Session session = Session.getInstance(props, this);
      MimeMessage message = new MimeMessage( session );
      message.setSender(new InternetAddress(alarmFromUsername));
      message.addRecipient(Message.RecipientType.TO, new InternetAddress(alarmToUsername));
      message.setSubject( subject);
      message.setText( body );
      Transport.send( message );

    }
   
   public boolean sendTestMessage()
   {
       try
       {
           send_mail("Test Message", "Alarms Go Here!");
           return true;
       }
       catch (Exception e)
       {
            e.printStackTrace();
            AlarmManager.generateAlarm(Level.SEVERE, "Live Look Error","Failed to test email message",false);
       }
       return false;
   }
}
