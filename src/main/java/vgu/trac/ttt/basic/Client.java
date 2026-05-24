package vgu.trac.ttt.basic;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        String serverAddress = "localhost";
        int port = 8080;

        try {
            Socket socket = new Socket(serverAddress, port);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            Scanner keyboard = new Scanner(System.in);
            
            System.out.println("Who moves first? You or Server (1- You, 2 - Server): ");
            while (true) { 
                int firstPlayer = Integer.parseInt(keyboard.nextLine());
                if (firstPlayer != 1 && firstPlayer != 2) {
                    System.out.println("Invalid Input, please input only [1-2]");
                }
                else {
                    out.println(firstPlayer);
                    break;
                }
            }
            // Establish a protocol for communication here: Read the lines until it sees 'Player#1's turn' so that then we will continue 
            // listen to the input from the user
            while (true) {
                try { 
                    String response = in.readLine();
                    if (response == null) {
                        System.out.println("Server Disconnected.");
                        break;
                    }
                    System.out.println(response);

                    if (response.contains("Player#1's turn")) {
                        String move = keyboard.nextLine();
                        out.println(move);
                        if (move.equals("q")) {
                            out.println(move);
                            System.out.println("Game Quit.");
                            break;
                        }
                    }
                } catch (IOException e) {
                    System.out.println("End of the game");
                    break;
                }
            }
            socket.close();
            keyboard.close();
        } catch (IOException e) {
            System.out.println("Network Error: " + e.getMessage());
        }
    }
}