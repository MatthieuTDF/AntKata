package AntKata.ant;

import AntKata.Random.RNG;

import java.awt.Point;
import java.util.List;


public class Ant {
    private Point colonyPosition;
    private Point position;
    private Status status;
    private Point lastKnownFoodPosition;


    // TODO Attributs

    public Ant(Point positionColony) {
        // TODO
        this.colonyPosition = this.position = positionColony; //first position for an ant
        this.status = Status.WANDERING; //first status for an ant is going to be wandering because she is searching for food
    }

    public void scatter() {
        int randomX = RNG.random(-1, 1);
        int randomY = RNG.random(-1, 1);

        // TODO
        //the ant advance in the map whether she knows where the food is or not or she has food

        switch (this.getStatus()) {
            //if the ant is wandering, advances randomly
            case WANDERING -> this.position = new Point(this.getPositionX() + randomX, this.getPositionY() + randomY);
            //the ant goes to the direction where the food is
            case FETCHING_FOOD -> this.direction( this.lastKnownFoodPosition );
//if the ant has food she's going backward to the colony
            case RETURNING_COLONY -> this.direction( this.colonyPosition );
        }
    }

    //method to set the direction the ant will be going according to the position given in parameters
    private void direction ( Point foodPosition ) {
        if ( this.getPositionX() < foodPosition.getX() ) {
            this.position = new Point( this.getPositionX() + 1, this.getPositionY() );
        } else {
            this.position = new Point( this.getPositionX() - 1, this.getPositionY() );
        }
        if ( this.getPositionY() < foodPosition.getY() ){
            this.position = new Point( this.getPositionX(), this.getPositionY() + 1 );
        } else {
            this.position = new Point( this.getPositionX(), this.getPositionY() - 1 );
        }
    }
//method to go back to the colony
    private void returning ( Point foodPosition ){
        this.lastKnownFoodPosition = foodPosition;
        this.status = Status.RETURNING_COLONY;
    }

    //if an ant is wandering and find food, to set her status to returning
    public void foodPosition ( List<Point> food ) {
        for ( Point f : food ){
            if ( f.equals(this.position) ){
                this.returning( f );
            }
        }

    }
//if the ant is on colony
    public boolean antPositionColony() {
        if ( this.position.equals(this.colonyPosition) ){
            this.status = Status.FETCHING_FOOD;
            return true;
        }
        return false;
    }
//method to tell what the ant should do if on a food
    public boolean antPositionOnFood( List<Point> food ){
        if ( this.position.equals( this.lastKnownFoodPosition ) ){ //if the ant position is equal to the position of the last food found
            if ( food.contains( this.lastKnownFoodPosition ) ){ //if the lastKnowFoodPosition is included in food
                this.returning( this.lastKnownFoodPosition ); //goes back to the colony
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

    public Status getStatus() {
        return this.status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setPosition(Point point) {
        this.position = new Point(point.x, point.y);
    }

    public Point getLastKnownFoodPosition() {
        return lastKnownFoodPosition;
    }

    public void setLastKnownFoodPosition(Point lastKnownFoodPosition) {
        this.lastKnownFoodPosition = lastKnownFoodPosition;
    }
}
