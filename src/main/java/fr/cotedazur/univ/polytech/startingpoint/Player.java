package fr.cotedazur.univ.polytech.startingpoint;
import java.util.ArrayList;
import java.util.List;

public class Player {
    private int point = 0;
    private String nom;
    private final Board board;
    private  int nbBambooGreen = 0;
    private int nbBambooYellow = 0;
    private int nbBambooRed = 0;
    private int nbActions = 2;

    private int nbTours = 1;

    public List<TypeOfArrangement> getListArrangement() {
        return listArrangement;
    }

    private List<TypeOfArrangement> listArrangement = new ArrayList<TypeOfArrangement>();

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

    public void resetNbActions() {
        if (this.board.getDice().getMeteo() == Meteo.SUN) {
            this.nbActions = 3;
        } else {
            this.nbActions = 2;
        }
    }



    public void play(){
        if(this.nbTours >1){
            this.board.getDice().randomMeteo();
            while(this.board.getDice().getMeteo()==Meteo.NONE || this.board.getDice().getMeteo()==Meteo.QUESTIONMARK){
                this.board.getDice().randomMeteo();
            }
        }
        System.out.println(" la meteo est " + this.board.getDice().getMeteo());
        this.resetNbActions();
        if (this.focusCard == null){
            checkBetterCard();
        }
        if(this.focusCard instanceof ObjectiveGardener){
            this.playForGardenerCard();
        }else if(this.focusCard instanceof ObjectivePanda){
            ObjectivePanda objectivePanda = (ObjectivePanda) this.focusCard;
            if(this.board.getDice().getMeteo()==Meteo.LIGHTNING){
                for(Tile tile : this.board.getBoardTiles()){
                    if(tile.getTypeOfTile().equals(objectivePanda.getTypeOfTile())){
                        this.board.getPanda().moveOn(tile.getCoordinate(),this);
                        this.playAction();
                        break;
                    }
                }
            }
            else{
                this.playForPandaCard();
            }

        }else{
            this.playForPatternCard();
        }
        this.checkPatternOnBoard();
        this.nbTours ++;
        System.out.println();
    }

    public void playForPatternCard(){
        System.out.println("la focus card = " +this.focusCard);
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
        //Check if there is only 1 tile (the central one) on the board
        if (this.board.getBoardTiles().size() == 1){
            List<Coordinate> availableCoordinates = this.board.scanAvailableTilePosition();
            List<Tile> tilesPicked = board.pickThreeTiles();
            Tile toAdd = chooseBetterOf3Tiles(tilesPicked);
            toAdd.setCoordinate(availableCoordinates.get(0));
            System.out.println(this.board.addTile(toAdd));
            this.playAction();
        }

        while(this.getNbActions()>0) {
            Tile gardenerPosition = this.board.getGardener().getTile();
            List<Coordinate> availablePositionsGardener = gardenerPosition.scanAvailableCoordinatesToMove(this.board.getBoardTiles());
            if(availablePositionsGardener.size()==0){ //If we can't moove the gardener on any tile
                List<Coordinate> availableCoordinates = this.board.scanAvailableTilePosition();
                List<Tile> tilesPicked = board.pickThreeTiles();
                Tile toAdd = chooseBetterOf3Tiles(tilesPicked);
                toAdd.setCoordinate(availableCoordinates.get(0));
                System.out.println(this.board.addTile(toAdd));
                this.playAction();
            }else{
                boolean moved = false;
                for(Coordinate co : availablePositionsGardener){ //We'll check if one of the availableTile is the same color as our focus card
                    Tile potentialTile = this.board.getTile(co);
                    ObjectiveGardener objectiveGardener = (ObjectiveGardener) this.focusCard;
                    if(potentialTile.getTypeOfTile().equals(objectiveGardener.getTypeOfTile())){ //It's the same color so we moove
                        System.out.println(this.board.moveGardenerOn(availablePositionsGardener.get(0)));
                        this.playAction();
                        moved = true;
                        break;
                    }
                }
                if(!moved){ //If all of the tile's color != focus card's color so we just pick and place another tile
                    List<Coordinate> availableCoordinates = this.board.scanAvailableTilePosition();
                    List<Tile> tilesPicked = board.pickThreeTiles();
                    Tile toAdd = chooseBetterOf3Tiles(tilesPicked);
                    toAdd.setCoordinate(availableCoordinates.get(0));
                    System.out.println(this.board.addTile(toAdd));
                    this.playAction();
                }

            }
        }

        if(this.focusCard.isValid(this, this.board)){
            this.setPoint(this.getPoint()+this.focusCard.getNbPointsWin());
            List<ObjectiveInterface> objectifs = this.getObjective();
            objectifs.remove(focusCard);
            this.focusCard = null;
            System.out.println("Objecti jardinier realise");
        }
    }

