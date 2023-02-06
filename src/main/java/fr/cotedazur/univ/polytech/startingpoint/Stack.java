package fr.cotedazur.univ.polytech.startingpoint;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.*;
public abstract class Stack<T> {
    public List<T> list;

    protected Stack(List<T> list){
        this.list = list;
    }
    protected Stack(){
        this.list = new ArrayList<T>();
    }

    public void putBelow(T o){
        this.list.add(o);
    }

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

    public T randomPick(){
        if(list.isEmpty()){
            throw new IllegalArgumentException("La pile est vide");
        }
        int i = (int) (Math.random() * list.size());
        T o = list.get(i);
        list.remove(o);
        return o;
    }



    public String toString(){
        String s = "Pile d'objectifs : ";
        for (T objective: list){
            s += objective.toString() + " ";
        }
        return s;

    }

    public List<T> getStack(){
        return list;
    }

    public void generate(){

    }
}
