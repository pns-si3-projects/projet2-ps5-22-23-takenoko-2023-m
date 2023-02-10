package fr.cotedazur.univ.polytech.startingpoint;

import fr.cotedazur.univ.polytech.startingpoint.bots.Bot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
public class Board {
    private List<Tile> boardTiles = new ArrayList<>();

    public Dice getDice() {
        return dice;
    }

    public void setDice(Dice dice) {
        this.dice = dice;
    }

    private Dice dice = new Dice();

    public Gardener getGardener() {
        return gardener;
    }

    private final Gardener gardener = new Gardener(this);


    public Panda getPanda() {
        return panda;
    }

    private final Panda panda = new Panda(this);

    private final ObjectiveStackGardener stackGardener = new ObjectiveStackGardener();

    public ObjectiveStackPanda getStackPanda() {
        return stackPanda;
    }

    public ObjectiveStackPlot getStackPlot() {
        return stackPlot;
    }

    private final ObjectiveStackPanda stackPanda = new ObjectiveStackPanda();

    private final ObjectiveStackPlot stackPlot = new ObjectiveStackPlot();

    private final TileStack tileStack = new TileStack();

    private final ArrayList<Irrigation> placedIrrigations = new ArrayList<>();  //placed irrigations MUST be created using the board tiles for them to be modified by the irrigation
    private final ArrayList<Irrigation> legalIrrigationPlacement;   //hypothetical irrigations MUST be created using only coordinates

    private final ArrangementStack enclosureStack = new ArrangementStack(TypeOfArrangement.ENCLOSURE);
    private final ArrangementStack basinStack = new ArrangementStack(TypeOfArrangement.BASIN);
    private final ArrangementStack fertilizerStack = new ArrangementStack(TypeOfArrangement.FERTILIZER);


    final PatternDetector patternDetector = new PatternDetector(this);

    //constructor setting up the first tile of the board
    public Board(){
        this.stackGardener.generate();
        this.stackPanda.generate();
        this.stackPlot.generate();
        this.basinStack.generate();
        this.enclosureStack.generate();
        this.fertilizerStack.generate();
        this.addTile(new Tile(new Coordinate(0,0),TypeOfTile.POND, TypeOfArrangement.NONE));
        boardTiles.get(0).irrigate();
        this.legalIrrigationPlacement = new ArrayList<>(Arrays.asList(
                new Irrigation(new Coordinate(1,0),new Coordinate(0,1)),
                new Irrigation(new Coordinate(0,1),new Coordinate(-1,1)),
                new Irrigation(new Coordinate(-1,1),new Coordinate(-1,0)),
                new Irrigation(new Coordinate(-1,0),new Coordinate(0,-1)),
                new Irrigation(new Coordinate(0,-1),new Coordinate(1,-1)),
                new Irrigation(new Coordinate(1,-1),new Coordinate(1,0))
                ));
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
        return "Le jardinier à été déplacé en "+coordinate.getX()+", "+coordinate.getY() + " voici les tuiles affectées : \n" + bNumber + "La case " + coordinate + " a poussé et est maintenant à " + this.getTile(coordinate).getBamboo() + " bambou(s)\n";


    }

    public String movePandaOn(Coordinate coordinate, Bot player){
        this.panda.moveOn(coordinate,player);
        return "Le panda a ete deplace en "+coordinate.getX()+", "+coordinate.getY() + " il possede maintenant : "+player.getNbBamboo(TypeOfTile.GREEN) +" bambous verts, "+player.getNbBamboo(TypeOfTile.YELLOW)+" bambous jaunes et "+player.getNbBamboo(TypeOfTile.RED)+" bambous roses";
    }

    public String addTile(final Tile tile){
        List<Tile> listeTile = getBoardTiles();
        for(Tile t : listeTile){
            if(t.getCoordinate().equals(tile.getCoordinate())){
                return "On ne peut pas poser cette tuile ! ";
            }
        }
        boardTiles.add(tile);
        patternDetector.detectPatternNear(tile.getCoordinate());
        return "Une carte a ete posee en:"+tile.getCoordinnateX()+" "+tile.getCoordinnateY();
    }

