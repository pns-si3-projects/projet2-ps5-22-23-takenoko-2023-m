package fr.cotedazur.univ.polytech.startingpoint;

import java.util.ArrayList;
import java.util.List;

public class Pattern {
    int point;
    TypeOfPattern type;
    TypeOfTile firstColor;
    TypeOfTile secondColor;
    public Pattern(List<Pattern> createdPattern){
        do{
             generateRandomPattern();
        }while(createdPattern.contains(this));
        this.point = generatePatternPoint();
        createdPattern.add(this);
    }

    public Pattern(TypeOfPattern type, TypeOfTile firstColor, TypeOfTile secondColor){
        this.type = type;
        this.firstColor = firstColor;
        this.secondColor = secondColor;
    }
    public Pattern(TypeOfPattern type, TypeOfTile firstColor){
        this.type = type;
        this.firstColor = firstColor;
    }

    private int generatePatternPoint() {
        int sumOfPoint;
        if(this.type.equals(TypeOfPattern.SQUARE)){
            sumOfPoint = 3;
        }
        else sumOfPoint = 2;
        if(this.firstColor.equals(TypeOfTile.YELLOW)) return sumOfPoint+1;
        else if(this.firstColor.equals(TypeOfTile.RED)) return sumOfPoint+2;
        else return sumOfPoint;
    }

    private void generateRandomPattern() {
       this.type = randomPatternType();
       this.firstColor = randomTypeOfTile();
       if(this.type.equals(TypeOfPattern.SQUARE)&&Math.random()==0){
           this.secondColor = randomTypeOfTile();
       }
    }

    private TypeOfTile randomTypeOfTile() {
        return switch ((int) (Math.random() * 2)) {
            case 0 -> TypeOfTile.YELLOW;
            case 1 -> TypeOfTile.RED;
            default -> TypeOfTile.GREEN;
        };
    }

    private TypeOfPattern randomPatternType(){
        return switch ((int) (Math.random() * 3)) {
            case 0 -> TypeOfPattern.LINE;
            case 1 -> TypeOfPattern.BOOMRANG;
            case 2 -> TypeOfPattern.SQUARE;
            default -> TypeOfPattern.TRIANGLE;
        };
    }
}
