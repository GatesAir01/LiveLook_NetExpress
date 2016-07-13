/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package intraplex.alarms;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Schre_000
 */
public class MailSend implements Runnable{
    
    SMTPMailer mailer;
    String subject;
    String body;
    public MailSend(SMTPMailer mailer, String subject, String body)
    {
        this.mailer = mailer;
        this.subject = subject;
        this.body = body;
        
    }
    
    public static void send(SMTPMailer mailer, String subject, String body)
    {
        (new Thread(new MailSend(mailer, subject, body))).start();
    }

    public void run() {
        try {
            mailer.send_mail(subject, body);
        } catch (Exception ex) {
            
            //AlarmManager.generateAlarm(Level.SEVERE, "Failed to send mail message: "+body,false);
            ex.printStackTrace();
        }
    }
    
}
