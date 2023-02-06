package fr.cotedazur.univ.polytech.startingpoint;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PatternDetectorTest {
    Board board;
    @BeforeEach
    void setUp() {
        board = new Board();
        board.addTile(new Tile(new Coordinate(1,0),TypeOfTile.RED));
    }

    @Test
    void detectLINEPatternNearHorizontal() {
        board.addTile(new Tile(new Coordinate(2,0),TypeOfTile.RED));
        assertEquals(0,board.patternDetector.getPatternBoardList().size());
        board.addTile(new Tile(new Coordinate(3,0),TypeOfTile.RED));
        assertEquals(1,board.patternDetector.getPatternBoardList().size());
        board.addTile(new Tile(new Coordinate(4,0),TypeOfTile.GREEN));
        assertEquals(1,board.patternDetector.getPatternBoardList().size());
    }
    @Test
    void detectLINEPatternNearCenter() {
        board.addTile(new Tile(new Coordinate(3,0),TypeOfTile.RED));
        assertEquals(0,board.patternDetector.getPatternBoardList().size());
        board.addTile(new Tile(new Coordinate(2,0),TypeOfTile.RED));
        System.out.println(board.patternDetector.getPatternBoardList());
        assertEquals(1,board.patternDetector.getPatternBoardList().size());
        assertEquals(TypeOfPattern.LINE,board.patternDetector.getPatternBoardList().get(0).type);
    }
    @Test
    void detectLINEPatternNearDiagonal() {
        board.addTile(new Tile(new Coordinate(1,-1),TypeOfTile.RED));
        assertEquals(0,board.patternDetector.getPatternBoardList().size());
        board.addTile(new Tile(new Coordinate(1,-2),TypeOfTile.RED));
        assertEquals(1,board.patternDetector.getPatternBoardList().size());
        board.addTile(new Tile(new Coordinate(1,-3),TypeOfTile.GREEN));
        assertEquals(1,board.patternDetector.getPatternBoardList().size());
    }
    @Test
    void detectTRIANGLEPatternNear() {
        board.addTile(new Tile(new Coordinate(2,-1),TypeOfTile.RED));
        assertEquals(0,board.patternDetector.getPatternBoardList().size());
        board.addTile(new Tile(new Coordinate(2,-0),TypeOfTile.RED));
        System.out.println(board.patternDetector.getPatternBoardList());
        assertEquals(TypeOfPattern.TRIANGLE,board.patternDetector.getPatternBoardList().get(0).type);
        board.addTile(new Tile(new Coordinate(1,-1),TypeOfTile.GREEN));
        assertEquals(1,board.patternDetector.getPatternBoardList().size());
    }

    @Test
    void detectBOOMRANGPatternNear() {
        board.addTile(new Tile(new Coordinate(0,1),TypeOfTile.RED));
        assertEquals(0,board.patternDetector.getPatternBoardList().size());
        board.addTile(new Tile(new Coordinate(0,2),TypeOfTile.RED));
        assertEquals(TypeOfPattern.BOOMRANG,board.patternDetector.getPatternBoardList().get(0).type);
        board.addTile(new Tile(new Coordinate(-1,1),TypeOfTile.GREEN));
        assertEquals(1,board.patternDetector.getPatternBoardList().size());
    }
    @Test
    void detectBOOMRANGPatternNearCenter() {
        board.addTile(new Tile(new Coordinate(0,2),TypeOfTile.RED));
        assertEquals(0,board.patternDetector.getPatternBoardList().size());
        board.addTile(new Tile(new Coordinate(0,1),TypeOfTile.RED));
        assertEquals(TypeOfPattern.BOOMRANG,board.patternDetector.getPatternBoardList().get(0).type);
    }
    @Test
    void bestCoordinateForTriangle() {
        board.addTile(new Tile(new Coordinate(1,-1),TypeOfTile.GREEN));
        board.addTile(new Tile(new Coordinate(2,-1),TypeOfTile.GREEN));
        board.addTile(new Tile(new Coordinate(0,1),TypeOfTile.GREEN));
        Coordinate coordinate = board.patternDetector.bestCoordinateForTriangle(new ObjectivePlot(new Pattern(TypeOfPattern.TRIANGLE,TypeOfTile.RED)));
        assertTrue(new Coordinate(2,0).equals(coordinate)||new Coordinate(1,1).equals(coordinate));
        board.addTile(new Tile(new Coordinate(-1,0),TypeOfTile.RED));
        board.addTile(new Tile(new Coordinate(-2,0),TypeOfTile.RED));
        assertEquals(new Coordinate(-2,1),board.patternDetector.bestCoordinateForTriangle(new ObjectivePlot(new Pattern(TypeOfPattern.TRIANGLE,TypeOfTile.RED))));
    }
    @Test
    void bestCoordinateForLine() {
        board.addTile(new Tile(new Coordinate(1,-1),TypeOfTile.GREEN));
        board.addTile(new Tile(new Coordinate(2,-1),TypeOfTile.GREEN));
        board.addTile(new Tile(new Coordinate(0,1),TypeOfTile.GREEN));
        board.addTile(new Tile(new Coordinate(1,1),TypeOfTile.GREEN));
        Coordinate coordinate = board.patternDetector.bestCoordinateForLine(new ObjectivePlot(new Pattern(TypeOfPattern.LINE,TypeOfTile.RED)));
        assertEquals(new Coordinate(2,0),coordinate);
        board.addTile(new Tile(new Coordinate(-1,0),TypeOfTile.RED));
        board.addTile(new Tile(new Coordinate(-2,0),TypeOfTile.RED));
        assertEquals(new Coordinate(-3,0),board.patternDetector.bestCoordinateForLine(new ObjectivePlot(new Pattern(TypeOfPattern.LINE,TypeOfTile.RED))));
    }
    @Test
    void bestCoordinateForBoomrang() {
        board.addTile(new Tile(new Coordinate(1,-1),TypeOfTile.GREEN));
        board.addTile(new Tile(new Coordinate(2,-1),TypeOfTile.GREEN));
        board.addTile(new Tile(new Coordinate(1,1),TypeOfTile.GREEN));
        Coordinate coordinate = board.patternDetector.bestCoordinateForBoomrang(new ObjectivePlot(new Pattern(TypeOfPattern.BOOMRANG,TypeOfTile.RED)));
        assertTrue(new Coordinate(2,0).equals(coordinate)||new Coordinate(0,1).equals(coordinate));
        board.addTile(new Tile(new Coordinate(-1,0),TypeOfTile.RED));
        board.addTile(new Tile(new Coordinate(-2,0),TypeOfTile.RED));
        board.addTile(new Tile(new Coordinate(-2,-1),TypeOfTile.GREEN));
        board.addTile(new Tile(new Coordinate(-2,1),TypeOfTile.GREEN));
        board.addTile(new Tile(new Coordinate(0,-1),TypeOfTile.GREEN));
        assertEquals(new Coordinate(-1,1),board.patternDetector.bestCoordinateForBoomrang(new ObjectivePlot(new Pattern(TypeOfPattern.BOOMRANG,TypeOfTile.RED))));
    }
}
