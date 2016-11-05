/*
    class that the server uses for broadcasting the messages to all clients
*/

import java.net.*;
import java.io.*;
import java.util.*;

public class ServerMessageIncoming extends Thread {
    private Socket server;
    private DataInputStream in;
    private DataOutputStream out;
    private static ArrayList<ServerMessageIncoming> messages = new ArrayList<ServerMessageIncoming>();  

    public ServerMessageIncoming(Socket server) throws IOException {
        this.server = server;
        in = new DataInputStream(server.getInputStream());
        out = new DataOutputStream(server.getOutputStream());   // send data to ClientSocket
    }

    //for getting the message from the user
    public void run() {
        messages.add(this);
        while (true) {
            try{
                String message = in.readUTF();  // read data from ClientSocket
                sendToAll(message);
            } catch (IOException e) {
                System.out.println("Stream has been closed or Input/Ouput error occurs!");
                System.exit(1);
            }
        }
    }

    // sending message data to all connected clients
    public void sendToAll(String message) {
        synchronized (messages) {
            for (ServerMessageIncoming data : messages) {
                try {
                    data.out.writeUTF(message); // send data to ClientSocket
                } catch(IOException e) {
                    System.out.println("Input/Ouput error occurs!");
                    System.exit(1);
                }
            }
        }
    }
}