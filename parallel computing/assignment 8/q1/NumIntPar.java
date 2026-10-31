package q1;

public class NumIntPar {

    public static void main(String[] args) {

        long numSteps = 100000;
        double sum = 0.0;

        /*
         * parse command line
         * if (args.length != 1) {
         * System.out.println("arguments:  number_of_steps");
         * System.exit(1);
         * }
         * try {
         * numSteps = Long.parseLong(args[0]);
         * } catch (NumberFormatException e) {
         * System.out.println("argument "+ args[0] +" must be long int");
         * System.exit(1);
         * }
         */

        /* start timing */
        long startTime = System.currentTimeMillis();

        // προσθετω το limit και το step στα tasks και ξεκιναω το thread
        int limit = 1000;
        double step = 1.0 / (double) numSteps;

        Recursive mytask = new Recursive(0, numSteps, limit, step);
        Thread mythread = new Thread(mytask);
        mythread.start();

        // το global sum ειναι τελικα το sum του πρωτου task οποτε χρειαζομαι μονο αυτο
        try {
            mythread.join();
            sum = mytask.myResult;
        } catch (InterruptedException e) {
        }

        double pi = sum * step;

        /* end timing and print result */
        long endTime = System.currentTimeMillis();
        System.out.printf("sequential program results with %d steps\n", numSteps);
        System.out.printf("computed pi = %22.20f\n", pi);
        System.out.printf("difference between estimated pi and Math.PI = %22.20f\n",
                Math.abs(pi - Math.PI));
        System.out.printf("time to compute = %f seconds\n", (double) (endTime -
                startTime) / 1000);
    }
}

class Recursive implements Runnable {

    private long myFrom;
    private long myTo;
    private int myLimit;
    public double myResult;
    private double step;

    public Recursive(long from, long to, int limit, double step) {
        this.myFrom = from;
        this.myTo = to;
        this.myLimit = limit;
        this.myResult = 0.0;
        this.step = step;
    }

    // δουλευω παντα με το τοπικο myResult
    // το myResult του task0 ειναι το τελικο αποτελεσμα
    // αρα αρκει να επιστρεψω το myResult στην main
    public void run() {
        System.out.println("In thread" + Thread.currentThread().getName());
        long workload = myTo - myFrom;
        if (workload <= myLimit) {
            for (long i = myFrom; i < myTo; ++i) {
                double x = ((double) i + 0.5) * step;
                myResult += 4.0 / (1.0 + x * x);
            }
        } else {
            long mid = myFrom + workload / 2;

            Recursive taskL = new Recursive(myFrom, mid, myLimit, step);
            Thread threadL = new Thread(taskL);
            threadL.start();

            Recursive taskR = new Recursive(mid, myTo, myLimit, step);
            taskR.run();

            // εδω για να ολοκληρωθει το task του n επιπεδου περιμενει να τελειωσουν τα
            // tasks του n+1 επιπεδου για τα παρει τα αποτελεσματα τους
            // οσο πλησιαζουμε στην κορυφη τα αποτελεσματα καθε task συσσωρευονται
            // αρα αρκει να γυρισουμε το αποτελεσμα του task0
            try {
                threadL.join();
                myResult = taskL.myResult + taskR.myResult;
            } catch (InterruptedException e) {
            }
        }
    }

}
