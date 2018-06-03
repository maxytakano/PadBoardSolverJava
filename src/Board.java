import java.util.LinkedList;

public class Board {
  private static final int SPACE = 0;

  private int[][] startPosition;
  private static int[][] targetPosition; // TODO: why does this have to be static to work...
  private int size; // TODO: refactor m x n
  private Integer hammingDistance;

  /************** Constructors **************/

  // Constructor used to setup initial board
  public Board(int[][] startPosition, int[][] targetPosition) {
    this.startPosition = copy2d(startPosition);
    this.targetPosition = copy2d(targetPosition);
    size = startPosition.length;
  }

  // Internal use Constructor for board permutations only?
  public Board(int[][] startPosition) {
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
   * Calculates a unique hashcode to this board state.
   * @return int - unique hashcode to this board state
   */
  @Override
  public int hashCode() {
    int hash = java.util.Arrays.deepHashCode(startPosition);
    return hash;
  }

  /**
   * Checks for object equality and compares the start position.
   * @return boolean - true if board is equal to another
   */
  @Override
  public boolean equals(Object otherBoard) {
//    System.out.println("calling equals in board");

    // 1. Check if its a Move object.
//    if (otherBoard.getClass() != this.getClass()){
//      return false;
//    }

    // 2. Check if we are comparing against our self.
    Board castedBoard = (Board) otherBoard;
    if (this == castedBoard) {
      return true;
    }

    // 3. Check if invalid board comparison
    if (castedBoard==null ||
        castedBoard.startPosition.length != startPosition.length) {
      return false;
    }

    // 4. Otherwise compare boards with the other move.
    for (int x = 0; x < startPosition.length; x++) {
      for (int y = 0; y < startPosition.length; y++) {
        if (castedBoard.startPosition[x][y] != startPosition[x][y]) {
          return false;
        }
      }
    }

    return true;
  }
//
//  @Override
//  public int compareTo(Board otherBoard) {
//
//  }


  /**
   * TODO: adapt m x n
   * Generates linked list of possible neighbors based on moving the SPACE up, down, left, or right.
   * @return LinkedList<Board> - linked list of neighbor board states
   */
  public Iterable<Board> neighbors() {
    LinkedList<Board> neighbors = new LinkedList<>();

    int[] spaceLocation = getSpaceLocation();
    int spaceX = spaceLocation[0];
    int spaceY = spaceLocation[1];

    // Check if left of space is on board.
    if (spaceX > 0) {
      Board shiftLeft = swapCells(spaceX, spaceY, spaceX - 1, spaceY);
      neighbors.add(shiftLeft);
    }
    // Right of space.
    if (spaceX < size - 1) {
      Board shiftRight = swapCells(spaceX, spaceY, spaceX + 1, spaceY);
      neighbors.add(shiftRight);
    }
    // Below the space.
    if (spaceY > 0) {
      Board shiftDown = swapCells(spaceX, spaceY, spaceX, spaceY - 1);
      neighbors.add(shiftDown);
    }
    // Above the space.
    if (spaceY < size - 1) {
      Board shiftUp = swapCells(spaceX, spaceY, spaceX, spaceY + 1);
      neighbors.add(shiftUp);
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