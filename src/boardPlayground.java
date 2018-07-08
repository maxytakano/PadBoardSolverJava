import edu.princeton.cs.algs4.*;

import java.io.File;
import java.util.*;

public class boardPlayground {
  public static void main(String[] args) {
    String target_dir = "./testPuzzles/other";
    File dir = new File(target_dir);
    File[] files = dir.listFiles();

    File first;
    if (files[0].isFile()) {
      first = files[0];
    } else {
      return;
    }

    In in = new In(first);
    int rows = in.readInt();
    int cols = in.readInt();
    int movePosition = in.readInt();
    // Read starting board position
    int[][] startPosition = new int[rows][cols];
    for (int row = 0; row < rows; row++) { // Vertical
      for (int col = 0; col < cols; col++) { // Horizontal
        startPosition[row][col] = in.readInt();
      }
    }

    // Read end board position
    int[][] targetPosition = new int[rows][cols];
    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < cols; col++) {
        targetPosition[row][col] = in.readInt();
      }
    }

    Board startBoard = new Board(startPosition, movePosition);
    Board targetBoard = new Board(targetPosition, movePosition);

    System.out.println(startBoard);
    System.out.println(targetBoard);
    System.out.println(startBoard.hamming(targetBoard));

    Solver solver = new Solver(startBoard, targetBoard, false);
    System.out.println("Solved");

  }
}
