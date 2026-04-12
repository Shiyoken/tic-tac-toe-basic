package vgu.trac.ttt.basic;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.io.*;

public class HumanTest {
    @Test
    /*
        Testing success input from Human player
     */
    void makeMoveSuccess() {
        Board board = new Board_1D();
        String move = "5\n";
        ByteArrayInputStream in = new ByteArrayInputStream(move.getBytes()); 
        Human human = new Human();
        assertEquals(5, human.makeMove(board, in));
    }

    @Test
    /*
        Test invalid input from Human Player
     */
    void makeMoveInvalid() {
        Board board = new Board_1D();
        String move1 = "a\n";
        ByteArrayInputStream in1 = new ByteArrayInputStream(move1.getBytes());
        Human human = new Human();
        assertEquals(-1, human.makeMove(board, in1));

        String move2 = "36\n";
        ByteArrayInputStream in2 = new ByteArrayInputStream(move2.getBytes());
        assertEquals(-1, human.makeMove(board, in2));
    }
}
