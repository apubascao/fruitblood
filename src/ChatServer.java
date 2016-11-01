/*
    server takes note of the connected clients and creates a thread for each one
    when the server receives a message from any of the client, will broadcast to all
*/

import java.net.*;
import java.io.*;
import java.util.*;

public class ChatServer extends Thread {
    private ServerSocket serverSocket;
    private Socket[] sockets;
    private static int totalPlayers = 0;

    public ChatServer(int port, int num) throws IOException {
        serverSocket = new ServerSocket(port);  // bind the port to a socket
        totalPlayers = num;
        sockets = new Socket[totalPlayers];    // create unconnected sockets
    }

    public void run() {
        int count = 0;
        System.out.println("Players: " + count + " / " + totalPlayers);
        
        ServerMessageIncoming[] threads = new ServerMessageIncoming[totalPlayers];

        while (count < totalPlayers) {  // check current player count
            try {
                Socket server = serverSocket.accept();  // accept connection from client    
                sockets[count] = server;
                threads[count] = new ServerMessageIncoming(server);  
                count++;
                System.out.println("Players: " + count + " / " + totalPlayers);              
            } catch (IOException e) {
                System.out.println("Input/Output error occurs while waiting for a connection!");
            }
        }

        for(int i = 0; i < totalPlayers; i++)
            threads[i].start();
    }

    public static void main(String [] args) {
        try {
            int port = Integer.parseInt(args[0]);
            int totalPlayers = Integer.parseInt(args[1]);

            ChatServer t = new ChatServer(port, totalPlayers);
            t.start();
        } catch (IOException e) {
            System.out.println("Usage: java ChatServer <port no.> <no. of player>\n" +
                    "Make sure to use valid ports (greater than 1023)");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Usage: java ChatServer <port no.> <no. of players>\n"+
                    "Insufficient arguments given.");
        }
    }
}
