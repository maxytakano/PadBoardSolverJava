import java.util.ArrayList;
import java.util.LinkedList;

public class Board {
  // Bitboards representing the start/current position
  private long s0 = 0, s1 = 0, s2 = 0, s3 = 0, s4 = 0, s5 = 0;
  // Bitboards representing the target position
  private static long t0 = 0, t1 = 0, t2 = 0, t3 = 0, t4 = 0, t5 = 0;
  private int movePosition;
  private static int width, height;
  private static int size; // size is the length of a bitboard (width * height)
  private final int HAMMING_INITIAL_VALUE = -999;
  private int hammingDistance = HAMMING_INITIAL_VALUE; // initialize to -999 to show it's uninitialized

  /*----------- Constructors -----------*/

  // Constructor used to setup initial board
  public Board(int[][] startPosition, int[][] targetPosition, int movePosition) {
    width = startPosition.length;
    height = startPosition[0].length;
    this.movePosition = movePosition;
    size = width * height;
    arrayToBitboards(startPosition);
  }

  // Internal use Constructor for board neighbor expansion
  public Board(long s0, long s1, long s2, long s3, long s4, long s5, int movePosition) {
    this.s0 = s0;
    this.s1 = s1;
    this.s2 = s2;
    this.s3 = s3;
    this.s4 = s4;
    this.s5 = s5;
    this.movePosition = movePosition;
  }

  /*----------- Bitboard methods -----------*/

  private void arrayToBitboards(int[][] inputArray) {
    long mask;
    for (int i = 0; i < size; i++) {
      mask = 1 << i;
      switch(inputArray[i/height][i%width]) {
        case 0:
          s0 |= mask;
          break;
        case 1:
          s1 |= mask;
          break;
        case 2:
          s2 |= mask;
          break;
        case 3:
          s3 |= mask;
          break;
        case 4:
          s3 |= mask;
          break;
        case 5:
          s3 |= mask;
          break;
      }
    }
  }

  /*----------- Core Overrides -----------*/

  // These overrides allow a priority queue to optimize which board
  // to explore next.

  /**
   * Calculates a unique hashcode to this board state.
   * @return int - unique hashcode to this board state
   */
  @Override
  public int hashCode() {
    // TODO: unclear if caching hashcode benefits performance
//    if (cachedHashCode != null) {
//      return cachedHashCode;
//    }
//
//    cachedHashCode = java.util.Arrays.deepHashCode(startPosition);
//    return cachedHashCode;

    // Effective java recipe for hash codes.
    int result = 42;
    result = 31 * result + (int) s0;
    result = 31 * result + (int) s1;
    result = 31 * result + (int) s2;
    result = 31 * result + (int) s3;
    result = 31 * result + (int) s4;
    result = 31 * result + (int) s5;
    return result;

  }

  /**
   * Checks for object equality and compares the start position.
   * @return boolean - true if board is equal to another
   */
  @Override
  public boolean equals(Object otherBoard) {
    // 1. Check if we are comparing against our self.
    if (this == otherBoard) return true;

    // TODO: Unclear if this is actually a shortcut
//    if (hashCode() != otherBoard.hashCode()) {
//      return false;
//    }

    // Commented out for performance, means Object must be Board class or we crash.
    // 2. Check if its a Board object.
//    if (otherBoard.getClass() != this.getClass()){
//      return false;
//    }

    // 3. Check if invalid board comparison
    // 3-1. Could compare board sizes here too, deferring for performance.
//    if (castedBoard == null) {
//      return false;
//    }

    // 5. Otherwise XOR all bitboards to determine board equality.
    Board castedBoard = (Board) otherBoard;
    long result = (s0 ^ castedBoard.s0) | (s1 ^ castedBoard.s1) | (s2 ^ castedBoard.s2) |
        (s3 ^ castedBoard.s3) | (s4 ^ castedBoard.s4) | (s5 ^ castedBoard.s5);

    return result == 0;
  }

  /*----------- Helper methods -----------*/

