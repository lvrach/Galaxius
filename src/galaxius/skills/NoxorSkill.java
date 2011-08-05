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
public class NoxorSkill extends Skill{

    private static final int basicAttackColldownDefaul=800;
    private int basicAttackColldown=0;
    private boolean basicAttackEnabled;
    
    protected Ship ship;
    
    public NoxorSkill(Ship ship){
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
                
                Bullet bullet = new Bullet(ship);                
                bullet.setDamage(10+(ship.getLevel()*3));
                bullet.setSpeed(300+(ship.getLevel()*4));
                bullet.setDirection(94);
                
                Bullet bullet2 = new Bullet(ship);   
                bullet2.setDirection(86);
                bullet2.setDamage(10+(ship.getLevel()*3));
                bullet2.setSpeed(300+(ship.getLevel()*4));
                bullet2.setX(bullet.getX()+5);
                bullets.add(bullet);
                bullets.add(bullet2);
                informer.inform(new Pack(Pack.BULLET, bullet));
                informer.inform(new Pack(Pack.BULLET, bullet2));
                
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
        basicAttackEnabled=state;
        System.out.println(state);
    }
    
}
