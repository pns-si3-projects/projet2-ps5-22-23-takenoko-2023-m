package fr.cotedazur.univ.polytech.startingpoint;
import java.util.ArrayList;
public class Player {
    private int point = 0;
    private String nom;
    private final Board board;
    private int nbBamboo = 0;

    //private ObjectivePlot objective ;
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

    public void play(){
        System.out.println(this.addTile(new Tile(0,0)));
        for(ObjectiveInterface objective : objectives){
            while(!objective.isValid(this.board)){
                if(objective .getType()=="gardener"){
                    ObjectiveGardener objectiveGardener = (ObjectiveGardener) objective;
                    this.playToAchieveObjectiveGardener( objectiveGardener);

                }
                else if (objective.getType()=="line2"){
                    System.out.println(this.playToAchieveObjectivePlot());
                }

            }
        }
        //this.addTile(tile);
    }

    public int playToAchieveObjectiveGardener(ObjectiveGardener objective){
        System.out.println("Le joueur joue pour un objectif de type jardin");
        int i = 0;
        boolean found = false;
        while(!found){
            for (Tile tile : this.board.getBoardTiles()){
                if(tile.getBamboo()-objective.getNb()== i*(-1) ){
                    this.board.getGardener().moveOn(tile.getCoordinate());
                    System.out.println("le jardinier est maintenant en "+tile.getCoordinate());
                    System.out.println("le un bambou a été planté en " + tile.getCoordinnateX() + " " + tile.getCoordinnateY());
                    found=true;
                    return 0;
                }
                else if (tile.getBamboo()-objective.getNb()== i ) {
                    this.board.getPanda().moveOn(tile.getCoordinate(), this);
                    System.out.println("le panda est maintenant en " + tile.getCoordinate());

                    tile.eatBamboo();
                    System.out.println("Le panda a mangé un bambou en " + tile.getCoordinnateX() + " " + tile.getCoordinnateY());
                    found=true;
                    return 0;
                }

                else{
                    i++;
                }
            }
        }
        return 1;
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

    public int nbObjectivesValid(){
        int nb = 0;
        for(ObjectiveInterface objective : this.objectives){
            if(objective.isValid(this.board)){
                nb++;
            }
        }
        return nb;
    }

   //play try to make objective valid by adding one tile

    //algorithme à optimiser mais pour l'instant j'ai pas trouvé mieux : pour chaque tuiles du board on vérifie si un des
    //emplacements autour est libre et si oui on ajoute une tuile à cet emplacement
    public String playToAchieveObjectivePlot(){
        System.out.println("Le joueur joue pour un objectif de type parcelle");
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

    public void upNbBamboo(){
        this.nbBamboo++;
    }


}
