/*
    class for client outgoing chat, client message
*/

import java.net.*;
import java.io.*;
import java.util.*;

public class PlayerChatOutgoing extends Thread{
    private Socket server;
    private String username;
    private DataOutputStream out;

    public PlayerChatOutgoing(Socket server, String username) throws IOException {
        this.server = server;
        this.username = username;

        OutputStream outToServer = server.getOutputStream();
        this.out = new DataOutputStream(outToServer);
    }

    //for getting the message from the user
    public void run(){
        while(true){
            try{
                System.out.print("\nMessage: ");
                String message = System.console().readLine();
                out.writeUTF(username + ": " + message);    
            }catch(IOException e){}            
        }
    }
}