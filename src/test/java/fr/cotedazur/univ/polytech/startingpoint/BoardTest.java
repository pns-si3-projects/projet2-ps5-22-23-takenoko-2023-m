package fr.cotedazur.univ.polytech.startingpoint;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

class BoardTest {
    Board twoTiles = new Board();
    Tile t0_0 = new Tile(new Coordinate(0,0));
    Tile t1_0 = new Tile(new Coordinate(1,0));

    @BeforeEach
    void setup() {
        twoTiles.addTile(t0_0);
        twoTiles.addTile(t1_0);
    }

    @Test   //tests for the fisrt version, the one that only requires new tiles to be touching the edge, not the ones having two neighbours
    void testScanAvailableTilePosition() {
        List<Coordinate> tilesToTest = twoTiles.scanAvailableTilePosition();
        List<Coordinate> trueTiles = new ArrayList<>();

        trueTiles.add(new Coordinate(-1, 0));
        trueTiles.add(new Coordinate(-1, 1));
        trueTiles.add(new Coordinate(0, -1));
        trueTiles.add(new Coordinate(0, 1));
        trueTiles.add(new Coordinate(1, -1));


        assertEquals(5, tilesToTest.size());
        for (int i = 0; i < tilesToTest.size(); i++) {
            //System.out.println(tilesToTest.get(i));   //only to visualise the ArrayList
            assertEquals(tilesToTest.get(i), trueTiles.get(i));
        }
    }
}
