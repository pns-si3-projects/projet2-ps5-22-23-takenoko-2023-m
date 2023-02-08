package fr.cotedazur.univ.polytech.startingpoint;


import fr.cotedazur.univ.polytech.startingpoint.bots.Bot;
import fr.cotedazur.univ.polytech.startingpoint.bots.IntermediateBot;
import fr.cotedazur.univ.polytech.startingpoint.bots.PrimaryBot;

public class ObjectiveGardener extends ObjectifWithOneColor implements ObjectiveInterface {
    private int nbPointsWin;
    private String type = "gardener";
    private int nbBambooRequired;



    private TypeOfArrangement typeOfArrangement;
    private int complexity;

    public ObjectiveGardener(String type,int nbBambooRequired, int nbPointsWin, TypeOfTile typeOfTile, TypeOfArrangement typeOfArrangement) {
        super(typeOfTile);
        this.type = type;
        this.typeOfArrangement = typeOfArrangement;
        this.nbPointsWin = nbPointsWin;
        this.nbBambooRequired = nbBambooRequired;
        this.typeOfTile = typeOfTile;
    }

    public void setComplexity(int complexity){
        if(this.complexity>complexity){
            this.complexity = complexity;
        }
    }
    public int getComplexity(){
        return this.complexity;
    }
    public TypeOfArrangement getTypeOfArrangement() {
        return typeOfArrangement;
    }
    public int getNbPointsWin() {
        return nbPointsWin;
    }

    public void setNbPointsWin(int nb) {
        this.nbPointsWin = nb;
    }

    public int getNbBambooRequired() { return this.nbBambooRequired; }

    public boolean isValid(Bot p, Board b){
        for(Tile tile : b.getBoardTiles()){
            if(tile.getBamboo() >= this.getNbBambooRequired() && tile.getTypeOfTile().equals(this.getTypeOfTile())){
                tile.setBamboo(0);
                return true;
            }
        }
        return false;
    }

    @Override
    public void play(PrimaryBot player) {
        player.playForGardenerCard();
    }

    @Override
    public void play(IntermediateBot player) {
        player.playForGardenerCard();
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
