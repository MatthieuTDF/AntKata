package AntKata;

import java.awt.*;
import java.util.Random;

import AntKata.Random.RNG;

public class Food {
    private Point position;
    private int size;

    public Point getPosition() {
        return this.position;
    }

    public Food(int x, int y) {
        this.position = new Point(x, y);
        this.size = RNG.random(1, 100);
    }

    public boolean isAlive() {
        if (size > 0) {
            return true;
        }
        else return false;
    }

    public void nextTurn() {
        this.size--;
    }
}
