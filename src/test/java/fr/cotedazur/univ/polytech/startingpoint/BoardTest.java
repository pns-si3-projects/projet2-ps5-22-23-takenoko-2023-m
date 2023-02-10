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
        //simple test with 3 tiles in the board
        List<Coordinate> tilesToTest_2 = twoTiles.scanAvailableTilePosition();
        List<Coordinate> trueTiles_2 = new ArrayList<>();

        trueTiles_2.add(new Coordinate(-1, 0));
        trueTiles_2.add(new Coordinate(-1, 1));
        trueTiles_2.add(new Coordinate(0, -1));
        trueTiles_2.add(new Coordinate(0, 1));
        trueTiles_2.add(new Coordinate(1, -1));

        assertEquals(5, tilesToTest_2.size());
        for (int i = 0; i < tilesToTest_2.size(); i++) {
            //System.out.println(tilesToTest_2.get(i));   //only to visualise the ArrayList
            assertEquals(tilesToTest_2.get(i), trueTiles_2.get(i));
        }

        //more complex test with 10 tiles in the board
        Board tenTiles = new Board();
        tenTiles.addTile(new Tile(new Coordinate(-1, 0), TypeOfTile.GREEN, TypeOfArrangement.NONE));
        tenTiles.addTile(new Tile(new Coordinate(1, 0), TypeOfTile.GREEN, TypeOfArrangement.NONE));
        tenTiles.addTile(new Tile(new Coordinate(-1, 1), TypeOfTile.GREEN, TypeOfArrangement.NONE));
        tenTiles.addTile(new Tile(new Coordinate(0, 1), TypeOfTile.GREEN, TypeOfArrangement.NONE));
        tenTiles.addTile(new Tile(new Coordinate(-1, 2), TypeOfTile.GREEN, TypeOfArrangement.NONE));
        tenTiles.addTile(new Tile(new Coordinate(-2, 2), TypeOfTile.GREEN, TypeOfArrangement.NONE));
        tenTiles.addTile(new Tile(new Coordinate(0, 2), TypeOfTile.GREEN, TypeOfArrangement.NONE));
        tenTiles.addTile(new Tile(new Coordinate(-2, 1), TypeOfTile.GREEN, TypeOfArrangement.NONE));
        tenTiles.addTile(new Tile(new Coordinate(1, 1), TypeOfTile.GREEN, TypeOfArrangement.NONE));

        List<Coordinate> trueTiles_10 = new ArrayList<>();
        trueTiles_10.add(new Coordinate(0,-1));
        trueTiles_10.add(new Coordinate(1,-1));
        trueTiles_10.add(new Coordinate(-2,0));
        trueTiles_10.add(new Coordinate(2,0));
        trueTiles_10.add(new Coordinate(-2,3));
        trueTiles_10.add(new Coordinate(-1,3));
        trueTiles_10.add(new Coordinate(-3,2));
        trueTiles_10.add(new Coordinate(1,2));

        List<Coordinate> tilesToTest_10 = tenTiles.scanAvailableTilePosition();
        assertEquals(8, tilesToTest_10.size());
        for (int i = 0; i < tilesToTest_10.size(); i++) {
            //System.out.println(tilesToTest_10.get(i));
            assertEquals(tilesToTest_10.get(i), trueTiles_10.get(i));
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

        //simple test with 3 tiles
        Board board_3 = new Board();
        ArrayList<Irrigation> legalIrrigationPlacement_3 = board_3.getLegalIrrigationPlacement();
        assertTrue(legalIrrigationPlacement_3.size() == 6);
        board_3.addTile(new Tile(new Coordinate(1,0)));
        board_3.addTile(new Tile(new Coordinate(0,1)));
        board_3.addIrrigation(legalIrrigationPlacement_3.get(0));

        List<Irrigation> trueIrrigations_3 = new ArrayList<>();
        trueIrrigations_3.add(new Irrigation(new Coordinate(-1,1), new Coordinate(0,1)));
        trueIrrigations_3.add(new Irrigation(new Coordinate(-1,0), new Coordinate(-1,1)));
        trueIrrigations_3.add(new Irrigation(new Coordinate(-1,0), new Coordinate(0,-1)));
        trueIrrigations_3.add(new Irrigation(new Coordinate(0,-1), new Coordinate(1,-1)));
        trueIrrigations_3.add(new Irrigation(new Coordinate(1,-1), new Coordinate(1,0)));
        trueIrrigations_3.add(new Irrigation(new Coordinate(0,0), new Coordinate(1,0)));
        trueIrrigations_3.add(new Irrigation(new Coordinate(0,0), new Coordinate(0,1)));
        trueIrrigations_3.add(new Irrigation(new Coordinate(1,0), new Coordinate(1,1)));
        trueIrrigations_3.add(new Irrigation(new Coordinate(0,1), new Coordinate(1,1)));

        assertEquals(9, legalIrrigationPlacement_3.size());   //-1, +4
        for (int i = 0; i < legalIrrigationPlacement_3.size(); i++) {
            //System.out.println(legalIrrigationPlacement_3.get(i));
            assertEquals(trueIrrigations_3.get(i), legalIrrigationPlacement_3.get(i));
        }

        //bigger test with 10 tiles
        Board board_10 = new Board();
        board_10.addTile(new Tile(new Coordinate(-1, 0), TypeOfTile.GREEN, TypeOfArrangement.NONE));
        board_10.addTile(new Tile(new Coordinate(1, 0), TypeOfTile.GREEN, TypeOfArrangement.NONE));
        board_10.addTile(new Tile(new Coordinate(-1, 1), TypeOfTile.GREEN, TypeOfArrangement.NONE));
        board_10.addTile(new Tile(new Coordinate(0, 1), TypeOfTile.GREEN, TypeOfArrangement.NONE));
        board_10.addTile(new Tile(new Coordinate(-1, 2), TypeOfTile.GREEN, TypeOfArrangement.NONE));
        board_10.addTile(new Tile(new Coordinate(-2, 2), TypeOfTile.GREEN, TypeOfArrangement.NONE));
        board_10.addTile(new Tile(new Coordinate(0, 2), TypeOfTile.GREEN, TypeOfArrangement.NONE));
        board_10.addTile(new Tile(new Coordinate(-2, 1), TypeOfTile.GREEN, TypeOfArrangement.NONE));
        board_10.addTile(new Tile(new Coordinate(1, 1), TypeOfTile.GREEN, TypeOfArrangement.NONE));

        board_10.addIrrigation(new Irrigation(new Coordinate(-1,0), new Coordinate(-1,1)));
        board_10.addIrrigation(new Irrigation(new Coordinate(-2,1), new Coordinate(-1,0)));
        board_10.addIrrigation(new Irrigation(new Coordinate(-1,1), new Coordinate(0,1)));
        board_10.addIrrigation(new Irrigation(new Coordinate(0,1), new Coordinate(-1,2)));
        board_10.addIrrigation(new Irrigation(new Coordinate(-1,2), new Coordinate(0,2)));

        List<Irrigation> trueIrrigation_10 = new ArrayList<>();
        trueIrrigation_10.add(new Irrigation(new Coordinate(0,1), new Coordinate(1,0)));
        trueIrrigation_10.add(new Irrigation(new Coordinate(-1,0), new Coordinate(0,-1)));
        trueIrrigation_10.add(new Irrigation(new Coordinate(0,-1), new Coordinate(1,-1)));
        trueIrrigation_10.add(new Irrigation(new Coordinate(1,-1), new Coordinate(1,0)));
        trueIrrigation_10.add(new Irrigation(new Coordinate(-2,1), new Coordinate(-1,1)));
        trueIrrigation_10.add(new Irrigation(new Coordinate(-1,0), new Coordinate(0,0)));
        trueIrrigation_10.add(new Irrigation(new Coordinate(-1,1), new Coordinate(0,0)));
        trueIrrigation_10.add(new Irrigation(new Coordinate(-2,0), new Coordinate(-1,0)));
        trueIrrigation_10.add(new Irrigation(new Coordinate(-2,0), new Coordinate(-2,1)));
        trueIrrigation_10.add(new Irrigation(new Coordinate(0,0), new Coordinate(0,1)));
        trueIrrigation_10.add(new Irrigation(new Coordinate(-1,1), new Coordinate(-1,2)));
        trueIrrigation_10.add(new Irrigation(new Coordinate(0,1), new Coordinate(0,2)));
        trueIrrigation_10.add(new Irrigation(new Coordinate(-1,2), new Coordinate(-1,3)));
        trueIrrigation_10.add(new Irrigation(new Coordinate(-1,3), new Coordinate(0,2)));

        List<Irrigation> legalIrrigationPlacement_10 = board_10.getLegalIrrigationPlacement();
        assertEquals(14, legalIrrigationPlacement_10.size());
        for (int i = 0; i < legalIrrigationPlacement_10.size(); i++) {
            //System.out.println(legalIrrigationPlacement_10.get(i));
            assertEquals(trueIrrigation_10.get(i), legalIrrigationPlacement_10.get(i));
        }
    }

    @Test
    void testIsIrrigated(){
        Board board = new Board();
        board.addTile(new Tile(new Coordinate(-1,0), TypeOfTile.GREEN, TypeOfArrangement.NONE));
        assertTrue(board.getBoardTiles().get(1).isIrrigated());

    }
}
