package fr.cotedazur.univ.polytech.startingpoint;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TileTest {

    Tile t0_0 = new Tile(0,0);
    Tile t0_1 = new Tile(0,1);
    Tile t1_1 = new Tile(1,1);
    Tile t0_n2 = new Tile(0,-2);
    Tile tn1_n1 = new Tile(-1,-1);
    Tile t1_n2 = new Tile(1,-2);

    @Test
    void testIsNeighbour() {
        assertTrue(t0_0.isNeighbour(t0_1));
        assertFalse(t0_0.isNeighbour(t1_1));
        assertTrue(t0_1.isNeighbour(t1_1));
        assertTrue(tn1_n1.isNeighbour(t0_n2));
        assertTrue(t1_n2.isNeighbour(t0_n2));
        assertFalse(tn1_n1.isNeighbour(t1_n2));
    }
}