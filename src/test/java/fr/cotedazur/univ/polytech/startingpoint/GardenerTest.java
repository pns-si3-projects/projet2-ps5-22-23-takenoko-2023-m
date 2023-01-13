package fr.cotedazur.univ.polytech.startingpoint;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Gardener Tests")
class GardenerTest {
    private static Board board;
    @BeforeAll
    static void beforeAll() {
        board = new Board();
        board.addTile(new Tile(1,0, TypeOfTile.GREEN));
        board.addTile(new Tile(0,1,TypeOfTile.RED));
        board.addTile(new Tile(1,1,TypeOfTile.GREEN));
        board.addTile(new Tile(1,2,TypeOfTile.YELLOW));

    }
    @Test
    @DisplayName("Gardener is moving to a tile ungrown")
    void moveOnUngrown() {
        System.out.println(board.moveGardenerOn(new Coordinate(1,0)));
        assertTrue(board.getTile(new Coordinate(1,0)).getBamboo()==1);
        assertTrue(board.getTile(new Coordinate(1,1)).getBamboo()==1);
        assertFalse(board.getTile(new Coordinate(0,1)).getBamboo()==1);
        assertFalse(board.getTile(new Coordinate(1,2)).getBamboo()==1);

    }
    @Test
    @DisplayName("Gardener is moving to a tile grown to the max")
    void moveOnFullgrown() {
        Tile tileTest4 = new Tile(new Coordinate(2,2));
        tileTest4.setBamboo(4);
        board.addTile(tileTest4);
        System.out.println(board.moveGardenerOn(new Coordinate(2,2)));
        assertTrue(board.getTile(new Coordinate(2,2)).getBamboo()==4);
    }


}