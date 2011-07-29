/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package galaxius;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author xalgra
 */
public class PlayerInfo extends JPanel{
    
    Client client;
    Timer repaintTimer;
    public int Hp;
    public int MaxHp;
    
    public PlayerInfo(Client newClient)
    {
        this.setSize(200, 300);
        this.setPreferredSize( new Dimension(200,300));
        this.client = newClient;
        
             Hp=0;
             MaxHp=100;  
        setOpaque(false);
        this.setBackground(Color.DARK_GRAY);
        
    }
    
    
   public void setHP(int hp)
   {
       Hp=hp;
       repaint();
   }
    
    @Override
    public void paint(Graphics g)
    {
        
        if(client.board.getMyShip()!=null)
        {              
            
            g.setColor(Color.GREEN);
            g.fillRect(10,30, (Hp*200/MaxHp),20);
            g.setColor(Color.BLACK);
            g.drawRect(10,30,200,21);
            g.setColor(Color.WHITE);
            g.drawString("HP:"+Hp+"/"+MaxHp, 90 , 45);
        
        }
         
    }
}
