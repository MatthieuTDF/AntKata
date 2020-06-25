package AntKata;

import java.awt.*;

public class Food {
    private Point position;
    private int lifeTime;

    public Point getPosition() {
        return position;
    }

    public int getPositionX() {
        return position.x;
    }

    public int getPositionY() {
        return position.y;
    }

    public Food(int x, int y) {
        this.position = new Point(x, y);
        this.lifeTime = 100;
    }

    public boolean isAlive() {
        return this.lifeTime > 0;
    }

    public void nextTurn() {
        this.lifeTime--;
    }
}
