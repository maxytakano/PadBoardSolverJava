import java.util.LinkedList;

public class Board {
  private static final int SPACE = 0;

  private int[][] startPosition;
  private static int[][] targetPosition; // TODO: why does this have to be static to work...
  private int size; // TODO: refactor m x n

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

    return !isSpace(block) && (block != targetBlock) && (targetBlock != 99);
  }

  /**
   * @return boolean - true if block == 0 (SPACE)
   */
  private boolean isSpace(int block) {
    return block == SPACE;
  }

  public boolean isGoal() {
    for (int row = 0; row < startPosition.length; row++)
      for (int col = 0; col < startPosition.length; col++)
        if (blockIsNotInPlace(row, col)) {
          return false;
        }

    return true;
  }

  /**
   * ???
   * @return boolean - ???
   */
  public boolean equals(Board otherBoard) {
    // Check if comparing against self
    if (otherBoard==this) return true;
    // Check if invalid board comparison
    if (otherBoard==null ||
        otherBoard.startPosition.length != startPosition.length) {
      return false;
    }

    for (int x = 0; x < startPosition.length; x++) {
      for (int y = 0; y < startPosition.length; y++) {
        boolean condition1 = otherBoard.startPosition[x][y] != startPosition[x][y];
        if (condition1 && targetPosition[x][y] != 99) {
          return false;
        }
      }
    }

    return true;
  }

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
//    int block = blocks[row][col];
//    int targetBlock = target[row][col];
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
    int count = 0;
    for (int row = 0; row < startPosition.length; row++)
      for (int col = 0; col < startPosition.length; col++)
        if (blockIsNotInPlace(row, col)) count++;

    return count;
  }

//  // TODO: refactor m x n (doesnt work currently)
//  public int manhattan() {
//    int sum = 0;
//    for (int row = 0; row < blocks.length; row++)
//      for (int col = 0; col < blocks.length; col++)
//        sum += calculateDistances(row, col);
//
//    return sum;
//  }
//
//  private int calculateDistances(int row, int col) {
//    int block = blocks[row][col];
//
//    return (isSpace(block)) ? 0 : Math.abs(row - row(block)) + Math.abs(col - col(block));
//  }
//
//  private int row (int block) {
//    return (block - 1) / size;
//  }
//
//  private int col (int block) {
//    return (block - 1) % size;
//  }

}