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
        int i;
        this.ants = new ArrayList<>();
        for(i=0;i<nbAnts;i++){
            this.ants.add(new Ant(position)); // On initialise les fourmis a la pos de depart de la colony
        }
        this.position = position;
    }

    public int next(List<Point> food) {
        // TODO
        //Oups je l'avais pas vu

        return foodCollected;
    }

    public List<Ant> getAnts() {
        return ants;
    }

    public int getPositionX() { return position.x; }

    public int getPositionY() {
        return position.y;
    }

}
