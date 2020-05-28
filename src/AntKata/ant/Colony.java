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
        // TODO
        this.ants = new ArrayList<>();
        this.position = position;
        this.foodCollected = 0;
        for(int i = 0; i< nbAnts; i++){
            ants.add(new Ant(position));
        }
    }

    //Every next turn
    public int next(List<Point> food) {
        // TODO
        for (Ant a : ants){
            switch(a.getStatus()) {
                case WANDERING:
                    a.findFood(food);
                    break;
                case RETURNING_COLONY:
                    if(a.isOnColony()) this.foodCollected ++;
                    break;
                case FETCHING_FOOD:
                    a.isOnFood(food);
                    break;
                default:
            }
            //If two ants are in the same cell, communicate food location
            for (int i = 0; i<ants.size()-1; i ++){
                for (int j = i + 1; j<ants.size(); j ++){
                    if(ants.get(i).getPosition().equals(ants.get(j).getPosition())){
                        if(ants.get(i).getStatus() == Status.FETCHING_FOOD || ants.get(i).getStatus() == Status.RETURNING_COLONY){
                            ants.get(j).setLastKnownFoodPosition(ants.get(i).getLastKnownFoodPosition());
                            ants.get(j).setStatus(Status.FETCHING_FOOD);
                        }else{
                            if(ants.get(j).getStatus() == Status.FETCHING_FOOD || ants.get(j).getStatus() == Status.RETURNING_COLONY){
                                ants.get(i).setLastKnownFoodPosition(ants.get(j).getLastKnownFoodPosition());
                                ants.get(i).setStatus(Status.FETCHING_FOOD);
                            }
                        }
                    }
                }
            }
            a.scatter();
        }

        return this.foodCollected;
    }

    public List<Ant> getAnts() {
        return ants;
    }

    public int getPositionX() { return position.x; }

    public int getPositionY() {
        return position.y;
    }

}
