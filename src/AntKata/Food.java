package AntKata;

import java.awt.Point;

public class Food {
    private Point position;
    // TODO Attributs à rajouter pour gérer le cycle de vie
    private int life;

    public Point getPosition() {
        return this.position;
    }

    public Food(int x, int y,int life) {
        // TODO
        this.position = new Point(x,y);
        this.life = life;
    }

    public Food(int x, int y){
        this(x,y,100);
    }

    public boolean isAlive() {
        // TODO
        return this.life > 0;
    }

    public void harvest(){
        this.life-=1;
    }

    public void nextTurn() {
        // TODO
    }
}
