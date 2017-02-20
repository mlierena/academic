import java.util.Scanner;

class numOfBCTree {
    private static long[][] bc = new long[1000][1000];

    public static void main(String args[]) {
        Scanner s = new Scanner(System.in);
        int n = s.nextInt();
        int m = s.nextInt();

        System.out.println(solve(n,m));
        return;
    }

    private static long solve(int n, int m) {
        if (m == 0 || m == n)
        {
            return bc[n][m] = 1;
        }

        if(bc[n][m] > 0)
        {
            return bc[n][m];
        } 
        else 
        {
            return bc[n][m] = 1 + solve(n-1, m) + solve(n-1, m-1); 
        }
    }
}