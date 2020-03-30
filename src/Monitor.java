/**
 * Class Monitor
 * To synchronize dining philosophers.
 *
 * @author Serguei A. Mokhov, mokhov@cs.concordia.ca
 */
public class Monitor
{
    /*
     * ------------
     * Data members
     * ------------
     */
    //This array will keep track of the chopsticks in use.Therefore, false means they are available
    private boolean[] chopsticks;
    //this boolean represents someone talking at the moment. Therefore, false means no one is speaking.
    private boolean talking=false;
    /**
     * Constructor
     */
    public Monitor(int piNumberOfPhilosophers)
    {
        // TODO: set appropriate number of chopsticks based on the # of philosophers
        chopsticks=new boolean[piNumberOfPhilosophers];
        for (int i=0;i<piNumberOfPhilosophers;i++){
            chopsticks[i]=false;
        }
    }

    /*
     * -------------------------------
     * User-defined monitor procedures
     * -------------------------------
     */

    /**
     * Grants request (returns) to eat when both chopsticks/forks are available.
     * Else forces the philosopher to wait()
     */
    public synchronized void pickUp(final int piTID)
    {
        // ...
        if (piTID!=chopsticks.length && (!chopsticks[piTID-1] && !chopsticks[piTID])){
            chopsticks[piTID-1]=true;
            chopsticks[piTID]=true;
        }
        else if (!chopsticks[0] && !chopsticks[piTID-1]){
            chopsticks[0]=true;
            chopsticks[piTID-1]=true;
        }
        else{
            try {
                wait();
            }
            catch (InterruptedException e){
                System.err.println(e.getMessage());
                System.exit(1);
            }
        }
    }

    /**
     * When a given philosopher's done eating, they put the chopstiks/forks down
     * and let others know they are available.
     */
    public synchronized void putDown(final int piTID)
    {
        // ...
        if (piTID!=chopsticks.length && (chopsticks[piTID-1] && chopsticks[piTID])){
            chopsticks[piTID-1]=false;
            chopsticks[piTID]=false;
        }
        else if (chopsticks[0] && chopsticks[piTID-1]){
            chopsticks[0]=false;
            chopsticks[piTID-1]=false;
        }
        notifyAll();
    }

    /**
     * Only one philopher at a time is allowed to philosophy
     * (while she is not eating).
     */
    public synchronized void requestTalk()
    {
        // ...
        if (talking){
            try {
                wait();
            }
            catch (InterruptedException e){
                System.err.println(e.getMessage());
                System.exit(1);
            }
        }
        else {
            talking=true;
        }
    }

    /**
     * When one philosopher is done talking stuff, others
     * can feel free to start talking.
     */
    public synchronized void endTalk()
    {
        // ...
        talking=false;
    }
}

// EOF
