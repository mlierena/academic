import java.util.Scanner;

class numOfFibonacciTree {
    private static long[] fibtree = new long[1000];

    public static void main(String[] args)
    {
        Scanner s = new Scanner(System.in);
        int n = s.nextInt();

        fibtree[0] = 1;
        fibtree[1] = 1;

        System.out.println(solve(n));
        return;
        
    }

    private static long solve(int n)
    {
        if(n==0 || n==1 || fibtree[n] > 0) {
            return fibtree[n];
        } else {
            return fibtree[n] = 1 + solve(n-1) + solve(n-2);
        }
    }
}