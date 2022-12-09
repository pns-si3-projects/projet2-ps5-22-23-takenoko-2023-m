package fr.cotedazur.univ.polytech.startingpoint;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CoordinateTest {
    Coordinate c0_0;
    Coordinate cn1_n1;

    @BeforeEach
    void setup() {
        c0_0 = new Coordinate(0,0);
        cn1_n1 = new Coordinate(-1,-1);
    }

    @Test
    void testEquals() {
        assertEquals(c0_0, c0_0);
        assertEquals(c0_0, new Coordinate(0,0));
        assertNotEquals(c0_0, cn1_n1);
    }

}