package fr.cotedazur.univ.polytech.startingpoint.bots;

import fr.cotedazur.univ.polytech.startingpoint.Board;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IntermediateBotTest {

    @Test
    void testCreator(){
        Board board = new Board();
        IntermediateBot ib = new IntermediateBot(board, "Simon");
        assertTrue(ib.getObjective().size()==3);
    }

}