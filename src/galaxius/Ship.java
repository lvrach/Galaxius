/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package galaxius;


import galaxius.Ships.ShipLinker;
import galaxius.skills.AIsimpleSkill;
import galaxius.skills.FireStarterSkill;
import galaxius.skills.Skill;

/**
 *
 * @author xalgra
 */
public class Ship extends FlyObject {

    private int ownerID;
    private static int CountID = 0;
    private final int ID;
    private int typeID;
    
    public Skill skill;
    
    private int healpoints;
    private int healpointsMax=100;
    private int maxSpeed=180;   
    private int level;
    
    public Ship(int ownerID) {
        super(400, 650 ,20 ,20);
        this.ownerID = ownerID;
        ID = CountID;
        CountID++;
        healpoints =healpointsMax;  
        level=1;
        setType(0);
        if(ownerID>=0)
        skill = new FireStarterSkill(this);
        else
        skill = new AIsimpleSkill(this);
    }

    public Ship(Ship newShip) {
        super(newShip);
        ownerID = newShip.ownerID;
        ID = newShip.getID();
        healpoints =100;
        setType(newShip.getTypeID());
        level=1;
    }
    
    public void setType(int typeID)
    {
        this.typeID=typeID;
        skill = ShipLinker.newSkill(typeID, this);
    }
    
    public Skill getSkill()
    {
        return skill;
    }        
    public int getID() {
        return ID;
    }
    public int getTypeID()
    {
        return typeID;
    }
    public int getOwnerID() {
        return ownerID;
    }
    public int getLevel() {
        return level;
    } 
    public boolean is(Ship ship) {
        return ship.ID == ID;
    }

    @Override
    public String toString() {
        return "Ship id:" + ID + " owner ID:" + ownerID + " possision:(" + getX() + "," + getY() + ")";
    }
    
    
    public void setTypeID(int type) {
         typeID=type; 
    }
    
    public void setHP(int hp)
    {
        if(hp<0){
            this.delete();
            healpoints=0;
        }else{            
        healpoints=hp;
        }
    }
    public int getHP()
    {
        return healpoints;
    }
    public int getMaxHP()
    {
        return healpointsMax;
    }
    public void setMove(boolean moving,int direction)
    {
        if(moving)
        {
            super.setDirection(direction);
            super.setSpeed(maxSpeed);
        }
        else
        { 
            super.setDirection(90);
            super.setSpeed(0);
            
        }
    }
    
   
    
    public boolean move(int timePeriod) {
        
        if(super.getSpeed()!=0)
        {
            setX(getRealX() + Math.cos(Math.toRadians(getDirection())) * getSpeed() * timePeriod/1000);
            
            setY(getRealY() - Math.sin(Math.toRadians(getDirection())) * getSpeed() * timePeriod/1000 );
           return true;
            
        }
        return false;
        
    }
    
    public boolean interact(FlyObject object)//return if ther is need for inform
    {
        if(object instanceof Bullet)
        {
            Bullet bullet  = (Bullet) object;
             if(bullet.getPlayerID()<0 && this.getOwnerID()<0)
                    return false;
             if(bullet.getPlayerID()>=0 && this.getOwnerID()>=0)
                    return false;           
            
            if((Math.abs(bullet.getRealX()-super.getRealX())<15)&&
               (Math.abs(bullet.getRealY()-super.getRealY())<20))
            {
                 if(getHP()>bullet.getDamage())
                 {
                     setHP(getHP()-bullet.getDamage());
                 }else{
                
                    
                    delete();
                    
                 }
                 bullet.delete();
                 return true;
            }
        }
        return false;
    }
    
}
