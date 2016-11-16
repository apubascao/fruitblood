/*
    server takes note of the connected clients and creates a thread for each one
    when the server receives a message from any of the client, will broadcast to all
*/

import java.net.*;
import java.io.*;
import java.util.*;

public class Server extends Thread {
    private ServerSocket serverSocket;
    private Socket[] sockets;
    private static int totalPlayers = 0;
	
	private Thread moveIncoming;
	private int port;
	private String address;
	private InetAddress ia;
	private InetAddress[] clientsIA;

    public Server(int port, int num, String address) throws IOException {
        serverSocket = new ServerSocket(port);  // bind the port to a socket
        totalPlayers = num;
        sockets = new Socket[totalPlayers];    // create unconnected sockets
		
		this.port = port;
		this.address = address;
		ia = InetAddress.getByName(address);

		clientsIA = new InetAddress[totalPlayers];
		startMI();
    }

	private void startMI(){
		moveIncoming = new Thread() {
			public void run(){
				try {
					DatagramSocket socket = new DatagramSocket(port);
					while (true) {
						//receive data from a player
						byte buffer[] = new byte[256];
						DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
						socket.receive(packet);						
						
						String data = new String(packet.getData());
						System.out.println("server received = " + data);
						//NOTE - CHECK IF WE NEED TO REMOVE THE SENDER, recursion
						//send to all
						for(int i = 0; i < totalPlayers; i++){
							DatagramPacket tosend = new DatagramPacket(buffer, buffer.length, clientsIA[i], port);
							socket.send(tosend);
							System.out.println(tosend);
						}						
					}
				} catch (SocketException se) {
					System.out.println(se);
				} catch (IOException ioe) {
					System.out.println("Input/Ouput error occurs!");
				}
			}
		};
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
				clientsIA[count] = server.getInetAddress();
                count++;
                System.out.println("Players: " + count + " / " + totalPlayers);              
				System.out.println(server.getInetAddress());
            } catch (IOException e) {
                System.out.println("Input/Output error occurs while waiting for a connection!");
            }
        }		
		
        for(int i = 0; i < totalPlayers; i++)
            threads[i].start();	
		
		moveIncoming.start();
					
    }
}
