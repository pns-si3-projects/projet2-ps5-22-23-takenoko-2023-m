package fr.cotedazur.univ.polytech.startingpoint.bots;

import fr.cotedazur.univ.polytech.startingpoint.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.logging.Level;

import static org.junit.jupiter.api.Assertions.*;

class IntermediateBotTest {
    Board board;
    IntermediateBot bot1;

    @BeforeEach
    void setup() {
        Main.LOGGER.setLevel(Level.SEVERE);
        board = new Board();
        bot1 = new IntermediateBot(board, "bot1");
        bot1.objectives.clear();
        Main.LOGGER.setLevel(Level.INFO);
    }

    @Test
    void testCreator(){
        IntermediateBot ib = new IntermediateBot(board, "Simon");
        assertEquals(3, ib.getObjective().size());
    }

    @Test
    void testChooseBetterObjective() {
        bot1.objectives.add(new ObjectivePanda("panda", 2, 3, TypeOfTile.GREEN));
        bot1.objectives.add(new ObjectiveGardener("gardener", 4, 6, TypeOfTile.RED, TypeOfArrangement.NONE));
        bot1.objectives.add(new ObjectivePlot(new Pattern(TypeOfPattern.LINE, TypeOfTile.GREEN)));
        //when the bot plays, it sorts the objectives in the best order for it (sorted by number of points given)
        board.addTile(new Tile(new Coordinate(-1, 0), TypeOfTile.RED, TypeOfArrangement.NONE));
        board.addTile(new Tile(new Coordinate(-1, 1), TypeOfTile.RED, TypeOfArrangement.NONE));
        assertTrue(board.getBoardTiles().get(0).isIrrigated());
        bot1.play();
        System.out.println(bot1.objectives.toString());
    }

}