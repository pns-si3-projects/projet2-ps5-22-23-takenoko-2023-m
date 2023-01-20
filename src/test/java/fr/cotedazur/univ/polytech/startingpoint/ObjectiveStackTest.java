package fr.cotedazur.univ.polytech.startingpoint;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ObjectiveStackTest {

    @Test
    void putBelow() {

        ObjectiveGardener objectiveGardener = new ObjectiveGardener("gardener",2,2);
        ObjectiveGardener objectiveGardener2 = new ObjectiveGardener("gardener",3,3);
        List<ObjectiveGardener> list = new ArrayList<ObjectiveGardener>();
        ObjectiveStackGardener objectiveStack = new ObjectiveStackGardener(list);
        ObjectiveGardener objectiveGardener3 = new ObjectiveGardener("gardener",4,4);
        objectiveStack.putBelow(objectiveGardener3);
        assertTrue(objectiveStack.getStack().contains(objectiveGardener3));

    }

    @Test
    void pick() {
        ObjectiveGardener objectiveGardener = new ObjectiveGardener("gardener",2,2);
        ObjectiveGardener objectiveGardener2 = new ObjectiveGardener("gardener",3,3);
        List<ObjectiveGardener> list = new ArrayList<ObjectiveGardener>();
        list.add(objectiveGardener2);
        ObjectiveStackGardener objectiveStack = new ObjectiveStackGardener(list);
        ObjectiveGardener objectiveGardener3 = new ObjectiveGardener("gardener",4, 4);
        objectiveStack.putBelow(objectiveGardener3);
        objectiveStack.pick(objectiveGardener2);
        assertFalse(objectiveStack.getStack().contains(objectiveGardener2));
    }

    @Test
    void randomPick() {
        ObjectivePanda objectivePanda = new ObjectivePanda("panda",2, 2,TypeOfTile.GREEN);
        ObjectivePanda objectivePanda2 = new ObjectivePanda("panda",3, 3,TypeOfTile.GREEN);
        List<ObjectivePanda> list = new ArrayList<>();
        list.add(objectivePanda);
        list.add(objectivePanda2);
        ObjectiveStackPanda objectiveStack = new ObjectiveStackPanda(list);
        ObjectivePanda objectivePanda3 = new ObjectivePanda("panda",4, 4,TypeOfTile.GREEN);
        objectiveStack.putBelow(objectivePanda3);
        objectiveStack.randomPick();
        assertEquals(2, objectiveStack.getStack().size());


    }

}
