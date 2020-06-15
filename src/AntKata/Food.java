package AntKata;

import java.awt.*;

public class Food {
    private Point position;
    private int hp;

    public Point getPosition() {
        return position;
    }

    public Food(int x, int y) {
        this.position = new Point(x,y);
        this.hp = 375; // On initialise la vie de la food
    }

    public boolean isAlive() {
        if (hp <=0)
            return false; //Si la food a 0 hp ou moins (au cas ou ça bug) on la tue
        return true;
    }

    public int nextTurn() {
        if (isAlive()){
            hp -= 1;
            return 1; // Pour le nombre de food colectées
        }
        return 0;
    }
}
