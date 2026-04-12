package vgu.trac.ttt.basic;

abstract class Board {
    abstract void printBoard();
    abstract void placeMove(int move, int player);
    abstract int isWin();
    abstract boolean isFull();
    abstract boolean isEmpty(int move);
    abstract boolean isValid(int move); 
    abstract int getCell(int move);
}
