/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package galaxius;


import galaxius.skills.Skill;

/**
 *
 * @author xalgra
 */
public class Ship extends FlyObject {

    private int ownerID;
    private static int CountID = 0;
    private int ID;
    private int typeID;
    
    public Skill skill;
    public String pilotName="";
    
    private int healpoints;
    private int healpointsMax=100;
    private int maxSpeed=180;   
    private int level;
   
    public Ship(int ownerID) {
        super(400, 700 ,20 ,20);
        this.ownerID = ownerID;
        ID = CountID;
        CountID++;
        healpoints =healpointsMax;  
        level=1;
             
    }
    
   
    public Ship(Ship newShip) {
        super(newShip);
        ownerID = newShip.ownerID;
        ID = newShip.getID();
        healpoints =100;
        skill=newShip.skill;
        level=newShip.getLevel();
        pilotName=newShip.pilotName;
       typeID=newShip.typeID;
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
    public int getHP()
    {
        return healpoints;
    }
    public int getMaxHP()
    {
        return healpointsMax;
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
     public void setLevel(int level)
    {
        this.level=level;
    }
     public void setID(int ID)
    {
        this.ID = ID;
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
            
            if((Math.abs(bullet.getRealX()-super.getRealX())<25)&&
               (Math.abs(bullet.getRealY()-super.getRealY())<25))
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
