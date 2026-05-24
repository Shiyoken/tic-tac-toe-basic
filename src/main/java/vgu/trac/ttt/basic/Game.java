package vgu.trac.ttt.basic;

// import java.util.logging.Level;
// import java.util.logging.Logger;

public class Game {

    private Board board;
    private Human human;
    private Computer computer;
    private boolean gameActive;
    private Player currentPlayer;
    private int currentPlayerId;

    public Game(int firstPlayer) {
        this.board = new Board_2D();
        this.human = new Human();
        this.computer = new Computer();
        this.gameActive = true;
        this.currentPlayerId = firstPlayer;
    }
    // Constructor for Client-Server Instructor
    public Game(int firstPlayer, Board board, Human human) {
        this.board = board;
        this.human = human;
        this.computer = new Computer();
        this.gameActive = true;
        this.currentPlayerId = firstPlayer;
    }

    public void start() {
        System.out.println("Hello!");
        board.printBoard();

        if (currentPlayerId == 1) {
            currentPlayer = human;
        } else {
            currentPlayer = computer;
        }

        // Thread gameThread = new Thread(
        // () -> {
        while (gameActive) {
            System.out.println("Player#" + currentPlayerId + "'s turn");
            int moveCheck = currentPlayer.makeMove(board);

            if (moveCheck == -2) {
                System.out.println("End of the game");
                gameActive = false;
                return;
            } else if (moveCheck == 0) {
                System.out.println("Please, input a valid number [1-9]");
            } else if (!board.isValid(moveCheck)) {
                System.out.println("Please, input a valid number [1-9]");
            } else if (!board.isEmpty(moveCheck)) {
                System.out.println("The cell is occupied!");
            } else {
                board.placeMove(moveCheck, currentPlayerId);
                int winner = board.isWin();
                if (winner != 0) {
                    board.printBoard();
                    System.out.println("Player#" + currentPlayerId + " won!");
                    gameActive = false;
                } else if (board.isFull()) {
                    board.printBoard();
                    System.out.println("It is a draw!");
                    gameActive = false;
                } else {
                    board.printBoard();
                    if (currentPlayerId == 1) {
                        currentPlayerId = 2;
                        currentPlayer = computer;
                    } else {
                        currentPlayerId = 1;
                        currentPlayer = human;
                    }
                }
            }
        }
        // });
        // gameThread.start();

        // while (gameActive) {
        // System.out.println("Player# " + currentPlayerId + "'s turn");
        // int moveCheck = currentPlayer.makeMove(board);

        // if (moveCheck == -1) {}
        // else if (!board.isValid(moveCheck)) {
        // System.out.println("Please, input a valid number [1-9]");
        // }
        // else if (!board.isEmpty(moveCheck)) {
        // System.out.println("The cell is occupied!");
        // }
        // else {
        // board.placeMove(moveCheck, currentPlayerId);
        // int winner = board.isWin();
        // if (winner != 0) {
        // board.printBoard();
        // System.out.println("Player# " + currentPlayerId + " won!");
        // gameActive = false;
        // } else if (board.isFull()) {
        // board.printBoard();
        // System.out.println("It is a draw!");
        // gameActive = false;
        // } else {
        // board.printBoard();
        // if (currentPlayerId == 1) {
        // currentPlayerId = 2;
        // currentPlayer = computer;
        // } else {
        // currentPlayerId = 1;
        // currentPlayer = human;
        // }
        // }
        // }
        // }
    }
}
