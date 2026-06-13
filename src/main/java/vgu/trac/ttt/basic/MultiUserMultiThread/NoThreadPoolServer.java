package vgu.trac.ttt.basic.MultiUserMultiThread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import vgu.trac.ttt.basic.Game;
import vgu.trac.ttt.basic.Human;
import vgu.trac.ttt.basic.Board;
import vgu.trac.ttt.basic.Board_1D;


public class NoThreadPoolServer {
    public static void main(String[] args) {
        int port = 8080;
        System.out.println("Starting Tic-Tac-Toe Server...");
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            while (true) {
                Socket socket = serverSocket.accept();
                ClientHandler client = new ClientHandler(socket);
                new Thread(client).start();
            }
        } catch (IOException e) {
            System.out.println("Failed Connection:" + e.getMessage());
        }
    }
    public static class ClientHandler implements Runnable {
        private Socket clientSocket;
        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }
        @Override 
        public void run() {
            try {
                Board board = new Board_1D(clientSocket.getOutputStream());
                Human human = new Human(clientSocket.getInputStream());    
                // Player computer = new Computer            
                // Game game = new Game(board, computer, human);
                Game game = new Game(1, board, human, clientSocket.getOutputStream());
                game.start();
            } catch (IOException e) {
                System.out.println("Error while playing game: " + e.getMessage());
           }
        }
    }
}

