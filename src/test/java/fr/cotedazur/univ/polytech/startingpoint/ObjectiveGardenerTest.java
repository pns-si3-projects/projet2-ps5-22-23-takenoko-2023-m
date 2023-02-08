package fr.cotedazur.univ.polytech.startingpoint;

import fr.cotedazur.univ.polytech.startingpoint.bots.Bot;
import fr.cotedazur.univ.polytech.startingpoint.bots.PrimaryBot;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ObjectiveGardenerTest {

    @Test
    void getNb() {
        ObjectiveGardener objectiveGardener = new ObjectiveGardener("gardener",2, 2,TypeOfTile.GREEN);
        assertEquals(2, objectiveGardener.getNbPointsWin());
    }

    @Test
    void setNb() {


        ObjectiveGardener objectiveGardener = new ObjectiveGardener("gardener",2, 11,TypeOfTile.GREEN);
        objectiveGardener.setNbPointsWin(3);
        assertEquals(3, objectiveGardener.getNbPointsWin());
    }

    @Test
    void isValid() {
        Board board = new Board();
        ObjectiveGardener objectiveGardener = new ObjectiveGardener("gardener",3, 11,TypeOfTile.GREEN);
        Bot bot1 = new PrimaryBot(board, "Robot 1");
        board.addTile(new Tile(new Coordinate(0,0)));
        board.addTile(new Tile(new Coordinate(1,0)));
        board.addTile(new Tile(new Coordinate(0,1)));
        board.addTile(new Tile(new Coordinate(1,1)));
        board.addTile(new Tile(new Coordinate(1,2)));
        for (int i = 0; i < board.getBoardTiles().size(); i++) {
            board.getBoardTiles().get(i).irrigate();
        }
        board.moveGardenerOn(new Coordinate(1,0));
        board.moveGardenerOn(new Coordinate(1,1));
        board.moveGardenerOn(new Coordinate(1,0));
        assertTrue(objectiveGardener.isValid(bot1, board));
    }

    @Test
    void getType() {
        ObjectiveGardener objectiveGardener = new ObjectiveGardener("gardener",2, 11,TypeOfTile.GREEN);
        assertEquals("gardener", objectiveGardener.getType());
    }

    @Test
    void setType() {
        ObjectiveGardener objectiveGardener = new ObjectiveGardener("gardener",2, 11,TypeOfTile.GREEN);
        objectiveGardener.setType("gardener");
        assertEquals("gardener", objectiveGardener.getType());
    }
}
