/**
 * Class DiningPhilosophers
 * The main starter.
 *
 * @author Serguei A. Mokhov, mokhov@cs.concordia.ca
 */
public class DiningPhilosophers
{
    /*
     * ------------
     * Data members
     * ------------
     */

    /**
     * This default may be overridden from the command line
     */
    public static final int DEFAULT_NUMBER_OF_PHILOSOPHERS = 4;

    /**
     * Dining "iterations" per philosopher thread
     * while they are socializing there
     */
    public static final int DINING_STEPS = 10;

    /**
     * Our shared monitor for the philosphers to consult
     */
    public static Monitor soMonitor = null;

    /*
     * -------
     * Methods
     * -------
     */

    /**
     * Main system starts up right here
     */
    public static void main(String[] argv)
    {
        try
        {
            int iPhilosophers = DEFAULT_NUMBER_OF_PHILOSOPHERS;

            // When no arguments are given it will take the default value of 4
            if (argv.length==0) {
                soMonitor = new Monitor(iPhilosophers);
            }
            else if (argv.length==1){
                //if there is one argument passed we verify its an integer first and then we check that its positive as well
                try{
                    int arg=Integer.parseInt(argv[0]);
                    if (arg<0){
                        System.err.println("\"" +argv[0]+"\" is not a positive decimal integer \n Usage: java DiningPhilosopher [NUMBER_OF_PHILOSOPHERS]");
                        System.exit(1);
                    }
                    else{
                        iPhilosophers=arg;
                        soMonitor=new Monitor(iPhilosophers);
                    }
                }
                catch (NumberFormatException e){
                    System.err.println("\"" +argv[0]+"\" is not a positive decimal integer \n Usage: java DiningPhilosopher [NUMBER_OF_PHILOSOPHERS]");
                    System.exit(1);
                }

            }
            else{
                //if there are more than one argument this is automatically wrong as we only expect one argument
                System.err.println("\"" +argv[0]+"\" is not a positive decimal integer \n Usage: java DiningPhilosopher [NUMBER_OF_PHILOSOPHERS]");
                System.exit(1);
            }
            // Space for all the philosophers
            Philosopher aoPhilosophers[] = new Philosopher[iPhilosophers];

            // Let 'em sit down
            for(int j = 0; j < iPhilosophers; j++)
            {
                aoPhilosophers[j] = new Philosopher();
                aoPhilosophers[j].start();
            }

            System.out.println
                    (
                            iPhilosophers +
                                    " philosopher(s) came in for a dinner."
                    );

            // Main waits for all its children to die...
            // I mean, philosophers to finish their dinner.
            for(int j = 0; j < iPhilosophers; j++)
                aoPhilosophers[j].join();

            System.out.println("All philosophers have left. System terminates normally.");
        }
        catch(InterruptedException e)
        {
            System.err.println("main():");
            reportException(e);
            System.exit(1);
        }
    } // main()

    /**
     * Outputs exception information to STDERR
     * @param poException Exception object to dump to STDERR
     */
    public static void reportException(Exception poException)
    {
        System.err.println("Caught exception : " + poException.getClass().getName());
        System.err.println("Message          : " + poException.getMessage());
        System.err.println("Stack Trace      : ");
        poException.printStackTrace(System.err);
    }
}

// EOF
