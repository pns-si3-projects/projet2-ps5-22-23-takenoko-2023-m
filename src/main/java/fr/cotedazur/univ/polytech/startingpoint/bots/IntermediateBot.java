package fr.cotedazur.univ.polytech.startingpoint.bots;
import fr.cotedazur.univ.polytech.startingpoint.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * This class is a specific bot which is the better one
 */
public class IntermediateBot extends Bot {

    /**
     * The constructor of the IntermediateBot
     * @param board The board of the bot
     * @param nom The name of the bot
     */
    public IntermediateBot(Board board, String nom) {
        super(board, nom);
    }

    /**
     * The main method of the bot, it's call to make the bot play
     */
    public void play(){
        super.play();
        this.objectives.sort((o1, o2) -> o2.getNbPointsWin() - o1.getNbPointsWin());
        switch(this.board.getDice().getMeteo()){
                case SUN -> this.upNbActions();
                case LIGHTNING -> playLigntningDice();
                case CLOUD -> playCloudDice();
                case QUESTIONMARK -> playLigntningDice(); //A revoir potentiellement

                default -> Main.LOGGER.info("Météo non prise en compte");
            }

        this.objectives.get(0).play(this);
        ArrayList<ObjectiveInterface> toSuppress = new ArrayList<>();
        for(int i =0; i!=this.objectives.size(); i++){
            if(this.objectives.get(i).isValid(this, this.board)){
                setPoint(getPoint()+this.objectives.get(i).getNbPointsWin());
                upNbObjectifsRealises();
                toSuppress.add(this.objectives.get(i));
                Main.LOGGER.info("Objectif réalisé par "+getNom());
            }

        }
        if(this.objectives.size()<3){
            if(!board.getStackGardener().getStack().isEmpty()){
                this.pickGardenerCard();
            }else{
                pickPandaCard();
            }
        }
        else {
            this.objectives.get(0).play(this);
        }

    }

    /**
     * This method will be called to play when the dice is Lightning
     */
    public void playLigntningDice(){
        Tile tile = this.board.getPanda().getTile();
        ObjectivePanda cardToPlay = findObjectivePanda();
        if(cardToPlay != null){
            for(Tile t : this.board.getBoardTiles()){
                if (t.getTypeOfTile().equals(cardToPlay.getTypeOfTile()) && t.getBamboo()>0){
                    Main.LOGGER.info(this.board.movePandaOn(t.getCoordinate(),this));
                    break;
                }
            }
        }
    }

    /**
     * This method will be called to play when the dice is Cloud
     */
    public void playCloudDice(){
        pickArrangement(TypeOfArrangement.BASIN);
    }

