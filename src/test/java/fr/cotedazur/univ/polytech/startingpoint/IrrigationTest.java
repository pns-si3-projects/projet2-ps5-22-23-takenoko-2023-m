package fr.cotedazur.univ.polytech.startingpoint;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class IrrigationTest {
    ArrayList<Tile> boardTiles;
    Tile x0y0;
    Tile x1y0;
    Tile xn1y1;
    Tile x0y1;
    Tile x1y1;
    Board board;

    Irrigation verticalIrrigation;

    Irrigation fSlashIrrigation;

    Irrigation bSlashIrrigation;


    @BeforeEach
    void setup() {
        x0y0 = new Tile(new Coordinate(0,0));
        x1y0 = new Tile(new Coordinate(1,0));
        xn1y1 = new Tile(new Coordinate(-1,1));
        x0y1 = new Tile(new Coordinate(0,1));
        x1y1 = new Tile(new Coordinate(1,1));
        boardTiles = new ArrayList<>( Arrays.asList(x0y0,x1y0,xn1y1,x0y1,x1y1) );
        board = new Board();
        board.setBoardTiles(boardTiles);
        verticalIrrigation = new Irrigation(x0y0,x1y0,board);
        fSlashIrrigation = new Irrigation(x1y0,x1y1,board);
        bSlashIrrigation = new Irrigation(x0y0,xn1y1,board);
    }

    @Test
    void testTypeAttribution() {
        assertEquals(TypeOfIrrigation.vertical,verticalIrrigation.getIrrigationType());
        assertEquals(TypeOfIrrigation.fSlash,fSlashIrrigation.getIrrigationType());
        assertEquals(TypeOfIrrigation.bSlash,bSlashIrrigation.getIrrigationType());
    }

    @Test
    void testGetLegalIrrigationPlacement() {
        //tests for the vertical irrigations
        assertEquals(2, verticalIrrigation.getLegalIrrigationPlacement(boardTiles).size()); //only the bottom tile exists in boardTiles so only two irrigations will be outputted
        assertTrue(boardTiles.containsAll(verticalIrrigation.getLegalIrrigationPlacement(boardTiles).get(0).getTiles()));
        assertTrue(boardTiles.containsAll(verticalIrrigation.getLegalIrrigationPlacement(boardTiles).get(1).getTiles()));
        boardTiles.add(new Tile(new Coordinate(1,-1)));
        assertEquals(4, verticalIrrigation.getLegalIrrigationPlacement(boardTiles).size());
        assertTrue(boardTiles.containsAll(verticalIrrigation.getLegalIrrigationPlacement(boardTiles).get(0).getTiles()));
        assertTrue(boardTiles.containsAll(verticalIrrigation.getLegalIrrigationPlacement(boardTiles).get(1).getTiles()));
        assertTrue(boardTiles.containsAll(verticalIrrigation.getLegalIrrigationPlacement(boardTiles).get(2).getTiles()));
        assertTrue(boardTiles.containsAll(verticalIrrigation.getLegalIrrigationPlacement(boardTiles).get(3).getTiles()));

        //tests for the fslash irrigations
        assertEquals(2, fSlashIrrigation.getLegalIrrigationPlacement(boardTiles).size());
        assertTrue(boardTiles.containsAll(fSlashIrrigation.getLegalIrrigationPlacement(boardTiles).get(0).getTiles()));
        assertTrue(boardTiles.containsAll(fSlashIrrigation.getLegalIrrigationPlacement(boardTiles).get(1).getTiles()));
        boardTiles.add(new Tile(new Coordinate(2,0)));
        assertEquals(4, fSlashIrrigation.getLegalIrrigationPlacement(boardTiles).size());
        assertTrue(boardTiles.containsAll(fSlashIrrigation.getLegalIrrigationPlacement(boardTiles).get(0).getTiles()));
        assertTrue(boardTiles.containsAll(fSlashIrrigation.getLegalIrrigationPlacement(boardTiles).get(1).getTiles()));
        assertTrue(boardTiles.containsAll(fSlashIrrigation.getLegalIrrigationPlacement(boardTiles).get(2).getTiles()));
        assertTrue(boardTiles.containsAll(fSlashIrrigation.getLegalIrrigationPlacement(boardTiles).get(3).getTiles()));

        //tests for bslash irrigations
        assertEquals(2, bSlashIrrigation.getLegalIrrigationPlacement(boardTiles).size());
        assertTrue(boardTiles.containsAll(bSlashIrrigation.getLegalIrrigationPlacement(boardTiles).get(0).getTiles()));
        assertTrue(boardTiles.containsAll(bSlashIrrigation.getLegalIrrigationPlacement(boardTiles).get(1).getTiles()));
        boardTiles.add(new Tile(new Coordinate(-1,0)));
        assertEquals(4, bSlashIrrigation.getLegalIrrigationPlacement(boardTiles).size());
        assertTrue(boardTiles.containsAll(bSlashIrrigation.getLegalIrrigationPlacement(boardTiles).get(0).getTiles()));
        assertTrue(boardTiles.containsAll(bSlashIrrigation.getLegalIrrigationPlacement(boardTiles).get(1).getTiles()));
        assertTrue(boardTiles.containsAll(bSlashIrrigation.getLegalIrrigationPlacement(boardTiles).get(2).getTiles()));
        assertTrue(boardTiles.containsAll(bSlashIrrigation.getLegalIrrigationPlacement(boardTiles).get(3).getTiles()));
    }

    @Test
    void testTilesAreIrrigated() {
        assertTrue(boardTiles.get(boardTiles.indexOf(x0y0)).isIrrigated());
        Tile x2y0 = new Tile(new Coordinate(2,0));
        boardTiles.add(x2y0);
        assertFalse(boardTiles.get(boardTiles.indexOf(x2y0)).isIrrigated());
        new Irrigation(boardTiles.get(boardTiles.indexOf(x2y0)), boardTiles.get(boardTiles.indexOf(x1y0)),board);
        assertTrue(boardTiles.get(boardTiles.indexOf(x2y0)).isIrrigated());
    }


    @Test
    void testExceptionThrowing() {
        //Irrigation shouldThrowAnException = new Irrigation(boardTiles.get(boardTiles.indexOf(xn1y1)), boardTiles.get(boardTiles.indexOf(x1y1)));
        //assertThrows(RuntimeException.class , (Executable) new Irrigation(boardTiles.get(boardTiles.indexOf(xn1y1)), boardTiles.get(boardTiles.indexOf(x1y1))));
        try {
            Irrigation youShouldNotExist = new Irrigation(boardTiles.get(boardTiles.indexOf(xn1y1)), boardTiles.get(boardTiles.indexOf(x1y1)),board);
        } catch (RuntimeException e) {
            assertTrue(true);   //asserts that it caught the exception;
            assertEquals("These tiles are not neighbours (" + xn1y1 + "\t\t" + x1y1 + ")",e.getMessage());
        }
    }

    @Test
    void testEquals() {
        assertTrue(verticalIrrigation.equals(new Irrigation(x0y0,x1y0,board)));
    }
}
