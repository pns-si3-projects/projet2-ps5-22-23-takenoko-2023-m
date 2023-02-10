package fr.cotedazur.univ.polytech.startingpoint;

import java.util.List;

public class ObjectiveStackPanda extends Stack<ObjectivePanda> {
    /**
     * The constructor of the class
     * @param list The lust of objectives
     */
    public ObjectiveStackPanda(List<ObjectivePanda> list) {
        super(list);
    }

    /**
     * The constructor with no args
     */
    public ObjectiveStackPanda() {
        super();
    }

    /**
     * The method to put below a specific card
     * @param o The specific card
     */
    @Override
    public void putBelow(ObjectivePanda o) {
        super.putBelow(o);
    }

    /**
     * A method to pick a specific card
     * @param o The specific card you want to pick
     * @return The good card
     */
    @Override
    public ObjectivePanda pick(ObjectivePanda o) {
        return super.pick(o);
    }

    /**
     * A method to randomPick a card
     * @return A random card
     */
    @Override
    public ObjectivePanda randomPick() {
        return super.randomPick();
    }


    /**
     * toString method
     * @return a String
     */
    public String toString(){
        String s = "Pile d'objectifs panda : ";
        for (ObjectivePanda objective: list){
            s += objective.toString() + " ";
        }
        return s;

    }

    /**
     * A getter to get the stack
     * @return A list of the stack
     */
    @Override
    public List<ObjectivePanda> getStack() {
        return super.getStack();
    }

    /**
     * A method to generate all of the cards
     */
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

