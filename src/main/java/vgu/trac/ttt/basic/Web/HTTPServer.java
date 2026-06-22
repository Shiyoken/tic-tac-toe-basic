package vgu.trac.ttt.basic.Web;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import vgu.trac.ttt.basic.Board;
import vgu.trac.ttt.basic.Board_1D;
import vgu.trac.ttt.basic.Computer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;


public class HTTPServer {
    private static Computer computer = new Computer();
    public static void main(String[] args) {

        try {
            HttpServer server = HttpServer.create(new InetSocketAddress("localhost", 1234), 0);
            server.createContext("/", new BasicHandler());
            server.createContext("/start", new StartHandler());
            server.createContext("/play", new PlayHandler());

            server.start();
            System.out.println("Server is running on port 1234");
        } catch (IOException e) {
            System.out.println("Failed to start server: " + e.getMessage());
        }
    }
    
    static class BasicHandler implements HttpHandler {
       // Hình như exchange ~ port, kết nối ...
        @Override
       public void handle(HttpExchange exchange) throws IOException {
            
            // Step 1: Tạo message
            String response = "Hello, this is the first endpoint";
            // Step 2: Bắt đầu gửi Header ~ welcome message, the status - success or not and the message length
            exchange.sendResponseHeaders(200, response.length());
            // Step 3: Ask java to open a pipe to the client
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
       } 
    }

    static class StartHandler implements HttpHandler {
        @Override 
        public void handle(HttpExchange exchange) throws IOException {
            String initialBoard = "000000000";
            sendJsonResponse(exchange, 200, initialBoard, "PLAYING");
        }
    }
    static class PlayHandler implements HttpHandler {
        @Override 
        public void handle(HttpExchange exchange) throws IOException {

            // Handle the "OPTIONS" sent by the web browser
            if ("OPTIONS".equals(exchange.getRequestMethod())) {
                exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
                exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "POST, OPTIONS");
                exchange.getResponseHeaders().set("Access-Control-Allow-Headers", "Content-Type");
                exchange.sendResponseHeaders(204, -1);
                return;
            }
            if ("POST".equals(exchange.getRequestMethod())) {

                InputStreamReader input = new InputStreamReader(exchange.getRequestBody(), "utf-8");
                BufferedReader reader = new BufferedReader(input);
                String body = reader.readLine();

                String request = parseJson(body);

                String response = handleRequest(request, computer); // it should be return like, "01000000, PLAYING" or ",ERROR_INVALID"
                // response = process the board and move from the request

                String[] responseParts = response.split(",");
                String boardResult = responseParts[0].trim();
                String statusResult = responseParts[1].trim();
                sendJsonResponse(exchange, 200 , boardResult, statusResult);

            }
            else {
                exchange.sendResponseHeaders(405, 0);
                exchange.close();
            }
        }
    }

    private static void sendJsonResponse(HttpExchange exchange, int statusCode, String board, String status) throws IOException {
        String jsonResponse = "{\n" +
                          "  \"board\": \"" + board + "\",\n" +
                          "  \"status\": \"" + status + "\"\n" +
                          "}";
        exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(statusCode, jsonResponse.length());
        OutputStream os = exchange.getResponseBody();
        os.write(jsonResponse.getBytes());
        os.close();
    }


    private static String parseJson(String jsonString) {
        if (jsonString == null || !jsonString.contains("{")) {
            return "";
        }
        jsonString = jsonString.replace("{", "").replace("}", "").replace("\"", "").trim();
        String board = "";
        String move = "";

        String[] pairs = jsonString.split(",");
        for (String pair : pairs) {
            String[] keyValue = pair.split(":");
            if (keyValue.length == 2) {
                String key = keyValue[0].trim();
                String value = keyValue[1].trim();

                if (key.equals("board")) {
                    board = value;
                }
                if (key.equals("move")) {
                    move = value;
                }
            }
        }
        return board + "," + move;
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


