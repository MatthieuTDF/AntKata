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
        for(int i = 0; i<nbAnts;i++){
            this.ants.add(new Ant(position));
        }
        this.foodCollected = 0;
    }

    public int next(List<Point> food) {
        // TODO

        return foodCollected;
    }

    public void addFood(){
        foodCollected++;
    }

    public List<Ant> getAnts() {
        return this.ants;
    }

    public int getPositionX() { return this.position.x; }

    public int getPositionY() {
        return this.position.y;
    }

    public Point getPosition(){return this.position;}

    public int getFoodCollected(){
        return this.foodCollected;
    }

}
