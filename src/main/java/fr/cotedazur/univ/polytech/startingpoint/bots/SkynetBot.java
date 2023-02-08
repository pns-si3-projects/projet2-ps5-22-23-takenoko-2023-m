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

        switch (this.board.getDice().getMeteo()){
            case SUN -> this.nbActions++;
            case LIGHTNING -> movePandaToTheBestLocation();
            case CLOUD -> {
                if(board.getBasinStack().getStack().isEmpty()&&board.getEnclosureStack().getStack().isEmpty()&&board.getFertilizerStack().getStack().isEmpty()){
                    chooseBestMeteo();
                }
                boolean meteoIsCloud = true;
            }
            case QUESTIONMARK -> chooseBestMeteo();
        }
        focusedObjective.play(this);
        Main.LOGGER.info("\n");
    }

    private void movePandaToTheBestLocation() {
    }

    private void chooseBestMeteo() {
    }
    public void playForPandaCard(){
    }
    public void playForPatternCard(){
    }
    public void playForGardenerCard(){
    }

    private void checkObjectiveToFocus() {
        if(this.getObjective().size()<5){
            if(board.getPatternBoard().getPatternBoardList().size()>6&&!board.isPlotCardEmpty()){
                objectives.add(board.getPlotCard());
            }
            else{
                objectives.add(board.getPandaCard());
            }
            nbIrrigation++;
            nbActions--;
            nbActions--;
        }
        else{
            focusedObjective = chooseObjectiveToFocus();
        }

    }

    private ObjectiveInterface chooseObjectiveToFocus() {
        ObjectiveInterface objectiveToFocus = null;
        int max = 0;
        for(ObjectiveInterface objective : objectives){
            checkObjectiveComplexity(objective);
            if(objective.getNbPointsWin()>max){
                if(objectiveToFocus==null || (objectiveToFocus.getComplexity()-1>objective.getComplexity())){
                    max = objective.getNbPointsWin();
                    objectiveToFocus = objective;
                }
            }
            else if(objectiveToFocus.getComplexity()>objective.getComplexity()+1){
                max = objective.getNbPointsWin();
                objectiveToFocus = objective;
            }
        }
        return objectiveToFocus;
    }

    private void checkObjectiveComplexity(ObjectiveInterface objective) {
        if(objective instanceof ObjectivePlot objectivePlot){
            checkPlotObjectiveComplexity(objectivePlot);
        }
        else if(objective instanceof ObjectivePanda objectivePanda){
            checkPandaObjectiveComplexity(objectivePanda);
        }
        else if(objective instanceof ObjectiveGardener objectiveGardener){
            checkGardenerObjectiveComplexity(objectiveGardener);
        }
    }

    private void checkGardenerObjectiveComplexity(ObjectiveGardener objectiveGardener) {
        List<Tile> listOfTileOfSameColorInBoard = this.getBoardTileOfType(objectiveGardener.getTypeOfTile());
        for(Tile tile : listOfTileOfSameColorInBoard){
            //case where only one bamboo is missing to complete the objective
            if(tile.getBamboo()-1 == objectiveGardener.getNbBambooRequired()){
                //si il suffit juste d'ajouter un bambou
                if(tile.getTypeOfTile() == objectiveGardener.getTypeOfTile()){
                    objectiveGardener.setComplexity(1);
                }
                //si il faut ajouter un bambou et poser un arrangement
                else if(this.getListArrangement().contains(objectiveGardener.getTypeOfArrangement())){
                    objectiveGardener.setComplexity(3);
                }
                //si il faut attendre d'avoir un arrangement pour pouvoir compléter l'objectif
                else{
                    objectiveGardener.setComplexity(5);
                }
            }
            //si il y a le bon nombre de bamboo mais qu'il n'y a pas le bon arrangement
            else if(tile.getBamboo() == objectiveGardener.getNbBambooRequired()){
                if(this.getListArrangement().contains(objectiveGardener.getTypeOfArrangement())){
                    objectiveGardener.setComplexity(1);
                }
                else{
                    objectiveGardener.setComplexity(5);
                }
            }
            else{
                objectiveGardener.setComplexity(5);
            }
        }
    }

    private void checkPandaObjectiveComplexity(ObjectivePanda objectivePanda) {
        TypeOfTile type = objectivePanda.getTypeOfTile();
        int numberOfBambou = objectivePanda.getNbToEat();
        int maxNumberOfBambouEatableOnBoardOnATile = checkMaxNumberOfBambouEatableOnBoardOnATile(type);
        switch (type){
            case GREEN:
                if(numberOfBambou<nbBambooGreen+maxNumberOfBambouEatableOnBoardOnATile){
                    objectivePanda.setComplexity(1);
                }
                else{
                    objectivePanda.setComplexity(3);
                }
                break;
            case YELLOW:
                if(numberOfBambou<nbBambooYellow+maxNumberOfBambouEatableOnBoardOnATile){
                    objectivePanda.setComplexity(1);
                }
                else{
                    objectivePanda.setComplexity(3);
                }
                break;
            case RED:
                if(numberOfBambou<nbBambooRed+maxNumberOfBambouEatableOnBoardOnATile){
                    objectivePanda.setComplexity(1);
                }
                else{
                    objectivePanda.setComplexity(3);
                }
                break;
        }
    }

    private void checkPlotObjectiveComplexity(ObjectivePlot objectivePlot) {
        List<Tile> listOfTileOfSameColorInBoard = this.getBoardTileOfType(objectivePlot.getColors().get(0));
        objectivePlot.setComplexity(4);
        int numberOfTileWhichHaveSameColorAsNeighbour = getNumberOfTileWhichHaveSameColorAsNeighbour(listOfTileOfSameColorInBoard);
        if ( numberOfTileWhichHaveSameColorAsNeighbour > 0 && numberOfTileWhichHaveSameColorAsNeighbour<=2) {
            objectivePlot.setComplexity(2);
        }
        else if(numberOfTileWhichHaveSameColorAsNeighbour > 2){
            objectivePlot.setComplexity(1);
        }
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

}
