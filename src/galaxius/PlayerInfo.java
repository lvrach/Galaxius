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
    
    
    public PlayerInfo(Client newClient)
    {
        client = newClient;
        this.setSize(250, 150);
        this.setPreferredSize( new Dimension(250,150));        
        setOpaque(false);
        this.setBackground(Color.DARK_GRAY);
        
    }  
    @Override
    public void paint(Graphics g)
    {
        
        if(client.board.getMyShip() != null)
        {              
            int Hp = client.board.getMyShip().getHP();
            int MaxHp = client.board.getMyShip().getMaxHP();
            g.setColor(Color.GREEN);
            g.fillRect(10,30, (Hp*200/MaxHp),20);
            g.setColor(Color.BLACK);
            g.drawRect(10,30,200,21);
            g.setColor(Color.WHITE);
            g.drawString("HP:"+Hp+"/"+MaxHp, 90 , 45);
        
        }
         
    }
}
