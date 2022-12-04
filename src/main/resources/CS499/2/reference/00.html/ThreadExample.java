/*
 * ThreadExample - this is the main driver program that creates and starts a new thread of execution. It
 * waits for the thread to complete (joim) before continuing
 */

public class ThreadExample {

    public static void main(String[] args) {
        Slumber sobject = new Slumber(4); // create a runnable object  that will sleep for 4 seconds
        Thread  mt = new Thread(sobject);    // add this object to a thread and start the thread
        mt.start();

        System.out.println("Started the thread");
        // without the join, either thread can complete before the other
        try {
            mt.join();  // wait for my thread to complete
        } catch (Exception e) {
            // TO DO handle system error here
        }
        System.out.println("Main program exiting");
        System.exit(0);
    }
}
