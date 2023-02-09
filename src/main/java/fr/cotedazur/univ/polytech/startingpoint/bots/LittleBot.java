package fr.cotedazur.univ.polytech.startingpoint.bots;

import fr.cotedazur.univ.polytech.startingpoint.*;

import java.util.ArrayList;
import java.util.List;

public class LittleBot extends IntermediateBot{

    private ObjectiveInterface focusCard = null;
    int nbTour = 1;

    public LittleBot(Board board, String nom) {
        super(board, nom);
    }

    public void play(){
        this.resetNbActions();
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
        }
        this.objectives.get(this.objectives.size()-1).play(this);
        ArrayList<ObjectiveInterface> toSuppress = new ArrayList<>();
        for(int i =0; i!=this.objectives.size(); i++){
            if(this.objectives.get(i).isValid(this, this.board)){
                setPoint(getPoint()+this.objectives.get(i).getNbPointsWin());
                upNbObjectifsRealises();
                toSuppress.add(this.objectives.get(i));
                Main.LOGGER.severe("Objectif réalisé par "+getNom());
            }
        }
        //Suppress all objectives that are done
        for(ObjectiveInterface ob : toSuppress){
            this.objectives.remove(ob);
            if(board.getStackGardener().getStack().size()!=0){
                this.pickGardenerCard();
            }else{
                pickPandaCard();
            }
        }
        this.checkPatternOnBoard();
        nbTour++;
    }

    public void playLigntningDice(){
        super.playLigntningDice();
    }

    public void playCloudDice(){
        super.playCloudDice();
    }

    public void playForGardenerCard(){
      super.playForGardenerCard();
    }

    public void playForPandaCard(){
        super.playForPandaCard();
    }

    public void playForPatternCard(){
        super.playForPatternCard();
    }


    public Tile chooseBetterOf3Tiles(List<Tile> threeCards){
      return super.chooseBetterOf3Tiles(threeCards);

    }

    public ObjectiveGardener findObjectiveGardener(){
       return super.findObjectiveGardener();
    }

    public ObjectivePanda findObjectivePanda(){
       return super.findObjectivePanda();
    }

    public ObjectivePlot findObjectivePlot(){
       return super.findObjectivePlot();
    }

    public void checkPatternOnBoard() {
        super.checkPatternOnBoard();
    }

    public void playGardenerForSpecificTile(TypeOfTile type, Tile tileOfPanda){
       super.playGardenerForSpecificTile(type, tileOfPanda);
    }

    public void playForIrrigation(){
        super.playForIrrigation();
    }

}
