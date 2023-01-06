package fr.cotedazur.univ.polytech.startingpoint;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

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
        assertFalse(c0_0.equals(new Tile(0,0)));
    }

    @Test
    void testGetNumberOfNeighbours() {
        ArrayList<Coordinate> occupiedCoordinates = new ArrayList<>();
        occupiedCoordinates.add(new Coordinate(0,0));
        occupiedCoordinates.add(new Coordinate(1,0));

        Coordinate c0_1 = new Coordinate(0,1);
        Coordinate c1_1 = new Coordinate(1,1);

        assertEquals(2, c0_1.getNumberOfNeighbours(occupiedCoordinates));
        assertEquals(1, c1_1.getNumberOfNeighbours(occupiedCoordinates));
    }

    @Test
    void testGetNeighbourCoordinates() {
        Coordinate c0_0 = new Coordinate(0,0);
        ArrayList<Coordinate> neighboursToTest = c0_0.getNeighbourCoordinates();
        ArrayList<Coordinate> neighbours = new ArrayList<>();
        neighbours.add(new Coordinate(-1,0));
        neighbours.add(new Coordinate(-1,1));
        neighbours.add(new Coordinate(0,-1));
        neighbours.add(new Coordinate(0,1));
        neighbours.add(new Coordinate(1,-1));
        neighbours.add(new Coordinate(1,0));

        for (int i = 0; i < neighboursToTest.size(); i++) {
            assertTrue(neighbours.contains(neighboursToTest.get(i)));
        }
    }

}