package vgu.trac.ttt.basic;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.*;

public class Board_1DTest {

    @Test
    // Does Human Win?
    void isWinHuman() {
        int[] cells = {2, 2, 1 , 2, 1, 2, 1, 0, 0};
        Board board = new Board_1D(cells);
        assertEquals(board.isWin(), 1);
    }

    @Test 
    // Does Computer Win?
    void isWinComputer() {
        int[] cells = {2, 2, 2, 1, 1, 0, 0, 0, 0};
        Board board = new Board_1D(cells);
        assertEquals(board.isWin(), 2);
    }
    
    @Test
    // Checking: Board is Full (True case)
    void isFullTrue() {
        int[] cells = {2, 1, 2, 1, 2, 1, 1, 2, 1};
        Board board = new Board_1D(cells);
        assertTrue(board.isFull());
    }

    @Test
    // Checking: Board is not yet Full (Fall Case)
    void isFullFalse() {
        int[] cells = {2, 1, 2, 1, 2, 1, 1, 0, 0};
        Board board = new Board_1D(cells);
        assertFalse(board.isFull());
    }
    
    @Test
    // Checking: The move is valid (True Case)
    void isValidTrue() {
        Board board = new Board_1D();
        assertTrue(board.isValid(2));
    }
    
    @Test
    // Checking: The move is invalid (False Case)
    void isValidFalse() {
        Board board = new Board_1D();
        assertFalse(board.isValid(0));
    }

    @Test 
    // Checking: The cell is empty (True Case)
    void isEmptyTrue() {
        int[] cells = {2, 1, 2, 1, 2, 1, 1, 0, 0};
        Board board = new Board_1D(cells);
        assertTrue(board.isEmpty(8));
    }

    @Test 
    /*  Checking: The cell is not empty (False Case) */
    void isEmptyFalse() {
        int[] cells = {2, 1, 2, 1, 2, 1, 1, 0, 0};
        Board board = new Board_1D(cells);
        assertFalse(board.isEmpty(2));
    }

    @Test
    // Checking printing board method
    void printBoard(){
        int[] cells = {2, 1, 2, 1, 2, 1, 1, 0, 0};
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Board board = new Board_1D(cells, out);
        board.printBoard();

        String output = out.toString();
        // The normal \n and println in windows will have the /r/n => use lineSeparator
        String nl = System.lineSeparator();
        String expected = 
        " | 2 | 1 | 2 | " + nl + 
        " | 1 | 2 | 1 | " + nl + 
        " | 1 | 0 | 0 | " + nl;
        assertEquals(expected.trim(), output.trim());
    }

    @Test 
    // Test: Get the id player of in a position
    void getCell() {
        int[] cells = {2, 2, 1, 2, 1, 2, 1, 0, 0};
        Board board = new Board_1D(cells);
        assertEquals(board.getCell(8), 0);
    }

    @Test 
    // Checking: A move is successfully saved
    void placeMoveSuccess() {
        int[] cells = {2, 1, 2, 1, 2, 1, 1, 0, 0};
        Board board = new Board_1D(cells);
        board.placeMove(8, 1);
        assertEquals(board.getCell(8), 1);
    }

}
