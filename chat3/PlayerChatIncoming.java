/*
    class for client incoming chat, chat from other users / server
*/

import java.net.*;
import java.io.*;
import java.util.*;

public class PlayerChatIncoming extends Thread{
    private Socket server;
    private DataInputStream in;

    public PlayerChatIncoming(Socket server) throws IOException {
        this.server = server;
        this.in = new DataInputStream(server.getInputStream());
    }

    //for getting messages from other users
    public void run(){
        while(true){
            try{
                System.out.println(in.readUTF()); //readUTF waits for input    
            }catch(IOException e){}            
        }
    }
}