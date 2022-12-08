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

   //play try to make objective valid by adding one tile

    //algorithme à optimiser mais pour l'instant j'ai pas trouvé mieux : pour chaque tuiles du board on vérifie si un des
    //emplacements autour est libre et si oui on ajoute une tuile à cet emplacement
    public String playToAchieveObjective(){
        for(Tile tile : this.board.getBoardTiles()){
            if(!this.board.isInBoard(tile.getCoordinnateX()+1,tile.getCoordinnateY())){
                return this.addTile(new Tile(tile.getCoordinnateX()+1,tile.getCoordinnateY()));

            }
            else if(!this.board.isInBoard(tile.getCoordinnateX()-1,tile.getCoordinnateY())){
                return this.addTile(new Tile(tile.getCoordinnateX()-1,tile.getCoordinnateY()));

            }
            else if(!this.board.isInBoard(tile.getCoordinnateX(),tile.getCoordinnateY()+1)){
                return this.addTile(new Tile(tile.getCoordinnateX(),tile.getCoordinnateY()+1));

            }
            else if(!this.board.isInBoard(tile.getCoordinnateX(),tile.getCoordinnateY()-1)){
                return this.addTile(new Tile(tile.getCoordinnateX(),tile.getCoordinnateY()-1));

            }
            else if(!this.board.isInBoard(tile.getCoordinnateX()-1,tile.getCoordinnateY()+1)){
                return this.addTile(new Tile(tile.getCoordinnateX()-1,tile.getCoordinnateY()+1));

            }
            else if(!this.board.isInBoard(tile.getCoordinnateX()+1,tile.getCoordinnateY()-1)){
                return this.addTile(new Tile(tile.getCoordinnateX()+1,tile.getCoordinnateY()-1));

            }

        }
        //si tout les emplacement autour de toutes les  tuiles du board sont prises, il y a un problème
        return "erreur de placement";



    }


}
