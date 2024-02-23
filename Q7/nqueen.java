import java.util.Scanner;

public class nqueen {

    static boolean isSafe(int[][] arr, int row, int col, int n)
    {
        // Check each row for the given column
        for (int i = 0; i < row; i++) {
            if (arr[i][col] == 1) {
                return false;
            }
        }

        // Check upward-left direction
        for (int i = row, j = col; i >= 0 && j >= 0; i--, j--)
        {
            if (arr[i][j] == 1) {
                return false;
            }
        }

        // Check upward-right direction
        for (int i = row, j = col; i >= 0 && j < n; i--, j++)
        {
            if (arr[i][j] == 1) {
                return false;
            }
        }

        return true;
    }

    static boolean nQueen(int[][] arr, int row, int n) {
        if (row >= n) {
            return true;
        }

        for (int col = 0; col < n; col++) {
            if (isSafe(arr, row, col, n)) {
                arr[row][col] = 1;
                if (nQueen(arr, row + 1, n)) {
                    return true;
                }
                arr[row][col] = 0; // Backtracking
            }
        }
        return false;
    }

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.println("Enter number of Queens");
        int n = s.nextInt();

        if(n<4|n>8){
            System.out.println("condition false \n queens must be b/w 4 to 8");
            return;
        }

        int[][] arr = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                arr[i][j] = 0;
            }
        }

        if (nQueen(arr, 0, n)) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    System.out.print(arr[i][j] + " ");
                }
                System.out.println();
            }
        }
    }
}