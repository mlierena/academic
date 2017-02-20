import java.util.Scanner;

public class fibonacci {
    private static long[] fib = new long[10001];
    
    public static void main(String args[])
    {
        Scanner s = new Scanner(System.in);
        int n = s.nextInt();
        fib[0] = 0;
        fib[1] = 1;

        System.out.println(solve(n));

        return;
    }

    private static long solve(int n)
    {
        if(n == 0 || n == 1 || fib[n] > 0)
            return fib[n];
        else
            return fib[n] = solve(n-1) + solve(n-2);
    }
}