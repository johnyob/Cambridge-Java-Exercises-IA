package uk.ac.cam.ajo41.prejava.ex4;

public class FibonacciCache {


    public static long[] fib = null;
   
    public static void store() throws Exception {
        if (fib == null) {
            throw new Exception("The fibonacci cache has not be initialised. Please use FibonacciCache.reset(n), where n is some positive integer.");
        }

        for (int i = 0; i < fib.length; i++) {
            fib[i] = (i == 0 || i == 1) ? 1L : fib[i - 1] + fib[i - 2];
        }
    }

    public static void reset(int cachesize) {
        fib = new long[cachesize];

        for (int i = 0; i < fib.length; i++) {
            fib[i] = 0L;
        }
    }

    public static long get(int i) throws Exception {
        if (fib == null) {
            throw new Exception("The fibonacci cache has not be initialised. Please use FibonacciCache.reset(n), where n is some positive integer.");
        }

        if (i >= fib.length || i < 0) {
            throw new Exception("The requested value is out of bounds of the fibonacci cache.");
        }

        return fib[i];
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("The fibonacci cache main method requires CLI arguments. No arguments have been provided.");
            return;
        }

        reset(20);
        try {
            store();            
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }

        try {
            int i = Integer.decode(args[0]);
            System.out.println(get(i));
        } catch (NumberFormatException e) {
            System.out.println("The argument " + args[0] + " was not in integer format. Please enter an integer argument");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
  
}