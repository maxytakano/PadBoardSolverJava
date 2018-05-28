import edu.princeton.cs.algs4.*;

public class TestClient {
  public static void main(String[] args) {
    // Create initial board from file
    In in = new In(args[0]);
    int size = in.readInt();

    // Read starting board position
    int[][] startPosition = new int[size][size];
    for (int row = 0; row < size; row++) {
      for (int col = 0; col < size; col++) {
        startPosition[row][col] = in.readInt();
      }
    }

    // Read end board position
    int[][] endPosition = new int[size][size];
    for (int row = 0; row < size; row++) {
      for (int col = 0; col < size; col++) {
        endPosition[row][col] = in.readInt();
      }
    }

    // Create board with start and end positions
    Board board = new Board(startPosition, endPosition);

    // Solve the board
    Solver solver = new Solver(board);

    if (solver.isSolvable()) {
      StdOut.println("Minimum number of moves = " + solver.minMoves());
      for (Board b : solver.solution()) {
        StdOut.println(b);
      }
    } else {
      StdOut.println("No solution possible");
    }

  }
}