/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package galaxius;

/**
 *
 * @author xalgra
 */
public class Bullet extends FlyObject {

    private final int ID;
    private static int idCounter = 0;
    private int ownerID;
    private int shipID;
    private int damage;

    

    public Bullet(Ship ship) {
        super(ship.getX(), ship.getY(),3,3, 90, 300);        
        ID = idCounter;
        idCounter++;
        this.ownerID = ship.getOwnerID();
        this.shipID = ship.getID();
        this.damage = 1;

    }
    
    public Bullet(Bullet bullet)
    {
        super(bullet);
        ID = bullet.getID();
        ownerID = bullet.getPlayerID();
        shipID = bullet.getShipID();
        damage = bullet.getDamage();        
              
    }

    public void move(int timePeriod) {        
        
        setX(getRealX() + (Math.cos(Math.toRadians(getDirection())) * getSpeed() * timePeriod / 1000));
        
        setY(getRealY() - (Math.sin(Math.toRadians(getDirection())) * getSpeed() * timePeriod / 1000));
    }

    public void setDamage(int damage){
        this.damage=damage;
    }

    public int getDamage() {
        return damage;
    }
    public int getPlayerID() {
        return ownerID;
    }

    public int getShipID() {
        return shipID;
    }
    public int getID()
    {
        return ID;
    }
    public boolean is(Bullet compare)
    {
        return compare.getID()==getID();
    }
    @Override
    public String toString()
    {
        return "Bullet("+getX()+","+getY()+")";
    }
            
}
