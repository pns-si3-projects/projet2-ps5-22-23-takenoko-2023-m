package fr.cotedazur.univ.polytech.startingpoint;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;


import static org.junit.jupiter.api.Assertions.*;

public class MeteoTest {


    @Test
    void testDice() {
        //a faire avec un mock
        Board b = new Board();
        assertEquals(b.getDice().getMeteo(), Meteo.NONE);
        b.getDice().randomMeteo();
        assertNotEquals(b.getDice().getMeteo(), Meteo.NONE);



    }

}

