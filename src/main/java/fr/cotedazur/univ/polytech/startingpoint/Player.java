package fr.cotedazur.univ.polytech.startingpoint;
import java.util.ArrayList;
import java.util.List;

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
        //this.pickGardenerCard();
        this.pickPlotCard();
        //this.pickPandaCard();
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
        this.checkPatternOnBoard();
        System.out.println();
    }

    public void playForPatternCard(){
        ObjectivePlot objectivePlot = (ObjectivePlot) this.focusCard;
        List<TypeOfTile> colors = objectivePlot.getColors();
        List<Tile> tilesPicked = board.pickThreeTiles();
        System.out.println("Le joueur " +this.getNom() +" a pioche les tuiles suivantes :");
        for(Tile tile : tilesPicked){
            System.out.println(tile.getTypeOfTile());
        }
        if(objectivePlot.getPattern().type.equals(TypeOfPattern.LINE)){
            boolean isPlaced = false;
            List<Tile> tilesToPutBackInStack = new ArrayList<>();
            for(Tile tile : tilesPicked){
                if(tile.getTypeOfTile().equals(colors.get(0))&&!isPlaced){
                    isPlaced = true;
                    System.out.println(board.addTile(new Tile(board.bestCoordinateForLine(objectivePlot),tile.getTypeOfTile()))+ " de type:"+tile.getTypeOfTile());
                }
                else{
                    tilesToPutBackInStack.add(tile);
                }
            }
            if(!isPlaced){
                System.out.println(board.addTile(new Tile(board.scanAvailableTilePosition().get(0),tilesPicked.get(0).getTypeOfTile()))+ " de type:"+tilesPicked.get(0).getTypeOfTile());
                board.putBackInTileStack(tilesPicked.get(1));
                board.putBackInTileStack(tilesPicked.get(2));
            }
            else{
                board.putBackInTileStack(tilesToPutBackInStack.get(0));
                board.putBackInTileStack(tilesToPutBackInStack.get(1));
            }
            this.playAction();
        }

    }

    private void checkPatternOnBoard() {
    //take objective of type ObjectivePlot from the list objectives
        ArrayList<ObjectivePlot> objectivePlotList = new ArrayList<>();
        for(ObjectiveInterface objective : this.objectives){
            if(objective instanceof ObjectivePlot){
                objectivePlotList.add((ObjectivePlot) objective);
            }
        }
        for(ObjectivePlot objectivePlot : objectivePlotList){
            if(board.patternDetector.getPatternBoardList().contains(objectivePlot.getPattern())){
                this.point += objectivePlot.getNbPointsWin();
                System.out.println("Le joueur "+this.getNom()+" a gagne "+objectivePlot.getNbPointsWin()+" points pour avoir realise le pattern "+objectivePlot);
                this.objectives.remove(objectivePlot);
            }
        }
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
            System.out.println("Objectif accompli !");
        }
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
        System.out.println("Le joueur "+this.getNom()+" a pioche une carte Panda!");
        this.playAction();
    }

    public void pickPlotCard(){
        //TODO : make possible to pick other objective than LINE
        ObjectivePlot objectivePlot = this.board.getPlotCard();
        this.objectives.add(objectivePlot);
        System.out.println("Le joueur "+this.getNom()+" a pioche une carte Pattern de type "+objectivePlot.getType()+" et de couleur "+objectivePlot.getColors().get(0)+"!");
        this.playAction();
    }

    public void pickGardenerCard(){
        this.objectives.add(this.board.getGardenerCard());
        System.out.println("Le joueur "+this.getNom()+" a pioche une carte Jardinier!");
        this.playAction();
    }

}
