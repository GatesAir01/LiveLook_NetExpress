

package intraplex.livelook;

/** 
 * Provides Locks and UnLocks to protect objects from being accessed from
 * multiple threads at the same time.                         
 * 
 * @author Jacob Schreiver
 * 
 */
public class Mutex {
    private boolean locked;
    private Thread myThread;


        /** 
     * waits for the Mutex to be free then locks the Mutex and gives ownership 
     * to the thread requesting the lock                        
     * 
     */
    public synchronized boolean trylock() {
        
        //If Not Locked we can lock it
        if (!locked)
        {
          myThread = Thread.currentThread();
          locked = true;
        }
        return locked;
    }

    // use synchronized to prevent to threads from trying to lock at the same
    // time   

    /** 
     * waits for the Mutex to be free then locks the Mutex and gives ownership 
     * to the thread requesting the lock                        
     * 
     */
    public synchronized void lock() {
        
        //If Not Locked we can lock it
        if (!locked)
        {
          myThread = Thread.currentThread();
          locked = true;
        }
        //Wait for the Thread to become unlocked
        //Loop until we own the mutex
        while (myThread != Thread.currentThread())
        {
            
            if (!locked) {
                myThread = Thread.currentThread();
                locked = true;
            } else {
                //System.out.println(Thread.currentThread().getName() + " is waiting on " + myThread.getName());
                try {
                    wait(); //wait for another thread to call notify
                } catch (InterruptedException e) {
                    try {
                        wait(1000); //Sleep to Save CPU Cycles
                    } catch (InterruptedException ex) {
                        // Check to see if we are able to get the lock yet
                    }
                }
            }
        } 
    }

    /** 
     * unlocks the Mutex so that it can be locked by another thread                      
     */
    public synchronized void unlock() {
        if (Thread.currentThread() == myThread) {
        myThread = null;
        locked = false;
        notify(); // Let any thread waiting on us know we are done
        }
    }

}