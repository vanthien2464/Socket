/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cuoiky_server;

import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author XPS
 */
public class TCPServer {

    static final int PORT = 1234;
    private ServerSocket server = null;
    public TCPServer(){
        try{
            server = new ServerSocket(PORT);
        }catch(Exception e)
        {
         e.printStackTrace();
        }
    }
    
    public void action(){
        Socket socket = null;
        int i = 0;
        System.out.println("Server listening...");
        try{
            while((socket = server.accept()) != null){
                new ServerThread(socket, "Client#"+i);
                System.out.printf("Thread for Client#%d generating... %n",i++);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        new TCPServer().action();
    }
    
}
