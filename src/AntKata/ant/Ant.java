package AntKata.ant;

import AntKata.Food;
import AntKata.Random.RNG;

import java.awt.Point;
import java.util.List;


public class Ant {
    private Point position;
    private Status status;
    private Point lastKnownFoodPosition;
    private Point colonyPosition;

    // TODO Attributs

    public Ant(Point positionColony) {
        this.position = positionColony;
        this.colonyPosition = positionColony;
        this.status = Status.WANDERING;
        this.lastKnownFoodPosition = null;
    }

    public void scatter(List<Point> f) {
        int randomX = RNG.random(-1, 1);
        int randomY = RNG.random(-1, 1);
        this.setPosition(new Point(this.position.x + randomX, this.position.y + randomY));
        for (Point food : f){
            if (food.getLocation() == this.getPosition()){
                this.lastKnownFoodPosition = this.position;
                this.status = Status.RETURNING_COLONY;
            }
        }
    }

    // TODO Méthodes de classes

    private void checkPos(Point lastFood, int newPosX, int newPosY) {
        if (newPosX > lastFood.x){
            newPosX--;
        }
        if (newPosX < lastFood.x){
            newPosX++;
        }
        if (newPosY > lastFood.y){
            newPosY--;
        }
        if (newPosY < lastFood.y){
            newPosY++;
        }

        this.setPosition(new Point(newPosX,newPosY));
    }

    public boolean fetch(){
        Point lastFood = this.lastKnownFoodPosition;
        int newPosX = this.position.x;
        int newPosY = this.position.y;

        if (this.status == Status.WANDERING){
            return false;
        }

        if (this.getPosition() == lastFood){
            this.status = Status.RETURNING_COLONY;
            collect();
            return false;
        }

        checkPos(lastFood, newPosX, newPosY);

        return true;
    }

    public boolean collect(){
        Point posCol = this.colonyPosition;
        int newPosX = this.position.x;
        int newPosY = this.position.y;
        if (this.getPosition() == posCol && this.status == Status.RETURNING_COLONY){
            return true;
        }
        checkPos(posCol, newPosX, newPosY);
        return false;
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
        return lastKnownFoodPosition;
    }

    public void setLastKnownFoodPosition(Point colony){
        this.lastKnownFoodPosition = colony;
    }
}
