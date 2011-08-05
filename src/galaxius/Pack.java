/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package galaxius;

import java.io.Serializable;
import java.lang.Object;

/**
 *
 * @author xalgra
 */
public class Pack implements Serializable {

    public static final int INIT = 0;//initial pack 
    public static final int ECHO = 1;
    public static final int MESSAGE = 2;
    public static final int SHIP = 3;
    public static final int BULLET = 4;
    public static final int DEBRIS = 5;
    public static final int PROFIL = 6;
    public static final int ACTION = 10;
    public static final int NAME = 11;
    public static final int EVENT = 20;
    public static final int STATE = 12;
     
    private int type;
    private Object index;

    public Pack(String msg) {
        type = MESSAGE;
        index = msg;
    }

    public Pack(int newType) {
        type = newType;
    }

    public Pack(int newType, Object index) {
        type = newType;
        this.index = index;
    }

    public void pack(Object index) {
        this.index = index;
    }

    public boolean isInit() {
        return (type == INIT);
    }

    public boolean isEcho() {
        return (type == ECHO);
    }

    public Object unPack() {
        return index;
    }

    public boolean isMessage() {
        return (type == MESSAGE);
    }

    public boolean isShip() {
        return (type == SHIP);
    }

    public boolean isAction() {
        return (type == ACTION);
    }
    public boolean isBullet()
    {
        return (type==BULLET);
    }
    public boolean isEvent()
    {
        return (type==EVENT);
    }
    public boolean isName()
    {
        return (type==NAME);
    }
    public boolean isState()
    {
        return type==STATE;
    }
    public boolean isDebris()
    {
        return type==DEBRIS;
    }

    @Override
    public String toString() {
        return "Pack type: " + type + "Object:" + index;
    }
}
