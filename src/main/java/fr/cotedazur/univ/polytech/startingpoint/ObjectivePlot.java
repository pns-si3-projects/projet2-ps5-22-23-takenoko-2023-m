package fr.cotedazur.univ.polytech.startingpoint;

import java.util.List;

public class ObjectivePlot implements ObjectiveInterface {

    final Pattern pattern;
    final int nbPointsWin;

    private TypeOfTile typeOfTile;


    public ObjectivePlot(Pattern pattern){
        this.pattern = pattern;
        this.nbPointsWin = generatePatternPoint();
        this.typeOfTile = null; //this type of card dont have a typeOfTile because it can got different colors
    }

    public int getNbPointsWin(){
        return this.nbPointsWin;
    }

    public boolean isValid(Player p, Board b){
        for(Tile tile : b.getBoardTiles()){
            for(Tile tile2 : b.getBoardTiles()){
                if(tile.isNeighbour(tile2)){
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void play(Player player) {
        player.playForPatternCard();
    }

    private int generatePatternPoint() {
        int sumOfPoint;
        if(this.pattern.type.equals(TypeOfPattern.SQUARE)){
            sumOfPoint = 3;
        }
        //normally 2
        else sumOfPoint = 2;
        if(this.pattern.firstColor.equals(TypeOfTile.YELLOW)) return sumOfPoint+1;
        else if(this.pattern.firstColor.equals(TypeOfTile.RED)) return sumOfPoint+2;
        else return sumOfPoint;
    }
    public String getType() {
        return this.pattern.type.toString();
    }

    public void setType(String type) {
        this.pattern.type = TypeOfPattern.valueOf(type);
    }

    public Pattern getPattern() {
        return pattern;
    }

    public String toString(){
        if(this.pattern.type.equals(TypeOfPattern.SQUARE)){
            return "Objectif de type "+this.pattern.type + " et de couleur " + this.pattern.firstColor + " et " + this.pattern.secondColor+ " et de nombre de points " + this.nbPointsWin;
        }
        return "Objectif plot de type "+this.pattern.type+" et de couleur "+this.pattern.firstColor + " et de nombre de points " + this.nbPointsWin;
    }
    public List<TypeOfTile> getColors(){
        return this.pattern.getColors();
    }





}
