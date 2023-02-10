package fr.cotedazur.univ.polytech.startingpoint.bots;

import fr.cotedazur.univ.polytech.startingpoint.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
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

    @Test
    void testChooseBetterOf3Tiles(){
        bot1.objectives.add(new ObjectiveGardener("gardener",1,85,TypeOfTile.GREEN,TypeOfArrangement.NONE));
        Tile t1 = new Tile(null,TypeOfTile.GREEN,TypeOfArrangement.NONE);
        Tile t2 = new Tile(null, TypeOfTile.RED, TypeOfArrangement.NONE);
        List<Tile> listToTest = new ArrayList<>();
        listToTest.add(t1);
        listToTest.add(t2);
        listToTest.add(t2);
        assertEquals(t1,bot1.chooseBetterOf3Tiles(listToTest));
    }

    @Test
    void testFinObjectif(){
        ObjectiveGardener og = new ObjectiveGardener("gardener",3,3,TypeOfTile.GREEN,TypeOfArrangement.NONE);
        ObjectivePanda op = new ObjectivePanda("panda",2,2,TypeOfTile.GREEN);
        ObjectivePlot opt = new ObjectivePlot(new Pattern(TypeOfPattern.LINE,TypeOfTile.GREEN));
        bot1.objectives.add(og); bot1.objectives.add(op); bot1.objectives.add(opt);
        assertEquals(og,bot1.findObjectiveGardener());
        assertEquals(op,bot1.findObjectivePanda());
        assertEquals(opt,bot1.findObjectivePlot());
    }

    @Test
    void testPlayForIrrigation(){
        board.addTile(new Tile(new Coordinate(0,1),TypeOfTile.RED,TypeOfArrangement.NONE));
        board.addTile(new Tile(new Coordinate(1,0),TypeOfTile.RED,TypeOfArrangement.NONE));
        int size = board.getPlacedIrrigations().size();
        bot1.playForIrrigation();
        int size2 = board.getPlacedIrrigations().size();
        assertTrue(size2 > size);
    }

    @Test
    void testGetNbActions(){
        ObjectivePanda op = new ObjectivePanda("panda",2,2,TypeOfTile.GREEN);
        bot1.objectives.add(op);
        bot1.objectives.get(0).play(bot1);
        assertTrue(bot1.getNbActions()<=0);
    }

    @Test
    void testPlayForPandaCardMovementPanda(){
        ObjectivePanda op = new ObjectivePanda("panda",2,2,TypeOfTile.GREEN);
        bot1.objectives.add(op);
        Tile tile = new Tile(new Coordinate(0,1),TypeOfTile.GREEN,TypeOfArrangement.NONE);
        tile.irrigate();
        tile.setBamboo(3);
        board.addTile(tile);
        bot1.resetNbActions();
        bot1.objectives.get(0).play(bot1);
        System.out.println(board.getBoardTiles());
        assertEquals(board.getPanda().getTile(),tile);
    }

    @Test
    void testPlayForGardenerCardMovementGardener(){
        ObjectiveGardener og = new ObjectiveGardener("gardener",3,3,TypeOfTile.GREEN,TypeOfArrangement.NONE);
        bot1.objectives.add(og); bot1.resetNbActions();
        Tile tile = new Tile(new Coordinate(0,1),TypeOfTile.GREEN,TypeOfArrangement.NONE);
        tile.irrigate();
        board.addTile(tile);
        bot1.objectives.get(0).play(bot1);
        assertEquals(board.getGardener().getTile(),tile);
    }

    @Test
    void testPlayForGardenerCardAvailablePosition(){
        ObjectiveGardener og = new ObjectiveGardener("gardener",3,3,TypeOfTile.GREEN,TypeOfArrangement.NONE);
        bot1.objectives.add(og); bot1.resetNbActions();

        Tile tile = new Tile(new Coordinate(0,1),TypeOfTile.RED,TypeOfArrangement.NONE);
        Tile tile2 = new Tile(new Coordinate(0,2),TypeOfTile.GREEN,TypeOfArrangement.NONE);
        Tile tile3 = new Tile(new Coordinate(1,1),TypeOfTile.GREEN,TypeOfArrangement.NONE);
        board.addTile(tile); board.addTile(tile2); board.addTile(tile3);

        System.out.println(board.getGardener().getTile().scanAvailableCoordinatesToMove(board.getBoardTiles()));

        assertEquals(board.getGardener().getTile().scanAvailableCoordinatesToMove(board.getBoardTiles()).size(),2);
        assertFalse(board.getGardener().getTile().scanAvailableCoordinatesToMove(board.getBoardTiles()).contains(tile3));
    }



}