package AntKata.ant;

import AntKata.Random.RNG;
import AntKata.Random.RNGMock;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class AntTest {

    @BeforeClass
    public static void mockRNG() {
        RNG.setImpl(new RNGMock());
    }

    @Test
    public void antFindFoodAndCollect() {
        // Given
        Colony c = new Colony(1, new Point(5, 5));

        List<Point> food = new ArrayList<>(List.of(new Point(6, 6)));
        CellType[][] cellArray = new CellType[10][10];
        cellArray[6][6] = CellType.FOOD;
        Assert.assertEquals(new Point(5, 5), c.getAnts().get(0).getPosition());
        Assert.assertEquals(Status.WANDERING, c.getAnts().get(0).getStatus());
        // Init, ant moving to 6, 6
        c.next(cellArray);

        // Check
        Assert.assertEquals(1, c.getAnts().size());
        Assert.assertEquals(new Point(6, 6), c.getAnts().get(0).getPosition());
        Assert.assertEquals(Status.RETURNING_COLONY, c.getAnts().get(0).getStatus());
        // Ant found food, going back to the colony
        c.next(cellArray);

        // Check
        Assert.assertEquals(Status.FETCHING_FOOD, c.getAnts().get(0).getStatus());
        Assert.assertEquals(food.get(0), c.getAnts().get(0).getLastKnownFoodPosition());
        Assert.assertEquals(new Point(5, 5), c.getAnts().get(0).getPosition());

        // Ant brought back food going for some more
        c.next(cellArray);

        // Check
        Assert.assertEquals(new Point(6, 6), c.getAnts().get(0).getPosition());
        Assert.assertEquals(Status.RETURNING_COLONY, c.getAnts().get(0).getStatus());

        // Ant found food, going back to the colony
        c.next(cellArray);

        // Check
        Assert.assertEquals(Status.FETCHING_FOOD, c.getAnts().get(0).getStatus());
    }

    @Test
    public void antTalkOtherAntFetch() {
        // Given
        Colony c = new Colony(2, new Point(5, 5));

        List<Point> food = new ArrayList<>(List.of(new Point(8, 8)));
        CellType[][] cellArray = new CellType[10][10];
        cellArray[8][8] = CellType.FOOD;

        // check list size
        Assert.assertEquals(2, c.getAnts().size());

        c.getAnts().get(0).setPosition(new Point(7, 7));
        c.getAnts().get(1).setPosition(new Point(6, 6));

        // Init, ants moving to 7, 7
        c.next(cellArray);

        // Check
        Assert.assertEquals(2, c.getAnts().size());
        Assert.assertEquals(Status.RETURNING_COLONY, c.getAnts().get(0).getStatus());
        Assert.assertEquals(Status.WANDERING, c.getAnts().get(1).getStatus());

        // ants on same position, talk
        c.next(cellArray);

        // Check
        Assert.assertEquals(Status.RETURNING_COLONY, c.getAnts().get(0).getStatus());
        Assert.assertEquals(Status.RETURNING_COLONY, c.getAnts().get(1).getStatus());
        Assert.assertEquals(food.get(0), c.getAnts().get(1).getLastKnownFoodPosition());
    }

    @Test
    public void antsAttackEnnemyColony() {
        // given
        Colony c = new Colony(2, new Point(5, 5));
        Colony c2 = new Colony(0, new Point(4, 4));

        CellType[][] cellArray = new CellType[10][10];
        cellArray[5][5] = CellType.COLONY;
        cellArray[4][4] = CellType.COLONY;

        c.next(cellArray);
        
        Assert.assertEquals(new Point(4, 4), c.getAnts().get(0).getPosition());
        Assert.assertEquals(new Point(4, 4), c.getAnts().get(1).getPosition());
        // ants found second colony
        c.next(cellArray);

        Assert.assertEquals(new Point(4, 4), c.getAnts().get(0).getPosition());
        Assert.assertEquals(new Point(4, 4), c.getAnts().get(1).getPosition());
        Assert.assertEquals(90, c2.getHp());
    }

    @Test
    public void antsAttackOtherAnts() {
        Colony c = new Colony(1, new Point(9, 9));
        Colony c2 = new Colony(1, new Point(5, 5));

        CellType[][] cellArray = new CellType[10][10];
        cellArray[5][5] = CellType.COLONY;
        cellArray[4][4] = CellType.COLONY;

        c.next(cellArray);
        c2.next(cellArray);

        // c ant in 8,8
        // c2 ant in 6,6

        Assert.assertEquals(new Point(8, 8), c.getAnts().get(0).getPosition());
        Assert.assertEquals(new Point(6, 6), c2.getAnts().get(0).getPosition());
        // ants move to 7,7 and meet
        c.next(cellArray);
        c2.next(cellArray);
        // ants in the right position
        Assert.assertEquals(new Point(7, 7), c.getAnts().get(0).getPosition());
        Assert.assertEquals(new Point(7, 7), c2.getAnts().get(0).getPosition());
        // ants attacked each other
        Assert.assertEquals(5, c.getAnts().get(0).getHp());
        Assert.assertEquals(5, c2.getAnts().get(0).getHp());
    }
}
