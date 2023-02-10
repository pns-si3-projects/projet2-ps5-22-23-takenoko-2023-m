package fr.cotedazur.univ.polytech.startingpoint.bots;

import fr.cotedazur.univ.polytech.startingpoint.*;

import java.util.ArrayList;
import java.util.List;

public class LittleBot extends IntermediateBot{
    /** The card to focus during the gameplay*/
    private ObjectiveInterface focusCard = null;
    int nbTour = 1;

    /**
     * The constructor of the LittleBot
     * @param board The board of the LittleBot
     * @param nom The name of the LittleBot
     */
    public LittleBot(Board board, String nom) {
        super(board, nom);
    }

    /**
     * The main method bot, it will be called every tour
     */
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
                Main.LOGGER.info("Objectif réalisé par "+getNom());
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

    /**This method will play when the dice is Lightning*/
    public void playLigntningDice(){
        super.playLigntningDice();
    }

    /**This method will play when the dice is Cloud*/
    public void playCloudDice(){
        super.playCloudDice();
    }

    /**
     * This method will play the bot when the focus card is ObjectiveGardener
     */
    public void playForGardenerCard(){
      super.playForGardenerCard();
    }

    /**
     * This method will play the bot when the focus card is ObjectivePanda
     */
    public void playForPandaCard(){
        super.playForPandaCard();
    }

    /**
     * This method will play the bot when the focus card is ObjectivePlot
     */
    public void playForPatternCard(){
        super.playForPatternCard();
    }

    /**
     * This method will choose the best of the 3 cards in function of the focusCard
     * @param threeCards A List of the 3 cards
     * @return The best of the 3 cards
     */
    public Tile chooseBetterOf3Tiles(List<Tile> threeCards){
      return super.chooseBetterOf3Tiles(threeCards);

    }
    /**
     * This method will find the first ObjectiveGardener card in the bot's hand
     * @return The first ObjectiveGardener's card
     */
    public ObjectiveGardener findObjectiveGardener(){
       return super.findObjectiveGardener();
    }

    /**
     * This method will find the first ObjectivePanda card in the bot's hand
     * @return The first ObjectivePanda's card
     */
    public ObjectivePanda findObjectivePanda(){
       return super.findObjectivePanda();
    }

    /**
     * This method will find the first ObjectivePlot card in the bot's hand
     * @return The first ObjectivePlot's card
     */
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
