package vgu.trac.ttt.basic.SingleUserSingleThread;

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
            while (true) {
                try { 
                    String response = in.readLine();
                    if (response.contains("draw!") || response.contains("won")) {
                        System.out.println(response);
                        break;
                    }
                    System.out.println(response);

                    if (response.contains("Player#1's turn")) {
                        System.out.print("SENDING: ");
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