/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package galaxius;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
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
import javax.swing.JPanel;


/**
 *
 * @author xalgra
 */
public class Client extends JFrame {

    private ObjectOutputStream output;
    private ObjectInputStream input;
    public boolean connected = false;
    private String serverIP;
    private Socket client;
    private Pack pack;
    public BoardG board;
    private JPanel multiView;
    public PlayerInfo playerInfo;
    public ChatRoom chatRoom;
    private String playerName;
    private int state;
    JPanel leftBar = new JPanel();
    private boolean LeftPressed = false;
    private boolean RightPressed = false;
    private boolean SpacePressed = false;
    private long SpaceTime = 0;
    private long MoveRightTime = 0;
    private long MoveLeftTime = 0;

    public Client(String serverIP, String playerName) {
        super("Galaxius");
        this.serverIP = serverIP;
        this.playerName = playerName;

        setResizable(false);
        setSize(1024, 768);
        this.setPreferredSize(new Dimension(1024, 768));
        this.setMinimumSize(new Dimension(1024, 768));



        setBackground(Color.DARK_GRAY);

        board = new BoardG(this);
        chatRoom = new ChatRoom(this);
        board.setPreferredSize(new Dimension(770, 768));
        board.setMinimumSize(new Dimension(770, 768));

        add(board);

        multiView = new ShipSelector(this);
        playerInfo = new PlayerInfo(this);

        leftBar.setLayout(new BorderLayout());
        leftBar.setSize(254, 768);
        leftBar.setPreferredSize(new Dimension(254, 768));
        leftBar.setMinimumSize(new Dimension(254, 768));
        leftBar.setBackground(Color.DARK_GRAY);
        leftBar.add(multiView, BorderLayout.NORTH);
        leftBar.add(chatRoom, BorderLayout.CENTER);
        leftBar.add(playerInfo, BorderLayout.SOUTH);

        add(leftBar, BorderLayout.EAST);

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

    private void startListeners() {
        this.addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {

                if (state != 1) {
                    return;
                }

                if (e.getKeyCode() == KeyEvent.VK_LEFT && !LeftPressed) {
                    LeftPressed = true;


                    if (RightPressed) {
                        board.getMyShip().setMove(false, 90);
                        Action action = new Action(Action.MOVE_STOP);
                        action.time = (int) (System.currentTimeMillis() - MoveRightTime);
                        sendData(new Pack(Pack.ACTION, action));
                        RightPressed = false;
                    }

                    Action action = new Action(Action.MOVE_LEFT);
                    MoveLeftTime = System.currentTimeMillis();
                    board.getMyShip().setMove(true, 180);
                    sendData(new Pack(Pack.ACTION, action));


                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT && !RightPressed) {
                    RightPressed = true;


                    if (LeftPressed) {
                        LeftPressed = false;
                        board.getMyShip().setMove(false, 90);
                        Action action = new Action(Action.MOVE_STOP);
                        action.time = (int) (System.currentTimeMillis() - MoveLeftTime);
                        sendData(new Pack(Pack.ACTION, action));
                    }
                    Action action = new Action(Action.MOVE_RIGHT);
                    MoveRightTime = System.currentTimeMillis();
                    board.getMyShip().setMove(true, 0);
                    sendData(new Pack(Pack.ACTION, action));


                } else if (e.getKeyCode() == KeyEvent.VK_SPACE && !SpacePressed) {
                    SpacePressed = true;

                    SpaceTime = System.currentTimeMillis();
                    Action action = new Action(Action.ATTACK_BASIC);
                    action.x = board.getMyShip().getX();
                    sendData(new Pack(Pack.ACTION, action));

                } else if (e.getKeyCode() == KeyEvent.VK_S) {
                    showShipSelector();
                } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {

                    chatRoom.getFocus();

                } else if (e.getKeyCode() == KeyEvent.VK_N) {
                    board.showNames = true;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

                if (state != 1) {
                    return;
                }

                if (e.getKeyCode() == KeyEvent.VK_LEFT && LeftPressed) {
                    LeftPressed = false;
                    RightPressed = false;
                    board.getMyShip().setMove(false, 90);
                    Action action = new Action(Action.MOVE_STOP);
                    action.time = (int) (System.currentTimeMillis() - MoveLeftTime);
                    sendData(new Pack(Pack.ACTION, action));


                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT && RightPressed) {

                    RightPressed = false;
                    LeftPressed = false;
                    board.getMyShip().setMove(false, 90);
                    Action action = new Action(Action.MOVE_STOP);
                    action.time = (int) (System.currentTimeMillis() - MoveRightTime);
                    sendData(new Pack(Pack.ACTION, action));

                } else if (e.getKeyCode() == KeyEvent.VK_SPACE && SpacePressed) {
                    SpacePressed = false;

                    Action action = new Action(Action.ATTACK_BASIC_STOP);
                    action.time = (int) (System.currentTimeMillis() - SpaceTime);
                    sendData(new Pack(Pack.ACTION, action));
                } else if (e.getKeyCode() == KeyEvent.VK_N) {
                    board.showNames = false;
                }

            }
        });

    }

    private void unPack(Pack pack) {

        if (pack.isMessage()) {
            chatRoom.append((String) pack.unPack());
        } else if (pack.isShip()) {
            Ship ship = new Ship((Ship) pack.unPack());
            board.updateShip(ship);

        } else if (pack.isBullet()) {

            board.updateBullet((Bullet) pack.unPack());

        } else if (pack.isEvent()) {
            Event event = (Event) pack.unPack();
            board.updateShip(event);
            
        } else if (pack.isState()) {
            state = (Integer) pack.unPack();
            
        } else if (pack.isInit()) {
            board.setMyID((Integer) pack.unPack());
            connected = true;
            board.run();
            chatRoom.setChatEnabled(true);
            startListeners();
        }



    }

    private void connectToServer() throws IOException {

        client = new Socket(InetAddress.getByName(serverIP), 55555);

        output = new ObjectOutputStream(client.getOutputStream());
        output.flush();

        input = new ObjectInputStream(client.getInputStream());

        sendData(new Pack(Pack.NAME, playerName));

    }

    public void sendData(Pack packet) {
        try {
            output.writeObject(packet);

        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
