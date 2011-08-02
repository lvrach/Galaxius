/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package galaxius;

import galaxius.Ships.ShipLinker;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.Timer;

/**
 *
 * @author xalgra
 */
public class AIwave {

    private List<Ship> ships;
    private static List<Bullet> bullets;
    private static Random rand = new Random();
    private static ClientInformer clientInformer;
    private Timer AItimer;
    private int shipNumber;
    private int defaultShipNumber;
    private int level;
    private int aggressive;

    public static void setClientInformer(ClientInformer clientInformer) {
        AIwave.clientInformer = clientInformer;

    }

    public static void setBullets(List<Bullet> bullets) {
        AIwave.bullets = bullets;
    }

    public AIwave(int shipNumber,int level,int Aggressive) {

        this.level=level;
        this.aggressive=Aggressive;
        defaultShipNumber = shipNumber;
        ships = new ArrayList<Ship>();
        importShips();

        AItimer = new Timer(300, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                
                for (int i = 0; i < ships.size(); i++) {
                    
                    if (rand.nextInt((100-aggressive)+1) == 1) {
                        ships.get(i).getSkill().basicAttack(500);
                    }
                }
            }
        });
        AItimer.start();


    }
    Timer shipImportTimer;
    private void importShips() {
        
        if (defaultShipNumber - shipNumber >= 10) {
            for (int i = 0; i < 10; i++) {

                newShip(i,rand.nextInt(level));

            }
            shipImportTimer = new Timer(5000, new ActionListener(){

                @Override
                public void actionPerformed(ActionEvent e) {
                    importShips();
                }
            
            });
            shipImportTimer.setRepeats(false);
            shipImportTimer.start();
            
        }
        else 
        {
             for (int i = 0; i < defaultShipNumber - shipNumber ; i++) {

                newShip(i,rand.nextInt(level));

            }
        }
    }

    private void newShip(int x,int level) {
        shipNumber++;
        Ship ship = ShipLinker.newShip(0, -1);
        ship.setY(rand.nextInt(30));
        ship.setLevel(level);
        ship.setX(20 + rand.nextInt(30) + x * 70);
        ship.setMove(true, 270);
        ship.setSpeed(30+(level-1)*3);
        ship.setHP(10+(level-1)*10+rand.nextInt(5));
        ships.add(ship);
        clientInformer.inform(new Pack(Pack.SHIP, new Ship(ship)));
    }

    public void move(int timePeriod) {
        for (int i = 0; i < ships.size(); i++) {
            ships.get(i).move(timePeriod);
            if (!ships.get(i).exist()) {
                clientInformer.inform(new Pack(Pack.SHIP, new Ship(ships.get(i))));
                ships.remove(i);

            }
        }
    }

    public boolean hasShips() {
        return !ships.isEmpty();
    }

    public void interactive(Bullet bullet) {
        
        for (int i=0; i < ships.size(); i++) {
            if (!ships.get(i).exist()) {
                ships.remove(i);                
                break;
            }            
            if (ships.get(i).interact(bullet)) {
                
                clientInformer.inform(new Pack(Pack.SHIP, new Ship(ships.get(i))));
                break;
            }

        }

    }
}
