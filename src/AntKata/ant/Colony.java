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
        for (int i = 0; i < nbAnts; i++) {
            this.ants.add(new Ant(position));
        }
        this.position = position;
    }

    public int next(List<Point> food) {
        for (Ant a : ants){
            if (a.getStatus() == Status.WANDERING){
                a.scatter(food);
            }
            if (a.getStatus() == Status.FETCHING_FOOD){
                a.fetch();
            }
            if (a.getStatus() == Status.RETURNING_COLONY){
                if (a.collect()){
                    this.foodCollected++;
                }
            }

            for (int i = 0; i < ants.size(); i++) {
                for (Ant ant : ants) {
                    if (ants.get(i).getPosition() == ant.getPosition()) {
                        if ((ants.get(i).fetch() || ants.get(i).collect()) && ant.getStatus() == Status.WANDERING) {
                            ant.setLastKnownFoodPosition(ants.get(i).getLastKnownFoodPosition());
                        }
                        if ((ant.fetch() || ant.collect()) && ants.get(i).getStatus() == Status.WANDERING) {
                            ants.get(i).setLastKnownFoodPosition(ant.getLastKnownFoodPosition());
                        }
                    }
                }
            }
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
