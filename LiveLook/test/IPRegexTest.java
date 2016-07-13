/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author jschreiv
 */
public class IPRegexTest {
    
    public IPRegexTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }



    
    @Test
    public void testRegex() {}
    {
        String ipregex = "([01]?\\d\\d?|2([0-4]\\d|5[0-5]))(\\.([01]?\\d\\d?|2([0-4]\\d|5[0-5]))){3}";
        for (int i = 0; i < 255; i++)
        {
            for (int j = 0; j < 255; j++)
            {
                for (int x = 0; x < 255; x++)
                {
                    String ip = i+"."+x+"."+j+"."+x;
                    assertTrue(ip,ip.matches(ipregex));
                }
            }
        System.out.println(i);
        }
        
        String ipf = "J.s.f.w";
        
        assertFalse(ipf,ipf.matches(ipregex));
        for (int i = 0; i < 255; i++)
        {
            for (int j = 0; j < 255; j++)
            {
                String x = "256";
                String ip = i+"."+x+"."+j+"."+x;
                assertFalse(ip,ip.matches(ipregex));
                ip = x+"."+i+"."+j+"."+i;
                assertFalse(ip,ip.matches(ipregex));
                ip = j+"."+i+"."+j+"."+x;
                assertFalse(ip,ip.matches(ipregex));
                x = "300";
                ip = i+"."+x+"."+j+"."+x;
                assertFalse(ip,ip.matches(ipregex));
                ip = x+"."+i+"."+j+"."+i;
                assertFalse(ip,ip.matches(ipregex));
                ip = j+"."+i+"."+j+"."+x;
                assertFalse(ip,ip.matches(ipregex));    
                x = "260";
                ip = i+"."+x+"."+j+"."+x;
                assertFalse(ip,ip.matches(ipregex));
                ip = x+"."+i+"."+j+"."+i;
                assertFalse(ip,ip.matches(ipregex));
                ip = j+"."+i+"."+j+"."+x;
                assertFalse(ip,ip.matches(ipregex));     
                x = "301";
                ip = i+"."+x+"."+j+"."+x;
                assertFalse(ip,ip.matches(ipregex));
                ip = x+"."+i+"."+j+"."+i;
                assertFalse(ip,ip.matches(ipregex));
                ip = j+"."+i+"."+j+"."+x;
                assertFalse(ip,ip.matches(ipregex));    
                x = "261";
                ip = i+"."+x+"."+j+"."+x;
                assertFalse(ip,ip.matches(ipregex));
                ip = x+"."+i+"."+j+"."+i;
                assertFalse(ip,ip.matches(ipregex));
                ip = j+"."+i+"."+j+"."+x;
                assertFalse(ip,ip.matches(ipregex));      
                x = "";
                ip = i+"."+x+"."+j+"."+x;
                assertFalse(ip,ip.matches(ipregex));
                ip = x+"."+i+"."+j+"."+i;
                assertFalse(ip,ip.matches(ipregex));
                ip = j+"."+i+"."+j+"."+x;
                assertFalse(ip,ip.matches(ipregex)); 
                x = "a";
                ip = i+"."+x+"."+j+"."+x;
                assertFalse(ip,ip.matches(ipregex));
                ip = x+"."+i+"."+j+"."+i;
                assertFalse(ip,ip.matches(ipregex));
                ip = j+"."+i+"."+j+"."+x;
                assertFalse(ip,ip.matches(ipregex));         
                
            }
        System.out.println(i);
        }
        
    }
}
