package fr.cotedazur.univ.polytech.startingpoint;


public class ObjectiveGardener implements ObjectiveInterface {
    private int nbPointsWin;
    private String type = "gardener";
    private int nbBambooRequired;

    private TypeOfTile typeOfTile;

    public ObjectiveGardener(String type,int nbBambooRequired, int nbPointsWin, TypeOfTile typeOfTile){
        this.type = type;
        this.nbPointsWin = nbPointsWin;
        this.nbBambooRequired = nbBambooRequired;
        this.typeOfTile = typeOfTile;
    }

    public int getNbPointsWin() {
        return nbPointsWin;
    }

    public TypeOfTile getTypeOfTile(){ return this.typeOfTile;}

    public void setNbPointsWin(int nb) {
        this.nbPointsWin = nb;
    }

    public int getNbBambooRequired() { return this.nbBambooRequired; }

    public boolean isValid(Player p,Board b){
        for(Tile tile : b.getBoardTiles()){
            if(tile.getBamboo() == this.getNbBambooRequired()){
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