package fr.cotedazur.univ.polytech.startingpoint;


import fr.cotedazur.univ.polytech.startingpoint.bots.Bot;

public class ObjectiveGardener extends ObjectifWithOneColor implements ObjectiveInterface {
    /** The number of points of the objective*/
    private int nbPointsWin;
    /** The type of the objective*/
    private String type = "gardener";
    /** The number of bamboo required*/
    private int nbBambooRequired;
    /** The arrangement of the objective*/
    private TypeOfArrangement typeOfArrangement;
    /**The complexity of the objective*/
    private int complexity = 7;

    /**
     * The constructor of the objective
     * @param type The type of the objective
     * @param nbBambooRequired The number of bamboo required
     * @param nbPointsWin The number of points of the objective
     * @param typeOfTile The typeOfTile of the objective
     * @param typeOfArrangement The arrangement on the objective
     */
    public ObjectiveGardener(String type,int nbBambooRequired, int nbPointsWin, TypeOfTile typeOfTile, TypeOfArrangement typeOfArrangement) {
        super(typeOfTile);
        this.type = type;
        this.typeOfArrangement = typeOfArrangement;
        this.nbPointsWin = nbPointsWin;
        this.nbBambooRequired = nbBambooRequired;
        this.typeOfTile = typeOfTile;
    }

    /**
     * A setter of the new complexity
     * @param complexity The nex complexity
     */
    public void setComplexity(int complexity){
        if(this.complexity>complexity){
            this.complexity = complexity;
        }
    }

    /**
     * A getter of the complexity
     * @return The complexity
     */
    public int getComplexity(){
        return this.complexity;
    }

    /**
     * A getter of the arranggement
     * @return The type of the arrangement
     */
    public TypeOfArrangement getTypeOfArrangement() {
        return typeOfArrangement;
    }

    /**
     * A getter of the points of the objective
     * @return The number of points of the objective
     */
    public int getNbPointsWin() {
        return nbPointsWin;
    }

    /**
     * A setter of the number of points of the objective
     * @param nb The new number of points of the objective
     */
    public void setNbPointsWin(int nb) {
        this.nbPointsWin = nb;
    }

    /**
     * The getter of the number of bamboo required
     * @return The number of bamboo required
     */
    public int getNbBambooRequired() { return this.nbBambooRequired; }

    /**
     * A method to check if the objective is valid
     * @param p The bot
     * @param b The board
     * @return True if the objective is valid, else false
     */
    public boolean isValid(Bot p, Board b){
        for(Tile tile : b.getBoardTiles()){
            if(tile.getBamboo() >= this.getNbBambooRequired() && tile.getTypeOfTile().equals(this.getTypeOfTile())){
                tile.setBamboo(0);
                return true;
            }
        }
        return false;
    }

    /**
     * Call the specific play method of the bot
     * @param player The bot
     */
    @Override
    public void play(Bot player) {
        player.playForGardenerCard();
    }

    /**
     * A getter of the type of the objective
     * @return The type of the objective
     */
    public String getType() {
        return type;
    }

    /**
     * A setter of the new type of the objective
     * @param type The new type of the objective
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * toString method
     * @return A string
     */
    public String toString(){
        return "Objectif de type "+this.type;
    }


}
