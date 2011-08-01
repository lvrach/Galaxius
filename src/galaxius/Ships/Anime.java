/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package galaxius.Ships;

import java.awt.Component;
import java.awt.Graphics;
import javax.swing.ImageIcon;

/**
 *
 * @author xalgra
 */
public class Anime {
    
     
        
        public final static  int RIGHT=0;
        public final static int LEFT=180;
        public final static int IDLE=90;
    
        private ImageIcon idle;
        private ImageIcon left;
        private ImageIcon right;
        
        private ImageIcon own_idle;
        private ImageIcon own_left;
        private ImageIcon own_right;      
        
        
        
        public Anime(String shipName)
        {
            own_idle=new ImageIcon(getClass().getResource(shipName+"/Own/Idle.png"));
            own_left=new ImageIcon(getClass().getResource(shipName+"/Own/Left.png"));
            own_right=new ImageIcon(getClass().getResource(shipName+"/Own/Right.png"));
            
            idle=new ImageIcon(getClass().getResource(shipName+"/Allies/Idle.png"));
            left=new ImageIcon(getClass().getResource(shipName+"/Allies/Left.png"));
            right=new ImageIcon(getClass().getResource(shipName+"/Allies/Right.png"));
            
            
        }
        public ImageIcon getImage(boolean owned,int direction)
        {
            if(owned)
            {
                
                if(direction==RIGHT)
                {
                    return own_left;
                }
                else if(direction==LEFT)
                {
                    return own_right;
                }
                 return own_idle;
            }
            else
            {
                 if(direction==RIGHT)
                {
                    return left;
                }
                else if(direction==LEFT)
                {
                    return right;
                }
                 return idle;
            }
        }
    
    
}
