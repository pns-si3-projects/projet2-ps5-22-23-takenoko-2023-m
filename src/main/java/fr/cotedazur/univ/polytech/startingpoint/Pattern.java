package fr.cotedazur.univ.polytech.startingpoint;

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
}
