/*
    class for client incoming chat, chat from other users / server
*/

import java.net.*;
import java.io.*;
import java.util.*;

public class PlayerChatIncoming extends Thread {
    private Socket server;
    private String username;
    private DataInputStream in;

    public PlayerChatIncoming(Socket server, String username) throws IOException {
        this.server = server;
        this.username = username;

        in = new DataInputStream(server.getInputStream());
    }

    //for getting messages from other users
    public void run() {
        while (true) {
            try{
                System.out.println(in.readUTF()); // get message data 
                System.out.print("\n\nMessage: ");
            } catch (IOException e) {
                System.out.println("Stream has been closed or Input/Ouput error occurs!");
                System.exit(1);
            }            
        }
    }
}