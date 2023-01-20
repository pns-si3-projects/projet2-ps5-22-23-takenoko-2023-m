package fr.cotedazur.univ.polytech.startingpoint;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ArrangementInGameTest {
    @Test
    void testGameWithArrangement() {
        Board board = new Board();
        Player bot1 = new Player(board,"crashtest");
        Tile tile1 = new Tile(new Coordinate(0,0),TypeOfTile.GREEN,TypeOfArrangement.BASIN);
        Tile tile2 = new Tile(new Coordinate(0,1),TypeOfTile.RED,TypeOfArrangement.NONE);

        bot1.addTile(tile1);
        bot1.addTile(tile2);

        bot1.setArrangment(tile1,TypeOfArrangement.ENCLOSURE);
        assertTrue(tile1.getTypeOfArrangement()==TypeOfArrangement.BASIN);

        bot1.setArrangment(tile2,TypeOfArrangement.FERTILIZER);
        System.out.println(tile2.getTypeOfArrangement());
        assertTrue(tile2.getTypeOfArrangement()==TypeOfArrangement.FERTILIZER);





    }

}
