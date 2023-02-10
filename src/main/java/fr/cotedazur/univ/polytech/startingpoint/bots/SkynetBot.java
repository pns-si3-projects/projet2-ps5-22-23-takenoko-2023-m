package fr.cotedazur.univ.polytech.startingpoint.bots;

import fr.cotedazur.univ.polytech.startingpoint.*;

import java.util.ArrayList;
import java.util.List;

public class SkynetBot extends Bot {
    ObjectiveInterface focusedObjective;
    public SkynetBot(Board board, String nom) {
        super(board, nom);
    }

    @Override
    public void play() {
        super.play();
        this.checkObjectiveToFocus();
        nbActions=2;

        switch (this.board.getDice().getMeteo()){
            case SUN -> this.nbActions++;
            case LIGHTNING -> {
                this.nbActions++;
                movePandaToTheBestLocation();
            }
            case CLOUD -> {
                if(board.getBasinStack().getStack().isEmpty()&&board.getEnclosureStack().getStack().isEmpty()&&board.getFertilizerStack().getStack().isEmpty()){
                    this.nbActions++;
                }
            }
            case QUESTIONMARK -> {
                if(nbTours<4){
                    this.nbIrrigation++;
                }
                else{
                    this.nbActions++;
                }
            }
        }
        focusedObjective.play(this);

        Main.LOGGER.info("\n");
    }

    private void movePandaToTheBestLocation() {
        Main.LOGGER.info("Move panda to the best location");
        Tile bestLocationForPanda = bestLocationForPanda();
        if(bestLocationForPanda!=null){
            Main.LOGGER.info(this.board.movePandaOn(bestLocationForPanda.getCoordinate(), this));
            this.playAction();
        }
        else{
            this.nbActions--;
        }
    }

    private Tile bestLocationForPanda() {
        List<ObjectivePanda> listOfObjectivePanda = getListOfPandaObjective();
        List<Tile> listOfBambooMax = new ArrayList<>();
        Tile yellowMaxTile = getNbBambooMaxOfType(TypeOfTile.YELLOW);
        Tile redMaxTile = getNbBambooMaxOfType(TypeOfTile.RED);
        Tile greenMaxTile = getNbBambooMaxOfType(TypeOfTile.GREEN);
        if(redMaxTile!=null){
            listOfBambooMax.add(redMaxTile);
        }
        if(greenMaxTile!=null){
            listOfBambooMax.add(greenMaxTile);
        }
        if(yellowMaxTile!=null){
            listOfBambooMax.add(yellowMaxTile);
        }
        if(!listOfBambooMax.isEmpty()){
            if(!listOfObjectivePanda.isEmpty()) {
                for (ObjectivePanda objectivePanda : listOfObjectivePanda) {
                    for (Tile tileMax : listOfBambooMax) {
                        if (tileMax.getTypeOfTile().equals(objectivePanda.getTypeOfTile())) {
                            return tileMax;
                        }
                    }
                }
            }
            else{
                Tile tileMax = listOfBambooMax.get(0);
                for(Tile tile : listOfBambooMax){
                    if(tile.getBamboo()>tileMax.getBamboo()){
                        tileMax = tile;
                    }
                }
                return tileMax;
            }
        }
        return null;

    }

    private Tile getNbBambooMaxOfType(TypeOfTile type) {
        Tile tileOfPanda = this.board.getPanda().getTile();
        List<Coordinate> listOfCoordinateAvailable = tileOfPanda.scanAvailableCoordinatesToMove(board.getBoardTiles());
        Tile tileMax = null;
        if(listOfCoordinateAvailable.isEmpty()){
            return null;
        }
        for(Coordinate co : listOfCoordinateAvailable){
            Tile tile = this.board.getTile(co);
            if((tileMax == null || tile.getBamboo()>tileMax.getBamboo()) && type.equals(tile.getTypeOfTile())){
                    tileMax = tile;
            }

        }
        return tileMax;

    }


