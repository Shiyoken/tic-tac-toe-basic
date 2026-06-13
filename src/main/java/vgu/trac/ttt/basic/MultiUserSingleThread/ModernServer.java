package vgu.trac.ttt.basic.MultiUserSingleThread;

import java.io.*;
import java.net.*;
import vgu.trac.ttt.basic.Board;
import vgu.trac.ttt.basic.Board_1D;
import vgu.trac.ttt.basic.Computer;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.Base64;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class ModernServer {
    private static final String SECRET_KEY = "Trac";
    private static ConcurrentHashMap<String, Long> usedNonce = new ConcurrentHashMap<>();
    public static void main(String[] args) {
        int port = 1234;
        System.out.println("Server is listening on port 1234....");
        nonceCleanUp(); // A Clean up Service to automatically check 5 seconds and delete the nonce
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
    // Handle Request
    private static String handleRequest(String request, Computer computer) {
        if (request.equals("START")) {
            String message = createResponse("000000000", "PLAYING");
            return message;
        }
        // Take the message from client
        String[] message = request.split(",");
        String boardString = message[0];
        String hashBoard = message[1];
        String nonce = message[2];
        String hashNonce = message[3];
        String timeStamp = message[4];
        String hashTimeStamp = message[5];
        String playerMove = message[6];
        // Check Hash:  
        if (!hashBoard.equals(computeHash(boardString, SECRET_KEY)) || !hashNonce.equals(computeHash(nonce, SECRET_KEY)) || !hashTimeStamp.equals(computeHash(timeStamp, SECRET_KEY))) {
            return createResponse(boardString, "ERROR_REJECT_MOVE");
        }
        // Check timeStamp: new protocol
        long checkTimeStamp = Instant.now().toEpochMilli() - Long.parseLong(timeStamp);
        if (checkTimeStamp > 10000) {
            return createResponse(boardString, "ERROR_TIMEOUT");
        }
        // Check if the nonce is already in the database
        if (usedNonce.containsKey(nonce)) {
            return createResponse(boardString, "ERROR_REJECT_MOVE");
        }
        // If it's a valid new nonce, save it to the database so it can't be used again
        usedNonce.put(nonce, Long.parseLong(timeStamp));
        Board board = new Board_1D(boardString);
        int humanMove = 0;
        try {
            humanMove = Integer.parseInt(playerMove);
        } catch (NumberFormatException e) {
            if (playerMove.equals("q")) {
                return createResponse(boardString, "END_GAME");
            }
            return createResponse(boardString, "ERROR_FORMAT");
        }
        if (!board.isEmpty(humanMove)) {
            return createResponse(boardString, "ERROR_EMPTY");
        }
        if (!board.isValid(humanMove)) {
            return createResponse(boardString, "ERROR_INVALID");
        }
        // Play the human move
        board.placeMove(humanMove, 1);
        String newBoard = board.boardState();

        if (board.isWin() == 1) {
            return createResponse(newBoard, "HUMAN_WIN");
        }
        if (board.isFull()) {
            return createResponse(newBoard, "DRAW");
        }
        // Place computer move
        int computerMove = computer.makeMove(board);
        board.placeMove(computerMove,2);
        newBoard = board.boardState();

        if (board.isWin() == 2) {
            return createResponse(newBoard, "COMPUTER_WIN");
        }
        if (board.isFull()) {
            return createResponse(newBoard, "DRAW");
        }
        return createResponse(newBoard, "PLAYING");
    }

    private static String createResponse(String boardState, String status) {
        String hashBoard = computeHash(boardState, SECRET_KEY);
        String newNonce = String.valueOf(generateNonce());
        String hashNonce = computeHash(newNonce, SECRET_KEY);
        String newTimeStamp = String.valueOf(Instant.now().toEpochMilli());
        String hashTimeStamp = computeHash(newTimeStamp, SECRET_KEY);
        return boardState + "," + hashBoard + "," + newNonce + "," + hashNonce + "," + newTimeStamp + "," + hashTimeStamp + "," + status;
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
    // Nonce Function
    private static int generateNonce() {
        SecureRandom random = new SecureRandom();
        return random.nextInt();
    }
    //Clean up
    private static void nonceCleanUp() {
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(() -> 
        {
            long currentTime = Instant.now().toEpochMilli();
            // Remove any nonce that is older than 10,000 milliseconds (10 seconds)
            usedNonce.entrySet().removeIf(entry -> (currentTime - entry.getValue()) > 10000);
            System.out.println("[Cron] Current Nonces: " + usedNonce.keySet());
        
        }, 0, 5, TimeUnit.SECONDS); // Run every 5 seconds
    }

}
