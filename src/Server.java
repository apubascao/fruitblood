/*
    server takes note of the connected clients and creates a thread for each one
    when the server receives a message from any of the client, will broadcast to all
*/

import java.net.*;
import java.io.*;
import java.util.*;

public class Server extends Thread {
    private ServerSocket serverSocket;
	private DatagramSocket socket;
	
    private static int totalPlayers = 0;
	
	private Thread moveIncoming;
	private int port;
	private String address;
	private InetAddress ia;
	
	private Socket[] clients;
	private InetAddress[] clientsIA;	
	private int[] clientsPort;

    public Server(int port, int num, String address) throws IOException {
        serverSocket = new ServerSocket(port);  // bind the port to a socket
		socket = new DatagramSocket(port);
        totalPlayers = 2;
        clients = new Socket[totalPlayers];    // create unconnected sockets
		
		this.port = port;
		this.address = address;
		ia = InetAddress.getByName(address);

		clientsIA = new InetAddress[totalPlayers];		
		clientsPort = new int[totalPlayers];
		
		startMI();
    }

	private void startMI(){
		moveIncoming = new Thread() {
			public void run(){
				try {
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
							DatagramPacket tosend = new DatagramPacket(buffer, buffer.length, clientsIA[i], clientsPort[i]);
							socket.send(tosend);
							System.out.println(tosend);
						}						
					}
				} catch (SocketException se) {
					System.out.println("server" + se);
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
                Socket client = serverSocket.accept();  // accept connection from client    
                clients[count] = client;
                threads[count] = new ServerMessageIncoming(client); 
				clientsIA[count] = client.getInetAddress();     
				
				
				//for getting the port from the user
				byte buffer[] = new byte[256];
				DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
				socket.receive(packet);						
				
				String data = new String(packet.getData());
				String[] dataArray = data.split(",");
				System.out.println(dataArray[1]);
				int playerPort = Integer.parseInt(dataArray[1].trim());
				
				clientsPort[count] = playerPort;
				
                count++;
                System.out.println("Players: " + count + " / " + totalPlayers);              
            } catch (IOException e) {
                System.out.println(e);
            }
        }	
		
		
		
        for(int i = 0; i < totalPlayers; i++)
            threads[i].start();	
		
		moveIncoming.start();
					
    }
}
