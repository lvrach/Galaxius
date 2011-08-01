/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package galaxius.Ships;

import galaxius.Ship;
import galaxius.skills.*;
import javax.swing.ImageIcon;

/**
 *
 * @author xalgra
 */
public  class ShipLinker {
  
   
    public static final String[] Names={"fire-Starter","Noxor"};
    private int Width=30;
    private int Height=50;    
            
    
    public static int size()
    {
        return Names.length;
    }    
    
    public static Skill newSkill(int shipTypeId,Ship ship)
    {
        switch (shipTypeId)
        {
            case 0:
                return new FireStarterSkill(ship);
            case 1:
                return new NoxorSkill(ship);
        }
        return null;
                
    }
    
    
    
}
