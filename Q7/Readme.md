# Assignment 1 : Question 7
|Std ID|Name|
|------|-|
|K22_4078|Abdul Rehman Nazeer|
|K22_8739|Ahmed Muneeb|

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



**EXPLANATION**

In this code I first made isSafe function which checks if the row of the current column is empty or not.Then it checks for availability 
of the upward left direction and right direction.Then the nqueen function code is a recursive function nQueen which attempts to place queens on an n x nchessboard represented by the 2D array arr. It iterates through each column in the current row, checking if it's safe to place a queen atthat position using the isSafe method. If safe, it places a queen at that position, recurses to the next row, and repeats the process. If successful placement is not possible, it backtracks by resetting the position and continues with the next column. The function returnstrue if all queens are successfully placed, otherwise false.

I intentionally did not add the condition to randomly place the queen intially because it kept produing inconsistency in the output.
