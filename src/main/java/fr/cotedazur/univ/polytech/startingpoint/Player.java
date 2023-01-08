package fr.cotedazur.univ.polytech.startingpoint;
import java.util.ArrayList;
public class Player {
    private int point = 0;
    private String nom;
    private final Board board;
    private int nbBamboo = 0;
    private int nbActions = 2;

    private ObjectiveInterface focusCard = null;

    private ArrayList<ObjectiveInterface> objectives = new ArrayList<ObjectiveInterface>();
    public Player(Board board, String nom){
        this.nom = nom;
        this.board = board;
        this.pickGardenerCard();
        //this.objectives.add(this.board.getPlotCard()); Can't do this now
        this.pickPandaCard();
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

    public void resetNbActions() { this.nbActions = 2;}


    public void play(){
        this.resetNbActions();
        if (this.focusCard == null){
            checkBetterCard();
        }
        if(this.focusCard instanceof ObjectiveGardener){
            this.playForGardenerCard();
        }else if(this.focusCard instanceof ObjectivePanda){
            this.playForPandaCard();
        }else{
            this.playForPatternCard();
        }
        System.out.println();
    }

    public void playForGardenerCard(){
        if (this.board.getBoardTiles().size() == 1){
            ArrayList<Coordinate> availableCoordinates = this.board.scanAvailableTilePosition();
            System.out.println(this.board.addTile(new Tile(new Coordinate(availableCoordinates.get(0).getX(), availableCoordinates.get(0).getY()))));
            this.playAction();
        }
        int numberTile = this.board.getBoardTiles().size();
        Tile tileToMove = this.board.getBoardTiles().get(numberTile-1);
        System.out.println(this.board.moveGardenerOn(tileToMove.getCoordinate()));
        this.playAction();

        if(this.getNbActions()==1){
            System.out.println(this.board.moveGardenerOn(tileToMove.getCoordinate()));
            this.playAction();
        }

        if(this.focusCard.isValid(this, this.board)){
            this.setPoint(this.getPoint()+this.focusCard.getNbPointsWin());
            ArrayList<ObjectiveInterface> objectifs = this.getObjective();
            objectifs.remove(focusCard);
            this.focusCard = null;
            System.out.println("Objecti accompli !");
        }
    }

    public void playForPandaCard(){
        boolean verification = false;
        for(Tile tile : this.board.getBoardTiles()){
            if(tile.getBamboo()>0){
                System.out.println(this.board.movePandaOn(tile.getCoordinate(),this));
                this.playAction();
                System.out.println(this.board.movePandaOn(tile.getCoordinate(),this));
                this.playAction();
                //System.out.println("Le joueur "+this.getNom()+" a fait avance le panda sur les coordones "+tile.getCoordinnateX() + tile.getCoordinnateY());
                verification = true; break;
            }
        }

        if (!verification){
            ArrayList<Coordinate> availableCoordinates = this.board.scanAvailableTilePosition();
            System.out.println(this.addTile(new Tile(new Coordinate(availableCoordinates.get(0).getX(), availableCoordinates.get(0).getY()))));
            this.playAction();
            if(this.getNbActions()==1){
                System.out.println(this.addTile(new Tile(new Coordinate(availableCoordinates.get(0).getX(), availableCoordinates.get(0).getY()))));
                this.playAction();
            }
        }

        if (this.focusCard.isValid(this, this.board)){
            this.resetNbBamboo();
            this.setPoint(this.focusCard.getNbPointsWin()+this.getPoint());
            this.focusCard = null;
            System.out.println("Objectif realise");
        }
    }

    public void playForPatternCard(){
        // do nothing actually
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

    public void resetNbBamboo(){
        this.nbBamboo = 0;
    }

    public String addTile(Tile tile){
        return this.board.addTile(tile);
    }

    public ArrayList<ObjectiveInterface> getObjective() {
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

    public int getNbBamboo() {
        return this.nbBamboo;
    }

    public void upNbBamboo(){
        this.nbBamboo++;
    }

    public void pickPandaCard(){
        this.objectives.add(this.board.getPandaCard());
        System.out.println("Le joueur "+this.getNom()+" à pioché une carte Panda!");
        this.playAction();
    }

    public void pickPlotCard(){
        this.objectives.add(this.board.getPlotCard());
        System.out.println("Le joueur "+this.getNom()+" à pioché une carte Pattern!");
        this.playAction();
    }

    public void pickGardenerCard(){
        this.objectives.add(this.board.getGardenerCard());
        System.out.println("Le joueur "+this.getNom()+" à pioché une carte Jardinier!");
        this.playAction();
    }

}
