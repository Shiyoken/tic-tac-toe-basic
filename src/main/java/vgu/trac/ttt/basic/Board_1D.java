package vgu.trac.ttt.basic;

public class Board_1D extends Board{
    private int[] board;
    
    public Board_1D() {
        board = new int[9];
        for (int i = 0; i < 9; i++) {board[i] = 0;}
    }
    public void printBoard() {
        for (int i = 0; i < 9; i++) {
            if (i % 3 == 0) {
                System.out.print("| ");
            }
            System.out.print(board[i] + " | ");
            if ((i + 1) % 3 == 0) {
                System.out.println();
            }
        }
    }
    public int placeMove(int move, int player){
        int i = move - 1;
        if (i < 0 || i > 8) {
            return -1;
        }
        if (board[i] != 0){
            return 0;
        }
        board[i] = player;
        return 1;
    }
    public int isWin() {
        if (board[0] == board[1] && board[1] == board[2] && board[2] != 0) {return board[2];}
        if (board[0] == board[3] && board[3] == board[6] && board[6] != 0) {return board[6];}
        if (board[0] == board[4] && board[4] == board[8] && board[8] != 0) {return board[8];}

        if (board[1] == board[4] && board[4] == board[7] && board[7] != 0) {return board[7];}
        if (board[2] == board[5] && board[5] == board[8] && board[8] != 0) {return board[8];}
        if (board[2] == board[4] && board[4] == board[6] && board[6] != 0) {return board[6];}

        if (board[3] == board[4] && board[4] == board[5] && board[5] != 0) {return board[5];}
        if (board[6] == board[7] && board[7] == board[8] && board[8] != 0) {return board[8];}

        return 0;
    }
    public boolean isDraw() {
        for (int i = 0; i < 9; i++) {
            if (board[i] == 0) {
                return false;
            }
        }
        return true;
    }
}
