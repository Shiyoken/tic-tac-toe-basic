package vgu.trac.ttt.basic;
import java.util.*;

public class Player {
    protected int id;

    public Player() {
        this.id = 1;
    }
    public void makeMove(Board board){
        Scanner keyboard = new Scanner(System.in);
        boolean validMove = false;
        while (!validMove){
            System.out.println("Player1 turn");
            int move = keyboard.nextInt();
            int result = board.placeMove(move, this.id);
            if (result == 1) {
                validMove = true;
            }
            else if (result == 0) {
                System.out.println("Occupied. Retry");
            }
            else if (result == -1) {
                System.out.println("Invalid Range");
            }
        }
        // keyboard.close();   
    }
}
