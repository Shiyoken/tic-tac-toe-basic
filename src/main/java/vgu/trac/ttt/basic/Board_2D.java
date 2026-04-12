package vgu.trac.ttt.basic;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class Board_2D extends Board{
    private int[][] board;
    private PrintStream printer;

    public Board_2D() {
        board = new int[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = 0;
            }
        }
        printer = new PrintStream(System.out);
    }

    public Board_2D(int arr[]) {
        board = new int[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = arr[3 * i + j];
            }
        }
    }

    // For testing
    public Board_2D(ByteArrayOutputStream out) {
        board = new int[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = 0;
            }
        }
        printer = new PrintStream(out);
        
    }

    // For testing 
    public Board_2D(int arr[], ByteArrayOutputStream out) {
        board = new int[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = arr[3 * i + j];
            }
        }
        printer = new PrintStream(out);
    }

    public void printBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                printer.print(" | " + board[i][j]);
            }
            printer.print(" | ");
            printer.println();
        }
    }

    public void placeMove(int move, int player) {
        int board_move = move - 1;
        int i = board_move / 3;
        int j = board_move % 3;
        board[i][j] = player;

    }

    public boolean isValid(int move) {
        if (move < 0  || move > 9) {
            return false;
        }
        return true;
    }
    
    public boolean isEmpty(int move) {
        int board_move = move - 1;
        int i = board_move / 3;
        int j = board_move % 3;
        if (board[i][j] == 0) {
            return true;
        }
        return false;
    }

    public int isWin() {
        // Check horizontally and diagonally
        for (int i = 0; i < 3; i++ ){
            if (board[i][0] == board[i][1] && board[i][1] == board[i][2]) {return board[i][2];}
            if (board[0][i] == board[1][i] && board[1][i] == board[2][i]) {return board[2][i];}
        }
        if (board[0][0] == board[1][1] && board[1][1] == board[2][2]) {return board[2][2];}
        if (board[0][2] == board[1][1] && board[1][1] == board[2][0]) {return board[2][0];}
        return 0;
    }

    public boolean isFull() {
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                if (board[i][j] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public int getCell(int move) {
        int i = (move - 1) / 3;
        int j = (move - 1) % 3;
        return board[i][j];
    }
}
