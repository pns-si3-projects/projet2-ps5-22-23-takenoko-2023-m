package fr.cotedazur.univ.polytech.startingpoint.bots;

import fr.cotedazur.univ.polytech.startingpoint.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SkynetBotTest {
    Board board1 = new Board();
    SkynetBot bot1 = new SkynetBot(board1, "Skynet");


    @Test
    void getListOfPandaObjectiveTest() {
        assertEquals(1, bot1.getListOfPandaObjective().size());
        bot1.objectives.add(new ObjectivePanda("panda",2,2, TypeOfTile.GREEN));
        assertEquals(2, bot1.getListOfPandaObjective().size());
    }

    @Test
    void skynetAlwaysHasFiveObjective(){
        bot1.play();
        bot1.play();
        bot1.play();
        bot1.play();
        assertTrue(bot1.objectives.size() >= 5);
        bot1.play();
        bot1.play();
        bot1.play();
        bot1.play();
        assertTrue(bot1.objectives.size() >= 5);
    }
    @Test
    void skynetFocusBambooEvenIfDontHaveObjectivePanda(){
        board1.addTile(new Tile(new Coordinate(0,1), TypeOfTile.GREEN, TypeOfArrangement.NONE));
        board1.getTile(new Coordinate(0,1)).setBamboo(4);
        bot1.objectives.removeAll(bot1.objectives);
        bot1.objectives.add(new ObjectivePlot(new Pattern(TypeOfPattern.TRIANGLE, TypeOfTile.RED)));
        bot1.objectives.add(new ObjectivePlot(new Pattern(TypeOfPattern.TRIANGLE, TypeOfTile.YELLOW)));
        bot1.objectives.add(new ObjectivePlot(new Pattern(TypeOfPattern.LINE, TypeOfTile.RED)));
        bot1.objectives.add(new ObjectivePlot(new Pattern(TypeOfPattern.TRIANGLE, TypeOfTile.YELLOW)));
        bot1.objectives.add(new ObjectivePlot(new Pattern(TypeOfPattern.BOOMRANG, TypeOfTile.RED)));

        bot1.play();
        Main.LOGGER.info(board1.getBoardTiles().toString());
        assertEquals(1, bot1.getNbBamboo(TypeOfTile.GREEN));
    }

}
