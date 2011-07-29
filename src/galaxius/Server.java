/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package galaxius;

import java.awt.BorderLayout;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executor;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

/**
 *
 * @author xalgra
 */
public class Server extends JFrame{
    
    private JTextArea logDisplay; 
    private JLabel statusLabel;
    private ServerSocket server;   
    private Board board;
    
    
    public Server()
    {
        super("Galaxius Server");
        logDisplay = new JTextArea();
        add( new JScrollPane(logDisplay));
        statusLabel = new JLabel("...");
        add(statusLabel,BorderLayout.SOUTH);
               
        setSize(200,300);
        setVisible(true);      
        
        
        
    }
    public void runServer()
    {
        status("Start Server");
        board = new Board();
        handleConnections();       
        
    }                         
    
    private void handleConnections() 
    {
        Thread Connectionhandle = new Thread( 
                new Runnable() {

                    @Override
                    public void run() {
                        try 
                        {
                            status("Prepare to accept connections");
                            server = new ServerSocket( 55555 , 8);
                            while( true )
                            {
                               Socket tempSocket = server.accept();                
                                status("New connection from"+ tempSocket.getInetAddress().getHostName() );

                                      board.addPlayer(tempSocket);
                                      status("new Player added");

                            }
                           } 
                       catch (IOException ex) 
                       {
                                ex.printStackTrace();
                       }
                       finally
                        {
                            try {
                                server.close();
                            } catch (IOException ex) {
                                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        } 
                  }
        }); 
        Connectionhandle.start();
     
    }
    
    public void status(final String  message)
    {
        SwingUtilities.invokeLater( new Runnable()
                {                   

                    @Override
                    public void run() {
                        statusLabel.setText(message);
                        logDisplay.append(message+"\n");
                    }
                });
    }
    
}
