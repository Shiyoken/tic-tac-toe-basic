package vgu.trac.ttt.basic.MultiUserSingleThread;

import java.io.*;
import java.net.*;
import java.util.Scanner;
import vgu.trac.ttt.basic.Board;
import vgu.trac.ttt.basic.Board_1D;

public class Client {
    public static void main(String[] args) {
        String serverAddress = "localhost";
        int port = 1234;
        System.out.println("Hello!");
        Scanner keyboard = new Scanner(System.in);
        boolean gameActive = true;        
        Board board = new Board_1D();  
        // Receive the intial board from server
        try {
            Socket socket = new Socket(serverAddress, port);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                
            //Start the game
            out.println("START");
            String response = in.readLine();
            if (response == null || response.startsWith("ERROR")) {
                System.out.println("Failed to start game: " + response);
                // return;
            }
            String[] parts = response.split(",");
            if (parts.length != 2) {
                System.out.println("Invalid START response from server: " + response);
                // return;
            }
            String initialBoardState = parts[0];
            board.setBoard(initialBoardState);
            board.printBoard();
            socket.close();
        } catch (IOException e) {
            System.out.println("Failed to connect to server");
        }
        // Start playing the game
        try {
            while (gameActive) {
                Socket socket = new Socket(serverAddress, port);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                System.out.println("Player#1's turn");
                String move = keyboard.nextLine().trim(); 
                String request = board.boardState() + "," + move;   
                out.println(request);
                
                String response = in.readLine();
                String[] parts = response.split(",");
                String status = parts[1];
                // Check response from user: Handling Error
                if (status.equals("END_GAME")) {
                    System.out.println("End of the game!");
                    break;
                }
                if (status.equals("ERROR_FORMAT") || parts[0].equals("ERROR_INVALID")) {
                    System.out.println("Please, input a valid number [1-9]");
                    continue;
                }
                if (status.equals("ERROR_EMPTY")) {
                    System.out.println("The cell is occupied!");
                    continue;
                }
                String boardState = parts[0];
                board.setBoard(boardState);
                board.printBoard();
                if (status.equals("HUMAN_WIN")) {
                    System.out.println("Player#1's win!");
                    socket.close();
                    gameActive = false;
                }
                if (status.equals("COMPUTER_WIN")) {
                    System.out.println("Player#2's win!");
                    socket.close();
                    gameActive = false;
                }
                if (status.equals("DRAW")) {
                    System.out.println("Draw!");
                    socket.close();
                    gameActive = false;
                }  
            }
        } catch (IOException e) {
            System.out.println("Fail to connect to server");
        }
        keyboard.close();
    }
}