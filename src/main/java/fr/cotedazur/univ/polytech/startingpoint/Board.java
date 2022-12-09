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

// ----- This method still does not work -----
    public ArrayList<Coordinate> newScanAvailableTilePosition() {

        ArrayList<Coordinate> occupiedCoordinates = new ArrayList<>();
        ArrayList<Coordinate> availableCoordinates = new ArrayList<>();

        // n complexity, gets the coordinates of all the tiles of the board
        for (int i = 0; i < boardTiles.size(); i++) {
            occupiedCoordinates.add(boardTiles.get(i).getCoordinate());
        }

        for (int i = 0; i < boardTiles.size(); i++) {       //cycling through all the tiles of the board
            //we get all the neighbours of tile[i]
            ArrayList<Coordinate> closeNeighbours = boardTiles.get(i).getNeighbourCoordinates();
            for (int j = 0; j < closeNeighbours.size(); j++) {
                boolean isDouble = false;
                boolean isPlaced = false;
                boolean isIllegal = false;

                //we check if the close neighbour is already in the ArrayList
                if (availableCoordinates.contains(closeNeighbours.get(j))) {
                    isDouble = true;
                }

                //we check if the close neighbour is already placed on the board
                if (occupiedCoordinates.contains(closeNeighbours.get(j))) {
                    isPlaced = true;
                }

                //checks if the close neighbour is legal == has two neighbours on the board
                //TODO not quite implemented yet
                //if(CONDITION)

                if (!isDouble && !isPlaced && !isIllegal) {
                    availableCoordinates.add(closeNeighbours.get(i));
                }
            }
        }
        return availableCoordinates;
    }

    public void setBoardTiles(ArrayList<Tile> boardTiles) {
        this.boardTiles = boardTiles;
    }
}