    public boolean addIrrigation(Irrigation irrigation) {    //gets a dummyIrrigation
        if (legalIrrigationPlacement.contains(irrigation)) {
            Tile tmpTile1 = this.getTile(irrigation.getCoordinates().get(0));
            Tile tmpTile2 = this.getTile(irrigation.getCoordinates().get(1));

            if ((tmpTile1 != null) && (tmpTile2 != null)) {
                Irrigation newIrrigation = new Irrigation(tmpTile1, tmpTile2);
                placedIrrigations.add(newIrrigation);
                legalIrrigationPlacement.remove(legalIrrigationPlacement.indexOf(irrigation));

                List<Irrigation> neighbourIrrigations = irrigation.getNeighbourIrrigations();
                for (int i = 0; i < neighbourIrrigations.size(); i++) { //adds the new legal irrigation placements
                    if (!legalIrrigationPlacement.contains(neighbourIrrigations.get(i))&&!placedIrrigations.contains(neighbourIrrigations.get(i))) {
                        legalIrrigationPlacement.add(neighbourIrrigations.get(i));
                    }
                }

                this.patternDetector.detectPatternNear(irrigation.getCoordinates().get(0));
                this.patternDetector.detectPatternNear(irrigation.getCoordinates().get(1));
                return true;
            }
        }
        return false;
    }

    public List<Irrigation> getLegalIrrigationPlacement() {
        return legalIrrigationPlacement;
    }


    public List<Tile> getBoardTiles() {
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

    public List<Coordinate> scanAvailableTilePosition() {

        List<Coordinate> occupiedCoordinates = new ArrayList<>();
        List<Coordinate> availableCoordinates = new ArrayList<>();

        // n complexity, gets the coordinates of all the tiles of the board
        for (int i = 0; i < boardTiles.size(); i++) {
            occupiedCoordinates.add(boardTiles.get(i).getCoordinate());
        }

        for (int i = 0; i < boardTiles.size(); i++) {       //cycling through all the tiles of the board
            //we get all the neighbours of tile[i]
            List<Coordinate> closeNeighbours = boardTiles.get(i).getNeighbourCoordinates();
            for (int j = 0; j < closeNeighbours.size(); j++) {
                boolean isDouble = availableCoordinates.contains(closeNeighbours.get(j));
                boolean isPlaced = occupiedCoordinates.contains(closeNeighbours.get(j));
                boolean isIllegal = false;
                //checks if the close neighbour is legal == has two neighbours on the board
                if (closeNeighbours.get(j).getNumberOfNeighbours(occupiedCoordinates) < 2) {
                    isIllegal = !new Coordinate(0,0).getNeighbourCoordinates().contains(closeNeighbours.get(j));   //the tile is illegal
                }

                if (!isDouble && !isPlaced && !isIllegal) {
                    availableCoordinates.add(closeNeighbours.get(j));
                }
            }
        }
        return availableCoordinates;
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
    public Coordinate bestCoordinateForBoomrang(ObjectivePlot objectivePlot) {
        return patternDetector.bestCoordinateForBoomrang(objectivePlot);
    }
    public Coordinate bestCoordinateForTriangle(ObjectivePlot objectivePlot) {
        return patternDetector.bestCoordinateForTriangle(objectivePlot);
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


    public ObjectiveStackGardener getStackGardener() {
        return stackGardener;
    }

    public ArrangementStack getEnclosureStack() {
        return enclosureStack;
    }

    public ArrangementStack getBasinStack() {
        return basinStack;
    }

    public ArrangementStack getFertilizerStack() {
        return fertilizerStack;
    }


    public TileStack getTileStack() {
        return tileStack;

    }

    public PatternDetector getPatternBoard() {
        return patternDetector;
    }

    public boolean isPlotCardEmpty() {
        return stackPlot.getStack().isEmpty();
    }
}

