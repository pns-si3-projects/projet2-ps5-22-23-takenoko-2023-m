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



}