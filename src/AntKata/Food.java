package AntKata;

import java.awt.*;

public class Food {
    private Point position;
    private int cycles;
    // TODO Attributs à rajouter pour gérer le cycle de vie

    public Point getPosition() {
        return position;
    }

    public Food(int x, int y) {
        this.position = new Point(x, y);
        this.cycles = 0;
    }

    public boolean isAlive() {
        // TODO
        return this.cycles < 5000; // if more then 5 mins then dead
    }

    public void nextTurn() {
        this.cycles ++;
    }
}
