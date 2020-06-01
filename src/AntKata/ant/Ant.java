package AntKata.ant;

import AntKata.Food;
import AntKata.Random.RNG;
import AntKata.ant.Colony;

import java.awt.Point;
import java.util.List;


import static AntKata.ant.Status.FETCHING_FOOD;
import static AntKata.ant.Status.WANDERING;


public class Ant {
    private Point position;
    private Status status;
    private Point lastKnownFoodPosition;
    //private List<Food> foodPoint;
    // TODO Attributs

    public Ant(Point positionColony) {
        // TODO
        this.position = positionColony;
        this.status = WANDERING;
        this.lastKnownFoodPosition = null;
    }

    private void scatter() {
        int randomX = RNG.random(-1, 1);
        int randomY = RNG.random(-1, 1);

        // TODO
        this.setPosition(new Point(this.position.x+randomX,this.position.y+randomY));

    }

    // TODO Méthodes de classes

    private boolean fetch(){
        Point lastFood = this.getLastKnownFoodPosition();
        int x = this.position.x;
        int y = this.position.y;
        if(this.position.equals(lastFood)) {
            this.status = Status.RETURNING_COLONY;
            return true;
        }
        else{
            if(x>lastFood.getX())
                x--;
            if(x<lastFood.getX())
                x++;
            if(y>lastFood.getY())
                y--;
            if(y<lastFood.getY())
                y++;
            this.setPosition(new Point(x,y));
        }
        return false;
    }

    public boolean collect(Point colony){
        int x = this.position.x;
        int y = this.position.y;
        if(this.getPosition().equals(colony)){
            this.status = FETCHING_FOOD;
            return true;
        }
        else{
            if(x>colony.getX())
                x--;
            if(x<colony.getX())
                x++;
            if(y>colony.getY())
                y--;
            if(y<colony.getY())
                y++;
            this.setPosition(new Point(x,y));
        }
        return false;
    }

    public boolean search(){
        if(status == WANDERING) {
            scatter();
            return false;
        }
        else
            return fetch();
    }

    public int getPositionX() {
        return this.position.x;
    }

    public int getPositionY() {
        return this.position.y;
    }

    public Point getPosition() {
        return this.position;
    }

    public Status getStatus() {
        return this.status;
    }

    public void setPosition(Point point) {
        this.position = new Point(point.x, point.y);
    }

    public Point getLastKnownFoodPosition() {
        return this.lastKnownFoodPosition;
    }

    public void foodFound(Point point) {
        this.lastKnownFoodPosition = new Point(point.x,point.y);
        this.status = FETCHING_FOOD;
    }
    public void foodDead(){
        this.lastKnownFoodPosition = null;
        this.status = WANDERING;
    }
}
