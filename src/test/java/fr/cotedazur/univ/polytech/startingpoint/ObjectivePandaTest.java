package fr.cotedazur.univ.polytech.startingpoint;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ObjectivePandaTest {


    @Test
    void getType() {
        ObjectivePanda objectivePanda = new ObjectivePanda("panda",2, 10);
        assertEquals("panda", objectivePanda.getType());
    }

    @Test
    void setType() {
        ObjectivePanda objectivePanda = new ObjectivePanda(null,2, 10);
        objectivePanda.setType("panda");
        assertEquals("panda", objectivePanda.getType());

    }

    @Test
    void getNbToEat() {
        ObjectivePanda objectivePanda = new ObjectivePanda("panda",2, 10);
        assertEquals(2, objectivePanda.getNbToEat());

    }

    @Test
    void testToString() {
        ObjectivePanda objectivePanda = new ObjectivePanda("panda",2, 10);
        assertEquals("Objectif de type panda et de nombre de bambou a manger 2", objectivePanda.toString());
    }
}