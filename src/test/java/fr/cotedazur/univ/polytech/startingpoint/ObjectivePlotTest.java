package fr.cotedazur.univ.polytech.startingpoint;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ObjectivePlotTest {

    @Test
    void isValid() {
        Board board = new Board();
        ObjectivePlot objectivePlot = new ObjectivePlot("line2", 3);
        ArrayList<ObjectiveInterface> objectives = new ArrayList<ObjectiveInterface>();
        objectives.add(objectivePlot);
        Player bot1 = new Player(board, "Robot 1", objectives);
        board.addTile(new Tile(new Coordinate(0,0)));
        board.addTile(new Tile(new Coordinate(1,0)));
        assertTrue(objectivePlot.isValid(bot1, board));
    }

    @Test
    void getType() {
        ObjectivePlot objectivePlot = new ObjectivePlot("line2", 3);
        assertEquals("line2", objectivePlot.getType());
    }

    @Test
    void setType() {
        ObjectivePlot objectivePlot = new ObjectivePlot("line2", 3);
        objectivePlot.setType("line3");
        assertEquals("line3", objectivePlot.getType());
    }

    @Test
    void testToString() {
        ObjectivePlot objectivePlot = new ObjectivePlot("line2", 3);
        assertEquals("Objectif de type line2", objectivePlot.toString());
    }
}