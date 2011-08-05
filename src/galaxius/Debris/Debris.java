/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package galaxius.Debris;

import galaxius.FlyObject;
import galaxius.Ship;
import java.io.Serializable;

/**
 *
 * @author xalgra
 */
public abstract class  Debris extends FlyObject {
    
    public int ID;
    private static int idCounter = 0;
    
    public Debris(FlyObject baseObject)
    {
        super(baseObject.getX(),baseObject.getY(),10,10,baseObject.getDirection(),baseObject.getSpeed()); 
        ID=idCounter;
        idCounter++;       
    }
     public Debris(Debris baseObject)
    {
        super(baseObject);
        ID=baseObject.ID;
    }
    
    public abstract void interact(Ship ship);
    
    public int getID()
    {
        return ID;
    }
    public boolean is(Debris compare)
    {
        return compare.getID()==getID();
    }
    public void move(int timePeriod) {        
        
        setX(getRealX() + (Math.cos(Math.toRadians(getDirection())) * getSpeed() * timePeriod / 1000));
        
        setY(getRealY() - (Math.sin(Math.toRadians(getDirection())) * getSpeed() * timePeriod / 1000));
    }
   
       
    
    
}
