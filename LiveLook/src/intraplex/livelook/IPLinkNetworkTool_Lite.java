/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package intraplex.livelook;

import com.jtattoo.plaf.hifi.HiFiLookAndFeel;
import intraplex.alarms.MailSettingsDialog;
//import static intraplex.livelook.NetworkLogDataPoint.BUFFER_DELAY;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Locale;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.html.HTMLDocument;

/**
 *
 * @author jschreiv
 */


public class IPLinkNetworkTool_Lite extends JFrame implements ActionListener, WindowListener{
    boolean lite = true;
	JPanel history;
	JPanel report;
    MultiLiveLookPanel livelook;
    JEditorPane connectedStreams;
    boolean showLivelook;
    Component csTab;
    public static ConfigFile configlite;
    public static LiveLookConfig livelookconfiglite;
    public SnmpMgr mgr;
    public static JTabbedPane tabbedPane;
    public IPLinkNetworkTool_Lite()
    {
        super("Intraplex"+((char)(174))+("LiveLook Lite NetXpress"));


        livelookconfiglite = new LiveLookConfig("livelook.properties");
        configlite = new ConfigFile();

        livelookconfiglite.load();
        configlite.load();
        System.out.println("LiveLook Lite NetXpress has started.");
        loadConfigurations();
        report = new JPanel(false);
        history = new JPanel(false);

        String filename = "log.csv";
        File f = new File("logs").getAbsoluteFile();
        if (f.exists() && f.isDirectory())
        {
            File [] streams = f.listFiles();
            if (streams != null && streams.length > 0)
            {
                Arrays.sort(streams, new Comparator<File>(){
                    public int compare(File f1, File f2)
                        {return (int)(f2.lastModified()-f1.lastModified());}});

                for (int i = 0; i < streams.length; i++)
                {
                    File [] data = streams[i].listFiles();

                    if (data != null && data.length > 0)
                    {
                    Arrays.sort(data, new Comparator<File>(){
                        public int compare(File f1, File f2)
                            {return (int)(f2.lastModified()-f1.lastModified());}});

                    filename = data[0].getPath();
                    break;
                    }
                }
            }

        }

        tabbedPane = new JTabbedPane();
        mgr = new SnmpMgr(lite, null);
        showLivelook = false;
        while (mgr.bindState() == 0)
        {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(IPLinkNetworkTool_Lite.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (mgr.bindState() == 2)
        {
            showLivelook = true;
            livelook = new MultiLiveLookPanel(mgr, lite);
            tabbedPane.add("Live View", livelook);

            try
            {
                String s = livelookconfiglite.get("Connections").toString();
                if (s != null)
                {
                    mgr.loadConnections(s);
                }
            }
            catch (Exception e)
            {
            }
        }
        tabbedPane.add("History", history);
        tabbedPane.add("Report", report);

        tabbedPane.setEnabledAt(1, false);
        tabbedPane.setEnabledAt(2, false);

        StatusPanel sp = new StatusPanel(mgr);
        if (showLivelook)
        {
            csTab = tabbedPane.add("Connected Streams", sp);
            //csTab.setBackground(Color.red);
            //sp.setBackground(Color.red);
            //sp.setForeground(Color.red);
            tabbedPane.setBackgroundAt(3,Color.gray);
        }


        setContentPane(tabbedPane);
        try {
            BufferedImage icon = ImageIO.read(getClass().getClassLoader().getResource("resources/icon.png"));
            if (icon != null)
                this.setIconImage(icon);
        } catch (IOException ex) {
            Logger.getLogger(IPLinkNetworkTool_Lite.class.getName()).log(Level.SEVERE, null, ex);
        }

      if (showLivelook)
        {
            JMenuBar bar = new JMenuBar();
            JMenu menu = new JMenu("Settings");

            JMenuItem menuItem = new JMenuItem("Save");
            menuItem.addActionListener(this);
            menuItem.setActionCommand("SAVE");
            menuItem.setEnabled(false);
            menu.add(menuItem);
            menuItem = new JMenuItem("Load");
            menuItem.addActionListener(this);
            menuItem.setActionCommand("LOAD");
            menuItem.setEnabled(false);
            menu.add(menuItem);
            menuItem = new JMenuItem("Log File Rotation");
            menuItem.addActionListener(this);
            menuItem.setActionCommand("LOGROT");
            menuItem.setEnabled(false);
            menu.add(menuItem);
            menuItem = new JMenuItem("Signaling Port");
            menuItem.addActionListener(this);
            menuItem.setActionCommand("SP");
            menuItem.setEnabled(true);
            menu.add(menuItem);
            menuItem = new JMenuItem("Alarm Settings");
            menuItem.addActionListener(this);
            menuItem.setActionCommand("MAIL");
            menuItem.setEnabled(false);
            menu.add(menuItem);
            menuItem = new JMenuItem("Remember Connections");
            menuItem.addActionListener(this);
            menuItem.setActionCommand("REMC");
            menuItem.setEnabled(false);
            menu.add(menuItem);

           /* menuItem = new JMenuItem("Set Default Configuration");
            menuItem.addActionListener(this);
            menuItem.setActionCommand("DEFC");
            menu.add(menuItem);*/
            bar.add(menu);

            menu = new JMenu("About");
            menuItem = new JMenuItem("About LiveLook Lite NetXpress");
            menuItem.setActionCommand("ABOUT");
            menuItem.addActionListener(this);
            bar.add(menu);
            menu.add(menuItem);


            this.setJMenuBar(bar);
        }

        //csTab.setBackground(Color.red);
        setSize(850, 630);
        addWindowListener(this);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }

    public static void updateConnectedStreamstabColor(Color e)
    {
        if(tabbedPane != null)
        	tabbedPane.setBackgroundAt(3,e);
    }


    public static void main(String[] args)
    {
        Handler handler;
        try {
            handler = new FileHandler("error.log", 1000, 5, true);
            Logger.getLogger("errorlog").addHandler(handler);
        } catch (IOException e) {}

        //Set the Local to English, This must be removed to support other languages.
        Locale.setDefault(new Locale("en", "US"));
        String laf = UIManager.getSystemLookAndFeelClassName();
        Properties props = new Properties();
        props.put("logoString", "Intraplex");
        HiFiLookAndFeel.setCurrentTheme(props);
        try {UIManager.setLookAndFeel("com.jtattoo.plaf.hifi.HiFiLookAndFeel");} catch (Exception ex) {}
        IPLinkNetworkTool_Lite ipnt = new IPLinkNetworkTool_Lite();

        ipnt.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent ae) {

        if (ae.getActionCommand().equals("ABOUT"))
        {
            AboutDialog dialog = new AboutDialog(this, true, lite);
            dialog.setVisible(true);
        }
        else if (ae.getActionCommand().equals("SAVE"))
        {
            JFileChooser chooser = new JFileChooser();
            int returnVal = chooser.showSaveDialog(this);
            if(returnVal == JFileChooser.APPROVE_OPTION)
            {
                livelookconfiglite.saveCopy(chooser.getSelectedFile().getAbsolutePath());
            }
        }
        else if (ae.getActionCommand().equals("REMC"))
        {

           livelookconfiglite.setProperty("Connections", mgr.getConnectionsForSave());
           livelookconfiglite.save();
        }
        else if (ae.getActionCommand().equals("DEFC"))
        {
            livelookconfiglite.delete();
        }
        else if (ae.getActionCommand().equals("LOAD"))
        {
            JFileChooser chooser = new JFileChooser();
            //chooser.setAcceptAllFileFilterUsed(false);
            FileFilter filter = new FileNameExtensionFilter("Properties File","properties");
            chooser.addChoosableFileFilter(filter);
            int returnVal = chooser.showOpenDialog(this);
            if(returnVal == JFileChooser.APPROVE_OPTION)
            {
                loadNewConfiguration(chooser.getSelectedFile().getAbsolutePath());
            }
        }
        else if (ae.getActionCommand().equals("MAIL"))
        {
            MailSettingsDialog dia =  new MailSettingsDialog(this, true);
        }
        else
        {
            Integer[] intA = new Integer[] {1,2,3,4,5,6,7};
            Integer logR = (Integer)JOptionPane.showInputDialog(null,
             "Number of Days", "Set Log File Rotation",
             JOptionPane.INFORMATION_MESSAGE, null,
             intA, intA[LogMapEntry.daysPerLog-1]);

            if (logR != null)
            {
                LogMapEntry.daysPerLog = logR.intValue();
                livelookconfiglite.setProperty("LogRotation", LogMapEntry.daysPerLog+"");
                livelookconfiglite.save();
            }
        }
    }

    public boolean getEnableLogging()
    {
        boolean retVal = true;

        return retVal;
    }

    private void loadConfigurations()
    {

        LogMapEntry.loadConfigLite();
        try
        {
            LogMapEntry.daysPerLog = Integer.parseInt(livelookconfiglite.get("LogRotation").toString());
            if (LogMapEntry.daysPerLog < 1 ||  LogMapEntry.daysPerLog > 7)
            {
                LogMapEntry.daysPerLog = 1;
            }
        }
        catch (Exception e)
        {
                LogMapEntry.daysPerLog = 1;
        }

        try
        {
            NetworkLogDataPoint.ppsInterval = Integer.parseInt(livelookconfiglite.get("PPSInterval").toString());
            if (LogMapEntry.daysPerLog < 1 ||  LogMapEntry.daysPerLog > 30)
            {
                NetworkLogDataPoint.ppsInterval = 1;
            }
        }
        catch (Exception e)
        {
                NetworkLogDataPoint.ppsInterval = 1;
        }

        boolean showJitter = false;
        try
        {

            showJitter = livelookconfiglite.get("showRxJitter").toString().equalsIgnoreCase("true");
            if (LogMapEntry.daysPerLog < 1 ||  LogMapEntry.daysPerLog > 30)
            {
                NetworkLogDataPoint.ppsInterval = 1;
            }
        }
        catch (Exception e)
        {
               showJitter = false;
        }
        if (showJitter)
        {
            //NetworkLogDataPoint.NUM_TRACES = NetworkLogDataPoint.RX_JITTER_IN_MSEC + 1;
        }
    }

    public void loadNewConfiguration(String file)
    {
        livelookconfiglite.load(file);
        loadConfigurations();
        livelook.reloadConfiguation();
        String s = livelookconfiglite.getBadProperties();

        if (s.length() > 0)
        {
            JOptionPane.showMessageDialog(this,
                "The selected properties file contained unknown propertes:\n"+s+"\nThe file was loaded, but these configurations were ignored",
                "Unknown Configurations",
                JOptionPane.WARNING_MESSAGE);
        }

    }

    @Override
    public void windowOpened(WindowEvent we) {
    }

    @Override
    public void windowClosing(WindowEvent we) {
        if (showLivelook)
        {
            Object[] options = { "Yes", "No" };
            int r = JOptionPane.showOptionDialog(null, "Close " + ((lite)?"LiveLook Lite NetXpress":"LiveLook") + " and disconnect?", "Close " + ((lite)?"LiveLook Lite NetXpress":"LiveLook"),
            JOptionPane.DEFAULT_OPTION, JOptionPane.YES_NO_OPTION,
            null, options, options[0]);

            if (r == 0)
            {
                livelook.disconnectAll();
                System.exit(0);
            }
        }
        else
        {
            System.exit(0);
        }
    }

    @Override
    public void windowClosed(WindowEvent we) {
    }

    @Override
    public void windowIconified(WindowEvent we) {
    }

    @Override
    public void windowDeiconified(WindowEvent we) {
    }

    @Override
    public void windowActivated(WindowEvent we) {
    }

    @Override
    public void windowDeactivated(WindowEvent we) {
      }
}
