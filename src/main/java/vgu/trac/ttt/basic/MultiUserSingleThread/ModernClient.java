package vgu.trac.ttt.basic.MultiUserSingleThread;

import java.io.*;
import java.net.*;
import java.util.Scanner;
import vgu.trac.ttt.basic.Board;
import vgu.trac.ttt.basic.Board_1D;


public class ModernClient {
    public static void main(String[] args) {
        String serverAddress = "localhost";
        int port = 1234;
        Board board = null;
        String hashBoard = null;
        String nonce = null;
        String hashNonce = null;
        String timeStamp = null;
        String hashTimeStamp = null;
        try {
            Socket socket = new Socket(serverAddress, port);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println("START");
            String response = in.readLine();
            if (response == null) {
                System.out.println("Failed to start game: " + response);
            }
            String[] parts = response.split(",");
            String initialBoard = parts[0];
            hashBoard = parts[1];
            nonce = parts[2];
            hashNonce = parts[3];
            timeStamp = parts[4];
            hashTimeStamp = parts[5];

            board = new Board_1D(initialBoard);
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
            try {
                Socket socket = new Socket(serverAddress, port);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                String request = board.boardState() + "," + hashBoard + "," 
                + nonce + "," + hashNonce + "," 
                + timeStamp + "," + hashTimeStamp + "," + move;

                out.println(request);
                String response = in.readLine();
                if (response == null) {
                    System.out.println("Server disconnected unexpectedly");
                    break;
                }
                String[] parts = response.split(",");
                String newBoardState = parts[0];
                hashBoard = parts[1];
                nonce = parts[2];
                hashNonce = parts[3];
                timeStamp = parts[4];
                hashTimeStamp = parts[5];
                String status = parts[6];

                board.setBoard(newBoardState);
                board.printBoard();
                
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
                else if (status.equals("ERROR_TIMEOUT")) {
                    System.out.println("Out of 10 seconds");
                    break;
                }
                else if (status.equals("HUMAN_WIN")) {
                    System.out.println("Player#1's win!");
                    socket.close();
                    gameActive = false;
                }
                else if (status.equals("COMPUTER_WIN")) {
                    System.out.println("Player#2's win!");
                    socket.close();
                    gameActive = false;
                }
                if (status.equals("DRAW")) {
                    System.out.println("Draw!");
                    socket.close();
                    gameActive = false;
                }
                else if (status.equals("PLAYING")) {
                }
            } catch (IOException e) {
                System.out.println("Network Error: Could not connect to Tic-Tac-Toe server: " + e.getMessage());
                break;
            }
                
            }
            keyboard.close();
        }
}

