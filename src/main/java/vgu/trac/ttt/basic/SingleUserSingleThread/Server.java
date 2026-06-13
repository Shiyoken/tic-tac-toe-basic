package vgu.trac.ttt.basic.SingleUserSingleThread;

import java.io.*;
import java.net.*;

import vgu.trac.ttt.basic.Board;
import vgu.trac.ttt.basic.Board_1D;
import vgu.trac.ttt.basic.Game;
import vgu.trac.ttt.basic.Human;

public class Server {
    public static void main(String[] args) {
        int port = 8080;
        System.out.println("Starting Tic-Tac-Toe Server...");
        PrintStream serverOut = new PrintStream(System.out);
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            while (true) {
                Socket socket = serverSocket.accept();
                // Redirect all the System.out in this system to the socketOutputStream
                // Send all the messages through this for user
                serverOut.println("Successfully connect a client");
                try {
                    Board board = new Board_1D(socket.getOutputStream());
                    Human human = new Human(socket.getInputStream());    
                    // Player computer = new Computer            
                    // Game game = new Game(board, computer, human);
                    Game game = new Game(1, board, human, socket.getOutputStream());
                    game.start();
                } catch (IOException e) {
                    System.out.println("Error while playing game: " + e.getMessage());
                } 
            }
        } catch (IOException e) {
            System.out.println("Failed Connection:" + e.getMessage());
        }
    }
}