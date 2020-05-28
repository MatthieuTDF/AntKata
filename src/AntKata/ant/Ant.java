package AntKata.ant;

import AntKata.Random.RNG;

import java.awt.Point;
import java.util.List;


public class Ant {
    private Point position;
    private Status status;
    private Point lastKnownFoodPosition;
    private Point positionColony;
    private int cycles;

    // TODO Attributs

    public Ant(Point positionColony) {
        // TODO
        this.position = this.positionColony = positionColony;
        this.status = Status.WANDERING;
    }

    //If food found status changes and last known food position is set
    private void foundFood(Point foodLocation){
        this.lastKnownFoodPosition = foodLocation;
        this.status = Status.RETURNING_COLONY;
    }

    private void followPath(Point endPath){
        if (this.getPositionX() < (int)endPath.getX()) this.position = new Point(this.getPositionX()+1, this.getPositionY());
        if (this.getPositionX() > (int)endPath.getX()) this.position = new Point(this.getPositionX()-1, this.getPositionY());
        if (this.getPositionY() < (int)endPath.getY()) this.position = new Point(this.getPositionX(), this.getPositionY()+1);
        if (this.getPositionY() > (int)endPath.getY()) this.position = new Point(this.getPositionX(), this.getPositionY()-1);
    }

    private boolean isOutOfBounds(){
        int maxY = (int)this.positionColony.getY()*2;
        int maxX = (int)this.positionColony.getX()*2;
        if(this.getPositionX() >= maxX-10 || this.getPositionX() <= 10 || this.getPositionY() >= maxY-10 || this.getPositionY() <= 10){
            if(this.getPositionX() >= maxX-10) this.position = new Point(this.getPositionX()-1, this.getPositionY());
            if(this.getPositionX() <= 10) this.position = new Point(this.getPositionX()+1, this.getPositionY());
            if(this.getPositionY() >= maxY-10) this.position = new Point(this.getPositionX(), this.getPositionY()-1);
            if(this.getPositionY() <= 10) this.position = new Point(this.getPositionX(), this.getPositionY()+1);
            return true;
        }
        return false;
    }

    //Checks if ant is hungry
    public boolean isHungry(){
        if (this.cycles >= 2500) { // 2mins before ant gets hungry
            this.cycles = 0;
            return true;
        }
        return false;
    }

    public void scatter() {
        int randomX = RNG.random(-1, 1);
        int randomY = RNG.random(-1, 1);

        switch(this.getStatus()) {
            case WANDERING:
                if(!this.isOutOfBounds()){
                    this.position = new Point(this.getPositionX()+randomX, this.getPositionY()+randomY);
                }
                break;
            case RETURNING_COLONY:
                this.followPath(this.positionColony);
                break;
            case FETCHING_FOOD:
                this.followPath(this.lastKnownFoodPosition);
                break;
            default:
        }
        this.cycles ++;

        // TODO
    }

    //Checks if ant on food
    public void findFood(List<Point> food){
        for (Point foodLocation : food) {
            if (foodLocation.equals(this.position)) this.foundFood(foodLocation);
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
    public boolean isOnFood(List<Point> food){
        if (this.position.equals(this.lastKnownFoodPosition)){
            if (food.contains(this.lastKnownFoodPosition)){
                this.foundFood(this.lastKnownFoodPosition);
            }else{
                this.status = Status.WANDERING;
            }
            return true;
        }
        return false;
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