    public List<ObjectivePanda> getListOfPandaObjective() {
        List<ObjectivePanda> listOfObjectivePanda = new ArrayList<>();
        for(ObjectiveInterface objective : objectives){
            if(objective instanceof ObjectivePanda objectivePanda){
                listOfObjectivePanda.add(objectivePanda);
            }
        }
        return listOfObjectivePanda;
    }
    @Override
    public void playForPandaCard(){
        Main.LOGGER.info("Play for panda card"+nbActions);
        if(focusedObjective instanceof ObjectivePanda objectivePanda) {
            if(nbActions >0) {
                if(board.getDice().getMeteo().equals(Meteo.WIND)){
                    this.movePandaToTheBestLocation();
                }
                this.movePandaToTheBestLocation();
            }
            if(nbActions>0){
                this.playForIrrigation();
            }
        }
    }
    @Override
    public void playForPatternCard(){
        if(focusedObjective instanceof ObjectivePlot objectivePlot) {
            if (nbActions > 1) {
                this.movePandaToTheBestLocation();
            }
            if (nbActions > 0) {
                this.placeTileForPattern(objectivePlot);
            }
            if(nbActions>0){
                this.playForIrrigation();
            }
        }
    }

    private void placeTileForPattern(ObjectivePlot objectivePlot) {
        List<TypeOfTile> colors = objectivePlot.getColors();
        if (this.board.getTileStack().sizeTileStack() > 2) {
            List<Tile> tilesPicked = board.pickThreeTiles();
            Main.LOGGER.info("Le joueur " + this.getNom() + " a pioche les tuiles suivantes :");
            for (Tile tile : tilesPicked) {
                String message = tile.getTypeOfTile().toString();
                Main.LOGGER.info(message);
            }
            boolean isPlaced = false;
            List<Tile> tilesToPutBackInStack = new ArrayList<>();
            for (Tile tile : tilesPicked) {
                if (tile.getTypeOfTile().equals(colors.get(0)) && !isPlaced) {
                    isPlaced = true;
                    String returnMessage = switch (objectivePlot.getPattern().getType()) {
                        case BOOMRANG ->
                                board.addTile(new Tile(board.bestCoordinateForBoomrang(objectivePlot), tile.getTypeOfTile(), TypeOfArrangement.NONE)) + " de type:" + tile.getTypeOfTile();
                        case LINE ->
                                board.addTile(new Tile(board.bestCoordinateForLine(objectivePlot), tile.getTypeOfTile(), TypeOfArrangement.NONE)) + " de type:" + tile.getTypeOfTile();
                        case TRIANGLE ->
                                board.addTile(new Tile(board.bestCoordinateForTriangle(objectivePlot), tile.getTypeOfTile(), TypeOfArrangement.NONE)) + " de type:" + tile.getTypeOfTile();
                        default -> "Aucun type de pattern n'a ete trouve";
                    };
                    Main.LOGGER.info(returnMessage);
                } else {
                    tilesToPutBackInStack.add(tile);
                }
            }
            if (!isPlaced) {
                String message = board.addTile(new Tile(board.scanAvailableTilePosition().get(0), tilesPicked.get(0).getTypeOfTile(), TypeOfArrangement.NONE)) + " de type:" + tilesPicked.get(0).getTypeOfTile();
                Main.LOGGER.info(message);
                board.putBackInTileStack(tilesPicked.get(1));
                board.putBackInTileStack(tilesPicked.get(2));
            } else {
                board.putBackInTileStack(tilesToPutBackInStack.get(0));
                board.putBackInTileStack(tilesToPutBackInStack.get(1));
            }
            this.playAction();
        }
    }
    @Override
    public void playForGardenerCard(){
        if(focusedObjective instanceof ObjectiveGardener objectif) {
            while(getNbActions()>0){
                Tile tileOfGardener = board.getGardener().getTile();
                List<Coordinate> availablePositionsForGarderner = tileOfGardener.scanAvailableCoordinatesToMove(board.getBoardTiles());
                if(availablePositionsForGarderner.isEmpty()){
                    if(board.getTileStack().sizeTileStack()>2){
                        List<Coordinate> availableCoordinatesToPutTile = this.board.scanAvailableTilePosition();
                        List<Tile> tilesPicked = board.pickThreeTiles();
                        Tile bestCard = chooseBetterOf3Tiles(tilesPicked, objectif);
                        Tile toAdd = new Tile(availableCoordinatesToPutTile.get(0),bestCard.getTypeOfTile(),bestCard.getTypeOfArrangement());
                        String message = this.board.addTile(toAdd);
                        Main.LOGGER.info(message);
                        this.playAction();
                    }else{
                        playForIrrigation();
                    }
                }else{
                    ArrayList<Coordinate> availablePositionsForGarderner2 = new ArrayList<>();
                    for(Coordinate co : availablePositionsForGarderner){
                        Tile tile = board.getTile(co);
                        if(tile != null && tile.getTypeOfTile().equals(objectif.getTypeOfTile())){
                            availablePositionsForGarderner2.add(co);
                        }
                    }
                    if(!availablePositionsForGarderner2.isEmpty()){
                        boolean gardenerMooved = false;
                        for(Coordinate co : availablePositionsForGarderner2){
                            Tile tile = board.getTile(co);
                            if(tile != null && tile.isIrrigated()){
                                String message = board.moveGardenerOn(co);
                                Main.LOGGER.info(message);
                                this.playAction();
                                gardenerMooved = true;
                                break;
                            }
                        }
                        if(!gardenerMooved){
                            List<Irrigation> listOfIrrigation = board.getLegalIrrigationPlacement();
                            boolean isIrrigationPosed = false;
                            for(Coordinate co : availablePositionsForGarderner){
                                for(Irrigation i : listOfIrrigation){
                                    if(i.getCoordinates().get(0).equals(co) || i.getCoordinates().get(1).equals(co)){
                                        Tile t1 = board.getTile(i.getCoordinates().get(0));
                                        Tile t2 = board.getTile(i.getCoordinates().get(1));
                                        if(t1 != null && t2 != null){
                                            isIrrigationPosed = board.addIrrigation(i);
                                            if(isIrrigationPosed){
                                                this.playAction();
                                                break;
                                            }
                                        }
                                    }
                                }
                                if(isIrrigationPosed) break;
                            }
                            if(!isIrrigationPosed){
                                for(Irrigation irrigation : listOfIrrigation){
                                    Tile t1 = board.getTile(irrigation.getCoordinates().get(0));
                                    Tile t2 = board.getTile(irrigation.getCoordinates().get(1));
                                    if(t1 != null && t2 != null && getNbActions() > 0){
                                        boolean verif = board.addIrrigation(irrigation);
                                        if(verif){
                                            this.playAction();
                                            break;
                                        }
                                    }
                                }
                                if(board.getTileStack().sizeTileStack()>2){
                                    List<Coordinate> availableCoordinatesToPutTile = this.board.scanAvailableTilePosition();
                                    List<Tile> tilesPicked = board.pickThreeTiles();
                                    Tile bestCard = chooseBetterOf3Tiles(tilesPicked,objectif);
                                    Tile toAdd = new Tile(availableCoordinatesToPutTile.get(0),bestCard.getTypeOfTile(),bestCard.getTypeOfArrangement());
                                    String message = this.board.addTile(toAdd);
                                    Main.LOGGER.info(message);
                                    this.playAction();
                                }else{

                                    playForIrrigation();
                                }
                            }
                        }else{
                            if(board.getTileStack().sizeTileStack()>2){
                                List<Coordinate> availableCoordinatesToPutTile = this.board.scanAvailableTilePosition();
                                List<Tile> tilesPicked = board.pickThreeTiles();
                                Tile bestCard = chooseBetterOf3Tiles(tilesPicked, objectif);
                                Tile toAdd = new Tile(availableCoordinatesToPutTile.get(0),bestCard.getTypeOfTile(),bestCard.getTypeOfArrangement());
                                String message = this.board.addTile(toAdd);
                                Main.LOGGER.info(message);
                                this.playAction();
                            }else{

                                playForIrrigation();
                            }
                        }
                    }else{
                        if(board.getTileStack().sizeTileStack()>2){
                            List<Coordinate> availableCoordinatesToPutTile = this.board.scanAvailableTilePosition();
                            List<Tile> tilesPicked = board.pickThreeTiles();
                            Tile bestCard = chooseBetterOf3Tiles(tilesPicked, objectif);
                            Tile toAdd = new Tile(availableCoordinatesToPutTile.get(0),bestCard.getTypeOfTile(),bestCard.getTypeOfArrangement());
                            String message = this.board.addTile(toAdd);
                            Main.LOGGER.info(message);
                            this.playAction();
                        }else{
                            playForIrrigation();
                        }
                    }
                }
            }
        }
    }

