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
public class Noxor_Ship extends Ship{
     
    public Noxor_Ship(int ownerID)
    {
        super(ownerID);
        skill = new Noxor_Skill(this);
        setWidth(40);
        setHeight(50);        
        super.setTypeID(1);
        
    }
}
