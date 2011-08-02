/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package galaxius.Ships;

import galaxius.Bullet;
import galaxius.Pack;
import galaxius.Ship;
import galaxius.skills.Skill;
/**
 *
 * @author xalgra
 */
public class AI_0_Skill extends Skill{
    private static final int basicAttackColldownDefaul=300;
    private int basicAttackColldown=basicAttackColldownDefaul;
    protected Ship ship;
        
    public AI_0_Skill(Ship ship)
    {
        this.ship = ship;
        
    }    
    
    @Override
    public void basicAttack(int timePeriod) {
        
        basicAttackColldown -= timePeriod;
       if (basicAttackColldown <= 0) {

                basicAttackColldown = basicAttackColldownDefaul;
                Bullet bullet = new Bullet(ship);
                bullet.setDirection(270);
                bullet.setSpeed(250);
                bullet.setDamage(3+ship.getLevel()*5);
                bullets.add(bullet);
                informer.inform(new Pack(Pack.BULLET, bullet));
                
            }
    }

    @Override
    public void firstSkill(int timePeriod) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void secondSkill(int timePeriod) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void thirdSkill(int timePeriod) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void forthSkill(int timePeriod) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void basicAttackEnable(boolean state) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
