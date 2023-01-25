package fr.cotedazur.univ.polytech.startingpoint;

import java.util.ArrayList;
import java.util.List;

public class Pattern {
    TypeOfPattern type;
    TypeOfTile firstColor;
    TypeOfTile secondColor;

    public Pattern(TypeOfPattern type, TypeOfTile firstColor, TypeOfTile secondColor){
        this.type = type;
        this.firstColor = firstColor;
        this.secondColor = secondColor;
    }
    public Pattern(TypeOfPattern type, TypeOfTile firstColor){
        this.type = type;
        this.firstColor = firstColor;
    }



    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Pattern pattern){
            if(this.secondColor!= null){
                return this.type.equals(pattern.type)&&this.firstColor.equals(pattern.firstColor)&&this.secondColor.equals(pattern.secondColor);
            }
            return this.type.equals(pattern.type)&&this.firstColor.equals(pattern.firstColor);
        }
        return false;
    }
    @Override
    public int hashCode() {
        return this.type.hashCode()+this.firstColor.hashCode()+this.secondColor.hashCode();
    }

    public List<TypeOfTile> getColors() {
        List<TypeOfTile> colors = new ArrayList<>();
        colors.add(this.firstColor);
        if(this.secondColor!=null){
            colors.add(this.secondColor);
        }
        return colors;
    }
    @Override
    public String toString(){
        if(this.type.equals(TypeOfPattern.SQUARE)){
            return "Pattern de type "+this.type + " et de couleur " + this.firstColor + " et " + this.secondColor;
        }
        return "Pattern de type "+this.type+" et de couleur "+this.firstColor;
    }
}
