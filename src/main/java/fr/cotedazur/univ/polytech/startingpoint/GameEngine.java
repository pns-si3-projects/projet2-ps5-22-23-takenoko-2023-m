package fr.cotedazur.univ.polytech.startingpoint;

import java.util.ArrayList;

public class GameEngine {
    Board board;
    ArrayList<Player> playerList = new ArrayList<>();

    public GameEngine(Board board, ArrayList<Player> players){
        this.board = board;
        this.playerList = players;
    }

    public void launchGame(){
        int indexPlayer = 0;
        while(true){
            //On mime ici les actions réalisées par les différents bots. La méthode play() produira une action réalisée par le bot et son tour sera fini
            this.playerList.get(indexPlayer).play();

            //Si suite à une action d'un bot, son nombre d'objectifs réalisés est de 9, alors la game est finie et le bot est le vainqueur de la partie
            //if(this.playerList.get(indexPlayer).getNbObjectifsRealises == 9){
            System.out.println("Le bot "+this.playerList.get(indexPlayer).getNom()+" a gagné la partie");
            break;
                //Print winner
                //break
            //}
            //indexPlayer++;
            /*if(indexPlayer == this.playerList.size()){
                indexPlayer=0;
            }*/
        }
    }
    public void printWinner(Player p){
        System.out.println("Le joueur est gagnant est : "+p.getNom()+" avec un score de "+p.getPoint()+" points marqués");
    }
}
