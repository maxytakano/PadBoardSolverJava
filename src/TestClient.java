import edu.princeton.cs.algs4.*;

public class TestClient {
  public static void main(String[] args) {

    // create initial board from file
    In in = new In(args[0]);
    int n = in.readInt();
    int[][] blocks = new int[n][n];
    for (int i = 0; i < n; i++)
      for (int j = 0; j < n; j++)
        blocks[i][j] = in.readInt();

    int[][] target = new int[n][n];
    for (int i = 0; i < n; i++)
      for (int j = 0; j < n; j++)
        target[i][j] = in.readInt();

    // initial board from the file
    Board initial = new Board(blocks, target);

    // solve the puzzle
    Solver solver = new Solver(initial);

    // print solution to standard output
    if (!solver.isSolvable())
      StdOut.println("No solution possible");
    else {
      StdOut.println("Minimum number of moves = " + solver.moves());
      for (Board board : solver.solution())
        StdOut.println(board);
    }
  }
}