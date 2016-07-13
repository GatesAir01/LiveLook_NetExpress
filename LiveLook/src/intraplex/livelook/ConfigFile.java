/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package intraplex.livelook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jschreiv
 */
public class ConfigFile extends Properties{
    
    public static String configfile = "hist.prop";
    public ConfigFile()
    {
    }
    
    protected boolean load()
    {
        try {
            configfile = (new File(configfile)).getAbsolutePath();
            load(new FileInputStream(configfile));
            //loadFromXML(InputStream in)
        } catch (IOException ex) {
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
    
    public void saveEntry(String k, String v)
    {
        put(k, v);
        save();
    }
    
}
