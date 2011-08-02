/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package galaxius.Ships;

import galaxius.Ship;
import galaxius.Ships.*;
import galaxius.skills.*;
import javax.swing.ImageIcon;

/**
 *
 * @author xalgra
 */
public class ShipLinker {

    public static final String[] Names = {"fire-Starter", "Noxor"};
    private int Width = 30;
    private int Height = 50;

    public static int size() {
        return Names.length;
    }

    public static Ship newShip(int shipTypeId, int ownerID ) {
        if (ownerID<0) {
            switch (shipTypeId) {
                case 0:
                    return new AI_0_Ship();
                case 1:
                    
            }        
        } else {
            switch (shipTypeId) {
                case 0:
                    return new Fire_Starter_Ship(ownerID);
                case 1:
                    return new Noxor_Ship(ownerID);
            }
        }
        return null;
    }

    public static Skill newSkill(int shipTypeId, Ship ship) {
        switch (shipTypeId) {
            case 0:
                return new Fire_Starter_Skill(ship);
            case 1:
                return new Noxor_Skill(ship);
        }
        return null;

    }
}
