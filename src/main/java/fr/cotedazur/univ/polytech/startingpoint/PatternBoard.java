package fr.cotedazur.univ.polytech.startingpoint;
/*
This class stock a pattern present in board
 */
public class PatternBoard extends Pattern{
    final Coordinate coordinate;
    public PatternBoard(TypeOfPattern typeOfPattern,TypeOfTile firstColor, TypeOfTile secondColor, Coordinate coordinate) {
        super(typeOfPattern, firstColor, secondColor);
        this.coordinate = coordinate;
    }
    public PatternBoard(TypeOfPattern typeOfPattern,TypeOfTile firstColor, Coordinate coordinate) {
        super(typeOfPattern, firstColor);
        this.coordinate = coordinate;
    }
    public boolean equals(Pattern pattern){
        if(this.type.equals(pattern.type)){
            if(this.firstColor.equals(pattern.firstColor)){
                if(this.secondColor!=null&&pattern.secondColor!=null){
                    return this.secondColor.equals(pattern.secondColor);
                }
                else return this.secondColor == null && pattern.secondColor == null;
            }
        }
        return false;
    }
    public Coordinate getCoordinate() {
        return coordinate;
    }
}