    public void playForPandaCard(){

        while(this.getNbActions() > 0) {
            Tile positionPanda = this.board.getPanda().getTile();
            List<Coordinate> availablePositionPanda = positionPanda.scanAvailableCoordinatesToMove(this.board.getBoardTiles());
            if (availablePositionPanda.size() == 0) {
                List<Coordinate> availablePositions = this.board.scanAvailableTilePosition();
                List<Tile> tilesPicked = board.pickThreeTiles();
                Tile toAdd = chooseBetterOf3Tiles(tilesPicked);
                toAdd.setCoordinate(availablePositions.get(0));
                System.out.println(this.board.addTile(toAdd));
                this.playAction();
            } else {
                boolean pandaMove = false;
                for (Coordinate co : availablePositionPanda) {
                    ObjectivePanda objectivePanda = (ObjectivePanda) this.focusCard;
                    if (this.board.getTile(co).getBamboo() > 0 && this.board.getTile(co).getTypeOfTile().equals(objectivePanda.getTypeOfTile())) {
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
                            System.out.println(this.board.moveGardenerOn(availablePositionGardener.get(i)));
                            gardenerMove = true;
                            this.playAction();
                        }
                    }
                    if (!gardenerMove) {
                        List<Coordinate> availableCoordinates = this.board.scanAvailableTilePosition();
                        List<Tile> tilesPicked = board.pickThreeTiles();
                        Tile toAdd = chooseBetterOf3Tiles(tilesPicked);
                        toAdd.setCoordinate(availableCoordinates.get(0));
                        System.out.println(this.board.addTile(toAdd));
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

    public void resetNbBamboo(TypeOfTile type){
        if(type == TypeOfTile.RED) this.nbBambooRed = 0;
        if(type == TypeOfTile.YELLOW) this.nbBambooYellow = 0;
        if(type == TypeOfTile.GREEN) this.nbBambooGreen = 0;
    }

    private void resetNbBamboo(int value, TypeOfTile type) {
        if(type == TypeOfTile.RED) this.nbBambooRed = value;
        if(type == TypeOfTile.YELLOW) this.nbBambooYellow = value;
        if(type == TypeOfTile.GREEN) this.nbBambooGreen = value;
    }

    public String addTile(Tile tile){
        return this.board.addTile(tile);
    }

    public List<ObjectiveInterface> getObjective() {
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

    public void pickArrangement(TypeOfArrangement t){

            switch (t){
                case NONE:
                    throw new IllegalArgumentException("il faut choisir un type valide");
                case FERTILIZER:
                    if(this.board.getFertilizerStack().getStack().size()>0){
                        this.getListArrangement().add(this.board.getFertilizerStack().pick(t));
                    }
                    break;
                case BASIN:
                    if(this.board.getBasinStack().getStack().size()>0){
                        this.getListArrangement().add(this.board.getBasinStack().pick(t));

                    }
                    break;
                case ENCLOSURE:
                    if(this.board.getEnclosureStack().getStack().size()>0){
                        this.getListArrangement().add(this.board.getEnclosureStack().pick(t));
                    }
                    break;
            }

    }
    public void setArrangement(Tile tile, TypeOfArrangement t){
        if (tile.getTypeOfArrangement()== TypeOfArrangement.NONE&&this.getListArrangement().contains(t)){
            tile.setTypeOfArrangement(t);
        }
    }

    public int getNbBambooGreen() {
        return nbBambooGreen;
    }

    public int getNbBambooYellow() {
        return nbBambooYellow;
    }

    public int getNbBambooRed() {
        return nbBambooRed;
    }

    public void upBambooGreen() {
        this.nbBambooGreen++;
    }

    public void upBambooYellow() {
        this.nbBambooYellow++;
    }

    public void upBambooRed() {
        this.nbBambooRed++;
    }

    public int getNbBamboo(TypeOfTile type) {
        if(type == TypeOfTile.RED) return this.nbBambooRed;
        if(type == TypeOfTile.YELLOW) return this.nbBambooYellow;
        if(type == TypeOfTile.GREEN) return this.nbBambooGreen;
        return 0;
    }

    public void upNbBamboo(TypeOfTile type) {
        if(type == TypeOfTile.RED){
            this.upBambooRed();
        }else if (type == TypeOfTile.YELLOW) {
            this.upBambooYellow();
        }else if (type == TypeOfTile.GREEN) {
            this.upBambooGreen();
        }
    }

    public void pickPandaCard(){
        ObjectivePanda objectivePanda = this.board.getPandaCard();
        this.objectives.add(objectivePanda);
        System.out.println("Le joueur "+this.getNom()+" a pioche une carte Panda et qui vaut "+objectivePanda.getNbPointsWin()+" points");
        this.playAction();
    }

    public void pickPlotCard(){
        //TODO : make possible to pick other objective than LINE
        ObjectivePlot objectivePlot = this.board.getPlotCard();
        this.objectives.add(objectivePlot);
        System.out.println("Le joueur "+this.getNom()+" a pioche une carte Pattern de type "+objectivePlot.getType()+" et de couleur "+objectivePlot.getColors().get(0)+" et qui vaut "+objectivePlot.getNbPointsWin()+" points");
        this.playAction();
    }

    public void pickGardenerCard(){
        ObjectiveGardener objectiveGardener = this.board.getGardenerCard();
        this.objectives.add(objectiveGardener);
        System.out.println("Le joueur "+this.getNom()+" a pioche une carte Jardinier et qui vaut "+objectiveGardener.getNbPointsWin());
        this.playAction();
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
