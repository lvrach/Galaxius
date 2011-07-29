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
public class FlyObject implements Serializable {

    private double x;
    private double y;
    private float direction;
    private float speed;
    private boolean existan = true;
   
    private int width;
    private int height;
    private final static int limitX=770;
    private final static int limitY=768;

    public FlyObject(int x, int y, int width, int height, float direction, float speed) {
         this.x = x;
         this.y = y;
        this.direction = direction;
        this.speed = speed;
        this.width = width;
        this.height = height;

    }
    
    public FlyObject(int x, int y, int width, int height, float direction) {
        this(x, y,width, height, direction, (float) 0);
    }
    
    public FlyObject(int x, int y, int width, int height) {
        this(x, y,width, height,(float) 90, (float) 0);
    }
    public FlyObject(int x, int y) {
        this(x, y,1, 1,(float) 90, (float) 0);
    }

    public FlyObject(FlyObject obj) {

         x = obj.getRealX();
         y = obj.getRealY();
        direction = obj.getDirection();
        speed = obj.getSpeed();
        existan = obj.exist();
        this.width = obj.getWidth();
        this.height = obj.getHeight();
        
    }

    public int getX() {
        return (int)x;
    }
    public int getY() {
        return (int)y;
    }
    public double getRealX() {
        return x;
    }
    public double getRealY() {
        return y;
    }
    
   
    public void setY(double y) {
        if (y < 0) {
            delete();
            y=0;
        }else if(y > limitY){
            delete();
            y=limitY;
        }

        this.y = y;
    }
     public void setY(int y) {
         setY((double) y);
     }    
    public boolean setX(double x) {
         if (x < 0) {
           this.x=0; 
           return false;
        }else if(x > limitX){
            this.x=limitX;
             return false;
        }
         this.x= x;
          return true;
    }
    
public boolean setX(int x) {
       return setX((double) x);
    }
    
   
    
   
    public int getWidth()
    {
        return width;
    }  
    public void setWidth(int width )
    {
        this.width =width;
    } 
    public int getHeight()
    {
        return height;
    }
     public void setHeight(int height )
    {
        this.height =height;
    } 
    
    public void setDirection(float direction) {
        this.direction = direction;
    }

    public float getDirection() {
        return direction;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getSpeed() {
        return speed;
    }

    public boolean exist() {
        return existan;
    }

    public void delete() {

        existan = false;
    }

  


   
}
