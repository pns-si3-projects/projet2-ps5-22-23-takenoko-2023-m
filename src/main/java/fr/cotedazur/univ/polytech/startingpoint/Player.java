package fr.cotedazur.univ.polytech.startingpoint;
import java.lang.reflect.Array;
import java.util.ArrayList;
public class Player {
    private int point = 0;
    private String nom;
    private final Board board;
    private int nbBamboo = 0;
    private int nbActions = 2;

    private ObjectiveInterface focusCard = null;

    private ArrayList<ObjectiveInterface> objectives = new ArrayList<ObjectiveInterface>();
    public Player(Board board, String nom, ArrayList<ObjectiveInterface> objectives){
        this.nom = nom;
        this.board = board;
        this.objectives = objectives;
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
            System.out.println(this.board.addTile(new Tile(availableCoordinates.get(0).getX(), availableCoordinates.get(0).getY())));
            this.playAction();
        }

        while(this.getNbActions()>0) {
            Tile gardenerPosition = this.board.getGardener().getTile();
            ArrayList<Coordinate> availablePositionsGardener = gardenerPosition.scanAvailableCoordinatesToMove(this.board.getBoardTiles());
            if(availablePositionsGardener.size()==0){
                ArrayList<Coordinate> availableCoordinates = this.board.scanAvailableTilePosition();
                System.out.println(this.board.addTile(new Tile(availableCoordinates.get(0).getX(), availableCoordinates.get(0).getY())));
                this.playAction();
            }else{
                this.board.moveGardenerOn(availablePositionsGardener.get(0));
                this.playAction();
            }
        }
        /* PREVIOUS VERSION -- KEEP IT HERE
        int numberTile = this.board.getBoardTiles().size();
        Tile tileToMove = this.board.getBoardTiles().get(numberTile-1);
        System.out.println(this.board.moveGardenerOn(tileToMove.getCoordinate()));
        this.playAction();

        if(this.getNbActions()==1){
            System.out.println(this.board.moveGardenerOn(tileToMove.getCoordinate()));
            this.playAction();
        }*/

        if(this.focusCard.isValid(this, this.board)){
            this.setPoint(this.getPoint()+this.focusCard.getNbPointsWin());
            ArrayList<ObjectiveInterface> objectifs = this.getObjective();
            objectifs.remove(focusCard);
            this.focusCard = null;
            System.out.println("Objecti accompli !");
        }
    }

    public void playForPandaCard(){
        while(this.getNbActions() > 0){
            Tile positionPanda = this.board.getPanda().getTile();
            ArrayList<Coordinate> availablePositionPanda = positionPanda.scanAvailableCoordinatesToMove(this.board.getBoardTiles());
            if(availablePositionPanda.size()==0){
                ArrayList<Coordinate> availablePositions = this.board.scanAvailableTilePosition();
                System.out.println(this.board.addTile(new Tile(availablePositions.get(0).getX(),availablePositions.get(0).getY())));
                this.playAction();
            }else{
                boolean pandaMove = false;
                for(Coordinate co : availablePositionPanda){
                    if(this.board.getTile(co).getBamboo()>0){
                        System.out.println(this.board.movePandaOn(co,this));
                        this.playAction();
                        pandaMove = true;
                        break;
                    }
                }
                if(!pandaMove){
                    ArrayList<Coordinate> availablePositions = this.board.scanAvailableTilePosition();
                    System.out.println(this.board.addTile(new Tile(availablePositions.get(0).getX(),availablePositions.get(0).getY())));
                    this.playAction();
                }
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

}
