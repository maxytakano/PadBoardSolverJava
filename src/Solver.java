import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

import java.util.HashSet;
import java.util.Set;

public class Solver {
  Board targetPosition;
  private boolean allow8Way;

  public Solver(Board startPosition, Board targetPosition, boolean allow8Way) {
    // Initialize solve options
    this.allow8Way = allow8Way;

    // Set target
    this.targetPosition = targetPosition;

    // Initiate Solve
    this.solveBoard(startPosition);
  }

  private void solveBoard(Board startPosition) {
    MinPQ<Move> frontier = new MinPQ<>();
    frontier.insert(new Move(startPosition));
    Set<Board> explored = new HashSet<>();
    int dupeCount = 0;
    int processed = 1;

    while (!frontier.isEmpty()) {
      Move bestMove = frontier.delMin();
      if (explored.contains(bestMove.board)) {
        dupeCount++;
      }
      explored.add(bestMove.board);

      if (bestMove.board.isGoal(targetPosition)) {
        lastMove = bestMove;
        System.out.println("dupe count: " + dupeCount);
        int d = minMoves();
        int n = processed;
        System.out.println("Processed (N): " + processed);
        System.out.println("Depth of solution (D): " + d);
        System.out.println("Approximate branching factor (b*): " + Math.pow(n, (1.0/d)));
        System.out.println("PQ size: " + frontier.size());
        return;
      }

//      System.out.println("BEST WAS:" + bestMove.board);
//      System.out.println(allow8Way);
      for (Board neighbor : bestMove.board.neighbors(allow8Way)) {
//        System.out.print(neighbor);
        // neighbor not in frontier U explored
        boolean criticalCheck = (bestMove.previous == null || !neighbor.equals(bestMove.previous.board));

        // TODO: (!isBoardInPQ(frontier, neighbor) is SLOW can this be sped up?
//        boolean memoryOptimization = (!isBoardInPQ(frontier, neighbor) && !explored.contains(neighbor));
        boolean memoryOptimization = !explored.contains(neighbor);

        boolean finalCheck = criticalCheck && memoryOptimization;
//        boolean finalCheck = memoryOptimization;

        if (finalCheck) {
          processed++;
          frontier.insert(new Move(neighbor, bestMove));
        }
      }
    }
  }

  private class Move implements Comparable<Move> {
    private Move previous;
    private Board board;
    private int numMoves = 0;

    // Initial constructor
    public Move(Board board) {
      this.board = board;
    }

    // Expand constructor, previous used to construct solution path
    public Move(Board board, Move previous) {
      this.board = board;
      this.previous = previous;
      this.numMoves = previous.numMoves + 1;
    }

    /**
     * Priority = f(x) = h(x) + g(x) where h is a chosen heuristic and
     * g is the number of moves made.  When h is admissible, solution is proven optimal,
     * otherwise solution can be reached quicker but will not be optimal always.
     * @param move to compare
     * @return priority of a given move
     */
    @Override
    public int compareTo(Move move) {
//      return (this.board.manhattan() - move.board.manhattan()) + (this.numMoves - move.numMoves);
      int moveComponent = this.numMoves - move.numMoves;
      int hammingComponent = this.board.hamming(targetPosition) - move.board.hamming(targetPosition);
      // priority = f(x) = h(x) + g(x) where
      // Multiplying the
      return (hammingComponent) + (moveComponent);
    }
  }

  // Final move needed for the goal, assigned when the goal is reached.
  private Move lastMove;

  // Used for optimization, seems slow currently
  private boolean isBoardInPQ(MinPQ<Move> pq, Board board) {
    for (Move pqMove : pq) {
      if (pqMove.board == board) {
        return true;
      }
    }
    return false;
  }

  /**
   * @return boolean - true if board was solved
   */
  public boolean isSolvable() {
    return (lastMove != null);
  }

  /**
   * @return int - minimum number of moves to solve the board
   */
  public int minMoves() {
    return isSolvable() ? lastMove.numMoves : -1;
  }

  /**
   * @return Stack - stack of board states used in solution.
   */
  public Iterable<Board> solution() {
    if (!isSolvable()) return null;

    Stack<Board> moves = new Stack<>();
    while(lastMove != null) {
      moves.push(lastMove.board);
      lastMove = lastMove.previous;
    }

    return moves;
  }
}