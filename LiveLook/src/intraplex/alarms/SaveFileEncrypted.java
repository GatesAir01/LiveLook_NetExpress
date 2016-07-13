/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package intraplex.alarms;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author jschreiv
 */
public class SaveFileEncrypted {
    
    
    
    public static byte[] encrypt(byte[] b)
    {
        try{
        byte[] KeyData = getKey();
        SecretKeySpec KS = new SecretKeySpec(KeyData, "Blowfish");
        Cipher cipher = Cipher.getInstance("Blowfish");
        cipher.init(Cipher.ENCRYPT_MODE, KS);
        return cipher.doFinal(b);
        }
        catch (Exception e)
        {
            return null;
        }
    }
    
    public static byte[] decrypt(byte[] b) 
    {
        try{
        byte[] KeyData =  getKey();
        SecretKeySpec KS = new SecretKeySpec(KeyData, "Blowfish");
        Cipher cipher = Cipher.getInstance("Blowfish");
        cipher.init(Cipher.DECRYPT_MODE, KS);
        
            return cipher.doFinal(b);
        }
        catch (Exception e)
        {
            return null;
        }
    }
    

    
    public static byte[] getKey() 
    {

        InetAddress ip;
        try {

            ip = InetAddress.getLocalHost();
            NetworkInterface network = NetworkInterface.getByInetAddress(ip);
            byte[] mac = network.getHardwareAddress();
            String s = new String(mac) + "revierhcs";
            byte[] bytesOfMessage = s.getBytes();
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] thedigest = md.digest(bytesOfMessage);
            return thedigest;

        } catch (Exception e) {

            String s = System.getProperty("user.name") + "revierhcs";
            byte[] bytesOfMessage = s.getBytes();
            try{
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] thedigest = md.digest(bytesOfMessage);
            return thedigest;
            }
            catch (Exception e2)
            {
                return s.getBytes();
            }

        } 
    }
        
    public static boolean saveEncyptedString(String file, String s)
    {
        byte[] b =  encrypt(s.getBytes());
        if (b == null)return false;
        try {
            FileOutputStream fos = new FileOutputStream(file);
            
            fos.write(b);
            fos.flush();
            fos.close();
        } catch (Exception ex) {
            return false;
        }
        return true;
    }

    public static String loadEncyptedString(String file)
    {

        try {
            FileInputStream fis = new FileInputStream(file);
            byte[] b = new byte[1024];
            int len = fis.read(b);
            byte[] de = decrypt(Arrays.copyOf(b, len));

            if (de == null) {
                return null;
            }
            return new String(de);
        } catch (Exception ex) {
            return null;
        }
    }
    
    
}
