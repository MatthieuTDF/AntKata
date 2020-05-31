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

        //Pour chaque fourmi dans ma colonie
        for (Ant ant : ants)
        {
            //Recherche de nourriture
            ant.foodFinder(food);

            //Communication entre fourmis
            this.communication(ants, ant);

            switch(ant.getStatus())
            {
                case WANDERING:
                    break ;

                case RETURNING_COLONY:
                    if(ant.getPosition().equals(this.position) )
                    {
                        foodCollected++ ;
                        ant.setStatus(Status.FETCHING_FOOD);
                    }

                case FETCHING_FOOD:
                    //Pour savoir si la fourmi connait un point de nourriture qui a expiré
                    if(ant.isOnOldFoodPoint(food)){
                        ant.setStatus(Status.WANDERING);
                    }

                default:
                    break ;
            }
            //Déplacement de la fourmi
            ant.scatter() ;
        }
        return foodCollected;
    }

    //Si deux fourmis sont sur la même case elle se transmette l'emplacement du dernier spot de nourriture qu'elle connaisse
    public void communication(List<Ant> colony, Ant otherAnt){

        for(Ant ant :colony){
            if(ant.getPosition().equals(otherAnt.getPosition())){
                if(ant.getLastKnownFoodPosition() != null && otherAnt.getLastKnownFoodPosition() == null){
                    otherAnt.setLastKnownFoodPosition(ant.getLastKnownFoodPosition()) ;
                    otherAnt.setStatus(Status.FETCHING_FOOD);
                }
            }
        }
    }

    public List<Ant> getAnts() {
        return ants;
    }

    public int getPositionX() {
        return position.x;
    }

    public int getPositionY() {
        return position.y;
    }

    public int getCollectedFood(){

        return this.foodCollected ;
    }
}
