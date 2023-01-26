package fr.cotedazur.univ.polytech.startingpoint;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PandaTest {
    private static Board board;
    private static Player player;

    @BeforeAll
    static void beforeAll(){
        board = new Board();
        player = new Player(board,"Joueur1");
        Tile start = new Tile(new Coordinate(0,0));
        board.addTile(start);
        Tile tile1 = new Tile(new Coordinate(1,0));
        tile1.grow();
        board.addTile(tile1);
        Tile tile2 = new Tile(new Coordinate(2,0));
        board.addTile(tile2);
    }

    @Test
    void movePandaOnCaseWithBamboo(){
        assertEquals(0,player.getNbBamboo(TypeOfTile.GREEN));
        this.board.movePandaOn(new Coordinate(1,0),player);
        assertEquals(1,player.getNbBamboo(TypeOfTile.GREEN));
        assertEquals(0,board.getTile(new Coordinate(1,0)).getBamboo());
    }

    @Test
    void movePandaOnCaseWithoutBamboo(){
        this.board.movePandaOn(new Coordinate(2,0),player);
        assertEquals(0,player.getNbBamboo(TypeOfTile.GREEN));
    }
}