package AntKata.ant;

import AntKata.Random.RNG;
//import sun.net.www.content.audio.basic;

import java.awt.Point;
import java.util.List;

public class Ant {
    private Point position;
    private Status status;
    private Point lastKnownFoodPosition;
    private Point colonyPosition;
    private int foodCarried;

    public Ant(Point positionColony) {
        this.position = positionColony;
        this.colonyPosition = positionColony;
        this.status = Status.WANDERING;
        this.lastKnownFoodPosition = new Point(0, 0);
        this.foodCarried = 0;
    }

    private void scatter() {
        int randomX = RNG.random(-1, 1);
        int randomY = RNG.random(-1, 1);

        setPosition(new Point(getPositionX()+randomX, getPositionY()+randomY));
    }

    private void fetchFood() {
        int[] axis = this.pathTo(this.lastKnownFoodPosition);
        int newposX=0, newposY=0;
        switch (axis[0]) {
            case 1:
                newposX = this.getPositionX()+1;
                break;

            case -1:
                newposX = this.getPositionX()-1;
                break;

            case 0:
                newposX = this.getPositionX();
                break;
        }

        switch (axis[1]) {
            case 1:
                newposY = this.getPositionY()+1;
                break;

            case -1:
                newposY = this.getPositionY()-1;
                break;

            case 0:
                newposY = this.getPositionY();
                break;
        }
        setPosition(new Point(newposX, newposY));
    }

    private void collect() {
        int[] axis = this.pathTo(this.colonyPosition);
        int newposX=0, newposY=0;
        switch (axis[0]) {
            case 1:
                newposX = this.getPositionX()+1;
                break;

            case -1:
                newposX = this.getPositionX()-1;
                break;

            case 0:
                newposX = this.getPositionX();
                break;
        }

        switch (axis[1]) {
            case 1:
                newposY = this.getPositionY()+1;
                break;

            case -1:
                newposY = this.getPositionY()-1;
                break;

            case 0:
                newposY = this.getPositionY();
                break;
        }
        setPosition(new Point(newposX, newposY));
    }

    private int[] pathTo(Point location) {
        double distanceX = location.getX() - this.position.getX();
        double distanceY = location.getY() - this.position.getY();
        int x, y;

        if (distanceX == 0) {
            x = 0;
        }
        else if (distanceX > 0) {
            x = 1;
        }
        else {x = -1;}
        if (distanceY == 0) {
            y = 0;
        }
        else if (distanceY > 0) {
            y = 1;
        }
        else {y = -1;}

        return new int[] {x, y};
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

    public void setLastKnownFoodPosition(Point p) {
        this.lastKnownFoodPosition = new Point(p.x, p.y);
    }

    public void shareFoodPosition(Ant otherAnt) {
        this.talk(otherAnt);
    }

    public int getFoodCarried() {
        return this.foodCarried;
    }

    public void talk(Ant anotherAnt) {
        if (anotherAnt.getStatus() == Status.WANDERING && this.getStatus() == Status.FETCHING_FOOD) {
            anotherAnt.setLastKnownFoodPosition(this.getLastKnownFoodPosition());
        }
        else if (this.getStatus() == Status.WANDERING && anotherAnt.getStatus() == Status.FETCHING_FOOD) {
            this.setLastKnownFoodPosition(anotherAnt.getLastKnownFoodPosition());
        }
    }

    public void processMovement() {
        switch(this.getStatus()) {
            case WANDERING:
                this.scatter();
                break;

            case RETURNING_COLONY:
                this.collect();
                break;

            case FETCHING_FOOD:
                this.fetchFood();
                break;
        }
    }

    public void resetFoodCarried() {
        this.foodCarried = 0;
    }

    public void setFoodCarried(int val) {
        this.foodCarried = val;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}