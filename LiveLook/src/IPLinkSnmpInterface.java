/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package intraplex.livelook;

import java.net.InetAddress;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;
import snmp.SNMPIPAddress;
import snmp.SNMPInteger;
import snmp.SNMPObject;
import snmp.SNMPObjectIdentifier;
import snmp.SNMPOctetString;
import snmp.SNMPSequence;
import snmp.SNMPVarBindList;
import snmp.SNMPv1CommunicationInterface;

/**
 *
 * @author jschreiv
 */
public class IPLinkSnmpInterface {
    
    SNMPv1CommunicationInterface comInterface;
    
    public IPLinkSnmpInterface()
    {
        
    }
    
    protected void finalize( ) throws Throwable {
        if (comInterface != null)
        {
            comInterface.closeConnection();
        }
        super.finalize();
    }
    
    public void close()
    {
        try {
            comInterface.closeConnection();
        } catch (SocketException ex) {
            Logger.getLogger(IPLinkSnmpInterface.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean open(String ipAddress, String community)
    {
        try
        {
            InetAddress hostAddress = InetAddress.getByName(ipAddress);
            //String community = "public";
            int version = 0;    // SNMPv1

            comInterface = new SNMPv1CommunicationInterface(version, hostAddress, community);
            return true;
        }
        catch (Exception e){}
        return false;
    }
    
    public String getSnmp(String itemID, int index)
    {
        try{
             itemID = itemID+"."+index;

             
            // the getMIBEntry method of the communications interface returns an SNMPVarBindList
            // object; this is essentially a Vector of SNMP (OID,value) pairs. In this case, the
            // returned Vector has just one pair inside it.
            SNMPVarBindList newVars = comInterface.getMIBEntry(itemID);

            // extract the (OID,value) pair from the SNMPVarBindList; the pair is just a two-element
            // SNMPSequence
            SNMPSequence pair = (SNMPSequence)(newVars.getSNMPObjectAt(0));

            // extract the object identifier from the pair; it's the first element in the sequence
            SNMPObjectIdentifier snmpOID = (SNMPObjectIdentifier)pair.getSNMPObjectAt(0);

            // extract the corresponding value from the pair; it's the second element in the sequence
            SNMPObject snmpValue = pair.getSNMPObjectAt(1);

            // print out the String representation of the retrieved value
            return snmpValue.toString();
	    }
	    catch (Exception e)
	    {
	
	    }
        return "";
    }
    
    public byte[] getSnmp(String itemID, int index, boolean blah)
    {
        try{
             itemID = itemID+"."+index;

             
            // the getMIBEntry method of the communications interface returns an SNMPVarBindList
            // object; this is essentially a Vector of SNMP (OID,value) pairs. In this case, the
            // returned Vector has just one pair inside it.
            SNMPVarBindList newVars = comInterface.getMIBEntry(itemID);

            // extract the (OID,value) pair from the SNMPVarBindList; the pair is just a two-element
            // SNMPSequence
            SNMPSequence pair = (SNMPSequence)(newVars.getSNMPObjectAt(0));

            // extract the object identifier from the pair; it's the first element in the sequence
            SNMPObjectIdentifier snmpOID = (SNMPObjectIdentifier)pair.getSNMPObjectAt(0);

            // extract the corresponding value from the pair; it's the second element in the sequence
            SNMPObject snmpValue = pair.getSNMPObjectAt(1);

            // print out the String representation of the retrieved value
            return (byte[])snmpValue.getValue();
	    }
	    catch (Exception e)
	    {
	
	    }
        return new byte[0];
    }
    
    //For tables with 2 indexes
    public String getSnmp(String itemID, int index, int secondIndex)
    {
        try{
             itemID = itemID + "." + index + "." + secondIndex;

             
            // the getMIBEntry method of the communications interface returns an SNMPVarBindList
            // object; this is essentially a Vector of SNMP (OID,value) pairs. In this case, the
            // returned Vector has just one pair inside it.
            SNMPVarBindList newVars = comInterface.getMIBEntry(itemID);

            // extract the (OID,value) pair from the SNMPVarBindList; the pair is just a two-element
            // SNMPSequence
            SNMPSequence pair = (SNMPSequence)(newVars.getSNMPObjectAt(0));

            // extract the object identifier from the pair; it's the first element in the sequence
            SNMPObjectIdentifier snmpOID = (SNMPObjectIdentifier)pair.getSNMPObjectAt(0);

            // extract the corresponding value from the pair; it's the second element in the sequence
            SNMPObject snmpValue = pair.getSNMPObjectAt(1);

            // print out the String representation of the retrieved value
            return snmpValue.toString();
	    }
	    catch (Exception e)
	    {
	
	    }
        return "";
    }
        
    public boolean setSnmpNumber( String itemID, int index, int value)
    {
            itemID = itemID+"."+index;

            try
            {

                SNMPInteger newValue = new SNMPInteger(value);
                comInterface.setMIBEntry(itemID, newValue);
                return true;
            }
            catch(Exception e)
            {
                return false;    
            }

    }
           
        
    public void sleep(int ms)
    {
        try 
        {
        Thread.sleep(ms);
        } 
        catch (InterruptedException ex) 
        {
        }
    }

    public String convertMacsFromByte(byte[] data) {
    	String returnString = new String();
        int convert = data[0];
         if (convert < 0){
               convert += 256;
         }
        returnString += ((convert != 0)?String.format("%02X", convert):"00");
        //returnString += ((convert != 0)?Integer.toHexString(convert):"00");
                
        for (int i = 1; i < data.length; i++)
        {
            convert = data[i];
            if (convert < 0)
                convert += 256;
           
           returnString +=":" + ((convert != 0)?String.format("%02X", convert):"00"); // changed from Hex String to String Format API to get the hex in two digit format   
        }
        
        
        return returnString;
    }
}
