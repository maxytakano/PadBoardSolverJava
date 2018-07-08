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
  int[][] medium4x4s, medium4x4t;
  int[][] hStart, h1, h2, h3, h4, hTarget;
  int[][] allBB, allBBr, allBBl, allBBu, allBBd;

  @Before
  public void init() {
    allBB = new int[][] {
        {6, 1, 2, 7, 0, 6},
        {5, 6, 3, 3, 6, 5},
        {0, 7, 5, 4, 2, 1},
        {2, 6, 1, 3, 6, 3},
        {6, 4, 0, 4, 7, 6}
    };

    allBBl = new int[][] {
        {6, 1, 2, 7, 0, 6},
        {5, 6, 3, 3, 6, 5},
        {0, 5, 7, 4, 2, 1},
        {2, 6, 1, 3, 6, 3},
        {6, 4, 0, 4, 7, 6}
    };

    allBBr = new int[][] {
        {6, 1, 2, 7, 0, 6},
        {5, 6, 3, 3, 6, 5},
        {0, 7, 4, 5, 2, 1},
        {2, 6, 1, 3, 6, 3},
        {6, 4, 0, 4, 7, 6}
    };

    allBBu = new int[][] {
        {6, 1, 2, 7, 0, 6},
        {5, 6, 5, 3, 6, 5},
        {0, 7, 3, 4, 2, 1},
        {2, 6, 1, 3, 6, 3},
        {6, 4, 0, 4, 7, 6}
    };

    allBBd = new int[][] {
        {6, 1, 2, 7, 0, 6},
        {5, 6, 3, 3, 6, 5},
        {0, 7, 1, 4, 2, 1},
        {2, 6, 5, 3, 6, 3},
        {6, 4, 0, 4, 7, 6}
    };

    hStart = new int[][] {
        {0, 1, 2, 3, 4, 5},
        {0, 1, 2, 3, 4, 5},
        {0, 1, 2, 3, 4, 5},
        {0, 1, 2, 3, 4, 5},
        {0, 1, 2, 3, 4, 5},
    };
    hTarget = new int[][] {
        {1, 2, 3, 4, 5, 0},
        {0, 1, 2, 3, 4, 5},
        {0, 1, 2, 3, 4, 5},
        {0, 1, 2, 3, 4, 5},
        {0, 1, 2, 3, 4, 5},
    };
    h1 = new int[][] {
        {1, 0, 2, 3, 4, 5},
        {0, 1, 2, 3, 4, 5},
        {0, 1, 2, 3, 4, 5},
        {0, 1, 2, 3, 4, 5},
        {0, 1, 2, 3, 4, 5},
    };
    h2 = new int[][] {
        {1, 2, 0, 3, 4, 5},
        {0, 1, 2, 3, 4, 5},
        {0, 1, 2, 3, 4, 5},
        {0, 1, 2, 3, 4, 5},
        {0, 1, 2, 3, 4, 5},
    };
    h3 = new int[][] {
        {1, 2, 3, 0, 4, 5},
        {0, 1, 2, 3, 4, 5},
        {0, 1, 2, 3, 4, 5},
        {0, 1, 2, 3, 4, 5},
        {0, 1, 2, 3, 4, 5},
    };
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

    medium4x4s = new int[][] {
        {0, 1, 1, 2},
        {1, 0, 0, 2},
        {3, 3, 3, 2},
        {0, 0, 0, 3},
    };
    medium4x4t = new int[][] {
        {0, 0, 0, 2},
        {1, 1, 1, 3},
        {3, 2, 2, 3},
        {0, 0, 0, 3},
    };
  }

  @org.junit.Test
  public void medium4x4test() {
    Board start = new Board(medium4x4s, 0);
    Board target = new Board(medium4x4t, 0);

  }

  @org.junit.Test
  public void hashCodeTest() {
    Board bstart1 = new Board(start1, 0);
    Board bstart2 = new Board(start2, 0);
    assertTrue(bstart1.equals(bstart2) && bstart2.equals(bstart1));
    assertTrue(bstart1.hashCode() == bstart2.hashCode());
  }

  @org.junit.Test
  public void equals() {
    Board bstart1 = new Board(start1, 0);
    Board btarget1 = new Board(target1, 0);
    Board bstart2 = new Board(start2, 0);
    Board boffSize1 = new Board(offSize1, 0);
    Board boffSize2 = new Board(offSize2, 0);
    Board boffSize3 = new Board(offSize3, 0);
    Board bbig1 = new Board(big1, 0);
    Board bbig1a = new Board(big1a, 0);
    Board bbig2 = new Board(big2, 6);
    Board bbig3 = new Board(big3, 0);


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
    Board bstart1 = new Board(start1, 4);
    Board bstart2 = new Board(start1, 0);
    Board bstart3 = new Board(start1, 8);
    Board bnL = new Board(nL, 3);
    Board bnR = new Board(nR, 5);
    Board bnU = new Board(nU, 1);
    Board bnD = new Board(nD, 7);
    Board bn2R = new Board(n2R, 1);
    Board bn2D = new Board(n2D, 3);
    Board bn3L = new Board(n3L, 7);
    Board bn3U = new Board(n3U, 5);
    Board[] neighborSet1 = {bnL, bnR, bnU, bnD};
    Board[] neighborSet2 = {bn2R, bn2D};
    Board[] neighborSet3 = {bn3L, bn3U};

    Iterator<Board> neighbors1 = bstart1.neighbors(false).iterator();
    int i = 0;
    while (neighbors1.hasNext()) {
      Board curNeighbor = neighbors1.next();
      assertEquals(curNeighbor, neighborSet1[i]);
      i++;
    }
    Iterator<Board> neighbors2 = bstart2.neighbors(false).iterator();
    i = 0;
    while (neighbors2.hasNext()) {
      Board curNeighbor = neighbors2.next();
      assertEquals(curNeighbor, neighborSet2[i]);
      i++;
    }
    Iterator<Board> neighbors3 = bstart3.neighbors(false).iterator();
    i = 0;
    while (neighbors3.hasNext()) {
      Board curNeighbor = neighbors3.next();
      assertEquals(curNeighbor, neighborSet3[i]);
      i++;
    }
  }

  @org.junit.Test
  public void isGoal() {
    Board bstart1 = new Board(start1, 0);
    Board bstart2 = new Board(start2, 0);

    Board bbig1 = new Board(big1, 0);
    Board bbig1a = new Board(big1a, 0);
    Board btarget2 = new Board(target2, 0);

    assertTrue(bstart1.isGoal(bstart2));
    assertTrue(bbig1.isGoal(bbig1a));
    assertFalse(btarget2.isGoal(bstart1));
  }

  @org.junit.Test
  public void hamming() {
    Board bstart1 = new Board(start1, 0);
    Board target1 = new Board(start2, 0);
    long s0 = 0b111;
    long s1 = 0b111000;
    long s2 = 0b111000000;
    long tx = 0b0;

    Board bstart2 = new Board(s0, s1, s2, tx, tx, tx, tx, tx, 0);
    assertEquals(0, bstart1.hamming(target1));
    assertEquals(0, bstart2.hamming(target1));

    long c0 = 0b111000000;
    long c1 = 0b111;
    long c2 = 0b111000;

    Board bstart3 = new Board(c0, c1, c2, tx, tx, tx, tx, tx, 0);
    assertEquals(9, bstart3.hamming(target1));

    long d0 = 0b111000;
    long d1 = 0b111;
    long d2 = 0b111000000;

    Board bstart4 = new Board(d0, d1, d2, tx, tx, tx, tx, tx, 0);
    assertEquals(6, bstart4.hamming(target1));
  }

  @org.junit.Test
  public void specificHamming() {
    Board hstart = new Board(hStart, 0);
    Board htarget = new Board(hTarget, 5);
    Board state1 = new Board(h1, 0);
    Board state2 = new Board(h2, 0);
    Board state3 = new Board(h3, 0);
    System.out.println(hstart.hamming(htarget));
    System.out.println(state1.hamming(htarget));
    System.out.println(state2.hamming(htarget));
    System.out.println(state3.hamming(htarget));
  }

  @org.junit.Test
  public void bigBoardTest() {
    Board bigBoardStart = new Board(allBB, 14);
    Board bigBoardl = new Board(allBBl, 13);
    Board bigBoardr = new Board(allBBr, 15);
    Board bigBoardu = new Board(allBBu, 8);
    Board bigBoardd = new Board(allBBd, 20);

    Board[] neighborSet = {bigBoardl, bigBoardr, bigBoardu, bigBoardd};

    Iterator<Board> neighbors = bigBoardStart.neighbors(false).iterator();
    int i = 0;
    while (neighbors.hasNext()) {
      Board curNeighbor = neighbors.next();
      assertEquals(curNeighbor, neighborSet[i]);
      i++;
    }
  }
}