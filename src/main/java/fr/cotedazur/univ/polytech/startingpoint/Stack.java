package fr.cotedazur.univ.polytech.startingpoint;

import java.util.ArrayList;
import java.util.List;
public abstract class Stack<T> {
    protected List<T> list;

    protected Stack(List<T> list){
        this.list = list;
    }
    protected Stack(){
        this.list = new ArrayList<>();
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
        StringBuilder s = new StringBuilder("Pile d'objectifs : ");
        for (T objective: list){
            s.append(objective.toString()).append(" ");
        }
        return s.toString();

    }

    public List<T> getStack(){
        return list;
    }

    public void generate(){

    }
}
