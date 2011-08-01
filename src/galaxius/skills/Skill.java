/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package galaxius.skills;

import galaxius.Bullet;
import galaxius.ClientInformer;
import galaxius.Ship;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.List;
import javax.swing.Timer;

/**
 *
 * @author xalgra
 */
public  abstract class Skill implements Serializable{
    
    protected static List<Bullet> bullets;             
    protected static ClientInformer informer;
    protected static Timer timer=new Timer(30, new ActionListener(){

        @Override
        public void actionPerformed(ActionEvent e) {
            
        }
    });
    
    
    public static void setClientInformer(ClientInformer informer)
    {
        Skill.informer = informer;        
    }
    public static void setBullets(List<Bullet> bullets)
    {
        Skill.bullets = bullets;
    }   
  
    
    
    
    public abstract void basicAttack(int timePeriod);
    
    public abstract void basicAttackEnable(boolean state);
    
    public abstract void firstSkill(int timePeriod);
    
    public abstract void secondSkill(int timePeriod);
    
    public abstract void thirdSkill(int timePeriod);
    
    public abstract void forthSkill(int timePeriod); 
    
    
}
