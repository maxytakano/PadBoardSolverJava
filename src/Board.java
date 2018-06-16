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
    height = startPosition.length;
    width = startPosition[0].length;
    this.movePosition = movePosition;
    size = width * height;
    startToBitboard(startPosition);
    targetToBitboard(targetPosition);
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

  /**
   * Sets up the start position bit boards using the startPosition Array.
   * Java does not support passing by reference and we are using ints for performance
   * instead of arrays, so we need a start and target function.
   */
  private void startToBitboard(int[][] inputArray) {
    long mask;
    int row, col;
    for (int i = 0; i < size; i++) {
      mask = 1 << i;
      row = i / width;
      col = i % width;
      switch(inputArray[row][col]) {
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
          s4 |= mask;
          break;
        case 5:
          s5 |= mask;
          break;
      }
    }
  }

  private void targetToBitboard(int[][] inputArray) {
    long mask;
    int row, col;
    for (int i = 0; i < size; i++) {
      mask = 1 << i;
      row = i / width;
      col = i % width;
      switch(inputArray[row][col]) {
        case 0:
          t0 |= mask;
          break;
        case 1:
          t1 |= mask;
          break;
        case 2:
          t2 |= mask;
          break;
        case 3:
          t3 |= mask;
          break;
        case 4:
          t4 |= mask;
          break;
        case 5:
          t5 |= mask;
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
      if (((s0>>i) & 1) == 1) buffer += String.format("%d", 0);
      if (((s1>>i) & 1) == 1) buffer += String.format("%d", 1);
      if (((s2>>i) & 1) == 1) buffer += String.format("%d", 2);
      if (((s3>>i) & 1) == 1) buffer += String.format("%d", 3);
      if (((s4>>i) & 1) == 1) buffer += String.format("%d", 4);
      if (((s5>>i) & 1) == 1) buffer += String.format("%d", 5);
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

  public boolean isGoal() {
    if (hammingDistance != HAMMING_INITIAL_VALUE) {
      return hamming() == 0;
    }
    return (hammingDistance == 0);
  }


  /*----------- Solver interface -----------*/

  // TODO: refactor m x n
  public int hamming() {
    // Return cached hamming if not equal to HAMMING_INITIAL_VALUE (-999)
    if (hammingDistance != HAMMING_INITIAL_VALUE) {
      return hammingDistance;
    }

    int distance = 0;
    long xor0 = s0 ^ t0;
    long xor1 = s1 ^ t1;
    long xor2 = s2 ^ t2;
    long xor3 = s3 ^ t3;
    long xor4 = s4 ^ t4;
    long xor5 = s5 ^ t5;

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
    long new0 = bitSwap(s0, p1, p2);
    long new1 = bitSwap(s1, p1, p2);
    long new2 = bitSwap(s2, p1, p2);
    long new3 = bitSwap(s3, p1, p2);
    long new4 = bitSwap(s4, p1, p2);
    long new5 = bitSwap(s5, p1, p2);
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