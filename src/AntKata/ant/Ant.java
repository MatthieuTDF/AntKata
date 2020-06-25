package AntKata.ant;

import AntKata.Random.RNG;
//import sun.net.www.content.audio.basic;

import java.awt.Point;
import java.util.List;

public class Ant {
    private Point position;
    protected Status status;
    private Point lastKnownFoodPosition;
    private Point colonyPosition;
    private int foodCarried;
    protected int hp;
    protected Point ennemyColonyPosition;
    protected int damage;

    public Ant(Point positionColony) {
        this.position = positionColony;
        this.colonyPosition = positionColony;
        this.status = Status.WANDERING;
        this.lastKnownFoodPosition = new Point(positionColony);
        this.foodCarried = 0;
        this.hp = 10;
        this.ennemyColonyPosition = new Point(positionColony);
        this.damage = 5;
    }

    protected void scatter() {
        int randomX = RNG.random(-1, 1);
        int randomY = RNG.random(-1, 1);

        setPosition(new Point(getPositionX()+randomX, getPositionY()+randomY));
    }

    protected void fetchFood() {
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

    protected void collect() {
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

    protected int[] pathTo(Point location) {
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
        if (!this.getEnnemyPointColony().equals(this.colonyPosition) && anotherAnt.getEnnemyPointColony().equals(anotherAnt.colonyPosition)) {
            anotherAnt.setEnnemyPointColony(this.getEnnemyPointColony());
        }
        else if (!anotherAnt.getEnnemyPointColony().equals(anotherAnt.colonyPosition) && !this.getEnnemyPointColony().equals(this.colonyPosition)) {
            this.setEnnemyPointColony(anotherAnt.getEnnemyPointColony());
        }
    }

    public void fight(Ant ennemyAnt) {
        ennemyAnt.setHp(ennemyAnt.getHp()-this.damage);
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

            case SEEKING:
                this.setStatus(Status.WANDERING);
                this.scatter();
                break;
        }
    }

    public void resetFoodCarried() {
        this.foodCarried = 0;
    }

    public void setFoodCarried(int val) {
        this.foodCarried = val;
    }

    public int getHp() {
        return this.hp;
    }

    public void setHp(int val) {
        this.hp = val;
    }

    public Point getEnnemyPointColony() {
        return this.ennemyColonyPosition;
    }

    public void setEnnemyPointColony(Point p) {
        this.ennemyColonyPosition = new Point(p);
    } 

    public void attackEnnemyColony(Colony c) {
        c.setHp(c.getHp()-this.damage);
    }

    public Point getColonyPoint() {
        return this.colonyPosition;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}