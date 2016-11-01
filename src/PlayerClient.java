/*
    two threads, one for user input and one for messages from other users
    this will eliminate the turn based chat
*/

import java.net.*;
import java.io.*;
import java.util.*;

public class PlayerClient extends Thread {
    public PlayerChatOutgoing messageOutgoing;
    public PlayerChatIncoming messageIncoming;
    private String username;

    public PlayerClient(Socket client, String username) throws IOException {
        this.username = username;

        messageOutgoing = new PlayerChatOutgoing(client, username);
        messageIncoming = new PlayerChatIncoming(client, username);
        messageOutgoing.start();
        messageIncoming.start();
    }

    public static void main(String [] args) {
        try {
            String serverName = args[0];
            int port = Integer.parseInt(args[1]);

            System.out.println("Connecting to " + serverName + " on port " + port);
            Socket client = new Socket(serverName, port);   // creating a new socket for client and binding it to a port

            System.out.print("Enter username: ");
            String username = System.console().readLine();

            /*// notification for a connected client
            OutputStream outToServer = client.getOutputStream();
            DataOutputStream out = new DataOutputStream(outToServer);
            out.writeUTF("\n" + username + " joined chat room...");*/

            new PlayerClient(client, username);
        } catch (IOException e) {
            System.out.println("Cannot find (or disconnected from) Server");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Usage: java PlayerClient <server ip> <port no.>");
        }
    }
}
