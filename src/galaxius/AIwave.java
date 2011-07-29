/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package galaxius;

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
     
    public static void setClientInformer(ClientInformer clientInformer)
    {
        AIwave.clientInformer = clientInformer;
        
    }
    public static void setBullets(List<Bullet> bullets)
    {
        AIwave.bullets=bullets;
    }
    
    public AIwave(int shipNumber) {

        
        ships = new ArrayList<Ship>();
        
        for (int i = 0; i < shipNumber; i++) {

            newShip();
            
        }
        AItimer = new Timer(500, new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                
                 for (int i = 0; i < ships.size(); i++) {
                     
                     if(rand.nextInt(10)==1)
                     ships.get(i).getSkill().basicAttack(500);
                 }
            }
        });
        AItimer.start();
        

    }
    private void  newShip()
    {
            Ship ship = new Ship(-1);
            ship.setY(0);
            ship.setX(5+rand.nextInt(20)*30);    
            ship.setMove(true, 270);
            ship.setSpeed(30);
            ship.setHP(5);
            ships.add(ship);
            clientInformer.inform(new Pack( Pack.SHIP, new Ship(ship) ));
    }

    public void move(int timePeriod) {
        for (int i = 0; i < ships.size(); i++) {
            ships.get(i).move(timePeriod);
            if(!ships.get(i).exist())
            {
                clientInformer.inform(new Pack(Pack.SHIP, new Ship(ships.get(i))));
                ships.remove(i);
                
            }
        }
    }
    public boolean  hasShips()
    {
        return !ships.isEmpty();
    }
            
    public void interactive(Bullet bullet) {
        for (int i = 0; i < ships.size(); i++) {
            if(!ships.get(i).exist())
            {
                ships.remove(i);
                break;
            }
            if(ships.get(i).interact(bullet))
            {
                clientInformer.inform(new Pack(Pack.SHIP, new Ship(ships.get(i))));                
                break;
            }
            
        }
        
    }
    
}
