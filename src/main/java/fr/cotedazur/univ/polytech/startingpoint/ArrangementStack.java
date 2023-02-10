package fr.cotedazur.univ.polytech.startingpoint;

import java.util.ArrayList;
import java.util.List;

public class ArrangementStack extends Stack<TypeOfArrangement>{
    private TypeOfArrangement type;
    public ArrangementStack(List<TypeOfArrangement> list, TypeOfArrangement type) {
        this.list = list;
        this.type = type;
    }

    public ArrangementStack(TypeOfArrangement type) {
        this.list = new ArrayList<TypeOfArrangement>();
        this.type = type;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public void generate() {
        for(int i = 0; i < 3; i++){
            this.putBelow(this.getType());
        }
    }

    public TypeOfArrangement getType() {
        return type;
    }

    public void setType(TypeOfArrangement type) {
        this.type = type;
    }
}