  /**
   * Generates linked list of possible neighbors based on moving up, down, left, or right.
   * @return LinkedList<Board> - linked list of neighbor board states
   */
  public Iterable<Board> neighbors() {
    // TODO: Compare List/array/etc. performance.
    LinkedList<Board> neighbors = new LinkedList<>();

    int moveRow = movePosition / height;
    int moveCol = movePosition % width;


    // Check if we can move left.
    if (moveCol > 0) {
      Board shiftLeft = swapCells(spaceX, spaceY, spaceX - 1, spaceY);
      neighbors.add(shiftLeft);
    }
    // Right
    if (moveCol < width - 1) {
      Board shiftRight = swapCells(spaceX, spaceY, spaceX + 1, spaceY);
      neighbors.add(shiftRight);
    }
    // Up
    if (moveRow < height - 1) {
      Board shiftUp = swapCells(spaceX, spaceY, spaceX, spaceY + 1);
      neighbors.add(shiftUp);
    }
    // Down
    if (moveRow > 0) {
      Board shiftDown = swapCells(spaceX, spaceY, spaceX, spaceY - 1);
      neighbors.add(shiftDown);
    }

    return neighbors;
  }

  /**
   * Takes two cell positions from startPosition and swaps them.
   * @return Board - board with the cell position swapped
   */
  private Board swapCells(int x1, int y1, int x2, int y2) {
    int[][] copy = copy2d(startPosition);
    int tmp = copy[x1][y1];
    copy[x1][y1] = copy[x2][y2];
    copy[x2][y2] = tmp;

    return new Board(copy);
  }

  public boolean isGoal() {
    return (hammingDistance == 0);
  }

  private boolean blockIsNotInPlace(int row, int col) {
    int block = startPosition[row][col];
    int targetBlock = targetPosition[row][col];

    return !isSpace(block) && (block != targetBlock);
  }





  /**
   * TODO: adapt m x n
   * TODO: refactor to support moving without a space cell
   * Finds where the space is ('0') on the board.
   * @return Tuple[x][y] - Space's location
   */
  private int[] getSpaceLocation() {
    for (int x = 0; x < startPosition.length; x++) {
      for (int y = 0; y < startPosition.length; y++) {
        if (isSpace(startPosition[x][y])) {
          int[] location = new int[2];
          location[0] = x;
          location[1] = y;
          return location;
        }
      }
    }

    throw new RuntimeException();
  }

  /**
   * TODO: adapt m x n
   * Returns board string for pretty print.
   * @return String - stringified board
   */
  public String toString() {
    StringBuilder str = new StringBuilder();
    str.append(size + "\n");
    for (int row = 0; row < startPosition.length; row++) {
      for (int col = 0; col < startPosition.length; col++) {
        str.append(String.format("%2d ", startPosition[row][col]));
      }
      str.append("\n");
    }

    return str.toString();
  }

  /**
   * TODO: adapt m x n
   * Copies passed in matrix.
   * @return Array[][] - new copied matrix.
   */
  private int[][] copy2d(int[][] matrix) {
    int[][] copy = new int[matrix.length][];
    for (int x = 0; x < matrix.length; x++) {
      int[] aMatrix = matrix[x];
      int   aLength = aMatrix.length;
      copy[x] = new int[aLength];
      System.arraycopy(aMatrix, 0, copy[x], 0, aLength);
    }

    return copy;
  }

  // ?? bugged?
//  private boolean blockIsInPlace(int row, int col) {
//    int block = startPosition[row][col];
//    int targetBlock = targetPosition[row][col];
//
//    return !isSpace(block) && block != goalFor(row, col);
//  }
//
//  private int goalFor(int row, int col) {
//    return row*size + col + 1;
//  }

  /************** Board solve algorithm methods **************/

  // TODO: refactor m x n
  public int hamming() {
    if (hammingDistance != HAMMING_INITIAL_VALUE) {
      return hammingDistance;
    }

    int count = 0;
    for (int x = 0; x < startPosition.length; x++)
      for (int y = 0; y < startPosition.length; y++)
        if (blockIsNotInPlace(x, y)) count++;

    hammingDistance = count;

    return hammingDistance;
  }

// TODO: refactor m x n
// TODO: (doesnt work currently)
  public int manhattan() {
    int sum = 0;
    for (int x = 0; x < startPosition.length; x++)
      for (int y = 0; y < startPosition.length; y++)
        sum += calculateDistances(x, y);

    return sum;
  }

  private int calculateDistances(int x, int y) {
    int cell = startPosition[x][y];

    return (isSpace(cell)) ? 0 : Math.abs(x - getRow(cell)) + Math.abs(y - col(cell));
  }

  /**
   * @return which row the given cell is on.
   */
  private int getRow (int cell) {
    return (cell - 1) / size;
  }

  /**
   * @return which col the given cell is on.
   */
  private int col (int cell) {
    return (cell - 1) % size;
  }

}