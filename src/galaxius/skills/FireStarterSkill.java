/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package galaxius.skills;

import galaxius.Bullet;
import galaxius.Pack;
import galaxius.Ship;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author xalgra
 */
public class FireStarterSkill extends Skill{

    private static final int basicAttackColldownDefaul=300;
    private int basicAttackColldown=0;
    protected Ship ship;
    private boolean basicAttackEnabled;
        
    public FireStarterSkill(Ship ship)
    {
        this.ship = ship;
        timer.addActionListener( new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                basicAttack(30);
            }
        });
        timer.start();
    }    
    
    @Override
    public void basicAttack(int timePeriod) {
        
        basicAttackColldown -= timePeriod;
        if (basicAttackEnabled && basicAttackColldown <= 0) {

                basicAttackColldown = basicAttackColldownDefaul;
                Bullet bullet = new Bullet(ship.getOwnerID(), ship);                
                bullet.setDamage(5+(ship.getLevel()*2));
                bullet.setSpeed(350+(ship.getLevel()*5));
                bullets.add(bullet);
                informer.inform(new Pack(Pack.BULLET, bullet));
         }
    }
    @Override
    public void basicAttackEnable(boolean state) {
        basicAttackEnabled=state;
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

    
    
}
