package vgu.trac.ttt.basic;

abstract class Board {
    abstract void printBoard();
    abstract int placeMove(int move, int player);
    abstract int isWin();
    abstract boolean isDraw();
}
