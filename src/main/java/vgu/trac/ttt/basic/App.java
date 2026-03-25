package vgu.trac.ttt.basic;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ) {
        int firstPlayer = 1;
        if (args.length > 0) {
            try {
                firstPlayer = Integer.parseInt(args[0]);
            } catch (NumberFormatException e){
                System.out.println("Invalid argument. 1- Player, 2- Computer");
            }
        }
        else {
            System.out.println("You need to choose a player to go first");
        }
        Board board = new Board_1D();
        Player human = new Human();
        Computer computer = new Computer();
        boolean gameActive = true;
        
        Player currentPlayer = null;

        if (firstPlayer == 1){
            currentPlayer = human;
        }
        else if (firstPlayer == 2){
            currentPlayer = computer;
        }
        
        while (gameActive) {
            board.printBoard();
            currentPlayer.makeMove(board);
            
            int winner = board.isWin();
            if (winner != 0 ){
                board.printBoard();
                System.out.println("Player" + winner + " wins");
                gameActive = false;
            } else if (board.isDraw()){
                board.printBoard();
                System.out.println("The game is tie!");
                gameActive = false;
            }
            // Switch Player
            if (currentPlayer == human){
                currentPlayer = computer;
            } else {
                currentPlayer = human;
            }   
        }
    }
}
