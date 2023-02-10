package fr.cotedazur.univ.polytech.startingpoint;

import fr.cotedazur.univ.polytech.startingpoint.bots.Bot;

import java.util.List;

public class ObjectivePlot implements ObjectiveInterface {
    /**The pattern of the objective*/
    final Pattern pattern;
    /**The number of points of the card*/
    final int nbPointsWin;
    /**The complexity of the card*/
    private int complexity;

    /**
     * The constructor of the objective
     * @param pattern The specific pattern
     */
    public ObjectivePlot(Pattern pattern){
        this.pattern = pattern;
        this.nbPointsWin = generatePatternPoint();
    }

    /**
     * The setter of the new complexity
     * @param complexity The new complexity
     */
    public void setComplexity(int complexity){
        if(this.complexity>complexity){
            this.complexity = complexity;
        }
    }

    /**
     * The getter of the complexity
     * @return The complexity
     */
    public int getComplexity(){
        return this.complexity;
    }

    /**
     * A getter of the number of points of the card
     * @return The number of points of the card
     */
    public int getNbPointsWin(){
        return this.nbPointsWin;
    }

    /**
     * Check if the objective is valid
     * @param p The bot
     * @param b The board
     * @return True if it's valid, false else
     */
    public boolean isValid(Bot p, Board b){
        for(Tile tile : b.getBoardTiles()){
            for(Tile tile2 : b.getBoardTiles()){
                if(tile.isNeighbour(tile2)){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * A method to call the specific method of the player
     * @param player The bot
     */
    @Override
    public void play(Bot player) {
        player.playForPatternCard();
    }


    /**
     * A method to generate the pattern's point
     * @return The pattern's point
     */
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

    /**
     * A getter of the type of the card
     * @return the type of the card
     */
    public String getType() {
        return this.pattern.type.toString();
    }

    /**
     * A setter of the type of the card
     * @param type The new type of the card
     */
    public void setType(String type) {
        this.pattern.type = TypeOfPattern.valueOf(type);
    }

    /**
     * A getter of the pattern
     * @return the pattern
     */
    public Pattern getPattern() {
        return pattern;
    }

    /**
     * toString method
     * @return A string
     */
    public String toString(){
        if(this.pattern.type.equals(TypeOfPattern.SQUARE)){
            return "Objectif de type "+this.pattern.type + " et de couleur " + this.pattern.firstColor + " et " + this.pattern.secondColor+ " et de nombre de points " + this.nbPointsWin;
        }
        return "Objectif plot de type "+this.pattern.type+" et de couleur "+this.pattern.firstColor + " et de nombre de points " + this.nbPointsWin;
    }

    /**
     * A getter of the colors of the objectives
     * @return The colors of the objectives
     */
    public List<TypeOfTile> getColors(){
        return this.pattern.getColors();
    }




}
