/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package galaxius;

import galaxius.Ships.Anime;
import galaxius.Ships.AnimeAI;
import galaxius.Ships.ShipType;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
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

    public BoardG(Client client) {
        this.client = client;
        ships = new ArrayList<Ship>();
        bullets = new ArrayList<Bullet>();
        
        //load ship animes
        Animator = new Anime[ShipType.size()];
        for (int i = 0; i < ShipType.size(); i++) {
            Animator[i] = new Anime(ShipType.Names[i]);
        }

        //load AIship animes
        AnimatorAI = new AnimeAI ();

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

    public synchronized void setMyID(int newID) {
        myID = newID;
    }

    public synchronized Ship getMyShip() {
        for (int i = 0; i < ships.size(); i++) {
            if (ships.get(i).getID() == myID) {
                return ships.get(i);
            }
        }
        return null;
    }

    public synchronized void updateShip(Ship newShip) {


        for (int i = 0; i < ships.size(); i++) {
            if (ships.get(i).is(newShip)) {

                ships.remove(i);

            }

        }
        if (newShip.exist()) {
            ships.add(newShip);
        }

    }

    public synchronized void updateBullet(Bullet newBullet) {

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
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 770, 768);
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
                Animator[ship.getTypeID()].getImage(true, Anime.LEFT).paintIcon(this, g, ship.getX() - ship.getWidth() / 2, ship.getY() - 40);
            } else if (ship.getDirection() == 0) {
                Animator[ship.getTypeID()].getImage(true, Anime.RIGHT).paintIcon(this, g, ship.getX() - ship.getWidth() / 2, ship.getY() - 40);
            } else {
                Animator[ship.getTypeID()].getImage(true, Anime.IDLE).paintIcon(this, g, ship.getX() - ship.getWidth() / 2, ship.getY() - 40);
            }

        } else if (ship.getOwnerID() < 0) {
           AnimatorAI.getImage(ship).paintIcon(this, g, ship.getX() - ship.getWidth() / 2, ship.getY());           
        } else {
            if (ship.getDirection() == 180) {
                Animator[ship.getTypeID()].getImage(false, Anime.LEFT).paintIcon(this, g, ship.getX() - ship.getWidth() / 2, ship.getY() - 40);
            } else if (ship.getDirection() == 0) {
                Animator[ship.getTypeID()].getImage(false, Anime.RIGHT).paintIcon(this, g, ship.getX() - ship.getWidth() / 2, ship.getY() - 40);
            } else {
                Animator[ship.getTypeID()].getImage(false, Anime.IDLE).paintIcon(this, g, ship.getX() - ship.getWidth() / 2, ship.getY() - 40);
            }
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
