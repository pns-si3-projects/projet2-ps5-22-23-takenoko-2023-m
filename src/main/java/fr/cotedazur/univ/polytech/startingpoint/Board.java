package fr.cotedazur.univ.polytech.startingpoint;

import java.util.ArrayList;
import java.util.List;

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

    private final ObjectiveStackGardener stackGardener = new ObjectiveStackGardener();

    private final ObjectiveStackPanda stackPanda = new ObjectiveStackPanda();

    private final ObjectiveStackPlot stackPlot = new ObjectiveStackPlot();

    private final TileStack tileStack = new TileStack();

    final PatternDetector patternDetector = new PatternDetector(this);

    //constructor setting up the first tile of the board
    public Board(){
        this.stackGardener.generate();
        this.stackPanda.generate();
        this.stackPlot.generate();
        this.addTile(new Tile(new Coordinate(0,0),TypeOfTile.POND));
    }

    public ObjectivePanda getPandaCard(){
        return this.stackPanda.randomPick();
    }

    public ObjectiveGardener getGardenerCard(){
        return this.stackGardener.randomPick();
    }

    public ObjectivePlot getPlotCard(){
        return this.stackPlot.randomPick();
    }

    //this method allow a player to move the gardener on a decided position
    public String moveGardenerOn(Coordinate coordinate){

        String bNumber = gardener.moveOn(coordinate);
        int nbBamboo = this.getTile(coordinate).getBamboo();
        return "Le jardinier à été déplacé en "+coordinate.getX()+", "+coordinate.getY() + " voici les tuiles affectées : \n" + bNumber + "La case " + coordinate + " a poussé et est maintenant à " + this.getTile(coordinate).getBamboo() + " bambou(s)\n";


    }

    public String movePandaOn(Coordinate coordinate, Player player){
        this.panda.moveOn(coordinate,player);
        return "Le panda a ete deplace en "+coordinate.getX()+", "+coordinate.getY() + " il possede maintenant : "+player.getNbBamboo(TypeOfTile.GREEN) +" bambous verts, "+player.getNbBamboo(TypeOfTile.YELLOW)+" bambous jaunes et "+player.getNbBamboo(TypeOfTile.RED)+" bambous roses";
    }

    public String addTile(Tile tile){
        boardTiles.add(tile);
        patternDetector.detectPatternNear(tile.getCoordinate());
        return "Une carte a ete posee en:"+tile.getCoordinnateX()+" "+tile.getCoordinnateY();
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

    /**
     * Pick three tile in the tileStack
     * @return the array of the three tiles
     */
    public List<Tile> pickThreeTiles() {
        return tileStack.pickThreeTiles();
    }

    /**
     * Find the best available position to place a tile and complete the objective
     * @param objectivePlot the objective to complete
     * @return the coordinate of the best position
     */
    public Coordinate bestCoordinateForLine(ObjectivePlot objectivePlot) {
        return patternDetector.bestCoordinateForLine(objectivePlot);
    }

    /**
     * Find all availbable coordinates near a specific coordinate
     * @param firstTileCoordinate coordinate of the position to check
     * @return the list of available coordinates
     */
    public List<Coordinate> getAvailableCoordinateNear(Coordinate firstTileCoordinate) {
        List<Coordinate> availableCoordinates = new ArrayList<>();
        this.getTile(firstTileCoordinate).getNeighbourCoordinates().forEach(coordinate -> {
            if(!this.isInBoard(coordinate.getX(),coordinate.getY())){
                availableCoordinates.add(coordinate);
            }
        });
        return availableCoordinates;
    }

    public void putBackInTileStack(Tile tile) {
        tileStack.putBelow(tile);
    }

    public TileStack getTileStack() {
        return tileStack;
    }
}

