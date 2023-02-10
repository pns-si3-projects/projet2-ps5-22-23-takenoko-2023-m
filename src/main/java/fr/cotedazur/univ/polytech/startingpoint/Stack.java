package fr.cotedazur.univ.polytech.startingpoint;

import java.util.ArrayList;
import java.util.List;
public abstract class Stack<T> {
    protected List<T> list;

    /**
     * A constructor of the class with one parameter
     * @param list The list of the card
     */
    protected Stack(List<T> list){
        this.list = list;
    }

    /**
     * A constructor of the class with no parameter
     */
    protected Stack(){
        this.list = new ArrayList<>();
    }

    /**
     * A method to put below a card
     * @param o The card to put below
     */
    public void putBelow(T o){
        this.list.add(o);
    }

    /**
     * A method to pick a specific card
     * @param o The specific card you want to pick
     * @return The good card
     */
    public T pick(T o){
        if(this.list.isEmpty()){
            throw new ArrayIndexOutOfBoundsException("pile vide");
        }
        else{
            for (T objective: list){
                if (objective.equals(o)){
                    list.remove(objective);
                    return o;
                }
            }
        }



        Main.LOGGER.info("L'objectif voulu n'est pas dans la pile");
        return null;
    }

    /**
     * A specific method to random pick a card
     * @return A random card
     */
    public T randomPick(){
        if(list.isEmpty()){
            throw new IllegalArgumentException("La pile est vide");
        }
        int i = (int) (Math.random() * list.size());
        T o = list.get(i);
        list.remove(o);
        return o;
    }


    /**
     * toString method
     * @return a String
     */
    public String toString(){
        StringBuilder s = new StringBuilder("Pile d'objectifs : ");
        for (T objective: list){
            s.append(objective.toString()).append(" ");
        }
        return s.toString();

    }

    /**
     * A getter of the stack
     * @return the List of the stack
     */
    public List<T> getStack(){
        return list;
    }

    /**
     * A method to generate the stack
     */
    public void generate(){

    }
}
