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
        while(chopsticks[piTID%chopsticks.length] || chopsticks[piTID-1]){
            try {
                wait();
            }
            catch (InterruptedException e){
                System.err.println(e.getMessage());
                System.exit(1);
            }
        }
        chopsticks[piTID-1]=true;
        chopsticks[piTID%chopsticks.length]=true;
    }

    /**
     * When a given philosopher's done eating, they put the chopstiks/forks down
     * and let others know they are available.
     */
    public synchronized void putDown(final int piTID)
    {
        //this is to release the chopsticks. it does not need a check as it won't change anything if you release something that you don't have
        //we also notify all the philosophers that some chopsticks have been released, maybe one of them can grab them
        chopsticks[piTID%chopsticks.length]=false;
        chopsticks[piTID-1]=false;
        notifyAll();
    }

    /**
     * Only one philopher at a time is allowed to philosophy
     * (while she is not eating).
     */
    public synchronized void requestTalk()
    {
        //we only need to verify no one is already talking other than that the philosopher can talk.
        //If no one is talking we can allow the philosopher to speak and tell every one you will be speaking
        while (talking){
            try {
                wait();
            }
            catch (InterruptedException e){
                System.err.println(e.getMessage());
                System.exit(1);
            }
        }
        talking=true;
    }

    /**
     * When one philosopher is done talking stuff, others
     * can feel free to start talking.
     */
    public synchronized void endTalk()
    {
        //No need to check if someone is talking as we only need to tell everyone that we are done talking
        //and grant the permission to talk to others
        talking=false;
        notifyAll();
    }
}

// EOF
