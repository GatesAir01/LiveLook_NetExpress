/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package intraplex.livelook;

/**
 *
 * @author jschreiv
 */
public interface ZoomListener {
    
    public void zoom(long s, long e);
    
    public void findFirst();
    
    public void findLast();
    
    public void findNext();
    
    public void findPrev();
    

}
