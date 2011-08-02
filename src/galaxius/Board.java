/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package galaxius;

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
    private ClientInformer clientInformer;
    private Timer SimClock;
    private Timer waveClock;
    


    public Board() {
        
        
        players = new ArrayList<Player>();
        players = Collections.synchronizedList(players);        
        
        clientInformer = new ClientInformer(players);
        bullets = new ArrayList<Bullet>();
        bullets = Collections.synchronizedList(bullets);
        
        Skill.setBullets(bullets);
        Skill.setClientInformer(clientInformer);
        AIwave.setClientInformer(clientInformer);
        AIwave.setBullets(bullets);
        
        
        SimClock = new Timer(30, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                simulator();
            }
        });
        wave = new AIwave(0,1,20);
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

    private int[] wave_size = {8,10,10,10,10,15,8,10,15,20};
    private int[] wave_level ={1,1,1 ,2,2 ,2 ,3,3 ,3 ,3 };
    private int[] wave_aggressive ={80,90,90 ,60,70 ,98 ,90,90 ,100 ,88 };
    private int wave_count=0;
    
    private void AIsimulator()
    {
        
         waveClock = new Timer(2500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!wave.hasShips())
                {
                    wave = new AIwave(wave_size[wave_count]*players.size(),wave_level[wave_count],wave_aggressive[wave_count]);
                    wave_count++;
                    if(wave_count>=wave_size.length)
                       waveClock.stop();
                    
                }
            }
        });
        waveClock.start();
        
    }
    private void simulator() {

        shipMoveSimulator();
        bulletMoveSimulator();
        wave.move(30);
             

    }

    private void shipMoveSimulator() {
        for (int i = 0; i < players.size(); i++) {
            players.get(i).doMove(30);           
            
            players.get(i).doActions(30);
             if (!players.get(i).exist()) {
                    players.remove(i);
                    clientInformer.inform(new Pack(Pack.SHIP, new Ship(players.get(i).getShip())));
                    
                }

        }
    }

    private void bulletMoveSimulator() {
                 
            
            
            for (int j = 0; j < bullets.size(); j++) {
 
                Bullet bullet = bullets.get(j);
                bullet.move(30);
                
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
