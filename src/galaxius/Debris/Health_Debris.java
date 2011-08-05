/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package galaxius.Debris;

import galaxius.FlyObject;
import galaxius.Ship;

/**
 *
 * @author xalgra
 */
public class Health_Debris extends Debris{
    
    public Health_Debris(FlyObject baseObject)
    {
        super(baseObject);
    
    }
    public Health_Debris(Debris baseDebris)
    {
        super(baseDebris);
    
    }

    @Override
    public void interact(Ship ship) {
        ship.setHP(ship.getHP()+5);
        delete();
    }
}
