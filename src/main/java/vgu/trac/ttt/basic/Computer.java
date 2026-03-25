package vgu.trac.ttt.basic;

public class Computer extends Player {
    private int i = 1;
    @Override
    public void makeMove(Board board) {
        System.out.println("Player2 turn");
        for (int index = i ; index <= 9; index++){
            if (board.placeMove(index, 2) == 1) {
                break;
            }
        }
        this.i++;
    }
}
