package vgu.trac.ttt.basic.MultiUserSingleThread;

import java.io.*;
import java.net.*;
import vgu.trac.ttt.basic.Board;
import vgu.trac.ttt.basic.Board_1D;
import vgu.trac.ttt.basic.Computer;

public class Server {
    public static void main(String args[]) {
        int port = 1234;
        System.out.println("Server is opening at port 1234...");
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            Computer computer = new Computer();
            while (true) {
                Socket clientSocket = serverSocket.accept();
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                
                String request = in.readLine();
                if (request == null) {
                    continue;
                }
                System.out.println("Receiving: " + request);
                String response = handleRequest(request, computer);
                System.out.println("Sending: " + response); 
                out.println(response);
                clientSocket.close();
            }
        } catch (IOException e) {
            System.out.println("Failed Connection with Client " + e.getMessage());
        }
    }
    private static String handleRequest(String request, Computer computer) {
        if (request.equals("START")) {
            return "000000000, PLAYING";
        }
        String[] message = request.split(",");
        String boardString = message[0];
        String clientMove = message[1];
        
        Board board = new Board_1D(boardString);
        int humanMove = 0;
        // Check validity
        try {
            humanMove = Integer.parseInt(clientMove);
        } catch (NumberFormatException e) {
            if (clientMove.equals("q")) {
                return ",END_GAME";
            }
            return ",ERROR_FORMAT";
        }
        if (!board.isEmpty(humanMove)) {
            return ",ERROR_EMPTY";
        }
        if (!board.isValid(humanMove)) {
            return ",ERROR_INVALID";
        }
        // Play the human move
        board.placeMove(humanMove, 1);
        if (board.isWin() == 1) {
            return board.boardState() + ",HUMAN_WIN";
        }
        if (board.isFull()) {
            return board.boardState() + ",DRAW";
        }
        // Place computer move
        int computerMove = computer.makeMove(board);
        board.placeMove(computerMove,2);
        if (board.isWin() == 2) {
            return board.boardState() + ",COMPUTER_WIN";
        }
        if (board.isFull()) {
            return board.boardState() + ",DRAW";
        }
        return board.boardState() + ",PLAYING";
    }
}