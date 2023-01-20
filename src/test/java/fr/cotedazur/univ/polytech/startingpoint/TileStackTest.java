package fr.cotedazur.univ.polytech.startingpoint;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TileStackTest {

    @Test
    void pickThreeTiles() {
        TileStack tileStack = new TileStack();
        tileStack.putBelow(new Tile(new Coordinate(0,0)));
        tileStack.putBelow(new Tile(new Coordinate(1,0)));
        tileStack.putBelow(new Tile(new Coordinate(0,1)));
        tileStack.putBelow(new Tile(new Coordinate(1,1)));
        tileStack.putBelow(new Tile(new Coordinate(1,2)));

        assertEquals(32, tileStack.getStack().size());
        List<Tile> threeList = tileStack.pickThreeTiles();
        assertEquals(3,threeList.size());
        assertEquals(29, tileStack.getStack().size());
    }
}