package fr.cotedazur.univ.polytech.startingpoint;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TypeOfTileTest {

    @Test
    void testEquals() {
        TypeOfTile red = TypeOfTile.RED;
        TypeOfTile green = TypeOfTile.GREEN;
        TypeOfTile yellow = TypeOfTile.YELLOW;
        assertFalse(red.equals(green));
        TypeOfTile red2 = TypeOfTile.RED;
        assertTrue(red.equals(red2));
    }


}