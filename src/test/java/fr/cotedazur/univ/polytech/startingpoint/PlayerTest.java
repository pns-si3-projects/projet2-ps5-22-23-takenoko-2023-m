package fr.cotedazur.univ.polytech.startingpoint;

import fr.cotedazur.univ.polytech.startingpoint.bots.Bot;
import fr.cotedazur.univ.polytech.startingpoint.bots.IntermediateBot;
import fr.cotedazur.univ.polytech.startingpoint.bots.PrimaryBot;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import java.util.List;
import java.util.logging.Level;

import java.util.Arrays;


import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {


    @Test
    void getPoint() {
        Board board = new Board();
        Bot bot1 = new PrimaryBot(board,"bot1");
        assertEquals(0,bot1.getPoint());
    }

    @Test
    void setPoint() {
        Board board = new Board();
        Bot bot1 = new PrimaryBot(board,"bot1");
        bot1.setPoint(15);
        assertEquals(15,bot1.getPoint());
    }

    @Test
    void getNom() {
        Board board = new Board();
        Bot bot1 = new PrimaryBot(board,"bot1");
        assertEquals("bot1",bot1.getNom());
    }

    @Test
    void setNom() {
        Board board = new Board();
        Bot bot1 = new PrimaryBot(board,"bot1");
        bot1.setNom("botte1");
        assertEquals("botte1",bot1.getNom());
    }

    @Test
    void getNbActions() {
        Board board = new Board();
        Bot bot1 = new PrimaryBot(board,"bot1");
        bot1.resetNbActions();
        assertEquals(2,bot1.getNbActions());
    }

    @Test
    void playAction() {
        Board board = new Board();
        Bot bot1 = new PrimaryBot(board,"bot1");
        bot1.resetNbActions();
        bot1.playAction();
        assertEquals(1,bot1.getNbActions());
    }

    @Test
    void resetNbActions() {
        Board board = new Board();
        Bot bot1 = new PrimaryBot(board,"bot1");
        bot1.playAction();
        bot1.resetNbActions();
        assertEquals(2,bot1.getNbActions());
    }

    @Test
    void resetNbBamboo() {
        Board board = new Board();
        Bot bot1 = new PrimaryBot(board,"bot1");
        bot1.upNbBamboo(TypeOfTile.GREEN); bot1.upNbBamboo(TypeOfTile.GREEN);
        bot1.resetNbBamboo(TypeOfTile.GREEN);
        assertEquals(0,bot1.getNbBamboo(TypeOfTile.GREEN));
    }

    @Test
    void getNbBamboo() {
        Board board = new Board();
        Bot bot1 = new PrimaryBot(board,"bot1");
        assertEquals(0,bot1.getNbBamboo(TypeOfTile.GREEN));
    }

    @Test
    void upNbBamboo() {
        Board board = new Board();
        Bot bot1 = new PrimaryBot(board,"bot1");
        bot1.upNbBamboo(TypeOfTile.GREEN);
        assertEquals(1,bot1.getNbBamboo(TypeOfTile.GREEN));
    }

    @Test
    void testChooseBetterOf3Tiles(){ //NEED TO DO A MALOCK RIGHT HERE IN THE FUTURE
        //TODO : Simon doit faire le mockito
        Board board = new Board();
        PrimaryBot bot1 = new PrimaryBot(board,"bot1");
        bot1.setFocusCard(new ObjectivePanda("panda",1,80,TypeOfTile.GREEN));
        List<Tile> tiles = (ArrayList<Tile>) board.pickThreeTiles();
        assertEquals(24, board.getTileStack().getStack().size());
        List<Tile> dummyTiles = new ArrayList<>(Arrays.asList(
                new Tile(null, TypeOfTile.GREEN),
                new Tile(null, TypeOfTile.RED),
                new Tile(null, TypeOfTile.YELLOW)
        ));
        Tile tile = bot1.chooseBetterOf3Tiles(dummyTiles);

        assertEquals(TypeOfTile.GREEN, tile.getTypeOfTile());
        assertEquals(26, board.getTileStack().getStack().size());
    }

    @Test
    void testSortObjective(){
        Board board = new Board();
        Main.LOGGER.setLevel(Level.SEVERE);
        IntermediateBot ib = new IntermediateBot(board,"Simon");
        ib.getObjective().sort((o1, o2) -> o2.getNbPointsWin() - o1.getNbPointsWin());
        assertTrue(ib.getObjective().get(0).getNbPointsWin() > ib.getObjective().get(2).getNbPointsWin());
        Main.LOGGER.severe(Integer.toString(ib.getObjective().get(0).getNbPointsWin()));
        Main.LOGGER.severe(Integer.toString(ib.getObjective().get(2).getNbPointsWin()));
    }
}
