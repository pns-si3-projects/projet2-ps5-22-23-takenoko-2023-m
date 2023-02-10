package fr.cotedazur.univ.polytech.startingpoint;

import java.util.ArrayList;
import java.util.List;

public class Pattern {
    TypeOfPattern type;
    TypeOfTile firstColor;
    TypeOfTile secondColor;

    /**
     * The constructor of the class
     * @param type the type of the pattern
     * @param firstColor the first color of the pattern
     * @param secondColor the second color of the pattern
     */
    public Pattern(TypeOfPattern type, TypeOfTile firstColor, TypeOfTile secondColor){
        this.type = type;
        this.firstColor = firstColor;
        this.secondColor = secondColor;
    }

    /**
     * The constructor of the class
     * @param type the type of the pattern
     * @param firstColor the color of the pattern
     */
    public Pattern(TypeOfPattern type, TypeOfTile firstColor){
        this.type = type;
        this.firstColor = firstColor;
    }


    /**
     * Equals method
     * @param obj The pattern to test with
     * @return true if it's equals, false if it's not
     */
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

    /**
     * hashCode method
     * @return the hashCode of the object
     */
    @Override
    public int hashCode() {
        return this.type.hashCode()+this.firstColor.hashCode()+this.secondColor.hashCode();
    }

    /**
     * A getter of the patten's colors
     * @return a list of the colors (TypeOfTile)
     */
    public List<TypeOfTile> getColors() {
        List<TypeOfTile> colors = new ArrayList<>();
        colors.add(this.firstColor);
        if(this.secondColor!=null){
            colors.add(this.secondColor);
        }
        return colors;
    }

    /**
     * toString method
     * @return a String
     */
    @Override
    public String toString(){
        if(this.type.equals(TypeOfPattern.SQUARE)){
            return "Pattern de type "+this.type + " et de couleur " + this.firstColor + " et " + this.secondColor;
        }
        return "Pattern de type "+this.type+" et de couleur "+this.firstColor;
    }

    public TypeOfPattern getType() {
        return type;
    }
}
