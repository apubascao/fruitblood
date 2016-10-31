/*
    class that the server uses for broadcasting the messages to all clients
*/

import java.net.*;
import java.io.*;
import java.util.*;

public class ServerMessageIncoming extends Thread{
    private Socket client;
    private DataInputStream in;
    private DataOutputStream out;
    private static ArrayList<ServerMessageIncoming> clients = new ArrayList<ServerMessageIncoming>();    

    public ServerMessageIncoming(Socket client) throws IOException {
        this.client = client;
        this.in = new DataInputStream(client.getInputStream());
        this.out = new DataOutputStream(client.getOutputStream());
    }

    //for getting the message from the user
    public void run(){
         clients.add(this);
         while(true){
            try{
                String message = in.readUTF();
                sendToAll(message);
            }catch(IOException e){}
         }
    }

    //for sending to other users that are connected
    public void sendToAll(String message){
        synchronized(clients){
            for(ServerMessageIncoming a : clients){
                try{
                     a.out.writeUTF(message);
                }catch(IOException e){}
            }
        }
    }
}