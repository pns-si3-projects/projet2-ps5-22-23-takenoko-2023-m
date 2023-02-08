
package fr.cotedazur.univ.polytech.startingpoint.bots;

import fr.cotedazur.univ.polytech.startingpoint.*;


import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


public class PrimaryBot extends Bot {

    private ObjectiveInterface focusCard = null;

    public PrimaryBot(Board board, String name) {
        super(board, name);
    }

    @Override
    public void play(){
        if(this.nbTours >1){
            this.board.getDice().randomMeteo();
            while(this.board.getDice().getMeteo()==Meteo.NONE || this.board.getDice().getMeteo()==Meteo.QUESTIONMARK){
                this.board.getDice().randomMeteo();
            }
        }
        Main.LOGGER.info("La météo est  "  + this.board.getDice().getMeteo());


        this.resetNbActions();
        if (this.focusCard == null){
            checkBetterCard();
        }
        this.focusCard.play(this);
        this.checkPatternOnBoard();
        this.nbTours ++;
        Main.LOGGER.info("\n");
    }

    public void playForPatternCard(){
        Main.LOGGER.info("la focus card = " +this.focusCard);
        ObjectivePlot objectivePlot = (ObjectivePlot) this.focusCard;
        List<TypeOfTile> colors = objectivePlot.getColors();
        if(this.board.getTileStack().sizeTileStack()>2){
            System.out.println(this.board.getTileStack().sizeTileStack());
            List<Tile> tilesPicked = board.pickThreeTiles();
            Main.LOGGER.info("Le joueur " +this.getNom() +" a pioche les tuiles suivantes :");
            for(Tile tile : tilesPicked){
                String message = tile.getTypeOfTile().toString();
                Main.LOGGER.info(message);
            }
            boolean isPlaced = false;
            List<Tile> tilesToPutBackInStack = new ArrayList<>();
            for(Tile tile : tilesPicked){
                if(tile.getTypeOfTile().equals(colors.get(0))&&!isPlaced){
                    isPlaced = true;
                    String returnMessage;
                    switch (objectivePlot.getPattern().getType()) {
                        case BOOMRANG:
                            returnMessage = board.addTile(new Tile(board.bestCoordinateForBoomrang(objectivePlot), tile.getTypeOfTile())) + " de type:" + tile.getTypeOfTile();
                            break;
                        case LINE:
                            returnMessage = board.addTile(new Tile(board.bestCoordinateForLine(objectivePlot), tile.getTypeOfTile())) + " de type:" + tile.getTypeOfTile();
                            break;
                        case TRIANGLE:
                            returnMessage = board.addTile(new Tile(board.bestCoordinateForTriangle(objectivePlot), tile.getTypeOfTile())) + " de type:" + tile.getTypeOfTile();
                            break;
                        default:
                            returnMessage = "Aucun type de pattern n'a ete trouve";
                            break;
                    }
                    String ret = board.addTile(new Tile(board.bestCoordinateForLine(objectivePlot),tile.getTypeOfTile()))+ " de type:"+tile.getTypeOfTile();
                    Main.LOGGER.info(ret);
                }
                else{
                    tilesToPutBackInStack.add(tile);
                }
                if(board.getDice().getMeteo()!=Meteo.RAIN){
                    this.playAction();
                }
                else{
                    board.getDice().setMeteo(Meteo.NONE);
                }
            }
            if(!isPlaced){
                String message = board.addTile(new Tile(board.scanAvailableTilePosition().get(0),tilesPicked.get(0).getTypeOfTile()))+ " de type:"+tilesPicked.get(0).getTypeOfTile();
                Main.LOGGER.info(message);
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
            if(board.getPatternBoard().getPatternBoardList().contains(objectivePlot.getPattern())){
                this.point += objectivePlot.getNbPointsWin();
                String message = "Le joueur "+this.getNom()+" a gagne "+objectivePlot.getNbPointsWin()+" points pour avoir realise le pattern "+objectivePlot;
                Main.LOGGER.info(message);
                this.objectives.remove(objectivePlot);
            }
        }
    }

    public void playForGardenerCard(){
        //Check if there is only 1 tile (the central one) on the board
        if (this.board.getBoardTiles().size() == 1){
            List<Coordinate> availableCoordinates = this.board.scanAvailableTilePosition();
            List<Tile> tilesPicked = board.pickThreeTiles();
            Tile bestCard = chooseBetterOf3Tiles(tilesPicked);
            Tile toAdd = new Tile(availableCoordinates.get(0), bestCard.getTypeOfTile(), bestCard.getTypeOfArrangement());
            String message = this.board.addTile(toAdd);
            Main.LOGGER.info(message);
            this.playAction();
        }

        while(this.getNbActions()>0) {
            Tile gardenerPosition = this.board.getGardener().getTile();
            List<Coordinate> availablePositionsGardener = gardenerPosition.scanAvailableCoordinatesToMove(this.board.getBoardTiles());
            if(availablePositionsGardener.size()==0){ //If we can't moove the gardener on any tile
                List<Coordinate> availableCoordinates = this.board.scanAvailableTilePosition();
                List<Tile> tilesPicked = board.pickThreeTiles();
                Tile bestCard = chooseBetterOf3Tiles(tilesPicked);
                Tile toAdd = new Tile(availableCoordinates.get(0), bestCard.getTypeOfTile(), bestCard.getTypeOfArrangement());
                String message = this.board.addTile(toAdd);
                Main.LOGGER.info(message);
                this.playAction();
            }else{
                boolean moved = false;
                for(Coordinate co : availablePositionsGardener){ //We'll check if one of the availableTile is the same color as our focus card
                    Tile potentialTile = this.board.getTile(co);
                    ObjectiveGardener objectiveGardener = (ObjectiveGardener) this.focusCard;
                    if(potentialTile.getTypeOfTile().equals(objectiveGardener.getTypeOfTile())){ //It's the same color so we moove
                        String message = this.board.moveGardenerOn(availablePositionsGardener.get(0));
                        Main.LOGGER.info(message);
                        this.playAction();
                        moved = true;
                        break;
                    }
                }
                if(!moved && this.board.getTileStack().sizeTileStack()>2){ //If all of the tile's color != focus card's color so we just pick and place another tile
                    List<Coordinate> availableCoordinates = this.board.scanAvailableTilePosition();
                    List<Tile> tilesPicked = board.pickThreeTiles();
                    Tile bestCard = chooseBetterOf3Tiles(tilesPicked);
                    Tile toAdd = new Tile(availableCoordinates.get(0), bestCard.getTypeOfTile(), bestCard.getTypeOfArrangement());
                    String message = this.board.addTile(toAdd);
                    Main.LOGGER.info(message);
                    this.playAction();
                }
                else if(!moved && this.board.getTileStack().sizeTileStack()<=2 && this.board.getTileStack().sizeTileStack() >0){ //If we can't pick 3 tiles because there is only 2 left in the stack
                    List<Coordinate> availableCoordinates = this.board.scanAvailableTilePosition();
                    Tile tilePicked = board.getTileStack().randomPick();

                    //tilePicked.setCoordinate(availableCoordinates.get(0));
                    Tile toAdd = new Tile(availableCoordinates.get(0), tilePicked.getTypeOfTile(), tilePicked.getTypeOfArrangement());
                    String message = this.board.addTile(toAdd);
                    Main.LOGGER.info(message);
                    this.playAction();
                }
                else{
                    this.playAction();
                }

            }
        }

        if(this.focusCard.isValid(this, this.board)){
            this.setPoint(this.getPoint()+this.focusCard.getNbPointsWin());
            List<ObjectiveInterface> objectifs = this.getObjective();
            objectifs.remove(focusCard);
            this.focusCard = null;
            Main.LOGGER.info("Objectif jardinier realise");
        }
    }

    public void playForPandaCard(){
        if(this.board.getDice().getMeteo()==Meteo.LIGHTNING){
            for(Tile tile : this.board.getBoardTiles()){
                ObjectivePanda objectivePanda = (ObjectivePanda) this.focusCard;
                if(tile.getTypeOfTile().equals(objectivePanda.getTypeOfTile())){
                    this.board.getPanda().moveOn(tile.getCoordinate(),this);
                    this.playAction();
                    break;
                }
            }
        }
        else{
            while(this.getNbActions() > 0) {
                Tile positionPanda = this.board.getPanda().getTile();
                List<Coordinate> availablePositionPanda = positionPanda.scanAvailableCoordinatesToMove(this.board.getBoardTiles());
                if (availablePositionPanda.size() == 0) {
                    List<Coordinate> availablePositions = this.board.scanAvailableTilePosition();
                    List<Tile> tilesPicked = board.pickThreeTiles();
                    Tile bestCard = chooseBetterOf3Tiles(tilesPicked);
                    Tile toAdd = new Tile(availablePositions.get(0), bestCard.getTypeOfTile(), bestCard.getTypeOfArrangement());
                    String message = this.board.addTile(toAdd);
                    Main.LOGGER.info(message);
                    this.playAction();
                } else {
                    boolean pandaMove = false;
                    for (Coordinate co : availablePositionPanda) {
                        ObjectivePanda objectivePanda = (ObjectivePanda) this.focusCard;
                        if (this.board.getTile(co).getBamboo() > 0 && this.board.getTile(co).getTypeOfTile().equals(objectivePanda.getTypeOfTile())) {
                            String message = this.board.movePandaOn(co, this);
                            Main.LOGGER.info(message);
                            this.playAction();
                            pandaMove = true;
                            break;
                        }
                    }
                    if (!pandaMove) {

                        //if there is no bamboo available to the panda, the player will try to move the gardener onto a Tile accessible to the panda
                        boolean gardenerMove = false;
                        Tile positionGardener = this.board.getGardener().getTile();
                        List<Coordinate> availablePositionGardener = positionGardener.scanAvailableCoordinatesToMove(this.board.getBoardTiles());
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
                                String message = this.board.moveGardenerOn(availablePositionGardener.get(i));
                                Main.LOGGER.info(message);
                                gardenerMove = true;
                                this.playAction();
                            }
                        }
                        if (!gardenerMove) {
                            List<Coordinate> availableCoordinates = this.board.scanAvailableTilePosition();
                            List<Tile> tilesPicked = board.pickThreeTiles();
                            Tile bestCard = chooseBetterOf3Tiles(tilesPicked);
                            Tile toAdd = new Tile(availableCoordinates.get(0), bestCard.getTypeOfTile(), bestCard.getTypeOfArrangement());
                            String message = this.board.addTile(toAdd);
                            Main.LOGGER.info(message);
                            this.playAction();
                        }
                        //end of new code (if it does not fit in the issue just comment out and add to a new issue)
                    }
                }
            }
            if (this.focusCard.isValid(this, this.board)){
                ObjectivePanda card = (ObjectivePanda) this.focusCard;
                this.resetNbBamboo(card.getNbToEat(),card.getTypeOfTile());
                this.setPoint(this.focusCard.getNbPointsWin()+this.getPoint());
                this.objectives.remove(focusCard);
                this.focusCard = null;
                Main.LOGGER.info("Objectif panda realise");
            }
        }

    }


    public void checkBetterCard(){
        ObjectiveInterface card = null;
        int max = -1;
        for(ObjectiveInterface cardObj : objectives){
            if(cardObj.getNbPointsWin() > max){
                if(cardObj instanceof ObjectivePlot && this.board.getTileStack().sizeTileStack()==0){

                }
                else {
                    max = cardObj.getNbPointsWin();
                    card = cardObj;
                }
            }
        }
        //Main.LOGGER.severe("La carte la plus interessante est : " + card.toString() +" pour " +this.getNom());
        this.focusCard = card;
    }

    public Tile chooseBetterOf3Tiles(List<Tile> tiles){
        if(tiles.get(0).getTypeOfTile().equals(focusCard.getType())) {
            Tile ret = tiles.get(0);
            tiles.remove(0);
            this.board.putBackInTileStack(tiles.get(0));
            this.board.putBackInTileStack(tiles.get(1));
            return ret;
        }else if(tiles.get(1).getTypeOfTile().equals(focusCard.getType())) {
            Tile ret = tiles.get(1);
            tiles.remove(1);
            this.board.putBackInTileStack(tiles.get(0));
            this.board.putBackInTileStack(tiles.get(1));
            return ret;
        }else if(tiles.get(2).getTypeOfTile().equals(focusCard.getType())) {
            Tile ret = tiles.get(2);
            tiles.remove(2);
            this.board.putBackInTileStack(tiles.get(0));
            this.board.putBackInTileStack(tiles.get(1));
            return ret;
        }else{
            Tile ret = tiles.get(0);
            tiles.remove(0);
            this.board.putBackInTileStack(tiles.get(0)); this.board.putBackInTileStack(tiles.get(1));
            return ret;
        }
    }

    public void setFocusCard(ObjectiveInterface focusCard) {
        this.focusCard = focusCard;
    }


}
