package fr.cotedazur.univ.polytech.startingpoint;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArrangementTest {



    @Test
    void testGetTypeOfArrangment() {
        Tile tile = new Tile(TypeOfTile.GREEN);
        assertEquals(TypeOfArrangement.NONE, tile.getTypeOfArrangement());
        tile.setTypeOfArrangement(TypeOfArrangement.ENCLOSURE);
        assertEquals(TypeOfArrangement.ENCLOSURE, tile.getTypeOfArrangement());

        Tile tile2 = new Tile(TypeOfTile.RED, TypeOfArrangement.BASIN);
        assertEquals(TypeOfArrangement.BASIN, tile2.getTypeOfArrangement());
        tile2.setTypeOfArrangement(TypeOfArrangement.FERTILIZER);
        assertEquals(TypeOfArrangement.FERTILIZER, tile2.getTypeOfArrangement());
    }

    @Test
    void testArrangmentEffect() {
        Tile tile = new Tile(0,1,TypeOfTile.GREEN, TypeOfArrangement.ENCLOSURE);
        assertEquals(0, tile.getBamboo());
        tile.irrigate();
        tile.grow();
        assertEquals(1, tile.getBamboo());
        tile.eatBamboo();
        assertEquals(1, tile.getBamboo());

        Tile tile2 = new Tile(1,0,TypeOfTile.RED, TypeOfArrangement.FERTILIZER);
        assertEquals(0, tile2.getBamboo());
        tile2.irrigate();
        tile2.grow();
        assertEquals(2, tile2.getBamboo());
    }



}