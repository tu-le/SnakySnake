/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package snakysnake;

import java.awt.Point;
import java.util.ArrayList;

/**
 *
 * @author Tom Le
 */
public class Snake {
    private ArrayList<Point> body;
    private Direction direction = Direction.RIGHT;
    private int growthcounter = 0;

    /**
     * @return the body
     */
    {
        setBody(new ArrayList<Point>());
    }
    
    public void move(){
        //Create a new location for the head, using the direction.
        int x = 0;
        int y = 0;
        
        switch (getDirection()){
            case UP:
                x = 0;
                y = -1;
                break;
            case DOWN:
                x = 0;
                y = 1;
                break;
            case RIGHT:
                x = 1;
                y = 0;
                break;
            case LEFT:
                x = -1;
                y = 0;
        }
        getBody().add(0, new Point(getHead().x + x, getHead().y + y));
        
        if (growthcounter > 0){
            growthcounter --;
        } else {
            getBody().remove(getBody().size() -1);
        }
        //DELETE TAIL
    }
    public Point getHead(){
        return getBody().get(0);
}
        
    
    public ArrayList<Point> getBody() {
        return body;
    }

    /**
     * @param body the body to set
     */
    public void setBody(ArrayList<Point> body) {
        this.body = body;
    }

    /**
     * @return the direction
     */
    public Direction getDirection() {
        return direction;
    }

    /**
     * @param direction the direction to set
     */
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    /**
     * @return the growthcounter
     */
    public int getGrowthcounter() {
        return growthcounter;
    }

    /**
     * @param growthcounter the growthcounter to set
     */
    public void setGrowthcounter(int growthcounter) {
        this.growthcounter = growthcounter;
    }
    
    /**
     * @param growthcounter the growthcounter to set
     */
    public void addGrowthcounter(int growthcounter) {
        this.growthcounter += growthcounter;
    }
    
}
