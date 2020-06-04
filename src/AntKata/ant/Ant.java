package AntKata.ant;

import AntKata.Food;
import AntKata.Random.RNG;
import AntKata.ant.Colony;

import java.awt.Point;
import java.util.List;

import static AntKata.ant.Status.*;


public class Ant {
    private Point position;
    private Status status;
    //private Point lastKnownFoodPosition;
    private Food lastFood;
    //private List<Food> foodPoint;
    // TODO Attributs

    public Ant(Point positionColony) {
        // TODO
        this.position = positionColony;
        this.status = WANDERING;
        //this.lastKnownFoodPosition = null;
        this.lastFood = null;
    }

    private void scatter() {
        int randomX = RNG.random(-1, 1);
        int randomY = RNG.random(-1, 1);

        // TODO
        this.setPosition(new Point(this.position.x+randomX,this.position.y+randomY));

    }

    // TODO Méthodes de classes

    private void fetch(){
        //Point lastFood = this.getLastKnownFoodPosition();
        if(this.lastFood.isAlive()) {
            int x = this.position.x;
            int y = this.position.y;
            if (this.position.equals(this.lastFood.getPosition())) {
                this.status = Status.RETURNING_COLONY;
                this.lastFood.harvest();
                //return true;
            } else {
                if (x > this.lastFood.getPosition().getX())
                    x--;
                if (x < this.lastFood.getPosition().getX())
                    x++;
                if (y > this.lastFood.getPosition().getY())
                    y--;
                if (y < this.lastFood.getPosition().getY())
                    y++;
                this.setPosition(new Point(x, y));
            }
            //return false;
        }
        else{
            this.status = WANDERING;
            this.scatter();
        }

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

    public void search(){
        if(status == WANDERING) {
            this.scatter();
            //return false;
        }
        else
            this.fetch();
    }

    public void talk(Ant ant){
        if (this.position.equals(ant.getPosition()))
            if(ant.getStatus()==WANDERING && this.status != WANDERING)
                ant.foodFound(this.lastFood);
            else if(ant.getPosition().equals(this.position)&&ant.getStatus()!=WANDERING)
                this.foodFound((ant.getLastFood()));
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
        return this.lastFood.getPosition();
    }
    /*
    public void foodFound(Point point) {
        this.lastKnownFoodPosition = new Point(point.x,point.y);
        this.status = RETURNING_COLONY;
    }
    public void foodDead(){
        this.lastKnownFoodPosition = null;
        this.status = WANDERING;
    }*/

    public Food getLastFood(){
        return this.lastFood;
    }

    public void foodFound(Food food){
        this.status = FETCHING_FOOD;
        this.lastFood = food;
    }

    //public void foodDead(){
        //if(this.status)
    //}
}
