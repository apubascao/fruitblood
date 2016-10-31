/*
    server takes note of the connected clients and creates a thread for each one
    when the server receives a message from any of the client, will broadcast to all
*/

import java.net.*;
import java.io.*;
import java.util.*;

public class GreetingServer extends Thread{
    private static ServerSocket serverSocket;
    private static Socket[] clients;

    public GreetingServer(int port) throws IOException{
    }    

    public static void main(String [] args){
        try{
            int port = Integer.parseInt(args[0]);
            int threadNum = Integer.parseInt(args[1]);

            serverSocket = new ServerSocket(port);

            int count = 0;
            System.out.println("Players: " + count + " / " + threadNum);

            Socket[] clients = new Socket[threadNum];
            ServerMessageIncoming[] threads = new ServerMessageIncoming[threadNum];

            while(count != threadNum){
                Socket client = serverSocket.accept(); //waits 
                clients[count] = client;
                threads[count] = new ServerMessageIncoming(client);
                count++;
                System.out.println("Players: " + count + " / " + threadNum);           
            }

            for(int i=0; i<threadNum; i++)
                threads[i].start();
            

        }catch(IOException e){}
    }
}
