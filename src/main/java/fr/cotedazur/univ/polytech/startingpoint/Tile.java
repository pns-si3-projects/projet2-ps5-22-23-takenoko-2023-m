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
}
