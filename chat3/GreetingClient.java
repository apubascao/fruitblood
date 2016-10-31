/*
    two threads, one for user input and one for messages from other users
    this will eliminate the turn base chat
*/

import java.net.*;
import java.io.*;
import java.util.*;

public class GreetingClient extends Thread{
    public PlayerChatOutgoing messageOutgoing;
    public PlayerChatIncoming messageIncoming;

    public GreetingClient(Socket server, String username) throws IOException {
        this.messageOutgoing = new PlayerChatOutgoing(server, username);
        this.messageIncoming = new PlayerChatIncoming(server);

        messageOutgoing.start();
        messageIncoming.start();
    }

    public static void main(String [] args){
        try{
            String serverName = args[0];
            int port = Integer.parseInt(args[1]);

            System.out.println("Connecting to " + serverName + " on port " + port);
            Socket server = new Socket(serverName, port);

            System.out.print("Enter username: ");
            String username = System.console().readLine();

            new GreetingClient(server, username);

        }catch(IOException e){
            e.printStackTrace();
            System.out.println("Cannot find (or disconnected from) Server");
        }catch(ArrayIndexOutOfBoundsException e){
            System.out.println("Usage: java GreetingClient <server ip> <port no.> '<your message to the server>'");
        }
    }
}
