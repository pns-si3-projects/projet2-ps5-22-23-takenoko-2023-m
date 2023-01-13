package fr.cotedazur.univ.polytech.startingpoint;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {


    @Test
    void getPoint() {
        Board board = new Board();
        Player bot1 = new Player(board,"bot1");
        assertEquals(0,bot1.getPoint());
    }

    @Test
    void setPoint() {
        Board board = new Board();
        Player bot1 = new Player(board,"bot1");
        bot1.setPoint(15);
        assertEquals(15,bot1.getPoint());
    }

    @Test
    void getNom() {
        Board board = new Board();
        Player bot1 = new Player(board,"bot1");
        assertEquals("bot1",bot1.getNom());
    }

    @Test
    void setNom() {
        Board board = new Board();
        Player bot1 = new Player(board,"bot1");
        bot1.setNom("botte1");
        assertEquals("botte1",bot1.getNom());
    }

    @Test
    void getNbActions() {
        Board board = new Board();
        Player bot1 = new Player(board,"bot1");
        bot1.resetNbActions();
        assertEquals(2,bot1.getNbActions());
    }

    @Test
    void playAction() {
        Board board = new Board();
        Player bot1 = new Player(board,"bot1");
        bot1.resetNbActions();
        bot1.playAction();
        assertEquals(1,bot1.getNbActions());
    }

    @Test
    void resetNbActions() {
        Board board = new Board();
        Player bot1 = new Player(board,"bot1");
        bot1.playAction();
        bot1.resetNbActions();
        assertEquals(2,bot1.getNbActions());
    }

    @Test
    void resetNbBamboo() {
        Board board = new Board();
        Player bot1 = new Player(board,"bot1");
        bot1.upNbBamboo(); bot1.upNbBamboo();
        bot1.resetNbBamboo();
        assertEquals(0,bot1.getNbBamboo());
    }

    @Test
    void getNbBamboo() {
        Board board = new Board();
        Player bot1 = new Player(board,"bot1");
        assertEquals(0,bot1.getNbBamboo());
    }

    @Test
    void upNbBamboo() {
        Board board = new Board();
        Player bot1 = new Player(board,"bot1");
        bot1.upNbBamboo();
        assertEquals(1,bot1.getNbBamboo());
    }
}