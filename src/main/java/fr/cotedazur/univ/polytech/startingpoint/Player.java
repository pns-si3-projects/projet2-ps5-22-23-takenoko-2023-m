package fr.cotedazur.univ.polytech.startingpoint;

public class Player {
    private int point = 0;
    private String nom;
    private final Board board;

    private ObjectivePlot objective ;
    public Player(Board board, String nom, ObjectivePlot objective){
        this.nom = nom;
        this.board = board;
        this.objective = objective;
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
    public String addTile(Tile tile){
        return this.board.addTile(tile);
    }

    public ObjectivePlot getObjective() {
        return objective;
    }

    public void setObjective(ObjectivePlot objective) {
        this.objective = objective;
    }

    public boolean isObjectiveValid(){
        return this.objective.isValid(this.board);
    }


}
