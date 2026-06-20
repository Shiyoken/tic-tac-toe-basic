package vgu.trac.ttt.basic.HTTP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
// import java.net.*;
import java.util.Scanner;
import vgu.trac.ttt.basic.Board;
import vgu.trac.ttt.basic.Board_1D;

public class HTTPClient {
    private static final String BASE_URL = "http://localhost:1234";

    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);
        boolean gameActive = true;
        Board board = new Board_1D();

        // Phase 1: Get/start
        try {
          String jsonResponse = sendGet(BASE_URL + "/start");
          String response = parseResponse(jsonResponse);
          // response: "123456789","PLAYING"
          String[] responseParts = response.split(",");
          board.setBoard(responseParts[0]);
          board.printBoard();
        } catch (IOException e) {
            System.out.println("Failed to connect to server: " + e.getMessage()); 
        }

        // Phase 2: Playing the game
        while (gameActive) {
            try {
                System.out.println("Player#1's turn: ");
                String move = keyboard.nextLine().trim();

                // Create a message to send to Server
                String jsonBody = "{\"board\": \"" + board.boardState() + "\", \"move\": \"" + move + "\"}";

                String jsonResponse = sendPost(BASE_URL + "/play", jsonBody);
                String response = parseResponse(jsonResponse);
                String[] responseParts = response.split(",");

                String boardResult = responseParts[0];
                String status = responseParts[1];

                if (status.equals("END_GAME")) {
                    System.out.println("Quit the game.");
                    gameActive = false;
                    continue;
                }

                if (status.equals("ERROR_FORMAT") || status.equals("ERROR_INVALID")) {
                    System.out.println("Please, input a valid number [1-9]");
                    continue;
                }
                if (status.equals("ERROR_EMPTY")) {
                    System.out.println("That cell is occupied");
                    continue;
                }
                board.setBoard(boardResult);
                board.printBoard();

                if (status.equals("HUMAN_WIN")) {
                    System.out.println("Player#1 wins");
                    gameActive = false;
                }
                else if (status.equals("COMPUTER_WIN")) {
                    System.out.println("Computer wins!");
                    gameActive = false;
                } 
                else if (status.equals("DRAW")) {
                    System.out.println("It is a draw!");
                    gameActive = false;
                }
            } catch (IOException e) {
                System.out.println("Bad Internet Connection: " + e.getMessage());
                break;
            }
        }
        keyboard.close();
    }

    // First function: send a GET request + return Response Body
    private static String sendGet(String urlString) throws IOException {
        
        URL url = new URL(urlString); // This is the API endpoint, where the User can talk with the Server
        HttpURLConnection connection = (HttpURLConnection) url.openConnection(); // Establish a connection
        //using HttpURLConnection
        connection.setRequestMethod("GET"); //send a get?
        
        // Then what here? return the response body
        InputStreamReader in = new InputStreamReader(connection.getInputStream());
        BufferedReader reader = new BufferedReader(in);
        
        StringBuilder response = new StringBuilder();
        String line; 
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();
        return response.toString();
    }
    // Second function: send Post 
    private static String sendPost(String urlString, String jsonBody) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        
        // Gửi POST request + a Message body 
        // Then get the result
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        // Send the JSON body 
        OutputStream os = connection.getOutputStream();
        os.write(jsonBody.getBytes("utf-8"));
        os.close();

        // Read the response 
        InputStreamReader in = new InputStreamReader(connection.getInputStream());
        BufferedReader reader = new BufferedReader(in);
        StringBuilder response = new StringBuilder();
        String line; 
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();
        return response.toString();
    }
    private static String parseResponse(String jsonString) {
        jsonString = jsonString.replace("{", "").replace("}", "").replace("\"", "").trim();
        String board = "";
        String status = "";

        String[] pairs = jsonString.split(",");
        for (String pair : pairs) {
            String[] keyValue = pair.split(":");
            if (keyValue.length == 2) {
                String key = keyValue[0].trim();
                String value = keyValue[1].trim();
                if (key.equals("board")) {
                    board = value; 
                }
                if (key.equals("status")) {
                    status = value;
                }
            }
        }
        return board + "," + status;
    }
}
