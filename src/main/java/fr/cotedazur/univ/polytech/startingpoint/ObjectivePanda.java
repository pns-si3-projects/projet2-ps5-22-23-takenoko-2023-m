package fr.cotedazur.univ.polytech.startingpoint;

import fr.cotedazur.univ.polytech.startingpoint.bots.Bot;
import fr.cotedazur.univ.polytech.startingpoint.bots.IntermediateBot;
import fr.cotedazur.univ.polytech.startingpoint.bots.PrimaryBot;

public class ObjectivePanda extends ObjectifWithOneColor implements ObjectiveInterface{
        /**The type of the objective*/
        private String type = "panda";
        /**The number of bamboo to eat*/
        private int nbToEat;
        /**The number of points of the objectives*/
        private int nbPointsWin;
        /**The complexity of the objective*/
        private int complexity;

    /**
     * The constructor of the objective
     * @param type The type of the objective
     * @param nbToEat The number of bamboo to eat
     * @param nbPointsWin The number of points of the objective
     * @param typeOfTile The typeOfTile of the objectives
     */
    public ObjectivePanda(String type, int nbToEat, int nbPointsWin, TypeOfTile typeOfTile) {
            super(typeOfTile);
            this.type = type;
            this.nbToEat = nbToEat;
            this.nbPointsWin = nbPointsWin;
        }

    /**
     * The setter of the complexity
      * @param complexity The new complexity
     */
    public void setComplexity(int complexity){
        if(this.complexity>complexity){
            this.complexity = complexity;
        }
    }

    /**
     * A getter of the complexity
     * @return the complexity
     */
    public int getComplexity(){
        return this.complexity;
    }

    /**
     * A getter of the number of points of the card
     * @return The number of points of the card
     */
    public int getNbPointsWin() { return this.nbPointsWin; }

    /**
     * The method to check if the objective is valid
     * @param p The bot
     * @param b The board
     * @return True if the objective is valid, else false
     */
        public boolean isValid(Bot p, Board b){
            if(p.getNbBamboo(this.typeOfTile)>=this.nbToEat){
                p.resetNbBamboo(this.typeOfTile);
                return true;
            }else{
                return false;
            }
        }

    /**
     * The method to call the play of the specific bot
     * @param player The specific bot
     */
    @Override
    public void play(Bot player) {
        player.playForPandaCard();
    }


    /**
     * A getter of the type of the objective
     * @return the type of the objective
     */
    public String getType() {
        return type;
    }

    /**
     * A setter of the new type of the tile
     * @param type The new type of the tile
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * A getter of the number of bamboo to eat
     * @return The number of bamboo to eat
     */
    public int getNbToEat(){
        return this.nbToEat;
    }

    /**
     * toString method
     * @return A string
     */
    public String toString(){
        return "Objectif de type "+this.type + " et de nombre de bambou a manger " + this.nbToEat;
    }

}
