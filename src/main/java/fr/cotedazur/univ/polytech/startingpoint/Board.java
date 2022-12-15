package fr.cotedazur.univ.polytech.startingpoint;

import java.util.ArrayList;

public class Board {
    private ArrayList<Tile> boardTiles = new ArrayList<>();

    public String addTile(Tile tile){
        boardTiles.add(tile);
        return "Une carte à été posée en:"+tile.getCoordinnateX()+" "+tile.getCoordinnateY();
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

    //this method returns an ArrayList of all the possible positions that are in contact with the edge of the board
    //TODO : make it return only the available positions for new tiles = coordinates where there are at least two neighbours
    public ArrayList<Coordinate> scanAvailableTilePosition() {

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
                if (closeNeighbours.get(j).getNumberOfNeighbours(occupiedCoordinates) < 2) {
                    isIllegal = true;   //the tile is illegal
                    //except if it is near 0,0
                    ArrayList<Coordinate> near0_0 = new Coordinate(0,0).getNeighbourCoordinates();
                    if (near0_0.contains(closeNeighbours.get(j))) {
                        //the tile is neat 0,0 and thus is legal
                        isIllegal = false;
                    }

                }

                if (!isDouble && !isPlaced && !isIllegal) {
                    availableCoordinates.add(closeNeighbours.get(j));
                }
            }
        }
        return availableCoordinates;
    }

    public void setBoardTiles(ArrayList<Tile> boardTiles) {
        this.boardTiles = boardTiles;
    }
}