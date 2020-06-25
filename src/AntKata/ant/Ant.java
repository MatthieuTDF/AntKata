package AntKata.ant;

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

    public void scatter(List<Point> food) {
        int randomX = RNG.random(-1, 1);
        int randomY = RNG.random(-1, 1);
        this.setPosition(new Point(this.position.x + randomX, this.position.y + randomY));
        for (Point f : food){
            if (f.getLocation().equals(new Point(this.getPositionX(), this.getPositionY()))){
                this.lastKnownFoodPosition = new Point(this.getPositionX(), this.getPositionY());
                this.status = Status.RETURNING_COLONY;
            }
        }
    }

    // TODO Méthodes de classes

    private void changePos(Point lastFood, int newPosX, int newPosY) {
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

        if (this.status == Status.RETURNING_COLONY ){
            this.status = Status.FETCHING_FOOD;
        }

        if (this.status == Status.WANDERING){
            return false;
        }

        if (new Point(this.getPositionX(), this.getPositionY()).equals(lastFood)){
            this.status = Status.RETURNING_COLONY;
            collect();
            return false;
        }

        changePos(lastFood, newPosX, newPosY);
        this.status = Status.FETCHING_FOOD;
        return true;
    }

    public boolean collect(){
        Point posCol = this.colonyPosition;
        int newPosX = this.position.x;
        int newPosY = this.position.y;

        if (this.status == Status.FETCHING_FOOD ){
            this.status = Status.RETURNING_COLONY;
        }

        if (new Point(this.getPositionX(), this.getPositionY()).equals(posCol) && this.status == Status.RETURNING_COLONY){
            return true;
        }
        changePos(posCol, newPosX, newPosY);
        return false;
    }

    public int getPositionX() {
        return this.position.x;
    }

    public int getPositionY() {
        return this.position.y;
    }

    public Point getPosition() {
        return new Point(this.getPositionX(), this.getPositionY());
    }

    public Status getStatus() {
        return this.status;
    }

    public void setStatus(Status status) { this.status = status;}

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
