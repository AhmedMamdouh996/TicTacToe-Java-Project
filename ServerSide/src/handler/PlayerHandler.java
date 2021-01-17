/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package handler;


import database.playerinfo.Player;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Vector;

/**
 *
 * @author Hossam
 */

public class PlayerHandler extends Thread{
    
    private Socket playerSocket;
    private Player player;
    
    private Player playerRequest;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    
    public static Vector<Player> onlinePlayersVect;
    
    public PlayerHandler(Socket socket, Player playerInfo)
    {
        this.playerSocket = socket;
        this.player = playerInfo;
        
        try {
            //Create the input and output channels
            inputStream = new ObjectInputStream(socket.getInputStream());
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            
            //return the info to the player (logged in client)
            outputStream.writeObject(player);
            
            //add the player to the online players vector
            onlinePlayersVect.add(player);
            
            //set the player status into signned in 
            
            
            //Start the thread to accept requests
            this.start();
            
        } catch (IOException ex) {
            ex.printStackTrace();
            close();
        }
    }
    
    @Override
    public void run()
    {
        //Listen to the requests of the users
        while (true)
        {
            try {
//                playerRequest = (player)inputStream.readObject();
                
                /*
                if () //Sign in request
                {
                }
                else if ()//Sign up request
                {
                }
                else //Null (invalid request)
                {   
                    close();
                }
                */
                //Connection Drop
            } catch (IOException | ClassNotFoundException ex) { 
                //close this socket and end this thread
                ex.printStackTrace();
                close();
            } 
        }
    }
    
    public void close()
    {
        try {
            //remove this current player from online vector
            onlinePlayersVect.remove(this.player);
            
            //Close the connection
            playerSocket.close();
            this.stop();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}