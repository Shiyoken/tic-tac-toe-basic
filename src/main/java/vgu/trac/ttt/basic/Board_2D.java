package vgu.trac.ttt.basic;

public class Board_2D extends Board{
    private int[][] board;

    public Board_2D() {
        board = new int[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = 0;
            }
        }
    }
    public void printBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(" | " + board[i][j]);
            }
            System.out.print(" |");
            System.out.println();
        }
    }
    public int placeMove(int move, int player) {
        int board_move = move - 1;
        int i = board_move / 3;
        int j = board_move % 3;
        if (board_move < 0 || board_move > 8) {
            return -1;
        }
        if (board[i][j] != 0){
            return 0;
        }
        board[i][j] = player;
        return 1;
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


    public boolean isDraw() {
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                if (board[i][j] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

}
