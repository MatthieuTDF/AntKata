package AntKata.ant;

import AntKata.ant.Ant;
import AntKata.ant.CellType;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Colony {
    private List<Ant> ants;
    private Point position;
    private int foodCollected;

    public Colony(int nbAnts, Point position) {
        this.ants = new ArrayList<>();
        for (int i=0; i<nbAnts; i++) {
            this.ants.add(new Ant(position));
        }
        this.position = position;
        this.foodCollected = 0;
    }

    public int next(CellType[][] cellArray) {
        for (Ant ant: this.ants) {
            for (Ant otherAnt: this.ants) {
                if(otherAnt.getPosition().equals(ant.getPosition())) {
                    ant.talk(otherAnt);
                }
            }
        }
        for (Ant ant: this.ants) {
            ant.processMovement();
            if (ant.getPosition().equals(this.position) && ant.getStatus() == Status.RETURNING_COLONY) {
                this.foodCollected += ant.getFoodCarried();
                ant.resetFoodCarried();
                if (ant.getLastKnownFoodPosition() == this.getPosition()) {
                    ant.setStatus(Status.WANDERING);
                }
                else ant.setStatus(Status.FETCHING_FOOD);
            }
            else if (cellArray[ant.getPositionX()][ant.getPositionY()] == CellType.FOOD) {
                switch (ant.getStatus()) {
                    case FETCHING_FOOD:
                        ant.setLastKnownFoodPosition(ant.getPosition());
                        ant.setStatus(Status.RETURNING_COLONY);
                        ant.setFoodCarried(1);
                        break;

                    case RETURNING_COLONY:
                        ant.setLastKnownFoodPosition(ant.getPosition());
                        break;
                    
                    case WANDERING:
                        ant.setLastKnownFoodPosition(ant.getPosition());
                        ant.setStatus(Status.RETURNING_COLONY);
                        ant.setFoodCarried(1);
                        break;
                }
            }
            else if (ant.getPosition().equals(ant.getLastKnownFoodPosition())) {
                ant.setLastKnownFoodPosition(this.getPosition());
                ant.setStatus(Status.WANDERING);
            }
        }
        return foodCollected;
    }

    public List<Ant> getAnts() {
        return ants;
    }

    public int getFoodCollected() {
        return this.foodCollected;
    }

    public Point getPosition() {
        return this.position;
    }

    public int getPositionX() { return position.x; }

    public int getPositionY() {
        return position.y;
    }

}
