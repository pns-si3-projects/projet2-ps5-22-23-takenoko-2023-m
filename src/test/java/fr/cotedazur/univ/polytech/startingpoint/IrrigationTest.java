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
        verticalIrrigation = new Irrigation(x0y0,x1y0);
        fSlashIrrigation = new Irrigation(x1y0,x1y1);
        bSlashIrrigation = new Irrigation(x0y0,xn1y1);
    }

    @Test
    void testTypeAttribution() {
        assertEquals(TypeOfIrrigation.vertical,verticalIrrigation.getIrrigationType());
        assertEquals(TypeOfIrrigation.fSlash,fSlashIrrigation.getIrrigationType());
        assertEquals(TypeOfIrrigation.bSlash,bSlashIrrigation.getIrrigationType());
    }

    @Test
    void testNeighbourIrrigation() {

        //Test for vertical irrigations
        assertTrue(verticalIrrigation.getNeighbourIrrigations().contains(new Irrigation(new Coordinate(0,0), new Coordinate(1,-1))));
        assertTrue(verticalIrrigation.getNeighbourIrrigations().contains(new Irrigation(new Coordinate(1,0), new Coordinate(1,-1))));
        assertTrue(verticalIrrigation.getNeighbourIrrigations().contains(new Irrigation(new Coordinate(0,0), new Coordinate(0,1))));
        assertTrue(verticalIrrigation.getNeighbourIrrigations().contains(new Irrigation(new Coordinate(1,0), new Coordinate(0,1))));

        //Test for fslash irrigations
        assertTrue(fSlashIrrigation.getNeighbourIrrigations().contains(new Irrigation(new Coordinate(1,0), new Coordinate(0,1))));
        assertTrue(fSlashIrrigation.getNeighbourIrrigations().contains(new Irrigation(new Coordinate(0,1), new Coordinate(1,1))));
        assertTrue(fSlashIrrigation.getNeighbourIrrigations().contains(new Irrigation(new Coordinate(1,0), new Coordinate(2,0))));
        assertTrue(fSlashIrrigation.getNeighbourIrrigations().contains(new Irrigation(new Coordinate(1,1), new Coordinate(2,0))));

        //Test for bslash irrigations
        assertTrue(bSlashIrrigation.getNeighbourIrrigations().contains(new Irrigation(new Coordinate(-1,0), new Coordinate(0,0))));
        assertTrue(bSlashIrrigation.getNeighbourIrrigations().contains(new Irrigation(new Coordinate(-1,0), new Coordinate(-1,1))));
        assertTrue(bSlashIrrigation.getNeighbourIrrigations().contains(new Irrigation(new Coordinate(0,0), new Coordinate(0,1))));
        assertTrue(bSlashIrrigation.getNeighbourIrrigations().contains(new Irrigation(new Coordinate(-1,1), new Coordinate(0,1))));

        Board board = new Board();
        board.addTile(x0y0);
        board.addTile(x1y0);
        board.addTile(x1y1);
        board.addTile(x0y1);
        board.addTile(xn1y1);

        Irrigation tmp = new Irrigation(x0y1.getCoordinate(), xn1y1.getCoordinate());
        Irrigation tmp2 = new Irrigation(x0y1, xn1y1);
        //board.addIrrigation(new Irrigation(xn1y1.getCoordinate(), x0y1.getCoordinate()));
        board.addIrrigation(new Irrigation(x0y1.getCoordinate(), xn1y1.getCoordinate()));


/*
        Irrigation test = new Irrigation(x0y1.getCoordinate(), xn1y1.getCoordinate());
        Irrigation test2 = new Irrigation(x0y1, xn1y1);*/
/*
        ArrayList<Irrigation> legalIrrigationPlacement = board.getLegalIrrigationPlacement();
        for (int i = 0; i < legalIrrigationPlacement.size(); i++) {
            System.out.println(legalIrrigationPlacement.get(i));

            if (board.getTile(legalIrrigationPlacement.get(i).getCoordinates().get(0)) != null) {
                if (board.getTile(legalIrrigationPlacement.get(i).getCoordinates().get(1)) != null) {
                    board.addIrrigation(legalIrrigationPlacement.get(i));
                    System.out.println("added irrigation " + legalIrrigationPlacement.get(i));
                    i--;
                }
            }

        }*/
    }

    @Test
    void testTilesAreIrrigated() {
        assertTrue(boardTiles.get(boardTiles.indexOf(x0y0)).isIrrigated());
        Tile x2y0 = new Tile(new Coordinate(2,0));
        boardTiles.add(x2y0);
        assertFalse(boardTiles.get(boardTiles.indexOf(x2y0)).isIrrigated());
        new Irrigation(boardTiles.get(boardTiles.indexOf(x2y0)), boardTiles.get(boardTiles.indexOf(x1y0)));
        assertTrue(boardTiles.get(boardTiles.indexOf(x2y0)).isIrrigated());
    }


    @Test
    void testExceptionThrowing() {
        //Irrigation shouldThrowAnException = new Irrigation(boardTiles.get(boardTiles.indexOf(xn1y1)), boardTiles.get(boardTiles.indexOf(x1y1)));
        //assertThrows(RuntimeException.class , (Executable) new Irrigation(boardTiles.get(boardTiles.indexOf(xn1y1)), boardTiles.get(boardTiles.indexOf(x1y1))));
        try {
            Irrigation youShouldNotExist = new Irrigation(boardTiles.get(boardTiles.indexOf(xn1y1)), boardTiles.get(boardTiles.indexOf(x1y1)));
        } catch (RuntimeException e) {
            assertTrue(true);   //asserts that it caught the exception;
            assertEquals("These tiles are not neighbours (" + xn1y1 + "\t\t" + x1y1 + ")",e.getMessage());
        }
    }

    @Test
    void testEquals() {
        assertTrue(verticalIrrigation.equals(new Irrigation(x0y0,x1y0)));
        assertTrue(verticalIrrigation.equals(new Irrigation(x1y0,x0y0)));
    }
}