    public Tile chooseBetterOf3Tiles(List<Tile> threeCards, ObjectiveGardener objectif){
        if(threeCards.get(0).getTypeOfTile().equals(objectif.getTypeOfTile())){
            board.getTileStack().addTile(threeCards.get(1));
            board.getTileStack().addTile(threeCards.get(2));
            String message = Integer.toString(board.getTileStack().sizeTileStack());
            Main.LOGGER.info(message);
            return threeCards.get(0);
        }

        if(threeCards.get(1).getTypeOfTile().equals(objectif.getTypeOfTile())){
            board.getTileStack().addTile(threeCards.get(0));
            board.getTileStack().addTile(threeCards.get(2));
            String message = Integer.toString(board.getTileStack().sizeTileStack());
            Main.LOGGER.info(message);
            return threeCards.get(1);
        }

        if(threeCards.get(2).getTypeOfTile().equals(objectif.getTypeOfTile())){
            board.getTileStack().addTile(threeCards.get(0));
            board.getTileStack().addTile(threeCards.get(1));
            String message = Integer.toString(board.getTileStack().sizeTileStack());
            Main.LOGGER.info(message);
            return threeCards.get(2);
        }

        board.getTileStack().addTile(threeCards.get(1));
        board.getTileStack().addTile(threeCards.get(2));
        String message = Integer.toString(board.getTileStack().sizeTileStack());
        Main.LOGGER.info(message);
        return threeCards.get(0);

    }

