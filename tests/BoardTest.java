import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;

public class BoardTest {
  int[][] start1, start2;
  int[][] nL, nR, nU, nD;
  int[][] n2R, n2D;
  int[][] n3L, n3U;
  int[][] target1, target2;
  int[][] offSize1, offSize2, offSize3;
  int[][] big1, big1a, big2, big3;

  @Before
  public void init() {
    start1 = new int[][] {
      {0, 0, 0},
      {1, 1, 1},
      {2, 2, 2},
    };
    start2 = new int[][] {
        {0, 0, 0},
        {1, 1, 1},
        {2, 2, 2},
    };
    nL = new int[][] {
        {0, 0, 0},
        {1, 1, 1},
        {2, 2, 2},
    };
    nR = new int[][] {
        {0, 0, 0},
        {1, 1, 1},
        {2, 2, 2},
    };
    nU = new int[][] {
        {0, 1, 0},
        {1, 0, 1},
        {2, 2, 2},
    };
    nD = new int[][] {
        {0, 0, 0},
        {1, 2, 1},
        {2, 1, 2},
    };
    n2R = new int[][] {
        {0, 0, 0},
        {1, 1, 1},
        {2, 2, 2},
    };
    n2D = new int[][] {
        {1, 0, 0},
        {0, 1, 1},
        {2, 2, 2},
    };
    n3U = new int[][] {
        {0, 0, 0},
        {1, 1, 2},
        {2, 2, 1},
    };
    n3L = new int[][] {
        {0, 0, 0},
        {1, 1, 1},
        {2, 2, 2},
    };
    target1 = new int[][] {
      {2, 2, 2},
      {3, 3, 3},
      {4, 4, 4},
    };
    target2 = new int[][] {
        {0, 0, 1},
        {1, 1, 2},
        {2, 2, 0},
    };
    offSize1 = new int[][] {
        {0, 0, 0, 0},
        {1, 1, 1, 1},
        {2, 2, 2, 2},
    };
    offSize2 = new int[][] {
        {0, 0, 0},
        {1, 1, 1},
        {2, 2, 2},
        {3, 3, 3},
    };
    offSize3 = new int[][] {
        {0, 0, 0, 3},
        {1, 1, 1, 3},
        {2, 2, 2, 3},
    };
    big1 = new int[][] {
        {0, 0, 0, 0, 0},
        {1, 1, 1, 1, 1},
        {2, 2, 2, 2, 2},
        {3, 3, 3, 3, 3},
        {4, 4, 4, 4, 4},
    };
    big1a = new int[][] {
        {0, 0, 0, 0, 0},
        {1, 1, 1, 1, 1},
        {2, 2, 2, 2, 2},
        {3, 3, 3, 3, 3},
        {4, 4, 4, 4, 4},
    };
    big2 = new int[][] {
        {0, 0, 0, 0, 3},
        {1, 1, 1, 1, 3},
        {2, 2, 2, 2, 3},
        {3, 3, 3, 3, 3},
        {4, 4, 4, 4, 3},
    };
    big3 = new int[][] {
        {0, 0, 0, 0, 0},
        {1, 1, 1, 1, 1},
        {2, 2, 2, 2, 2},
        {3, 3, 3, 3, 3},
        {0, 0, 0, 0, 0},
    };

//    s0 = 0b111;
  }

  @org.junit.Test
  public void hashCodeTest() {
    Board bstart1 = new Board(start1, target1, 0);
    Board bstart2 = new Board(start2, target1, 0);
    assertTrue(bstart1.equals(bstart2) && bstart2.equals(bstart1));
    assertTrue(bstart1.hashCode() == bstart2.hashCode());
  }

