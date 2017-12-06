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
//      System.out.println("current score : " + this.board.hamming());
        return score;
    }
  }

  private Move lastMove;

  private Move expand(MinPQ<Move> moves) {
    if(moves.isEmpty()) return null;
    Move bestMove = moves.delMin();
    if (bestMove.board.isGoal()) {
      System.out.println("is Goal was true");
      return bestMove;
    }
    for (Board neighbor : bestMove.board.neighbors()) {
      if (bestMove.previous == null || !neighbor.equals(bestMove.previous.board)) {
        if (neighbor.hamming() < 3) {
          System.out.println(neighbor + " assd " + neighbor.hamming());
        }
        moves.insert(new Move(neighbor, bestMove));
      }
    }
    return null;
  }

  public Solver(Board initial) {
    MinPQ<Move> moves = new MinPQ<Move>();
    moves.insert(new Move(initial));

//    MinPQ<Move> twinMoves = new MinPQ<Move>();
//    twinMoves.insert(new Move(initial.twin()));

    while(true) {
      lastMove = expand(moves);
      if (lastMove != null) {
        return;
      }
//      if (lastMove != null || expand(twinMoves) != null) return;
    }
  }

  public boolean isSolvable() {
    return (lastMove != null);
  }

  public int moves() {
    return isSolvable() ? lastMove.numMoves : -1;
  }

  public Iterable<Board> solution() {
    if (!isSolvable()) return null;

    Stack<Board> moves = new Stack<Board>();
    while(lastMove != null) {
      moves.push(lastMove.board);
      lastMove = lastMove.previous;
    }

    return moves;
  }
}