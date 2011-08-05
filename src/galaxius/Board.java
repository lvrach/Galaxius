/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package galaxius;

import galaxius.Debris.Debris;
import galaxius.Debris.Health_Debris;
import galaxius.skills.Skill;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.Timer;

/**
 *
 * @author xalgra
 */
public class Board {

    private AIwave wave;
    private List<Player> players;
    private List<Bullet> bullets;
    private List<Debris> debrises;
    private ClientInformer clientInformer;
    private Timer SimClock;
    private Timer waveClock;
    


    public Board() {
        
        
        players = new ArrayList<Player>();
        players = Collections.synchronizedList(players);        
        
        clientInformer = new ClientInformer(players);
        bullets = new ArrayList<Bullet>();
        bullets = Collections.synchronizedList(bullets);
        
        debrises = new ArrayList<Debris>();
        debrises = Collections.synchronizedList(debrises);
        
        
        Skill.setBullets(bullets);
        Skill.setClientInformer(clientInformer);
        
        AIwave.setBullets(bullets);
        AIwave.setDebrises(debrises);
        AIwave.setClientInformer(clientInformer);
        
        SimClock = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                simulator();
            }
        });
        
        wave = new AIwave(0,1,20,1);
        AIsimulator();
        SimClock.start();

    }

    public void addPlayer(Socket playerSocket) {
        Player player = new Player(playerSocket, clientInformer);

        Thread playerThread = new Thread(player);
        players.add(player);
        playerThread.setName("Player"+player.getID());
        playerThread.start();

        
        clientInformer.inform();


    }

    private int[] wave_size = {8,10,10,10,10,15,8,10,10,15};
    private int[] wave_level ={1,1,1 ,2,2 ,2 ,3,3 ,3 ,3 };
    private int[] wave_aggressive ={80,90,95 ,60,70 ,95 ,90,90 ,95 ,88 };
    
    private int wave_count=0;
    
    private void AIsimulator()
    {
        
         waveClock = new Timer(2500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!wave.hasShips())
                {
                    wave = new AIwave(wave_size[wave_count],wave_level[wave_count],wave_aggressive[wave_count],players.size()-1);
                    wave_count++;
                    if(wave_count>=wave_size.length)
                       wave_count=0;
                    
                }
            }
        });
        waveClock.start();
        
    }
    private void simulator() {

        shipMoveSimulator();
        bulletMoveSimulator();
        debrisMoveSimulator();
        wave.move(SimClock.getDelay());
             

    }

    private void shipMoveSimulator() {
        for (int i = 0; i < players.size(); i++) {
            players.get(i).doMove(SimClock.getDelay());           
            
            players.get(i).doActions(SimClock.getDelay());
             if (!players.get(i).exist()) {
                    players.remove(i);
                    clientInformer.inform(new Pack(Pack.SHIP, new Ship(players.get(i).getShip())));
                    
                }

        }
    }

    private void debrisMoveSimulator()
    {
        for (int j = 0; j < debrises.size(); j++) {
 
                Debris debris = debrises.get(j);
                debris.move(SimClock.getDelay());
                
                if (debris.exist()) {
                     for (int i = 0; i < players.size(); i++) {
                        players.get(i).getShip().interact(debris);
                        clientInformer.inform(new Pack( Pack.EVENT,new Event(Event.HP_Change,
                                                                               players.get(i).getShip().getHP(),
                                                                               players.get(i).getShip().getID()) 
                                ));
                    }
                }
                else
                {
                   debrises.remove(j);
                   clientInformer.inform(new Pack(Pack.DEBRIS, new Health_Debris(debris) ));
                   System.out.println(j);
                }
                
        }
    }
    private void bulletMoveSimulator() {
                 
            
            
            for (int j = 0; j < bullets.size(); j++) {
 
                Bullet bullet = bullets.get(j);
                bullet.move(SimClock.getDelay());
                
                if (bullet.exist()) {
                    wave.interactive(bullet);
                    
                    for (int i = 0; i < players.size(); i++) {
                        players.get(i).getShip().interact(bullet);
                        clientInformer.inform(new Pack( Pack.EVENT,new Event(Event.HP_Change,
                                                                               players.get(i).getShip().getHP(),
                                                                               players.get(i).getShip().getID()) 
                                ));
                    }
                }else{
                    bullets.remove(j);
                    clientInformer.inform(new Pack(Pack.BULLET, new Bullet(bullet)));
                }
                    

            }
        


    }
}
