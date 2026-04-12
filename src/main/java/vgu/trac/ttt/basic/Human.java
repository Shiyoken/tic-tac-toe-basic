package vgu.trac.ttt.basic;
import java.io.InputStream;

import java.util.*;

public class Human extends Player {
    protected int id;
    private Scanner keyboard;

    public Human() {
        this.id = 1;
        keyboard = new Scanner(System.in);
    }
    //For testing
    public Human(InputStream in) {
        this.id = 1;
        keyboard = new Scanner(in);
    }
    public int makeMove(Board board){
        String move = keyboard.nextLine();
        int moveInt = -1;
        try {
            moveInt = Integer.parseInt(move);
            return moveInt;
        } catch (NumberFormatException e) {
            System.out.println("Please enter only numbers");
            return moveInt;
        } 
    }

    //For testing
    public int makeMove(Board board, InputStream in) {
        keyboard = new Scanner(in);
        String move = keyboard.nextLine();
        int moveInt = -1;
        try {
            moveInt = Integer.parseInt(move);
            if (!board.isValid(moveInt)) {
                System.out.println("Invalid range, please input numbers from [1-9]");
                return -1;
            }
            return moveInt;
        } catch (NumberFormatException e) {
            System.out.println("Please enter only numbers");
            return moveInt;
        }
    }
}

