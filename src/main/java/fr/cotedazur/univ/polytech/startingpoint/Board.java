package fr.cotedazur.univ.polytech.startingpoint;

import java.util.ArrayList;

public class Board {
    private ArrayList<Tile> boardTiles = new ArrayList<>();

    public String addTile(Tile tile){
        boardTiles.add(tile);
        return "Une carte à été posée en:"+tile.getCoordinnateX()+" "+tile.getCoordinnateY();
    }

    //this method returns an ArrayList of all the possible positions where a new tile can be added
    public ArrayList<Coordinate> scanAvailableTilePosition() {
        //TODO
        return new ArrayList<Coordinate>();
    }

}
