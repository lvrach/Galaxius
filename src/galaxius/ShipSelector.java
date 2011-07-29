/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package galaxius;

import galaxius.Ships.Anime;
import galaxius.Ships.ShipType;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author xalgra
 */
public class ShipSelector extends JPanel {
   
    
    Client client;
    
    public ShipSelector(Client newClient)
    {
        client = newClient;
        
       
        this.setBackground(Color.DARK_GRAY);
        JLabel Title = new JLabel("Select Ship:");        
        add(Title,BorderLayout.NORTH);
        JPanel ShipsPanel =new JPanel();
        
        
        
        for(int i=0;i<ShipType.size();i++)
        {
            Anime Animator= new Anime(ShipType.Names[i]);
            JButton button = new JButton();
            button.setActionCommand( Integer.toString(i));           
            button.setIcon(Animator.getImage(false, Anime.IDLE));
            button.setFocusable(false);
            button.setMinimumSize(new Dimension(50,50));
            button.setPreferredSize(new Dimension(50,50));
            button.addActionListener( new ActionListener(){

                @Override
                public void actionPerformed(ActionEvent e) {
                    Action action = new Action(Action.SHIP_SELECT);
                    
                    action.x=Integer.parseInt(e.getActionCommand());
                    client.sendData(new Pack(Pack.ACTION,action));
                    
                }
            });         
            ShipsPanel.add(button);
            
            
        }
        add(ShipsPanel,BorderLayout.CENTER);
        
        
    }
    
    
                
    
    
    
    
    
}
