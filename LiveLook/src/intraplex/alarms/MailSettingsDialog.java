/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package intraplex.alarms;

import intraplex.livelook.IPLinkNetworkTool;
import intraplex.livelook.IPLinkNetworkTool_Lite;
import intraplex.livelook.JDispatchMgr;
import intraplex.livelook.LogMapEntry;
import java.util.Collection;
import java.util.logging.Level;
import javax.swing.JOptionPane;

/**
 *
 * @author Schre_000
 */
public class MailSettingsDialog extends javax.swing.JDialog {

    /**
     * Creates new form MailSettingsDialog
     */
    SMTPMailer mailer;
    AlarmManager man;
    Collection<LogMapEntry> streams;
    String [] streamList;
    double [] lossRate;
    double [] lossRateAfterCorrection;
    boolean [] enablelossRate;
    boolean [] enablelossRateAfterCorrection;
    boolean [] enableDefault;
    boolean [] enableEmail;
    boolean [] enableStreamLogging;
    int[]    alarmthreshold;
    int oldIndex = 0;
    public MailSettingsDialog(IPLinkNetworkTool parent, boolean modal) {
        super(parent, modal);
        streams = parent.mgr.getStreams();
        streamList = new String[streams.size()+1];
        lossRate = new double[streams.size()+1];
        lossRateAfterCorrection = new double[streams.size()+1];
        alarmthreshold = new int [streams.size()+1];
        enablelossRate = new boolean[streams.size()+1];
        enablelossRateAfterCorrection = new boolean[streams.size()+1];
        enableDefault = new boolean[streams.size()+1];
        enableEmail = new boolean[streams.size()+1];
        enableStreamLogging = new boolean[streams.size()+1];
        streamList[0] = "Default Profile";
        lossRate[0] = LogMapEntry.default_lossRateAlarm*100;
        lossRateAfterCorrection[0] = LogMapEntry.default_lossRateCorrectedAlarm*100;
        alarmthreshold[0] = LogMapEntry.default_alarmthresholdTime;
        enablelossRate[0] = LogMapEntry.default_lossRateAlarmEnabled;
        enablelossRateAfterCorrection[0] = LogMapEntry.default_lossRateCorrectedAlarmEnabled;
        enableDefault[0] = true;
        enableEmail[0] = true;
        enableStreamLogging[0] = true;
        
        int i = 1;
        for (LogMapEntry e : streams)
        {
           streamList[i] = e.streamName;
           lossRate[i] = e.lossRateAlarm*100;
           lossRateAfterCorrection[i] = e.lossRateCorrectedAlarm*100;
           enablelossRate[i] = e.lossRateAlarmEnabled;
           enablelossRateAfterCorrection[i] = e.lossRateCorrectedAlarmEnabled;
           enableDefault[i] = e.useDefault;
           enableEmail[i] = e.enableEmail;
           enableStreamLogging[i] = e.enableLogging;
           alarmthreshold[i] = e.alarmThresholdStream;
           i++;
        }
        initComponents();
        man = AlarmManager.getAlarmManager();
        this.mailer = man.getMailer();
        destEmailAddress.setText(mailer.getAlarmToUsername());
        emailPassword.setText(mailer.getAlarmFromPassword());
        sourceEmail.setText(mailer.getAlarmFromUsername());
        serverAddress.setText(mailer.getServerAddress());
        serverPort.setText(mailer.getPort());
        enableEmailAlarms.setSelected(LogMapEntry.default_enableEmail);
        lossRatePercent.setText(LogMapEntry.default_lossRateAlarm*100 + "");
        afterCorrectPer.setText(LogMapEntry.default_lossRateCorrectedAlarm*100 + "");
        alarmThresholdTime.setText(LogMapEntry.default_alarmthresholdTime + "");
        enableLossRateAlarm.setSelected(LogMapEntry.default_lossRateAlarmEnabled);
        enableLossRateCorAlarm.setSelected(LogMapEntry.default_lossRateCorrectedAlarmEnabled);
        enableDataLogging.setSelected(LogMapEntry.default_enableStreamLogging);
        useDefaultBox.setSelected(true);
        emailAlarmsSet(man.isSendEmailAlarms());
        globalEnableEmail.setSelected(man.isSendEmailAlarms());
        useDefaultBox.setVisible(false);
        //emailAlarmsFailOnly.setSelected(man.getEmailLevel() == Level.SEVERE);
        setVisible(true);
    }
    
