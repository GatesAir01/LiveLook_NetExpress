/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package intraplex.livelook;

import static intraplex.livelook.ConfigFile.configfile;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Iterator;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jschreiv
 */
public class LiveLookConfig extends Properties{
    
    private String configfile;
    String [] allowedProperties  = new String[] {
    "LogRotation",
    "History.Trace.1.Color",
    "History.Trace.2.Color",
    "History.Trace.3.Color",
    "History.Trace.4.Color",
    "History.Trace.5.Color",
    "History.Trace.6.Color",
    "History.Trace.7.Color",
    "History.Trace.8.Color",
    "LiveView.Trace.1.Color",
    "LiveView.Trace.2.Color",
    "LiveView.Trace.3.Color",
    "LiveView.Trace.4.Color",
    "LiveView.Trace.5.Color",
    "LiveView.Trace.6.Color",
    "LiveView.Trace.7.Color",
    "LiveView.Trace.8.Color",
    "LiveView.Interval",
    "PPSInterval",
    "Search.Query.1",
    "Search.Query.2",
    "Search.Query.3",
    "defaultDirectory",
    "showRxJitter",
    "Connections",
    "Alert.EnableEmail",
    "Alert.LossRatePercent",
    "Alert.EnableCorrectedLossRate",
    "Alert.EnableLossRate",
    "Alert.alarmthresholdtime",
    "Alert.EnableStreamLogging",
    "Alert.CorrectedLossRatePercent",
    "SignalingPort",
    "MacFileLocation"
    };
    
    public LiveLookConfig(String config)
    {
        configfile = config;
        put("defaultDirectory", System.getProperty("user.home")+"/Livelook NetXpress");
        
    }
    
    int getInt(String prop, int def)
    {
        int i = def;
        try
        {
        i = Integer.parseInt((String)get(prop));
        }catch (Exception e){}
        return i;
    }
    
    double getDouble(String prop, double def)
    {
        double i = def;
        try
        {
        i = Double.parseDouble((String)get(prop));
        }catch (Exception e){}
        return i;
    }
    
    boolean getBoolean(String prop, boolean def)
    {
        boolean i = def;
        try
        {
         String s = (String)get(prop);
         if (s.equalsIgnoreCase("true"))i = true;
         else if (s.equalsIgnoreCase("false"))i = false;
         
        }catch (Exception e){}
        return i;
    }
    
    protected boolean load(String filename)
    {
        
        try {
            load(new FileInputStream(filename));
            //loadFromXML(InputStream in)
        } catch (IOException ex) {
            return false;
        }
        moveCurrentDirectory();
        return true;
    }   
    
    public boolean moveCurrentDirectory()
    {
        File currentDirectory = new File("templates").getAbsoluteFile();
        
        boolean result = false;  // Boolean indicating whether directory was set
        File    directory;       // Desired current working directory

        directory = new File(this.getProperty("defaultDirectory"));
        
        if (directory.exists() || directory.mkdirs())
        {
            result = (System.setProperty("user.dir", directory.getAbsolutePath()) != null);
            
            File newDirectory = new File("templates").getAbsoluteFile();
            try {
                
                if (!newDirectory.exists())
                {
                    newDirectory.mkdir();
                    //Move the template
                    Files.copy(new File(currentDirectory.getAbsolutePath()+"/report.html").toPath(), new File(newDirectory.getAbsolutePath()+"/report.html").toPath());
                    File gen = new File("generated").getAbsoluteFile();
                    if (!gen.exists())
                    {
                        gen.mkdir();
                    }
                }
                        
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
        }
        
        return result;
    }
       
    protected boolean load()
    {
        try {
            load(new FileInputStream(configfile));
            //loadFromXML(InputStream in)
        } catch (IOException ex) {
            try
            {
                configfile = this.getProperty("defaultDirectory") + "/"+configfile;
                load(new FileInputStream(configfile));
                
            }
            catch (Exception e)
            {
                moveCurrentDirectory();
                return false;
            }
        }
        configfile = (new File(configfile)).getAbsolutePath();
        moveCurrentDirectory();
        return true;
    }
    
    public String getBadProperties()
    {
        Iterator iter = this.stringPropertyNames().iterator();
        String bad = "";
        while (iter.hasNext())
        {
            String n = (String)iter.next();
            boolean known = false;
            for (String allowedPropertie : allowedProperties) {
                if (allowedPropertie.equalsIgnoreCase(n)) {
                    known = true;
                    break;
                }
            }
            if (!known)
            {
                if (bad.length() > 0)
                {
                   bad+=",\n"+n;
                }
                else
                {
                    bad = n;
                }
                this.remove(n);
            }
        }
        return bad;
    }
    
    protected boolean delete()
    {
        try {
            File newConfigFile = new File(configfile);
            
            if(newConfigFile.delete())
            {
                System.out.println("Deleted file " + configfile );
            }
            else
            {
                System.out.println("Failed To Delete file " +configfile );
                if (!newConfigFile.exists())
            System.out.println( "It doesn't exist in the first place.");
                else if (newConfigFile.isDirectory() && newConfigFile.list().length > 0)
            System.out.println( "It's a directory and it's not empty.");
                 else
            System.out.println( "Somebody else has it open, we don't have write permissions, or somebody stole my disk.");
                
            }
            //storeToXML(new FileOutputStream(configfile),null);
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }
    

    protected boolean save()
    {
        try {
            store(new FileOutputStream(configfile),null);
            //storeToXML(new FileOutputStream(configfile),null);
        } catch (IOException ex) {
            return false;
        }
        return true;
    }
    
    protected boolean saveCopy(String filename)
    {
        if (!filename.endsWith(".properties"))
        {
            filename = filename + ".properties";
        }
        try {
            store(new FileOutputStream(filename),null);
            //storeToXML(new FileOutputStream(configfile),null);
        } catch (IOException ex) {
            return false;
        }
        return true;
    }
        
    public void saveEntry(String k, String v)
    {
        put(k, v);
        save();
    }
    
}
