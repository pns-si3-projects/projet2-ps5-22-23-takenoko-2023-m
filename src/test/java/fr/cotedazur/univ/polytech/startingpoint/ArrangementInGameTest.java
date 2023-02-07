package fr.cotedazur.univ.polytech.startingpoint;

import fr.cotedazur.univ.polytech.startingpoint.bots.Bot;
import fr.cotedazur.univ.polytech.startingpoint.bots.PrimaryBot;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ArrangementInGameTest {
    @Test
    void testGameWithArrangement() {
        Board board = new Board();
        Bot bot1 = new PrimaryBot(board,"crashtest");
        Tile tile1 = new Tile(new Coordinate(0,0),TypeOfTile.GREEN,TypeOfArrangement.BASIN);
        Tile tile2 = new Tile(new Coordinate(0,1),TypeOfTile.RED,TypeOfArrangement.NONE);

        bot1.addTile(tile1);
        bot1.addTile(tile2);

        bot1.pickArrangement(TypeOfArrangement.ENCLOSURE);

        bot1.setArrangement(tile1,TypeOfArrangement.ENCLOSURE);
        assertTrue(tile1.getTypeOfArrangement()==TypeOfArrangement.BASIN);

        bot1.pickArrangement(TypeOfArrangement.FERTILIZER);

        bot1.setArrangement(tile2,TypeOfArrangement.FERTILIZER);
        assertTrue(tile2.getTypeOfArrangement()==TypeOfArrangement.FERTILIZER);





    }

}
