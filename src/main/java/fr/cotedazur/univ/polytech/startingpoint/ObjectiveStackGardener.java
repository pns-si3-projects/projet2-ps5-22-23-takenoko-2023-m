package fr.cotedazur.univ.polytech.startingpoint;

import java.util.List;
import java.util.logging.*;
public class ObjectiveStackGardener extends Stack<ObjectiveGardener> {

    /**
     * A method to generate the stack
     */
    @Override
    public void generate() {
        //TODO Ajouter des arrangements différents
        for(int i=0; i<3;i++){
            this.putBelow(new ObjectiveGardener("gardener", 4,4, TypeOfTile.GREEN, TypeOfArrangement.NONE));
        }
        for(int i=0; i<4;i++){
            this.putBelow(new ObjectiveGardener("gardener", 4,5,TypeOfTile.YELLOW, TypeOfArrangement.NONE));
        }
        for(int i=0; i<3;i++){
            this.putBelow(new ObjectiveGardener("gardener", 4,6,TypeOfTile.RED, TypeOfArrangement.NONE));
        }
        this.putBelow(new ObjectiveGardener("gardener", 4,3,TypeOfTile.GREEN, TypeOfArrangement.NONE));
        this.putBelow(new ObjectiveGardener("gardener", 4,7,TypeOfTile.RED, TypeOfArrangement.NONE));

        this.putBelow(new ObjectiveGardener("gardener", 3,6,TypeOfTile.YELLOW, TypeOfArrangement.NONE));
        this.putBelow(new ObjectiveGardener("gardener", 3,7,TypeOfTile.GREEN, TypeOfArrangement.NONE));
        this.putBelow(new ObjectiveGardener("gardener", 3,8,TypeOfTile.RED, TypeOfArrangement.NONE));

    }

    /**
     * The constructor of the clas
     * @param list The list of the stack
     */
    public ObjectiveStackGardener(List<ObjectiveGardener> list){
        super(list);

    }

    /**
     * The constructor which call the super constructor with no args
     */
    public ObjectiveStackGardener() {
        super();
    }


    /**
     * A method to put bellow a card
     * @param o The card
     */
    public void putBelow(ObjectiveGardener o){
        this.list.add( o);
    }


    /**
     * A method to picka specific card
     * @param o The specific card
     * @return The card
     */
    public ObjectiveGardener pick(ObjectiveGardener o){
        for (ObjectiveGardener objective: list){
            if (objective.equals(o)){
                list.remove(objective);
                return o;
            }
        }
        Main.LOGGER.info("L'objectif voulu n'est pas dans la pile");
        return null;
    }

    /**
     * A method to pick a random card
     * @return A random card
     */
    public ObjectiveGardener randomPick(){
        int i = (int) (Math.random() * list.size());
        ObjectiveGardener o = list.get(i);
        list.remove(o);
        return o;
    }

    /**
     * A getter to the stack
     * @return The stack
     */
    public  List<ObjectiveGardener> getStack(){
        return list;
    }


    /**
     * toString method
     * @return A string
     */
    public String toString(){
        String s = "Pile d'objectifs jardinier : ";
        for (ObjectiveGardener objective: list){
            s += objective.toString() + " ";
        }
        return s;

    }


}