    private void playForIrrigation(){
        List<Irrigation> listeIrrigations = board.getLegalIrrigationPlacement();
        for(Irrigation irrigation : listeIrrigations){
            boolean verif = board.addIrrigation(irrigation);
            this.playAction();
            if(verif){
                break;
            }else{
                this.playAction();
            }
        }
    }

    private void checkObjectiveToFocus() {
        if(this.getObjective().size()<5){
            if(board.getPatternBoard().getPatternBoardList().size()>6&&!board.isPlotCardEmpty()){
                pickPlotCard();
            }
            else{
                pickPandaCard();
            }
            nbIrrigation++;
            nbActions--;
        }
        focusedObjective = chooseObjectiveToFocus();


    }

    private ObjectiveInterface chooseObjectiveToFocus() {
        ObjectiveInterface objectiveToFocus = null;
        int max = 0;
        for(ObjectiveInterface objective : objectives){
            Main.LOGGER.info("objective: "+objective.getType());
            objective.setComplexity(checkObjectiveComplexity(objective));
            Main.LOGGER.info("objective: "+objective+" complexity: "+objective.getComplexity());
            if(objective.getNbPointsWin()>max){
                if(objectiveToFocus==null || (objectiveToFocus.getComplexity()-1>objective.getComplexity())){
                    max = objective.getNbPointsWin();
                    objectiveToFocus = objective;
                }
            }
            else if(objectiveToFocus != null&&objectiveToFocus.getComplexity()>objective.getComplexity()){
                max = objective.getNbPointsWin();
                objectiveToFocus = objective;
            }
        }
        return objectiveToFocus;
    }

    private int checkObjectiveComplexity(ObjectiveInterface objective) {
        if(objective instanceof ObjectivePlot objectivePlot){
            return checkPlotObjectiveComplexity(objectivePlot);
        }
        else if(objective instanceof ObjectivePanda objectivePanda){
            return checkPandaObjectiveComplexity(objectivePanda);
        }
        else if(objective instanceof ObjectiveGardener objectiveGardener){
            return checkGardenerObjectiveComplexity(objectiveGardener);
        }
        return 5;
    }

    private int checkGardenerObjectiveComplexity(ObjectiveGardener objectiveGardener) {
        List<Tile> listOfTileOfSameColorInBoard = this.getBoardTileOfType(objectiveGardener.getTypeOfTile());
        for(Tile tile : listOfTileOfSameColorInBoard){
            //case where only one bamboo is missing to complete the objective
            if(tile.getBamboo()-1 == objectiveGardener.getNbBambooRequired()){
                //si il suffit juste d'ajouter un bambou
                if(tile.getTypeOfTile() == objectiveGardener.getTypeOfTile()){
                    return 1;
                }
                //si il faut ajouter un bambou et poser un arrangement
                else if(this.getListArrangement().contains(objectiveGardener.getTypeOfArrangement())){
                    return 3;
                }
                //si il faut attendre d'avoir un arrangement pour pouvoir compléter l'objectif
                else{
                    return 5;
                }
            }
            //si il y a le bon nombre de bamboo mais qu'il n'y a pas le bon arrangement
            else if(tile.getBamboo() == objectiveGardener.getNbBambooRequired()){
                if(this.getListArrangement().contains(objectiveGardener.getTypeOfArrangement())){
                    return 1;
                }
                else{
                    return 5;
                }
            }
            else{
                return 5;
            }
        }
        return 5;
    }