  @org.junit.Test
  public void equals() {
    Board bstart1 = new Board(start1, target1, 0);
    Board btarget1 = new Board(target1, start1, 0);
    Board bstart2 = new Board(start2, start1, 0);
    Board boffSize1 = new Board(offSize1, offSize1, 0);
    Board boffSize2 = new Board(offSize2, offSize2, 0);
    Board boffSize3 = new Board(offSize3, offSize3, 0);
    Board bbig1 = new Board(big1, big2, 0);
    Board bbig1a = new Board(big1a, big1, 0);
    Board bbig2 = new Board(big2, big2, 6);
    Board bbig3 = new Board(big3, big1, 0);


    assertNotEquals(bstart1, btarget1);
    assertEquals(bstart1, bstart1);
    assertEquals(bstart1, bstart2);
    assertNotEquals(boffSize1, bstart1);
    assertNotEquals(boffSize2, bstart1);
    assertNotEquals(boffSize1, boffSize2);
    assertNotEquals(bbig1, bstart1);
    assertNotEquals(bbig1, bbig2);
    assertEquals(bbig1, bbig1a);
  }

  @org.junit.Test
  public void toStringTest() {

  }

  @org.junit.Test
  public void neighbors() {
    Board bstart1 = new Board(start1, start2, 4);
    Board bstart2 = new Board(start1, start2, 0);
    Board bstart3 = new Board(start1, start2, 8);
    Board bnL = new Board(nL, start2, 0);
    Board bnR = new Board(nR, start2, 0);
    Board bnU = new Board(nU, start2, 0);
    Board bnD = new Board(nD, start2, 0);
    Board bn2R = new Board(n2R, start2, 0);
    Board bn2D = new Board(n2D, start2, 0);
    Board bn3L = new Board(n3L, start2, 0);
    Board bn3U = new Board(n3U, start2, 0);
    Board[] neighborSet1 = {bnL, bnR, bnU, bnD};
    Board[] neighborSet2 = {bn2R, bn2D};
    Board[] neighborSet3 = {bn3L, bn3U};

    Iterator<Board> neighbors1 = bstart1.neighbors().iterator();
    int i = 0;
    while (neighbors1.hasNext()) {
      Board curNeighbor = neighbors1.next();
      assertEquals(curNeighbor, neighborSet1[i]);
      i++;
    }
    Iterator<Board> neighbors2 = bstart2.neighbors().iterator();
    i = 0;
    while (neighbors2.hasNext()) {
      Board curNeighbor = neighbors2.next();
      assertEquals(curNeighbor, neighborSet2[i]);
      i++;
    }
    Iterator<Board> neighbors3 = bstart3.neighbors().iterator();
    i = 0;
    while (neighbors3.hasNext()) {
      Board curNeighbor = neighbors3.next();
      assertEquals(curNeighbor, neighborSet3[i]);
      i++;
    }
  }

  @org.junit.Test
  public void isGoal() {
    Board bstart1 = new Board(start1, start2, 0);
    Board bbig1 = new Board(big1, big1a, 0);
    Board btarget2 = new Board(start1, target2, 0);
    assertTrue(bstart1.isGoal());
    assertTrue(bbig1.isGoal());
    assertFalse(btarget2.isGoal());
  }

  @org.junit.Test
  public void hamming() {
    Board bstart1 = new Board(start1, start2, 0);
    long s0 = 0b111;
    long s1 = 0b111000;
    long s2 = 0b111000000;
    long tx = 0b0;

    Board bstart2 = new Board(s0, s1, s2, tx, tx, tx, 0);
    assertEquals(0, bstart1.hamming());
    assertEquals(0, bstart2.hamming());

    long c0 = 0b111000000;
    long c1 = 0b111;
    long c2 = 0b111000;

    Board bstart3 = new Board(c0, c1, c2, tx, tx, tx, 0);
    assertEquals(9, bstart3.hamming());

    long d0 = 0b111000;
    long d1 = 0b111;
    long d2 = 0b111000000;

    Board bstart4 = new Board(d0, d1, d2, tx, tx, tx, 0);
    assertEquals(6, bstart4.hamming());

  }
}