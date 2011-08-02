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
public class AI_0_Ship extends Ship {
    
    public AI_0_Ship()
    {
        super(-1);
        
        skill=new AI_0_Skill(this);
    }
            
}
