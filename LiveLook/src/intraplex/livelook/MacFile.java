/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intraplex.livelook;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * 
 * MacFile is responsible for the loading, saving, encrypting and decrypting of the file of MacAdresses.
 * 
 * @author Josh Lucas
 *
 */
public class MacFile {
    private String filename;
    public ArrayList<String> MacAddresses;

    byte[] key = {115, 35, -69, -112, 54, 114, 122, 74, 113, -2, 64, -37, -100, -112, 120, 107};
    private Cipher desCipher;
    private SecretKey myDesKey;
	
    /**
     * Class constructor initializes list, filename, and encryption cipher
     * 
     * @param filename	This is the filename of the file containing the list of MacAddresses
     */
    public MacFile(String filename) {
        MacAddresses = new ArrayList<String>();
        this.filename = filename;

        //This is part of the Bouncy Castle api that provides the encryption method used
        myDesKey = new SecretKeySpec(key, 0, key.length, "AES");
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());  
        try
        {
            try {
                desCipher = Cipher.getInstance("AES/ECB/PKCS5Padding", "BC");
            } catch (NoSuchProviderException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        catch (NoSuchAlgorithmException | NoSuchPaddingException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Reads the filename passed in through the constructor.
     * 
     * @returns byte[]		byte[] of the information in the file still encrypted 
     * @throws IOException	caused by reading the file
     */
    public byte[] load() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filename));
        try {
        	//Reads the file into one big line
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                line = br.readLine();
            }
            String everything = sb.toString();
            if(everything.isEmpty())
            {
                br.close();
                return null;
            }
            //Then the string is split into parts by commas
            String[] parts = everything.split(",");
            byte[] result = new byte[parts.length];

            //the string array is then parsed into a byte array
            for(int x = 0; x < parts.length; x++) {
                result[x] = Byte.parseByte(parts[x]);
            }

            return result;
        } finally {
            br.close();
        }
    }

    /**
     * Saves the encrypted information to a file specified by the filename in the constructor.
     * <p>
     * This is not used in this program left over from MacEncrypter.
     * 
     * @param savebytes			encrypted byte[] to be saved
     */
    public void save(byte[] savebytes) {
        try{ 
            PrintWriter writer = new PrintWriter(filename, "UTF-8");

            String saveString = "";
            
            //makes one big string
            for(int x = 0; x < savebytes.length - 1; x++) {
                    saveString += savebytes[x] + ",";
            }

            //saves the string to the file
            writer.println(saveString + savebytes[savebytes.length - 1]);

            writer.close(); 
        }
        catch(Exception exc){
            exc.printStackTrace();
        }
    }

    /**
     * 
     * Encrypts the string passed to it and returns it in byte[] form
     * 
     * @param input		non-encrypted input string
     * @return			returns encrypted byte array
     */
    public byte[] encrypt(String input)
    {
    	//This is all from the Bouncy Castle api to encrypt info
        byte[] textEncrypted = null;
        try
        {
            desCipher.init(Cipher.ENCRYPT_MODE, myDesKey);
            textEncrypted = desCipher.doFinal(input.getBytes());
        }
        catch(InvalidKeyException | IllegalBlockSizeException | BadPaddingException e)
        {
            e.printStackTrace();
        }
        return textEncrypted;
    }

    /**
     * 
     * Decrypts encrypted byte[] and returns decrypted byte []
     * 
     * 
     * @param encryptedText		encrypted text passed in to be decrypted
     * @return					decrypted byte[]
     * @throws IllegalBlockSizeException	both due to the decrypting process
     * @throws BadPaddingException
     */
    public byte[] decrypt(byte[] encryptedText) throws IllegalBlockSizeException, BadPaddingException
    {
    	//This is all from the Bouncy Castle api to decrypt info
        try
        {
            desCipher.init(Cipher.DECRYPT_MODE, myDesKey);
        }
        catch (InvalidKeyException e)
        {
            e.printStackTrace();
        }
        return desCipher.doFinal(encryptedText);
    }
}
