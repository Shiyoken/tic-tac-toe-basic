package vgu.trac.ttt.basic;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ComputerTest {
    @Test
    /* 
        Test: Whether computer can automatically find 
        the first non-empty cell and place Move there
    */ 
    void makeMoveComputer2D(){
        int[] cells = {2, 2, 1, 2, 1, 2, 1, 0, 0};
        Board board = new Board_1D(cells);
        Computer computer = new Computer();
        assertEquals(8, computer.makeMove(board));
    }
}
