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
public class Event implements Serializable {
    
    private int eventType;
    public static final int HP_Change=0;
    private int value;
    private int id;
    
    public Event(int eventType,int value,int id)
    {
        this.eventType=eventType;
        this.value = value;
        this.id = id;
    }
    public boolean isHpChange()
    {
        return eventType==HP_Change;
    }
    public int getValue()
    {
        return value;
    }
    public int getID()
    {
        return id;
    }
            
}
