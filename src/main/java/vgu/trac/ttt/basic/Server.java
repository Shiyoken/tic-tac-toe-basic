package vgu.trac.ttt.basic;

import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) {
        int port = 8080;
        System.out.println("Starting Tic-Tac-Toe Server...");
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            Socket socket = serverSocket.accept();
            System.out.println("Successfully connect a client");
            // input stream
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String firstPlayer = in.readLine();
            int first_player = Integer.parseInt(firstPlayer);

            PrintStream networkOut = new PrintStream(socket.getOutputStream(), true);
            // Redirect all the System.out in this system to the socketOutputStream
            // Send all the messages through this for user
            System.setOut(networkOut);

            Board board = new Board_1D();
            Human human = new Human(socket.getInputStream());                
            Game game = new Game(first_player, board, human);
            game.start();
            serverSocket.close();
        } catch (IOException e) {
            System.out.println("Failed Connection:" + e.getMessage());
        }
    }
}