    private int checkPandaObjectiveComplexity(ObjectivePanda objectivePanda) {
        TypeOfTile type = objectivePanda.getTypeOfTile();
        int numberOfBambou = objectivePanda.getNbToEat();
        int maxNumberOfBambouEatableOnBoardOnATile = checkMaxNumberOfBambouEatableOnBoardOnATile(type);
        switch (type){
            case GREEN:
                if(numberOfBambou<nbBambooGreen+maxNumberOfBambouEatableOnBoardOnATile){
                    return 1;
                }
                else{
                    return 3;
                }
            case YELLOW:
                if(numberOfBambou<nbBambooYellow+maxNumberOfBambouEatableOnBoardOnATile){
                    return 1;
                }
                else{
                    return 3;
                }
            case RED:
                if(numberOfBambou<nbBambooRed+maxNumberOfBambouEatableOnBoardOnATile){
                    return 1;
                }
                else{
                    return 3;
                }
        }
        return 4;
    }

    private int checkPlotObjectiveComplexity(ObjectivePlot objectivePlot) {
        List<Tile> listOfTileOfSameColorInBoard = this.getBoardTileOfType(objectivePlot.getColors().get(0));

        int numberOfTileWhichHaveSameColorAsNeighbour = getNumberOfTileWhichHaveSameColorAsNeighbour(listOfTileOfSameColorInBoard);
        if ( numberOfTileWhichHaveSameColorAsNeighbour > 0 && numberOfTileWhichHaveSameColorAsNeighbour<=2) {
            return 2;
        }
        else if(numberOfTileWhichHaveSameColorAsNeighbour > 2){
            return 1;
        }
        return 4;
    }

    private int checkMaxNumberOfBambouEatableOnBoardOnATile(TypeOfTile type) {
        int max = 0;
        for(Tile tile : board.getBoardTiles()){
            if(tile.getTypeOfTile()==type){
                int numberOfBambouEatableOnTile = tile.getBamboo();
                if(numberOfBambouEatableOnTile>max && !tile.getTypeOfArrangement().equals(TypeOfArrangement.ENCLOSURE)){
                    max = numberOfBambouEatableOnTile;
                }
            }
        }
        return max;
    }

    private int getNumberOfTileWhichHaveSameColorAsNeighbour(List<Tile> listOfTileOfSameColorInBoard) {
        int numberOfTileWhichHaveSameColorAsNeighboor = 0;
        for(Tile tile : listOfTileOfSameColorInBoard){
            for(Tile possibleNeighboor : listOfTileOfSameColorInBoard){
                if(tile.isNeighbour(possibleNeighboor)){
                    numberOfTileWhichHaveSameColorAsNeighboor++;
                }
            }
        }
        return numberOfTileWhichHaveSameColorAsNeighboor;
    }

    /**
     * Find in the board all the tiles of a specific type
     * @param color the type of tile we want to find
     * @return an ArrayList of tiles of the specific type
     */
    private List<Tile> getBoardTileOfType(TypeOfTile color) {
        List<Tile> boardTilesOfType = new ArrayList<>();
        for(Tile tile : board.getBoardTiles()){
            if(color.equals(tile.getTypeOfTile())){
                boardTilesOfType.add(tile);
            }
        }
        return boardTilesOfType;
    }
    private void playGardenerForSpecificTile(TypeOfTile type, Tile tileOfPanda) {
        List<Tile> tileList = board.getBoardTiles();
        List<Coordinate> coordinateToMoovePanda = tileOfPanda.scanAvailableCoordinatesToMove(tileList);
        List<Coordinate> coordinateToMooveGardener = board.getGardener().getTile().scanAvailableCoordinatesToMove(tileList);
        List<Irrigation> availablePositionsIrrigation = board.getLegalIrrigationPlacement();
        for (Coordinate co : coordinateToMooveGardener) {
            if (coordinateToMoovePanda.contains(co)) {
                if (board.getTile(co).isIrrigated() && board.getTile(co).getTypeOfTile().equals(type)) {
                    String message = board.moveGardenerOn(co);
                    Main.LOGGER.info(message);
                    this.playAction();
                    break;
                } else {
                    for (Irrigation irrigation : availablePositionsIrrigation) {
                        if (irrigation.getCoordinates().get(0).equals(co) || irrigation.getCoordinates().get(1).equals(co)) {
                            board.getTile(co);
                        }
                    }
                }
            }
        }
    }

}
