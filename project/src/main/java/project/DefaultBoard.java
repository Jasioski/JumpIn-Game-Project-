package project;

import project.model.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Sets up a default layout for the board.
 */
public class DefaultBoard extends Board {

    /**
     * Logs an error if it comes up in creating the board.
     */
    private static Logger logger = LogManager.getLogger(DefaultBoard.class);

    /**
     * Creates a default setup for the board.
     */
    public DefaultBoard() {
        super(5);

        Hole hole1 = new Hole(0, 0);
        Hole hole2 = new Hole(0, 4);
        Hole hole3 = new Hole(4, 0);
        Hole hole4 = new Hole(4, 4);
        Hole hole5 = new Hole(2, 2);

        Fox fox1 = new Fox(3,2,3,3);
        Fox fox2 = new Fox(0,1,1,1);

        Rabbit rabbit1 = new Rabbit(4, 2);
        Rabbit rabbit2 = new Rabbit(2, 0);
        Rabbit rabbit3 = new Rabbit(0, 3);
        Rabbit rabbit4 = new Rabbit(0, 0);

        Mushroom mushroom2 = new Mushroom(0, 0);
        Mushroom mushroom3 = new Mushroom(1, 3);

        ElevatedBoardItem elevatedBoardItem1 = new ElevatedBoardItem(0,2);
        ElevatedBoardItem elevatedBoardItem2 = new ElevatedBoardItem(2,0);
        ElevatedBoardItem elevatedBoardItem3 = new ElevatedBoardItem(2,4);
        ElevatedBoardItem elevatedBoardItem4 = new ElevatedBoardItem(4,2);

        try {
            this.setItem(hole1);
            this.setItem(hole2);
            this.setItem(hole3);
            this.setItem(hole4);
            this.setItem(hole5);

            this.setItem(fox1);
            this.setItem(fox2);

            this.setItem(elevatedBoardItem1);
            this.setItem(elevatedBoardItem2);
            this.setItem(elevatedBoardItem3);
            this.setItem(elevatedBoardItem4);
            this.setItem(rabbit1);
            this.setItem(rabbit2);
            this.setItem(rabbit3);

            this.setItem(mushroom2);
            this.setItem(mushroom3);
            hole2.contain(rabbit4);

        } catch (Exception e) {
            logger.error("Default Board Issue: " + e);
        }
    }


}
