package AntKata.ant;

import java.awt.Point;

public class WarriorAnt extends Ant {
    public WarriorAnt(Point positionColony) {
        super(positionColony);
        this.hp = 25;
        this.damage = 10;
    }

    private void seekAndDestroyEnnemyColony() {
        this.pathTo(this.getEnnemyPointColony());
    }

    public void setEnnemyPointColony(Point p) {
        this.ennemyColonyPosition = new Point(p);
        if (!p.equals(this.getColonyPoint())) {
            this.status = Status.SEEKING;
        }
    }

    public void processMovement() {
        switch(this.getStatus()) {
            case WANDERING:
                this.scatter();
                break;

            case RETURNING_COLONY:
                this.collect();
                break;

            case FETCHING_FOOD:
                this.fetchFood();
                break;

            case SEEKING:
                this.seekAndDestroyEnnemyColony();
                break;
        }
    }
}