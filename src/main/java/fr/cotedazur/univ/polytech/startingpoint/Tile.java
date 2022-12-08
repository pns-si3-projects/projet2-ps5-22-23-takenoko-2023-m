package fr.cotedazur.univ.polytech.startingpoint;

public class Tile {
    private final int coordinnateX;
    private final int coordinnateY;
    public Tile(int x, int y){
        coordinnateX = x;
        coordinnateY = y;
    }

    public int getCoordinnateX() {
        return coordinnateX;
    }

    public int getCoordinnateY(){
        return coordinnateY;
    }

//fonction temporaire destinée à être remplacée
    public boolean isNeighbor(Tile tile){
        if(tile.getCoordinnateX() == this.getCoordinnateX() && tile.getCoordinnateY() == this.getCoordinnateY()+1){
            return true;
        }
        if(tile.getCoordinnateX() == this.getCoordinnateX() && tile.getCoordinnateY() == this.getCoordinnateY()-1){
            return true;
        }
        if(tile.getCoordinnateX() == this.getCoordinnateX()+1 && tile.getCoordinnateY() == this.getCoordinnateY()){
            return true;
        }
        if(tile.getCoordinnateX() == this.getCoordinnateX()-1 && tile.getCoordinnateY() == this.getCoordinnateY()){
            return true;
        }
        if(tile.getCoordinnateX() == this.getCoordinnateX()+1 && tile.getCoordinnateY() == this.getCoordinnateY()-1){
            return true;
        }
        if(tile.getCoordinnateX() == this.getCoordinnateX()-1 && tile.getCoordinnateY() == this.getCoordinnateY()+1){
            return true;
        }
        return false;
    }


}

