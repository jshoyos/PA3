import common.BaseThread;

/**
 * Class Philosopher.
 * Outlines main subrutines of our virtual philosopher.
 *
 * @author Serguei A. Mokhov, mokhov@cs.concordia.ca
 */
public class Philosopher extends BaseThread
{
    /**
     * Max time an action can take (in milliseconds)
     */
    public static final long TIME_TO_WASTE = 1000;

    /**
     * The act of eating.
     * - Print the fact that a given phil (their TID) has started eating.
     * - yield
     * - Then sleep() for a random interval.
     * - yield
     * - The print that they are done eating.
     */
    public void eat()
    {
        try
        {
            //Very similar method  to the think method
            //This method only prints the intention to begin to eat
            //calls the eat method
            //informs that the philosopher is done eating
            System.out.println("Philosopher " + this.getTID() + " has started eating");
            Thread.yield();
            sleep((long)(Math.random() * TIME_TO_WASTE));
            Thread.yield();
            System.out.println("Philosopher " + this.getTID() + " is done eating");
        }
        catch(InterruptedException e)
        {
            System.err.println("Philosopher.eat():");
            DiningPhilosophers.reportException(e);
            System.exit(1);
        }
    }

    /**
     * The act of thinking.
     * - Print the fact that a given phil (their TID) has started thinking.
     * - yield
     * - Then sleep() for a random interval.
     * - yield
     * - The print that they are done thinking.
     */
    public void think()
    {
        //Only informs that the x philosopher will begin to talk and inform when he is done thinking
        //as well as simulating the speech
        try{

            System.out.println("Philosopher " + this.getTID() + " has started thinking");
            Thread.yield();
            Thread.sleep((long)(Math.random()*TIME_TO_WASTE));
            Thread.yield();
            System.out.println("Philosopher " + this.getTID() + " is done thinking");
        }
        catch (InterruptedException e){
            System.err.println("Philosopher.think():");
            DiningPhilosophers.reportException(e);
            System.exit(1);
        }
    }

    /**
     * The act of talking.
     * - Print the fact that a given phil (their TID) has started talking.
     * - yield
     * - Say something brilliant at random
     * - yield
     * - The print that they are done talking.
     */
    public void talk()
    {
        // we are simulating the talk. This method only calls the method to randomize the speech, and informs that someone will speak and/or is done speaking
        System.out.println("Philosopher " + this.getTID() + " has started saying something");
        Thread.yield();
        saySomething();
        Thread.yield();
        System.out.println("Philosopher " + this.getTID() + " is done saying something");
    }

    /**
     * No, this is not the act of running, just the overridden Thread.run()
     */
    public void run()
    {
        for(int i = 0; i < DiningPhilosophers.DINING_STEPS; i++)
        {
            DiningPhilosophers.soMonitor.pickUp(getTID());

            eat();

            DiningPhilosophers.soMonitor.putDown(getTID());

            think();

            /*
            The philosopher will speak if the random number selected is an even number. This makes the speaking random
             */
            int randomNumber=(int)(Math.random()*10);
            if(randomNumber%2==0)
            {
                DiningPhilosophers.soMonitor.requestTalk();
                talk();
                DiningPhilosophers.soMonitor.endTalk();
            }

            yield();
        }
    } // run()

    /**
     * Prints out a phrase from the array of phrases at random.
     * Feel free to add your own phrases.
     */
    public void saySomething()
    {
        String[] astrPhrases =
                {
                        "Eh, it's not easy to be a philosopher: eat, think, talk, eat...",
                        "You know, true is false and false is true if you think of it",
                        "2 + 2 = 5 for extremely large values of 2...",
                        "If thee cannot speak, thee must be silent",
                        "My number is " + getTID() + ""
                };

        System.out.println
                (
                        "Philosopher " + getTID() + " says: " +
                                astrPhrases[(int)(Math.random() * astrPhrases.length)]
                );
    }
}

// EOF
