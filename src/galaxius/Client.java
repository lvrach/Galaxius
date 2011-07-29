/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package galaxius;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JLabel;

import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author xalgra
 */
public class Client extends JFrame {

    private ObjectOutputStream output;
    private ObjectInputStream input;
    private String message = "";
    private String serverIP;
    private Socket client;   
    private Pack pack;
    public BoardG board;
    private JPanel multiView;
    private PlayerInfo playerInfo;
    
    JPanel leftBar = new JPanel();
    private boolean LeftPressed = false;
    private boolean RightPressed = false;
    private boolean SpacePressed = false;
    private long SpaceTime = 0;
    private long MoveRightTime = 0;
    private long MoveLeftTime = 0;

    public Client(String serverIP) {
        super("Galaxius");
        this.serverIP = serverIP;

        
        
        setSize(1024, 768);
        this.setPreferredSize(new Dimension(1024, 768));
        this.setMinimumSize(new Dimension(1024, 768));

        
        setBackground(Color.DARK_GRAY);
        
        board = new BoardG(this);
      
        add(board);

          multiView = new ShipSelector(this);
        playerInfo=new  PlayerInfo(this);
        
        leftBar.setSize(254,768);
        leftBar.setPreferredSize(new Dimension(254,768));         
        leftBar.setMinimumSize(new Dimension(254,768));
        leftBar.setBackground(Color.DARK_GRAY);
        leftBar.add(multiView, BorderLayout.NORTH);
         leftBar.add(playerInfo, BorderLayout.SOUTH); 
          
        add(leftBar, BorderLayout.EAST);
 

        setSize(800, 600);
        setVisible(true);
        
       
    }
    
      

    public void runClient() {
        Thread listenerThread = new Thread(
                new Runnable() {

                    @Override
                    public void run() {
                        try {

                            connectToServer();
                            ListenToServer();


                        } catch (IOException ex) {
                            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                        } finally {
                            try {
                                client.close();
                            } catch (IOException ex) {
                                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                });

        listenerThread.start();



    }

    private void ListenToServer() {



        try {
            while (true) {
                try {
                    pack = (Pack) input.readObject();

                    unPack(pack);

                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                } catch (EOFException ex) {
                    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);


                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);

        } finally {
            System.exit(0);
        }




    }

    public void showShipSelector() {
        leftBar.remove(multiView);
        multiView = new ShipSelector(this);

        leftBar.add(multiView, BorderLayout.NORTH);
        leftBar.updateUI();

    }
    
    public void showProfil()
    {
        
    }

    private void startListeners() {
        this.addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {


             
                if (e.getKeyCode() == KeyEvent.VK_LEFT && !LeftPressed) {
                    LeftPressed = true;
                    
                    
                    if(RightPressed)
                    {                        
                        board.getMyShip().setMove(false, 90);
                        Action action = new Action(Action.MOVE_STOP);
                        action.time =(int) (System.currentTimeMillis() - MoveRightTime);
                        sendData(new Pack(Pack.ACTION, action));
                        RightPressed = false;
                    }
                    
                    Action action = new Action(Action.MOVE_LEFT);
                    MoveLeftTime= System.currentTimeMillis();
                    board.getMyShip().setMove(true, 180);                    
                    sendData(new Pack(Pack.ACTION, action));


                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT && !RightPressed) {
                    RightPressed = true;
                    
                    
                    if(LeftPressed)
                    {
                        LeftPressed = false;
                        board.getMyShip().setMove(false, 90);                    
                        Action action = new Action(Action.MOVE_STOP);
                        action.time =(int) (System.currentTimeMillis() - MoveLeftTime);
                        sendData(new Pack(Pack.ACTION, action));
                    }
                    Action action = new Action(Action.MOVE_RIGHT);                    
                    MoveRightTime =System.currentTimeMillis();
                    board.getMyShip().setMove(true, 0);                    
                    sendData(new Pack(Pack.ACTION, action));
                    

                } else if (e.getKeyCode() == KeyEvent.VK_SPACE && !SpacePressed) {
                    SpacePressed = true;
                    SpaceTime = System.currentTimeMillis();
                    Action action = new Action(Action.ATTACK_BASIC);  
                    action.x=board.getMyShip().getX();
                    sendData(new Pack(Pack.ACTION, action));

                }

                if (e.getKeyCode() == KeyEvent.VK_S) {
                    showShipSelector();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT && LeftPressed) {
                    LeftPressed = false;
                    RightPressed = false;
                    board.getMyShip().setMove(false, 90);                    
                    Action action = new Action(Action.MOVE_STOP);
                    action.time =(int) (System.currentTimeMillis() - MoveLeftTime);
                    sendData(new Pack(Pack.ACTION, action));
                    

                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT && RightPressed) {
                    
                    RightPressed = false;
                    LeftPressed = false;
                    board.getMyShip().setMove(false, 90);
                    Action action = new Action(Action.MOVE_STOP);
                    action.time =(int) (System.currentTimeMillis() - MoveRightTime);
                    sendData(new Pack(Pack.ACTION, action));

                } else if (e.getKeyCode() == KeyEvent.VK_SPACE && SpacePressed) {
                    SpacePressed = false;
                    Action action = new Action(Action.ATTACK_BASIC_STOP);
                    action.time = (int) (System.currentTimeMillis() - SpaceTime);
                    sendData(new Pack(Pack.ACTION, action));
                }

            }
        });

    }

    private void unPack(Pack pack) {

        if (pack.isMessage()) {
           
        }

        if (pack.isShip()) {



            Ship ship = new Ship((Ship) pack.unPack());
            board.updateShip(ship);

        }
        if (pack.isBullet()) {

            board.updateBullet((Bullet) pack.unPack());

        }
        if (pack.isEvent())
        {
            Event event = (Event) pack.unPack();
            if(event.isHpChange())
            {
                playerInfo.setHP(event.getValue());                
                
            }
            
            
        }
        if (pack.isInit()) {
            board.setMyID((Integer) pack.unPack());
            startListeners();
            board.run();
           

        }



    }

    private void connectToServer() throws IOException {

        client = new Socket(InetAddress.getByName(serverIP), 55555);

        output = new ObjectOutputStream(client.getOutputStream());
        output.flush();

        input = new ObjectInputStream(client.getInputStream());

    }

    public void sendData(Pack packet) {
        try {
            output.writeObject(packet);

        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
