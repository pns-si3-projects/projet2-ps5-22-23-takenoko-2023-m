package fr.cotedazur.univ.polytech.startingpoint;

public class ObjectivePlot implements ObjectiveInterface {

    private String type = "line2";
    private int nbPointsWin;


    public ObjectivePlot(String type, int nbPointsWin){
        this.type = type;
        this.nbPointsWin = nbPointsWin;
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
