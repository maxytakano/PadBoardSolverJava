import edu.princeton.cs.algs4.*;
import java.io.File;

public class TestClient {
  private static void solveBoard(File f) {
    // Create initial board from file
    In in = new In(f);

    // Row major matrices
    int rows = in.readInt();
    int cols = in.readInt();
    int movePosition = in.readInt();

    // Read starting board position
    int[][] startPosition = new int[rows][cols];
    for (int row = 0; row < rows; row++) { // Vertical
      for (int col = 0; col < cols; col++) { // Horizontal
        startPosition[row][col] = in.readInt();
      }
    }

    // Read end board position
    int[][] endPosition = new int[rows][cols];
    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < cols; col++) {
        endPosition[row][col] = in.readInt();
      }
    }

    long startTime = System.currentTimeMillis();
    System.out.println("Solving " + f.getName() + " . . .");

    // Create board with start and end positions
    Board board = new Board(startPosition, endPosition, movePosition);

    // Solve the board
    Solver solver = new Solver(board);

    if (solver.isSolvable()) {
      long endTime = System.currentTimeMillis();
      long duration = (endTime - startTime);
      StdOut.println("Min moves: " + solver.minMoves());
      StdOut.println("Time taken: " + duration);

      // print the board out.
      for (Board b : solver.solution()) {
        StdOut.println(b);
      }
    } else {
      StdOut.println("No solution possible");
    }
  }

  public static void main(String[] args) {
    String target_dir = "./testPuzzles/newBoards";
    File dir = new File(target_dir);
    File[] files = dir.listFiles();

    for (File f : files) {
      if(f.isFile()) {
        solveBoard(f);
      }
    }
  }
}