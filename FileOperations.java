import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class FileOperations {

  public static boolean readMatrix(String fileName, int[][] matrixA, int[][] matrixB, int size) {
    try (Scanner scanner = new Scanner(new File(fileName))) {
      for (int i = 0; i < size; i++) {
        for (int j = 0; j < size; j++) {
          if (scanner.hasNextInt()) {
            matrixA[i][j] = scanner.nextInt();
          }
        }
      }
      for (int i = 0; i < size; i++) {
        for (int j = 0; j < size; j++) {
          if (scanner.hasNextInt()) {
            matrixB[i][j] = scanner.nextInt();
          }
        }
      }
    } catch (FileNotFoundException e) {
      return false;
    }
    return true;
  }

  public static void writeMatrix(int[][] matrix, String fileName) {
    try (FileWriter writer = new FileWriter(fileName)) {
      for (int[] row : matrix) {
        for (int val : row) {
          writer.write(val + " ");
        }
        writer.write("\n");
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
