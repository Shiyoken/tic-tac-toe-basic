package vgu.trac.ttt.basic.MultiUserSingleThread;

import java.io.*;
import java.net.*;
import java.util.Scanner;
import vgu.trac.ttt.basic.Board;
import vgu.trac.ttt.basic.Board_1D;



public class SecureClient {
    public static void main(String[] args) {
        String serverAddress = "localhost";
        int port = 1234;
        Board board = null;
        String currentHash = null;
        // 1. Get the initial empty board and signed hash from the server
        try {
            Socket socket = new Socket(serverAddress, port);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out.println("START");
            String response = in.readLine();
            if (response == null) {
                System.out.println("Failed to start game: " + response);
            }
            String[] parts = response.split(",");
            String initialBoardState = parts[0];
            currentHash = parts[1];
            board = new Board_1D(initialBoardState);
            System.out.println("Hello!");
            board.printBoard();
            socket.close();
            
        } catch (IOException e) {
            System.out.println("Network Error: " + e.getMessage());
            return;
        }
        Scanner keyboard = new Scanner(System.in);
        boolean gameActive = true;
        while (gameActive) {
            System.out.println("Player#1's turn");
            String move = keyboard.nextLine().trim();
            // Connect to server and exchange data
            try {
                Socket socket = new Socket(serverAddress, port);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                String request = board.boardState() + "," + currentHash + "," + move;
                out.println(request);
                String response = in.readLine();
                if (response == null) {
                    System.out.println("Server disconnected unexpectedly.");
                    break;
                }               
                String[] parts = response.split(",");
                String newBoardState = parts[0];
                String newHash = parts[1];
                String status = parts[2];
                // Update local board and hash
                board.setBoard(newBoardState);
                currentHash = newHash;
                
                if (status.equals("END_GAME")) {
                    System.out.println("End of the game!");
                    break;
                }
                else if (status.equals("ERROR_FORMAT") || parts[0].equals("ERROR_INVALID")) {
                    System.out.println("Please, input a valid number [1-9]");
                    continue;
                }
                else if (status.equals("ERROR_EMPTY")) {
                    System.out.println("The cell is occupied!");
                    continue;
                }
                else if (status.equals("ERROR_REJECT_MOVE")) {
                    System.out.println("Cheater!");
                    break;
                }
                else if (status.equals("HUMAN_WIN")) {
                    board.printBoard();
                    System.out.println("Player#1's win!");
                    socket.close();
                    gameActive = false;
                }
                else if (status.equals("COMPUTER_WIN")) {
                    board.printBoard();
                    System.out.println("Player#2's win!");
                    socket.close();
                    gameActive = false;
                }
                if (status.equals("DRAW")) {
                    board.printBoard();
                    System.out.println("Draw!");
                    socket.close();
                    gameActive = false;
                }
                else if (status.equals("PLAYING")) {
                    board.printBoard();
                }
            } catch (IOException e) {
                System.out.println("Network Error: Could not connect to Tic-Tac-Toe server: " + e.getMessage());
                break;
            }
        }
        keyboard.close();
    }
}
