package vgu.trac.ttt.basic;

public abstract class Board {
    public abstract void printBoard();
    public abstract void placeMove(int move, int player);
    public abstract int isWin();
    public abstract boolean isFull();
    public abstract boolean isEmpty(int move);
    public abstract boolean isValid(int move); 
    public abstract int getCell(int move);
    
    public abstract void setBoard(String boardState);
    public abstract String boardState();
}
