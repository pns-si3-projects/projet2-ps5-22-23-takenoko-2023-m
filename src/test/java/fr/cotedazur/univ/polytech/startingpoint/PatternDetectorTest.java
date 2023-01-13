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
    void detectLINEPatternNearDiagonal() {
        board.addTile(new Tile(new Coordinate(1,-1),TypeOfTile.RED));
        assertEquals(0,board.patternDetector.getPatternBoardList().size());
        board.addTile(new Tile(new Coordinate(1,-2),TypeOfTile.RED));
        assertEquals(1,board.patternDetector.getPatternBoardList().size());
        board.addTile(new Tile(new Coordinate(1,-3),TypeOfTile.GREEN));
        assertEquals(1,board.patternDetector.getPatternBoardList().size());
    }
}
