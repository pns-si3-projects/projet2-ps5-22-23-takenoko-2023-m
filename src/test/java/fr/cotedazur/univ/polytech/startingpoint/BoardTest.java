package fr.cotedazur.univ.polytech.startingpoint;

import fr.cotedazur.univ.polytech.startingpoint.bots.Bot;
import fr.cotedazur.univ.polytech.startingpoint.bots.PrimaryBot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

class BoardTest {
    Board twoTiles = new Board();
    Tile t0_0 = new Tile(new Coordinate(0,0), TypeOfTile.GREEN, TypeOfArrangement.NONE);
    Tile tx1y0 = new Tile(new Coordinate(1,0), TypeOfTile.GREEN, TypeOfArrangement.NONE);

    @BeforeEach
    void setup() {
        twoTiles.addTile(t0_0);
        twoTiles.addTile(tx1y0);
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

    @Test
    void testPutBackInTileStack(){
        Board board = new Board();
        assertEquals(27, board.getTileStack().sizeTileStack());
        Tile t = new Tile(null, TypeOfTile.RED, TypeOfArrangement.NONE);
        board.putBackInTileStack(t);
        assertEquals(28,board.getTileStack().sizeTileStack());
    }

    @Test
    void testPutBackWithPlayer(){
        Board board = new Board();
        PrimaryBot p =  new PrimaryBot(board, "Simon");
        p.checkBetterCard();
        List<Tile> liste = board.pickThreeTiles();
        assertEquals(24,board.getTileStack().sizeTileStack());
        Tile t = p.chooseBetterOf3Tiles(liste);
        assertEquals(26,board.getTileStack().sizeTileStack());
    }

    @Test
    void testIrrigationPlacement() {
        Tile tx0y1 = new Tile(new Coordinate(0,1), TypeOfTile.GREEN, TypeOfArrangement.NONE);
        Tile tx1y1 = new Tile(new Coordinate(1,1), TypeOfTile.GREEN, TypeOfArrangement.NONE);
        Board board = new Board();
        board.addTile(tx1y0);
        board.addTile(tx0y1);
        board.addTile(tx1y1);
        ArrayList<Irrigation> legalIrrigationPlacement = board.getLegalIrrigationPlacement();
        board.addIrrigation(legalIrrigationPlacement.get(0));
        assertTrue(tx1y0.isIrrigated());
        assertTrue(tx0y1.isIrrigated());
        assertFalse(tx1y1.isIrrigated());
        board.addIrrigation(new Irrigation(new Coordinate(1,0), new Coordinate(1,1)));
        System.out.println(legalIrrigationPlacement);
        assertEquals(10, legalIrrigationPlacement.size());
        assertTrue(tx1y0.isIrrigated());
        assertTrue(tx0y1.isIrrigated());
        assertTrue(tx1y1.isIrrigated());


    }
    @Test
    void testLegalIrrigationPlacement() {
        Board board = new Board();
        ArrayList<Irrigation> legalIrrigationPlacement = board.getLegalIrrigationPlacement();
        assertTrue(legalIrrigationPlacement.size() == 6);
        board.addTile(new Tile(new Coordinate(1,0), TypeOfTile.GREEN, TypeOfArrangement.NONE));
        board.addTile(new Tile(new Coordinate(0,1), TypeOfTile.GREEN, TypeOfArrangement.NONE));
        board.addIrrigation(legalIrrigationPlacement.get(0));
        assertEquals(9, legalIrrigationPlacement.size());   //-1, +4
    }

    @Test
    void testIsIrigated(){
        Board board = new Board();
        board.addTile(new Tile(new Coordinate(-1,0), TypeOfTile.GREEN, TypeOfArrangement.NONE));
        assertTrue(board.getBoardTiles().get(1).isIrrigated());

    }
}
