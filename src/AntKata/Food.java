package AntKata;

import java.awt.*;

public class Food {
    private Point position;
    // TODO Attributs à rajouter pour gérer le cycle de vie
    private int cycles;

    public Point getPosition() {
        return position;
    }

    public Food(int x, int y) {
        // TODO
        this.position = new Point(x, y);
        this.cycles = 0;
    }

    public boolean isAlive() {
        // TODO
        return this.cycles < 1000;
    }

    public void nextTurn() {
        // TODO
        this.cycles ++;
    }
}
