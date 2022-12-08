package fr.cotedazur.univ.polytech.startingpoint;

import java.util.ArrayList;

public class Board {
    private ArrayList<Tile> boardTiles = new ArrayList<>();

    public String addTile(Tile tile){
        boardTiles.add(tile);
        return "Une carte à été posée en:"+tile.getCoordinnateX()+" "+tile.getCoordinnateY();
    }

    //this method returns an ArrayList of all the possible positions that are in contact with the edge of the board
    //TODO : make it return only the available positions for new tiles = coordinates where there are at least two neighbours
    public ArrayList<Coordinate> scanAvailableTilePosition() {
        ArrayList<Coordinate> potentiallyAvailableCoordinates = new ArrayList<>();
        for (int i = 0; i < boardTiles.size(); i++) {   //creates an array that contains all the coordinates of the tiles of the board and the available tile positions at the edge of the board BUT contains double coordinates
            potentiallyAvailableCoordinates.addAll(boardTiles.get(i).getNeighbourCoordinates());
        }
        ArrayList<Coordinate> availableCoordinates = new ArrayList<>();
        for (int i = 0; i < potentiallyAvailableCoordinates.size(); i ++) {
            boolean isDouble = false;
            boolean isPlaced = false;
            for (int j = 0; j < availableCoordinates.size(); j++) {
                if (potentiallyAvailableCoordinates.get(i).equals(availableCoordinates.get(j))) {
                    isDouble = true;
                    break;
                }
            }
            for (int j = 0; j < boardTiles.size(); j++) {
                if (potentiallyAvailableCoordinates.get(i).equals(boardTiles.get(j).getCoordinate())) {
                    isPlaced = true;
                    break;
                }
            }
            if (!isDouble && !isPlaced) {
                availableCoordinates.add(potentiallyAvailableCoordinates.get(i));
            }
        }
        return availableCoordinates;
    }

    public ArrayList<Tile> getBoardTiles() {
        return boardTiles;
    }

    public boolean isInBoard(int x, int y){
        for(Tile tile : boardTiles){
            if(tile.getCoordinnateX() == x && tile.getCoordinnateY() == y){
                return true;
            }
        }
        return false;
    }

    public void setBoardTiles(ArrayList<Tile> boardTiles) {
        this.boardTiles = boardTiles;
    }

    public Tile getTile(Coordinate coordinate) {
        for(Tile i : boardTiles){
            if(i.getCoordinate().equals(coordinate)){
                return i;
            }
        }
        return null;
    }
}
