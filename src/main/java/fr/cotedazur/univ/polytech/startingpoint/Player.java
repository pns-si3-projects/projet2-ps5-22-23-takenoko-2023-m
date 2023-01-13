package fr.cotedazur.univ.polytech.startingpoint;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Player {
    private int point = 0;
    private String nom;
    private final Board board;
    private int nbBamboo = 0;
    private int nbActions = 2;

    private ObjectiveInterface focusCard = null;

    private ArrayList<ObjectiveInterface> objectives = new ArrayList<ObjectiveInterface>();
    public Player(Board board, String nom){
        this.nom = nom;
        this.board = board;
        this.pickGardenerCard();
        this.pickPlotCard();
        this.pickPandaCard();
        //System.out.println(objectives);
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getNbActions() { return this.nbActions;}


    public void playAction() {
        this.nbActions -= 1;
        System.out.println("Le joueur " +this.getNom() +" vient de jouer");
    }

    public void resetNbActions() { this.nbActions = 2;}


    public void play(){
        this.resetNbActions();
        if (this.focusCard == null){
            checkBetterCard();
        }
        if(this.focusCard instanceof ObjectiveGardener){
            this.playForGardenerCard();
        }else if(this.focusCard instanceof ObjectivePanda){
            this.playForPandaCard();
        }else{
            this.playForPatternCard();
        }
        this.checkPatternOnBoard();
        System.out.println();
    }

    public void playForPatternCard(){
        ObjectivePlot objectivePlot = (ObjectivePlot) this.focusCard;
        List<TypeOfTile> colors = objectivePlot.getColors();
        List<Tile> tilesPicked = board.pickThreeTiles();
        System.out.println("Le joueur " +this.getNom() +" a pioche les tuiles suivantes :");
        for(Tile tile : tilesPicked){
            System.out.println(tile.getTypeOfTile());
        }
        if(objectivePlot.getPattern().type.equals(TypeOfPattern.LINE)){
            boolean isPlaced = false;
            List<Tile> tilesToPutBackInStack = new ArrayList<>();
            for(Tile tile : tilesPicked){
                if(tile.getTypeOfTile().equals(colors.get(0))&&!isPlaced){
                    isPlaced = true;
                    System.out.println(board.addTile(new Tile(board.bestCoordinateForLine(objectivePlot),tile.getTypeOfTile()))+ " de type:"+tile.getTypeOfTile());
                }
                else{
                    tilesToPutBackInStack.add(tile);
                }
            }
            if(!isPlaced){
                System.out.println(board.addTile(new Tile(board.scanAvailableTilePosition().get(0),tilesPicked.get(0).getTypeOfTile()))+ " de type:"+tilesPicked.get(0).getTypeOfTile());
                board.putBackInTileStack(tilesPicked.get(1));
                board.putBackInTileStack(tilesPicked.get(2));
            }
            else{
                board.putBackInTileStack(tilesToPutBackInStack.get(0));
                board.putBackInTileStack(tilesToPutBackInStack.get(1));
            }
            this.playAction();
        }

    }

    private void checkPatternOnBoard() {
    //take objective of type ObjectivePlot from the list objectives
        ArrayList<ObjectivePlot> objectivePlotList = new ArrayList<>();
        for(ObjectiveInterface objective : this.objectives){
            if(objective instanceof ObjectivePlot){
                objectivePlotList.add((ObjectivePlot) objective);
            }
        }
        for(ObjectivePlot objectivePlot : objectivePlotList){
            if(board.patternDetector.getPatternBoardList().contains(objectivePlot.getPattern())){
                this.point += objectivePlot.getNbPointsWin();
                System.out.println("Le joueur "+this.getNom()+" a gagne "+objectivePlot.getNbPointsWin()+" points pour avoir realise le pattern "+objectivePlot);
                this.objectives.remove(objectivePlot);
            }
        }
    }

    public void playForGardenerCard(){
        if (this.board.getBoardTiles().size() == 1){
            ArrayList<Coordinate> availableCoordinates = this.board.scanAvailableTilePosition();
            System.out.println(this.board.addTile(new Tile(new Coordinate(availableCoordinates.get(0).getX(), availableCoordinates.get(0).getY()))));
            this.playAction();
        }

        while(this.getNbActions()>0) {
            Tile gardenerPosition = this.board.getGardener().getTile();
            ArrayList<Coordinate> availablePositionsGardener = gardenerPosition.scanAvailableCoordinatesToMove(this.board.getBoardTiles());
            if(availablePositionsGardener.size()==0){
                ArrayList<Coordinate> availableCoordinates = this.board.scanAvailableTilePosition();
                System.out.println(this.board.addTile(new Tile(availableCoordinates.get(0))));
                this.playAction();
            }else{
                System.out.println(this.board.moveGardenerOn(availablePositionsGardener.get(0)));
                this.playAction();
            }
        }
        /* PREVIOUS VERSION -- KEEP IT HERE
        int numberTile = this.board.getBoardTiles().size();
        Tile tileToMove = this.board.getBoardTiles().get(numberTile-1);
        System.out.println(this.board.moveGardenerOn(tileToMove.getCoordinate()));
        this.playAction();

        if(this.getNbActions()==1){
            System.out.println(this.board.moveGardenerOn(tileToMove.getCoordinate()));
            this.playAction();
        }*/

        if(this.focusCard.isValid(this, this.board)){
            this.setPoint(this.getPoint()+this.focusCard.getNbPointsWin());
            ArrayList<ObjectiveInterface> objectifs = this.getObjective();
            objectifs.remove(focusCard);
            this.focusCard = null;
            System.out.println("Objecti jardinier realise");
        }
    }

    public void playForPandaCard(){

        while(this.getNbActions() > 0) {
            Tile positionPanda = this.board.getPanda().getTile();
            ArrayList<Coordinate> availablePositionPanda = positionPanda.scanAvailableCoordinatesToMove(this.board.getBoardTiles());
            if (availablePositionPanda.size() == 0) {
                ArrayList<Coordinate> availablePositions = this.board.scanAvailableTilePosition();
                System.out.println(this.board.addTile(new Tile(availablePositions.get(0))));
            } else {
                boolean pandaMove = false;
                for (Coordinate co : availablePositionPanda) {
                    if (this.board.getTile(co).getBamboo() > 0) {
                        System.out.println(this.board.movePandaOn(co, this));
                        this.playAction();
                        pandaMove = true;
                        break;
                    }
                }
                if (!pandaMove) {
                    /*
                    //old code that only allows to place tiles : may be of use after this
                    ArrayList<Coordinate> availablePositions = this.board.scanAvailableTilePosition();
                    System.out.println(this.board.addTile(new Tile(availablePositions.get(0).getX(),availablePositions.get(0).getY())));
                    this.playAction();
                    */

                    //new code to implement if needed. it will allow the player to move the gardener to grow at least one tile available to the panda
                    //if there is no bamboo available to the panda, the player will try to move the gardener onto a Tile accessible to the panda
                    boolean gardenerMove = false;
                    Tile positionGardener = this.board.getGardener().getTile();
                    ArrayList<Coordinate> availablePositionGardener = positionGardener.scanAvailableCoordinatesToMove(this.board.getBoardTiles());
                    for (int i = 0; i < availablePositionGardener.size(); i++) {    //cycles through all the tiles where the gardener can go

                        //if the gardener can be moved to a tile where the panda can go
                        boolean canGrowBamboo = (availablePositionPanda.contains(availablePositionGardener.get(i)));
                        if (!canGrowBamboo) {   //we look if a neighbours of a tile of the gardener destination is an available panda destination IF canGrowBamboo is not already true (otherwise tests if it can be true even though it is already true)
                            ArrayList<Coordinate> closeNeighbours = availablePositionGardener.get(i).getNeighbourCoordinates(); //gets the neighbours of the tile
                            for (int j = 0; j < closeNeighbours.size(); j++) {
                                if (availablePositionPanda.contains(closeNeighbours.get(j))) {  //if on neighbour of the destination of the gardener is a neighbour of an available position for the panda
                                    Coordinate gardenerDestination = closeNeighbours.get(j);
                                    Coordinate pandaDestination = availablePositionPanda.get(availablePositionPanda.indexOf(gardenerDestination));
                                    if ((this.board.getTile(pandaDestination).getTypeOfTile()) == (this.board.getTile(gardenerDestination).getTypeOfTile())) { //if both the tiles has the same type
                                        canGrowBamboo = true;
                                    }
                                }
                            }
                        }
                        if (canGrowBamboo) {
                            System.out.println(this.board.moveGardenerOn(availablePositionGardener.get(i)));
                            gardenerMove = true;
                            this.playAction();
                        }
                    }
                    if (!gardenerMove) {
                        ArrayList<Coordinate> availableCoordinates = this.board.scanAvailableTilePosition();
                        System.out.println(this.board.addTile(new Tile(availableCoordinates.get(0))));
                        this.playAction();
                    }
                    //end of new code (if it does not fit in the issue just comment out and add to a new issue)
                }
            }
        }
        if (this.focusCard.isValid(this, this.board)){
            ObjectivePanda card = (ObjectivePanda) this.focusCard;
            this.resetNbBamboo(card.getNbToEat());
            this.setPoint(this.focusCard.getNbPointsWin()+this.getPoint());
            this.objectives.remove(focusCard);
            this.focusCard = null;
            System.out.println("Objectif panda realise");
        }
    }


    public void checkBetterCard(){
        ObjectiveInterface card = null;
        int max = -1;
        for(ObjectiveInterface cardObj : objectives){
            if(cardObj.getNbPointsWin() > max){
                max = cardObj.getNbPointsWin();
                card = cardObj;
            }
        }
        this.focusCard = card;
    }

    public void resetNbBamboo(){
        this.nbBamboo = 0;
    }

    private void resetNbBamboo(int value) {
        this.nbBamboo -= value;
    }

    public String addTile(Tile tile){
        return this.board.addTile(tile);
    }

    public ArrayList<ObjectiveInterface> getObjective() {
        return objectives;
    }

    public void setObjectivePlot(ObjectivePlot objective) {
        this.objectives.add(objective);
    }
    public void setObjectiveGardener(ObjectiveGardener objective) {
        this.objectives.add(objective);
    }

    public void setObjectives(ArrayList<ObjectiveInterface> objectives) {
        this.objectives = objectives;
    }

    public int getNbBamboo() {
        return this.nbBamboo;
    }

    public void upNbBamboo(){
        this.nbBamboo++;
    }

    public void pickPandaCard(){
        this.objectives.add(this.board.getPandaCard());
        System.out.println("Le joueur "+this.getNom()+" a pioche une carte Panda!");
        this.playAction();
    }

    public void pickPlotCard(){
        //TODO : make possible to pick other objective than LINE
        ObjectivePlot objectivePlot = this.board.getPlotCard();
        this.objectives.add(objectivePlot);
        System.out.println("Le joueur "+this.getNom()+" a pioche une carte Pattern de type "+objectivePlot.getType()+" et de couleur "+objectivePlot.getColors().get(0)+"!");
        this.playAction();
    }

    public void pickGardenerCard(){
        this.objectives.add(this.board.getGardenerCard());
        System.out.println("Le joueur "+this.getNom()+" a pioche une carte Jardinier!");
        this.playAction();
    }
}
