package AntKata.ant;

import AntKata.Random.RNG;

import java.awt.Point;
import java.util.List;


public class Ant {
    private Point position;
    private Status status;
    private Point lastKnownFoodPosition;
    private Point positionColony;
    private int count = 0;

    // TODO Attributs

    public Ant(Point positionColony) {
        // TODO
        this.position = this.positionColony = positionColony;
        this.status = Status.WANDERING;
    }

    public void scatter() {
        int randomX = RNG.random(-1, 1);
        int randomY = RNG.random(-1, 1);

        // TODO
        switch(this.getStatus()) {
            case WANDERING:
                if (this.getPositionX() >= (int)positionColony.getX()*2-3 || this.getPositionY() >= (int)positionColony.getY()*2-3){
                    this.position = new Point((int)positionColony.getX(),(int)positionColony.getY());
                }
                if (this.getPositionX() <= 3 || this.getPositionY() <= 3) {
                    this.position = new Point((int)positionColony.getX(),(int)positionColony.getY());
                } else {
                    this.position = new Point(this.getPositionX()+randomX, this.getPositionY()+randomY);
                }
                break;
            case WANDERING2:
                while(count < 100) {
                    if (this.getPositionX() >= (int) positionColony.getX() * 2 - 3 || this.getPositionY() >= (int) positionColony.getY() * 2 - 3) {
                        this.position = new Point((int) positionColony.getX(), (int) positionColony.getY());
                        count++;
                    }
                    if (this.getPositionX() <= 3 || this.getPositionY() <= 3) {
                        this.position = new Point((int) positionColony.getX(), (int) positionColony.getY());
                        count++;
                    } else {
                        this.position = new Point(this.getPositionX() + randomX, this.getPositionY() + randomY);
                        count++;
                    }
                    break;
                }
                this.status = Status.WANDERING;
                count = 0;
                break;
            case FETCHING_FOOD:
                if (this.getPositionX() < (int)this.lastKnownFoodPosition.getX()) {
                    this.position = new Point(this.getPositionX()+1, this.getPositionY());
                }
                if (this.getPositionX() > (int)this.lastKnownFoodPosition.getX()) {
                    this.position = new Point(this.getPositionX()-1, this.getPositionY());
                }
                if (this.getPositionY() < (int)this.lastKnownFoodPosition.getY()) {
                    this.position = new Point(this.getPositionX(), this.getPositionY()+1);
                }
                if (this.getPositionY() > (int)this.lastKnownFoodPosition.getY()) {
                    this.position = new Point(this.getPositionX(), this.getPositionY()-1);
                }
                break;
            case RETURNING_COLONY:
                if (this.getPositionX() < (int)positionColony.getX()) {
                    this.position = new Point(this.getPositionX()+1, this.getPositionY());
                }
                if (this.getPositionX() > (int)positionColony.getX()) {
                    this.position = new Point(this.getPositionX()-1, this.getPositionY());
                }
                if (this.getPositionY() < (int)positionColony.getY()) {
                    this.position = new Point(this.getPositionX(), this.getPositionY()+1);
                }
                if (this.getPositionY() > (int)positionColony.getY()) {
                    this.position = new Point(this.getPositionX(), this.getPositionY()-1);
                }
                break;
            default:
        }
    }

    //If food found status changes and last known food position is set
    private void foundFood(Point foodLocation){
        this.lastKnownFoodPosition = foodLocation;
        this.status = Status.RETURNING_COLONY;
    }

    //Checks if ant on food
    public void findFood(List<Point> food){
        for (Point foodLocation : food) {
            if (foodLocation.equals(this.position)){
                this.foundFood(foodLocation);
            }
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
                this.status = Status.WANDERING2;
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

    public Status getStatus() {
        return this.status;
    }

    public void setStatus(Status status){
        this.status = status;
    }

    public void setPosition(Point point) {
        this.position = new Point(point.x, point.y);
    }

    public Point getLastKnownFoodPosition() {
        return lastKnownFoodPosition;
    }

    public void setLastKnownFoodPosition(Point lastKnownFoodPosition){
        this.lastKnownFoodPosition = lastKnownFoodPosition;
    }
}
