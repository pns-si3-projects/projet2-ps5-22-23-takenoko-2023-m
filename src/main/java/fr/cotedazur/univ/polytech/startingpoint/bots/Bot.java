package fr.cotedazur.univ.polytech.startingpoint.bots;

import fr.cotedazur.univ.polytech.startingpoint.*;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.*;
public abstract class Bot {
    protected int point = 0;
    protected String nom;
    protected final Board board;
    protected   int nbBambooGreen = 0;
    protected int nbBambooYellow = 0;
    protected int nbBambooRed = 0;
    protected int nbActions = 2;
    protected int nbObjectifsRealises = 0;
    protected int nbIrrigation = 0;
    protected ArrayList<ObjectiveInterface> objectives = new ArrayList<ObjectiveInterface>();
    protected List<TypeOfArrangement> listArrangement = new ArrayList<TypeOfArrangement>();
    protected int nbTours = 1;

    public Bot(Board board, String nom){
        this.nom = nom;
        this.board = board;
        this.pickGardenerCard();
        this.pickPlotCard();
        this.pickPandaCard();
    }
    public void play() {
        if(this.nbTours >1){
            this.board.getDice().randomMeteo();
        }
        Main.LOGGER.info("La météo est  "  + this.board.getDice().getMeteo());
        this.resetNbActions();
        this.checkPatternOnBoard();
        this.checkGardenerObjectiveOnBoard();
        this.nbTours ++;
    }

    private void checkGardenerObjectiveOnBoard() {
        List<ObjectiveInterface> listObjectiveToRemove = new ArrayList<>();
        for(int i =0; i!=this.objectives.size(); i++){
            if(this.objectives.get(i).isValid(this, this.board)){
                setPoint(getPoint()+this.objectives.get(i).getNbPointsWin());
                this.upNbObjectifsRealises();
                Main.LOGGER.info(objectives.get(i).toString()+" a été réalisé ! ");
                listObjectiveToRemove.add(this.objectives.get(i));
            }
        }
        for(ObjectiveInterface o : listObjectiveToRemove){
            this.objectives.remove(o);
        }
    }

    public void pickPandaCard(){
        ObjectivePanda objectivePanda = this.board.getPandaCard();
        this.objectives.add(objectivePanda);
        Main.LOGGER.info("Le joueur "+this.getNom()+" a pioche une carte Panda et qui vaut "+objectivePanda.getNbPointsWin()+" points");
        this.playAction();
    }

    public void pickPlotCard(){
        //TODO : make possible to pick other objective than LINE
        ObjectivePlot objectivePlot = this.board.getPlotCard();
        this.objectives.add(objectivePlot);
        Main.LOGGER.info("Le joueur "+this.getNom()+" a pioche une carte Pattern de type "+objectivePlot.getType()+" et de couleur "+objectivePlot.getColors().get(0)+" et qui vaut "+objectivePlot.getNbPointsWin()+" points");
        this.playAction();
    }

    public void pickGardenerCard(){
        ObjectiveGardener objectiveGardener = this.board.getGardenerCard();
        this.objectives.add(objectiveGardener);
        Main.LOGGER.info("Le joueur "+this.getNom()+" a pioche une carte Jardinier et qui vaut "+objectiveGardener.getNbPointsWin());
        this.playAction();
    }


    public List<TypeOfArrangement> getListArrangement() {
        return listArrangement;
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
        Main.LOGGER.info("Le joueur " +this.getNom() +" vient de jouer");
    }

    public void resetNbActions() {
        if (this.board.getDice().getMeteo() == Meteo.SUN) {
            this.nbActions = 3;
        } else {
            this.nbActions = 2;
        }
    }
    public void resetNbBamboo(TypeOfTile type){
        if(type == TypeOfTile.RED) this.nbBambooRed = 0;
        if(type == TypeOfTile.YELLOW) this.nbBambooYellow = 0;
        if(type == TypeOfTile.GREEN) this.nbBambooGreen = 0;
    }
    //TODO : refactor both resetNbBamboo methods
    protected void resetNbBamboo(int value, TypeOfTile type) {
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

    public void pickArrangement(TypeOfArrangement t){
        if(board.getDice().getMeteo()==Meteo.CLOUD){
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
        else{
            throw new IllegalArgumentException("il faut choisir un type valide ou que le temps soit couvert");
        }


    }
    public void setArrangement(Tile tile, TypeOfArrangement t){
        if (tile.getTypeOfArrangement()== TypeOfArrangement.NONE&&this.getListArrangement().contains(t)){
            tile.setTypeOfArrangement(t);
        }
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
    public void playForPandaCard(){
    }
    public void playForPatternCard(){
    }
    public void playForGardenerCard(){
    }
    protected void checkPatternOnBoard() {
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

    public void upNbActions(){
        this.nbActions += 1;
    }

    public void upNbObjectifsRealises(){
        nbObjectifsRealises+=1;
    }

    public int getNbObjectifsRealises(){
        return nbObjectifsRealises;
    }

}
