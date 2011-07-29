/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package galaxius;

import java.io.Serializable;

/**
 *
 * @author xalgra
 */
public class Action implements Serializable {

    public static final int MOVE = 0;
    public static final int MOVE_RIGHT = 1;
    public static final int MOVE_LEFT = 2;
    public static final int MOVE_STOP = 3;
    public static final int ATTACK_BASIC = 4;
    public static final int ATTACK_BASIC_STOP =5; 
    public static final int SHIP_SELECT =10;
    public int x;
    public int y;    
    private int actionCode;
    public int time;

    public Action(int code) {
        actionCode = code;
    }

    public int getCode() {
        return actionCode;
    }

    public boolean isMoveRight() {
        return (actionCode == MOVE_RIGHT);
    }
    public boolean isMove() {
        return (actionCode == MOVE);
    }
    public boolean isMoveLeft() {
        return (actionCode == MOVE_LEFT);
    }
     public boolean isMoveStop() {
        return (actionCode == MOVE_STOP);
    }
    public boolean isAttackBasic() {
        return (actionCode == ATTACK_BASIC);
    }
    public boolean isAttackStop() {
        return (actionCode == ATTACK_BASIC_STOP);
    }
    public boolean isShipSelect() {
        return (actionCode == SHIP_SELECT);
    }
    
}
