package fr.cotedazur.univ.polytech.startingpoint;

import java.util.List;

public class ObjectiveStackPanda extends Stack<ObjectivePanda> {
    public ObjectiveStackPanda(List<ObjectivePanda> list) {
        super(list);
    }

    public ObjectiveStackPanda() {
        super();
    }

    @Override
    public void putBelow(ObjectivePanda o) {
        super.putBelow(o);
    }

    @Override
    public ObjectivePanda pick(ObjectivePanda o) {
        return super.pick(o);
    }

    @Override
    public ObjectivePanda randomPick() {
        return super.randomPick();
    }



    public String toString(){
        String s = "Pile d'objectifs panda : ";
        for (ObjectivePanda objective: list){
            s += objective.toString() + " ";
        }
        return s;

    }

    @Override
    public List<ObjectivePanda> getStack() {
        return super.getStack();
    }

    @Override
    public void generate() {
        for(int i=0; i<5;i++){
            this.putBelow(new ObjectivePanda("panda", 2,3,TypeOfTile.GREEN));
        }
        for(int i=0; i<4;i++){
            this.putBelow(new ObjectivePanda("panda", 2,4,TypeOfTile.YELLOW));
        }
        for(int i=0; i<3;i++){
            this.putBelow(new ObjectivePanda("panda", 2,5,TypeOfTile.RED));
        }
        for(int i=0; i<3;i++){
            this.putBelow(new ObjectivePanda("panda", 3,6,TypeOfTile.GREEN));
        }
    }


}

