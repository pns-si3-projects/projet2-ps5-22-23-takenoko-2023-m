package fr.cotedazur.univ.polytech.startingpoint;

public class ObjectivePlot {

    private String type = "line2";


    public ObjectivePlot(String type){
        this.type = type;
    }

    public boolean isValid(Board b){
        for(Tile tile : b.getBoardTiles()){
            for(Tile tile2 : b.getBoardTiles()){
                if(tile.isNeighbor(tile2)){
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
