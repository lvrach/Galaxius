/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package galaxius;

import galaxius.Ships.Anime;
import galaxius.Ships.AnimeAI;
import galaxius.Ships.ShipLinker;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.xml.stream.events.Namespace;

/**
 *
 * @author xalgra
 */
public class BoardG extends JPanel {

    private List<Ship> ships;
    private List<Bullet> bullets;
    private Timer screenFreq;
    private int myID = 0;
    private Anime[] Animator;
    private AnimeAI AnimatorAI;
    Client client;
    private ImageIcon background;
    
    public BoardG(Client client) {
        this.client = client;
        ships = new ArrayList<Ship>();
        bullets = new ArrayList<Bullet>();
        ships=Collections.synchronizedList(ships);
        bullets=Collections.synchronizedList(bullets);
        
        //load ship animes
        Animator = new Anime[ShipLinker.size()];
        for (int i = 0; i < ShipLinker.size(); i++) {
            Animator[i] = new Anime(ShipLinker.Names[i]);
        }

        //load AIship animes
        AnimatorAI = new AnimeAI ();
background=new ImageIcon(getClass().getResource("background.png"));
        setSize(770, 768);
        setPreferredSize(new Dimension(770, 768));
        setMinimumSize(new Dimension(770, 768));  
        setMaximumSize(new Dimension(770, 768));
    }

    public void run() {

        screenFreq = new Timer(30, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < bullets.size(); i++) {
                    bullets.get(i).move(30);
                }
                for (int i = 0; i < ships.size(); i++) {
                    ships.get(i).move(30);
                }
                
                repaint();
            }
        });

        screenFreq.start();
    }

    public void setMyID(int newID) {
        myID = newID;
        
    }

    public Ship getShip(int id) {
        for (int i = 0; i < ships.size(); i++) {
            if (ships.get(i).getID() == id) {
                return ships.get(i);
            }
        }
        return null;
    }
    public Ship getMyShip()
    {
        
        return  getShip(myID);
        
    }

    public synchronized void updateShip(Ship newShip) {


        for (int i = 0; i < ships.size(); i++) {
            if (ships.get(i).is(newShip)) {

                ships.remove(i);
                
            }

        }
        if (newShip.exist()) {
            ships.add(newShip);
            client.playerInfo.repaint();
        }

    }
     public void updateShip( Event event)
     {
       
         if(getShip(event.getID())!=null)
        {
         if ( event.isHpChange())
         {
             
             getShip(event.getID()).setHP(event.getValue());
             
             if(event.getID()== myID)
             {
                 client.playerInfo.repaint();
             }
         }
        }
     }

    public void updateBullet(Bullet newBullet) {

        for (int i = 0; i < bullets.size(); i++) {
            if (bullets.get(i).is(newBullet)) {

                bullets.remove(i);

            }

        }

        if (newBullet.exist()) {
            bullets.add(newBullet);

        }


    }

    @Override
    public void paint(Graphics g) {
        background.paintIcon(this, g, 0, 0);
        paintShips(g);
        paintBullets(g);
        
    }

    public void paintShips(Graphics g) {
        for (int i = 0; i < ships.size(); i++) {
            drawShip(g, ships.get(i));
        }
    }

    private void drawShip(Graphics g, Ship ship) {
        if (ship.getID() == myID) {
            if (ship.getDirection() == 180) {
                Animator[ship.getTypeID()].getImage(true, Anime.LEFT).paintIcon(this, g, ship.getX() - ship.getWidth() / 2, ship.getY() - ship.getHeight()/2);
            } else if (ship.getDirection() == 0) {
                Animator[ship.getTypeID()].getImage(true, Anime.RIGHT).paintIcon(this, g, ship.getX() - ship.getWidth() / 2, ship.getY() - ship.getHeight()/2);
            } else {
                Animator[ship.getTypeID()].getImage(true, Anime.IDLE).paintIcon(this, g, ship.getX() - ship.getWidth() / 2, ship.getY() - ship.getHeight()/2);
            }   
        } else if (ship.getOwnerID() < 0) {
           AnimatorAI.getImage(ship).paintIcon(this, g, ship.getX() - ship.getWidth() / 2, ship.getY());  
           
        } else {
            
            if (ship.getDirection() == 180) {
                Animator[ship.getTypeID()].getImage(false, Anime.LEFT).paintIcon(this, g, ship.getX() - ship.getWidth() / 2, ship.getY() - ship.getHeight()/2);
            } else if (ship.getDirection() == 0) {
                Animator[ship.getTypeID()].getImage(false, Anime.RIGHT).paintIcon(this, g, ship.getX() - ship.getWidth() / 2, ship.getY() - ship.getHeight()/2);
            } else {
                Animator[ship.getTypeID()].getImage(false, Anime.IDLE).paintIcon(this, g, ship.getX() - ship.getWidth() / 2, ship.getY() - ship.getHeight()/2);
            }
            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(ship.getX()-ship.getWidth()/2, 4+ship.getY()+ship.getHeight()/2,ship.getWidth(), 4);
            g.setColor(Color.GREEN);
            g.fillRect(ship.getX()-ship.getWidth()/2, 4+ship.getY()+ship.getHeight()/2,ship.getHP()*ship.getWidth()/ship.getMaxHP(), 4);
            
        }
    }

    public void paintBullets(Graphics g) {
        for (int i = 0; i < bullets.size(); i++) {
            drawBullet(g, bullets.get(i));
        }
    }

    private void drawBullet(Graphics g, Bullet bullet) {
        if (!bullet.exist()) {
            bullets.remove(bullet);
        }

        g.setColor(Color.ORANGE);
        g.fillOval(bullet.getX(), bullet.getY(), 5, 5);
    }
}
