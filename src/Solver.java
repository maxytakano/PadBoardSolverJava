import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

import java.util.HashSet;
import java.util.Set;

public class Solver {
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

    @Override
    public int compareTo(Move move) {
//      return (this.board.manhattan() - move.board.manhattan()) + (this.numMoves - move.numMoves);
      return (this.board.hamming() - move.board.hamming()) + (this.numMoves - move.numMoves);
    }

    // TODO: actually not needed, compare boards directly
    /**
     * Compares against another move by comparing boards.
     * @param otherMove - other move
     * @return true if other move's board is the same.
     */
//    @Override
//    public boolean equals(Object otherMove) {
//      // 1. Check if its a Move object.
//      if (otherMove.getClass() != this.getClass()){
//        return false;
//      }
//
//      // 2. Check if we are comparing against our self.
//      Move castedMove = (Move) otherMove;
//      if (this == castedMove) {
//        return true;
//      }
//
//      // 3. Otherwise compare boards with the other move.
//      Board otherBoard = castedMove.board;
//
//      return this.board.equals(otherBoard);
//    }
  }

  // Final move needed for the goal, assigned when the goal is reached.
  private Move lastMove;

  private boolean isBoardInPQ(MinPQ<Move> pq, Board board) {
    for (Move pqMove : pq) {
      if (pqMove.board == board) {
        return true;
      }
    }
    return false;
  }

  /**
   *
   * @param initial
   */
  public Solver(Board initial) {
    MinPQ<Move> frontier = new MinPQ<>();
    frontier.insert(new Move(initial));
    Set<Board> explored = new HashSet<>();

    while (!frontier.isEmpty()) {
      Move bestMove = frontier.delMin();
      explored.add(bestMove.board);


      if (bestMove.board.isGoal()) {
        lastMove = bestMove;
        return;
      }

      for (Board neighbor : bestMove.board.neighbors()) {
        // neighbor not in frontier U explored
        boolean criticalCheck = (bestMove.previous == null || !neighbor.equals(bestMove.previous.board));

        // TODO: memory optimization is SLOW can this be sped up?
//        boolean memoryOptimization = (!isBoardInPQ(frontier, neighbor) && !explored.contains(neighbor));
        boolean memoryOptimization = !explored.contains(neighbor);

        boolean finalCheck = criticalCheck && memoryOptimization;
//        boolean finalCheck = memoryOptimization;

        if (finalCheck) {
          frontier.insert(new Move(neighbor, bestMove));
        }
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