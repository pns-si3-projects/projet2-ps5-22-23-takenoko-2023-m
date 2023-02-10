package fr.cotedazur.univ.polytech.startingpoint.bots;

import fr.cotedazur.univ.polytech.startingpoint.*;

import java.util.ArrayList;
import java.util.List;
public abstract class Bot {
    /** The number of points of the Bot */
    protected int point = 0;
    /** The name of the bot*/
    protected String nom;
    /**The board of the bot*/
    protected final Board board;
    /**The number of green bamboo*/
    protected   int nbBambooGreen = 0;
    /**The number of yellow bamboo*/
    protected int nbBambooYellow = 0;
    /**The number of red bamboo*/
    protected int nbBambooRed = 0;
    /**The number of actions the bot can do*/
    protected int nbActions = 2;
    /**The number of objectives the bot realized*/
    protected int nbObjectifsRealises = 0;
    /**The number of irrigations*/
    protected int nbIrrigation = 0;

    /**The arrayList of all bot's objectives*/
    protected ArrayList<ObjectiveInterface> objectives = new ArrayList<ObjectiveInterface>();
    /**The arrayList of all bot's arrangement*/
    protected List<TypeOfArrangement> listArrangement = new ArrayList<TypeOfArrangement>();
    /**The number of tour the bot is playing*/
    protected int nbTours = 1;

    /**
     * The constructor of the Bot
     * @param board The board of the bot
     * @param nom The name of the bot
     */
    protected Bot(Board board, String nom){

        this.nom = nom;
        this.board = board;
        this.pickGardenerCard();
        this.pickPlotCard();
        this.pickPandaCard();
    }

    /**
     * The main method of the bot which make the bot play
     */
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

    /**
     * The method which check if the bot completed a Gardener objective
     */
    private void checkGardenerObjectiveOnBoard() {
        List<ObjectiveInterface> listObjectiveToRemove = new ArrayList<>();
        for(int i =0; i!=this.objectives.size(); i++){
            if(this.objectives.get(i).isValid(this, this.board)){
                setPoint(getPoint()+this.objectives.get(i).getNbPointsWin());
                this.upNbObjectifsRealises();
                String message = objectives.get(i).toString()+" a été réalisé ! ";
                Main.LOGGER.info(message);
                listObjectiveToRemove.add(this.objectives.get(i));
            }
        }
        for(ObjectiveInterface o : listObjectiveToRemove){
            this.objectives.remove(o);
        }
    }

    /**
     * The method which pick a random Panda card and add it in the bot's hand
     */
    public void pickPandaCard(){
        ObjectivePanda objectivePanda = this.board.getPandaCard();
        this.objectives.add(objectivePanda);
        String message ="Le joueur "+this.getNom()+" a pioche une carte Panda et qui vaut "+objectivePanda.getNbPointsWin()+" points";
        Main.LOGGER.info(message);
        this.playAction("pioche une carte Panda");
    }

    /**
     * The method which pick a random Plot card and add it in the bot's hand
     */
    public void pickPlotCard(){
        ObjectivePlot objectivePlot = this.board.getPlotCard();
        this.objectives.add(objectivePlot);
        Main.LOGGER.info("Le joueur "+this.getNom()+" a pioche une carte Pattern de type "+objectivePlot.getType()+" et de couleur "+objectivePlot.getColors().get(0)+" et qui vaut "+objectivePlot.getNbPointsWin()+" points");
        this.playAction("pioche une carte Pattern");
    }

    /**
     * The method which pick a random Gardener card and add it in the bot's hand
     */
    public void pickGardenerCard(){
        ObjectiveGardener objectiveGardener = this.board.getGardenerCard();
        this.objectives.add(objectiveGardener);
        Main.LOGGER.info("Le joueur "+this.getNom()+" a pioche une carte Jardinier et qui vaut "+objectiveGardener.getNbPointsWin());
        this.playAction("pioche une carte Jardinier");
    }

    /**
     * The getter which return the list of bot's arrangement
     * @return A List of the bot's arrangement
     */
    public List<TypeOfArrangement> getListArrangement() {
        return listArrangement;
    }

    /**
     * The getter of the number of point the bot have
     * @return
     */
    public int getPoint() {
        return point;
    }

    /**
     * The setter of the new number of bot's points
     * @param point The new number of bot's points
     */
    public void setPoint(int point) {
        this.point = point;
    }

    /**
     * The getter of the bot's name
     * @return The bot's name
     */
    public String getNom() {
        return nom;
    }

    /**
     * The setter of the bot's name
     * @param nom The new name of the bot
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * The getter of the number of bot's actions
     * @return
     */
    public int getNbActions() { return this.nbActions;}


    /**
     * A method which decrements the number of bot's actions
     */
    public void playAction(String action) {
        this.nbActions -= 1;
        Main.LOGGER.info("Le joueur " +this.getNom() +" vient de jouer" + action);
        Main.LOGGER.info("Nombre d'action restante: " + this.getNbActions());
    }

    /**
     * A method which reset the number of bot's action
     */
    public void resetNbActions() {
        if (this.board.getDice().getMeteo() == Meteo.SUN) {
            this.nbActions = 3;
        } else {
            this.nbActions = 2;
        }
    }

