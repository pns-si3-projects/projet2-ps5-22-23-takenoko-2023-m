package fr.cotedazur.univ.polytech.startingpoint;

import java.util.List;

public class ObjectiveStackGardener extends Stack<ObjectiveGardener> {


    public ObjectiveStackGardener(List<ObjectiveGardener> list){
        super(list);

    }

    public ObjectiveStackGardener() {
        super();
    }


    public void putBelow(ObjectiveGardener o){
        this.list.add( o);
    }


    @Override
    public boolean isValid() {
        return super.isValid();
    }



    public ObjectiveGardener pick(ObjectiveGardener o){
        for (ObjectiveGardener objective: list){
            if (objective.equals(o)){
                list.remove(objective);
                return o;
            }
        }
        System.out.println("L'objectif voulu n'est pas dans la pile");
        return null;
    }


    public ObjectiveGardener randomPick(){
        int i = (int) (Math.random() * list.size());
        ObjectiveGardener o = list.get(i);
        list.remove(o);
        return o;
    }

    public  List<ObjectiveGardener> getStack(){
        return list;
    }




    public String toString(){
        String s = "Pile d'objectifs jardinier : ";
        for (ObjectiveGardener objective: list){
            s += objective.toString() + " ";
        }
        return s;

    }


}
