package AntKata.ant;

import AntKata.Random.RNG;

import java.awt.Point;
import java.util.List;


public class Ant {
    private Point position;
    private Status status;
    private Point lastKnownFoodPosition;
    private Point colonyPosition;

    // TODO Attributs

    public Ant(Point positionColony) {
        // TODO
        this.position = positionColony ;
        this.colonyPosition = positionColony;
        this.status = Status.WANDERING;
    }


    //Deplacement d'une fourmi selon son status
    public void scatter() {
        int randomX = RNG.random(-1, 1);
        int randomY = RNG.random(-1, 1);

        //Elle se déplace de façon random quand elle cherche de la nourriture
        switch(this.getStatus()){
            case WANDERING:
                this.position = new Point(this.getPositionX()+randomX, this.getPositionY()+randomY);
                break ;

            //Retour à la colonie
            case RETURNING_COLONY:
                this.goTo(colonyPosition);
                break ;

            //Aller au spot de nourriture qu'elle connait
            case FETCHING_FOOD:
                this.goTo(lastKnownFoodPosition);

            default:
                break ;
        }


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

    public void setStatus(Status status){
        this.status = status;
    }

    public void setPosition(Point point) {
        this.position = new Point(point.x, point.y);
    }

    public Point getLastKnownFoodPosition() {
        return lastKnownFoodPosition;
    }

    public void setLastKnownFoodPosition(Point lastKnownFoodPosition){
        this.lastKnownFoodPosition = lastKnownFoodPosition;
    }

    //Prend la liste des points de nourriture et regarde si la fourmi est sur une des positions
    public void foodFinder(List<Point> foodPositions){
        for(Point foodPosition: foodPositions ){
            if(foodPosition.equals(this.position)){

                lastKnownFoodPosition = this.position ;
                this.status = Status.RETURNING_COLONY;
            }
        }
    }



    //Va à un point donné
    private void goTo(Point food){
        if(food != null){

            if(food.getX() > this.getPositionX()){
                this.position = new Point(this.getPositionX() +1, this.getPositionY() );
            }

            if(food.getX() < this.getPositionX()){
                this.position = new Point(this.getPositionX() -1, this.getPositionY() );
            }

            if(food.getX() == this.getPositionX()){

                if(food.getY() > this.getPositionY()){
                    this.position = new Point(this.getPositionX(), this.getPositionY() + 1) ;
                }

                if(food.getY() < this.getPositionY()){
                    this.position = new Point(this.getPositionX(), this.getPositionY() + -1) ;
                }
            }
        }
    }

    //Pour savoir la lastKnownFoodPosition est toujours là
    // (parfois la fourmi revient à la colonie et le point de nourriture expire le temps qu'elle fasse le trajet...)
    public boolean isOnOldFoodPoint(List<Point> foodPoints){
        if(this.lastKnownFoodPosition != null) {
            for (Point foodpoint : foodPoints) {

                if (foodpoint.equals(this.lastKnownFoodPosition)) {
                    return false;
                }
            }
        }
        return true ;
    }

}
