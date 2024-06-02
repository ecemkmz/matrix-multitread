import java.util.Random;

public class MatrixUtils {

  public static void randomMatrix(int[][] matrix, int size) {
    Random random = new Random();
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        matrix[i][j] = random.nextInt(10);
      }
    }
  }

  public static void computeRowProduct(int row, int[][] matrixA, int[][] matrixB, int[][] matrixC, int size) {
    long threadStartTime = System.nanoTime();
    for (int j = 0; j < size; j++) {
      for (int k = 0; k < size; k++) {
        matrixC[row][j] += matrixA[row][k] * matrixB[k][j];
      }
    }
    long threadEndTime = System.nanoTime();
    System.out
        .println("Thread processing time for row " + row + ": " + (threadEndTime - threadStartTime) / 1000 + " µs");
  }

  public static void displayMatrix(int[][] matrix) {
    for (int[] row : matrix) {
      for (int val : row) {
        System.out.print(val + " ");
      }
      System.out.println();
    }
  }
}
