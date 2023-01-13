package fr.cotedazur.univ.polytech.startingpoint;

import java.util.Arrays;
import java.util.List;

public class ObjectiveStackPlot extends Stack<ObjectivePlot> {

    public ObjectiveStackPlot() {
        super();
        this.generate();
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
    public void generate() {
        //List<TypeOfPattern> types = Arrays.asList(TypeOfPattern.LINE,TypeOfPattern.TRIANGLE,TypeOfPattern.BOOMRANG,TypeOfPattern.SQUARE);
        List<TypeOfPattern> types = Arrays.asList(TypeOfPattern.LINE);
        List<TypeOfTile> colors = Arrays.asList(TypeOfTile.YELLOW,TypeOfTile.RED,TypeOfTile.GREEN);
        for(TypeOfPattern type : types){
            for(TypeOfTile color : colors){
                if(type.equals(TypeOfPattern.SQUARE)){
                    this.putBelow(new ObjectivePlot(new Pattern(type,color,color)));
                }else{
                    this.putBelow(new ObjectivePlot(new Pattern(type,color)));
                }
            }
        }
        //this.putBelow(new ObjectivePlot(new Pattern(TypeOfPattern.SQUARE,TypeOfTile.YELLOW,TypeOfTile.RED)));
        //this.putBelow(new ObjectivePlot(new Pattern(TypeOfPattern.SQUARE,TypeOfTile.RED,TypeOfTile.GREEN)));
        //this.putBelow(new ObjectivePlot(new Pattern(TypeOfPattern.SQUARE,TypeOfTile.GREEN,TypeOfTile.YELLOW)));

    }
}
