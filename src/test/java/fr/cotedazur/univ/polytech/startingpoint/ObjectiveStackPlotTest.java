package fr.cotedazur.univ.polytech.startingpoint;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ObjectiveStackPlotTest {

    @Test
    void generateAll15ObjectifPlot() {
        ObjectiveStackPlot objectiveStackPlot = new ObjectiveStackPlot();
        for(ObjectivePlot objectivePlot : objectiveStackPlot.getStack()){
            System.out.println(objectivePlot);
        }
        assertEquals(3, objectiveStackPlot.getStack().size());
    }
}
