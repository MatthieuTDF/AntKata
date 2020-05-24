package AntKata.ant;

import AntKata.Random.RNG;

import java.awt.Point;
import java.util.List;


public class Ant {
    private Point position;
    private Status status;
    private Point lastKnownFoodPosition;
    private Point positionColony;

    // TODO Attributs

    public Ant(Point positionColony) {
        // TODO
        this.position = this.positionColony = positionColony;
        this.status = Status.WANDERING;
    }

    public void scatter() {
        int randomX = RNG.random(-1, 1);
        int randomY = RNG.random(-1, 1);

        switch(this.getStatus()) {
            case WANDERING:
                this.position = new Point(this.getPositionX()+randomX, this.getPositionY()+randomY);
                break;
            case RETURNING_COLONY:
                this.position = new Point(this.getPositionX()-randomX, this.getPositionY()-randomY);
                break;
            case FETCHING_FOOD:
                this.position = new Point(this.getPositionX()+randomX, this.getPositionY()+randomY);
                break;
            default:
        }

        // TODO
    }

    //If food found status changes and last known food position is set
    private void foundFood(Point foodLocation){
        this.lastKnownFoodPosition = foodLocation;
        this.status = Status.RETURNING_COLONY;
        //System.out.println("Found Food");
    }

    //Checks if ant on food
    public void findFood(List<Point> food){
        for (Point foodLocation : food) {
            if (foodLocation.equals(this.position)) this.foundFood(foodLocation);
            //System.out.println("test");
        }
    }

    //checks if ant on colony cell
    public boolean isOnColony(){

        if (this.position.equals(this.positionColony)){
            this.status = Status.FETCHING_FOOD;
            return true;
        }
        return false;
    }

    //Checks if ant on food cell
    public void isOnFood(List<Point> food){
        if (this.position.equals(this.lastKnownFoodPosition)){
            if (food.contains(this.lastKnownFoodPosition)){
                this.foundFood(this.lastKnownFoodPosition);
            }else{
                this.status = Status.WANDERING;
            }
        }
    }

    // TODO Méthodes de classes

    public int getPositionX() {
        return this.position.x;
    }

    public int getPositionY() {
        return this.position.y;
    }

    public Point getPosition() {
        return this.position;
    }

    public void setStatus(Status status){
        this.status = status;
    }

    public Status getStatus() {
        return this.status;
    }

    public void setPosition(Point point) {
        this.position = new Point(point.x, point.y);
    }

    public void setLastKnownFoodPosition(Point lastKnownFoodPosition){
        this.lastKnownFoodPosition = lastKnownFoodPosition;
    }

    public Point getLastKnownFoodPosition() {
        return lastKnownFoodPosition;
    }
}
