package org.example;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {

        try (ServerSocket serverSocket = new ServerSocket(4444)) {

            System.out.println("Server is listening on port 4444");

            while (true) {
                Socket firstClient = serverSocket.accept();
                System.out.println("First client connected");
                System.out.println("Waiting for the second player");

                Socket secondClient = serverSocket.accept();
                System.out.println("Second client connected");

                //change it to gamev2 when all's ready
                Game g = new Game(firstClient, secondClient);
                Thread gTh = new Thread(g);
                gTh.start();

                // TO DO: Musi byc dokldnie dwoch klientow

            }

        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}


