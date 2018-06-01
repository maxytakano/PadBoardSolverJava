import edu.princeton.cs.algs4.*;
import java.util.Arrays;

public class TestClient {
  public static void main(String[] args) {
    // Create initial board from file
    In in = new In(args[0]);
    int size = in.readInt();
    int[] startCounts = new int[20];
    int[] endCounts = new int[20];
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
        endCounts[nextCell]++;
        endPosition[row][col] = nextCell;
      }
    }

    boolean boardValid = Arrays.equals(startCounts, endCounts);
    System.out.println("Board valid: " + boardValid);

    long startTime = System.currentTimeMillis();
    System.out.println("start solve");

    // Create board with start and end positions
    Board board = new Board(startPosition, endPosition);

    // Solve the board
    Solver solver = new Solver(board);

    if (solver.isSolvable()) {
      StdOut.println("Minimum number of moves = " + solver.minMoves());
      long endTime = System.currentTimeMillis();
      long duration = (endTime - startTime);
      System.out.println("Time taken: " + duration);
      for (Board b : solver.solution()) {
        StdOut.println(b);
      }
    } else {
      StdOut.println("No solution possible");
    }

  }
}