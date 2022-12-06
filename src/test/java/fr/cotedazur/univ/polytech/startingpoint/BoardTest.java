package fr.cotedazur.univ.polytech.startingpoint;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {
    Board twoTiles = new Board();
    Tile t0_0 = new Tile(0,0);
    Tile t0_1 = new Tile(0,1);

    @BeforeEach
    void setup() {
        twoTiles.addTile(t0_0);
        twoTiles.addTile(t0_1);
    }

    @Test
    void testScanAvailableTilePosition() {
        ArrayList<Coordinate> tilesToTest = twoTiles.scanAvailableTilePosition();

        for (int i = 0; i < tilesToTest.size(); i++) {
            System.out.println(tilesToTest.get(i));
        }
    }
}