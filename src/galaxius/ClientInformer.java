/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package galaxius;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author xalgra
 */
public class ClientInformer {

    private List<Player> players;

    ClientInformer(List<Player> players) {
        this.players = players;
    }

    public void inform() {

        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            sendALL(new Pack(Pack.SHIP, player.getShip()));

//            for (int j = 0; j < player.getBullets().size(); j++) {
//                sendALL(new Pack(Pack.BULLET, new Bullet(player.getBullets().get(j))));
//                
//            }

        }

    }
   

    public void inform(Pack pack,int skipID) {

        sendALL(pack,skipID);

    }
    public void inform(Pack pack) {

        sendALL(pack);

    }

    public void sendALL(Pack packet,int skipID) {

        for (int i = 0; i < players.size(); i++) {
            if(players.get(i).getID()==skipID)
                continue;
            
            players.get(i).sendData(packet);
            
           

        }

    }
    public void sendALL(Pack packet) {

        for (int i = 0; i < players.size(); i++) {          
            
            players.get(i).sendData(packet);           
           

        }

    }
   
}
