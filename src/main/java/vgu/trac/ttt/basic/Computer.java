package vgu.trac.ttt.basic;

public class Computer extends Player {

    @Override
    public int makeMove(Board board) {
        for (int i = 1 ; i <= 9; i++){
            if (board.isEmpty(i)) {
                return i;
            }
        }
        return -1;
    }
}
