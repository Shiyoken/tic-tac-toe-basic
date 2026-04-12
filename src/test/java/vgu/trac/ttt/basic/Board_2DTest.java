package vgu.trac.ttt.basic;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.*;


public class Board_2DTest {

    @Test
    // Checking: Board is Full (True case)
    void isFullTrue() {
        int[] cells = {2, 1, 2, 1, 2, 1, 1, 2, 1};
        Board board = new Board_2D(cells);
        assertTrue(board.isFull());
    }

    @Test
    // Checking: Board is not yet Full (Fall Case)
    void isFullFalse() {
        int[] cells = {2, 1, 2, 1, 2, 1, 1, 0, 0};
        Board board = new Board_2D(cells);
        assertFalse(board.isFull());
    }
    
    @Test 
    // Checking: isWin() for Human
    void isWinHuman() {
        int[] cells = {2, 1, 2, 1, 2, 1, 1, 1, 1};
        Board board = new Board_2D(cells);
        assertEquals(board.isWin(), 1);
    }
    
    @Test
    // Checking: isWin() for Computer
    void isWinComputer() {
        int[] cells = {2, 1, 2, 1, 2, 1, 2, 1, 1};
        Board board = new Board_2D(cells);
        assertEquals(board.isWin(), 2);
    }
    
    @Test
    // Checking: the move is valid (true case)
    void isValidTrue() {
        Board board = new Board_2D();
        assertTrue(board.isValid(5));
    }
    
    @Test 
    // Checking: the move is not valid (false case)
    void isValidFalse() {
        Board board = new Board_2D();
        assertFalse(board.isValid(11));
    }

    @Test
    //Checking: that position is empty? (true case)
    void isEmptyTrue() {
        int[] cells = {2, 1, 2, 1, 0, 1, 2, 1, 2};
        Board board = new Board_2D(cells);
        assertTrue(board.isEmpty(5));
    }
    @Test 
    //Checking: that position is empty? (false case)
    void isEmptyFalse() {
        int[] cells = {2, 1, 2, 1, 0, 1, 2, 1, 2};
        Board board = new Board_2D(cells); 
        assertFalse(board.isEmpty(2));
    }
    @Test 
    // PrintBoard
    void printBoard(){
        int[] cells = {2, 1, 2, 1, 2, 1, 1, 0, 0};
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        Board board = new Board_2D(cells, outContent);
        
        board.printBoard();

        String output = outContent.toString();
        // The normal \n and println in windows will have the /r/n => use lineSeparator
        String nl = System.lineSeparator();
        String expected = 
        " | 2 | 1 | 2 | " + nl + 
        " | 1 | 2 | 1 | " + nl + 
        " | 1 | 0 | 0 | " + nl;
        assertEquals(expected.trim(), output.trim());
    }
    @Test
    // Test: whether a move is placed successfully (true case)
    void placeMoveSuccess() {
        int[] cells = {2, 1, 2, 1, 2, 1, 1, 0, 0};
        Board board = new Board_2D(cells);
        board.placeMove(8, 2);
        assertEquals(board.getCell(8), 2);
    }
    
}
