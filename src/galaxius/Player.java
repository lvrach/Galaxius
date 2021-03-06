/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package galaxius;

import galaxius.Ships.ShipLinker;
import java.awt.Dimension;
import java.io.EOFException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author xalgra
 */
public class Player implements Runnable {

    private ObjectInputStream input;
    private ObjectOutputStream output;
    private Socket playerClient;
    private ClientInformer clientInform;
    private static int idCount = 0;
    private ArrayBlockingQueue<Pack> buffer;
    private static ExecutorService sender = Executors.newCachedThreadPool();
    private final int ID;
    private Ship ship;
    
    private int state = 0;
    private final static int WAIT = 0;
    private final static int PLAY = 1;
    private final static int OBSERVER = 2;
    private final static int DEAD = 3;
    
    private boolean basicAttackSelected;
    private int basicAttackDuration;
    private int basicAttackSpend;
    private boolean moveSelected;
    private int moveDuration;
    private int moveSpend;
    private boolean existan = true;

    public Player(Socket client, ClientInformer inform) {
        clientInform = inform;
        buffer = new ArrayBlockingQueue<Pack>(100);

        ID = idCount;
        idCount++;
        ship = ShipLinker.newShip(0,ID);

        try {

            input = new ObjectInputStream(client.getInputStream());
            output = new ObjectOutputStream(client.getOutputStream());
            output.flush();            
            sendData(new Pack("wellcome"));
            sendData(new Pack(Pack.INIT, this.getShip().getID()));
            clientInform.inform(new Pack(Pack.EVENT, new Event(Event.HP_Change, ship.getHP(), ship.getID())));
            setState(Player.PLAY);
        } catch (IOException ex) {
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
        }

        sender.execute(new Runnable() {

            @Override
            public void run() {
                while (true) {

                    try {
                        while (!buffer.isEmpty()) {
                            output.writeObject(buffer.take());
                            output.flush();
                        }
                        Thread.sleep(1);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
                    }



                }
            }
        });


    }

    public void setState(int state) {
        this.state = state;
        sendData(new Pack(Pack.STATE,state));
        
    }

    public void selectBasicAttack(boolean selection) {
        basicAttackSelected = selection;
    }

    public boolean isBasicAttackSelected() {
        return basicAttackSelected;
    }

    public Ship getShip() {
        return ship;
    }

    public int getID() {
        return ID;
    }

    public boolean exist() {
        return existan;
    }

    public void doMove(int timePeriod) {
        moveSpend += timePeriod;

        if (moveSelected || moveSpend < moveDuration) {
            ship.move(timePeriod);
        } else {

            ship.setMove(false, 90);
            clientInform.inform(new Pack(Pack.SHIP, new Ship(ship)), getID());
            moveSpend = 0;
            moveDuration = 0;
        }


    }

    public void doActions(int timePeriod) {

        basicAttackSpend += timePeriod;


        if (isBasicAttackSelected() || basicAttackSpend < basicAttackDuration) {
        } else {
            basicAttackSpend = 0;
            basicAttackDuration = 0;
            ship.getSkill().basicAttackEnable(false);
        }


    }

    public void sendData(Pack pack) {
        try {

            buffer.put(pack);
        } catch (InterruptedException ex) {
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {

        try {
            while (true) {
                if (state == Player.PLAY) {


                    Pack pack = (Pack) input.readObject();

                    if (pack.isAction()) {

                        Action tempAction = (Action) pack.unPack();

                        if (tempAction.isMoveLeft()) {

                            ship.setMove(true, 180);
                            moveSelected = true;
                            clientInform.inform(new Pack(Pack.SHIP, new Ship(ship)), getID());

                        } else if (tempAction.isMoveRight()) {
                            ship.setMove(true, 0);
                            moveSelected = true;
                            clientInform.inform(new Pack(Pack.SHIP, new Ship(ship)), getID());

                        } else if (tempAction.isMoveStop()) {

                            moveSelected = false;
                            moveDuration = tempAction.time;

                        } else if (tempAction.isAttackBasic()) {
                            this.selectBasicAttack(true);
                            ship.setX(tempAction.x);
                            ship.getSkill().basicAttackEnable(true);



                        } else if (tempAction.isAttackStop()) {
                            this.selectBasicAttack(false);
                            this.basicAttackDuration = tempAction.time;


                        } else if (tempAction.isShipSelect()) {
                            int tempLevel =ship.getLevel();
                            int tempID = ship.getID();
                            String pilotName = ship.pilotName;
                            ship = ShipLinker.newShip(tempAction.x,getID());
                            ship.setLevel(tempLevel);
                            ship.setID(tempID);
                            ship.pilotName=pilotName;
                            clientInform.inform(new Pack(Pack.SHIP, new Ship(ship)));
                        }


                    } else if (pack.isMessage()) {
                        clientInform.inform(new Pack(ship.pilotName + ":" + pack.unPack()));
                    } else if (pack.isName()) {
                        ship.pilotName = (String) pack.unPack();
                        clientInform.inform(new Pack(Pack.SHIP, new Ship(ship)));
                        clientInform.inform(new Pack("*Commander*:" + (String) pack.unPack() + " join the team"));
                    }
                }

            }
        } catch (IOException ex) {
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            close();
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            close();
        }



    }

    private void close() {

        ship.delete();
        existan = false;

        try {

            playerClient.close();
            this.finalize();


        } catch (Throwable ex1) {
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex1);
        }
    }
}
