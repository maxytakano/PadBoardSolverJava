import edu.princeton.cs.algs4.*;
import java.util.Arrays;
import java.io.File;

public class TestClient {
  private static void solveBoard(File f) {
    // Create initial board from file
    In in = new In(f);
    int size = in.readInt();
    int[] startCounts = new int[20];
//    int[] endCounts = new int[20];
    int nextCell;

    // Read starting board position
    int[][] startPosition = new int[size][size];
    for (int row = 0; row < size; row++) {
      for (int col = 0; col < size; col++) {
        nextCell = in.readInt();
        startCounts[nextCell]++;
        startPosition[row][col] = nextCell;
      }
    }

    // Read end board position
    int[][] endPosition = new int[size][size];
    for (int row = 0; row < size; row++) {
      for (int col = 0; col < size; col++) {
        nextCell = in.readInt();
//        endCounts[nextCell]++;
        endPosition[row][col] = nextCell;
      }
    }

//    boolean boardValid = Arrays.equals(startCounts, endCounts);
//    System.out.println("Board valid: " + boardValid);

    long startTime = System.currentTimeMillis();
    System.out.println("Solving " + f.getName() + " . . .");

    // Create board with start and end positions
    Board board = new Board(startPosition, endPosition);

    // Solve the board
    Solver solver = new Solver(board);

    if (solver.isSolvable()) {
      long endTime = System.currentTimeMillis();
      long duration = (endTime - startTime);
      StdOut.println("Min moves: " + solver.minMoves());
      StdOut.println("Time taken: " + duration);

      // print the board out.
//      for (Board b : solver.solution()) {
//        StdOut.println(b);
//      }
    } else {
      StdOut.println("No solution possible");
    }
  }

  public static void main(String[] args) {
    String target_dir = "./testPuzzles/solvable";
    File dir = new File(target_dir);
    File[] files = dir.listFiles();

    for (File f : files) {
      if(f.isFile()) {
        solveBoard(f);
      }
    }
  }
}