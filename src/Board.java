import java.util.LinkedList;

public class Board {
  // Bitboards representing the start/current position
  public long bb0 = 0, bb1 = 0, bb2 = 0, bb3 = 0, bb4 = 0, bb5 = 0;
  private int movePosition;
  private static int width, height;
  private static int size; // size is the length of a bitboard (width * height)
  private final int HAMMING_INITIAL_VALUE = -999;
  private int hammingDistance = HAMMING_INITIAL_VALUE; // initialize to -999 to show it's uninitialized

  /*----------- Constructors -----------*/

  // Ctor used to setup initial board from input array
  public Board(int[][] startPosition, int movePosition) {
    height = startPosition.length;
    width = startPosition[0].length;
    this.movePosition = movePosition;
    size = width * height;
    startToBitboards(startPosition);
  }

  // Search expansion ctor built with N bitboards
  public Board(long bb0, long bb1, long bb2, long bb3, long bb4, long bb5, int movePosition) {
    this.bb0 = bb0;
    this.bb1 = bb1;
    this.bb2 = bb2;
    this.bb3 = bb3;
    this.bb4 = bb4;
    this.bb5 = bb5;
    this.movePosition = movePosition;
  }

  /*----------- Bitboard methods -----------*/

  /**
   * Sets up the start position bit boards using the startPosition Array.
   * Java does not support passing by reference and we are using ints for performance
   * instead of arrays, so we need a start and target function.
   */
  private void startToBitboards(int[][] inputArray) {
    long mask;
    int row, col;
    for (int i = 0; i < size; i++) {
      mask = 1 << i;
      row = i / width;
      col = i % width;
      switch(inputArray[row][col]) {
        case 0:
          bb0 |= mask;
          break;
        case 1:
          bb1 |= mask;
          break;
        case 2:
          bb2 |= mask;
          break;
        case 3:
          bb3 |= mask;
          break;
        case 4:
          bb4 |= mask;
          break;
        case 5:
          bb5 |= mask;
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
    result = 31 * result + (int) bb0;
    result = 31 * result + (int) bb1;
    result = 31 * result + (int) bb2;
    result = 31 * result + (int) bb3;
    result = 31 * result + (int) bb4;
    result = 31 * result + (int) bb5;
    result = 31 * result + movePosition;
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
    long result = (bb0 ^ castedBoard.bb0) | (bb1 ^ castedBoard.bb1) | (bb2 ^ castedBoard.bb2) |
        (bb3 ^ castedBoard.bb3) | (bb4 ^ castedBoard.bb4) | (bb5 ^ castedBoard.bb5);

    return (result == 0 && movePosition == castedBoard.movePosition);
  }

  /**
   * Returns board string.
   * @return String - stringified board
   */
  @Override
  public String toString() {
    StringBuilder str = new StringBuilder();
    str.append("~~~~~~~~~~" + "\n");

    String buffer;
    for (int i = 0; i < size; i++) {
      buffer = "";
      if (((bb0 >> i) & 1) == 1) buffer += String.format("%d", 0);
      if (((bb1 >> i) & 1) == 1) buffer += String.format("%d", 1);
      if (((bb2 >> i) & 1) == 1) buffer += String.format("%d", 2);
      if (((bb3 >> i) & 1) == 1) buffer += String.format("%d", 3);
      if (((bb4 >> i) & 1) == 1) buffer += String.format("%d", 4);
      if (((bb5 >> i) & 1) == 1) buffer += String.format("%d", 5);
      if (i == movePosition) {
        buffer += ")";
      } else {
        buffer += " ";
      }
      if (i > 0 && (i + 1) % width == 0) buffer += "\n";
      str.append(buffer);
    }

    return str.toString();
  }

  /*----------- Board interface -----------*/

  /**
   * Generates linked list of possible neighbors based on moving up, down, left, or right.
   * @return LinkedList<Board> - linked list of neighbor board states (order L, R, U D)
   */
  public Iterable<Board> neighbors() {
    // TODO: Compare List/array/etc. performance.
    LinkedList<Board> neighbors = new LinkedList<>();

    int moveRow = movePosition / width;
    int moveCol = movePosition % width;

    // Check if we can move left.
    if (moveCol > 0) {
      Board shiftLeft = swapCells(movePosition, movePosition - 1);
      neighbors.add(shiftLeft);
    }
    // Right
    if (moveCol < width - 1) {
      Board shiftRight = swapCells(movePosition, movePosition + 1);
      neighbors.add(shiftRight);
    }
    // Up
    if (moveRow > 0) {
      Board shiftUp = swapCells(movePosition, movePosition - width);
      neighbors.add(shiftUp);
    }
    // Down
    if (moveRow < height - 1) {
      Board shiftDown = swapCells(movePosition, movePosition + width);
      neighbors.add(shiftDown);
    }

    return neighbors;
  }

  public boolean isGoal(Board targetPosition) {
    if (hammingDistance == HAMMING_INITIAL_VALUE) {
      // Calculate hamming, since hammingDistance not initialized.
      return hamming(targetPosition) == 0;
    }

    // return cached hammingDistance
    return (hammingDistance == 0);
  }


  /*----------- Solver interface -----------*/

  public int hamming(Board otherBoard) {
    // Return cached hamming if not equal to HAMMING_INITIAL_VALUE (-999)
    if (hammingDistance != HAMMING_INITIAL_VALUE) {
      return hammingDistance;
    }

    int distance = 0;
    long xor0 = bb0 ^ otherBoard.bb0;
    long xor1 = bb1 ^ otherBoard.bb1;
    long xor2 = bb2 ^ otherBoard.bb2;
    long xor3 = bb3 ^ otherBoard.bb3;
    long xor4 = bb4 ^ otherBoard.bb4;
    long xor5 = bb5 ^ otherBoard.bb5;

    distance += countSetBits(xor0);
    distance += countSetBits(xor1);
    distance += countSetBits(xor2);
    distance += countSetBits(xor3);
    distance += countSetBits(xor4);
    distance += countSetBits(xor5);

    // Cache the hamming distance
    // Divide by 2 since setBits doubles distance
    hammingDistance = distance / 2;

    return hammingDistance;
  }

  // TODO: refactor m x n
// TODO: (doesnt work currently)
//  public int manhattan() {
//    int sum = 0;
//    for (int x = 0; x < startPosition.length; x++)
//      for (int y = 0; y < startPosition.length; y++)
//        sum += calculateDistances(x, y);
//
//    return sum;
//  }

  /*----------- private Board helpers -----------*/

  /**
   * Takes two cell positions from startPosition and swaps them.
   * @return Board - board with the cell position swapped
   */
  private Board swapCells(int p1, int p2) {
    long new0 = bitSwap(bb0, p1, p2);
    long new1 = bitSwap(bb1, p1, p2);
    long new2 = bitSwap(bb2, p1, p2);
    long new3 = bitSwap(bb3, p1, p2);
    long new4 = bitSwap(bb4, p1, p2);
    long new5 = bitSwap(bb5, p1, p2);
    // p2 is the new move position as it's where we are swapping to
    return new Board(new0, new1, new2, new3, new4, new5, p2);
  }

  /**
   * Counts number of 1 bits for a given long
   * @return int - number of bits set for the long
   */
  private int countSetBits(long n)
  {
    int count = 0;
    while (n != 0) {
      count += n & 1;
      n >>= 1;
    }
    return count;
  }

  /**
   * Swaps two bits in a long given their positions p1, p2
   * @return long - long with it's p1, p2 bits swapped
   */
  private long bitSwap(long i, int p1, int p2) {

    long bit1 = (i >> p1) & 1;// bit at p1
    long bit2 = (i >> p2) & 1;// bit at p2

    // bits same return original
    if (bit1 == bit2) return i;

    // bits different, swap with mask xor
    int mask = (1 << p1) | (1 << p2);

    return i ^ mask;
  }

  // used for manhattan possibly
//  private int calculateDistances(int x, int y) {
//    return 0;
//  }

}