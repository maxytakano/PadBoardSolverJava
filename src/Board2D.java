import java.util.LinkedList;

public class Board2D {
  private static final int SPACE = 0;

  private int[][] startPosition;
  private static int[][] targetPosition; // TODO: why does this have to be static to work...
  private int size; // TODO: refactor m x n
  private Integer hammingDistance;
  private Integer cachedHashCode;

  /************** Constructors **************/

  // Constructor used to setup initial Board2D
  public Board2D(int[][] startPosition, int[][] targetPosition) {
    this.startPosition = copy2d(startPosition);
    this.targetPosition = copy2d(targetPosition);
    size = startPosition.length;
  }

  // Internal use Constructor for Board2D permutations only?
  public Board2D(int[][] startPosition) {
    this.startPosition = copy2d(startPosition);
    size = startPosition.length;
  }

  /************** Helper methods **************/

  private boolean blockIsNotInPlace(int row, int col) {
    int block = startPosition[row][col];
    int targetBlock = targetPosition[row][col];

    return !isSpace(block) && (block != targetBlock);
  }

  /**
   * @return boolean - true if block == 0 (SPACE)
   */
  private boolean isSpace(int cell) {
    return cell == SPACE;
  }

  public boolean isGoal() {
    return (hammingDistance != null && hammingDistance == 0);
  }

  /**
   * Calculates a unique hashcode to this Board2D state.
   * @return int - unique hashcode to this Board2D state
   */
  @Override
  public int hashCode() {
    if (cachedHashCode != null) {
      return cachedHashCode;
    }

    cachedHashCode = java.util.Arrays.deepHashCode(startPosition);
    return cachedHashCode;
  }

  /**
   * Checks for object equality and compares the start position.
   * @return boolean - true if Board2D is equal to another
   */
  @Override
  public boolean equals(Object otherBoard2D) {
    if (hashCode() != otherBoard2D.hashCode()) {
      return false;
    }

//    System.out.println("calling equals in Board2D");

    // 1. Check if its a Move object.
//    if (otherBoard2D.getClass() != this.getClass()){
//      return false;
//    }

    // 2. Check if we are comparing against our self.
    Board2D castedBoard2D = (Board2D) otherBoard2D;
    if (this == castedBoard2D) {
      return true;
    }

    // 3. Check if invalid Board2D comparison
    if (castedBoard2D==null ||
        castedBoard2D.startPosition.length != startPosition.length) {
      return false;
    }

    // 4. Otherwise compare Board2Ds with the other move.
    for (int x = 0; x < startPosition.length; x++) {
      for (int y = 0; y < startPosition.length; y++) {
        if (castedBoard2D.startPosition[x][y] != startPosition[x][y]) {
          return false;
        }
      }
    }

    return true;
  }

  /**
   * TODO: adapt m x n
   * Generates linked list of possible neighbors based on moving the SPACE up, down, left, or right.
   * @return LinkedList<Board2D> - linked list of neighbor Board2D states
   */
  public Iterable<Board2D> neighbors() {
    LinkedList<Board2D> neighbors = new LinkedList<>();

    int[] spaceLocation = getSpaceLocation();
    int spaceX = spaceLocation[0];
    int spaceY = spaceLocation[1];

    // Check if left of space is on Board2D.
    if (spaceX > 0) {
      Board2D shiftLeft = swapCells(spaceX, spaceY, spaceX - 1, spaceY);
      neighbors.add(shiftLeft);
    }
    // Right of space.
    if (spaceX < size - 1) {
      Board2D shiftRight = swapCells(spaceX, spaceY, spaceX + 1, spaceY);
      neighbors.add(shiftRight);
    }
    // Below the space.
    if (spaceY > 0) {
      Board2D shiftDown = swapCells(spaceX, spaceY, spaceX, spaceY - 1);
      neighbors.add(shiftDown);
    }
    // Above the space.
    if (spaceY < size - 1) {
      Board2D shiftUp = swapCells(spaceX, spaceY, spaceX, spaceY + 1);
      neighbors.add(shiftUp);
    }

    return neighbors;
  }

  /**
   * Takes two cell positions from startPosition and swaps them.
   * @return Board2D - Board2D with the cell position swapped
   */
  private Board2D swapCells(int x1, int y1, int x2, int y2) {
    int[][] copy = copy2d(startPosition);
    int tmp = copy[x1][y1];
    copy[x1][y1] = copy[x2][y2];
    copy[x2][y2] = tmp;

    return new Board2D(copy);
  }

  /**
   * TODO: adapt m x n
   * TODO: refactor to support moving without a space cell
   * Finds where the space is ('0') on the Board2D.
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
   * Returns Board2D string for pretty print.
   * @return String - stringified Board2D
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

  /************** Board2D solve algorithm methods **************/

  // TODO: refactor m x n
  public int hamming() {
    if (hammingDistance != null) {
      return hammingDistance;
    }

    int count = 0;
    for (int x = 0; x < startPosition.length; x++)
      for (int y = 0; y < startPosition.length; y++)
        if (blockIsNotInPlace(x, y)) count++;

    hammingDistance = new Integer(count);

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