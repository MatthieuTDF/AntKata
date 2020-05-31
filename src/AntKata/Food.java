package AntKata;

import java.awt.*;

public class Food {
    private Point position;
    private int lifeCycle;


    public Point getPosition() {
        return position;
    }

    public Food(int x, int y) {
        this.position = new Point(x, y);
        this.lifeCycle = 0;
    }

    public boolean isAlive() {
        if(this.lifeCycle < 5000){
            return true;
        }else{
            return false ;
        }
    }

    public void nextTurn() {

        this.lifeCycle ++;
    }
}
