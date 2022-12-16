package fr.cotedazur.univ.polytech.startingpoint;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ObjectiveGardenerTest {

    @Test
    void getNb() {
        ObjectiveGardener objectiveGardener = new ObjectiveGardener("gardener",2);
        assertEquals(2, objectiveGardener.getNbPointsWin());
    }

    @Test
    void setNb() {
        ObjectiveGardener objectiveGardener = new ObjectiveGardener("gardener",2);
        objectiveGardener.setNb(3);
        assertEquals(3, objectiveGardener.getNbPointsWin());
    }

    @Test
    void isValid() {
        Board board = new Board();
        ObjectiveGardener objectiveGardener = new ObjectiveGardener("gardener",2);
        ArrayList<ObjectiveInterface> objectives = new ArrayList<ObjectiveInterface>();
        objectives.add(objectiveGardener);
        Player bot1 = new Player(board, "Robot 1", objectives);
        board.addTile(new Tile(0,0));
        board.addTile(new Tile(1,0));
        board.addTile(new Tile(0,1));
        board.addTile(new Tile(1,1));
        board.addTile(new Tile(1,2));
        board.moveGardenerOn(new Coordinate(1,0));
        board.moveGardenerOn(new Coordinate(1,1));
        board.moveGardenerOn(new Coordinate(1,0));
        assertTrue(objectiveGardener.isValid(board));
    }

    @Test
    void getType() {
        ObjectiveGardener objectiveGardener = new ObjectiveGardener("gardener",2);
        assertEquals("gardener", objectiveGardener.getType());
    }

    @Test
    void setType() {
        ObjectiveGardener objectiveGardener = new ObjectiveGardener("gardener",2);
        objectiveGardener.setType("gardener");
        assertEquals("gardener", objectiveGardener.getType());
    }
}