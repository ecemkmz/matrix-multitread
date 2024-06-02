import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MainApp {
  private static int[][] matrixA;
  private static int[][] matrixB;
  private static int[][] matrixC;
  private static int matrixSize;
  private static int numThreads;

  public static void main(String[] args) {
    if (!validateArguments(args)) {
      System.out.println("Usage: java MainApp <matrix size> <number of threads> <input file (optional)>");
      return;
    }

    initializeMatrices(args);

    System.out.println("Matrix A:");
    MatrixUtils.displayMatrix(matrixA);

    System.out.println("Matrix B:");
    MatrixUtils.displayMatrix(matrixB);

    long startTime = System.nanoTime();
    executeMatrixMultiplication();
    long endTime = System.nanoTime();

    System.out.println("Matrix C:");
    MatrixUtils.displayMatrix(matrixC);

    System.out.println("Total processing time: " + (endTime - startTime) / 1000000 + " ms");

    FileOperations.writeMatrix(matrixC, "output.txt");
  }

  private static boolean validateArguments(String[] args) {
    if (args.length < 2) {
      return false;
    }
    try {
      matrixSize = Integer.parseInt(args[0]);
      numThreads = Integer.parseInt(args[1]);
    } catch (NumberFormatException e) {
      return false;
    }
    return true;
  }

  private static void initializeMatrices(String[] args) {
    matrixA = new int[matrixSize][matrixSize];
    matrixB = new int[matrixSize][matrixSize];
    matrixC = new int[matrixSize][matrixSize];

    if (args.length > 2) {
      String inputFile = args[2];
      if (!FileOperations.readMatrix(inputFile, matrixA, matrixB, matrixSize)) {
        System.out.println("Input file not found.");
        System.exit(1);
      }
    } else {
      MatrixUtils.randomMatrix(matrixA, matrixSize);
      MatrixUtils.randomMatrix(matrixB, matrixSize);
    }
  }

  private static void executeMatrixMultiplication() {
    ExecutorService executor = Executors.newFixedThreadPool(numThreads);

    for (int i = 0; i < matrixSize; i++) {
      final int row = i;
      executor.submit(() -> MatrixUtils.computeRowProduct(row, matrixA, matrixB, matrixC, matrixSize));
    }

    shutdownExecutor(executor);
  }

  private static void shutdownExecutor(ExecutorService executor) {
    executor.shutdown();
    try {
      if (!executor.awaitTermination(1, TimeUnit.MINUTES)) {
        executor.shutdownNow();
        if (!executor.awaitTermination(1, TimeUnit.MINUTES)) {
          System.err.println("Executor did not terminate");
        }
      }
    } catch (InterruptedException e) {
      executor.shutdownNow();
      Thread.currentThread().interrupt();
    }
  }
}
