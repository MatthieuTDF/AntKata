package AntKata.ant;

import AntKata.Random.RNG;

import java.awt.Point;
import java.util.List;
import java.util.Random;


public class Ant {
    private Point position;
    private Status status = Status.WANDERING;
    private Point lastKnownFoodPosition = null;
    private Point positionColony;

    public Ant(Point positionColony) {
        this.positionColony = positionColony; // On stocke la position de la colony
        this.position= positionColony;
    }

    private void scatter() {
        int randomX = RNG.random(-1, 1);
        int randomY = RNG.random(-1, 1);
        this.setPosition(new Point(this.position.x+randomX,this.position.y+randomY)); // On met la position de la fourmis a +/- 1 en x et y
    }

    private void fetch(Point foodPosition){ // Le deplacement depend de la position de l'objet qu'on veut rejoindre (colony ou food)
        if(foodPosition.getX() < this.position.getX())
            this.position.setLocation(this.position.getX()-1,this.position.getY());
        if(foodPosition.getY() < this.position.getY())
            this.position.setLocation(this.position.getX(),this.position.getY()-1);
        if(foodPosition.getX() > this.position.getX())
            this.position.setLocation(this.position.getX()+1,this.position.getY());
        if(foodPosition.getY() > this.position.getY())
            this.position.setLocation(this.position.getX(),this.position.getY()+1);


    }

    public void setStatus(Status status){
        this.status = status;
    }

    public void newPosition(){ // Soit on utilise scatter soit on fetch vers une position precise en fonction du status
        if(this.status == Status.WANDERING) {
            this.scatter();
        }
        if(this.status == Status.FETCHING_FOOD){
            this.fetch(lastKnownFoodPosition);
       }
        if(this.status == Status.RETURNING_COLONY){
            this.fetch(positionColony);
        }

    }
    public void setLastKnownFoodPosition(Point position) {
        this.lastKnownFoodPosition= position;
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
}