    /**
     * The method will be called to play when the best gard is a Gardener Card
     */
    public void playForGardenerCard(){
        while(getNbActions()>0){
            ObjectiveGardener objectif = findObjectiveGardener();
            Tile tileOfGardener = board.getGardener().getTile();
            List<Coordinate> availablePositionsForGarderner = tileOfGardener.scanAvailableCoordinatesToMove(board.getBoardTiles());
            if(availablePositionsForGarderner.isEmpty()){
                if(board.getTileStack().sizeTileStack()>2){
                    List<Coordinate> availableCoordinatesToPutTile = this.board.scanAvailableTilePosition();
                    List<Tile> tilesPicked = board.pickThreeTiles();
                    Tile bestCard = chooseBetterOf3Tiles(tilesPicked);
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
                    if(tile != null){
                        if(tile.getTypeOfTile().equals(objectif.getTypeOfTile())){
                            availablePositionsForGarderner2.add(co);
                        }
                    }
                }
                if(!availablePositionsForGarderner2.isEmpty()){
                    boolean gardenerMooved = false;
                    for(Coordinate co : availablePositionsForGarderner2){
                        Tile tile = board.getTile(co);
                        if(tile != null){
                            if(tile.isIrrigated()){
                                Main.LOGGER.info(board.moveGardenerOn(co));
                                this.playAction();
                                gardenerMooved = true;
                                break;
                            }
                        }
                    }
                    if(!gardenerMooved){
                        ArrayList<Irrigation> listOfIrrigation = board.getLegalIrrigationPlacement();
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
                            //Main.LOGGER.info("Bite");
                            for(Irrigation irrigation : listOfIrrigation){
                                Tile t1 = board.getTile(irrigation.getCoordinates().get(0));
                                Tile t2 = board.getTile(irrigation.getCoordinates().get(1));
                                if(t1 != null && t2 != null){
                                    if(getNbActions() > 0){
                                        boolean verif = board.addIrrigation(irrigation);
                                        if(verif){
                                            this.playAction();
                                            break;
                                        }
                                    }
                                }
                            }
                            if(board.getTileStack().sizeTileStack()>2){
                                List<Coordinate> availableCoordinatesToPutTile = this.board.scanAvailableTilePosition();
                                List<Tile> tilesPicked = board.pickThreeTiles();
                                Tile bestCard = chooseBetterOf3Tiles(tilesPicked);
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
                            Tile bestCard = chooseBetterOf3Tiles(tilesPicked);
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
                        Tile bestCard = chooseBetterOf3Tiles(tilesPicked);
                        Tile toAdd = new Tile(availableCoordinatesToPutTile.get(0),bestCard.getTypeOfTile(),bestCard.getTypeOfArrangement());
                        String message = this.board.addTile(toAdd);
                        Main.LOGGER.info(message);
                        this.playAction();
                    }else{
                        playForIrrigation();
                    }
                }
            }

            //if(this.nbActions > 0) objectives.get(objectives.size()-1).play(this);
        }
    }

    /**
     * The method will be called to play when the best gard is a Panda Card
     */
    public void playForPandaCard(){
        while(getNbActions()>0){
            ObjectivePanda objectif = findObjectivePanda();
            Tile tileOfPanda = this.board.getPanda().getTile();
            List<Coordinate> availableCoordinates = tileOfPanda.scanAvailableCoordinatesToMove(this.board.getBoardTiles());
            boolean moove = false;
            if(availableCoordinates.isEmpty()){
                if(board.getTileStack().sizeTileStack()>2){
                    List<Coordinate> availableCoordinatesToPutTile = this.board.scanAvailableTilePosition();
                    List<Tile> tilesPicked = board.pickThreeTiles();
                    Tile bestCard = chooseBetterOf3Tiles(tilesPicked);
                    Tile toAdd = new Tile(availableCoordinatesToPutTile.get(0),bestCard.getTypeOfTile(),bestCard.getTypeOfArrangement());
                    String message = this.board.addTile(toAdd);
                    Main.LOGGER.info(message);
                    this.playAction();
                }else{
                    playForIrrigation();
                }
            }else{
                for(Coordinate co : availableCoordinates){
                    Tile tile = this.board.getTile(co);
                    if(tile != null){
                        if(tile.getBamboo()>0 && objectif.typeOfTile.equals(tile.getTypeOfTile())){
                            Main.LOGGER.info(this.board.movePandaOn(co,this));
                            this.playAction();
                            moove = true;
                            break;
                        }
                    }
                }
                if(!moove){
                    playGardenerForSpecificTile(objectif.getTypeOfTile(), tileOfPanda);
                }
            }
        }
    }

    /**
     * The method will be called to play when the best gard is a Pattern Card
     */
    public void playForPatternCard(){
        ObjectivePlot objectif = findObjectivePlot();
        Main.LOGGER.info("la focus card = " +objectif);
        List<TypeOfTile> colors = objectif.getColors();
        if(board.getTileStack().sizeTileStack()>2){
            List<Tile> tilesPicked = board.pickThreeTiles();
            Main.LOGGER.info("Le joueur " +this.getNom() +" a pioche les tuiles suivantes :");
            for(Tile tile : tilesPicked){
                String message = tile.getTypeOfTile().toString();
                Main.LOGGER.info(message);
            }
            if(objectif.getPattern().getType().equals(TypeOfPattern.LINE)){
                boolean isPlaced = false;
                List<Tile> tilesToPutBackInStack = new ArrayList<>();
                for(Tile tile : tilesPicked){
                    if(tile.getTypeOfTile().equals(colors.get(0))&&!isPlaced){
                        isPlaced = true;
                        String ret = board.addTile(new Tile(board.bestCoordinateForLine(objectif),tile.getTypeOfTile()))+ " de type:"+tile.getTypeOfTile();
                        Main.LOGGER.info(ret);
                    }
                    else{
                        tilesToPutBackInStack.add(tile);
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
                if(board.getDice().getMeteo()!=Meteo.RAIN){
                    this.playAction();
                }
                else{
                    board.getDice().setMeteo(Meteo.NONE);
                }

            }
        }else{
            playForIrrigation();
        }
    }


    /**
     * This method will choose the better card of 3 cards in function of the focus card
     * @param threeCards A List of the 3 cards
     * @return The better card
     */
    public Tile chooseBetterOf3Tiles(List<Tile> threeCards){
        ObjectifWithOneColor objectif = findObjectiveGardener();
        if(objectif == null) objectif = findObjectivePanda();

        if(threeCards.get(0).getTypeOfTile().equals(objectif.getTypeOfTile())){
            board.getTileStack().addTile(threeCards.get(1));
            board.getTileStack().addTile(threeCards.get(2));
            Main.LOGGER.info(Integer.toString(board.getTileStack().sizeTileStack()));
            return threeCards.get(0);
        }

        if(threeCards.get(1).getTypeOfTile().equals(objectif.getTypeOfTile())){
            board.getTileStack().addTile(threeCards.get(0));
            board.getTileStack().addTile(threeCards.get(2));
            Main.LOGGER.info(Integer.toString(board.getTileStack().sizeTileStack()));
            return threeCards.get(1);
        }

        if(threeCards.get(2).getTypeOfTile().equals(objectif.getTypeOfTile())){
            board.getTileStack().addTile(threeCards.get(0));
            board.getTileStack().addTile(threeCards.get(1));
            Main.LOGGER.info(Integer.toString(board.getTileStack().sizeTileStack()));
            return threeCards.get(2);
        }

        board.getTileStack().addTile(threeCards.get(1));
        board.getTileStack().addTile(threeCards.get(2));
        Main.LOGGER.info(Integer.toString(board.getTileStack().sizeTileStack()));
        return threeCards.get(0);

    }

    /**
     * This method will find the first ObjectiveGardener card in the bot's hand
     * @return The first ObjectiveGardener's card
     */
    public ObjectiveGardener findObjectiveGardener(){
        for(int i = 0; i!= objectives.size(); i++){
            if(objectives.get(i) instanceof ObjectiveGardener){
                return (ObjectiveGardener) objectives.get(i);
            }
        }
        return null;
    }

    /**
     * This method will find the first ObjectivePanda card in the bot's hand
     * @return The first ObjectivePanda's card
     */
    public ObjectivePanda findObjectivePanda(){
        for(int i=0; i!= objectives.size(); i++){
            if(objectives.get(i) instanceof ObjectivePanda){
                return (ObjectivePanda) objectives.get(i);
            }
        }
        return null;
    }

    /**
     * This method will find the first ObjectivePlot card in the bot's hand
     * @return The first ObjectivePlot's card
     */
    public ObjectivePlot findObjectivePlot(){
        for(int i=0; i!= objectives.size(); i++){
            if(objectives.get(i) instanceof ObjectivePlot){
                return (ObjectivePlot) objectives.get(i);
            }
        }
        return null;
    }

    /**
     * This method will check if the bot success to build a specific pattern in function of his objectives
     */
    public void checkPatternOnBoard() {
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

    /**
     * This method will play with the Gardener in function of a specific Type of tile
     * @param type The specific type of tile
     * @param tileOfPanda The tile where the Panda is
     */
    public void playGardenerForSpecificTile(TypeOfTile type, Tile tileOfPanda){

        boolean action = false;
        List<Tile> tileList = board.getBoardTiles();
        List<Coordinate> coordinateToMoovePanda = tileOfPanda.scanAvailableCoordinatesToMove(tileList);
        List<Coordinate> coordinateToMooveGardener = board.getGardener().getTile().scanAvailableCoordinatesToMove(tileList);
        ArrayList<Irrigation> availablePositionsIrrigation = board.getLegalIrrigationPlacement();
        for(Coordinate co : coordinateToMooveGardener){
            if(coordinateToMoovePanda.contains(co)){
                if(board.getTile(co).isIrrigated() && board.getTile(co).getTypeOfTile().equals(type)){
                    Main.LOGGER.info(board.moveGardenerOn(co));
                    this.playAction();
                    action=true;
                    break;
                }else{
                    for(Irrigation irrigation: availablePositionsIrrigation){
                        if((irrigation.getCoordinates().get(0).equals(co) || irrigation.getCoordinates().get(1).equals(co)) && !action && board.getTile(co).getTypeOfTile().equals(co)){
                            boolean verif = board.addIrrigation(irrigation);
                            if(verif){
                                this.playAction();
                                break;
                            }
                        }
                    }
                }
            }
        }
        if(!action && this.getNbActions()>0){
            if(board.getTileStack().sizeTileStack()>2){
                List<Coordinate> availableCoordinatesToPutTile = this.board.scanAvailableTilePosition();
                List<Tile> tilesPicked = board.pickThreeTiles();
                Tile bestCard = chooseBetterOf3Tiles(tilesPicked);
                Tile toAdd = new Tile(availableCoordinatesToPutTile.get(0),bestCard.getTypeOfTile(),bestCard.getTypeOfArrangement());
                String message = this.board.addTile(toAdd);
                Main.LOGGER.info(message);
                this.playAction();
            }else{
                playForIrrigation();
            }
        }
    }

    /**
     * This method will play to put some irrigations on the first legal irrigation's placement
     */
    public void playForIrrigation(){
        ArrayList<Irrigation> listeIrrigations = board.getLegalIrrigationPlacement();
        for(Irrigation irrigation : listeIrrigations){
            boolean verif = board.addIrrigation(irrigation);
            if(verif){
                this.playAction();
                break;
            }else{
                this.playAction();
                this.playAction();
            }
        }
    }


}
