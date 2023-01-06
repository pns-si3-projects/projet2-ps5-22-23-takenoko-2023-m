package fr.cotedazur.univ.polytech.startingpoint;

import java.util.List;

public class ObjectiveStackPlot extends Stack<ObjectivePlot> {
    public ObjectiveStackPlot(List<ObjectivePlot> list) {
        super(list);
    }

    public ObjectiveStackPlot() {
        super();
    }

    @Override
    public void putBelow(ObjectivePlot o) {
        super.putBelow(o);
    }

    @Override
    public ObjectivePlot pick(ObjectivePlot o) {
        return super.pick(o);
    }

    @Override
    public ObjectivePlot randomPick() {
        return super.randomPick();
    }



    @Override
    public String toString() {
        String s = "Pile d'objectifs plot : ";
        for (ObjectivePlot objective: list){
            s += objective.toString() + " ";
        }
        return s;
    }


    @Override
    public List<ObjectivePlot> getStack() {
        return super.getStack();
    }

    @Override
    public void generate() {
        //TODO
    }
}
