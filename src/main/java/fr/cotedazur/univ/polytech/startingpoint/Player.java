package fr.cotedazur.univ.polytech.startingpoint;

public class Player {
    private int point = 0;
    private String nom;
    private final Board board;
    public Player(Board board, String nom){
        this.nom = nom;
        this.board = board;
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
}
