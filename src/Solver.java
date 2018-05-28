import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

public class Solver {
  private class Move implements Comparable<Move> {
    private Move previous;
    private Board board;
    private int numMoves = 0;

    public Move(Board board) {
      this.board = board;
    }

    public Move(Board board, Move previous) {
      this.board = board;
      this.previous = previous;
      this.numMoves = previous.numMoves + 1;
    }

    public int compareTo(Move move) {
//      return (this.board.manhattan() - move.board.manhattan()) + (this.numMoves - move.numMoves);
      int score = 1 * (this.board.hamming() - move.board.hamming()) + (this.numMoves - move.numMoves);
      return score;
    }
  }

  private Move lastMove;

  private Move expand(MinPQ<Move> moves) {
    if(moves.isEmpty()) return null;
    Move bestMove = moves.delMin();
    if (bestMove.board.isGoal()) {
      return bestMove;
    }
    for (Board neighbor : bestMove.board.neighbors()) {
      if (bestMove.previous == null || !neighbor.equals(bestMove.previous.board)) {
        if (neighbor.hamming() < 2) {
//          System.out.println(neighbor + " assd " + neighbor.hamming());
        }
        moves.insert(new Move(neighbor, bestMove));
      }
    }
    return null;
  }

  /**
   *
   * @param initial
   */
  public Solver(Board initial) {
    MinPQ<Move> moves = new MinPQ<>();
    moves.insert(new Move(initial));

    while(true) {
      lastMove = expand(moves);
      if (lastMove != null) {
        return;
      }
    }
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