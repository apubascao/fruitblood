/*
    server takes note of the connected clients and creates a thread for each one
    when the server receives a message from any of the client, will broadcast to all
*/

import java.io.*;
import java.net.*;
import java.util.*;

import java.awt.*;

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
    private String[] clientsUsername;
    
    private int[][] seedCoordinates;
    
    private int numberSeeds;
    private int powerSeeds;
    
    private String initialCoordinates;

    public Server(int port, int num, String address) throws IOException {
        serverSocket = new ServerSocket(port);  // bind the port to a socket
        socket = new DatagramSocket(port);
        totalPlayers = num;
        clients = new Socket[totalPlayers];    // create unconnected sockets
        
        this.port = port;
        this.address = address;
        ia = InetAddress.getByName(address);

        clientsIA = new InetAddress[totalPlayers];      
        clientsPort = new int[totalPlayers];
        clientsUsername = new String[totalPlayers];
        
        //to scale the number of seeds against the number of players
        numberSeeds = totalPlayers * 5;
        powerSeeds = totalPlayers * 2;
        seedCoordinates = new int [1250][800];
        for(int i = 0; i < 1250; i++)
            for(int j = 0; j < 800; j++)
                seedCoordinates[i][j] = -1;
            
        initialSeedCoordinates();
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
                        
                        Boolean playerAte = false;
                        
                        if(data.startsWith("playermove")){                          
                            String substring[] = data.split(",");
                            String username = substring[1].trim();      
                            String fruitChoice = substring[2].trim();
                            int size = Integer.parseInt(substring[3].trim());
                            int x = Integer.parseInt(substring[4].trim());
                            int y = Integer.parseInt(substring[5].trim());
                            
                            //player ate a seed or ate an opponent
                            for (int a = 0; a < 1250; a++) {
                                for (int b = 0; b < 800; b++) {
                                    if (seedCoordinates[a][b] != -1) {
                                        Rectangle seedBounds = new Rectangle(a, b, 25, 25);
                                        Rectangle playerBounds = new Rectangle(x, y, size, size);

                                        if (overlap(playerBounds, seedBounds)) { // Check if player and seed overlap
                                            playerAte = true;

                                            int[] newCoordinates = new int[2];
                                            int type = 1;
                                            if (seedCoordinates[a][b] == 1) {
                                                type = 1;
                                                newCoordinates = newSeedCoordinate(a, b, 1);
                                            }
                                            else if(seedCoordinates[a][b] == 2) {
                                                type = 2;
                                                newCoordinates = newSeedCoordinate(a, b, 2);
                                            }

                                            seedCoordinates[a][b] = -1;
                                            
                                            byte seedupdatebuffer[] = new byte[256];
                                            String seedupdatedata = "seedupdate," + a + "," + b + "," + newCoordinates[0] + "," + newCoordinates[1] + "," + type;
                                            seedupdatebuffer = seedupdatedata.getBytes();   
                                            
                                            int arrayPosition = -1;
                                            
                                            //send to all
                                            for(int i = 0; i < totalPlayers; i++){
                                                DatagramPacket seedupdate = new DatagramPacket(seedupdatebuffer, seedupdatebuffer.length, clientsIA[i], clientsPort[i]);
                                                socket.send(seedupdate);
                                                
                                                DatagramPacket tosend = new DatagramPacket(buffer, buffer.length, clientsIA[i], clientsPort[i]);
                                                socket.send(tosend);
                                                
                                                if(clientsUsername[i].equals(username))
                                                    arrayPosition = i;
                                            }
                                            
                                            //inform the player that ate a seed
                                            byte playerupdatebuffer[] = new byte[256];
                                            String playerupdatedata = "ateseed," + type;
                                            playerupdatebuffer = playerupdatedata.getBytes();                               
                                            DatagramPacket ateseed = new DatagramPacket(playerupdatebuffer, playerupdatebuffer.length, clientsIA[arrayPosition], clientsPort[arrayPosition]);
                                            socket.send(ateseed);
                                        }
                                    }
                                }
                            }

                            //player just moved
                            if(!playerAte) {
                                for(int i = 0; i < totalPlayers; i++){                                  
                                    DatagramPacket tosend = new DatagramPacket(buffer, buffer.length, clientsIA[i], clientsPort[i]);
                                    socket.send(tosend);
                                }
                            }                           
                        }

                        if(data.startsWith("ateopponent")){
                            String substring[] = data.split(",");
                            String username = substring[1].trim();      
                            
                            System.out.println(username + " inside ate");
                            
                            for(int i = 0; i < totalPlayers; i++){                              
                                if(clientsUsername[i].equals(username)){
                                    DatagramPacket tosend = new DatagramPacket(buffer, buffer.length, clientsIA[i], clientsPort[i]);
                                    socket.send(tosend);
                                    break;
                                }
                            }
                        }
                        
                        if(data.startsWith("eaten")){
                            String substring[] = data.split(",");
                            String username = substring[1].trim();      
                            
                            System.out.println(username + " inside eaten");
                            
                            for(int i = 0; i < totalPlayers; i++){                              
                                if(clientsUsername[i].equals(username)){
                                    DatagramPacket tosend = new DatagramPacket(buffer, buffer.length, clientsIA[i], clientsPort[i]);
                                    socket.send(tosend);
                                    break;
                                }
                            }                               
                        }
						
						if(data.startsWith("newXY")){                            
                            for(int i = 0; i < totalPlayers; i++){                              
                                    DatagramPacket tosend = new DatagramPacket(buffer, buffer.length, clientsIA[i], clientsPort[i]);
                                    socket.send(tosend);
                            }                               
                        }
						
						if(data.startsWith("dead")){							
							DatagramPacket tosend;
                            String substring[] = data.split(",");
                            String username = substring[1].trim();      
							
							System.out.println(username + " inside dead");
                            
                            for(int i = 0; i < totalPlayers; i++){
								
								//send to all that a player is dead
								tosend = new DatagramPacket(buffer, buffer.length, clientsIA[i], clientsPort[i]);
								socket.send(tosend);
								
								//remove mouse listener
                                if(clientsUsername[i].equals(username)){										
									byte[] removeBuffer = new byte[256];
									String toSend = "removelistener";
									removeBuffer = toSend.getBytes();
									tosend = new DatagramPacket(removeBuffer, removeBuffer.length, clientsIA[i], clientsPort[i]);
                                    socket.send(tosend);
                                }
								
                            }                               
                        }
						
						
						if(data.startsWith("win")){                            
                            for(int i = 0; i < totalPlayers; i++){                              
								DatagramPacket tosend = new DatagramPacket(buffer, buffer.length, clientsIA[i], clientsPort[i]);
								socket.send(tosend);
                            }                               
                        }
						
                        
                    }
                } catch (SocketException se) {
                    System.out.println(se);
                } catch (IOException ioe) {
                    System.out.println(ioe);
                }
            }
        };
    }

    // Check if the player and the seed overlap
    private boolean overlap(Rectangle playerBounds, Rectangle seedBounds) {
        return seedBounds.x < playerBounds.x + playerBounds.width && 
            seedBounds.x + seedBounds.width > playerBounds.x && 
            seedBounds.y < playerBounds.y + playerBounds.height && 
            seedBounds.y + seedBounds.height > playerBounds.y;
    }

    private int[] newSeedCoordinate(int a, int b, int type){
        int[] toReturn = new int[2];
        
        Random rn = new Random();       
        int x, y;
        
        while(true){
            x = rn.nextInt(1250);
            y = rn.nextInt(800);

            if(seedCoordinates[x][y] == -1){
                toReturn[0] = x;
                toReturn[1] = y;
                seedCoordinates[a][b] = -1;
                seedCoordinates[x][y] = type;
                break;
            }
        }
        
        return toReturn;
    }
    
    
    private void initialSeedCoordinates(){
        int n = 0, m = 0, type, x, y;
        Random rn = new Random();

        initialCoordinates = null;
        
        while (true) {
            x = rn.nextInt(1250);
            y = rn.nextInt(800);
            
            if (seedCoordinates[x][y] == -1) {
                if (n < numberSeeds) {
                    type = 1;
                    n++;
                } else {
                    type = 2;
                    m++;
                }

                seedCoordinates[x][y] = type;

                if (initialCoordinates == null) initialCoordinates = x + "," + y + "," + type;    
                else initialCoordinates = initialCoordinates + "," + x + "," + y + "," + type;    
            }

            if(n == numberSeeds && m == powerSeeds) break;
        }
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
                
                //for checking if correct data
                if(data.startsWith("sendport")){            
                    String[] dataArray = data.split(",");
                    
                    String playerUsername = dataArray[1].trim();
                    int playerPort = Integer.parseInt(dataArray[2].trim());
                    
                    clientsPort[count] = playerPort;
                    clientsUsername[count] = playerUsername;
                    
                    count++;
                    System.out.println("Players: " + count + " / " + totalPlayers);                     
                }
                
                             
            } catch (IOException e) {
                System.out.println(e);
            }
        }           
        
        for(int i = 0; i < totalPlayers; i++)
            threads[i].start(); 
        
        moveIncoming.start();
        
        //for letting the players know that all players are already connected
        try {
            for(int i = 0; i < totalPlayers; i++){
                byte buffer[] = new byte[256];          
                
                //sending go signal
                buffer = new byte[256];
                DatagramPacket tosend = new DatagramPacket(buffer, buffer.length, clientsIA[i], clientsPort[i]);
                socket.send(tosend);
                
                //sending initial seed coordinates
                buffer = initialCoordinates.getBytes();         
                tosend = new DatagramPacket(buffer, buffer.length, clientsIA[i], clientsPort[i]);
                socket.send(tosend);
            }   
        } catch (SocketException se) {
            System.out.println(se);
        } catch (IOException ioe) {
            System.out.println(ioe);
        }                    
    }
}
