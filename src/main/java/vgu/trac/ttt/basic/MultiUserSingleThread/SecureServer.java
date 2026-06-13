package vgu.trac.ttt.basic.MultiUserSingleThread;

import java.io.*;
import java.net.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import vgu.trac.ttt.basic.Board;
import vgu.trac.ttt.basic.Board_1D;
import vgu.trac.ttt.basic.Computer;

public class SecureServer {
    private static final String SECRET_KEY = "Trac";
    public static void main(String args[]) {
        int port = 1234;
        System.out.println("Server is listening on port 1234...");
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
        // Initial Board
        if (request.equals("START")) {
            return createResponse("000000000", "PLAYING");
        }
        String[] message = request.split(",");
        String boardString = message[0];
        String hashBoard = message[1];
        String clientMove = message[2];
        // Check Hash 
        if (!hashBoard.equals(computeHash(boardString, SECRET_KEY))) {
            return createResponse(boardString, "ERROR_REJECT_MOVE");
        }

        Board board = new Board_1D(boardString);
        int humanMove = 0;
        // Check validity
        try {
            humanMove = Integer.parseInt(clientMove);
        } catch (NumberFormatException e) {
            if (clientMove.equals("q")) {
                return createResponse(board.boardState(), "END_GAME");
            }
            return createResponse(board.boardState(), "ERROR_FORMAT");
        }
        if (!board.isEmpty(humanMove)) {
            return createResponse(board.boardState(), "ERROR_EMPTY");
        }
        if (!board.isValid(humanMove)) {
            return createResponse(board.boardState(), "ERROR_INVALID");
        }
        
        // Play the human move
        board.placeMove(humanMove, 1);
        if (board.isWin() == 1) {
            return createResponse(board.boardState(), "HUMAN_WIN");
        }
        if (board.isFull()) {
            return createResponse(board.boardState(), "DRAW");
        }
        // Place computer move
        int computerMove = computer.makeMove(board);
        board.placeMove(computerMove,2);
        if (board.isWin() == 2) {
            return createResponse(board.boardState(), "COMPUTER_WIN");
        }
        if (board.isFull()) {
            return createResponse(board.boardState(), "DRAW");
        }
        return createResponse(board.boardState(), "PLAYING");
    }

    private static String createResponse(String boardState, String status) {
        String hashBoard = computeHash(boardState, SECRET_KEY);
        return boardState + "," + hashBoard + "," + status;
    }
    // Hash Function
    private static String computeHash(String data, String secretKey) {
        try {
            SecretKeySpec signingKey = new SecretKeySpec(secretKey.getBytes(), "HmacSHA256");
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(signingKey);
            byte[] rawHmac = mac.doFinal(data.getBytes());
            return Base64.getEncoder().encodeToString(rawHmac);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException("Failed to generate HMAC-SHA256 hash", e);
        }
    }
}