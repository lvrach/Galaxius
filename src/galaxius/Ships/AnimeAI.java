/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package galaxius.Ships;

import galaxius.Ship;
import javax.swing.ImageIcon;

/**
 *
 * @author xalgra
 */
public class AnimeAI {
    
     public final static int RIGHT=0;
     public final static int LEFT=180;
     public final static int MOVE=270;
    
     public final static int AI_SHIPS=2;
     private final static int MAX_LEVEL=3;
     private final static String directory="AIShips/";     
     private ImageIcon[][] move;
     private ImageIcon[][] left;
     private ImageIcon[][] right;
     
     
     
     public AnimeAI()
     {
         move =new ImageIcon[AI_SHIPS][MAX_LEVEL];
         left =new ImageIcon[AI_SHIPS][MAX_LEVEL];
         right =new ImageIcon[AI_SHIPS][MAX_LEVEL];
         
         for(int i=0;i<AI_SHIPS;i++ )
         {
             for(int j=0;j<MAX_LEVEL;j++)
             {
                 move[i][j]=new ImageIcon(getClass().getResource(directory+i+"/Level "+(j+1)+"/move.png"));
                 left[i][j]=new ImageIcon(getClass().getResource(directory+i+"/Level "+(j+1)+"/move_Left.png"));
                 right[i][j]=new ImageIcon(getClass().getResource(directory+i+"/Level "+(j+1)+"/move_Right.png"));
                 
             }
         }
     }
     
     public ImageIcon getImage(int id,int level ,float direction)
     {
         level--;
          if(direction==RIGHT)  
              return right[id][level];
          else if(direction==LEFT)
              return left[id][level];
          else 
              return move[id][level];
          
     }
     public ImageIcon getImage(Ship ship)
     {
         return getImage(ship.getTypeID(),ship.getLevel(),ship.getDirection());
     }
}
