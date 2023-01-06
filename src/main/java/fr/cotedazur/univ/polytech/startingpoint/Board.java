package fr.cotedazur.univ.polytech.startingpoint;

import java.util.ArrayList;

public class Board {
    private ArrayList<Tile> boardTiles = new ArrayList<>();

    public Gardener getGardener() {
        return gardener;
    }

    private final Gardener gardener = new Gardener(this);


    public Panda getPanda() {
        return panda;
    }

    private final Panda panda = new Panda(this);


    //constructor setting up the first tile of the board
    public Board(){
        this.addTile(new Tile(0,0,TypeOfTile.GREEN));
    }

    //this method allow a player to move the gardener on a decided position
    public String moveGardenerOn(Coordinate coordinate){

        int bNumber = gardener.moveOn(coordinate);
        return "Le jardinier à été déplacé en "+coordinate.getX()+", "+coordinate.getY() + " cette tuile est maintenant à: "+ bNumber +" bamboo(s)";

    }

    public String movePandaOn(Coordinate coordinate, Player player){
        this.panda.moveOn(coordinate,player);
        return "Le panda à été deplace en "+coordinate.getX()+", "+coordinate.getY() + " il possède maintenant : "+player.getNbBamboo() +" bambous";
    }

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

    //this method returns an ArrayList of all the possible positions that are in contact with the edge of the board and at a legal position = near 0,0 or with two neighbours

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

    //return the tile at the designed coordinate
    public Tile getTile(Coordinate coordinate) {
        for(Tile i : boardTiles){
            if(i.getCoordinate().equals(coordinate)){
                return i;
            }
        }
        return null;
    }
}