    /**
     * A method which will hard reset the number of specific bamboo in the bot
     * @param type The specific type of the bamboo
     */
    public void resetNbBamboo(TypeOfTile type){
        if(type == TypeOfTile.RED) this.nbBambooRed = 0;
        if(type == TypeOfTile.YELLOW) this.nbBambooYellow = 0;
        if(type == TypeOfTile.GREEN) this.nbBambooGreen = 0;
    }


    /**
     * A method which will change the number of a specific bamboo
     * @param value The new value of the specific bamboo
     * @param type The specific type of the bamboo
     */

    protected void resetNbBamboo(int value, TypeOfTile type) {
        if(type == TypeOfTile.RED) this.nbBambooRed = value;
        if(type == TypeOfTile.YELLOW) this.nbBambooYellow = value;
        if(type == TypeOfTile.GREEN) this.nbBambooGreen = value;
    }

    /**
     * A method which will add a new Tile on the board
     * @param tile The specific tile
     * @return A string which can be printed
     */
    public String addTile(Tile tile){
        return this.board.addTile(tile);
    }

    /**
     * A method which will return the List of bot's objectives
     * @return A List of the bot's objectives
     */
    public List<ObjectiveInterface> getObjective() {
        return objectives;
    }

    /**
     * A method which will pick a specific arrangement
     * @param t The specific type of arrangement the bot want to pick
     */
    public void pickArrangement(TypeOfArrangement t){
        if(board.getDice().getMeteo()==Meteo.CLOUD){
            switch (t){
                case NONE:
                    throw new IllegalArgumentException("il faut choisir un type valide");
                case FERTILIZER:
                    if(!this.board.getFertilizerStack().getStack().isEmpty()){
                        this.getListArrangement().add(this.board.getFertilizerStack().pick(t));
                    }
                    break;
                case BASIN:
                    if(!this.board.getBasinStack().getStack().isEmpty()){
                        this.getListArrangement().add(this.board.getBasinStack().pick(t));

                    }
                    break;
                case ENCLOSURE:
                    if(!this.board.getEnclosureStack().getStack().isEmpty()){
                        this.getListArrangement().add(this.board.getEnclosureStack().pick(t));
                    }
                    break;
            }
        }
        else{
            throw new IllegalArgumentException("il faut choisir un type valide ou que le temps soit couvert");
        }


    }

    /**
     * A method which will add on a specific tile a specific arrangement
     * @param tile The specific tile
     * @param t The arrangement we want to add
     */
    public void setArrangement(Tile tile, TypeOfArrangement t){
        if (tile.getTypeOfArrangement()== TypeOfArrangement.NONE&&this.getListArrangement().contains(t)){
            tile.setTypeOfArrangement(t);
        }
    }

    /**
     * A method for up the number of green's bamboo on the bot
     */
    public void upBambooGreen() {
        this.nbBambooGreen++;
    }

    /**
     * A method for up the number of yellow's bamboo on the bot
     */
    public void upBambooYellow() {
        this.nbBambooYellow++;
    }

    /**
     * A method for up the number of red's bamboo on the bot
     */
    public void upBambooRed() {
        this.nbBambooRed++;
    }

    /**
     * This method will return the number of specific bamboo of the bot
     * @param type The type of the specific bamboo
     * @return The number of the specific bamboo
     */
    public int getNbBamboo(TypeOfTile type) {
        if(type == TypeOfTile.RED) return this.nbBambooRed;
        if(type == TypeOfTile.YELLOW) return this.nbBambooYellow;
        if(type == TypeOfTile.GREEN) return this.nbBambooGreen;
        return 0;
    }

    /**
     * This method will up the number of a specific bamboo
     * @param type The type of the specific bamboo
     */
    public void upNbBamboo(TypeOfTile type) {
        if(type == TypeOfTile.RED){
            this.upBambooRed();
        }else if (type == TypeOfTile.YELLOW) {
            this.upBambooYellow();
        }else if (type == TypeOfTile.GREEN) {
            this.upBambooGreen();
        }
    }

    /**
     * This method will be called to play for a Panda card
     */
    public void playForPandaCard(){
    }
    /**
     * This method will be called to play for a Pattern card
     */
    public void playForPatternCard(){
    }
    /**
     * This method will be called to play for a Gardener card
     */
    public void playForGardenerCard(){
    }

    /**
     * This method will check if the bot success to build a specific pattern in function of his objectives
     */
    protected void checkPatternOnBoard() {
        //take objective of type ObjectivePlot from the list objectives
        ArrayList<ObjectivePlot> objectivePlotList = new ArrayList<>();
        for(ObjectiveInterface objective : this.objectives){
            if(objective instanceof ObjectivePlot obj){
                objectivePlotList.add(obj);
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
     * This method will up the number of bot's actions
     */
    public void upNbActions(){
        this.nbActions += 1;
    }

    /**
     * This method will up the number of bot's finished objectives
     */
    public void upNbObjectifsRealises(){
        nbObjectifsRealises+=1;
    }

    /**
     * This method will return the number of bot's finished objectives
     * @return
     */
    public int getNbObjectifsRealises(){
        return nbObjectifsRealises;
    }

}
