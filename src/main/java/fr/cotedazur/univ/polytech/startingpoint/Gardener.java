package fr.cotedazur.univ.polytech.startingpoint;

import java.util.ArrayList;

public class Gardener {
    private Coordinate coordinate;
    private final Board board;
    private Tile tile;
    public Gardener(Board board){
        coordinate = new Coordinate(0,0);
        this.board = board;
    }
    //move the entity gardener on a designed tile, growing 1 bamboo of this tile
    public int moveOn(Coordinate coordinate){
        this.coordinate = coordinate;
        spreadGrowing(coordinate);
        this.tile = board.getTile(coordinate);
        return this.tile.grow(1);
    }

    private void spreadGrowing(Coordinate coordinate) {
        ArrayList<Tile> boardTiles = board.getBoardTiles();
        ArrayList<Tile> tilesToGrow = new ArrayList<>();
        tilesToGrow.add(board.getTile(coordinate));
        for(Tile i : tilesToGrow){
            for(Coordinate c : i.getNeighbourCoordinates()){
                if(board.getTile(c).getTypeOfTile().equals(tile.getTypeOfTile())&& !(tilesToGrow.contains(board.getTile(c)))){
                    tilesToGrow.add(board.getTile(c));
                    board.getTile(c).grow(1);
                }
            }
        }
    }
}
