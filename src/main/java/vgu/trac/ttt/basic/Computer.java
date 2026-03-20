package vgu.trac.ttt.basic;

public class Computer extends Player {
    private int i = 1;
    
    public Computer (){
        id = 2;
    }
    
    @Override
    public void makeMove(Board board) {
        System.out.println("Player2 turn");
        for (int index = i ; index <= 9; index++){
            if (board.placeMove(index, this.id) == 1) {
                break;
            }
        }
        this.i++;
    }
}