    public MailSettingsDialog(IPLinkNetworkTool_Lite parent, boolean modal) {
        super(parent, modal);
        streams = parent.mgr.getStreams();
        streamList = new String[streams.size()+1];
        lossRate = new double[streams.size()+1];
        lossRateAfterCorrection = new double[streams.size()+1];
        alarmthreshold = new int [streams.size()+1];
        enablelossRate = new boolean[streams.size()+1];
        enablelossRateAfterCorrection = new boolean[streams.size()+1];
        enableDefault = new boolean[streams.size()+1];
        enableEmail = new boolean[streams.size()+1];
        enableStreamLogging = new boolean[streams.size()+1];
        streamList[0] = "Default Profile";
        lossRate[0] = LogMapEntry.default_lossRateAlarm*100;
        lossRateAfterCorrection[0] = LogMapEntry.default_lossRateCorrectedAlarm*100;
        alarmthreshold[0] = LogMapEntry.default_alarmthresholdTime;
        enablelossRate[0] = LogMapEntry.default_lossRateAlarmEnabled;
        enablelossRateAfterCorrection[0] = LogMapEntry.default_lossRateCorrectedAlarmEnabled;
        enableDefault[0] = true;
        enableEmail[0] = true;
        enableStreamLogging[0] = true;
        
        int i = 1;
        for (LogMapEntry e : streams)
        {
           streamList[i] = e.streamName;
           lossRate[i] = e.lossRateAlarm*100;
           lossRateAfterCorrection[i] = e.lossRateCorrectedAlarm*100;
           enablelossRate[i] = e.lossRateAlarmEnabled;
           enablelossRateAfterCorrection[i] = e.lossRateCorrectedAlarmEnabled;
           enableDefault[i] = e.useDefault;
           enableEmail[i] = e.enableEmail;
           enableStreamLogging[i] = e.enableLogging;
           alarmthreshold[i] = e.alarmThresholdStream;
           i++;
        }
        initComponents();
        man = AlarmManager.getAlarmManager();
        this.mailer = man.getMailer();
        destEmailAddress.setText(mailer.getAlarmToUsername());
        emailPassword.setText(mailer.getAlarmFromPassword());
        sourceEmail.setText(mailer.getAlarmFromUsername());
        serverAddress.setText(mailer.getServerAddress());
        serverPort.setText(mailer.getPort());
        enableEmailAlarms.setSelected(LogMapEntry.default_enableEmail);
        lossRatePercent.setText(LogMapEntry.default_lossRateAlarm*100 + "");
        afterCorrectPer.setText(LogMapEntry.default_lossRateCorrectedAlarm*100 + "");
        alarmThresholdTime.setText(LogMapEntry.default_alarmthresholdTime + "");
        enableLossRateAlarm.setSelected(LogMapEntry.default_lossRateAlarmEnabled);
        enableLossRateCorAlarm.setSelected(LogMapEntry.default_lossRateCorrectedAlarmEnabled);
        enableDataLogging.setSelected(LogMapEntry.default_enableStreamLogging);
        useDefaultBox.setSelected(true);
        emailAlarmsSet(man.isSendEmailAlarms());
        globalEnableEmail.setSelected(man.isSendEmailAlarms());
        useDefaultBox.setVisible(false);
        //emailAlarmsFailOnly.setSelected(man.getEmailLevel() == Level.SEVERE);
        setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField3 = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        serverPort1 = new javax.swing.JTextField();
        destEmailAddress1 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        serverAddress1 = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        sourceEmail1 = new javax.swing.JTextField();
        emailPassword1 = new javax.swing.JPasswordField();
        testMessage1 = new javax.swing.JButton();
        enableEmailAlarms1 = new javax.swing.JCheckBox();
        jLabel6 = new javax.swing.JLabel();
        saveButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        serverPort = new javax.swing.JTextField();
        destEmailAddress = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        serverAddress = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        sourceEmail = new javax.swing.JTextField();
        emailPassword = new javax.swing.JPasswordField();
        testMessage = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        lossRatePercent = new javax.swing.JTextField();
        enableLossRateAlarm = new javax.swing.JCheckBox();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        enableLossRateCorAlarm = new javax.swing.JCheckBox();
        afterCorrectPer = new javax.swing.JTextField();
        streamBox = new javax.swing.JComboBox();
        jLabel14 = new javax.swing.JLabel();
        enableEmailAlarms = new javax.swing.JCheckBox();
        useDefaultBox = new javax.swing.JCheckBox();
        enableDataLogging = new javax.swing.JCheckBox();
        alarmThresholdTime = new javax.swing.JTextField();
        AlarmThresholdLabel1 = new javax.swing.JLabel();
        alarmThresholdLabel2 = new javax.swing.JLabel();
        globalEnableEmail = new javax.swing.JCheckBox();

        jTextField3.setText("jTextField1");

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "SMTP Settings"));

        serverPort1.setText(" ");
        serverPort1.setEnabled(false);

        destEmailAddress1.setText(" ");
        destEmailAddress1.setEnabled(false);

        jLabel9.setText("Source Email Password");

        jLabel10.setText("Source Email Address");

        serverAddress1.setText(" ");
        serverAddress1.setEnabled(false);

        jLabel11.setText("Destination Email Address");

        jLabel12.setText("SMTP Server Port");

        jLabel13.setText("SMTP Server Address");

        sourceEmail1.setText(" ");
        sourceEmail1.setEnabled(false);

        emailPassword1.setText("jPasswordField1");
        emailPassword1.setEnabled(false);

        testMessage1.setText("Send Test Message");
        testMessage1.setEnabled(false);
        testMessage1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                testMessage1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11)
                            .addComponent(jLabel12))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(serverPort1)
                            .addComponent(destEmailAddress1, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(jLabel13)
                            .addComponent(jLabel10))
                        .addGap(24, 24, 24)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(serverAddress1)
                            .addComponent(emailPassword1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE)
                            .addComponent(sourceEmail1, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addContainerGap(36, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(testMessage1, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(108, 108, 108))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(sourceEmail1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(emailPassword1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(serverAddress1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(serverPort1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(destEmailAddress1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addComponent(testMessage1)
                .addContainerGap())
        );

        enableEmailAlarms1.setText("Enable Email Alarms");
        enableEmailAlarms1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                enableEmailAlarms1ActionPerformed(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel6.setText("Alarm Settings");

        saveButton.setText("OK");
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "SMTP Settings"));

        serverPort.setText(" ");
        serverPort.setEnabled(false);

        destEmailAddress.setText(" ");
        destEmailAddress.setEnabled(false);

        jLabel2.setText("Source Email Password");

        jLabel1.setText("Source Email Address");

        serverAddress.setText(" ");
        serverAddress.setEnabled(false);

        jLabel5.setText("Destination Email Address");

        jLabel4.setText("SMTP Server Port");

        jLabel3.setText("SMTP Server Address");

        sourceEmail.setText(" ");
        sourceEmail.setEnabled(false);

        emailPassword.setText("jPasswordField1");
        emailPassword.setEnabled(false);

        testMessage.setText("Send Test Message");
        testMessage.setEnabled(false);
        testMessage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                testMessageActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(serverPort))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(destEmailAddress))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(32, 32, 32)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(serverAddress)
                            .addComponent(emailPassword, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE)
                            .addComponent(sourceEmail, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(147, Short.MAX_VALUE)
                .addComponent(testMessage, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(124, 124, 124))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(sourceEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(emailPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(serverAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(serverPort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(destEmailAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(testMessage)
                .addGap(6, 6, 6))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Stream Alerts"));

        lossRatePercent.setText("5");
        lossRatePercent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lossRatePercentActionPerformed(evt);
            }
        });

        enableLossRateAlarm.setText("% Loss Rate greater than ");
        enableLossRateAlarm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                enableLossRateAlarmActionPerformed(evt);
            }
        });

        jLabel7.setText("%");

        jLabel8.setText("%");

        enableLossRateCorAlarm.setText("% Loss Rate after correction greater than ");
        enableLossRateCorAlarm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                enableLossRateCorAlarmActionPerformed(evt);
            }
        });

        afterCorrectPer.setText("1");

        streamBox.setModel(new javax.swing.DefaultComboBoxModel(streamList));
        streamBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                streamBoxActionPerformed(evt);
            }
        });

        jLabel14.setText("Stream Name");

        enableEmailAlarms.setText("Enable Email Alarms");
        enableEmailAlarms.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                enableEmailAlarmsActionPerformed(evt);
            }
        });

        useDefaultBox.setText("Use Default");
        useDefaultBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                useDefaultBoxActionPerformed(evt);
            }
        });

        enableDataLogging.setText("Enable Logging");
        enableDataLogging.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                enableDataLoggingActionPerformed(evt);
            }
        });

        alarmThresholdTime.setText("30");
        alarmThresholdTime.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                alarmThresholdTimeActionPerformed(evt);
            }
        });

        AlarmThresholdLabel1.setText("  Alarm Threshold Time");

        alarmThresholdLabel2.setText("seconds");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(streamBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addGap(0, 55, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(AlarmThresholdLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(alarmThresholdTime, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(alarmThresholdLabel2)
                        .addGap(88, 88, 88))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(useDefaultBox)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(enableLossRateCorAlarm)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(afterCorrectPer, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                        .addComponent(enableLossRateAlarm)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(lossRatePercent, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel7)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel8))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(enableEmailAlarms)
                                .addGap(40, 40, 40)
                                .addComponent(enableDataLogging, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(enableLossRateAlarm)
                    .addComponent(lossRatePercent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(afterCorrectPer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(enableLossRateCorAlarm)
                    .addComponent(streamBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AlarmThresholdLabel1)
                    .addComponent(alarmThresholdTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(alarmThresholdLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(enableEmailAlarms)
                    .addComponent(enableDataLogging))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 4, Short.MAX_VALUE)
                .addComponent(useDefaultBox)
                .addContainerGap())
        );

        globalEnableEmail.setText("Enable Email");
        globalEnableEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                globalEnableEmailActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(153, 153, 153)
                        .addComponent(saveButton, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(globalEnableEmail)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(11, 11, 11)
                                    .addComponent(jLabel6))
                                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addContainerGap(33, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(globalEnableEmail)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(saveButton)
                    .addComponent(cancelButton))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void testMessageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_testMessageActionPerformed
        SMTPMailer m = new SMTPMailer();
        m.setPort(serverPort.getText());
        m.setAlarmFromPassword(new String(emailPassword.getPassword()));
        m.setAlarmFromUsername(sourceEmail.getText());
        m.setAlarmToUsername(destEmailAddress.getText());
        m.setServerAddress(serverAddress.getText());
        try
        {
            int i = Integer.parseInt(m.port);
        }
        catch (Exception e)
        {
           JOptionPane.showMessageDialog(this.getParent(),"Port must be a valid integer!", "Error",
                                JOptionPane.ERROR_MESSAGE);
           return;
        }
        if (m.alarmFromUsername.length() == 0 || m.alarmToUsername.length() == 0 )
        {
           JOptionPane.showMessageDialog(this.getParent(),"Email cannot be empty", "Error",
                                JOptionPane.ERROR_MESSAGE);
           return;
        }
        if (m.serverAddress.length() == 0 )
        {
           JOptionPane.showMessageDialog(this.getParent(),"Server cannot be empty", "Error",
                                JOptionPane.ERROR_MESSAGE);
           return;
        }
        
        if (m.sendTestMessage())
        {
        JOptionPane.showMessageDialog(this.getParent(),"Test Message has been sent");
        }
        else
        {
           JOptionPane.showMessageDialog(this.getParent(),"Failed to send test message", "Error",
                                JOptionPane.ERROR_MESSAGE);
        }
            
    }//GEN-LAST:event_testMessageActionPerformed

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        
        if (globalEnableEmail.isSelected())
        {
            try
            {
                int i = Integer.parseInt(serverPort.getText());
            }
            catch (Exception e)
            {
                JOptionPane.showMessageDialog(this.getParent(),"Port must be a valid integer!", "Error",
                                JOptionPane.ERROR_MESSAGE);
                return;
            }
             if ((sourceEmail.getText().length())== 0 || (destEmailAddress.getText().length())== 0 )
             {
                JOptionPane.showMessageDialog(this.getParent(),"Email cannot be empty", "Error",
                                JOptionPane.ERROR_MESSAGE);
                return;
             }
             if ((serverAddress.getText().length())== 0 )
             {
                JOptionPane.showMessageDialog(this.getParent(),"Server cannot be empty", "Error",
                                JOptionPane.ERROR_MESSAGE);
                return;
              }
            
            
        }
        mailer.setPort(serverPort.getText());
        mailer.setAlarmFromPassword(new String(emailPassword.getPassword()));
        mailer.setAlarmFromUsername(sourceEmail.getText());
        mailer.setAlarmToUsername(destEmailAddress.getText());
        mailer.setServerAddress(serverAddress.getText());
        
        
        man.setSendEmailAlarms(globalEnableEmail.isSelected());
        //if (emailAlarmsFailOnly.isSelected())
        //{
        //    man.setEmailLevel(Level.SEVERE);
        //}
        //else
        //{
            man.setEmailLevel(Level.INFO);
        //}
        man.saveAlarmManager();
        
        streamBoxActionPerformed(null);
        
        LogMapEntry.default_lossRateAlarm = lossRate[0]/100;
        LogMapEntry.default_lossRateCorrectedAlarm = lossRateAfterCorrection[0]/100;
        LogMapEntry.default_lossRateAlarmEnabled = enablelossRate[0];
        LogMapEntry.default_lossRateCorrectedAlarmEnabled = enablelossRateAfterCorrection[0];
        LogMapEntry.default_enableEmail = enableEmail[0];
        LogMapEntry.default_enableStreamLogging = enableStreamLogging[0];
        LogMapEntry.default_alarmthresholdTime = alarmthreshold[0];
        
        int i = 1;
        for (LogMapEntry e : streams)
        {
            int index = i;
           e.useDefault = enableDefault[index];
           if (e.useDefault)index = 0;
           e.lossRateAlarm = lossRate[index]/100;
           e.lossRateCorrectedAlarm = lossRateAfterCorrection[index]/100;
           e.lossRateAlarmEnabled = enablelossRate[index];
           e.lossRateCorrectedAlarmEnabled = enablelossRateAfterCorrection[index];
           e.enableEmail = enableEmail[index];
           e.enableLogging = enableStreamLogging[index];
           e.alarmThresholdStream = alarmthreshold[index];
          // System.out.println("e. enablelogging is "+e.enableLogging +" index : "+ i);
           i++;
        }
        LogMapEntry.SaveConfig();
        this.setVisible(false);
        this.dispose();
    }//GEN-LAST:event_saveButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        this.setVisible(false);
        this.dispose();
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void enableEmailAlarmsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_enableEmailAlarmsActionPerformed
        
    }//GEN-LAST:event_enableEmailAlarmsActionPerformed

    private void enableLossRateAlarmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_enableLossRateAlarmActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_enableLossRateAlarmActionPerformed

    private void enableLossRateCorAlarmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_enableLossRateCorAlarmActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_enableLossRateCorAlarmActionPerformed

    private void lossRatePercentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lossRatePercentActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lossRatePercentActionPerformed

    private void testMessage1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_testMessage1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_testMessage1ActionPerformed

    private void streamBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_streamBoxActionPerformed
       int newIndex = streamBox.getSelectedIndex();
       
       //JOptionPane.showMessageDialog(this.getParent(),"New Index: "+ newIndex + " Old Index :" +oldIndex);
       
        enableEmail[oldIndex] = enableEmailAlarms.isSelected();
         try 
        {
            lossRate[oldIndex] = Double.parseDouble(lossRatePercent.getText());
        }
        catch (Exception e)
        {
            lossRate[oldIndex] = lossRate[0];
        }
        try 
        {
            lossRateAfterCorrection[oldIndex] = Double.parseDouble(afterCorrectPer.getText());
        }
        catch (Exception e)
        {
            lossRateAfterCorrection[oldIndex] = lossRateAfterCorrection[0];  
        }
        
        try
        {
            int value = Integer.parseInt(alarmThresholdTime.getText());
            if (value < 5)
            {
               JOptionPane.showMessageDialog(this.getParent(),"Minimum alarm threshold time is 5 seconds");
               value = 5;
               alarmthreshold[oldIndex] = value;
            }
            else if ((value % 5)>0)
            {
                value = value - (value % 5);
            }
            alarmthreshold[oldIndex] = value;
            
        }
        catch ( NumberFormatException e)
        {
            JOptionPane.showMessageDialog(this.getParent(),"Invalid alarm threshold");
            alarmthreshold[oldIndex] = alarmthreshold[0];
        }
        catch (Exception e)
        {
            alarmthreshold[oldIndex] = alarmthreshold[0];
        }
        enablelossRate[oldIndex] = enableLossRateAlarm.isSelected();
        enablelossRateAfterCorrection[oldIndex] = enableLossRateCorAlarm.isSelected();
        enableDefault[oldIndex] = useDefaultBox.isSelected();
        enableStreamLogging[oldIndex] = enableDataLogging.isSelected();
        //System.out.println ("Enable Stream logging for oldIndex  "+oldIndex + " is " + enableStreamLogging[oldIndex]);
        
        
        enableEmailAlarms.setSelected(enableEmail[newIndex]);
        lossRatePercent.setText(lossRate[newIndex] + "");
        afterCorrectPer.setText(lossRateAfterCorrection[newIndex] + "");
        alarmThresholdTime.setText(alarmthreshold[newIndex] + "");
        enableLossRateAlarm.setSelected(enablelossRate[newIndex]);
        enableLossRateCorAlarm.setSelected(enablelossRateAfterCorrection[newIndex]);
        useDefaultBox.setSelected(enableDefault[newIndex]);
        enableDataLogging.setSelected(enableStreamLogging[newIndex]);
        
        //System.out.println ("enableDataLogging for newIndex: "+newIndex + " is " + enableStreamLogging[newIndex]);
        
        if (newIndex != 0)
        {
           //not a default profile
           useDefaultSet(!enableDefault[newIndex]);
           useDefaultBox.setVisible(true);
        }
        else
        {
           useDefaultSet(true);
           useDefaultBox.setVisible(false);
        }
       oldIndex = newIndex;
    }//GEN-LAST:event_streamBoxActionPerformed

    private void enableEmailAlarms1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_enableEmailAlarms1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_enableEmailAlarms1ActionPerformed

    private void globalEnableEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_globalEnableEmailActionPerformed
        emailAlarmsSet(globalEnableEmail.isSelected());
    }//GEN-LAST:event_globalEnableEmailActionPerformed

    private void enableDataLoggingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_enableDataLoggingActionPerformed

        
    // TODO add your handling code here:
         
        
    }//GEN-LAST:event_enableDataLoggingActionPerformed

    private void useDefaultBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_useDefaultBoxActionPerformed

        
        // TODO add your handling code here:
        /*enableLossRateAlarm.setEnabled(useDefaultBox.isSelected());
        enableLossRateCorAlarm.setEnabled(useDefaultBox.isSelected());
        lossRatePercent.setEnabled(useDefaultBox.isSelected());
        afterCorrectPer.setEnabled(useDefaultBox.isSelected());
        enableEmailAlarms.setEnabled(useDefaultBox.isSelected());
        enableDataLogging.setEnabled(useDefaultBox.isSelected());*/
        
        useDefaultSet(!(useDefaultBox.isSelected()));
        
    }//GEN-LAST:event_useDefaultBoxActionPerformed

    private void alarmThresholdTimeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_alarmThresholdTimeActionPerformed
    
        // TODO add your handling code here:
               
    }//GEN-LAST:event_alarmThresholdTimeActionPerformed

    private void useDefaultSet (boolean b)
    {
        enableLossRateAlarm.setEnabled(b);
        enableLossRateCorAlarm.setEnabled(b);
        lossRatePercent.setEnabled(b);
        afterCorrectPer.setEnabled(b);
        enableEmailAlarms.setEnabled(b);
        enableDataLogging.setEnabled(b);
        alarmThresholdTime.setEnabled(b);
        AlarmThresholdLabel1.setEnabled(b);
        alarmThresholdLabel2.setEnabled(b);
        
    }
    
    private void emailAlarmsSet(boolean b)
    {
        testMessage.setEnabled(b);
        destEmailAddress.setEnabled(b);
        emailPassword.setEnabled(b);
        serverAddress.setEnabled(b);
        serverPort.setEnabled(b);
        sourceEmail.setEnabled(b);
    }
    /**
     * @param args the command line arguments
     */
   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel AlarmThresholdLabel1;
    private javax.swing.JTextField afterCorrectPer;
    private javax.swing.JLabel alarmThresholdLabel2;
    private javax.swing.JTextField alarmThresholdTime;
    private javax.swing.JButton cancelButton;
    private javax.swing.JTextField destEmailAddress;
    private javax.swing.JTextField destEmailAddress1;
    private javax.swing.JPasswordField emailPassword;
    private javax.swing.JPasswordField emailPassword1;
    private javax.swing.JCheckBox enableDataLogging;
    private javax.swing.JCheckBox enableEmailAlarms;
    private javax.swing.JCheckBox enableEmailAlarms1;
    private javax.swing.JCheckBox enableLossRateAlarm;
    private javax.swing.JCheckBox enableLossRateCorAlarm;
    private javax.swing.JCheckBox globalEnableEmail;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField lossRatePercent;
    private javax.swing.JButton saveButton;
    private javax.swing.JTextField serverAddress;
    private javax.swing.JTextField serverAddress1;
    private javax.swing.JTextField serverPort;
    private javax.swing.JTextField serverPort1;
    private javax.swing.JTextField sourceEmail;
    private javax.swing.JTextField sourceEmail1;
    private javax.swing.JComboBox streamBox;
    private javax.swing.JButton testMessage;
    private javax.swing.JButton testMessage1;
    private javax.swing.JCheckBox useDefaultBox;
    // End of variables declaration//GEN-END:variables
}
