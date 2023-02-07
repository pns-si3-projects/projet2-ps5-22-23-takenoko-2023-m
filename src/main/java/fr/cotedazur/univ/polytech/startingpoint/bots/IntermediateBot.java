package fr.cotedazur.univ.polytech.startingpoint.bots;
import fr.cotedazur.univ.polytech.startingpoint.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class IntermediateBot extends Bot {

    private ObjectiveInterface focusCard = null;
    int nbTour = 1;

    public IntermediateBot(Board board, String nom) {
        super(board, nom);
    }

    public void play(){
        this.resetNbActions();
        //Sort Objectives by nbPointsWin
        this.objectives.sort((o1, o2) -> o2.getNbPointsWin() - o1.getNbPointsWin());
        if(nbTour > 1){
            this.board.getDice().randomMeteo();
            switch(this.board.getDice().getMeteo()){
                case SUN -> this.upNbActions();
                case RAIN -> Main.LOGGER.info("//ATT QUE QUENTIN PR POUR PRENDRE SA METHODE DANS BOARD");
                case WIND -> Main.LOGGER.info("//WAIT");
                case LIGHTNING -> playLigntningDice();
                case CLOUD -> playCloudDice();
                case QUESTIONMARK -> playLigntningDice(); //A revoir potentiellement
            }

            this.objectives.get(0).play(this);

        }

        nbTour++;
    }

    public void playLigntningDice(){
        Tile tile = this.board.getPanda().getTile();
        ObjectivePanda cardToPlay = null;
        for(ObjectiveInterface ob : this.objectives){
            if(ob instanceof ObjectivePanda) cardToPlay = (ObjectivePanda) ob;
        }
        for(Tile t : this.board.getBoardTiles()){
            if (t.getTypeOfTile().equals(cardToPlay.getTypeOfTile())){
                Main.LOGGER.info(this.board.movePandaOn(t.getCoordinate(),this));
                break;
            }
        }
    }

    public void playCloudDice(){
        pickArrangement(TypeOfArrangement.BASIN);
    }

    public void playForGardenerCard(){
        while(getNbActions()>0){
            ObjectiveGardener objectif = findObjectiveGardener();
            Tile tileOfGardener = board.getGardener().getTile();
            List<Coordinate> availablePositionsForGarderner = tileOfGardener.scanAvailableCoordinatesToMove(board.getBoardTiles());
            if(availablePositionsForGarderner.isEmpty()){

            }
        }
    }

    public void playForPandaCard(){
        while(getNbActions()>0){
            ObjectivePanda objectif = findObjectivePanda();
            Tile tileOfPanda = this.board.getPanda().getTile();
            List<Coordinate> availableCoordinates = tileOfPanda.scanAvailableCoordinatesToMove(this.board.getBoardTiles());
            boolean moove = false;
            if(availableCoordinates.isEmpty()){
                List<Coordinate> availableCoordinatesToPutTile = this.board.scanAvailableTilePosition();
                List<Tile> tilesPicked = board.pickThreeTiles();
                Tile toAdd = chooseBetterOf3Tiles(tilesPicked);
                toAdd.setCoordinate(availableCoordinatesToPutTile.get(0));
                String message = this.board.addTile(toAdd);
                Main.LOGGER.info(message);
                this.playAction();
            }else{
                for(Coordinate co : availableCoordinates){
                    Tile tile = this.board.getTile(co);
                    if(tile != null){
                        if(tile.getBamboo()>0 && objectif.typeOfTile.equals(tile.getTypeOfTile())){
                            Main.LOGGER.info(this.board.moveGardenerOn(co));
                            this.playAction();
                            moove = true;
                        }
                        break;
                    }
                }
                if(moove){
                    if(getNbActions()==0){
                        if(objectif.isValid(this,board)){
                            setPoint(getPoint()+objectif.getNbPointsWin());
                            upNbObjectifsRealises();
                            objectives.remove(0);
                        }
                    }else{
                        objectives.get(1).play(this);
                    }
                }else{
                    Tile tileOfGardener = board.getGardener().getTile();
                    List<Coordinate> availableCoordinatesGardener = tileOfGardener.scanAvailableCoordinatesToMove(board.getBoardTiles());
                    boolean mooveGardener = false;
                    for(Coordinate co : availableCoordinatesGardener){
                        if(board.getTile(co)!=null && board.getTile(co).getTypeOfTile().equals(objectif.getTypeOfTile())){
                            Main.LOGGER.info(board.moveGardenerOn(co));
                            playAction();
                            mooveGardener = true;
                            break;
                        }
                    }
                    if(!mooveGardener){
                        List<Coordinate> availableCoordinatesToPutTile = this.board.scanAvailableTilePosition();
                        List<Tile> tilesPicked = board.pickThreeTiles();
                        Tile toAdd = chooseBetterOf3Tiles(tilesPicked);
                        toAdd.setCoordinate(availableCoordinatesToPutTile.get(0));
                        String message = this.board.addTile(toAdd);
                        Main.LOGGER.info(message);
                        this.playAction();
                        objectives.get(1).play(this);
                    }
                }
            }

        }
    }

    public void playForPatternCard(){}


    public Tile chooseBetterOf3Tiles(List<Tile> tiles){
        ObjectifWithOneColor objectif = (ObjectifWithOneColor) objectives.get(0);
        if(tiles.get(0).getTypeOfTile().equals(objectif.getTypeOfTile())) {
            Tile ret = tiles.get(0);
            tiles.remove(0);
            this.board.putBackInTileStack(tiles.get(0));
            this.board.putBackInTileStack(tiles.get(1));
            return ret;
        }else if(tiles.get(1).getTypeOfTile().equals(objectif.getTypeOfTile())){
            Tile ret = tiles.get(1);
            tiles.remove(1);
            this.board.putBackInTileStack(tiles.get(0));
            this.board.putBackInTileStack(tiles.get(1));
            return ret;
        }else if(tiles.get(2).getTypeOfTile().equals(objectif.getTypeOfTile())) {
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

    public ObjectiveGardener findObjectiveGardener(){
        for(int i = 0; i!= objectives.size(); i++){
            if(objectives.get(i) instanceof ObjectiveGardener){
                return (ObjectiveGardener) objectives.get(i);
            }
        }
        return null;
    }

    public ObjectivePanda findObjectivePanda(){
        for(int i=0; i!= objectives.size(); i++){
            if(objectives.get(i) instanceof ObjectivePanda){
                return (ObjectivePanda) objectives.get(i);
            }
        }
        return null;
    }

    public ObjectivePlot findObjectivePlot(){
        for(int i=0; i!= objectives.size(); i++){
            if(objectives.get(i) instanceof ObjectivePlot){
                return (ObjectivePlot) objectives.get(i);
            }
        }
        return null;
    }
}
