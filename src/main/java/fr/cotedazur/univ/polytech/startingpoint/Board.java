package fr.cotedazur.univ.polytech.startingpoint;

import java.util.ArrayList;

public class Board {
    ArrayList<Tile> boardTiles = new ArrayList<>();
    private ArrayList<Tile> availableTiles = new ArrayList<>();
    public String addTile(Tile tile){
        boardTiles.add(tile);
        return "Une carte à été posée en:"+tile.getCoordinnateX()+" "+tile.getCoordinnateY();
    }




}
