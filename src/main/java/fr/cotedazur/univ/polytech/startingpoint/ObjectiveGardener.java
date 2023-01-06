package fr.cotedazur.univ.polytech.startingpoint;


public class ObjectiveGardener implements ObjectiveInterface {
    private int nbPointsWin=2;
    private String type = "gardener";

    public int getNbPointsWin() {
        return nbPointsWin;
    }

    public void setNb(int nb) {
        this.nbPointsWin = nb;
    }

    public ObjectiveGardener(String type, int nb){
        this.type = type;
        this.nbPointsWin = nb;
    }

    public boolean isValid(Board b){
        for(Tile tile : b.getBoardTiles()){
            if(tile.getBamboo() == this.getNbPointsWin()){
                return true;
            }
        }
        return false;
    }
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    public String toString(){
        return "Objectif de type "+this.type;
    }

}