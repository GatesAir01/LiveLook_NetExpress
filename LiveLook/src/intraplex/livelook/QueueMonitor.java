/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package intraplex.livelook;

import java.io.FileWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import javax.swing.JLabel;

/**
 *
 * @author jschreiv
 */
public class QueueMonitor implements Runnable{


    JDispatchMgr mgr;
    JLabel numPoints;
    
    public QueueMonitor(JDispatchMgr m, JLabel n)
    {
        mgr = m;
        numPoints = n;
        new Thread(this, "queuemonitor").start();
    }
    
    public void run() {
        int heartbeat = 0;
        int queuePoints = 0;
        int loading = 0;
        while(true)
        {
        try {
               heartbeat++;
               Thread.sleep(250);
               int q = mgr.pointsInQueue();
               if (q < 10)
               {
                   loading = 0;
               }
               if (queuePoints < q && q > 20)
               {
                  loading = q;
               }
               queuePoints = q;
               if (loading > 10)
               {
                   numPoints.setText("Loading..."+((loading-queuePoints)*100/loading)+"%");
               }
               else
               {
                    numPoints.setText("");
               }
            } 
            catch (Exception e) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
            }
                
            }
       }
    }
    
}
