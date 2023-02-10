package fr.cotedazur.univ.polytech.startingpoint;

import fr.cotedazur.univ.polytech.startingpoint.bots.IntermediateBot;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TileStackTest {

    @Test
    void pickThreeTiles() {
        TileStack tileStack = new TileStack();
        assertEquals(tileStack.getStack().size(),27);
        tileStack.addTile(new Tile(null, TypeOfTile.RED, TypeOfArrangement.NONE));
        assertEquals(tileStack.getStack().size(),28);
        List<Tile> liste = tileStack.pickThreeTiles();
        assertEquals(tileStack.getStack().size(),25);
    }

    @Test
    void pickBetterOf3Tiles(){
        Board board = new Board();
        IntermediateBot p = new IntermediateBot(board,"Simon");
        List<Tile> listeTile = board.pickThreeTiles();
        assertEquals(24,board.getTileStack().sizeTileStack());
        Tile t = p.chooseBetterOf3Tiles(listeTile);
        assertEquals(26, board.getTileStack().sizeTileStack());
    }

    @Test
    void pick3Tiles2(){
        Board board = new Board();
        List<Tile> listeTile = board.pickThreeTiles();
        assertEquals(3, listeTile.size());
        listeTile = board.pickThreeTiles();
        assertEquals(3,listeTile.size());
        listeTile = board.pickThreeTiles();
        assertEquals(3,listeTile.size());
        listeTile = board.pickThreeTiles();
        assertEquals(3,listeTile.size());
    }
}
