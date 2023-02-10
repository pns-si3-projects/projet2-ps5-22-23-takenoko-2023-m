package fr.cotedazur.univ.polytech.startingpoint;

import java.util.ArrayList;
import java.util.List;

public class ArrangementStack extends Stack<TypeOfArrangement>{
    /**The type of the arrangement stack*/
    private TypeOfArrangement type;

    /**
     * The constructor of the class
     * @param list The list of the arrangement
     * @param type The type of the arrangements
     */
    public ArrangementStack(List<TypeOfArrangement> list, TypeOfArrangement type) {
        this.list = list;
        this.type = type;
    }

    /**
     * The constructor of the class
     * @param type The type of the arrangement
     */
    public ArrangementStack(TypeOfArrangement type) {
        this.list = new ArrayList<TypeOfArrangement>();
        this.type = type;
    }

    /**
     * A method to put below a new arragement on the stack
     * @param o The type of the arrangement
     */
    @Override

    public void putBelow(TypeOfArrangement o) {
        super.putBelow(o);
    }

    /**
     * A method to pick a specific arrangement
     * @param o
     * @return
     */
    @Override
    public TypeOfArrangement pick(TypeOfArrangement o) {
        return super.pick(o);
    }

    /**
     * A method to random pick an arrangement
     * @return
     */
    @Override
    public TypeOfArrangement randomPick() {
        return super.randomPick();
    }

    /**
     * A method to put on a string the stack
     * @return The string representation of the stack
     */
    @Override

    public String toString() {
        return super.toString();
    }

    /**
     * The getter of the stack
     * @return A list of the stack
     */
    @Override

    public List<TypeOfArrangement> getStack() {
        return super.getStack();
    }

    /**
     * A method to generate all of the arrangement
     */
    @Override

    public void generate() {
        for(int i = 0; i < 3; i++){
            this.putBelow(this.getType());
        }
    }

    /**
     * A getter of the type of the stack
     * @return The type of the stack
     */
    public TypeOfArrangement getType() {
        return type;
    }

    /**
     * A setter of the type of the stack
     * @param type The new type of the stack
     */
    public void setType(TypeOfArrangement type) {
        this.type = type;
    }
}
