package vgu.trac.ttt.basic;

import java.io.*;
import java.net.*;

public class SingleServer {
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
                PrintStream networkOut = new PrintStream(socket.getOutputStream(), true);
                System.setOut(networkOut);
                serverOut.println("Successfully connect a client");
                try {
                    Board board = new Board_1D();
                    Human human = new Human(socket.getInputStream());    
                    // Player computer = new Computer            
                    // Game game = new Game(board, computer, human);
                    Game game = new Game(1, board, human);
                    game.start();
                } catch (IOException e) {
                    System.out.println("Error while playing game: " + e.getMessage());
                } 
                // input stream
                // BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                // String firstPlayer = in.readLine();
                // int first_player = Integer.parseInt(firstPlayer);
                // Thread clientThread = new Thread(() ->{
                //     try {
                //         Board board = new Board_1D();
                //         Human human = new Human(socket.getInputStream());    
                //         // Player computer = new Computer            
                //         // Game game = new Game(board, computer, human);
                //         Game game = new Game(1, board, human);
                //         game.start();
                //     } catch (IOException e) {
                //         System.out.println("Error while playing game: " + e.getMessage());
                //     } 
                // });
                // clientThread.start();
            }
        } catch (IOException e) {
            System.out.println("Failed Connection:" + e.getMessage());
        }
    }
}