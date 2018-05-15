import java.util.LinkedList;

public class Board {
  private static final int SPACE = 0;

  private int[][] blocks;
  private static int[][] target;
  private int dimension; // TODO: refactor m x n

  public Board(int[][] blocks, int[][] target) {
    this.blocks = copy(blocks);
    this.target = copy(target);
    dimension = blocks.length;
  }

  public Board(int[][] blocks) {
    this.blocks = copy(blocks);
    dimension = blocks.length;
  }

  // TODO: refactor m x n
  private int[][] copy(int[][] blocks) {
    int[][] copy = new int[blocks.length][blocks.length];
    for (int row = 0; row < blocks.length; row++)
      for (int col = 0; col < blocks.length; col++)
        copy[row][col] = blocks[row][col];

    return copy;
  }

  // TODO: refactor m x n
  public int hamming() {
    int count = 0;
    for (int row = 0; row < blocks.length; row++)
      for (int col = 0; col < blocks.length; col++)
        if (blockIsNotInPlace(row, col)) count++;

    return count;
  }

  private boolean blockIsNotInPlace(int row, int col) {
    int block = blocks[row][col];
    int targetBlock = target[row][col];

    return !isSpace(block) && (block != targetBlock) && (targetBlock != 99);
  }

  // checks if block = 0 which is the empty space
  private boolean isSpace(int block) {
    return block == SPACE;
  }

//  // TODO: refactor m x n
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
//    return (block - 1) / dimension;
//  }
//
//  private int col (int block) {
//    return (block - 1) % dimension;
//  }

  public boolean isGoal() {
    for (int row = 0; row < blocks.length; row++)
      for (int col = 0; col < blocks.length; col++)
        if (blockIsNotInPlace(row, col)) {
          return false;
        }

    return true;
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
//    return row*dimension + col + 1;
//  }

  // returns new board state with two blocks swapped
  private int[][] swap(int row1, int col1, int row2, int col2) {
    int[][] copy = copy(blocks);
    int tmp = copy[row1][col1];
    copy[row1][col1] = copy[row2][col2];
    copy[row2][col2] = tmp;

    return copy;
  }

  // TODO: adapt m x n
  // determines if two boards are equal
  public boolean equals(Object y) {
    if (y==this) return true;
    if (y==null || !(y instanceof Board) || ((Board)y).blocks.length != blocks.length) return false;
    for (int row = 0; row < blocks.length; row++) {
      for (int col = 0; col < blocks.length; col++) {
        boolean condition1 = ((Board) y).blocks[row][col] != blocks[row][col];
        if (condition1 && target[row][col] != 99) {
          return false;
        }
      }
    }

    return true;
  }

  // Linked list of all neighbors for use of the neighbor optimization
  public Iterable<Board> neighbors() {
    LinkedList<Board> neighbors = new LinkedList<Board>();

    int[] location = spaceLocation();
    int spaceRow = location[0];
    int spaceCol = location[1];

    if (spaceRow > 0)               neighbors.add(new Board(swap(spaceRow, spaceCol, spaceRow - 1, spaceCol)));
    if (spaceRow < dimension - 1) neighbors.add(new Board(swap(spaceRow, spaceCol, spaceRow + 1, spaceCol)));
    if (spaceCol > 0)               neighbors.add(new Board(swap(spaceRow, spaceCol, spaceRow, spaceCol - 1)));
    if (spaceCol < dimension - 1) neighbors.add(new Board(swap(spaceRow, spaceCol, spaceRow, spaceCol + 1)));

    return neighbors;
  }

  // TODO: adapt m x n
  // finds where the space is by checking all cells, saves result into tuple
  private int[] spaceLocation() {
    for (int row = 0; row < blocks.length; row++)
      for (int col = 0; col < blocks.length; col++)
        if (isSpace(blocks[row][col])) {
          int[] location = new int[2];
          location[0] = row;
          location[1] = col;

          return location;
        }
    throw new RuntimeException();
  }

  // TODO: adapt m x n
  public String toString() {
    StringBuilder str = new StringBuilder();
    str.append(dimension + "\n");
    for (int row = 0; row < blocks.length; row++) {
      for (int col = 0; col < blocks.length; col++)
        str.append(String.format("%2d ", blocks[row][col]));
      str.append("\n");
    }

    return str.toString();
  }
}