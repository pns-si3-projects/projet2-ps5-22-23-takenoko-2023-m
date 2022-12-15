package fr.cotedazur.univ.polytech.startingpoint;


public class ObjectiveGardener implements ObjectiveInterface {

    private String type = "gardener";

    public int getNb() {
        return nb;
    }

    public void setNb(int nb) {
        this.nb = nb;
    }

    private int nb=2;

    public ObjectiveGardener(String type, int nb){
        this.type = type;
        this.nb = nb;
    }

    public boolean isValid(Board b){
        for(Tile tile : b.getBoardTiles()){
            if(tile.getBamboo() == this.getNb()){
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