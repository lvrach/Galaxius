/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package galaxius.Ships;

import galaxius.Ship;

/**
 *
 * @author xalgra
 */
public class Fire_Starter_Ship extends Ship{
    
    public Fire_Starter_Ship(int ownerID)
    {
        super(ownerID);
        skill = new Fire_Starter_Skill(this);
        setWidth(40);
        setHeight(50);        
        
    }
     
   
    
    
}
