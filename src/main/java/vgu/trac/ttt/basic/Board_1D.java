package vgu.trac.ttt.basic;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class Board_1D extends Board {
    private int[] board;
    private PrintStream printer;

    public Board_1D() {
        board = new int[9];
        for (int i = 0; i < 9; i++){
            board[i] = 0;
        }
        printer = new PrintStream(System.out);
    }

    public Board_1D(int arr[]) {
        board = new int[9];
        for (int i = 0; i < 9; i++) {
            board[i] = arr[i];
        }
    }

    // For testing
    public Board_1D(ByteArrayOutputStream out) {
        board = new int[9];
        for (int i = 0; i < 9; i++) {
            board[i] = 0;
        }
        printer = new PrintStream(out);

    }

    // For testing output
    public Board_1D(int arr[], ByteArrayOutputStream out) {
        board = new int[9];
        for (int i = 0; i < 9; i++) {
            board[i] = arr[i];
        }
        printer = new PrintStream(out);
    }

    public void printBoard() {
        for (int i = 0; i < 9; i++) {
            if (i % 3 == 0) {
                printer.print(" | ");
            }
            printer.print(board[i] + " | ");
            if ((i + 1) % 3 == 0) {
                printer.println();
            }
        }
    }

    public void placeMove(int move, int player) {
        int i = move - 1;
        board[i] = player;
    }

    public boolean isEmpty(int move) {
        int cell = move - 1;
        if (board[cell] == 0) {
            return true;
        }
        return false;
    }

    public boolean isValid(int move) {
        if (move < 1 || move > 9) {
            return false;
        }
        return true;
    }

    public int isWin() {
        if (board[0] == board[1] && board[1] == board[2] && board[2] != 0) {
            return board[2];
        }
        if (board[0] == board[3] && board[3] == board[6] && board[6] != 0) {
            return board[6];
        }
        if (board[0] == board[4] && board[4] == board[8] && board[8] != 0) {
            return board[8];
        }

        if (board[1] == board[4] && board[4] == board[7] && board[7] != 0) {
            return board[7];
        }
        if (board[2] == board[5] && board[5] == board[8] && board[8] != 0) {
            return board[8];
        }
        if (board[2] == board[4] && board[4] == board[6] && board[6] != 0) {
            return board[6];
        }

        if (board[3] == board[4] && board[4] == board[5] && board[5] != 0) {
            return board[5];
        }
        if (board[6] == board[7] && board[7] == board[8] && board[8] != 0) {
            return board[8];
        }

        return 0;
    }

    public boolean isFull() {
        for (int i = 0; i < 9; i++) {
            if (board[i] == 0) {
                return false;
            }
        }
        return true;
    }

    public int getCell(int move) {
        return board[move - 1];
    }
}
