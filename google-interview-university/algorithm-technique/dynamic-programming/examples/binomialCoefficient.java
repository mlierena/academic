import java.util.Scanner;

class binomialCoefficient {
    private static long[][] bin = new long[1000][1000];

    public static void main(String args[]) {
        Scanner s = new Scanner(System.in);
        int m = s.nextInt();
        int n = s.nextInt();

        System.out.println(solve(m, n));
        return;
    }

    private static long solve(int m, int n) {
        if(n == 0 || n == m) {
            return bin[m][n] = 1;
        }

        if(bin[m][n] > 0)
            return bin[m][n];
        else
            return bin[m][n] = solve(m-1, n) + solve(m-1, n-1);
    }
}