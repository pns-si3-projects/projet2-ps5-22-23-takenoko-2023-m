package fr.cotedazur.univ.polytech.startingpoint.bots;

import fr.cotedazur.univ.polytech.startingpoint.*;

import java.util.List;

public class EasyBot extends Bot{

    private ObjectiveInterface focusCard = null;

    public EasyBot(Board board, String nom) {
        super(board, nom);
    }

    @Override
    public void play() {
        System.out.println ("simonjoue");
        if (this.getObjective().size() == 0) {
            if (this.board.getStackGardener().getStack().size() != 0) {
                this.getObjective().add(this.board.getStackGardener().randomPick());
            } else if (this.board.getStackPanda().getStack().size() != 0) {
                this.getObjective().add(this.board.getStackPanda().randomPick());
            } else if (this.board.getStackPlot().getStack().size() != 0) {
                this.getObjective().add(this.board.getStackPlot().randomPick());
            }
        }
        if (this.nbTours > 1) {
            this.board.getDice().randomMeteo();
            while (this.board.getDice().getMeteo() == Meteo.NONE || this.board.getDice().getMeteo() == Meteo.QUESTIONMARK) {
                this.board.getDice().randomMeteo();
            }
        }
        Main.LOGGER.info("La météo est  " + this.board.getDice().getMeteo());
        this.resetNbActions();
        checkBetterGardnerCard();
        if(this.focusCard!=null){
            this.playForGardenerCard();
            this.verifyValid();
        }
        else{
            checkBetterCard();
            if(this.focusCard!=null){
                this.justPlaceSomeTile();
                this.verifyValid();
            }
        }

    }


    public void playForGardenerCard(){
        while(this.getNbActions()>0){
            if(this.board.getBoardTiles().size()<=1){
                List<Tile> tilesPicked=this.board.pickThreeTiles();
                for(int i=1;i<tilesPicked.size();i++){
                    this.board.putBackInTileStack(tilesPicked.get(i));
                }
                Tile bestTile = tilesPicked.get(0);
                List<Coordinate>lc = this.board.scanAvailableTilePosition();

                this.board.addTile(new Tile(lc.get(0),bestTile.getTypeOfTile(),bestTile.getTypeOfArrangement()));
                this.playAction();
            }
            else{
                List<Coordinate> lg =this.board.getGardener().getTile().scanAvailableCoordinatesToMove(this.board.getBoardTiles());
                for(Tile t : this.board.getBoardTiles()){
                    if(t.isIrrigated()&&lg.contains(t)){
                        this.board.getGardener().moveOn(t.getCoordinate());
                        this.playAction();
                        break;
                    }


                }
                //irrigateFirstThing();
                justPlaceSomeTile();
                this.playAction();

            }
        }

    }

    public void irrigateFirstThing(){
        Irrigation i =this.board.getLegalIrrigationPlacement().get(0);
        this.board.addIrrigation(i);
        System.out.println("irrigation ajoutée");

    }

    public void justPlaceSomeTile(){
        System.out.println(this.board.getBoardTiles().size());
        if(this.board.getBoardTiles().size()<=1){
            List<Tile> tilesPicked=this.board.pickThreeTiles();
            for(int i=1;i<tilesPicked.size();i++){
                this.board.putBackInTileStack(tilesPicked.get(i));
            }
            Tile bestTile = tilesPicked.get(0);
            List<Coordinate>lc = this.board.scanAvailableTilePosition();
            System.out.println(lc.get(0));

            this.board.addTile(new Tile(lc.get(0),bestTile.getTypeOfTile(),bestTile.getTypeOfArrangement()));

            this.playAction();
        }
        else{
            //irrigateFirstThing();
            //justPlaceSomeTile();
            System.out.println("je suis censé irriguer");
            this.playAction();
        }

    }

    public void verifyValid(){

        if(this.focusCard.isValid(this, this.board)){
            this.setPoint(this.getPoint()+this.focusCard.getNbPointsWin());
            List<ObjectiveInterface> objectifs = this.getObjective();
            objectifs.remove(focusCard);
            Main.LOGGER.info("Objectif "+this.focusCard.getType()+" realise");
            this.focusCard = null;

        }
    }
    public void checkBetterGardnerCard(){
        ObjectiveInterface card = null;
        int max = -1;
        for(ObjectiveInterface cardObj: objectives){
            if((cardObj.getNbPointsWin() > max) && cardObj instanceof ObjectiveGardener){
                max = cardObj.getNbPointsWin();
                card = cardObj;
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
}
